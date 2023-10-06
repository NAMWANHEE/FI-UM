import { Auction, NewAuction, Purchase } from '@/types';
import { api, authApi } from '.';
import { QueryKey } from '@tanstack/react-query';

// 경매 물품 전체 조회
const getAuctions = async (): Promise<Auction[]> => {
  return await api.get('auction').then(({ data }) => data);
};

// 경매 물품 상세 조회
const getAuctionDetailByAuctionNo = async ({
  queryKey: [_, auctionNo],
}: {
  queryKey: QueryKey;
}): Promise<Auction> => {
  return await api.get(`auction/detail/${auctionNo}`).then(({ data }) => data);
};

// 구매한 그림 내역 조회
const getAuctionPurchase = async (): Promise<Purchase[]> => {
  return await authApi.get(`auction/purchase`).then(({ data }) => data);
};

// 경매 물품 등록
const postAuction = async (auction: NewAuction): Promise<string> => {
  return await authApi.post(`auction`, auction);
};

// 경매 입찰 / 즉시구매
const postAuctionBid = async ({
  auctionNo,
  auctionPrice,
}: {
  auctionNo: number;
  auctionPrice: number;
}): Promise<string> => {
  return await authApi
    .post(`auction/bid/${auctionNo}`, { auctionPrice })
    .then(({ data }) => data.msg);
};

// 경매 물품 내용 수정
const putAuction = async ({
  auctionNo,
  title,
  content,
}: {
  auctionNo: string;
  title: string;
  content: string;
}): Promise<string> => {
  return await authApi.put(`auction/${auctionNo}`, { title, content });
};

export {
  getAuctions,
  getAuctionDetailByAuctionNo,
  getAuctionPurchase,
  postAuction,
  postAuctionBid,
  putAuction,
};
