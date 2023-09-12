import { BankLogo, Button, Text } from '@/components/atoms';
import styles from './DepositProduct.module.scss';
import { convertClassName, formatCurrency } from '@/utils';

interface DepositProductProps {
  status: number;
  bankLogoClassName: string;
  className?: string;
  title: string;
}

const DepositProduct = ({
  status,
  bankLogoClassName,
  className,
  title,
}: DepositProductProps) => {
  return (
    <div
      className={`${styles.depositProduct} ${convertClassName(
        className,
        styles,
      )}`}
    >
      <div className={styles.banklogo}>
        <BankLogo className={bankLogoClassName} />
      </div>
      <div className={styles.title}>
        <Text text={title} />
        <Text text={formatCurrency('123123')} />
      </div>
      <div className={styles.buttons}>
        {status === 1 && (
          <>
            <Button label="입금" className="primary xsmall" />
            <Button label="출금" className="gray xsmall" />
          </>
        )}
        {status === 2 && (
          <>
            <Text text="만기일 : 2023. 09. 15" />
          </>
        )}
        {status === 3 && (
          <>
            <div>
              <Text text="거치기간: 7일" />
              <Text text="이자율: 7%" />
            </div>
            <Button label="가입" className="primary xsmall" />
          </>
        )}
      </div>
    </div>
  );
};

export default DepositProduct;
