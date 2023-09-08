// logo.test.tsx

import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom';
import Logo from './Logo';

const renderImage = (className: string = '') => {
  render(<Logo src="/vite.svg" alt="확인" className={className} />);
  const img = screen.getByRole('img');
  return { img };
};

describe('Logo 컴포넌트', () => {
  test('렌더링 테스트', () => {
    renderImage('img primary');
  });

  test('Props로 전달된 className이 잘 반영 되는지 테스트', () => {
    const { img } = renderImage('img primary');

    expect(img).toHaveClass('img primary');
    expect(img).not.toHaveClass('img!!!!!');
  });
});
