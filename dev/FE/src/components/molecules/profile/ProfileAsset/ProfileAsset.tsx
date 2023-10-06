import { convertClassName, convertClassNameList } from '@/utils';
import styles from './ProfileAsset.module.scss';
import { PieChart, Text } from '@/components/atoms';
import { useQuery } from '@tanstack/react-query';
import { Capital } from '@/types';
import { getUserCapitalQuery } from '@/api/queries/user';
import { useMemo } from 'react';

interface ProfileAssetProps {
  className?: string;
  userNo: number;
  rival?: number;
}

const ProfileAsset = ({
  className,
  userNo,
  rival,
}: ProfileAssetProps): JSX.Element => {
  const { data: capital } = useQuery<Capital, Error>(
    getUserCapitalQuery(userNo),
  );
  const { data: rivalCapital } = rival
    ? useQuery<Capital, Error>(getUserCapitalQuery(rival))
    : { data: undefined };

  const { capitalChart, revenueChart, rivalChart } = useMemo(() => {
    if (!capital)
      return {
        capitalChart: undefined,
        revenueChart: undefined,
        rivalChart: undefined,
      };
    const capitalChart = {
      labels: ['주식', '예금', '적금', '포인트'],
      data: [capital.stock, capital.deposit, capital.saving, capital.point],
      length: 4,
    };

    const revenueChart = {
      labels: ['주식', '예금', '적금', '경매', '퀴즈'],
      data: [
        capital.stockIncome,
        capital.depositIncome,
        capital.savingIncome,
        capital.auctionIncome,
        capital.quizIncome,
      ],
      length: 5,
    };

    let rivalChart = undefined;
    if (rivalCapital) {
      rivalChart = {
        labels: ['주식', '예금', '적금', '경매', '퀴즈'],
        data: [
          rivalCapital.stockIncome,
          rivalCapital.depositIncome,
          rivalCapital.savingIncome,
          rivalCapital.auctionIncome,
          rivalCapital.quizIncome,
        ],
        length: 5,
      };
    }
    return {
      capitalChart,
      revenueChart,
      rivalChart,
    };
  }, [capital, rivalCapital]);

  return (
    <div
      style={{
        width: '75%',
      }}
      className={convertClassNameList(
        convertClassName(className, styles),
        styles['profile-asset'],
      )}
    >
      <div className={styles['profile-asset__pie-chart']}>
        <Text className="text-lg center" text="내 자산" />
        <PieChart chartData={capitalChart} />
      </div>
      <div className={styles['profile-asset__pie-chart']}>
        <Text className="text-lg center" text="수익률" />
        <PieChart chartData={revenueChart} />
      </div>
      <div className={styles['profile-asset__pie-chart']}>
        <Text className="text-lg center" text="라이벌의 수익률" />
        <PieChart chartData={rivalChart} />
      </div>
    </div>
  );
};

export default ProfileAsset;
