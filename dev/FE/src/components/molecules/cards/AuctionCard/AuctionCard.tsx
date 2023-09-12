import { Button, Image, Text } from '@/components/atoms';
import styles from './AuctionCard.module.scss';
import { convertClassName, formatCurrency } from '@/utils';

interface AuctionCardProps {
  src: string;
  alt: string;
  title: string;
  startValue: string;
  currentValue: string;
  buyItNow: string;
  className?: string;
  onClick?: () => void;
}

const AuctionCard = ({
  src,
  alt,
  title,
  startValue,
  currentValue,
  buyItNow,
  className,
  onClick,
}: AuctionCardProps) => {
  return (
    <div
      className={`${styles.auctionCard} ${convertClassName(className, styles)}`}
    >
      <div className={styles.image}>
        <Image src={src} alt={alt} />
      </div>
      <div className={styles.title}>
        <Text text={title} className="text-lg" />
      </div>
      <div className={styles.content}>
        <Text text={`시작가 : ${formatCurrency(startValue)}`} />
        <Text text={`현재가 : ${formatCurrency(currentValue)}`} />
        <Text text={`즉시구매가 : ${formatCurrency(buyItNow)}`} />
      </div>
      <div className={styles.button}>
        <Button
          label="경매하러가기"
          className="primary xsmall"
          onClick={onClick}
        />
      </div>
    </div>
  );
};

export default AuctionCard;
