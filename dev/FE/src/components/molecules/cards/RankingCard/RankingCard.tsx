import { Text } from '@/components/atoms';
import styles from './RankingCard.module.scss';
import { convertClassName } from '@/utils';

interface RankingCardProps {
  no1: string;
  no2: string;
  no3: string;
  cardClassName?: string;
  titleClassName?: string;
  contentClassName?: string;
}

const RankingCard = ({
  no1,
  no2,
  no3,
  cardClassName,
  titleClassName,
  contentClassName,
}: RankingCardProps) => {
  const contentText = [`1등 : ${no1}`, `2등 : ${no2}`, `3등 : ${no3}`];
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
        {contentText.map((item) => {
          return <Text key={item} text={item} className={contentClassName} />;
        })}
      </div>
    </div>
  );
};

export default RankingCard;
