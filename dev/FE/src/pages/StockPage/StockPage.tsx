import { getStockKing, getStocks } from '@/api/stock';
import { Table } from '@/components/atoms';
import { StockRanking } from '@/components/molecules';
import { News, Stock, StockRanking as StockRank } from '@/types';
import { useQuery } from '@tanstack/react-query';
import { useNavigate } from 'react-router-dom';
import styles from './StockPage.module.scss';
import { getRecentNewsQuery } from '@/api/queries';
import {
  Accordion,
  AccordionItem,
  AccordionItemButton,
  AccordionItemHeading,
  AccordionItemPanel,
} from 'react-accessible-accordion';

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
  const { data: recentNews, status: isRecentNewsLoading } = useQuery<
    News[],
    string
  >(getRecentNewsQuery());
  console.log(recentNews, '뉴스');
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
      <div className={styles.newsWrapper}>
        <Accordion preExpanded={['a', 'c']}>
          {recentNews &&
            recentNews.map((item) => {
              return (
                <AccordionItem key={item.newsNo}>
                  <AccordionItemHeading className={styles.accordionHeader}>
                    <AccordionItemButton>{item.newsTitle}</AccordionItemButton>
                  </AccordionItemHeading>
                  <AccordionItemPanel className={styles.accordionItem}>
                    <p>{item.newsContent}</p>
                  </AccordionItemPanel>
                </AccordionItem>
              );
            })}
        </Accordion>
      </div>
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
