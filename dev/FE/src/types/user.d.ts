import { PointRecord, Stock, StockDetail } from '.';

interface User {
  userId: string;
  password: string;
}

interface UserDetail extends User {
  userId: string;
  password2: string;
  userName: string;
  phoneNumber: string;
}

export type UserKeyType = keyof UserDetail;

interface TotalCapital {
  userNo: number;
  userName: string;
  point: number;
  stockMoney: number;
  depositMoney: number;
  fundingMoney: number;
  solvingRate: string;
  pointRecord: PointRecord[];
  stockList: StockDetail[];
}

interface Capital {
  userNo: number;
  userName: string;
  point: number;
  stock: number;
  stockIncome: number;
  deposit: number;
  depositIncome: number;
  saving: number;
  savingIncome: number;
  auctionIncome: number;
  quizIncome: number;
  donation: number;
}

interface UserInfo {
  userNo: number;
  userName: string;
  userId: string;
  userType: UserType;
  joinDate: number;
  imagePath: string;
  phoneNumber: string;
  point: number;
  isPrimed1: boolean;
  isPrimed2: boolean;
  rival?: number;
  tokenResponse?: { accessToken: string; refreshToken: string };
  quiz: boolean;
}

interface SponsorProfile {
  userNo: number;
  userName: string;
  sponsoredAmount: number;
  point: number;
  cash: number;
}

interface ChildProfile {
  userNo: number;
  userName: string;
  imagePath: string;
}

interface Art {
  auctionNo: number;
  imagePath: string;
  title: string;
  winner: string | null;
}

type UserType = 0 | 1 | 2 | 3;

export type {
  User,
  UserDetail,
  TotalCapital,
  UserInfo,
  UserType,
  Capital,
  SponsorProfile,
  ChildProfile,
  Art,
};
