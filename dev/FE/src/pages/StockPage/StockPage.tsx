import { getStockKing, getStocks } from '@/api/stock';
import { Table } from '@/components/atoms';
import { StockRanking } from '@/components/molecules';
import { Stock, StockRanking as StockRank } from '@/types';
import { useQuery } from '@tanstack/react-query';
import { useNavigate } from 'react-router-dom';
import styles from './StockPage.module.scss';

const StockPage = () => {
  const { data: allStocks, status: isAllStocksLoading } = useQuery<
    Stock[],
    string
  >(['getStockInformation'], getStocks);
  console.log(allStocks);
  const { data: stockKing, status: isStockKingLoading } = useQuery<
    StockRank[],
    string
  >(['getStockKing'], getStockKing);
  const navigate = useNavigate();
  return (
    <div className={styles.stockPage}>
      <div className={styles.allStocks}>
        {isAllStocksLoading === 'success' ? (
          <Table data={allStocks} onClick={navigate} />
        ) : (
          ''
        )}
      </div>
      <div className={styles.newsWrapper}>뉴스 들어갈곳</div>
      <div className={styles.rankingWrapper}>
        {isStockKingLoading === 'success'
          ? stockKing.map((item) => {
              return <StockRanking key={item.userName} title={item.userName} />;
            })
          : ''}
      </div>
    </div>
  );
};

export default StockPage;
