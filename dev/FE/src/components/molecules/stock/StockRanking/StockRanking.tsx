import { PieChart, Text } from '@/components/atoms';
import styles from './StockRanking.module.scss';
import { convertClassName } from '@/utils';

interface StockRankingProps {
  title: string;
  className?: string;
  titleClassName?: string;
}

const StockRanking = ({
  title,
  className,
  titleClassName,
}: StockRankingProps) => {
  return (
    <div
      className={`${styles.stockRanking} ${convertClassName(
        className,
        styles,
      )}`}
    >
      <div className={styles.title}>
        <Text text={title} className={titleClassName} />
      </div>
      <div className={styles.chart}>
        <PieChart />
      </div>
    </div>
  );
};

export default StockRanking;
