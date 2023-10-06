import { convertClassName, convertClassNameList } from '@/utils';
import styles from './FundingInfo.module.scss';
import { Image, Text } from '@/components/atoms';

interface FundingInfoProps {
  className?: string;
}

const FundingInfo = ({ className }: FundingInfoProps): JSX.Element => {
  return (
    <div
      className={convertClassNameList(
        convertClassName(className, styles),
        styles['funding-info'],
        'bg-gray-light',
      )}
    >
      <div>
        <Image src="" alt="" />
      </div>
      <Text className="text-xxl" text="제목" />
    </div>
  );
};

export default FundingInfo;
