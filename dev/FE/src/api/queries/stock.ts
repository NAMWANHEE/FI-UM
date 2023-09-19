import { StockAccount } from '@/types';
import {
  getMyStocks,
  getStocks,
  getStockKing,
  getStockChart,
  getStockMyAccount,
  postStockBuying,
  postStockSelling,
} from '../stock';

// 내 주식 확인
const getMyStocksQuery = () => {
  return { queryKey: ['getMyStocks'], queryFn: getMyStocks };
};

// 주식 종목 전체 조회 (권한 x)
const getStocksQuery = () => {
  return { queryKey: ['getStocks'], queryFn: getStocks };
};

// 주식왕 포트폴리오 조회 (권한 x)
const getStockKingQuery = () => {
  return { queryKey: ['getStockKing'], queryFn: getStockKing };
};

// 주식 차트 조회 (권한 x)
const getStockChartQuery = (stockNo: number) => {
  return { queryKey: ['getStockChart', stockNo], queryFn: getStockChart };
};

// 내 특정 주식 확인
const getStockMyAccountQuery = (stockNo: number) => {
  return {
    queryKey: ['getStockMyAccount', stockNo],
    queryFn: getStockMyAccount,
  };
};

// 주식 구매
const postStockBuyingQuery = (stockAccount: StockAccount) => {
  return { mutationFn: () => postStockBuying(stockAccount) };
};

// 주식 판매
const postStockSellingQuery = (stockAccount: StockAccount) => {
  return { mutationFn: () => postStockSelling(stockAccount) };
};

export {
  getMyStocksQuery,
  getStocksQuery,
  getStockKingQuery,
  getStockChartQuery,
  getStockMyAccountQuery,
  postStockBuyingQuery,
  postStockSellingQuery,
};
