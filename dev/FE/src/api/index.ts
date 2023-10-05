import axios, { AxiosInstance } from 'axios';
import { getreissue } from './user';
import { HTTP_STATUS } from '@/constants';
import { getAccessToken, getRefreshToken } from '@/utils';

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL;
const API_PORTFOLIO_URL = import.meta.env.VITE_API_PORTFOLIO_URL;
let isRefresh = false;

const apiInstance = () => {
  const instance = axios.create({
    baseURL: API_BASE_URL,
    headers: {
      'Access-Control-Allow-Origin': '*',
      'Content-Type': 'application/json;charset=UTF-8',
      Accept: 'application/json,',
    },
  });
  return instance;
};

const portfolioApiInstance = () => {
  const instance = axios.create({
    baseURL: API_PORTFOLIO_URL,
    headers: {
      'Access-Control-Allow-Origin': '*',
      'Content-Type': 'application/json;charset=UTF-8',
      Accept: 'application/json,',
    },
  });
  return instance;
};

const formApiInstance = () => {
  const instance = axios.create({
    baseURL: API_BASE_URL,
    headers: {
      'Access-Control-Allow-Origin': '*',
      'Content-Type': 'multipart/form-data;charset=UTF-8',
      Accept: 'application/json,',
    },
  });
  return instance;
};

const authInterceptor = (instance: AxiosInstance) => {
  instance.interceptors.request.use(
    (config) => {
      const accessToken = getAccessToken();
      config.headers['X-ACCESS-TOKEN'] = accessToken;
      return config;
    },
    (error) => {
      console.error('request error : ', error);
      return Promise.reject(error);
    },
  );

  instance.interceptors.response.use(
    (response) => {
      return response;
    },
    async (error) => {
      if (error.response) {
        switch (error.response.status) {
          // 권한 없음
          case HTTP_STATUS.UNAUTHORIZED:
            const refreshToken = await getRefreshToken();
            // access token 재 발급
            if (!isRefresh) {
              // refresh 요청 한 번만
              isRefresh = true;
              getreissue(refreshToken).finally(() => (isRefresh = false));
            }
            break;
        }

        console.error('response error : ', error);
        return Promise.reject(error);
      }
      console.error('response error : ', error);
      return Promise.reject(error);
    },
  );

  return instance;
};

const authApiInstance = () => {
  return authInterceptor(apiInstance());
};

const authFormInstance = () => {
  return authInterceptor(formApiInstance());
};

const api = apiInstance();
const portfolioApi = portfolioApiInstance();
const formApi = formApiInstance();
const authApi = authApiInstance();
const authFormApi = authFormInstance();

export { formApi, authFormApi, api, authApi, portfolioApi };
