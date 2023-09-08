import styles from '@/components/atoms/button/Button.module.scss';
import { convertClassName } from '@/utils';

interface ButtonProps {
  label: string;
  className?: string;
  onClick?: () => void;
}

const Button = ({ label, className, onClick }: ButtonProps) => {
  return (
    <button className={convertClassName(className, styles)} onClick={onClick}>
      {label}
    </button>
  );
};

export default Button;
