import { BankLogo, Button, Text } from '@/components/atoms';
import styles from './DepositProduct.module.scss';
import {
  convertClassName,
  convertClassNameList,
  convertDataRemainDays,
  convertDateAfter7days,
  priceFilter,
} from '@/utils';
import { useMemo } from 'react';
import { MyBankInfo } from '@/types';
import { LabelType } from '@/pages/DepositPage/DepositPage';

interface DepositProductProps {
  className?: string;
  deposit: MyBankInfo;
  onModal: (label: LabelType, deposit: MyBankInfo) => void;
}

const DepositProduct = ({
  className,
  deposit,
  onModal,
}: DepositProductProps) => {
  const { bankLogoClassName } = useMemo(() => {
    let bankLogoClassName = undefined;
    switch (deposit.bankName) {
      case '햇살은행':
        bankLogoClassName = 'shinhan';
        break;
      case '유니콘은행':
        bankLogoClassName = 'kb';
        break;

      default:
        bankLogoClassName = 'hana normal';
        break;
    }
    return {
      bankLogoClassName,
    };
  }, [deposit.bankName]);

  return (
    <div
      className={`${styles['deposit-product']} ${convertClassName(
        className,
        styles,
      )}`}
    >
      <div className={styles.banklogo}>
        <BankLogo className={bankLogoClassName} />
      </div>
      <div className={styles.title}>
        <Text
          className="text-sm"
          text={`${deposit.bankName} ${deposit.productType}`}
        />
        <Text
          className="text-sm"
          text={priceFilter(deposit.savingBalance ?? deposit.depositMoney)}
        />
      </div>
      <div className={styles.buttons}>
        {deposit.productType == '예금' && (
          <>
            <Button
              onClick={() => onModal('입금', deposit)}
              label="입금"
              value="입금"
              className={convertClassNameList(
                'primary xsmall',
                styles['deposit-product__item'],
              )}
            />
            <Button
              onClick={() => onModal('출금', deposit)}
              label="출금"
              value="출금"
              className={convertClassNameList(
                'gray xsmall',
                styles['deposit-product__item'],
              )}
            />
          </>
        )}
        {deposit.productType == '적금' &&
          (deposit.savingBalance ?? deposit.depositMoney ? (
            <>
              <div
                className={convertClassNameList(
                  styles['deposit-product__item'],
                )}
              >
                <Text
                  className={styles['deposit-product__text']}
                  text={`남은 기간: ${convertDataRemainDays(
                    deposit.createSaving,
                    deposit.bankName,
                  )}일`}
                />
                <Text
                  className={styles['deposit-product__text']}
                  text={`만기일: ${convertDateAfter7days(
                    deposit.createSaving,
                    deposit.bankName,
                  )}`}
                />
                <Text
                  className={styles['deposit-product__text']}
                  text={`이자율: ${
                    deposit.interestRate + deposit.primeInterestRate
                  }%`}
                />
              </div>
            </>
          ) : (
            <>
              <div
                className={convertClassNameList(
                  styles['deposit-product__item'],
                )}
              >
                <Text
                  className="text-sm"
                  text={`거치기간: ${
                    deposit.bankName === '햇살은행' ? 7 : 10
                  }일`}
                />
                <Text
                  className="text-sm"
                  text={`이자율: ${deposit.interestRate}% ~ ${
                    deposit.interestRate + deposit.primeInterestRate
                  }%`}
                />
              </div>
              <Button
                onClick={() => onModal('가입', deposit)}
                label="가입"
                value="가입"
                className={convertClassNameList(
                  'primary xsmall',
                  styles['deposit-product__item'],
                )}
              />
            </>
          ))}
      </div>
    </div>
  );
};

export default DepositProduct;
