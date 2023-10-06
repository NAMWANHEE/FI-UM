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
    const { getByText } = render(
      <Button label="확인" className="primary normal" />,
    );
    const button = getByText('확인');

    expect(button).toHaveClass(styles.normal);
  });
});
