import SignUpPage from './SignUpPage';
import { render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import '@testing-library/jest-dom';

describe('SignUpPage 컴포넌트', () => {
  test('컴포넌트 렌더링 테스트', () => {
    render(<SignUpPage />);
    userEvent.setup();
    screen.debug();
  });
});
