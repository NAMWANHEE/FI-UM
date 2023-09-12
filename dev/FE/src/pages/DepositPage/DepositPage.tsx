import { convertClassName, convertClassNameList } from '@/utils';
import styles from './DepositPage.module.scss';
import { DepositProduct } from '@/components/organisms';

interface DepositPageProps {
  className?: string;
}

const DepositPage = ({ className }: DepositPageProps): JSX.Element => {
  return (
    <div className={convertClassNameList(convertClassName(className, styles))}>
      <DepositProduct
        status={1}
        title="신한 입출금"
        bankLogoClassName="shinhan normal"
      />
      <DepositProduct
        status={2}
        title="국민 예금"
        bankLogoClassName="kb normal"
      />
      <DepositProduct
        status={3}
        title="하나 적금"
        bankLogoClassName="hana normal"
      />
    </div>
  );
};

export default DepositPage;
