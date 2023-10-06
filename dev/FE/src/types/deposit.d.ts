interface MyDeposit {
  bankName: string;
  productType: '입출금' | '예금' | '적금';
  interestRate: number;
  primeInterestRate: number;
  savingBalance: number | null;
  description?: string;
}

interface Deposit {
  bankNo: number;
  bankName: '신한은행' | '국민은행' | '하나은행';
  depositBalance: number;
  createDeposit: number;
  depositMoney: number;
}

interface MyBankInfo extends MyDeposit {
  depositMoney: number | null;
  createSaving: number;
}

export type { MyDeposit, Deposit, Bank, MyBankInfo };
