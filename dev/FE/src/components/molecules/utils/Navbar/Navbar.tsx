import { convertClassName, convertClassNameList, imgUrl } from '@/utils';
import styles from './Navbar.module.scss';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import useAuth from '@/hooks/useAuth';
import { USER_TYPE } from '@/constants';
import { useMemo, useCallback, MouseEvent } from 'react';
import { useMutation } from '@tanstack/react-query';
import { Button, Text } from '@/components/atoms';
import { userLogout } from '@/api/user';

interface NavbarProps {
  className?: string;
}

const Navbar = ({ className }: NavbarProps): JSX.Element => {
  const { userInfo } = useAuth();
  const navigate = useNavigate();
  const mutation = useMutation(userLogout, {
    onSuccess() {
      navigate('/login');
      sessionStorage.removeItem('user');
    },
  });

  const handleLogout = useCallback((e: MouseEvent<HTMLElement>) => {
    e.preventDefault();
    mutation.mutate();
  }, []);

  const location = useLocation();
  console.log(location);
  const openPayment = () => {
    window.open('/tosspay', '_blank', 'width=800, height=800');
  };

  const links = useMemo(() => {
    if (!userInfo)
      return (
        <>
          <Link
            key="login"
            className={convertClassNameList(
              styles['navbar__menu--item'],
              styles.login,
            )}
            to={'/login'}
          >
            로그인
          </Link>
          <Link
            key="signup"
            className={convertClassNameList(
              styles['navbar__menu--item'],
              styles.signup,
            )}
            to={'/signup'}
          >
            회원가입
          </Link>
        </>
      );

    let links = [
      <Link
        key="stock"
        className={convertClassNameList(styles['navbar__menu--item'])}
        to={'/stock'}
      >
        <div className="flex-container-col align-center">
          <img
            style={{ width: '30px', height: '30px' }}
            src={imgUrl('navbaricon/stock.png')}
            alt=""
          />
          <Text className="text-lg" text="주식" />
        </div>
      </Link>,

      <Link
        key="auction"
        className={convertClassNameList(styles['navbar__menu--item'])}
        to={'/auction'}
      >
        <div className="flex-container-col align-center">
          <img
            style={{ width: '30px', height: '30px' }}
            src={imgUrl('navbaricon/auction.png')}
            alt=""
          />
          <Text className="text-lg" text="경매" />
        </div>
      </Link>,
    ];
    switch (userInfo.userType) {
      case USER_TYPE.아이들:
        links.push(
          <Link
            key="deposit"
            className={convertClassNameList(styles['navbar__menu--item'])}
            to={'/deposit'}
          >
            <div className="flex-container-col align-center">
              <img
                style={{ width: '30px', height: '30px' }}
                src={imgUrl('navbaricon/deposit.png')}
                alt=""
              />
              <Text className="text-lg" text="자산" />
            </div>
          </Link>,
        );
        break;
    }
    links.push(
      ...[
        <Link
          key="funding"
          className={convertClassNameList(styles['navbar__menu--item'])}
          to={'/funding'}
        >
          <div className="flex-container-col align-center">
            <img
              style={{ width: '30px', height: '30px' }}
              src={imgUrl('navbaricon/funding.png')}
              alt=""
            />
            <Text className="text-lg" text="펀딩" />
          </div>
        </Link>,
        <Link
          key="gallery"
          className={convertClassNameList(styles['navbar__menu--item'])}
          to={'/gallery'}
        >
          <div className="flex-container-col align-center">
            <img
              style={{ width: '30px', height: '30px' }}
              src={imgUrl('navbaricon/gallery.png')}
              alt=""
            />
            <Text className="text-lg" text="갤러리" />
          </div>
        </Link>,
        <Button
          key="payment"
          className={convertClassNameList(
            styles['navbar__menu--item'],
            styles['navbar__menu--payment'],
          )}
          label="충전"
          onClick={openPayment}
        />,
        <Link
          key="profile"
          className={convertClassNameList(
            styles['navbar__menu--item'],
            styles.login,
          )}
          to={`/profile/${userInfo.userNo}`}
        >
          프로필
        </Link>,
        <Link
          key="logout"
          className={convertClassNameList(
            styles['navbar__menu--item'],
            styles.logout,
          )}
          to={''}
          onClick={handleLogout}
        >
          로그아웃
        </Link>,
      ],
    );
    return links;
  }, [userInfo]);
  return (
    <div
      className={convertClassNameList(
        convertClassName(className, styles),
        styles['navbar'],
        'container jc-space-between',
      )}
    >
      <Link className={convertClassNameList(styles['navbar__logo'])} to={'/'}>
        <img
          style={{ width: '100%', height: '100%' }}
          src="/lotus.png"
          alt="logo"
        />
      </Link>
      <div className={convertClassNameList(styles['navbar__menu'])}>
        <Link
          className={convertClassNameList(styles['navbar__menu--item'])}
          to={'/'}
        >
          <div className="flex-container-col align-center">
            <img
              style={{ width: '30px', height: '30px' }}
              src={imgUrl('navbaricon/home.png')}
              alt=""
            />
            <Text className="text-lg" text="Home" />
          </div>
        </Link>

        {links}
      </div>
    </div>
  );
};

export default Navbar;
