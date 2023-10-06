import { convertClassName, convertClassNameList, eduBook } from '@/utils';
import styles from './EducationPage.module.scss';
import { Button, Text } from '@/components/atoms';
import { useCallback, useReducer, useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import useAuth from '@/hooks/useAuth';

interface EducationPageProps {
  className?: string;
}

interface ActionType {
  type: string;
  pageNo?: number;
}

const eduBooks = eduBook();

const reducer = (state: number, action: ActionType): number => {
  switch (action.type) {
    case 'INCREMENT':
      return state + 1;
    case 'DECREMENT':
      return Math.max(state - 1, 0);
    case 'CHANGE':
      return action?.pageNo ?? state;
    default:
      return state;
  }
};

interface Index {
  titleNo: number;
  pageNo: number;
  subjectNo: number;
}

class IndexImpl implements Index {
  titleNo: number;
  pageNo: number;
  subjectNo: number;
  constructor(subjectNo: number, titleNo: number, pageNo: number) {
    this.titleNo = titleNo;
    this.pageNo = pageNo;
    this.subjectNo = subjectNo;
  }
}

// const subjectNameList = ['우리는 합리적인 경제인', '우리는 생산성 높은 생산자'];
const indexNameList = [
  '왜 경제를 알아야 하나요?',
  '어른들에게도 부족한 것이 있나요?',
  '부족하지 않은 것도 있나요?',
  '왜 돈을 내고 사야 하나요?',
  '왜 선택해야 하나요?',
  '선택하면 포기해야 한다고요?',
  '동화로 보는 경제: 아기 돼지 삼형제',
  '재화와 서비스가 무엇인가요?',
  '재화나 서비스를 생산하려면 무엇이 필요한가요?',
  '나도 생산활동을 하고 있나요?',
  '이왕이면 더 많이 생산할 수 없나요?',
  '동화로 보는 경제: 포도와 여우',
  '왜 더 좋은 자본을 사용하나요?',
];

const indexList: Index[] = [
  new IndexImpl(0, 0, 14),
  new IndexImpl(0, 1, 18),
  new IndexImpl(0, 2, 22),
  new IndexImpl(0, 3, 26),
  new IndexImpl(0, 4, 30),
  new IndexImpl(0, 5, 34),
  new IndexImpl(0, 6, 38),
  new IndexImpl(1, 7, 42),
  new IndexImpl(1, 8, 46),
  new IndexImpl(1, 9, 50),
  new IndexImpl(1, 10, 54),
  new IndexImpl(1, 11, 58),
  new IndexImpl(1, 12, 60),
];

const EducationPage = ({ className }: EducationPageProps): JSX.Element => {
  const [currentPageNo, dispatch] = useReducer(reducer, 1);

  const handlePageNoIncrement = useCallback(
    () => dispatch({ type: 'INCREMENT' }),
    [],
  );
  const handlePageNoDecrement = useCallback(
    () => dispatch({ type: 'DECREMENT' }),
    [],
  );
  const handlePageNoChange = useCallback(
    (pageNo: number) => () => dispatch({ type: 'CHANGE', pageNo }),
    [],
  );

  const navigate = useNavigate();
  const handleGoQuiz = () => {
    navigate('quiz');
  };

  useEffect(() => {
    const handleKeydown = (e: KeyboardEvent) => {
      switch (e.key) {
        case 'ArrowRight': // 오른쪽 화살표
          handlePageNoIncrement();
          break;
        case 'ArrowLeft': // 왼쪽 화살표
          handlePageNoDecrement();
          break;
        default:
          break;
      }
    };

    // 이벤트 리스너 등록
    window.addEventListener('keydown', handleKeydown);

    // 컴포넌트 언마운트 시 이벤트 리스너 제거
    return () => {
      window.removeEventListener('keydown', handleKeydown);
    };
  }, [handlePageNoIncrement, handlePageNoDecrement]);
  const [loading, setLoading] = useState(true);
  const [isDropdownView, setDropdownView] = useState(false);
  const handleClickContainer = () => {
    setDropdownView(!isDropdownView);
  };

  useEffect(() => {
    const timer = setTimeout(() => {
      setLoading(false);
    }, 700);

    return () => clearTimeout(timer);
  }, []);

  const { userInfo } = useAuth();

  return (
    <>
      {loading && (
        <div className="page-loading">
          <img
            style={{ height: '250px', width: '250px' }}
            src="./img/loading/education.gif"
          />
        </div>
      )}
      <div
        className={convertClassNameList(
          convertClassName(className, styles),
          styles['education-page'],
        )}
      >
        <div className={styles['education-page__index']}>
          <Text className="m-1 text-xxl bold" text="학습마당" />
          <span
            className={styles['education-page__index__title']}
            onClick={handleClickContainer}
          >
            목차{isDropdownView ? '▲' : '▼'}
          </span>
          {isDropdownView &&
            indexList.map(({ titleNo, pageNo }, idx) => (
              <Button
                key={pageNo}
                className={convertClassNameList(
                  styles[`education-page__index__button`],
                  styles[
                    `education-page__index__button__${idx % 2 ? 'odd' : 'even'}`
                  ],
                  pageNo <= currentPageNo &&
                    idx < indexList.length - 1 &&
                    currentPageNo < indexList[idx + 1].pageNo
                    ? styles[`education-page__index__button--active`]
                    : '',
                  pageNo <= currentPageNo && idx === indexList.length - 1
                    ? styles[`education-page__index__button--active`]
                    : '',
                )}
                label={indexNameList[titleNo]}
                onClick={handlePageNoChange(pageNo)}
              />
            ))}
          {userInfo && (
            <button className={styles.goQuizButton} onClick={handleGoQuiz}>
              퀴즈 풀러 가기
            </button>
          )}
        </div>
        <div className={styles['education-page__image']}>
          {eduBooks[currentPageNo]}
        </div>
        <div className={styles['education-page__pagination']}>
          <Button
            className={styles['education-page__pagination--button']}
            label={'이전'}
            onClick={handlePageNoDecrement}
          />
          {currentPageNo}쪽
          <Button
            className={styles['education-page__pagination--button']}
            label={'다음'}
            onClick={handlePageNoIncrement}
          />
        </div>
      </div>
    </>
  );
};

export default EducationPage;
