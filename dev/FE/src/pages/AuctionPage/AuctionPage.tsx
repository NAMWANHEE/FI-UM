import { convertClassName, convertClassNameList } from '@/utils';
import styles from './AuctionPage.module.scss';
import { AuctionCard } from '@/components/molecules';
import { useNavigate } from 'react-router-dom';
import { useQuery } from '@tanstack/react-query';
import { Auction } from '@/types';
import { getAuctionsQuery } from '@/api/queries/auction';
import useAuth from '@/hooks/useAuth';
import { Button } from '@/components/atoms';
import { USER_TYPE } from '@/constants';
import { useEffect, useState } from 'react';

interface AuctionPageProps {
  className?: string;
}

const AuctionPage = ({ className }: AuctionPageProps): JSX.Element => {
  const { data: auctions } = useQuery<Auction[], Error>(getAuctionsQuery());
  const navigate = useNavigate();
  const { userInfo } = useAuth();
  const userType = userInfo?.userType ?? 3;
  console.log(auctions);
  const currentTime = Date.now();
  const checkTimeFunction = (createdTime: number): boolean => {
    const checkTime = currentTime - createdTime;
    const day = 60 * 60 * 24 * 1000;
    if (checkTime < day) {
      return true;
    }
    return false;
  };

  const handleMoveAuctionDetail = (auctionNo: number, auction: Auction) => {
    navigate(`/auction/${auctionNo}`, { state: auction });
  };

  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const timer = setTimeout(() => {
      setLoading(false);
    }, 700);

    return () => clearTimeout(timer);
  }, []);

  return (
    <>
      {loading && (
        <div className="page-loading">
          <img
            style={{ height: '250px', width: '250px' }}
            src="./img/loading/auction.gif"
          />
        </div>
      )}
      <div
        className={convertClassNameList(
          convertClassName(className, styles),
          styles['auction-page'],
          'flex-container-col',
        )}
      >
        {userType === USER_TYPE.아이들 && (
          <Button
            className={convertClassNameList(
              convertClassName(className, styles),
              'self-end m-1',
              'primary xsmall',
            )}
            label="등록하기"
            onClick={() => navigate(`/create`, { state: 'auction' })}
          />
        )}
        <div className={styles['auction-page-card-container']}>
          {auctions?.map((auction) => (
            <AuctionCard
              key={auction.auctionNo}
              {...auction}
              onClick={() =>
                handleMoveAuctionDetail(auction.auctionNo, auction)
              }
            />
          ))}
        </div>
      </div>
    </>
  );
};

export default AuctionPage;
