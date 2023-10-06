import { Quiz } from '@/types';
import { getQuiz, postSubmitQuiz } from '../quiz';

const getQuizQuery = () => {
  return {
    queryKey: ['getQuiz'],
    queryFn: getQuiz,
  };
};

const postSubmitQuizQuery = (answer: Quiz[]) => {
  return {
    mutaionFn: () => postSubmitQuiz(answer),
  };
};

export { getQuizQuery, postSubmitQuizQuery };
