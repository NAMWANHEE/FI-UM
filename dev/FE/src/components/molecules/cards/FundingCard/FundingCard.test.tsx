import FundingCard from './FundingCard';
import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom';
import { Funding } from '@/types';

const funding: Funding = {
  itemNo: 'string',
  imagePath: '0',
  itemName: '0',
  itemUnitPrice: 0,
  itemCount: 0,
  fundingAmount: 0,
  description: 'string',
  unitPrice: 0,
  sponsorshipAmount: 0,
  fundingRanking: [],
  isCompleted: false,
};

const renderFundingCard = () => {
  render(<FundingCard funding={funding} />);

  const fundingCard = screen.getByTestId('funding-card');
  return { fundingCard };
};

describe('Funding Card 컴포넌트', () => {
  test('컴포넌트 렌더링 테스트', () => {
    renderFundingCard();
  });

  test('컴포넌트 내부에 Image, Text, BarChart 유무 확인', () => {
    renderFundingCard();

    screen.getByTestId('bar-chart');
    screen.getByTestId('text');
    screen.getByTestId('image');
  });
});
