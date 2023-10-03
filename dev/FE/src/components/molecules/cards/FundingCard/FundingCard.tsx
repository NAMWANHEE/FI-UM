import { Image, Text } from '@/components/atoms';
import styles from './FundingCard.module.scss';
import { convertClassName, convertClassNameList } from '@/utils';
import { FundingBar } from '../..';
import { Funding } from '@/types';

interface FundingCardProps {
  className?: string;
  funding: Funding;
  ratio?: number;
}

const FundingCard = ({
  className,
  ratio,
  funding: { imagePath, itemName, itemUnitPrice, itemCount, fundingAmount },
}: FundingCardProps): JSX.Element => {
  return (
    <div
      className={convertClassNameList(
        convertClassName(className, styles),
        styles['funding-card'],
      )}
      data-testid="funding-card"
    >
      <div className={styles['funding-card-imageWrapper']}>
        <Image
          className={styles['funding-card-image']}
          src={imagePath}
          alt={itemName}
        />
      </div>
      <Text
        className={convertClassNameList('test-sm', styles['funding-card-text'])}
        text={itemName}
      />
      {ratio && (
        <FundingBar
          className={convertClassNameList(
            styles['bar-chart'],
            styles['funding-card-funding-bar'],
          )}
          ratio={70}
          itemCount={itemCount}
          itemUnitPrice={itemUnitPrice}
          fundingAmount={fundingAmount}
        />
      )}
    </div>
  );
};

export default FundingCard;
