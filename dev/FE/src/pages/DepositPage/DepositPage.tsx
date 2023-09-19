import { convertClassName, convertClassNameList } from '@/utils';
import styles from './DepositPage.module.scss';
import { DepositProduct } from '@/components/organisms';
import { Deposit } from '@/types';
import useModal from '@/hooks/useModal';
import { useState, useCallback, MouseEvent } from 'react';
import { Modal, ModalDeposit, Swiper } from '@/components/molecules';

interface DepositPageProps {
  className?: string;
}

export type LabelType = '입금' | '출금' | '가입';

// dummy data
const depositList: Deposit[] = [
  {
    bankName: '신한',
    productType: '입출금',
    interestRate: 1000,
    primeInterestRate: 1,
    savingBalance: 1,
  },
  {
    bankName: '하나',
    productType: '적금',
    interestRate: 2000,
    primeInterestRate: 1,
    savingBalance: 0,
  },
  {
    bankName: '국민',
    productType: '적금',
    interestRate: 3000,
    primeInterestRate: 1,
    savingBalance: 1,
  },
];

const DepositPage = ({ className }: DepositPageProps): JSX.Element => {
  const { isOpen, openToggle, closeToggle } = useModal();
  const [label, setLabel] = useState<LabelType>('입금');

  const onModal = useCallback((e: MouseEvent<HTMLButtonElement>) => {
    setLabel(e.currentTarget.value as LabelType);
    openToggle();
  }, []);

  return (
    <div
      className={convertClassNameList(
        convertClassName(className, styles),
        styles['deposit-page'],
      )}
    >
      <div className={styles['deposit-page__main']}>
        {depositList.map((deposit) => (
          <DepositProduct
            key={deposit.bankName + deposit.productType}
            deposit={deposit}
            onModal={onModal}
          />
        ))}
      </div>

      <Swiper>
        <div className={styles['deposit-page__swiper--item']}>aa</div>
        <div className={styles['deposit-page__swiper--item']}>aa</div>
        <div className={styles['deposit-page__swiper--item']}>aa</div>
        <div className={styles['deposit-page__swiper--item']}>aa</div>
      </Swiper>

      <Modal isOpen={isOpen} toggle={closeToggle}>
        <ModalDeposit
          className={className}
          label={label}
          onClick={() => console.log('구매!!!!')}
          toggle={closeToggle}
        />
      </Modal>
    </div>
  );
};

export default DepositPage;
