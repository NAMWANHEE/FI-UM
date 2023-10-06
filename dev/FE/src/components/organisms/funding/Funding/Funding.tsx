import {
  checkConditionClassName,
  convertClassName,
  convertClassNameList,
} from '@/utils';
import styles from './Funding.module.scss';
import { FundingCard, Swiper } from '@/components/molecules';
import { Funding as FundingType } from '@/types';
import { useMemo } from 'react';

interface FundingProps {
  className?: string;
  fundings?: FundingType[];
}

const Funding = ({ className, fundings }: FundingProps): JSX.Element => {
  const funding = useMemo(() => {
    const funding: JSX.Element[] = [];
    if (fundings) {
      const len = fundings.length;

      for (let index = 0; index <= (len - 1) / 4; index++) {
        funding.push(
          <div key={index} className="card-container">
            {fundings
              .slice(index * 4, Math.min((index + 1) * 4, len))
              .map((funding) => (
                <FundingCard
                  className={checkConditionClassName(
                    funding.fundingAmount ===
                      funding.itemCount * funding.itemUnitPrice * 0.3,
                    styles['funding__finished'],
                  )}
                  key={funding.itemNo}
                  funding={funding}
                />
              ))}
          </div>,
        );
      }
    }
    return funding;
  }, [fundings]);

  return (
    <div
      className={convertClassNameList(
        convertClassName(className, styles),
        styles['funding'],
      )}
    >
      <Swiper>{funding}</Swiper>
    </div>
  );
};

export default Funding;
