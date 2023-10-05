import { useQuery } from '@tanstack/react-query';
import styles from './MainPage.module.scss';
import { Funding, MainAuction, Ranking } from '@/components/organisms';
import { Swiper } from '@/components/molecules';
import { getAuctionsQuery } from '@/api/queries/auction';
import {
  Auction,
  Funding as FundingType,
  Ranking as RankingType,
} from '@/types';
import { getFundingsQuery, getRankingsQuery } from '@/api/queries';
import { useEffect, useState } from 'react';
import { Text } from '@/components/atoms';

const MainPage = () => {
  const { data: auctions } = useQuery<Auction[], Error>(getAuctionsQuery());
  const { data: fundings } = useQuery<FundingType[]>(getFundingsQuery());
  const { data: rankings } = useQuery<RankingType[]>(getRankingsQuery());

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
            src="./img/loading/home.gif"
          />
        </div>
      )}
      <div className={styles['main-page']}>
        <MainAuction auctions={auctions} />
        <div className={styles['main-page__ranking']}>
          {rankings && (
            <Swiper>
              {rankings.map((ranking) => (
                <Ranking key={ranking.type} ranking={ranking} />
              ))}
            </Swiper>
          )}
        </div>
        <div>
          <Text className="text-xl bold m-1" text="진행중인 펀딩" />
          <Funding fundings={fundings} />
        </div>
      </div>
    </>
  );
};

export default MainPage;
