import { NewReview, Review } from '@/types';
import { authApi } from '.';

// 후기 게시글 전체 조회
const getReviews = async () => {
  return await authApi.get(`reviews`).then(({ data }) => data);
};

// 후기 게시글 상세 조회
const getReview = async (reviewNo: number): Promise<Review> => {
  return await authApi.get(`reviews/${reviewNo}`).then(({ data }) => data);
};

// 후기 게시글 생성
const postReview = async (newReview: NewReview): Promise<Review> => {
  return await authApi.post(`reviews`, newReview).then(({ data }) => data);
};

// 후기 게시글 수정
const putReview = async ({
  reviewNo,
  ...newReview
}: {
  reviewNo: number;
  newReview: NewReview;
}): Promise<Review> => {
  return await authApi
    .put(`reviews/${reviewNo}`, newReview)
    .then(({ data }) => data);
};

// 후기 게시글 삭제
const deleteReview = async (reviewNo: number): Promise<string> => {
  return await authApi.delete(`reviews/${reviewNo}`).then(({ data }) => data);
};

export { getReviews, getReview, postReview, putReview, deleteReview };
