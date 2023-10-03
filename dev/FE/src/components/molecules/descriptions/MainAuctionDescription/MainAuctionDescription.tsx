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
      navigate(`/auction/${e.currentTarget.value}`);
    },
    [auction.auctionNo],
  );

  return (
    <div
      className={convertClassNameList(
        convertClassName(className, styles),
        styles['main-auction-description'],
      )}
    >
      <Text className="text-xl" text={auction.title} />
      <Text className="text-sm" text={auction.name} />
      <Text text={auction.content} />
      <div className="flex-container">
        <Text className="text-sm" text={priceFilter(auction.auctionPrice)} />
        &nbsp;
        <Text className="text-sm" text={priceFilter(auction.instantPrice)} />
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
