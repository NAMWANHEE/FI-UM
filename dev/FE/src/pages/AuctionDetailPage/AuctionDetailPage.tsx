import { convertClassName, convertClassNameList } from '@/utils';
import styles from './AuctionDetailPage.module.scss';
import { AuctionDetailMain, CreaterProfile } from '@/components/organisms';
import { AuctionDetailDescription, Modal } from '@/components/molecules';
import { useParams } from 'react-router-dom';
import { useMutation, useQuery } from '@tanstack/react-query';
import { postAuctionBid } from '@/api/auction';
import { Auction } from '@/types';
import { getAuctionDetailByAuctionNoQuery } from '@/api/queries';
import useModal from '@/hooks/useModal';
import { Button } from '@/components/atoms';
import { getUserArtist } from '@/api/user';
import { useEffect, useState } from 'react';
import useAuth from '@/hooks/useAuth';

interface AuctionDetailPageProps {
  className?: string;
}

const AuctionDetailPage = ({
  className,
}: AuctionDetailPageProps): JSX.Element => {
  const [auctionPrice, setAuctionPrice] = useState<number>(0);
  const { auctionNo = '' } = useParams();
  const { data: auction, status: isAuctionLoading } = useQuery<Auction>(
    getAuctionDetailByAuctionNoQuery(auctionNo),
  );
  const { refreshUserInfo } = useAuth();

  useEffect(() => {
    if (isAuctionLoading === 'success') {
      setAuctionPrice(auction.auctionPrice + 100);
      getUserArtist({
        queryKey: ['', auction.userNo],
      }).then((res: any) => setArts(res));
    }
  }, [auction]);

  const checkAuctionPrice = (): boolean => {
    if (auction && auctionPrice < auction.auctionPrice + 100) {
      alert('최소 100원 이상 상회 입찰해야 합니다.');
      setAuctionPrice(auction.auctionPrice + 100);
      return false;
    }
    return true;
  };

  const postAuctionBidMutation = useMutation(
    ({
      auctionNo,
      auctionPrice,
    }: {
      auctionNo: number;
      auctionPrice: number;
    }) => postAuctionBid({ auctionNo, auctionPrice }),
    {
      onSuccess(data) {
        alert(data);
        refreshUserInfo();
        closeToggle();
      },
      onError(error) {
        console.error(error);
        alert(`구매 실패.. \n 잔액이 부족합니다.`);
      },
    },
  );
  const { isOpen, closeToggle, openToggle } = useModal();

  const [arts, setArts] = useState();

  return (
    <>
      <div
        className={convertClassNameList(
          convertClassName(className, styles),
          styles['auction-detail-page'],
        )}
      >
        {auction && (
          <AuctionDetailMain
            className={convertClassNameList(
              styles['auction-detail-page__main'],
            )}
            title={auction.title}
            src={auction.itemImagePath}
            alt={auction.title}
            imageClassName=""
            descriptionClassName={auction.content}
            auctionClick={() => {
              openToggle();
            }}
            buyItClick={() => {
              if (
                window.confirm(
                  `${auction.instantPrice}원에 즉시구매 하시겠습니까?`,
                )
              )
                postAuctionBidMutation.mutate({
                  auctionNo: auction.auctionNo,
                  auctionPrice: auction.instantPrice,
                });
            }}
            auctionPrice={auction.auctionPrice}
            instantPrice={auction.instantPrice}
            createdTime={auction.createdTime}
          />
        )}
        <AuctionDetailDescription
          className={convertClassNameList(
            styles['auction-detail-page__description'],
          )}
          description={auction?.content}
        />
        <CreaterProfile arts={arts} />
      </div>
      {auction && (
        <Modal isOpen={isOpen} toggle={closeToggle} auctionModal={true}>
          <div className={styles['modal']}>
            <div className={styles['modal__title']}>경매하기</div>
            <div className={styles['modal__content']}>
              현재가: {auction.auctionPrice} 원
            </div>
            <div className={styles['modal__content']}>
              최소 경매 금액: {auction.auctionPrice + 100} 원
            </div>
            <div className={styles['modal__buttonWrapper']}>
              <input
                type="number"
                value={auctionPrice}
                onChange={(e) => {
                  setAuctionPrice(Number(e.target.value));
                }}
                onBlur={checkAuctionPrice}
              />
              <Button
                label="경매하기"
                onClick={() => {
                  if (checkAuctionPrice()) {
                    postAuctionBidMutation.mutate({
                      auctionNo: auction.auctionNo,
                      auctionPrice: auctionPrice,
                    });
                  }
                }}
              />
            </div>
          </div>
        </Modal>
      )}
    </>
  );
};

export default AuctionDetailPage;
