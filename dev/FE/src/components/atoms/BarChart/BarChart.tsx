import styles from './BarChart.module.scss';
import { convertClassName, convertClassNameList } from '@/utils';

interface BarChartProps {
  className?: string;
  ratio: number;
}

const BarChart = ({ className, ratio }: BarChartProps): JSX.Element => {
  const width = `${ratio}%`;

  return (
    <div
      className={convertClassNameList(
        styles['bar-chart'],
        convertClassName(className, styles),
      )}
      data-testid="bar-chart"
    >
      <div
        className={convertClassNameList(styles['bar-chart-fill'])}
        style={{ width }}
      ></div>
      <div className={convertClassNameList(styles['bar-chart-base'])}></div>
    </div>
  );
};

export default BarChart;
