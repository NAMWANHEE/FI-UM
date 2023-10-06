import { render, screen } from '@testing-library/react';
import Table from './Table';

describe('Text', () => {
  it('Text Render', () => {
    render(<Table />);
    screen.debug();
  });
});
