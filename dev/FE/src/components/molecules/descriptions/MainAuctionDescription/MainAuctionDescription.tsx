import { Button, Text } from '@/components/atoms';
import { convertClassName, convertClassNameList, priceFilter } from '@/utils';
import styles from './MainAuctionDescription.module.scss';
import { Auction } from '@/types';
import { useNavigate } from 'react-router-dom';
import { MouseEvent, useCallback } from 'react';

interface MainAuctionDescriptionProps {
  className?: string;
  auction: Auction;
}

const MainAuctionDescription = ({
  className,
  auction,
}: MainAuctionDescriptionProps): JSX.Element => {
  const navigate = useNavigate();

  const onMove = useCallback(
    (e: MouseEvent<HTMLButtonElement>) => {
      navigate(`/auction/${e.currentTarget.value}`, {
        state: {
          src: auction.userImagePath,
          alt: auction.title,
          userNo: auction.userNo,
        },
      });
    },
    [auction],
  );

  return (
    <div
      className={convertClassNameList(
        convertClassName(className, styles),
        styles['main-auction-description'],
      )}
    >
      <Text
        className={convertClassNameList(
          'text-xl',
          styles['main-auction-description__title'],
        )}
        text={auction.title}
      />
      <Text className="text-sm" text={auction.name} />
      <Text text={auction.content} />
      <div className="flex-container-col">
        <div>
          <Text
            className="text-sm"
            text={'현재 가격 : ' + priceFilter(auction.auctionPrice)}
          />
        </div>
        <div>
          <Text
            className="text-sm"
            text={'즉시 구매가 : ' + priceFilter(auction.instantPrice)}
          />
        </div>
      </div>

      <Button
        className={convertClassNameList(
          styles['main-auction-description__button'],
          'primary',
        )}
        label="사러가기"
        value={auction.auctionNo}
        onClick={onMove}
      />
    </div>
  );
};

export default MainAuctionDescription;
