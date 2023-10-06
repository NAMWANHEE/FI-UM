import styles from '@/components/atoms/Text/Text.module.scss';
import { convertClassName } from '@/utils';

interface TextProps {
  className?: string;
  text?: string | number;
}

const Text = ({ text, className }: TextProps) => {
  return (
    <div className={convertClassName(className, styles)} data-testid="text">
      {text}
    </div>
  );
};

export default Text;
