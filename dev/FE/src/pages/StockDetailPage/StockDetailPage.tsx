import { useState, useCallback, MouseEvent, useEffect } from 'react';
import { convertClassName, convertClassNameList, loremData } from '@/utils';
import styles from './StockDetailPage.module.scss';
import useModal from '@/hooks/useModal';
import { Button, LineChart } from '@/components/atoms';
import { Modal, ModalStock } from '@/components/molecules';
import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { Stock, StockAccount } from '@/types';
import { useParams } from 'react-router-dom';
import {
  getStockChartQuery,
  getStockMyAccountQuery,
  postStockBuyingQuery,
  postStockSellingQuery,
} from '@/api/queries/stock';

interface StockDetailPageProps {
  className?: string;
}

const StockDetailPage = ({ className }: StockDetailPageProps): JSX.Element => {
  const { isOpen, toggle } = useModal();
  const [label, setLabel] = useState('매도');
  const [chartData, setChartData] = useState<any | undefined>();
  const { detail } = useParams<{ detail: string }>();

  const onModal = useCallback((e: MouseEvent<HTMLButtonElement>) => {
    setLabel(e.currentTarget.value);
    toggle();
  }, []);

  const { data: myStock, status: isMyStockLoading } = useQuery(
    getStockMyAccountQuery(Number(detail)),
  );

  const { data: stockChart, status: isStockChartLoading } = useQuery<Stock[]>(
    getStockChartQuery(Number(detail)),
  );

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
        toggle();
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
        toggle();
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
        {loremData}
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
          <div
            className={convertClassNameList(
              styles['stock-detail-page__content--prev-news'],
            )}
          >
            news
          </div>
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
        <Modal isOpen={isOpen} toggle={toggle}>
          <ModalStock
            setStockAccount={setStockAccount}
            className={className}
            label={label}
            onClick={label === '매도' ? handleStockSelling : handleStockBuying}
            toggle={toggle}
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
