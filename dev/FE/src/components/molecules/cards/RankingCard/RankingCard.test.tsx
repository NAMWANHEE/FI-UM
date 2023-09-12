// RankingCard.test.tsx

import RankingCard from './RankingCard';
import { render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import '@testing-library/jest-dom';

describe('RankingCard 컴포넌트', () => {
  test('컴포넌트 렌더링 테스트', () => {
    render(<RankingCard no1="남완희" no2="김영우" no3="신기정" />);
    userEvent.setup();
    screen.debug();
  });
});
