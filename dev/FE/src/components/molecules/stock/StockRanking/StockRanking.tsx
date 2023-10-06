import { PieChart, Text } from '@/components/atoms';
import styles from './StockRanking.module.scss';
import { convertClassName } from '@/utils';
import { MyStock } from '@/types';

interface StockRankingProps {
  title: string;
  className?: string;
  titleClassName?: string;
  stockList: MyStock[];
}

const StockRanking = ({
  title,
  className,
  titleClassName,
  stockList,
}: StockRankingProps) => {
  const labels = stockList.map(
    (myStock) => `${myStock.stockName}(${myStock.stockCount})`,
  );
  const data = stockList.map(
    (myStock) => myStock.stockCount * myStock.stockNowPrice,
  );

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
        <PieChart chartData={{ labels: labels, data: data, length: 5 }} />
      </div>
    </div>
  );
};

export default StockRanking;
