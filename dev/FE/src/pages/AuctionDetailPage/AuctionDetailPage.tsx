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
import { useEffect, useState } from 'react';

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

  useEffect(() => {
    if (isAuctionLoading === 'success') {
      setAuctionPrice(auction.auctionPrice + 100);
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
        console.log(data);

        alert(data);
        closeToggle();
      },
      onError(error) {
        alert(`구매 실패.. \n ${error}`);
      },
    },
  );
  const { isOpen, closeToggle, openToggle } = useModal();

  return (
    <div
      className={convertClassNameList(
        convertClassName(className, styles),
        styles['auction-detail-page'],
      )}
    >
      {auction && (
        <AuctionDetailMain
          className={convertClassNameList(styles['auction-detail-page__main'])}
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
      <CreaterProfile
        className={convertClassNameList(styles['auction-detail-page__profile'])}
      />
      {auction && (
        <Modal isOpen={isOpen} toggle={closeToggle}>
          <>
            <div>현재가: {auction.auctionPrice} 원</div>
            <div>최소 경매 금액: {auction.auctionPrice + 100} 원</div>
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
          </>
        </Modal>
      )}
    </div>
  );
};

export default AuctionDetailPage;
