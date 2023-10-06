import {
  ChangeEvent,
  MouseEvent,
  useCallback,
  useMemo,
  useState,
  useEffect,
} from 'react';
import { convertClassName, convertClassNameList, priceFilter } from '@/utils';
import styles from './ModalStock.module.scss';
import { Button, Text } from '@/components/atoms';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faChevronDown, faChevronUp } from '@/utils/fontAwesomeIcon';
import { StockAccount } from '@/types';

interface ModalStockProps {
  className?: string;
  label: string;
  onClick: () => void;
  toggle: () => void;
  price: number;
  totalCount: number;
  stockNo: number;
  setStockAccount: (e: StockAccount) => void;
}

// 나중에 props로 대체 예정
const ratioList = [10, 25, 50, 100];
// const totalCount = 12;
// const price = 10000;

const ModalStock = ({
  className,
  label,
  onClick,
  toggle,
  price,
  totalCount,
  stockNo,
  setStockAccount,
}: ModalStockProps): JSX.Element => {
  const [count, setCount] = useState<number>(0);

  const colorStyle = useMemo(() => {
    if (label === '매도') {
      return {
        textColor: 'blue',
        bgColor: 'bg-blue',
        bgColorLight: 'bg-skyblue',
      };
    } else {
      return {
        textColor: 'red',
        bgColor: 'bg-red',
        bgColorLight: 'bg-pink',
      };
    }
  }, [label]);

  // 주문 수량 변동 함수
  const handleChangeCount = useCallback(
    (
      e: ChangeEvent<HTMLInputElement> | MouseEvent<HTMLButtonElement>,
    ): void => {
      const newCount = Number(e.currentTarget.value);
      if (newCount > totalCount || newCount < 0) return;
      setCount(newCount);
    },
    [totalCount],
  );

  useEffect(() => {
    setStockAccount({
      stockNo: stockNo,
      price: price,
      count: count,
    });
  }, [count]);

  return (
    <div
      className={
        (convertClassNameList(convertClassName(className, styles)),
        styles['modal-stock'])
      }
    >
      <div
        className={convertClassNameList(
          styles['modal-stock__title'],
          'flex-container',
        )}
      >
        <Text
          className={convertClassNameList('text-lg', colorStyle.textColor)}
          text={label}
        />
        <Text className="text-lg" text="&nbsp;주문" />
      </div>
      <div className="flex-container jc-space-between">
        <Text className="text-md" text="주문수량" />
        <div className="flex-container">
          <Text className="text-md" text="주문가능&nbsp;" />
          <Text
            className={convertClassNameList('text-md', colorStyle.textColor)}
            text={String(totalCount)}
          />
          <Text className="text-md" text="&nbsp;주" />
        </div>
      </div>

      <div
        className={convertClassNameList(styles['modal-stock__input-container'])}
      >
        <input
          className={convertClassNameList(styles['modal-stock__input'])}
          type="number"
          name="count"
          value={count.toFixed(0)}
          onChange={handleChangeCount}
        />
        <Text
          className={convertClassNameList(
            'text-sm',
            styles['modal-stock__input--text'],
          )}
          text="&nbsp;주"
        />
        <div
          className={convertClassNameList(
            styles['modal-stock__input--button-container'],
          )}
        >
          <Button
            className={convertClassNameList(
              styles['modal-stock__input--button'],
            )}
            label={<FontAwesomeIcon icon={faChevronUp} size="xs" />}
            value={count + 1}
            onClick={handleChangeCount}
          />
          <Button
            className={convertClassNameList(
              styles['modal-stock__input--button'],
            )}
            label={<FontAwesomeIcon icon={faChevronDown} size="xs" />}
            value={count - 1}
            onClick={handleChangeCount}
          />
        </div>
      </div>

      <div className="flex-container jc-space-between">
        {/* % 버튼들 */}
        {ratioList.map(
          (ratio: number): JSX.Element => (
            <Button
              key={ratio}
              className={convertClassNameList(
                styles['modal-stock__button'],
                'bg-gray-light',
              )}
              label={ratio + '%'}
              value={String(Math.floor((totalCount * ratio) / 100))}
              onClick={handleChangeCount}
            />
          ),
        )}
      </div>

      <div
        className={convertClassNameList(
          'flex-container jc-space-between',
          styles['modal-stock__price'],
        )}
      >
        <Text className="text-md" text="주문단가" />
        <Text className="text-md" text={priceFilter(price)} />
      </div>

      <div
        className={convertClassNameList(
          'flex-container jc-space-between',
          styles['modal-stock__price'],
        )}
      >
        <Text className="text-md" text="총 주문금액" />
        <Text className="text-md" text={priceFilter(price * count)} />
      </div>

      <div
        className={convertClassNameList(
          'flex-container jc-center',
          styles['modal-stock__button-container'],
        )}
      >
        <Button
          className={convertClassNameList(
            styles['modal-stock__button'],
            colorStyle.bgColorLight,
            colorStyle.textColor,
          )}
          label="취소"
          onClick={toggle}
        />
        <Button
          className={convertClassNameList(
            styles['modal-stock__button'],
            'white',
            colorStyle.bgColor,
          )}
          label={label}
          onClick={onClick}
        />
      </div>
    </div>
  );
};

export default ModalStock;
