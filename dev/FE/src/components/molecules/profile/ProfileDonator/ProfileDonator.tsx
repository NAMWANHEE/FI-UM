import { convertClassName, convertClassNameList, priceFilter } from '@/utils';
import styles from './ProfileDonator.module.scss';
import { Text } from '@/components/atoms';
import { useQuery } from '@tanstack/react-query';
import { ChildProfile, Purchase, SponsorProfile } from '@/types';
import { getUserQuery } from '@/api/queries/user';
import { useMemo } from 'react';
import { getAuctionPurchaseQuery, getFollowingQuery } from '@/api/queries';

interface ProfileDonatorProps {
  className?: string;
}

const ProfileDonator = ({ className }: ProfileDonatorProps): JSX.Element => {
  const { data: sponsorProfile } = useQuery<
    SponsorProfile | ChildProfile,
    Error
  >(getUserQuery());

  const { data: auctionPurchases } = useQuery<Purchase[]>(
    getAuctionPurchaseQuery(),
  );

  const { data: childProfiles } = useQuery<ChildProfile[]>(getFollowingQuery());

  const { sponsoredAmount, cash, point } = useMemo(() => {
    return sponsorProfile
      ? (sponsorProfile as SponsorProfile)
      : { sponsoredAmount: 0, cash: 0, point: 0 };
  }, [sponsorProfile]);

  return (
    <div
      style={{
        width: '75%',
      }}
      className={
        (convertClassNameList(convertClassName(className, styles)),
        'flex-container jc-space-around align-center')
      }
    >
      <div>
        <div className="flex-container jc-space-between">
          <div className="flex-container-col align-end md-1vw">
            <Text className="text-lg" text="내 캐시 :" />
            <Text className="text-lg" text="내 포인트 :" />
            <Text className="text-lg" text="후원 총액 :" />
          </div>
          <div className="flex-container-col align-end">
            <Text className="blue text-lg" text={priceFilter(cash)} />
            <Text className="blue text-lg" text={priceFilter(point)} />
            <Text
              className="blue text-lg"
              text={priceFilter(sponsoredAmount)}
            />
          </div>
        </div>
      </div>
      <div className="flex-container jc-space-between">
        <div className="flex-container-col align-end md-1vw">
          <Text className="text-lg" text={`구매한 그림 :`} />
          <Text className="text-lg" text={`팔로우한 아이들 :`} />
        </div>
        <div className="flex-container-col align-end">
          <Text className="text-lg" text={`${auctionPurchases?.length} 개`} />
          <Text className="text-lg" text={`${childProfiles?.length} 명`} />
        </div>
      </div>
    </div>
  );
};

export default ProfileDonator;
