import { authFormApi, api } from '.';

const getImage = async (imageUrl: string): Promise<string> => {
  return await api.get(imageUrl).then(({ data }) => data);
};
const postImage = async (image: FormData): Promise<string> => {
  return await authFormApi.post(`image`, image).then(({ data }) => data);
};

export { getImage, postImage };
