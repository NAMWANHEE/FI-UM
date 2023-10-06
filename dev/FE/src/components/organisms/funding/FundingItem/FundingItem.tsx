import { useState } from 'react';
import { convertClassName, convertClassNameList } from '@/utils';
import styles from './FundingItem.module.scss';
import { Image, Text } from '@/components/atoms';
import { FundingBar, FundingDescription } from '@/components/molecules';
import { Funding, FundingRanking } from '@/types';

interface FundingItemProps {
  className?: string;
  itemNo: string;
  itemName: string;
  imagePath: string;
  itemUnitPrice: number;
  itemCount: number;
  fundingAmount: number;
  description: string;
  unitPrice: number;
  sponsorshipAmount: number;
  fundingRanking: FundingRanking[];
  isCompleted: boolean;
  onModal: (i: Funding) => void;
}

const FundingItem = ({
  className,
  onModal,
  ...funding
}: FundingItemProps): JSX.Element => {
  const [showDescription, setShowDescription] = useState<boolean>(false);
  const toggleDescription = () => {
    setShowDescription(!showDescription);
  };

  return (
    <div>
      <div
        className={convertClassNameList(
          convertClassName(className, styles),
          styles['funding-item'],
          'flex-container',
          'container',
        )}
        onClick={toggleDescription}
      >
        <Image
          className={convertClassNameList(styles['funding-item__image'])}
          src={funding?.imagePath || '/vite.svg'}
          alt={funding?.itemName}
        />
        <div className={convertClassNameList(styles['funding-item__main'])}>
          <Text
            className={convertClassNameList(
              styles['funding-item__main'],
              'text-xl',
            )}
            text={funding.itemName}
          />
          <Text
            className={convertClassNameList(styles['funding-item__main'])}
            text={funding.description ?? funding.description}
          />
          {funding.itemUnitPrice ? (
            <FundingBar
              itemUnitPrice={funding.itemUnitPrice * 0.3}
              totalPrice={funding.itemUnitPrice * funding.itemCount * 0.3}
              fundingAmount={funding.fundingAmount}
              itemCount={funding.itemCount}
              ratio={
                (funding.fundingAmount /
                  (funding.itemUnitPrice * funding.itemCount) /
                  3) *
                10 *
                100
              }
            />
          ) : (
            <FundingBar
              itemUnitPrice={
                funding.isCompleted
                  ? funding.unitPrice * 0.3
                  : funding.unitPrice
              }
              totalPrice={
                funding.isCompleted
                  ? funding.unitPrice * funding.itemCount * 0.3
                  : funding.unitPrice * funding.itemCount
              }
              fundingAmount={
                funding.isCompleted
                  ? funding.fundingAmount
                  : funding.sponsorshipAmount
              }
              itemCount={funding.itemCount}
              ratio={
                funding.isCompleted
                  ? (funding.fundingAmount /
                      (funding.unitPrice * funding.itemCount) /
                      3) *
                    10 *
                    100
                  : (funding.sponsorshipAmount /
                      (funding.unitPrice * funding.itemCount)) *
                    100
              }
            />
          )}
        </div>
      </div>
      {showDescription ? (
        <FundingDescription
          onModal={!funding.isCompleted ? onModal : () => {}}
          funding={funding}
        />
      ) : (
        <></>
      )}
    </div>
  );
};

export default FundingItem;
