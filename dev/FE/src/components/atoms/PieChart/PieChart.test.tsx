import { render, screen } from '@testing-library/react';
import PieChart from './PieChart';

describe('PieChart 컴포넌트', () => {
  test.skip('PieChart 컴포넌트 렌더링 테스트', () => {
    render(<PieChart />);
    screen.getByTestId('pie');
  });
});
