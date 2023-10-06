import { getImage, postImage } from '../image';

// 그림 조회
const getImageQuery = (imageUrl: string) => {
  return {
    queryKey: ['getImage', imageUrl],
    queryFn: () => getImage(imageUrl),
  };
};

// 그림 등록
const postImageQuery = (image: FormData) => {
  return {
    mutationFn: () => postImage(image),
  };
};

export { getImageQuery, postImageQuery };
