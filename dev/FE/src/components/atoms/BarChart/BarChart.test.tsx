import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom';
import { BarChart } from '..';

const renderBarChart = (ratio: number = 50) => {
  render(<BarChart ratio={ratio} />);
  const barChartContainer = screen.getByTestId('bar-chart');
  return {
    barChartContainer,
  };
};

describe('BarChart 컴포넌트', () => {
  test('렌더링 테스트', () => {
    renderBarChart();
  });
});
