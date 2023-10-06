import {
  MyStock,
  News,
  Portfoilo,
  Stock,
  StockAccount,
  StockMyAccount,
  StockRanking,
  TradeHistory,
} from '@/types';
import { api, authApi, portfolioApi } from '.';

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
    .then((res) => {
      return res.data;
    })
    .catch(() => {});
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

// 최신 뉴스 전체 조회
const getRecentNews = async (): Promise<News[]> => {
  return await api.get('stock/news').then(({ data }) => data);
};

// 세부 종목별 뉴스 조회
const getStockNews = async ({
  queryKey: [_, stockNo],
}: any): Promise<News[]> => {
  return await api.get(`stock/news/${stockNo}`).then(({ data }) => data);
};

// 주식 최근 거래 내역
const getTradeHistory = async ({
  queryKey: [_, stockNo],
}: any): Promise<TradeHistory[]> => {
  return await api.get(`stock/trade/${stockNo}`).then(({ data }) => data);
};

// 주식 포토폴리오 조회
const getPortfolio = async ({
  queryKey: [_, userNo],
}: any): Promise<Portfoilo> => {
  return await portfolioApi.get(`portfolio/${userNo}`).then(({ data }) => data);
};
export {
  getMyStocks,
  getStocks,
  getStockKing,
  getStockChart,
  getStockMyAccount,
  postStockBuying,
  postStockSelling,
  getRecentNews,
  getStockNews,
  getTradeHistory,
  getPortfolio,
};
