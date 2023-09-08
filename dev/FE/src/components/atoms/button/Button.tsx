import styles from '@/components/atoms/button/Button.module.scss';

interface ButtonProps {
  label: string;
  size?: 'small' | 'normal' | 'large';
  bgColor?: 'gray' | 'primary';
  onClick?: () => void;
}

const Button = ({ label, size, bgColor, onClick }: ButtonProps) => {
  const className: string[] = [
    styles[size ?? 'normal'],
    styles[bgColor ?? 'primary'],
  ];
  return (
    <button className={className.join(' ')} onClick={onClick}>
      {label}
    </button>
  );
};

export default Button;
