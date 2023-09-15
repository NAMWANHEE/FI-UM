import { UserDetail } from '@/types';
import { QueryKey } from '@tanstack/react-query';
import { api, authApi } from '.';

// 아이디 중복 확인
const getUserCheckId = async ({
  queryKey: [_, userId],
}: {
  queryKey: QueryKey;
}): Promise<string> => {
  return await api.get(`user/check-id?userId=${userId}`);
};

// 예술가 작품 전체 조회
const getUserArtist = async ({
  queryKey: [_, userNo],
}: {
  queryKey: QueryKey;
}) => {
  return await authApi.get(`user/artist/${userNo}`);
};

// 개인정보 조회
const getUser = async () => {
  return await authApi.get(`user`);
};

const getUserTotalCapital = async () => {
  return await authApi.get(`user/total-capital`);
};

const userSignup = async (userDetail: UserDetail) => {
  return await api.post(`user/signup`, userDetail);
};

export {
  getUserCheckId,
  getUserArtist,
  getUser,
  getUserTotalCapital,
  userSignup,
};
