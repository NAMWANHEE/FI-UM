import { convertClassName, convertClassNameList } from '@/utils';
import styles from './GalleryDetail.module.scss';
import { Image, Text } from '@/components/atoms';
import { Comment } from '@/components/molecules';
import { Review } from '@/types';

interface GalleryDetailProps {
  className?: string;
  review: Review;
}

const GalleryDetail = ({
  className,
  review,
}: GalleryDetailProps): JSX.Element => {
  return (
    <div
      className={convertClassNameList(
        convertClassName(className, styles),
        styles['gallery-detail'],
      )}
    >
      <div className={styles.imageWrapper}>
        <Image
          className={convertClassNameList(styles['gallery-detail__image'])}
          src={review.imagePath}
          alt="vite"
        />
      </div>
      <div className={convertClassNameList(styles['gallery-detail__content'])}>
        <Text
          className={convertClassNameList(
            styles['gallery-detail__content--title'],
            'text-xxl',
          )}
          text={review.title}
        />
        <Text
          className={convertClassNameList(
            styles['gallery-detail__content--description'],
          )}
          text={review.content}
        />

        <Comment
          reviewNo={review.reviewNo}
          className={convertClassNameList(
            styles['gallery-detail__content--comment'],
          )}
        />
      </div>
    </div>
  );
};

export default GalleryDetail;
