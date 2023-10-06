import { Ranking } from '@/types';
import { api } from '.';

// 랭킹왕 (권한 x)
const getRankings = async (): Promise<Ranking[]> => {
  return await api.get(`ranking`).then(({ data }) => data);
};

export { getRankings };
