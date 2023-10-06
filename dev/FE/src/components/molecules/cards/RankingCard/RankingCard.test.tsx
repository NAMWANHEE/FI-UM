// RankingCard.test.tsx

import RankingCard from './RankingCard';
import { render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import '@testing-library/jest-dom';
import { FundingRanking } from '@/types';

const fundingRanking: FundingRanking[] = [
  {
    userName: '',
    userFundingAmount: 0,
  },
];

describe('RankingCard 컴포넌트', () => {
  test('컴포넌트 렌더링 테스트', () => {
    render(<RankingCard fundingRanking={fundingRanking} />);
    userEvent.setup();
    screen.debug();
  });
});
