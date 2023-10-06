import { Doughnut } from 'react-chartjs-2';
import { ChartData } from 'chart.js';
import { Chart as ChartJS, ArcElement, Tooltip, Legend } from 'chart.js';
import { getLightColors } from '@/utils';
import { convertClassName, convertClassNameList } from '@/utils';
import styles from './PieChart.module.scss';
import { useMemo } from 'react';
ChartJS.register(ArcElement, Tooltip, Legend);

interface PieChartProps {
  className?: string;
  chartData?: { labels: string[]; data: number[]; length: number };
}

const options: any = {
  responsive: true,
  plugins: {
    legend: {
      display: false,
    },
    title: {
      display: true,
    },
  },
};

const PieChart = ({ className, chartData }: PieChartProps): JSX.Element => {
  const data: ChartData<'doughnut', number[], string> | undefined =
    useMemo(() => {
      if (!chartData) return undefined;
      const colors = getLightColors(chartData.length);
      return {
        labels: chartData.labels,
        datasets: [
          {
            data: chartData.data,
            backgroundColor: colors,
            borderColor: colors.map(() => '#ececec'),
            borderWidth: 0.5,
            rotation: Math.random() * 360,
          },
        ],
      };
    }, [chartData]);

  return (
    <div
      className={convertClassNameList(
        convertClassName(className, styles),
        styles['pie-chart'],
      )}
    >
      {!data || chartData?.data?.every((elm) => elm === 0) ? (
        <div className={styles['pie-chart__no-data']}>데이터 없음</div>
      ) : (
        <Doughnut data={data} options={options} data-testid="pie" />
      )}
    </div>
  );
};

export default PieChart;
