import GalleryPage from './GalleryPage';
import { render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event'
import '@testing-library/jest-dom';
  
describe('GalleryPage 컴포넌트', () => {
  test('컴포넌트 렌더링 테스트', () => {
    render(<GalleryPage />)
    userEvent.setup();
    screen.debug();
  });
});