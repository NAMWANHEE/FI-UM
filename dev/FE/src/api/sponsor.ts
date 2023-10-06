import { Item, NewItem, SponsorshipDetail } from '@/types';
import { api, authApi } from '.';

// 후원 전체 목록 조회 (권한 x)
const getSponsorShip = async (): Promise<Item[]> => {
  return await api.get(`sponsorship`).then(({ data }) => data);
};

// 후원 내역 조회
const getSponsorShipRecordDetail = async (): Promise<SponsorshipDetail[]> => {
  return await authApi
    .get(`sponsorship/record/detail`)
    .then(({ data }) => data);
};

// 전체 후원 내역 조회
const getSponsorShipRecords = async (): Promise<SponsorshipDetail[]> => {
  return await authApi.get(`sponsorship/record`).then(({ data }) => data);
};

// 후원하기
const postSponsorshipSupport = async (
  itemNo: number,
  money: number,
): Promise<number> => {
  return await authApi
    .post(`sponsorship/support/${itemNo}`, { money: money })
    .then(({ data }) => data);
};

// 후원 물품 등록하기
const postSponsorship = async ({
  itemNo,
  ...newItem
}: NewItem): Promise<string> => {
  return await authApi.post(`sponsorship`, newItem).then(({ data }) => data);
};

// 등록된 후원 물품 수정
const putSponsorship = async ({
  itemNo,
  ...newItem
}: NewItem): Promise<string> => {
  return await authApi
    .put(`sponsorship/${itemNo}`, newItem)
    .then(({ data }) => data);
};

export {
  getSponsorShip,
  getSponsorShipRecordDetail,
  getSponsorShipRecords,
  postSponsorshipSupport,
  postSponsorship,
  putSponsorship,
};
