import Modal from './Modal';
import { render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import '@testing-library/jest-dom';

describe('Modal 컴포넌트', () => {
  test.skip('컴포넌트 렌더링 테스트', () => {
    render(
      <Modal isOpen toggle={() => {}}>
        <div></div>
      </Modal>,
    );
    userEvent.setup();
    screen.debug();
  });
});
