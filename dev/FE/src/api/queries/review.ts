import { getReview, getReviews } from '../review';
// 후기 게시글 전체 조회
const getReviewsQuery = () => {
  return {
    queryKey: ['getReviews'],
    queryFn: getReviews,
  };
};
// 후기 게시글 상세 조회
const getReviewQuery = (reviewNo: number) => {
  return {
    queryKey: ['getReview'],
    queryFn: () => getReview(reviewNo),
  };
};
// 후기 게시글 생성
// 후기 게시글 수정
// 후기 게시글 삭제

export { getReviewsQuery, getReviewQuery };
