import { convertClassName, convertClassNameList } from '@/utils';
import styles from './Image.module.scss';
import { apiImgUrl } from '@/utils/imgUrl';

interface ImageProps {
  className?: string;
  src?: string;
  alt?: string;
}

const initImageUrl: string = '/vite.svg';

const Image = ({ className, src, alt }: ImageProps) => {
  return (
    <>
      <img
        className={convertClassNameList(
          convertClassName(className, styles),
          styles['image'],
        )}
        src={apiImgUrl(src) || initImageUrl}
        alt={alt}
        data-testid="image"
      />
    </>
  );
};

export default Image;
