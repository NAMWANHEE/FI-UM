import { Button, Text } from '@/components/atoms';
import { convertClassName, convertClassNameList, convertUser } from '@/utils';
import styles from './Login.module.scss';
import {
  useMemo,
  useState,
  ChangeEvent,
  useEffect,
  KeyboardEvent,
} from 'react';
import { UserDetail } from '@/types';
import { getUserCheckId, userLogin, userSignup } from '@/api/user';
import { useMutation } from '@tanstack/react-query';
import useAuth from '@/hooks/useAuth';
import { useNavigate } from 'react-router-dom';

interface LoginProps {
  className?: string;
  signUp?: boolean;
}

const Login = ({ className, signUp }: LoginProps): JSX.Element => {
  const { setUserInfo } = useAuth();
  const navigate = useNavigate();
  const [userInformation, setUserInformation] = useState<UserDetail>({
    userId: '',
    password: '',
    password2: '',
    phoneNumber: '',
    userName: '',
  });
  const [idCheckResponse, setIdCheckResponse] = useState<string>('');
  const [errorMessage, setErrorMessage] = useState<string>('');

  useEffect(() => {
    if (userInformation.userId && signUp) {
      getUserCheckId({ queryKey: ['', userInformation.userId] })
        .then((res: any) => {
          res.data.msg === '사용 가능'
            ? setIdCheckResponse('사용 가능한 아이디입니다.')
            : setIdCheckResponse('사용할 수 없는 아이디입니다.');
        })
        .catch((err) => {
          console.error(err);
        });
      if (userInformation.userId === '') {
        setIdCheckResponse('');
      }
    }
  }, [userInformation.userId, signUp]);

  useEffect(() => {
    // 모든 필드가 채워졌는지 확인
    if (
      !userInformation.userName ||
      !userInformation.userId ||
      !userInformation.password ||
      !userInformation.password2 ||
      !userInformation.phoneNumber
    ) {
      setErrorMessage('모든 필드를 채워주세요.');
      return;
    }

    // ID 최대 12자
    if (userInformation.userId.length > 12) {
      setErrorMessage('ID는 최대 12자 까지 설정 가능합니다.');
      return;
    }

    // ID가 알파벳과 숫자로만 구성되어 있는지 확인
    if (!/^[A-Za-z0-9]+$/.test(userInformation.userId)) {
      setErrorMessage('ID는 알파벳과 숫자로만 구성되어야 합니다.');
      return;
    }
    // ID 중복 확인
    if (idCheckResponse === '사용할 수 없는 아이디입니다.') {
      setErrorMessage('다른 아이디를 사용하여야 합니다.');
      return;
    }

    // 비밀번호의 길이가 8자 이상 15자 이하인지 확인
    if (
      userInformation.password.length < 8 ||
      userInformation.password.length > 15
    ) {
      setErrorMessage('비밀번호는 8자 이상 15자 이하이어야 합니다.');
      return;
    }

    // 비밀번호가 일치하는지 확인
    if (userInformation.password !== userInformation.password2) {
      setErrorMessage('비밀번호가 일치하지 않습니다.');
      return;
    }

    // 전화번호 형식이 맞는지 확인
    if (
      !/^01([0|1|6|7|8|9])-([0-9]{3,4})-([0-9]{4})$/.test(
        userInformation.phoneNumber,
      )
    ) {
      setErrorMessage('전화번호 형식이 올바르지 않습니다.');
      return;
    }
    setErrorMessage('');
  }, [userInformation]);

  const handleChangeValue = (e: ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;

    setUserInformation((prevState) => ({
      ...prevState,
      [name]: value,
    }));
  };

  const signUpMutation = useMutation(userSignup, {
    onSuccess: () => {
      alert('회원가입 되었습니다.');
      navigate('/login');
    },
    onError: (err) => {
      console.error(err);
    },
  });

  const handleSignUpClick = () => {
    if (errorMessage === '') {
      signUpMutation.mutate(userInformation);
    } else {
      alert(errorMessage);
    }
  };

  const loginMutation = useMutation(userLogin, {
    onSuccess: (data) => {
      setUserInfo(data);
      alert('로그인 성공!!');
      navigate('/');
    },
    onError: (err) => {
      console.error(err);
      alert('아이디 또는 비밀번호를 확인해주세요');
    },
  });

  const handleLoginClick = () => {
    loginMutation.mutate(userInformation);
  };

  const inputList = useMemo(() => {
    const arr = ['userId', 'password'];
    if (signUp) {
      arr.push('password2');
      arr.push('userName');
      arr.push('phoneNumber');
    }
    return arr;
  }, [signUp]);

  const onEnter = (e: KeyboardEvent<HTMLInputElement>) => {
    if (e.key === 'Enter') {
      if (signUp) {
        handleSignUpClick();
      } else {
        handleLoginClick();
      }
    }
  };

  return (
    <div
      className={convertClassNameList(
        convertClassName(className, styles),
        styles['login'],
      )}
    >
      <Text
        className={convertClassNameList(
          'text-md',
          styles['login__item'],
          styles['login__item--title'],
        )}
        text={signUp ? '회원가입' : '로그인'}
      />

      {inputList.map(
        (key, index): JSX.Element => (
          <div
            key={key}
            className={convertClassNameList(styles['login__item'])}
          >
            <label>
              <Text
                className={convertClassNameList(styles['login__item--label'])}
                text={convertUser(key as keyof UserDetail)}
              />
            </label>
            <input
              autoFocus={index === 0}
              className={convertClassNameList(styles['login__item--input'])}
              type={key.includes('password') ? 'password' : 'text'}
              name={key}
              value={userInformation[key as keyof UserDetail]}
              onChange={handleChangeValue}
              onKeyUp={index === inputList.length - 1 ? onEnter : () => {}}
            />
            {signUp && key === 'userId' ? <div>{idCheckResponse}</div> : ''}
          </div>
        ),
      )}
      <div className={convertClassNameList(styles['login__item'])}>
        <Button
          className={convertClassNameList(
            'bg-blue white',
            styles['login__item'],
            styles['login__item--button'],
          )}
          label={!signUp ? '로그인' : '회원가입'}
          onClick={signUp ? handleSignUpClick : handleLoginClick}
        />
      </div>
    </div>
  );
};

export default Login;
