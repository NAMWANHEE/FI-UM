import DepositProduct from './DepositProduct';
import { render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import '@testing-library/jest-dom';

describe('DepositProduct 컴포넌트', () => {
  test('컴포넌트 렌더링 테스트', () => {
    render(
      <>
        <DepositProduct
          status={1}
          title="신한 입출금"
          bankLogoClassName="shinhan normal"
        />
        <DepositProduct
          status={2}
          title="국민 예금"
          bankLogoClassName="kb normal"
        />
        <DepositProduct
          status={3}
          title="하나 적금"
          bankLogoClassName="hana normal"
        />
      </>,
    );
    userEvent.setup();
    screen.debug();
  });
});
