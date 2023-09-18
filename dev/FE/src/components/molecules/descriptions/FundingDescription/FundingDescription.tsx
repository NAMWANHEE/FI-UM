import { FundingItemStatus, RankingCard } from '@/components/molecules';
import { convertClassName, convertClassNameList } from '@/utils';
import styles from './FundingDescription.module.scss';
import { Funding } from '@/types';

interface FundingDescriptionProps {
  className?: string;
  funding: Funding;
}

const FundingDescription = ({
  className,
  funding,
}: FundingDescriptionProps): JSX.Element => {
  return (
    <div
      className={
        (convertClassNameList(convertClassName(className, styles)),
        styles['funding-description'])
      }
    >
      <RankingCard no1="노태균" no2="신기정" no3="김승우" />
      <FundingItemStatus funding={funding} />
    </div>
  );
};

export default FundingDescription;