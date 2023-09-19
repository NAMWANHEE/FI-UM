import {
  MyStock,
  Stock,
  StockAccount,
  StockMyAccount,
  StockRanking,
} from '@/types';
import { api, authApi } from '.';

// 내 주식 확인
const getMyStocks = async (): Promise<MyStock[]> => {
  return await authApi.get(`stock/my-stock`).then(({ data }) => data);
};

// 주식 종목 전체 조회 (권한 x)
const getStocks = async (): Promise<Stock[]> => {
  return await api.get(`stock`).then(({ data }) => data);
};

// 주식왕 포트폴리오 조회 (권한 x)
const getStockKing = async (): Promise<StockRanking[]> => {
  return await api.get(`stock/king`).then(({ data }) => data);
};

// 주식 차트 조회 (권한 x)
const getStockChart = async ({
  queryKey: [_, stockNo],
}: any): Promise<Stock[]> => {
  return await api.get(`stock/chart/${stockNo}`).then(({ data }) => data);
};

// 내 특정 주식 확인
const getStockMyAccount = async ({
  queryKey: [_, stockNo],
}: any): Promise<StockMyAccount> => {
  return await authApi
    .get(`stock/my-account/${stockNo}`)
    .then(({ data }) => data);
};

// 주식 구매
const postStockBuying = async (stockAccount: StockAccount): Promise<string> => {
  return await authApi.post(`stock/buying`, stockAccount);
};

// 주식 판매
const postStockSelling = async (
  stockAccount: StockAccount,
): Promise<string> => {
  return await authApi.post(`stock/selling`, stockAccount);
};

export {
  getMyStocks,
  getStocks,
  getStockKing,
  getStockChart,
  getStockMyAccount,
  postStockBuying,
  postStockSelling,
};
