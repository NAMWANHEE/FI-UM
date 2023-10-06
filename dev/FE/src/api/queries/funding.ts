import {
  getFundingProgress,
  getFundings,
  getMyFundings,
  postFunding,
  getFundingRecords,
} from '../funding';

// 진행 중인 펀딩 리스트 전체 조회
const getFundingsQuery = () => {
  return {
    queryKey: ['getFundings'],
    queryFn: getFundings,
  };
};

// 전체 펀딩 진행률(원장쌤)
const getFundingProgressQuery = () => {
  return {
    queryKey: ['getFundingProgress'],
    queryFn: getFundingProgress,
  };
};

// 내 펀딩 확인
const getMyFundingsQuery = () => {
  return {
    queryKey: ['getMyFundings'],
    queryFn: getMyFundings,
  };
};

// 전체 펀딩 현황 조회
const getFundingRecordsQuery = () => {
  return {
    queryKey: ['getFundingRecords'],
    queryFn: getFundingRecords,
  };
};

// 펀딩하기 버튼
const postFundingQuery = (itemNo: number, money: number) => {
  return {
    mutationFn: () => postFunding(itemNo, money),
  };
};

export {
  getFundingsQuery,
  getFundingProgressQuery,
  getMyFundingsQuery,
  postFundingQuery,
  getFundingRecordsQuery,
};
