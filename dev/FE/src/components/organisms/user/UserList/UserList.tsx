import { convertClassName, convertClassNameList, priceFilter } from '@/utils';
import styles from './UserList.module.scss';
import { TotalCapital } from '@/types';
import { Button, Table } from '@/components/atoms';
import { memo } from 'react';

interface UserListProps {
  className?: string;
  totalCapitals?: TotalCapital[];
  onClick: (capital: TotalCapital) => void;
}

const UserList = ({
  className,
  totalCapitals,
  onClick,
}: UserListProps): JSX.Element => {
  return (
    <div
      className={convertClassNameList(
        convertClassName(className, styles),
        styles['user-list'],
      )}
    >
      <Table
        className={styles['user-list__table']}
        data={
          totalCapitals &&
          totalCapitals.map((capital) => {
            return {
              이름: <div key={capital.userNo}>{capital.userName}</div>,
              포인트: priceFilter(capital.point),
              펀딩: priceFilter(capital.fundingMoney),
              주식: priceFilter(capital.stockMoney),
              예적금: priceFilter(capital.depositMoney),
              차트: <Button label="상세" onClick={() => onClick(capital)} />,
            };
          })
        }
        size={5}
      />
    </div>
  );
};

const propsAreEqual: (
  prevProps: Readonly<UserListProps>,
  nextProps: Readonly<UserListProps>,
) => boolean = (prevProps, nextProps) => {
  if (prevProps.totalCapitals === nextProps.totalCapitals) return true;
  return false;
};

export default memo(UserList, propsAreEqual);
