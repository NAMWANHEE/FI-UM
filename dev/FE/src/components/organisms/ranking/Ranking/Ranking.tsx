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
      {
        type: ranking.type,
        id: ranking.no2No,
        name: ranking.no2,
        src: ranking.no2ImagePath,
      },
      {
        type: ranking.type,
        id: ranking.no1No,
        name: ranking.no1,
        src: ranking.no1ImagePath,
      },
      {
        type: ranking.type,
        id: ranking.no3No,
        name: ranking.no3,
        src: ranking.no3ImagePath,
      },
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
      <Text
        className={styles['ranking__title']}
        text={ranking.type + ' 랭킹'}
      />
      <div className={styles['ranking__main']}>
        {data.map(({ id, name, src, type }, index) => {
          console.log(`profileCard__${index}`, '???');
          return (
            <div
              key={src + type}
              className={convertClassNameList(
                styles[`profileCard__${index}`],
                'flex-container-col',
              )}
            >
              {index === 1 ? (
                <div className={styles.crownWrapper}>
                  <img src="/img/crown.svg" alt="" className={styles.crown} />
                </div>
              ) : (
                <div className={styles.crownWrapper}></div>
              )}
              <ProfileCard key={id} src={src} alt={name} userNo={id} />
            </div>
          );
        })}
      </div>
    </div>
  );
};

export default Ranking;
