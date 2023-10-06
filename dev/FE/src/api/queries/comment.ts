import { Comment } from '@/types';
import { getComments } from '../comment';

// 후기 댓글 불러오기
const getCommentsQuery = (reviewNo: number) => {
  return {
    queryKey: ['getComments'],
    queryFn: () => getComments(reviewNo),
    select: (data: Comment[]) =>
      data.sort((a, b) => b.createTime - a.createTime),
  };
};
// 후기 댓글 달기
// 후기 댓글 수정
// 후기 댓글 삭제

export { getCommentsQuery };
