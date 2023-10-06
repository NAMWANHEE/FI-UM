import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom';
import { Image } from '@/components/atoms';

const imgSrc = process.env.PUBLIC_URL + '/vite.svg';

const renderImage = (className: string = '') => {
  render(<Image className={className} src={imgSrc} alt="" />);
  const img = screen.getByRole('img');
  return { img };
};

describe('Image 컴포넌트', () => {
  test('렌더링 테스트', () => {
    renderImage('img primary');
  });

  test('Props로 전달된 className이 잘 반영 되는지 테스트', () => {
    const { img } = renderImage('img primary');

    expect(img).toHaveClass('img primary');
    expect(img).not.toHaveClass('img!!!!!');
  });
});
