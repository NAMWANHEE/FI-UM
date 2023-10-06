import { TruncateText, convertClassName, convertClassNameList } from '@/utils';
import styles from './GalleryPage.module.scss';
import { Button, Text } from '@/components/atoms';
import useAuth from '@/hooks/useAuth';
import { useNavigate } from 'react-router-dom';
import { USER_TYPE } from '@/constants';
import { useQuery } from '@tanstack/react-query';
import { Review } from '@/types';
import { getReviewsQuery } from '@/api/queries/review';
import { AuctionCard } from '@/components/molecules';
import { useEffect, useState } from 'react';

interface GalleryPageProps {
  className?: string;
}

const GalleryPage = ({ className }: GalleryPageProps): JSX.Element => {
  const { userInfo } = useAuth();
  const navigate = useNavigate();
  const { data: reviews } = useQuery<Review[], Error>(getReviewsQuery());

  const handleMoveReviewDetail = (reviewNo: number) => {
    navigate(`/gallery/${reviewNo}`);
  };

  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const timer = setTimeout(() => {
      setLoading(false);
    }, 700);

    return () => clearTimeout(timer);
  }, []);

  return (
    <>
      {loading && (
        <div className="page-loading">
          <img
            style={{ height: '250px', width: '250px' }}
            src="./img/loading/gallery.gif"
          />
        </div>
      )}
      <div
        className={convertClassNameList(
          convertClassName(className, styles),
          styles['gallery-page'],
        )}
      >
        <div className="flex-container jc-space-between align-center m-1">
          <Text className="text-xxl bold" text="후기 게시판" />
          <div>
            {userInfo?.userType === USER_TYPE.원장쌤 && (
              <Button
                className={convertClassNameList(
                  convertClassName(className, styles),
                  'self-end m-1',
                  'primary xsmall',
                )}
                label="등록하기"
                onClick={() => navigate(`/create`, { state: 'gallery' })}
              />
            )}
          </div>
        </div>
        <div
          className={convertClassNameList(
            styles['gallery-page__card-container'],
          )}
        >
          {reviews
            ?.sort((a, b) => Number(a.createTime) - Number(b.createTime))
            .map(({ reviewNo, content, imagePath, title }) => (
              <AuctionCard
                key={reviewNo}
                itemImagePath={imagePath}
                title={TruncateText(title, 10)}
                content={TruncateText(content, 30)}
                review={true}
                onClick={() => handleMoveReviewDetail(reviewNo)}
              />
            ))}
        </div>
      </div>
    </>
  );
};

export default GalleryPage;
