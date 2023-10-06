type UseType = '주식' | '예금' | '펀딩';

interface PointRecord {
  useType: UseType;
  pointChange: number;
  changedTime: number;
}

interface Point extends PointRecord {
  //   useType: UseType;
  //   pointChange : number,
  changedTime: number;
}

export type { PointRecord, Point, UseType };
