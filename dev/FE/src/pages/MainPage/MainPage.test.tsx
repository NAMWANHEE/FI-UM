import MainPage from './MainPage';
import { render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import '@testing-library/jest-dom';

describe('MainPage 컴포넌트', () => {
  test('컴포넌트 렌더링 테스트', () => {
    render(<MainPage />);
    userEvent.setup();
    screen.debug();
  });
});
