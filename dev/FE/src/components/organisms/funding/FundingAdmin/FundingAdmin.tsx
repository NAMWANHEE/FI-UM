import { convertClassName, convertClassNameList } from '@/utils';
import styles from './FundingAdmin.module.scss';
import { FundingItem } from '../..';
import { Table } from '@/components/atoms';
import { useQuery } from '@tanstack/react-query';
import { getMyFundingsQuery } from '@/api/queries/funding';
import { Funding } from '@/types';

interface FundingAdminProps {
  className?: string;
}

const FundingAdmin = ({ className }: FundingAdminProps): JSX.Element => {
  const { data: fundings } = useQuery<Funding[], Error>(getMyFundingsQuery());

  return (
    <div
      className={convertClassNameList(
        convertClassName(className, styles),
        'flex-container jc-center',
      )}
    >
      <div>
        {fundings?.map((funding) => (
          <FundingItem key={funding.itemNo} {...funding} onModal={() => {}} />
        ))}
      </div>
      <div>
        <Table />
      </div>
    </div>
  );
};

export default FundingAdmin;
