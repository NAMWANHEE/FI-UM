import { MyBankInfo } from '@/types';
import {
  getBankCheckPrime,
  getBankDeposit,
  postBankDeposit,
  postBankSaving,
  getBankInfo,
} from '../deposit';

// 예금 정보 조회
const getBankDepositQuery = () => {
  return {
    queryKey: ['getBankDeposit'],
    queryFn: getBankDeposit,
  };
};
// 예금 입출금 하기
const postBankDepositQuery = (bankName: string, money: number) => {
  return {
    mutationFn: () => postBankDeposit(bankName, money),
  };
};
// 우대 조건 만족 확인
const getBankCheckPrimeQuery = (bankName: string) => {
  return {
    queryKey: ['getBankCheckPrime', bankName],
    queryFn: () => getBankCheckPrime(bankName),
  };
};

// 적금 가입하기
const postBankSavingQuery = (bankName: string, money: number) => {
  return {
    mutationFn: () => postBankSaving(bankName, money),
  };
};

// 은행 정보 조회
const getBankInfoQuery = () => {
  return {
    queryKey: ['getBankInfo'],
    queryFn: getBankInfo,
    select: (data: MyBankInfo[]) =>
      data.sort((a, b) => (a.productType < b.productType ? -1 : 1)),
  };
};

export {
  getBankDepositQuery,
  postBankDepositQuery,
  postBankSavingQuery,
  getBankCheckPrimeQuery,
  getBankInfoQuery,
};
