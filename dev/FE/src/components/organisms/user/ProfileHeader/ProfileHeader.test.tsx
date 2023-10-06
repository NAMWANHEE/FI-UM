import ProfileHeader from './ProfileHeader';
import { render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import '@testing-library/jest-dom';

describe('ProfileHeader 컴포넌트', () => {
  test('컴포넌트 렌더링 테스트', () => {
    render(<ProfileHeader myPage />);
    userEvent.setup();
    screen.debug();
  });
});
