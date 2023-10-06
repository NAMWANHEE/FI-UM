import { Funding } from '.';

interface Item extends Funding {
  // itemNo: number;
  // itemName: string;
  // imagePath: string;
  // itemCount: number;
  // fundingAmount: number;
  unitPrice: number;
  description: string;
  sponsorshipAmount: number;
  isCompleted: boolean;
  bestFunding: string;
}

interface SponsorshipDetail {
  userName: string;
  itemName: string;
  price: number;
}

interface NewItem {
  itemNo?: number;
  name: string;
  unitPrice: number;
  count: number;
  description: string;
  imagePath: string;
}

export type { Item, SponsorshipDetail, NewItem };
