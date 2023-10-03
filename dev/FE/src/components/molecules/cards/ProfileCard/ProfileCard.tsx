import { Button, Image, Text } from '@/components/atoms';
import {
  checkConditionClassName,
  convertClassName,
  convertClassNameList,
} from '@/utils';
import styles from './ProfileCard.module.scss';
import useModal from '@/hooks/useModal';
import { useState, ChangeEvent } from 'react';
import {
  deleteUserRival,
  postUserRival,
  putUserProfileImage,
} from '@/api/user';
import { postImage } from '@/api/image';
import { Modal } from '@/components/molecules';
import useAuth from '@/hooks/useAuth';
import { useLocation, useNavigate } from 'react-router-dom';
import { USER_TYPE } from '@/constants';
import { apiImgUrl } from '@/utils/imgUrl';

interface ProfileCardProps {
  className?: string;
  myPage?: boolean;
  src?: string;
  alt?: string;
  userNo?: number;
}

const ProfileCard = ({
  className,
  myPage,
  src,
  alt,
  userNo,
}: ProfileCardProps) => {
  const { isOpen, openToggle, closeToggle } = useModal();
  const [file, setFile] = useState<File | undefined>(undefined);
  const { userInfo, setUserInfo, refreshUserInfo } = useAuth();
  const { state } = useLocation();
  const navigate = useNavigate();

  const handleChangeFile = (e: ChangeEvent<HTMLInputElement>) => {
    setFile((e.currentTarget.files as FileList)[0]);
    if (file) console.log(URL.createObjectURL(file));
  };

  const handleChangeImage = async () => {
    if (!file) {
      alert('파일을 선택해주세요');
      return;
    }

    const formData = new FormData();
    formData.append('file', file);
    const imagePath = await postImage(formData);

    await putUserProfileImage(imagePath);
    setUserInfo((userInfo) => {
      return userInfo && { ...userInfo, imagePath };
    });
    setFile(undefined)
    closeToggle();
  };

  const handleMoveProfilePage = () => {
    if (!userInfo || (state?.userNo ?? userNo) === userInfo.userNo) return;
    navigate(`/profile/${state?.userNo ?? userNo}`, {
      state: { src, alt, userNo: state?.userNo ?? userNo },
    });
  };

  const handleRegistRival = async () => {
    if (!userNo || !window.confirm('라이벌로 등록하시겠습니까?')) return;
    await postUserRival(state?.userNo ?? userNo).then(() => {});
    refreshUserInfo();
  };

  const handleRemoveRival = async () => {
    if (!userNo || !window.confirm('라이벌을 삭제하시겠습니까?')) return;
    await deleteUserRival();
    refreshUserInfo();
  };

  return (
    <div
      className={`${styles['profile-card']} ${convertClassName(
        className,
        styles,
      )}`}
    >
      <div
        className={convertClassNameList(
          styles.image,
          styles['profile-card__image'],
          checkConditionClassName(
            userInfo && userNo !== userInfo.userNo,
            'cursor-pointer',
          ),
        )}
        onClick={handleMoveProfilePage}
      >
        <Image
          src={myPage ? userInfo?.imagePath : state?.src ?? src}
          alt={myPage ? userInfo?.userName : state?.alt ?? alt}
        />
      </div>
      <div className={styles.text}>
        <Text
          text={myPage ? userInfo?.userName : state?.alt ?? alt}
          className="text-lg"
        />
      </div>
      <div className={styles.button}>
        {myPage ? (
          <Button
            label="프로필 수정"
            className="gray xsmall"
            onClick={openToggle}
          />
        ) : (
          userInfo &&
          userInfo.userNo !== userNo &&
          (userInfo.userType === USER_TYPE.아이들 &&
          userInfo.rival === userNo ? (
            <Button
              label="라이벌 취소"
              className="bg-red white xxsmall border0"
              onClick={handleRemoveRival}
            />
          ) : (
            <Button
              label="라이벌 등록"
              className="gray xxsmall"
              onClick={handleRegistRival}
            />
          ))
        )}
      </div>
      <Modal isOpen={isOpen} toggle={closeToggle}>
        <div className={styles['profile-card__modal']}>
          <Text
            className={styles['profile-card__modal--title']}
            text="프로필 사진 수정"
          />
          <input
            className={convertClassNameList(
              styles['profile-card__modal--input'],
              )}
            type="file"
            name="file"
            accept='image/*'
            onChange={handleChangeFile}
          />


          <img className={styles["profile-card__modal--image"]} src={file ? URL.createObjectURL(file) : apiImgUrl(userInfo?.imagePath)} alt={userInfo?.userName}/>
          <div className={styles['profile-card__modal--button-container']}>
            <Button
              className={convertClassNameList(
                styles['profile-card__modal--button'],
                'bg-red',
                'white',
              )}
              label="취소"
              onClick={closeToggle}
            />
            <Button
              className={convertClassNameList(
                styles['profile-card__modal--button'],
                'bg-blue',
                'white',
              )}
              label="수정"
              onClick={handleChangeImage}
            />
          </div>
        </div>
      </Modal>
    </div>
  );
};

export default ProfileCard;
