import { Text } from '@/components/atoms';
import { convertClassName, convertClassNameList } from '@/utils';
import styles from './AuctionDetailDescription.module.scss';

interface AuctionDetailDescriptionProps {
  className?: string;
  description?: string;
}

const AuctionDetailDescription = ({
  className,
  description,
}: AuctionDetailDescriptionProps): JSX.Element => {
  return (
    <div
      className={convertClassNameList(
        convertClassName(className, styles),
        styles['auction-detail-description'],
      )}
    >
      <Text className="text-lg" text="작품 소개" />
      <Text text={description} />
    </div>
  );
};

export default AuctionDetailDescription;
