import { getTossPay } from '@/api/payment';
import { useEffect } from 'react';
import { useLocation } from 'react-router-dom';

const TossSuccessPage = () => {
  const location = useLocation();
  const queryParams = new URLSearchParams(location.search);
  const orderId = queryParams.get('orderId');
  const paymentKey = queryParams.get('paymentKey');
  const amount = queryParams.get('amount');

  useEffect(() => {
    if (paymentKey && orderId && amount) {
      getTossPay({ paymentKey, orderId, amount })
        .then(() => {
          alert(`${amount}원 충전 되었습니다.`);
          window.close();
        })
        .catch(() => {
          alert('충전 실패하였습니다.');
          window.close();
        });
    }
  }, []);

  return <></>;
};

export default TossSuccessPage;
