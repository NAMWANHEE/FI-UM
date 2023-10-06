interface Auction extends ArtistAuction {
  itemImagePath: string;
  auctionPrice: number;
  content: string;
  createdTime: number;
  instantPrice: number;
  likeCount: number;
  name: string;
  userImagePath: string | null;
  userNo: number;
  viewCount: number;
}

interface Purchase {
  auctionNo: number;
  imagePath: string;
  title: string;
}

interface NewAuction {
  title: string;
  instantPrice: number;
  content: string;
  imagePath: string;
}

interface ArtistAuction {
  title: string;
  imagePath: string;
  auctionNo: number;
  winner: number | null;
}

export type { Auction, Purchase, NewAuction, ArtistAuction };
