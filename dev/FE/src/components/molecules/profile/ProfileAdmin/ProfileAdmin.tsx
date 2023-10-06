import { FundingBar } from '@/components/molecules';
import { convertClassName, convertClassNameList, priceFilter } from '@/utils';
import styles from './ProfileAdmin.module.scss';
import { useQuery } from '@tanstack/react-query';
import { Item } from '@/types';
import { getSponsorShipQuery } from '@/api/queries';
import { useMemo } from 'react';
import { Image, Table, Text } from '@/components/atoms';

interface ProfileAdminProps {
  className?: string;
}

const ProfileAdmin = ({ className }: ProfileAdminProps): JSX.Element => {
  const { data: sponsorships } = useQuery<Item[]>(getSponsorShipQuery());

  const data = useMemo(() => {
    if (!sponsorships) return { ratio: 0, fundingAmount: 0 };
    const fundings: Item[] = sponsorships.filter(
      (sponsorship) => sponsorship.isCompleted,
    );
    const totalPrice = fundings.reduce(
      (total, { unitPrice, itemCount }) => total + unitPrice * itemCount * 0.3,
      0,
    );
    const fundingAmount = fundings.reduce(
      (total, { fundingAmount }) => total + fundingAmount,
      0,
    );
    const ratio = Math.round((fundingAmount * 100) / totalPrice);
    return { ratio, fundingAmount, totalPrice };
  }, [sponsorships]);

  return (
    <div
      style={{
        width: '75%',
      }}
      className={
        (convertClassNameList(convertClassName(className, styles)),
        styles['profile-admin'])
      }
    >
      <FundingBar className={styles['profile-admin__funding-bar']} {...data} />
      <div className={styles['profile-admin__record']}>
        <Text
          className={styles['profile-admin__record--text']}
          text="완료한 펀딩 내역"
        />
        <Table
          data={
            sponsorships &&
            sponsorships
              .filter(
                ({ isCompleted, fundingAmount, itemCount, unitPrice }) =>
                  isCompleted && fundingAmount === itemCount * unitPrice * 0.3,
              )
              .map(({ imagePath, itemName, fundingRanking, fundingAmount }) => {
                return {
                  사진: <Image src={imagePath} />,
                  이름: itemName,
                  펀딩금액: priceFilter(fundingAmount),
                  펀딩왕: fundingRanking?.[0]?.userName,
                };
              })
          }
          size={3}
        />
      </div>
    </div>
  );
};

export default ProfileAdmin;
