import { getQuizQuery, postSubmitQuizQuery } from '@/api/queries';
import { Quiz } from '@/types';
import { useMutation, useQuery } from '@tanstack/react-query';
import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import styles from './QuizPage.module.scss';
import useAuth from '@/hooks/useAuth';
import { convertClassNameList } from '@/utils';

const QuizPage = () => {
  const userInfo = useAuth().userInfo;
  const navigate = useNavigate();
  //   useEffect(() => {
  //     if (userInfo?.quiz) {
  //       alert('퀴즈는 하루에 한번만 할 수 있습니다.');
  //       navigate('/education');
  //     }
  //   }, [userInfo]);
  const [popUpWindow, setPopUpWindow] = useState<boolean>(false);
  const [popUpContent, setPopUpContent] = useState<string>('정답 입니다.');

  const [answer, setAnswer] = useState<Quiz[]>([
    {
      quizNo: 0,
      quiz: '',
      answer: true,
    },
  ]);
  const [quizNumber, setQuizNumber] = useState<number>(0);
  const [correctAnswer, setCorrectAnswer] = useState<number>(0);
  const { data: quizList, status: isQuizListLoading } = useQuery(
    getQuizQuery(),
  );

  useEffect(() => {
    if (isQuizListLoading === 'success' && answer[0].quiz === '') {
      setAnswer(quizList);
    }
  }, [quizList]);

  const checkAnswer = (idx: number, submit: boolean): boolean => {
    // console.log(submit, quizList[idx], '확인');
    // console.log(quizList, '리스트');
    console.log(answer, '정답지');
    console.log(submit === answer[idx].answer, '???');
    if (answer[idx].answer === submit) {
      setCorrectAnswer(correctAnswer + 1);
      setPopUpContent('정답입니다!');

      openPopUp();
      return true;
    } else {
      setPopUpContent('틀렸습니다.');
      openPopUp();
    }
    return false;
  };

  const openPopUp = () => {
    setPopUpWindow(true);
    setTimeout(() => {
      setPopUpWindow(false);
      setQuizNumber(quizNumber + 1);
    }, 2000);
  };

  const submitMutation = useMutation(
    postSubmitQuizQuery(answer as Quiz[]).mutaionFn,
    {
      onSuccess: (res) => {
        if (res.msg === '합격') {
          alert(`10문제중 ${correctAnswer}개를 맞춰 퀴즈에 통과하였습니다.`);
        } else {
          alert(
            `10문제중 ${correctAnswer}개를 맞춰 퀴즈에 통과하지 못하였습니다.`,
          );
        }
        navigate('/education');
      },
      onError: () => {},
    },
  );

  const handleSubmitButton = (submit: boolean) => {
    setAnswer((prevAnswer) => {
      const updatedAnswer = [...prevAnswer];
      updatedAnswer[quizNumber].answer = checkAnswer(quizNumber, submit);
      return updatedAnswer;
    });
    if (quizNumber === 9) {
      submitMutation.mutate();
    }
  };

  return (
    <>
      {isQuizListLoading !== 'success' ? (
        <>문제를 불러오는 중입니다.</>
      ) : (
        <>
          {popUpWindow && (
            <div className={styles.popUpWindow}>
              <div>{popUpContent}</div>
            </div>
          )}
          <div className={styles.quizPageWrapper}>
            <div className={styles.titleContentWrapper}>
              <div className={styles.quizTitle}>
                <div>{quizNumber + 1}번 문제</div>
              </div>
              <div className={styles.quizContent}>
                {answer[quizNumber].quiz}
              </div>
            </div>
            <div className={styles.buttonWrapper}>
              <button
                className={convertClassNameList(
                  styles.button,
                  styles.button__true,
                )}
                onClick={() => handleSubmitButton(true)}
              ></button>
              <button
                className={convertClassNameList(
                  styles.button,
                  styles.button__false,
                )}
                onClick={() => handleSubmitButton(false)}
              ></button>
            </div>
            <div></div>
          </div>
        </>
      )}
    </>
  );
};

export default QuizPage;
