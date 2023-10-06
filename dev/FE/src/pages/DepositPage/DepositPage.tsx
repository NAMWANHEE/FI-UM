import { convertClassName, convertClassNameList } from '@/utils';
import styles from './DepositPage.module.scss';
import { DepositProduct } from '@/components/organisms';
import { MyBankInfo } from '@/types';
import useModal from '@/hooks/useModal';
import { useState, useCallback, useEffect } from 'react';
import { Modal, ModalDeposit, Swiper } from '@/components/molecules';
import { getBankInfoQuery } from '@/api/queries';
import { useQuery } from '@tanstack/react-query';
import useAuth from '@/hooks/useAuth';
import { Text } from '@/components/atoms';

interface DepositPageProps {
  className?: string;
}

export type LabelType = '입금' | '출금' | '가입' | '해지';

const DepositPage = ({ className }: DepositPageProps): JSX.Element => {
  const { isOpen, openToggle, closeToggle } = useModal();
  const [label, setLabel] = useState<LabelType>('입금');
  const [curDeposit, setCurDeposit] = useState<MyBankInfo | undefined>(
    undefined,
  );
  const { data: depositList, refetch } = useQuery<MyBankInfo[]>(
    getBankInfoQuery(),
  );
  const { userInfo } = useAuth();

  const onModal = useCallback((label: string, deposit: MyBankInfo) => {
    setLabel(label as LabelType);
    setCurDeposit(deposit);
    openToggle();
  }, []);

  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const timer = setTimeout(() => {
      setLoading(false);
    }, 700);
    return () => clearTimeout(timer);
  }, []);

  return (
    <>
      {loading && (
        <div className="page-loading">
          <img
            style={{ height: '250px', width: '250px' }}
            src="./img/loading/deposit.gif"
          />
        </div>
      )}
      <div
        className={convertClassNameList(
          convertClassName(className, styles),
          styles['deposit-page'],
        )}
      >
        <div
          className={convertClassNameList(
            styles['deposit-page__main'],
            styles['deposit-page__bank'],
          )}
        >
          {depositList?.map((deposit) => (
            <DepositProduct
              key={deposit.bankName + deposit.productType}
              deposit={deposit}
              onModal={onModal}
            />
          ))}
        </div>

        <Swiper
          className={convertClassNameList(
            'bg-gray-light',
            styles['deposit-page__swiper'],
          )}
          type="autoplay"
        >
          {depositList?.map((deposit) => (
            <div
              key={deposit.bankName + deposit.productType}
              className={convertClassNameList(
                styles['deposit-page__swiper--item'],
                styles['deposit-page__main'],
              )}
            >
              <Text className="text-sm" text={deposit.description} />
            </div>
          ))}
        </Swiper>

        {curDeposit && (
          <Modal isOpen={isOpen} toggle={closeToggle}>
            <ModalDeposit
              point={userInfo?.point ?? 0}
              className={className}
              label={label}
              deposit={curDeposit}
              refetch={refetch}
              toggle={closeToggle}
            />
          </Modal>
        )}
      </div>
    </>
  );
};

export default DepositPage;
