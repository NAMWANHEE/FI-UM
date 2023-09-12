import AuctionDetailMain from './AuctionDetailMain';
import { render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import '@testing-library/jest-dom';

describe('AuctionDetailMain 컴포넌트', () => {
  test('컴포넌트 렌더링 테스트', () => {
    render(
      <AuctionDetailMain
        title="확인"
        src=""
        alt=""
        auctionClick={() => {
          console.log('경매하기');
        }}
        buyItClick={() => {
          console.log('즉시구매');
        }}
        auctionPrice="123123"
        instantPrice="2359820"
      />,
    );
    userEvent.setup();
    screen.debug();
  });
});
