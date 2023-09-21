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
  userName: string;
  imagePath: string;
  stockList: MyStock[];
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

export type {
  Stock,
  StockRanking,
  MyStock,
  StockMyAccount,
  StockDetail,
  StockAccount,
  News,
  TradeHistory,
};
