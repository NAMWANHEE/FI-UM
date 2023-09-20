import { authFormApi, formApi } from '.';

const getImage = async (imageUrl: string) => {
  return await formApi.get(imageUrl).then(({ data }) => data);
};
const postImage = async (image: FormData): Promise<string> => {
  return await authFormApi.post(`image`, image).then(({ data }) => data);
};

export { getImage, postImage };
