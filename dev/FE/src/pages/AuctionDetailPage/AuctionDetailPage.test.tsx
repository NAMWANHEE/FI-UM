import AuctionDetailPage from './AuctionDetailPage';
import { render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import '@testing-library/jest-dom';

describe('AuctionDetailpage 컴포넌트', () => {
  test.skip('컴포넌트 렌더링 테스트', () => {
    render(<AuctionDetailPage />);
    userEvent.setup();
    screen.debug();
  });
});
