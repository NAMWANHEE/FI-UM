import { render } from '@testing-library/react';
import '@testing-library/jest-dom';
import App from '@/App';

describe('App 컴포넌트', () => {
  test('App 컴포넌트 렌더링 테스트', () => {
    render(<App />);
  });
});
