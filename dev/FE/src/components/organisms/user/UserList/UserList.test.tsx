import UserList from './UserList';
import { render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import '@testing-library/jest-dom';

describe('UserList 컴포넌트', () => {
  test.skip('컴포넌트 렌더링 테스트', () => {
    render(<UserList onClick={() => {}} totalCapitals={[]} />);
    userEvent.setup();
    screen.debug();
  });
});
