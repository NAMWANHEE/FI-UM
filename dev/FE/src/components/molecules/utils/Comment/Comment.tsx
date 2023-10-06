import { convertClassName, convertClassNameList, convertDate } from '@/utils';
import styles from './Comment.module.scss';
import { useQuery } from '@tanstack/react-query';
import { Comment as CommentType } from '@/types';
import { getCommentsQuery } from '@/api/queries';
import { Button, Text } from '@/components/atoms';
import { deleteComment, postComment } from '@/api/comment';
import { ChangeEvent, MouseEvent, useState } from 'react';
import useAuth from '@/hooks/useAuth';
import { USER_TYPE } from '@/constants';

interface CommentProps {
  className?: string;
  reviewNo: number;
}

const Comment = ({ className, reviewNo }: CommentProps): JSX.Element => {
  const { data: comments } = useQuery<CommentType[]>(
    getCommentsQuery(reviewNo),
  );
  const [comment, setComment] = useState<string>('');
  const { userInfo } = useAuth();

  const handleCommentCreate = async () => {
    const msg = await postComment(reviewNo, comment);
    setComment('');
    alert(msg);
  };

  const handleCommentRemove = async (e: MouseEvent<HTMLButtonElement>) => {
    const msg = await deleteComment(Number(e.currentTarget.value));
    alert(msg);
  };

  const handleChangeComment = (e: ChangeEvent<HTMLInputElement>) => {
    setComment(e.currentTarget.value);
  };

  return (
    <div
      className={convertClassNameList(
        convertClassName(className, styles),
        styles['comment'],
      )}
    >
      <div className={styles['comment__create-box']}>
        <input
          autoFocus
          value={comment}
          className={styles['comment__create-box--text']}
          onChange={handleChangeComment}
          onKeyUp={(e) => {
            if (e.key === 'Enter') handleCommentCreate();
          }}
        />
        <Button
          className={styles['comment__create-box--button']}
          onClick={handleCommentCreate}
          label="등록"
        />
      </div>

      <div className={styles['comment__item-container']}>
        {comments?.map(
          ({ comment, commentNo, createTime, userName, userNo }) => (
            <div className={styles['comment__item']} key={commentNo}>
              <Text
                className={styles['comment__item--content']}
                text={comment}
              />
              <div className="flex-container jc-space-between">
                <Text
                  className={styles['comment__item--info']}
                  text={`${userName} | ${convertDate(createTime)}`}
                />
                {(userInfo?.userNo === userNo ||
                  userInfo?.userType === USER_TYPE.원장쌤) && (
                  <Button
                    className={styles['comment__item--remove']}
                    value={commentNo}
                    onClick={handleCommentRemove}
                    label="X"
                  />
                )}
              </div>
            </div>
          ),
        )}
      </div>
    </div>
  );
};

export default Comment;
