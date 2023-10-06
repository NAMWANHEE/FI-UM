import { Point } from '@/types';
import { getPoints } from '../point';

// 포인트 사용 내역 조회
const getPointsQuery = () => {
  return {
    queryKey: ['getPoints'],
    queryFn: getPoints,
    select: (data: Point[]) =>
      data.sort((a, b) => b.changedTime - a.changedTime),
  };
};

export { getPointsQuery };
