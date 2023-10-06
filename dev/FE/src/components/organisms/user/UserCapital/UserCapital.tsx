import {
  convertClassName,
  convertClassNameList,
  convertDate,
  priceFilter,
} from '@/utils';
import styles from './UserCapital.module.scss';
import { TotalCapital } from '@/types';
import { PieChart, Table, Text } from '@/components/atoms';
import { useMemo } from 'react';

interface UserCapitalProps {
  className?: string;
  totalCapitals?: TotalCapital[];
  curCapital?: TotalCapital;
}

const UserCapital = ({
  className,
  totalCapitals,
  curCapital,
}: UserCapitalProps): JSX.Element => {
  const totalCapital = useMemo<TotalCapital | undefined>(() => {
    return totalCapitals?.find(({ userNo }) => userNo === curCapital?.userNo);
  }, [curCapital]);

  return (
    <div
      className={convertClassNameList(
        convertClassName(className, styles),
        styles['user-capital'],
      )}
    >
      {totalCapital && (
        <>
          <div className={styles['user-capital__pie-chart-container']}>
            <div className={styles['user-capital__pie-chart']}>
              <Text text="자산 현황" />
              <PieChart
                chartData={{
                  labels: ['주식', '예적금', '펀딩', '포인트'],
                  data: [
                    totalCapital.stockMoney,
                    totalCapital.depositMoney,
                    totalCapital.fundingMoney,
                    totalCapital.point,
                  ],
                  length: 4,
                }}
              />
            </div>
            <div className={styles['user-capital__pie-chart']}>
              <Text text="주식 현황" />
              <PieChart
                chartData={{
                  labels: totalCapital.stockList.map(
                    ({ stockName }) => stockName,
                  ),
                  data: totalCapital.stockList.map(
                    ({ stockCount }) => stockCount,
                  ),
                  length: totalCapital.stockList.length,
                }}
              />
            </div>
          </div>

          <Table
            className={styles['user-list__table']}
            data={
              totalCapital.pointRecord &&
              totalCapital.pointRecord
                .sort((a, b) => b.changedTime - a.changedTime)
                .map(({ useType, pointChange, changedTime }) => {
                  return {
                    타입: useType,
                    변화량: (
                      <Text
                        className={
                          pointChange > 0
                            ? 'blue'
                            : pointChange !== 0
                            ? 'red'
                            : ''
                        }
                        text={priceFilter(pointChange)}
                      />
                    ),
                    시간: convertDate(changedTime),
                  };
                })
            }
            size={3}
          />
        </>
      )}
    </div>
  );
};

export default UserCapital;
