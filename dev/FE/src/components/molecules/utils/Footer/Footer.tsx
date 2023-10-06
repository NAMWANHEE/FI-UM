import { convertClassName, convertClassNameList } from '@/utils';
import styles from './Footer.module.scss';
import { Text } from '@/components/atoms';

interface FooterProps {
  className?: string;
}

const Footer = ({ className }: FooterProps): JSX.Element => {
  return (
    <div
      className={convertClassNameList(
        convertClassName(className, styles),
        styles.footer,
        'container',
      )}
    >
      <Text text="천사 투자단" />
      <Text
        className="text-sm gray"
        text="천사의 마음으로 아름다운 세상을 만듭니다."
      />
    </div>
  );
};

export default Footer;
