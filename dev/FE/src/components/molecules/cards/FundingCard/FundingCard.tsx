import { Image, Text } from '@/components/atoms';
import styles from './FundingCard.module.scss';
import {
  convertClassName,
  convertClassNameList,
  formatCurrency,
} from '@/utils';
import { FundingBar } from '../..';
import { Funding } from '@/types';
import { useNavigate } from 'react-router-dom';

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
  const navigate = useNavigate();
  const handleClick = () => {
    navigate('/funding');
  };
  const fundingGoalPrice = formatCurrency(
    String(itemUnitPrice * itemCount * 0.3),
  );
  const fundingCurrentPrice = formatCurrency(String(fundingAmount));
  return (
    <div
      className={convertClassNameList(
        convertClassName(className, styles),
        styles['funding-card'],
      )}
      data-testid="funding-card"
      onClick={handleClick}
    >
      <div className={styles['funding-card-imageWrapper']}>
        <Image
          className={styles['funding-card-image']}
          src={imagePath}
          alt={itemName}
        />
      </div>

      <div className={styles['funding-card-contentWrapper']}>
        <Text
          className={convertClassNameList(styles['funding-card-text'])}
          text={itemName}
        />
        <div>펀딩된 금액 : {fundingCurrentPrice}원</div>
        <div>펀딩 목표 금액 : {fundingGoalPrice}원</div>
      </div>
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
