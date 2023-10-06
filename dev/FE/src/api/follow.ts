import { ChildProfile } from '@/types';
import { authApi } from '.';

// 팔로우 전체 조회
const getFollowing = async (): Promise<ChildProfile[]> => {
  return await authApi.get(`following`).then(({ data }) => data);
};

// 팔로우 등록 & 취소
const postFollowing = async (userNo: number): Promise<string> => {
  return await authApi
    .post(`following`, { userNo })
    .then(({ data }) => data.msg);
};

export { getFollowing, postFollowing };
