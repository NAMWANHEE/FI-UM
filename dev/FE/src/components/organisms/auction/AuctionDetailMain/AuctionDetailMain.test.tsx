import AuctionDetailMain from './AuctionDetailMain';
import { render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import '@testing-library/jest-dom';

describe('AuctionDetailMain 컴포넌트', () => {
  test('컴포넌트 렌더링 테스트', () => {
    render(
      <AuctionDetailMain
        createdTime={0}
        title="확인"
        src=""
        alt=""
        auctionClick={() => {}}
        buyItClick={() => {}}
        auctionPrice={123123}
        instantPrice={2359820}
      />,
    );
    userEvent.setup();
    screen.debug();
  });
});
