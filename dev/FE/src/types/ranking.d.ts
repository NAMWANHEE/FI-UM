interface Ranking {
  type: '펀딩' | '기부' | '주식' | '판매';
  no1No: number;
  no1: string;
  no1ImagePath: string;
  no2No: number;
  no2: string;
  no2ImagePath: string;
  no3No: number;
  no3: string;
  no3ImagePath: string;
}

export type { Ranking };
