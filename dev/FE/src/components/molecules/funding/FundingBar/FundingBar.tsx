import { BarChart, Text } from '@/components/atoms';
import styles from './FundingBar.module.scss';
import {
  convertClassName,
  convertClassNameList,
  countFilter,
  priceFilter,
} from '@/utils';

interface FundingBar {
  className?: string;
  itemCount?: number;
  totalPrice?: number;
  itemUnitPrice?: number;
  ratio: number;
  fundingAmount: number;
}

const FundingBar = ({
  className,
  ratio,
  itemCount,
  itemUnitPrice,
  fundingAmount,
  totalPrice,
}: FundingBar): JSX.Element => {
  return (
    <div
      className={convertClassNameList(
        styles['funding-bar'],
        convertClassName(className, styles),
      )}
    >
      {/* <Text
        text="목표금액"
        className={convertClassNameList('text-sm', styles['funding-bar__item'])}
      ></Text> */}
      <BarChart
        className={convertClassNameList(styles['funding-bar__item'])}
        ratio={ratio}
      />
      <div
        className={convertClassNameList(
          'flex-container',
          styles['funding-bar__item--bottom'],
        )}
      >
        {itemUnitPrice ? (
          <div className="flex-container-col flex-item">
            <Text className="text-sm gray" text="개당 가격" />
            <Text className="text-sm" text={priceFilter(itemUnitPrice)} />
          </div>
        ) : (
          ''
        )}
        {itemCount ? (
          <div className="flex-container-col flex-item">
            <Text className="text-sm gray" text="개수" />
            <Text className="text-sm orange" text={countFilter(itemCount)} />
          </div>
        ) : (
          ''
        )}
        <div className="flex-container-col flex-item">
          <Text className="text-sm gray" text="현재 금액" />
          <Text
            className="text-sm orange"
            text={priceFilter(fundingAmount)}
          ></Text>
        </div>
        <div className="flex-container-col flex-item">
          <Text className="text-sm gray" text="목표 금액" />
          <Text className="text-sm" text={priceFilter(totalPrice)} />
        </div>
      </div>
    </div>
  );
};

export default FundingBar;
