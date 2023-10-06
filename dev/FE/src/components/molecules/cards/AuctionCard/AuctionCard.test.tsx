// AucionCard.test.tsx

import AuctionCard from './AuctionCard';
import { render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import '@testing-library/jest-dom';

describe('AuctionCard 컴포넌트', () => {
  test('컴포넌트 렌더링 테스트', () => {
    render(<AuctionCard itemImagePath="" title="" />);
    userEvent.setup();
    screen.debug();
  });
});
