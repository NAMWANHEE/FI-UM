import { convertClassName, convertClassNameList } from '@/utils';
import styles from './CreatePage.module.scss';
import { useState, ChangeEvent, useMemo } from 'react';
import { Button, Text } from '@/components/atoms';
import { postSponsorship } from '@/api/sponsor';
import { postAuction } from '@/api/auction';
import { postImage } from '@/api/image';
import { useLocation, useNavigate } from 'react-router-dom';
import { NewItem } from '@/types';
import { postReview } from '@/api/review';

interface CreatePageProps {
  className?: string;
}

const initialNewItem = {
  name: '',
  unitPrice: 0,
  count: 0,
  description: '',
  imagePath: '',
};

// 사진 등록
// 펀딩 등록
// 갤러리 등록

const CreatePage = ({ className }: CreatePageProps): JSX.Element => {
  const navigate = useNavigate();
  const [item, setItem] = useState<NewItem>(initialNewItem);
  const [file, setFile] = useState<File | undefined>(undefined);

  const imageUrl = useMemo(() => {
    return file && URL.createObjectURL(file);
  }, [file]);

  // 에러나길래 그냥 해둠

  const { state } = useLocation();

  const handleItem = (
    e: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>,
  ): void => {
    setItem({ ...item, [e.currentTarget.name]: e.currentTarget.value });
  };

  const handleFile = (e: ChangeEvent<HTMLInputElement>) => {
    setFile((e.currentTarget?.files as FileList)?.[0]);
  };

  const handleAddItem = async () => {
    if (!file) {
      alert('파일 선택 필요');
      return;
    }
    const formData = new FormData();
    formData.append('file', file);
    const imagePath = await postImage(formData);

    if (state === 'funding') {
      postSponsorship({ ...item, imagePath }).then(() => {
        alert('등록 성공!');
        navigate('/');
      });
    } else if (state === 'auction') {
      postAuction({
        title: item.name,
        instantPrice: item.unitPrice,
        content: item.description,
        imagePath: imagePath,
      })
        .then(() => {
          alert('등록 성공!');
          navigate('/');
        })
        .catch((err) => {
          console.error(err.data);
        });
    } else {
      postReview({
        title: item.name,
        content: item.description,
        imagePath: imagePath,
      })
        .then(() => {
          alert('등록 성공!');
          navigate('/');
        })
        .catch((err) => {
          console.error(err.data);
        });
    }
  };

  return (
    <div
      className={convertClassNameList(
        convertClassName(className, styles),
        styles['create-page'],
        'flex-container-col',
      )}
    >
      <div className="flex-container jc-space-between">
        <div>
          <div className="flex-container">
            <Text className="text-lg" text="제목" />
            <input
              className={styles['input']}
              type="text"
              name="name"
              value={item.name}
              onChange={handleItem}
            />
          </div>

          <br />
          {state === 'gallery' ? (
            <></>
          ) : (
            <>
              <div className="flex-container">
                <Text className="text-lg" text="가격" />
                <input
                  className={styles['input']}
                  type="number"
                  name="unitPrice"
                  value={item.unitPrice}
                  onChange={handleItem}
                />
              </div>

              <br />

              {state === 'funding' ? (
                <>
                  <div className="flex-container">
                    <Text className="text-lg" text="수량" />
                    <input
                      className={styles['input']}
                      type="number"
                      name="count"
                      value={item.count}
                      onChange={handleItem}
                    />
                  </div>

                  <br />
                </>
              ) : (
                <></>
              )}
            </>
          )}
        </div>
        <Button
          className={convertClassNameList(
            convertClassName(className, styles),
            'primary xsmall self-end mr-1 mb-1',
          )}
          onClick={handleAddItem}
          label={
            state === 'gallery'
              ? '게시글 등록'
              : state === 'funding'
              ? '물품 등록 하기'
              : '그림 등록 하기'
          }
        />
      </div>

      <div className="flex-container">
        <Text className="text-lg" text="내용" />
        <textarea
          name="description"
          value={item.description}
          onChange={handleItem}
        />
      </div>

      <br />

      <div className="flex-container">
        <Text className="text-lg" text="사진 업로드" />
        <div>
          <input
            className={styles['input']}
            type="file"
            accept="image/*"
            onChange={handleFile}
          />
          <div className="preview">
            {imageUrl && (
              <img
                className={styles['image']}
                src={imageUrl}
                alt="preview-img"
              />
            )}
          </div>
        </div>
      </div>
    </div>
  );
};

export default CreatePage;
