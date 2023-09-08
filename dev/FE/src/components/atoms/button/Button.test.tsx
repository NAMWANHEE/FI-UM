import { render } from '@testing-library/react';
import '@testing-library/jest-dom';
import styles from './Button.module.scss';
import Button from './Button';

describe('Button 컴포넌트', () => {
  test('Button 컴포넌트 렌더링 테스트', () => {
    const { getByText } = render(<Button label="확인" />);
    const button = getByText('확인');
    expect(button).toBeInTheDocument();
  });

  test('Button className이 제대로 전달되는지 확인', () => {
    const { getByText } = render(<Button label="확인" />);
    const button = getByText('확인');

    // styles 객체에서 `normal` 클래스를 가져와서 비교합니다.
    expect(button).toHaveClass(styles.normal);
  });

  test('size prop을 전달하지 않았을 때, normal 크기로 스타일이 적용되는지 확인', () => {
    const { getByText } = render(<Button label="확인" />);
    const button = getByText('확인');

    // normal 사이즈의 스타일을 검증합니다.
    expect(button).toHaveStyle(`
      width: 240px;
      height: 60px;
      font-size: 1.5rem;
    `);
  });
});
