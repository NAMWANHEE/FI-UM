import { Quiz } from '@/types';
import { authApi } from '.';

const getQuiz = async () => {
  return await authApi.get('quiz').then(({ data }) => data);
};

const postSubmitQuiz = async (quiz: Quiz[]) => {
  return await authApi.post('quiz', quiz).then(({ data }) => data);
};

export { getQuiz, postSubmitQuiz };
