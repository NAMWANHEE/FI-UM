import { MyDeposit } from '@/types';

class DepositImpl implements MyDeposit {
  bankName: string;
  productType: '예금' | '적금' | '입출금';
  interestRate: number;
  primeInterestRate: number;
  savingBalance: number;
  constructor(bankName: string, productType: '예금' | '적금' | '입출금') {
    this.bankName = bankName;
    this.productType = productType;
    this.interestRate = this.primeInterestRate = this.savingBalance = 0;
  }
}

export { DepositImpl };
