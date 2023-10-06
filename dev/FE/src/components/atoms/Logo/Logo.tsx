import { convertClassName } from '@/utils';
import styles from '@/components/atoms/Logo/Logo.module.scss';

interface LogoProps {
  src: string;
  alt: string;
  className?: string;
}

const Logo = ({ src, alt, className }: LogoProps) => {
  return (
    <>
      <img
        src={src}
        alt={alt}
        className={convertClassName(className, styles) + ` ${styles.logo}`}
      />
    </>
  );
};

export default Logo;
