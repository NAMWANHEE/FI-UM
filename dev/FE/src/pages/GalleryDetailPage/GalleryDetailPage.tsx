import { convertClassName, convertClassNameList } from '@/utils';
import styles from './GalleryDetailPage.module.scss';
import { GalleryDetail } from '@/components/organisms';
import { useParams } from 'react-router-dom';
import { Review } from '@/types';
import { getReviewQuery } from '@/api/queries';
import { useQuery } from '@tanstack/react-query';

interface GalleryDetailPageProps {
  className?: string;
}

const GalleryDetailPage = ({
  className,
}: GalleryDetailPageProps): JSX.Element => {
  const { reviewNo } = useParams();
  const { data: review } = useQuery<Review>(getReviewQuery(Number(reviewNo)));

  return (
    <div
      className={convertClassNameList(
        convertClassName(className, styles),
        styles['gallery-detail-page'],
      )}
    >
      {review && <GalleryDetail review={review} />}
    </div>
  );
};

export default GalleryDetailPage;
