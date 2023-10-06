import { UserDetail } from '@/types';

const user: UserDetail = {
  userId: '아이디',
  userName: '이름',
  password: '비밀번호',
  phoneNumber: '전화번호',
  password2: '비밀번호 확인',
};

const convertUser = (key: keyof UserDetail): string => {
  return user[key];
};

export default convertUser;
