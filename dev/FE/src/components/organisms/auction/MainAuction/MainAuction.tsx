import { MainAuctionDescription, Swiper } from '@/components/molecules';
import { Image } from '@/components/atoms';
import { convertClassName, convertClassNameList } from '@/utils';
import styles from './MainAuction.module.scss';
import { Auction } from '@/types';

interface MainAuctionProps {
  className?: string;
  auctions?: Auction[];
}

const MainAuction = ({
  className,
  auctions,
}: MainAuctionProps): JSX.Element => {
  return (
    <div className={convertClassNameList(convertClassName(className, styles))}>
      <Swiper type="autoplay">
        {auctions?.map((auction) => {
          return (
            <div
              className={styles['main-auction']}
              key={auction.auctionNo + auction.title}
            >
              <MainAuctionDescription
                className={styles['main-auction__description']}
                auction={auction}
              />
              <div className={styles.imageWrapper}>
                <Image
                  className={convertClassNameList(
                    convertClassName(className, styles),
                    styles['main-auction__image'],
                  )}
                  src={auction?.itemImagePath}
                  alt="aa"
                />
              </div>
            </div>
          );
        })}
      </Swiper>
    </div>
  );
};

export default MainAuction;
