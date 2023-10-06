import Swiper from './Swiper';
import { render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event'
import '@testing-library/jest-dom';
  
describe('Swiper 컴포넌트', () => {
  test('컴포넌트 렌더링 테스트', () => {
    render(<Swiper />)
    userEvent.setup();
    screen.debug();
  });
});