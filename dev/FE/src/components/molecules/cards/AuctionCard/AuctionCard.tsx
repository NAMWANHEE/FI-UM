import { Button, Image, Text } from '@/components/atoms';
import styles from './AuctionCard.module.scss';
import { convertClassName, convertClassNameList, priceFilter } from '@/utils';

interface AuctionCardProps {
  className?: string;
  onClick?: () => void;
  itemImagePath: string;
  title: string;
  auctionPrice?: number;
  instantPrice?: number;
  content?: string;
  review?: boolean;
  noBtn?: boolean;
  winner?: number | null;
}

const AuctionCard = ({
  className,
  onClick,
  itemImagePath,
  title,
  auctionPrice,
  instantPrice,
  content,
  review,
  noBtn,
  winner,
}: AuctionCardProps) => {
  return (
    <div
      className={`${styles.auctionCard} ${convertClassName(className, styles)}`}
    >
      <div className={styles.image}>
        <Image src={itemImagePath} alt={title} />
      </div>

      <Text
        text={title}
        className={convertClassNameList('text-lg', styles['title'])}
      />
      <div className={styles.content}>
        {auctionPrice && (
          <Text
            className="bold"
            text={`현재가 : ${priceFilter(auctionPrice)}`}
          />
        )}
        {instantPrice && (
          <Text
            className="bold"
            text={`즉시구매가 : ${priceFilter(instantPrice)}`}
          />
        )}
        {content && <Text text={content} />}
      </div>
      {!noBtn && onClick && (
        <div className={styles.button}>
          <Button
            label={
              review ? '자세히 보기' : winner ? '경매 완료' : '경매하러가기'
            }
            className={
              winner
                ? `gray xsmall ${styles['auction-card__finished']}`
                : 'primary xsmall'
            }
            onClick={winner ? undefined : onClick}
          />
        </div>
      )}
    </div>
  );
};

export default AuctionCard;
