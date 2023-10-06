import { useMemo, useState } from 'react';
import {
  checkConditionClassName,
  convertClassName,
  convertClassNameList,
} from '@/utils';
import styles from './Swiper.module.scss';
import { Swiper, SwiperSlide } from 'swiper/react';
import type { Swiper as Swipertype } from 'swiper';
import {
  Mousewheel,
  Autoplay,
  Keyboard,
  FreeMode,
  Thumbs,
  Pagination,
  Navigation,
} from 'swiper/modules';
import 'swiper/css';
import 'swiper/css/pagination';
import 'swiper/css/navigation';
import 'swiper/css/free-mode';
import 'swiper/css/thumbs';

interface SwiperProps {
  className?: string;
  children?: JSX.Element[];
  type?: 'autoplay' | 'thumbsLoop';
}

const CustomSwiper = ({
  className,
  type,
  children,
}: SwiperProps): JSX.Element => {
  const [thumbsSwiper, setThumbsSwiper] = useState<Swipertype | null>(null);

  const options = useMemo(() => {
    switch (type) {
      case 'autoplay':
        return {
          spaceBetween: 30,
          centeredSlides: true,
          autoplay: {
            delay: 5000,
            disableOnInteraction: false,
          },
          keyboard: {
            enabled: true,
          },
          pagination: {
            clickable: true,
          },
          navigation: true,

          modules: [Autoplay, Keyboard, Pagination, Navigation],
        };
      case 'thumbsLoop':
        return {
          onSwiper: setThumbsSwiper,
          loop: true,
          spaceBetween: 10,
          navigation: true,
          thumbs: { swiper: thumbsSwiper },
          modules: [FreeMode, Navigation, Thumbs],
        };
      default:
        return {
          slidesPerView: 1,
          spaceBetween: 30,
          keyboard: {
            enabled: true,
          },
          mousewheel: false,
          pagination: {
            clickable: true,
          },
          navigation: true,
          modules: [Mousewheel, Keyboard, Pagination, Navigation],
        };
    }
  }, [type]);

  return (
    <>
      <Swiper
        className={convertClassNameList(
          convertClassName(className, styles),
          checkConditionClassName(type !== 'thumbsLoop', styles['swiper']),
          checkConditionClassName(
            type === 'thumbsLoop',
            styles['swiper__thumbs--main'],
          ),
        )}
        {...options}
      >
        {children?.map((child, index) => (
          <SwiperSlide
            key={index}
            className={convertClassNameList(styles['swiper-slide'])}
          >
            {child}
          </SwiperSlide>
        ))}
      </Swiper>
      {type === 'thumbsLoop' && (
        <Swiper
          className={convertClassNameList(styles['swiper__thumbs--side'])}
          onSwiper={setThumbsSwiper}
          loop={true}
          spaceBetween={10}
          slidesPerView={4}
          freeMode={true}
          watchSlidesProgress={true}
          centeredSlides={true}
          modules={[FreeMode, Navigation, Thumbs]}
        >
          {children?.map((child, index) => (
            <SwiperSlide
              key={index}
              className={convertClassNameList(
                styles['swiper__thumbs--side-slide'],
              )}
            >
              {child}
            </SwiperSlide>
          ))}
        </Swiper>
      )}
    </>
  );
};

export default CustomSwiper;
