// BankLogo.test.tsx

import { render } from '@testing-library/react';
import '@testing-library/jest-dom';
import BankLogo from './BankLogo';

describe('BankLogo 컴포넌트', () => {
  test('BankLogo 컴포넌트 렌더링 테스트', () => {
    render(<BankLogo className="shinhan" />);
  });
});
