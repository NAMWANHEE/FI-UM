import { convertClassName, convertClassNameList } from '@/utils';
import styles from './CreatorProfile.module.scss';
import { ProfileCard } from '@/components/molecules';
import { Image, Text } from '@/components/atoms';
import { Art } from '@/types';
import { useLocation } from 'react-router-dom';

interface CreatorProfileProps {
  className?: string;
  arts?: Art[];
}

const CreatorProfile = ({
  className,
  arts,
}: CreatorProfileProps): JSX.Element => {
  const { state } = useLocation();

  return (
    <div
      className={convertClassNameList(
        convertClassName(className, styles),
        styles['creator-profile'],
      )}
    >
      <ProfileCard myPage={false} src={state.userImagePath} alt={state.name} />
      <div>
        <Text className="text-lg" text="이 작가의 다른 작품" />
        <div className={styles['creator-profile_arts']}>
          {arts?.map((art) => (
            <div key={art.imagePath} className={styles['creator-profile_art']}>
              <Image
                src={art.imagePath}
                className={styles['creator-profile_art__image']}
              />
              <div className="flex-container">
                <Text text={`${art.title}`} />
                {art.winner && <Text className="bold" text="(판매 완료)" />}
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};

export default CreatorProfile;
