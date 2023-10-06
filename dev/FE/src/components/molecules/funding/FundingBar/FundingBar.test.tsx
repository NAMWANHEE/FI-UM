import FundingBar from './FundingBar';
import { render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import '@testing-library/jest-dom';

describe('FundingBar 컴포넌트', () => {
  test('컴포넌트 렌더링 테스트', () => {
    render(
      <FundingBar
        ratio={40}
        itemUnitPrice={0}
        fundingAmount={0}
        itemCount={0}
      />,
    );
    userEvent.setup();
    screen.debug();
  });
});
