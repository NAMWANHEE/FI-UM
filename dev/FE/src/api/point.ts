import { Point } from '@/types';
import { authApi } from '.';

// 포인트 사용 내역 조회
const getPoints = async (): Promise<Point[]> => {
  return await authApi.get(`point`).then(({ data }) => data);
};

export { getPoints };
