import styles from '@/App.module.scss';
import Router from './router/Router';
import { Footer, Navbar } from './components/molecules';
import { checkConditionClassName, convertClassNameList } from './utils';
import useModal from './hooks/useModal';
import { useEffect } from 'react';
import { useLocation } from 'react-router-dom';

const App = () => {
  const { isOpen } = useModal();
  console.log('isOpen', isOpen);

  const location = useLocation();

  useEffect(() => {
    window.addEventListener('scroll', () => {
      const scrollbar = document.querySelector('.scrollbar');
      if (scrollbar) {
        // 스크롤 위치가 일정 이상 내려갔을 때 스크롤 바를 나타나게 함
        if (window.scrollY > 100) {
          scrollbar.classList.add('active');
        } else {
          scrollbar.classList.remove('active');
        }
      }
    });
  }, [document.documentElement.scrollTop]);

  return (
    <div
      className={convertClassNameList(
        styles.app,
        checkConditionClassName(!isOpen, styles['scrolling']),
      )}
    >
      {!location.pathname.includes('toss') && (
        <Navbar className={styles['app__navbar']} />
      )}
      <Router className={styles['app__main']} />
      {!location.pathname.includes('toss') && (
        <Footer className={styles['app__footer']} />
      )}
    </div>
  );
};

export default App;
