import DepositProduct from './DepositProduct';
import { render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import '@testing-library/jest-dom';
import { MyBankInfo } from '@/types';

const depositList: MyBankInfo[] = [
  {
    bankName: '신한은행',
    productType: '입출금',
    interestRate: 1000,
    primeInterestRate: 1,
    savingBalance: 1,
    createSaving: 0,
    depositMoney: null,
  },
  {
    bankName: '신한은행',
    productType: '적금',
    interestRate: 2000,
    primeInterestRate: 1,
    savingBalance: 1,
    createSaving: 0,
    depositMoney: null,
  },
  {
    bankName: '신한은행',
    productType: '적금',
    interestRate: 3000,
    primeInterestRate: 1,
    savingBalance: 1,
    createSaving: 0,
    depositMoney: null,
  },
];

describe('DepositProduct 컴포넌트', () => {
  test('컴포넌트 렌더링 테스트', () => {
    render(
      <>
        {depositList.map((deposit) => (
          <DepositProduct deposit={deposit} onModal={() => {}} />
        ))}
      </>,
    );
    userEvent.setup();
    screen.debug();
  });
});
