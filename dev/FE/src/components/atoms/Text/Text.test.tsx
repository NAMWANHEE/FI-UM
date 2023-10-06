import { render, screen } from '@testing-library/react';
import Text from './Text';

describe('Text', () => {
  it('Text Render', () => {
    render(<Text text="Text" className="blue text-lg" />);
    screen.debug();
  });
});
