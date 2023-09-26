import { Button, Text } from '@/components/atoms';
import { convertClassName, convertClassNameList, priceFilter } from '@/utils';
import styles from './AuctionDescription.module.scss';
import { useEffect, useState } from 'react';

interface AuctionDescriptionProps {
  className?: string;
  title: string;
  auctionPrice: number;
  instantPrice: number;
  createdTime: number;
  auctionClick: () => void;
  buyItClick: () => void;
}

const AuctionDescription = ({
  className,
  title,
  auctionPrice,
  instantPrice,
  createdTime,
  auctionClick,
  buyItClick,
}: AuctionDescriptionProps): JSX.Element => {
  const [timeLeft, setTimeLeft] = useState<number>(0);

  useEffect(() => {
    const endTime = new Date(createdTime).getTime() + 24 * 60 * 60 * 1000;
    const updateCountdown = () => {
      const now = Date.now();
      const diff = endTime - now;
      setTimeLeft(diff);
    };

    updateCountdown();
    const interval = setInterval(updateCountdown, 1000);

    return () => clearInterval(interval);
  }, [createdTime]);

  const hours = Math.floor(timeLeft / (60 * 60 * 1000));
  const minutes = Math.floor((timeLeft % (60 * 60 * 1000)) / (60 * 1000));
  const seconds = Math.floor((timeLeft % (60 * 1000)) / 1000);

  return (
    <div
      className={convertClassNameList(
        convertClassName(className, styles),
        styles['auction-description'],
      )}
    >
      <Text
        className={convertClassNameList('text-xl', styles['title'])}
        text={title}
      />
      <div>
        <Text className="text-lg" text="현재가" /> :
        <Text className="blue" text={priceFilter(auctionPrice)} />
      </div>
      <div>
        <Text className="text-lg" text="즉시구매가" /> :
        <Text className="blue" text={priceFilter(instantPrice)} />
      </div>
      <div>
        <Text className="text-lg" text="남은 시간" /> :
        <Text
          className="blue"
          text={`${hours}시간 ${minutes}분 ${seconds}초 남았습니다.`}
        />
      </div>
      <div className={styles.buttonWrapper}>
        <Button
          className={convertClassNameList('primary', styles['button'])}
          label="경매하기"
          onClick={auctionClick}
        />
        <Button
          className={convertClassNameList('primary', styles['button'])}
          label="즉시구매"
          onClick={buyItClick}
        />
      </div>
    </div>
  );
};

export default AuctionDescription;
