import { ProfileCard } from '@/components/molecules';
import styles from './Ranking.module.scss';
import { convertClassName, convertClassNameList } from '@/utils';
import { Ranking as RankingType } from '@/types';
import { useMemo } from 'react';
import { Text } from '@/components/atoms';

interface RankingProps {
  className?: string;
  ranking: RankingType;
}
const Ranking = ({ className, ranking }: RankingProps): JSX.Element => {
  const data = useMemo(
    () => [
      { id: ranking.no1No, name: ranking.no1, src: ranking.no1ImagePath },
      { id: ranking.no2No, name: ranking.no2, src: ranking.no2ImagePath },
      { id: ranking.no3No, name: ranking.no3, src: ranking.no3ImagePath },
    ],
    [ranking],
  );
  return (
    <div
      className={convertClassNameList(
        convertClassName(className, styles),
        styles.ranking,
      )}
    >
      <Text className={styles['ranking__title']} text={ranking.type} />
      <div className={styles['ranking__main']}>
        {data.map((item, index) => {
          return (
            <div key={item.id} className={styles[`profileCard${index}`]}>
              {index === 1 ? (
                <img src="/img/crown.svg" alt="" className={styles.crown} />
              ) : (
                ''
              )}
              <ProfileCard key={item.id} />
            </div>
          );
        })}
      </div>
    </div>
  );
};

export default Ranking;
