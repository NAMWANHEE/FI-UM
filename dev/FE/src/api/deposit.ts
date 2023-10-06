import { Deposit, MyBankInfo } from '@/types';
import { authApi } from '.';

// 예금 정보 조회
const getBankDeposit = async (): Promise<Deposit[]> => {
  return await authApi.get(`bank/deposit`).then(({ data }) => data);
};
// 예금 입출금 하기
const postBankDeposit = async (
  bankName: string,
  money: number,
): Promise<string> => {
  return await authApi
    .post(`bank/deposit?option=${bankName}`, { money })
    .then(({ data }) => {
      alert(data?.msg);
      return data;
    })
    .catch((err: any) => alert(err.response.data.msg));
};

// 우대 조건 만족 확인
const getBankCheckPrime = async (bankName: string): Promise<string> => {
  return await authApi
    .get(`bank/checkPrime?option=${bankName}`)
    .then(({ data }) => data)
    .catch((error) => {
      console.error(error);
      return Promise.reject(error);
    });
};

// 적금 가입하기
const postBankSaving = async (
  bankName: string,
  money: number,
): Promise<string> => {
  return await authApi
    .post(`bank/saving?option=${bankName}`, { money })
    .then(({ data }) => {
      alert(data?.msg);
      return data;
    })
    .catch((err: any) => alert(err.response.data.msg));
};

// 내 은행 정보 조회
const getBankInfo = async (): Promise<MyBankInfo[]> => {
  return await authApi.get(`bank/bank-info`).then(({ data }) => data);
};

export {
  getBankDeposit,
  getBankInfo,
  postBankDeposit,
  getBankCheckPrime,
  postBankSaving,
};
