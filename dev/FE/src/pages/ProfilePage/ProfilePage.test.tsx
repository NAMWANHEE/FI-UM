import ProfilePage from './ProfilePage';
import { render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event'
import '@testing-library/jest-dom';
  
describe('ProfilePage 컴포넌트', () => {
  test('컴포넌트 렌더링 테스트', () => {
    render(<ProfilePage />)
    userEvent.setup();
    screen.debug();
  });
});