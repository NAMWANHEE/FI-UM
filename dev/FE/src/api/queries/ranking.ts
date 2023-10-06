import { getRankings } from '../ranking';

// 랭킹왕 (권한 x)
const getRankingsQuery = () => {
  return { queryKey: ['getRankings'], queryFn: getRankings };
};

export { getRankingsQuery };
