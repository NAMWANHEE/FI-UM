import { convertClassName, convertClassNameList, priceFilter } from '@/utils';
import styles from './ModalFunding.module.scss';
import { Button, Text } from '@/components/atoms';
import { ChangeEvent, MouseEvent, useCallback, useMemo, useState } from 'react';
import { Funding, SponsorProfile } from '@/types';
import useAuth from '@/hooks/useAuth';
import { useQuery } from '@tanstack/react-query';
import { getUserQuery } from '@/api/queries';
import { USER_TYPE } from '@/constants';

interface ModalFundingProps {
  className?: string;
  item: Funding;
  onClick: (itemNo: number, price: number) => void;
  closeToggle?: () => void;
}

// 나중에 props로 대체 예정
const ratioList = [1, 5, 10, 100];
const balance = 100000;

const ModalFunding = ({
  className,
  item,
  onClick,
  closeToggle,
}: ModalFundingProps): JSX.Element => {
  const [price, setPrice] = useState<number>(0);
  const { userInfo } = useAuth();
  const point = userInfo?.point ?? 0;
  const { data: sponsorInfo } =
    userInfo?.userType === USER_TYPE.후원자
      ? useQuery(getUserQuery())
      : { data: undefined };
  const cash = sponsorInfo && 'cash' in sponsorInfo ? sponsorInfo?.cash : 0;

  const isChild = 'itemUnitPrice' in item;
  const totalPrice = item.unitPrice
    ? item.unitPrice * item.itemCount
    : item.itemUnitPrice * item.itemCount * 0.3;
  const curPrice = item.sponsorshipAmount
    ? item.sponsorshipAmount
    : item.fundingAmount;
  const maxPrice = Math.min(
    totalPrice - curPrice,
    item.isCompleted || isChild ? point : cash,
  );

  const colorStyle = useMemo(() => {
    return {
      textColor: 'blue',
      bgColor: 'bg-blue',
      bgColorLight: 'bg-skyblue',
    };
  }, []);

  // 주문 수량 변동 함수
  const handleChangePrice = useCallback(
    (
      e: ChangeEvent<HTMLInputElement> | MouseEvent<HTMLButtonElement>,
    ): void => {
      const newPrice = Number(e.currentTarget.value);
      if (newPrice < 0) return;
      setPrice(Math.min(newPrice, maxPrice));
    },
    [balance],
  );

  const handleFundingSend = () => {
    onClick(Number(item.itemNo), price);
  };

  return (
    <div
      className={
        (convertClassNameList(convertClassName(className, styles)),
        styles['modal-funding'])
      }
    >
      <div
        className={convertClassNameList(
          styles['modal-funding__title'],
          'flex-container',
        )}
      >
        <Text
          className={convertClassNameList('text-xl', colorStyle.textColor)}
          text={item.isCompleted || isChild ? '펀딩' : '후원'}
        />
      </div>

      <div
        className={convertClassNameList(
          'flex-container jc-space-between',
          styles['modal-funding__price'],
        )}
      >
        <Text
          className="text-md"
          text={
            item.isCompleted || isChild
              ? '거래 후 보유 포인트'
              : '거래 후 보유 캐시'
          }
        />
        <Text
          className="text-md"
          text={priceFilter(
            (item.isCompleted || isChild ? point : cash) - price,
          )}
        />
      </div>

      <div
        className={convertClassNameList(
          styles['modal-funding__input-container'],
        )}
      >
        <input
          className={convertClassNameList(styles['modal-funding__input'])}
          type="number"
          name="price"
          value={price.toFixed(0)}
          onChange={handleChangePrice}
        />
        <Text
          className={convertClassNameList(
            'text-sm',
            styles['modal-funding__input--text'],
          )}
          text="&nbsp;원"
        />
      </div>

      <div className="flex-container jc-space-between">
        {/* % 버튼들 */}
        {ratioList.map(
          (ratio: number): JSX.Element => (
            <Button
              key={ratio}
              className={convertClassNameList(
                styles['modal-funding__button'],
                'bg-gray-light',
              )}
              label={priceFilter(ratio * 10000)}
              value={price + ratio * 10000}
              onClick={handleChangePrice}
            />
          ),
        )}
      </div>

      <div
        className={convertClassNameList(
          'flex-container jc-space-between',
          styles['modal-funding__price'],
        )}
      >
        <Text
          className="text-md"
          text={item.isCompleted || isChild ? '전체 펀딩액' : '전체 후원액'}
        />
        <Text className="text-md" text={priceFilter(totalPrice)} />
      </div>

      <div
        className={convertClassNameList(
          'flex-container jc-space-between',
          styles['modal-funding__price'],
        )}
      >
        <Text
          className="text-md"
          text={item.isCompleted || isChild ? '현재 펀딩액' : '현재 후원액'}
        />
        <Text className="text-md" text={priceFilter(curPrice)} />
      </div>

      <div
        className={convertClassNameList(
          'flex-container jc-space-between',
          styles['modal-funding__price'],
        )}
      >
        <Text
          className="text-md"
          text={
            item.isCompleted || isChild ? '거래 후 펀딩액' : '거래 후 후원액'
          }
        />
        <Text
          className="text-md"
          text={priceFilter(
            item.sponsorshipAmount
              ? item.sponsorshipAmount + price
              : item.fundingAmount + price,
          )}
        />
      </div>

      <div
        className={convertClassNameList(
          'flex-container jc-center',
          styles['modal-funding__button-container'],
        )}
      >
        <Button
          className={convertClassNameList(
            styles['modal-funding__button'],
            colorStyle.bgColorLight,
            colorStyle.textColor,
          )}
          label="취소"
          onClick={closeToggle}
        />
        <Button
          className={convertClassNameList(
            styles['modal-funding__button'],
            'white',
            colorStyle.bgColor,
          )}
          label={item.isCompleted || isChild ? '펀딩' : '후원'}
          onClick={handleFundingSend}
        />
      </div>
    </div>
  );
};

export default ModalFunding;
