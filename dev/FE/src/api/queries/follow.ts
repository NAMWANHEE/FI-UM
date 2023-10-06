import { getFollowing, postFollowing } from '@/api/follow';

// 팔로우 전체 조회
const getFollowingQuery = () => {
  return {
    queryKey: ['getFollowing'],
    queryFn: getFollowing,
  };
};

// 팔로우 등록 & 취소
const postFollowingQuery = (userNo: number) => {
  return {
    mutationFn: () => postFollowing(userNo),
  };
};

export { getFollowingQuery, postFollowingQuery };
