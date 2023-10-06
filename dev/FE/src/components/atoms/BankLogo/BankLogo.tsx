import { convertClassName } from '@/utils';
import styles from '@/components/atoms/BankLogo/BankLogo.module.scss';

interface BankLogoProps {
  className: string;
}

const BankLogo = ({ className }: BankLogoProps) => {
  return (
    <>
      <div className={convertClassName(className, styles)}>
        <img
          className={styles.img}
          src={`/img/bankicon/${className}.png`}
          alt=""
        />
      </div>
    </>
  );
};

export default BankLogo;
