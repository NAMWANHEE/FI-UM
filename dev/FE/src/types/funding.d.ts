interface Funding {
  itemNo: string;
  itemName: string;
  imagePath: string;
  itemUnitPrice: number;
  itemCount: number;
  fundingAmount: number;
  description: string;
  unitPrice: number;
  sponsorshipAmount: number;
  fundingRanking: FundingRanking[];
  isCompleted: boolean;
}

interface FundingProgress {
  totalFundingPrice: number;
  fundingPrice: number;
}

interface FundingRecord {
  userName: string;
  itemName: string;
  price: number;
}

interface FundingRanking {
  userName: string;
  userFundingAmount: number;
}

export type { Funding, FundingProgress, FundingRecord, FundingRanking };
