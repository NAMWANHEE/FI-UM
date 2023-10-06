import { getUserInfo } from '@/api/user';
import { userState } from '@/store';
import { UserInfo } from '@/types';
import { getAccessToken, getRefreshToken } from '@/utils';
import { useCallback, useEffect } from 'react';
import { useRecoilState } from 'recoil';

let isRefresh = false;

const useAuth = () => {
  const [userInfo, setUserInfo] = useRecoilState<UserInfo | undefined>(
    userState,
  );
  const refreshUserInfo = useCallback(async () => {
    const newUserInfo = await getUserInfo();
    setUserInfo(newUserInfo);
  }, []);

  const accessToken = getAccessToken();
  const refreshToken = getRefreshToken();

  useEffect(() => {
    if (!userInfo && accessToken && !isRefresh) {
      isRefresh = true;
      refreshUserInfo().then(() => {
        isRefresh = false;
      });
    }
  }, []);

  useEffect(() => {
    if (!refreshToken) {
      setUserInfo(undefined);
    }
  }, [refreshToken]);

  return { userInfo, setUserInfo, refreshUserInfo };
};

export default useAuth;
