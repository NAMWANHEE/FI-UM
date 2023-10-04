interface Stock {
  searchNo: number;
  stockNo: number;
  stockName: string;
  nowPrice: number;
  fluctuationPrice: number;
}

interface MyStock {
  stockNo: number;
  stockName: string;
  stockCount: number;
  stockUnitPrice: number;
  stockNowPrice: number;
}

interface StockDetail {
  stockName: string;
  stockCount: number;
  stockAverage: number;
  nowPrice: number;
}

interface StockRanking {
  userName: string | null;
  imagePath: string | null;
  stockList: MyStock[] | null;
}

interface StockMyAccount {
  point: number;
  stockCount: number;
  stockAverage: number;
}

interface StockAccount {
  stockNo: number;
  price: number;
  count: number;
}

interface News {
  newsNo: number;
  newsTitle: string;
  newsContent: string;
  searchNo: number;
  stockNo: number;
  stockName: string;
}

interface TradeHistory {
  stockCount: number;
  tradeTime: number;
}

interface PortfolioKeys {
  [key: string]: any;
  '꿈꾸는 고양이 카페 주식회사': number;
  '토이케라톱스 공룡장난감 주식회사': number;
  '마법의 쿠키 과자 주식회사': number;
  '스피드킹 자동차 주식회사': number;
  '신비한 해적선 모험 주식회사': number;
}

interface Portfoilo {
  moneyBalance: PortfolioKeys;
  ratioBalance: PortfolioKeys;
}

export type {
  Stock,
  StockRanking,
  MyStock,
  StockMyAccount,
  StockDetail,
  StockAccount,
  News,
  TradeHistory,
  Portfoilo,
};
