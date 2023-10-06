import { ChildProfile, UserInfo } from '@/types';
import { atom } from 'recoil';

const userState = atom<UserInfo | undefined>({
  key: 'userState',
  default: undefined,
});

const followingState = atom<ChildProfile[] | undefined>({
  key: 'followingState',
  default: undefined,
});

export { userState, followingState };
