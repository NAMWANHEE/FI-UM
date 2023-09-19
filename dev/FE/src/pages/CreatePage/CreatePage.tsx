import { convertClassName, convertClassNameList } from '@/utils';
import styles from './CreatePage.module.scss';
import { useState } from 'react';
import { Button, Text } from '@/components/atoms';
import { postSponsorship } from '@/api/sponsor';
import { postAuction } from '@/api/auction';

interface CreatePageProps {
  className?: string;
  status: string;
}

// 사진 등록
// 펀딩 등록
// 갤러리 등록

const CreatePage = ({ className }: CreatePageProps): JSX.Element => {
  const [title, setTitle] = useState<string>('');
  const [description, setDescription] = useState<string>('');
  const [unitPrice, setPrice] = useState<number>(0);
  const [count, setCount] = useState<number>(0);

  // 에러나길래 그냥 해둠
  const status = true ? '후원' : '후원';

  const [imageSrc, setImageSrc] = useState<string>('');

  const encodeFileToBase64 = (fileBlob: Blob): Promise<void> => {
    const reader = new FileReader();
    reader.readAsDataURL(fileBlob);
    return new Promise<void>((resolve) => {
      reader.onload = (event) => {
        if (event.target && typeof event.target.result === 'string') {
          const base64Data = event.target.result;
          setImageSrc(base64Data);
          resolve();
        } else {
          return;
        }
      };
    });
  };

  const handleTitle = (e: any) => {
    setTitle(e.target.value);
  };

  const handleDescription = (e: any) => {
    setDescription(e.target.value);
  };

  const handlePrice = (e: any) => {
    setPrice(e.target.value);
  };
  const handleCount = (e: any) => {
    setCount(e.target.value);
  };

  const handleFile = (e: any) => {
    encodeFileToBase64(e.target.files[0]);
  };

  const addSpon = () => {
    postSponsorship({
      name: title,
      unitPrice: unitPrice,
      count: count,
      description: description,
      imagePath: imageSrc,
    }).then(() => {
      alert('상품 등록 성공!');
      window.location.href = '/';
    });
  };

  const addAuction = () => {
    if (imageSrc !== '') {
      postAuction({
        title: title,
        instantPrice: unitPrice,
        content: description,
        imagePath: imageSrc,
      }).then(() => {
        alert('그림 등록 성공!');
        window.location.href = '/';
      });
    } else {
      alert('안돼');
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
      <div className="flex-container">
        <Text className="text-lg" text="제목" />
        <input type="text" value={title} onChange={handleTitle} />
      </div>

      <br />

      <div className="flex-container">
        <Text className="text-lg" text="가격" />
        <input type="number" value={unitPrice} onChange={handlePrice} />
      </div>

      <br />

      {status === '후원' ? (
        <>
          <div className="flex-container">
            <Text className="text-lg" text="수량" />
            <input type="number" value={count} onChange={handleCount} />
          </div>

          <br />
        </>
      ) : (
        <></>
      )}

      <div className="flex-container">
        <Text className="text-lg" text="내용" />
        <textarea value={description} onChange={handleDescription} />
      </div>

      <br />

      <div className="flex-container">
        <Text className="text-lg" text="사진 업로드" />
        <div>
          <input type="file" onChange={handleFile} />
          <div className="preview">
            {imageSrc && (
              <img
                className={styles['image']}
                src={imageSrc}
                alt="preview-img"
              />
            )}
          </div>
        </div>
      </div>
      <Button
        className={convertClassNameList(
          convertClassName(className, styles),
          'primary xsmall self-end',
          styles['btn'],
        )}
        onClick={status === '후원' ? addSpon : addAuction}
        label={status === '후원' ? '물품 등록 하기' : '그림 등록 하기'}
      />
    </div>
  );
};

export default CreatePage;
