import { Text } from '@/components/atoms';
import styles from './RankingCard.module.scss';
import { convertClassName, priceFilter } from '@/utils';
import { FundingRanking } from '@/types';

interface RankingCardProps {
  fundingRanking: FundingRanking[];
  cardClassName?: string;
  titleClassName?: string;
  contentClassName?: string;
}

const RankingCard = ({
  fundingRanking,
  cardClassName,
  titleClassName,
  contentClassName,
}: RankingCardProps) => {
  const [no1, no2, no3] = fundingRanking;
  return (
    <div
      className={`${styles.rankingCard} ${convertClassName(
        cardClassName,
        styles,
      )}`}
    >
      <div className={styles.title}>
        <Text text="랭킹" className={`text-xl ${titleClassName}`} />
      </div>
      <div className={styles.content}>
        <Text
          text={
            no1
              ? `1등 : ${no1.userName} ${priceFilter(no1.userFundingAmount)}`
              : '1등 : x'
          }
          className={contentClassName}
        />
        <Text
          text={
            no2
              ? `2등 : ${no2.userName} ${priceFilter(no2.userFundingAmount)}`
              : '2등 : x'
          }
          className={contentClassName}
        />
        <Text
          text={
            no3
              ? `3등 : ${no3.userName} ${priceFilter(no3.userFundingAmount)}`
              : '3등 : x'
          }
          className={contentClassName}
        />
      </div>
    </div>
  );
};

export default RankingCard;
