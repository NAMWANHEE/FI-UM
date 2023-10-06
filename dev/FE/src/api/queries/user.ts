import { ArtistAuction } from '@/types';
import {
  getUserArtist,
  getUser,
  getUserTotalCapital,
  getUserCapital,
  userLogout,
  getUserDepositSaving,
  postUserRival,
  // getUserCheckId,
  // userSignup,
  // userLogin,
} from '../user';

const getUserArtistQuery = (userNo: number) => {
  return {
    queryKey: ['getArtist', userNo],
    queryFn: getUserArtist,
    select: (data: ArtistAuction[]) =>
      data.sort((a, b) => {
        if (a.winner && b.winner) {
          return a.auctionNo - b.auctionNo;
        } else {
          return b.winner ? -1 : 1;
        }
      }),
  };
};

const getUserTotalCapitalQuery = () => {
  return {
    queryKey: ['getTotalCapital'],
    queryFn: getUserTotalCapital,
  };
};

const getUserQuery = () => {
  return {
    queryKey: ['getUser'],
    queryFn: getUser,
  };
};

// 특정 아이의 재무 상태표
const getUserCapitalQuery = (userNo: number) => {
  return {
    queryKey: ['getUserCapital', userNo],
    queryFn: getUserCapital,
  };
};

// 자신의 현재 예적금 조회
const getUserDepositSavingQuery = () => {
  return {
    queryKey: ['getUserDepositSaving'],
    queryFn: getUserDepositSaving,
  };
};

// logout
const userLogoutQuery = () => {
  return {
    mutationFn: userLogout,
  };
};

// 라이벌 등록
const postUserRivalQuery = (userNo: number) => {
  return {
    mutaionFn: () => postUserRival(userNo),
  };
};

export {
  getUserArtistQuery,
  getUserQuery,
  getUserTotalCapitalQuery,
  getUserCapitalQuery,
  userLogoutQuery,
  getUserDepositSavingQuery,
  postUserRivalQuery,
  // getUserCheckIdQuery,
  // userSignupQuery,
  // userLoginQuery,
};
