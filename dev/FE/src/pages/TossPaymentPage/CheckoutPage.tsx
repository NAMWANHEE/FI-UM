import { useEffect, useRef, useState } from 'react';
import {
  PaymentWidgetInstance,
  loadPaymentWidget,
  // ANONYMOUS,
} from '@tosspayments/payment-widget-sdk';
import { nanoid } from 'nanoid';
import styles from './CheckoutPage.module.scss';
import useAuth from '@/hooks/useAuth';

// import '../App.css';

const selector = '#payment-widget';
// const clientKey = 'test_ck_Z1aOwX7K8mevlzZnZPA8yQxzvNPG';
// const clientKey = 'test_ck_6BYq7GWPVv5gmjp5pG73NE5vbo1d';
const clientKey = 'test_ck_D5GePWvyJnrK0W0k6q8gLzN97Eoq';
const customerKey = 'YbX2HuSlsC9uVJW6NMRMj';

const CheckoutPage = () => {
  const paymentWidgetRef = useRef<PaymentWidgetInstance | null>(null);
  const paymentMethodsWidgetRef = useRef<ReturnType<
    PaymentWidgetInstance['renderPaymentMethods']
  > | null>(null);
  const [price, setPrice] = useState(50000);

  useEffect(() => {
    (async () => {
      // ------  결제위젯 초기화 ------
      // 비회원 결제에는 customerKey 대신 ANONYMOUS를 사용하세요.
      const paymentWidget = await loadPaymentWidget(clientKey, customerKey); // 회원 결제
      // const paymentWidget = await loadPaymentWidget(clientKey, ANONYMOUS); // 비회원 결제

      // ------  결제위젯 렌더링 ------
      // https://docs.tosspayments.com/reference/widget-sdk#renderpaymentmethods선택자-결제-금액-옵션
      const paymentMethodsWidget = paymentWidget.renderPaymentMethods(
        selector,
        { value: price },
      );

      // ------  이용약관 렌더링 ------
      // https://docs.tosspayments.com/reference/widget-sdk#renderagreement선택자
      paymentWidget.renderAgreement('#agreement');

      paymentWidgetRef.current = paymentWidget;
      paymentMethodsWidgetRef.current = paymentMethodsWidget;
    })();
  }, []);

  useEffect(() => {
    // 최소 최대 금액 제한
    if (price < 0) {
      setPrice(0);
    }
    if (price > 100000) {
      setPrice(100000);
    }

    const paymentMethodsWidget = paymentMethodsWidgetRef.current;

    if (paymentMethodsWidget == null) {
      return;
    }

    // ------ 금액 업데이트 ------
    // https://docs.tosspayments.com/reference/widget-sdk#updateamount결제-금액
    paymentMethodsWidget.updateAmount(
      price,
      paymentMethodsWidget.UPDATE_REASON.COUPON,
    );
  }, [price]);

  // 유저 정보
  const userProfile = useAuth();
  const userName = userProfile.userInfo?.userName;

  return (
    <div>
      <h1 className={styles.header}>캐시 충전</h1>
      <div className={styles.inputWrapper}>
        <input
          className={styles.priceInput}
          type="number"
          value={price}
          onChange={(e) => {
            setPrice(parseInt(e.target.value, 10));
          }}
        />{' '}
      </div>
      {/* <span>{`${price.toLocaleString()}원`}</span> */}
      <div id="payment-widget" />
      <div id="agreement" />
      <div className={styles.buttonWrapper}>
        <button
          className={styles.paymentButton}
          onClick={async () => {
            const paymentWidget = paymentWidgetRef.current;

            try {
              // ------ '결제하기' 버튼 누르면 결제창 띄우기 ------
              // https://docs.tosspayments.com/reference/widget-sdk#requestpayment결제-정보
              await paymentWidget
                ?.requestPayment({
                  orderId: nanoid(),
                  orderName: '피움 캐시 충전',
                  customerName: userName,
                  customerEmail: 'customer123@gmail.com',
                  successUrl: `${window.location.origin}/tosspay/success`,
                  failUrl: `${window.location.origin}/fail`,
                })
                .then((res) => {
                  alert(res);
                });
            } catch (error) {
              // 에러 처리하기
              console.error(error);
            }
          }}
        >
          결제하기
        </button>
      </div>
    </div>
  );
};

export default CheckoutPage;
