import { Image } from '@/components/atoms';
import { AuctionDescription } from '@/components/molecules';
import styles from './AuctionDetailMain.module.scss';
import { convertClassName } from '@/utils';

interface AuctionDetailMainProps {
  title: string;
  src: string;
  alt: string;
  className?: string;
  imageClassName?: string;
  descriptionClassName?: string;
  auctionClick: () => void;
  buyItClick: () => void;
  auctionPrice: number;
  instantPrice: number;
  createdTime: number;
}

const AuctionDetailMain = ({
  title,
  src,
  alt,
  className,
  imageClassName,
  descriptionClassName,
  auctionClick,
  buyItClick,
  auctionPrice,
  instantPrice,
  createdTime,
}: AuctionDetailMainProps) => {
  return (
    <div
      className={`${styles['auction-detail-main']} ${convertClassName(
        className,
        styles,
      )}`}
    >
      {/* <div className={styles['auction-detail-main__title']}>
        <Text text={title} />
      </div> */}
      <div className={styles['auction-detail-main__image']}>
        <Image src={src} alt={alt} className={imageClassName} />
      </div>
      <div className={styles['auction-detail-main__description']}>
        <AuctionDescription
          title={title}
          className={descriptionClassName}
          auctionPrice={auctionPrice}
          instantPrice={instantPrice}
          createdTime={createdTime}
          auctionClick={auctionClick}
          buyItClick={buyItClick}
        />
      </div>
    </div>
  );
};

export default AuctionDetailMain;
