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
  AccordionItemState,
} from 'react-accessible-accordion';
import { convertClassNameList } from '@/utils';
import { useEffect, useState } from 'react';

const StockPage = () => {
  const { data: allStocks, status: isAllStocksLoading } = useQuery<
    Stock[],
    string
  >(['getStockInformation'], getStocks);
  const { data: stockKing } = useQuery<StockRank[], Error>(
    ['getStockKing'],
    getStockKing,
  );
  const { data: recentNews } = useQuery<News[], string>(getRecentNewsQuery());
  const navigate = useNavigate();

  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const timer = setTimeout(() => {
      setLoading(false);
    }, 700);

    return () => clearTimeout(timer);
  }, []);

  return (
    <>
      {loading && (
        <div className="page-loading">
          <img
            style={{ height: '250px', width: '250px' }}
            src="./img/loading/stock.gif"
          />
        </div>
      )}
      <div className={styles.stockPage}>
        <div className={styles.allStocks}>
          <div className={styles.allStocks__title}>주식 리스트</div>
          {isAllStocksLoading === 'success' ? (
            <Table data={allStocks} onClick={navigate} checkPagination={true} />
          ) : (
            ''
          )}
        </div>
        <div className={styles.newsWrapper}>
          <div className={styles.newsTitle}>최신 뉴스</div>
          <Accordion allowZeroExpanded={true}>
            {recentNews &&
              recentNews.map((item) => {
                return (
                  <AccordionItem key={item.newsNo}>
                    <AccordionItemHeading>
                      <AccordionItemButton>
                        <AccordionItemState>
                          {({ expanded }) =>
                            expanded ? (
                              <>
                                <div
                                  className={convertClassNameList(
                                    styles.open,
                                    styles.accordionHeader,
                                  )}
                                >
                                  <span
                                    className={convertClassNameList(
                                      styles.title,
                                      styles.title__open,
                                    )}
                                  >
                                    {item.newsTitle}
                                  </span>
                                  <div className={styles.angle__down}></div>
                                </div>
                              </>
                            ) : (
                              <>
                                <div
                                  className={convertClassNameList(
                                    styles.close,
                                    styles.accordionHeader,
                                  )}
                                >
                                  <span className={styles.title}>
                                    {item.newsTitle}
                                  </span>
                                  <div className={styles.angle__up}></div>
                                </div>
                              </>
                            )
                          }
                        </AccordionItemState>
                      </AccordionItemButton>
                    </AccordionItemHeading>
                    <AccordionItemPanel className={styles.accordionItem}>
                      <span>{item.newsContent}</span>
                    </AccordionItemPanel>
                  </AccordionItem>
                );
              })}
          </Accordion>
        </div>
        <div className={styles.rankingWrapper}>
          <div className={styles.rankingWrapper__title}>주식 랭킹왕</div>
          {stockKing &&
            stockKing.map((item) => {
              if (item.userName === null || item.stockList === null) {
                return '';
              }
              return (
                <StockRanking
                  className={styles.rankingItem}
                  key={item.userName}
                  title={item.userName}
                  stockList={item.stockList}
                />
              );
            })}
        </div>
      </div>
    </>
  );
};

export default StockPage;
