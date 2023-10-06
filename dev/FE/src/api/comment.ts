import { Comment } from '@/types';
import { api, authApi } from '.';

// 후기 댓글 불러오기
const getComments = async (reviewNo: number): Promise<Comment[]> => {
  return await api.get(`comments/${reviewNo}`).then(({ data }) => data);
};

// 후기 댓글 달기
const postComment = async (
  reniewNo: number,
  comment: string,
): Promise<string> => {
  return await authApi
    .post(`comments/${reniewNo}`, { comment })
    .then(({ data }) => data.msg);
};

// 후기 댓글 수정
const putComment = async (
  reniewNo: number,
  comment: string,
): Promise<string> => {
  return await authApi
    .put(`comments/${reniewNo}`, { comment })
    .then(({ data }) => data.msg);
};

// 후기 댓글 삭제
const deleteComment = async (reviewNo: number): Promise<string> => {
  return await authApi
    .delete(`comments/${reviewNo}`)
    .then(({ data }) => data.msg);
};

export { getComments, postComment, putComment, deleteComment };
