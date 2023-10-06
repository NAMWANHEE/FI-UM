import { convertClassName, convertClassNameList, priceFilter } from '@/utils';
import styles from './ModalDeposit.module.scss';
import { Button, Text } from '@/components/atoms';
import { ChangeEvent, MouseEvent, useCallback, useMemo, useState } from 'react';
import { postBankDeposit, postBankSaving } from '@/api/deposit';
import { MyBankInfo } from '@/types';
import useAuth from '@/hooks/useAuth';

interface ModalDepositProps {
  className?: string;
  point: number;
  label: string;
  deposit: MyBankInfo;
  refetch: () => void;
  onClick?: () => void;
  toggle: () => void;
}

// 나중에 props로 대체 예정
const ratioList = [1, 5, 10, 100];

const ModalDeposit = ({
  className,
  label,
  toggle,
  point,
  refetch,
  deposit: { bankName, savingBalance, depositMoney },
}: ModalDepositProps): JSX.Element => {
  const [count, setCount] = useState<number>(0);
  const { refreshUserInfo } = useAuth();

  const maxCount: number =
    (label === '출금' ? savingBalance ?? depositMoney : point) || 0;

  const onClick = useCallback(async () => {
    if (count <= 0) return;
    if (!window.confirm(`${label} 하시겠습니까?`)) return;

    switch (label) {
      case '입금':
        await postBankDeposit(bankName, count);
        break;
      case '출금':
        await postBankDeposit(bankName, -count);
        break;
      case '가입':
        await postBankSaving(bankName, count);
        break;
    }
    toggle();
    refetch();
    refreshUserInfo();
  }, [bankName, count]);

  const colorStyle = useMemo(() => {
    if (label === '입금') {
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
      if (newCount < 0) return;
      setCount(Math.min(newCount, maxCount));
    },
    [],
  );

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
          className={convertClassNameList('text-xl', colorStyle.textColor)}
          text={label}
        />
      </div>

      <div
        className={convertClassNameList(
          'flex-container jc-space-between',
          styles['modal-stock__price'],
        )}
      >
        <Text className="text-md" text={'거래 후 보유 포인트'} />
        <Text
          className="text-md"
          text={priceFilter(point + (label === '입금' ? -count : count))}
        />
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
          text="&nbsp;원"
        />
        <div
          className={convertClassNameList(
            styles['modal-stock__input--button-container'],
          )}
        ></div>
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
              label={priceFilter(ratio * 10000)}
              value={count + ratio * 10000}
              onClick={handleChangeCount}
            />
          ),
        )}
      </div>

      {label !== '가입' && (
        <div
          className={convertClassNameList(
            'flex-container jc-space-between',
            styles['modal-stock__price'],
          )}
        >
          <Text className="text-md" text={'현재 계좌 잔액'} />
          <Text
            className="text-md"
            text={priceFilter((savingBalance || depositMoney) ?? 0)}
          />
        </div>
      )}

      <div
        className={convertClassNameList(
          'flex-container jc-space-between',
          styles['modal-stock__price'],
        )}
      >
        <Text className="text-md" text="거래 후 계좌 잔액" />
        <Text
          className="text-md"
          text={priceFilter(
            ((savingBalance || depositMoney) ?? 0) +
              (label === '출금' ? -count : count),
          )}
        />
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

export default ModalDeposit;
