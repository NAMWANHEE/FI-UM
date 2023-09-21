import { useState, useCallback, MouseEvent, useEffect } from 'react';
import { convertClassName, convertClassNameList } from '@/utils';
import styles from './StockDetailPage.module.scss';
import useModal from '@/hooks/useModal';
import { Button, LineChart } from '@/components/atoms';
import { Modal, ModalStock } from '@/components/molecules';
import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { News, Stock, StockAccount, TradeHistory } from '@/types';
import { useParams } from 'react-router-dom';
import {
  getStockChartQuery,
  getStockMyAccountQuery,
  getStockNewsQuery,
  getTradeHistoryQuery,
  postStockBuyingQuery,
  postStockSellingQuery,
} from '@/api/queries/stock';

interface StockDetailPageProps {
  className?: string;
}

const StockDetailPage = ({ className }: StockDetailPageProps): JSX.Element => {
  const { isOpen, openToggle, closeToggle } = useModal();
  const [label, setLabel] = useState('매도');
  const [chartData, setChartData] = useState<any | undefined>();
  const { detail } = useParams<{ detail: string }>();
  const [news, setNews] = useState<News[] | null>(null);

  const onModal = useCallback((e: MouseEvent<HTMLButtonElement>) => {
    setLabel(e.currentTarget.value);
    openToggle();
  }, []);

  const { data: myStock, status: isMyStockLoading } = useQuery(
    getStockMyAccountQuery(Number(detail)),
  );

  const { data: stockChart, status: isStockChartLoading } = useQuery<Stock[]>(
    getStockChartQuery(Number(detail)),
  );

  const { data: stockNews, status: isStockNewsLoading } = useQuery<
    News[],
    string
  >(getStockNewsQuery(Number(detail)));
  useEffect(() => {
    if (isStockNewsLoading === 'success') {
      setNews(stockNews);
    }
  }, [isStockNewsLoading]);

  const { data: tradeHistory, status: isTradeHistoryLoading } = useQuery<
    TradeHistory[],
    string
  >(getTradeHistoryQuery(Number(detail)));

  useEffect(() => {
    if (isStockChartLoading === 'success' && stockChart) {
      const newChartData = {
        labels: stockChart.map((_, idx) => String(idx)),
        datasets: [
          {
            type: 'line',
            label: stockChart[0].stockName,
            backgroundColor: 'rgb(255, 255, 255)',
            data: stockChart.map((item) => item.nowPrice),
            borderColor: stockChart.map((item, idx) => {
              if (idx === 0) return 'rgb(0,0,0)';
              return item.fluctuationPrice < 0 ? 'blue' : 'red';
            }),
            borderWidth: 2,
          },
        ],
      };

      setChartData(newChartData);
    }
  }, [stockChart, isStockChartLoading]);

  const [stockAccount, setStockAccount] = useState<StockAccount>({
    stockNo: 0,
    price: 0,
    count: 0,
  });
  const queryClient = useQueryClient();

  // 주식 구매 함수
  const stockBuyingMutaion = useMutation(
    postStockBuyingQuery(stockAccount).mutationFn,
    {
      onSuccess: (res) => {
        console.log(res);
        console.log('매수 성공');
        queryClient.invalidateQueries(['getStockMyAccount']);
        closeToggle();
      },
      onError: (err) => {
        console.log(err);
        console.log('매수 실패');
      },
    },
  );
  const handleStockBuying = () => {
    console.log(stockAccount);
    stockBuyingMutaion.mutate();
  };

  // 주식 판매 함수
  const stockSellingMutation = useMutation(
    postStockSellingQuery(stockAccount).mutationFn,
    {
      onSuccess: (res) => {
        console.log(res);
        console.log('매도 성공');
        queryClient.invalidateQueries(['getStockMyAccount']);
        closeToggle();
      },
      onError: (err) => {
        console.log(err);
        console.log('매도 실패');
      },
    },
  );
  const handleStockSelling = () => {
    stockSellingMutation.mutate();
  };

  return (
    <div
      className={convertClassNameList(
        convertClassName(className, styles),
        styles['stock-detail-page'],
      )}
    >
      <div className={convertClassNameList(styles['stock-detail-page__news'])}>
        {news &&
          `${news[0].newsTitle} ${news[0].newsContent} ${news[0].newsTitle} ${news[0].newsContent} ${news[0].newsTitle} ${news[0].newsContent} `}
      </div>
      <div className="flex-container">
        {isStockChartLoading === 'success' ? (
          <LineChart
            data={chartData}
            className={convertClassNameList(
              styles['stock-detail-page__line-chart'],
            )}
          />
        ) : (
          ''
        )}

        <div
          className={convertClassNameList(styles['stock-detail-page__content'])}
        >
          <div className={styles.tradeHistory}>
            <div className={styles.tradeHistoryTitle}>최근 거래 내역</div>
            <div>
              {tradeHistory &&
                tradeHistory.map((item, idx) => {
                  const date = new Date(item.tradeTime);
                  return (
                    <div key={idx} className={styles.tradeHistoryWrapper}>
                      <div>{item.stockCount < 0 ? '매도' : '매수'}</div>
                      <div>{item.stockCount}</div>
                      <div>{`${date.getDate()}일 ${date.getHours()}시 ${date.getMinutes()}분`}</div>
                    </div>
                  );
                })}
            </div>
          </div>
          {isStockChartLoading === 'success' &&
            isMyStockLoading === 'success' && (
              <div className={styles.myStock}>
                <p>내가 갖고있는 수량: {myStock.stockCount}</p>
                <p>평균 단가: {myStock.stockAverage}</p>
                <p>현재 가격: {stockChart[stockChart.length - 1].nowPrice}</p>
              </div>
            )}
          <div
            className="
            flex-container jc-space-between"
          >
            <Button
              className={convertClassNameList(
                'bg-blue white',
                styles['stock-detail-page__content--button'],
              )}
              label="매도"
              value="매도"
              onClick={onModal}
            />
            <Button
              className={convertClassNameList(
                'bg-red white',
                styles['stock-detail-page__content--button'],
              )}
              label="매수"
              value="매수"
              onClick={onModal}
            />
          </div>
        </div>
      </div>

      {isMyStockLoading === 'success' && isStockChartLoading === 'success' && (
        <Modal isOpen={isOpen} toggle={closeToggle}>
          <ModalStock
            setStockAccount={setStockAccount}
            className={className}
            label={label}
            onClick={label === '매도' ? handleStockSelling : handleStockBuying}
            toggle={closeToggle}
            price={stockChart[stockChart.length - 1].nowPrice}
            stockNo={Number(detail)}
            totalCount={
              label === '매도'
                ? myStock.stockCount
                : Math.floor(
                    myStock.point / stockChart[stockChart.length - 1].nowPrice,
                  )
            }
          />
        </Modal>
      )}
    </div>
  );
};

export default StockDetailPage;
