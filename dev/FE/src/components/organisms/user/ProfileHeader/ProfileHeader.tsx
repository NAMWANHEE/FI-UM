import {
  checkConditionClassName,
  convertClassName,
  convertClassNameList,
} from '@/utils';
import styles from './ProfileHeader.module.scss';
import {
  ProfileAdmin,
  ProfileAsset,
  ProfileCard,
  ProfileDonator,
} from '@/components/molecules';
import { UserInfo } from '@/types';
import { USER_TYPE } from '@/constants';

interface ProfileHeaderProps {
  className?: string;
  userInfo?: UserInfo;
  myPage: boolean;
}

const ProfileHeader = ({
  className,
  userInfo,
  myPage,
}: ProfileHeaderProps): JSX.Element => {
  return (
    <div
      className={convertClassNameList(
        convertClassName(className, styles),
        styles['profile-header'],
        checkConditionClassName(
          !myPage,
          styles['profile-header__not-mypage'],
        ),
      )}
    >
        <ProfileCard
          myPage={myPage}
          src={userInfo?.imagePath}
          alt={userInfo?.userName}
          userNo={userInfo?.userNo}
        />
        {myPage &&
          (userInfo?.userType === USER_TYPE.아이들 ? (
            <ProfileAsset userNo={userInfo.userNo} rival={userInfo.rival} />
          ) : userInfo?.userType === USER_TYPE.원장쌤 ? (
            <ProfileAdmin />
          ) : userInfo?.userType === USER_TYPE.후원자 ? (
            <ProfileDonator />
          ) : (
            ''
          ))}
      </div>
  );
};

export default ProfileHeader;
