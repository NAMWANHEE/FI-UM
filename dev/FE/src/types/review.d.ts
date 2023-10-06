interface Review extends NewReview {
  reviewNo: number;
  // userNo: number;
  createTime: string;
}

interface NewReview {
  title: string;
  content: string;
  imagePath: string;
}

export { NewReview, Review };
