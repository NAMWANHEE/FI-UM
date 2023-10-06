import ModalFunding from './ModalFunding';
import { render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import '@testing-library/jest-dom';

describe('ModalFunding 컴포넌트', () => {
  test('컴포넌트 렌더링 테스트', () => {
    render(
      <ModalFunding
        onClick={() => {}}
        closeToggle={() => {}}
        item={{
          fundingRanking: [],
          isCompleted: false,
          itemNo: 'string',
          itemName: 'string',
          imagePath: 'string',
          itemUnitPrice: 1,
          itemCount: 1,
          fundingAmount: 1,
          description: 'string',
          unitPrice: 1,
          sponsorshipAmount: 1,
        }}
      />,
    );
    userEvent.setup();
    screen.debug();
  });
});
