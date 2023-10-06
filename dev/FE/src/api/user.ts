import {
  ArtistAuction,
  Capital,
  ChildProfile,
  MyDeposit,
  SponsorProfile,
  TotalCapital,
  UserDetail,
  UserInfo,
} from '@/types';
import { QueryKey } from '@tanstack/react-query';
import { api, authApi } from '.';
import { HTTP_STATUS } from '@/constants';
import { getRefreshToken, removeTokens, setAccessToken } from '@/utils';

// 아이디 중복 확인
const getUserCheckId = async ({
  queryKey: [_, userId],
}: {
  queryKey: QueryKey;
}): Promise<string> => {
  return await api.get(`user/check-id?userId=${userId}`);
};

// 예술가 작품 전체 조회
const getUserArtist = async ({
  queryKey: [_, userNo],
}: {
  queryKey: QueryKey;
}): Promise<ArtistAuction[]> => {
  return await authApi
    .get(`user/artist/${userNo}`)
    .then(({ data }: { data: ArtistAuction[] }) =>
      data.sort((a, b) => {
        if (a.winner && b.winner) {
          return a.auctionNo - b.auctionNo;
        } else {
          return b.winner ? -1 : 1;
        }
      }),
    );
};

// 개인정보 조회
const getUser = async (): Promise<SponsorProfile | ChildProfile> => {
  return await authApi.get(`user`).then(({ data }) => data);
};

// 로그인 유저 정보 갱신
const getUserInfo = async (): Promise<UserInfo> => {
  return await authApi.get(`user/user-info`).then(({ data }) => data);
};

// refresh token
const getreissue = async (refreshToken?: string | null): Promise<string> => {
  return await api
    .get(`user/reissue`, {
      headers: {
        'X-REFRESH-TOKEN': refreshToken ?? getRefreshToken(),
      },
    })
    .then(({ data }) => data)
    .then(({ token }) => {
      setAccessToken(token);
      return token;
    })
    .catch((error) => {
      // 재 로그인 필요
      if (
        error.response.status === HTTP_STATUS.FORBIDDEN ||
        error.response.status === HTTP_STATUS.UNAUTHORIZED
      ) {
        removeTokens();
        alert('로그인 만료!!');
        window.location.href = '/login';
      }
      console.error('refresh token error : ', error);
      return Promise.reject(error);
    });
};

// 전체 아이들의 자산 현황
const getUserTotalCapital = async (): Promise<TotalCapital[]> => {
  return await authApi.get(`user/total-capital`).then(({ data }) => data);
};

// 특정 아이의 재무 상태표
const getUserCapital = async ({
  queryKey: [_, userNo],
}: any): Promise<Capital> => {
  return await authApi.get(`user/capital/${userNo}`).then(({ data }) => data);
};

// 자신의 현재 예적금 조회
const getUserDepositSaving = async (): Promise<MyDeposit[]> => {
  return await authApi.get(`user/deposit-saving`).then(({ data }) => data);
};

// 회원가입
const userSignup = async (userDetail: UserDetail) => {
  return await api.post(`user/signup`, userDetail);
};

// 로그인
const userLogin = async (userDetail: UserDetail) => {
  return await api.post('user/login', userDetail).then(({ data }) => {
    const { accessToken, refreshToken } = data.tokenResponse;
    sessionStorage.setItem('accessToken', accessToken);
    sessionStorage.setItem('refreshToken', refreshToken);
    return data;
  });
};

// 로그아웃
const userLogout = async (): Promise<string> => {
  return await authApi
    .post('user/logout')
    .then(({ data }) => data)
    .catch((err) => {
      console.error(err);
    })
    .finally(() => {
      removeTokens();
      alert('로그아웃 성공!!');
    });
};

// 프로필 사진 수정
const putUserProfileImage = async (imagePath: string) => {
  return await authApi.put('user/profile-image', { imagePath });
};

// 라이벌 등록
const postUserRival = async (userNo: number) => {
  return await authApi
    .post(`user/rival`, { userNo })
    .then(({ data }) => {
      alert(data?.msg);
      return data;
    })
    .catch(async (error) => {
      if (error?.response?.status === HTTP_STATUS.FORBIDDEN) {
        await putUserRival(userNo).then(({ data }) => Promise.resolve(data));
      } else {
        alert(error.response.msg);
      }
    });
};
// 라이벌 수정
const putUserRival = async (userNo: number) => {
  return await authApi
    .put(`user/rival`, { userNo })
    .then(({ data }) => {
      alert(data?.msg);
      return data;
    })
    .catch((error) => {
      alert(error.response.msg);
    });
};
// 라이벌 삭제
const deleteUserRival = async () => {
  return await authApi.delete(`user/rival`).then(({ data }) => {
    alert(data?.msg);
    return data;
  });
};

export {
  getUserCheckId,
  getUserArtist,
  getUser,
  getUserTotalCapital,
  userSignup,
  userLogin,
  getreissue,
  getUserCapital,
  userLogout,
  getUserDepositSaving,
  putUserProfileImage,
  postUserRival,
  putUserRival,
  deleteUserRival,
  getUserInfo,
};
