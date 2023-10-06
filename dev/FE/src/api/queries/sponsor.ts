import {
  getSponsorShip,
  getSponsorShipRecordDetail,
  getSponsorShipRecords,
  postSponsorshipSupport,
  postSponsorship,
  putSponsorship,
} from '@/api/sponsor';

// 후원 전체 목록 조회 (권한 x)
const getSponsorShipQuery = () => {
  return {
    queryKey: ['getSponsorShip'],
    queryFn: getSponsorShip,
  };
};

// 후원 내역 조회
const getSponsorShipRecordDetailQuery = () => {
  return {
    queryKey: ['getSponsorShipRecordDetail'],
    queryFn: getSponsorShipRecordDetail,
  };
};

// 전체 후원 내역 조회
const getSponsorShipRecordsQuery = () => {
  return {
    queryKey: ['getSponsorShipRecords'],
    queryFn: getSponsorShipRecords,
  };
};

// 후원하기
const postSponsorshipSupportQuery = () => {
  return {
    mutationFn: postSponsorshipSupport,
  };
};

// 후원 물품 등록하기
const postSponsorshipQuery = () => {
  return {
    mutationFn: postSponsorship,
  };
};

// 등록된 후원 물품 수정
const putSponsorshipQuery = () => {
  return {
    mutationFn: putSponsorship,
  };
};

export {
  getSponsorShipQuery,
  getSponsorShipRecordDetailQuery,
  getSponsorShipRecordsQuery,
  postSponsorshipSupportQuery,
  postSponsorshipQuery,
  putSponsorshipQuery,
};
