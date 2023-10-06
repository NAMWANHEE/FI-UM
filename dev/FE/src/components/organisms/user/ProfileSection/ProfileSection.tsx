import { convertClassName, convertClassNameList } from '@/utils';
import styles from './ProfileSection.module.scss';
import { Text } from '@/components/atoms';

interface ProfileSectionProps {
  className?: string;
  label: string;
  children?: JSX.Element | JSX.Element[];
}

const ProfileSection = ({
  className,
  label,
  children,
}: ProfileSectionProps): JSX.Element => {
  return (
    <div
      className={convertClassNameList(
        convertClassName(className, styles),
        styles['profile-section'],
      )}
    >
      <Text className={convertClassNameList(
        styles['profile-section__text'],
        "text-xl"
      )} text={label} />
      {children ?? 'empty'}
    </div>
  );
};

export default ProfileSection;
