import { convertClassName, convertClassNameList } from '@/utils';
import styles from './FundingPage.module.scss';
import { FundingItem, Ranking } from '@/components/organisms';
import { getFundingsQuery } from '@/api/queries/funding';
import { useQuery } from '@tanstack/react-query';
import { Funding, Ranking as RankingType } from '@/types';
import { Modal } from '@/components/molecules';
import ModalFunding from '@/components/molecules/utils/Modal/contents/ModalFunding/ModalFunding';
import useModal from '@/hooks/useModal';
import { useCallback, useEffect, useState } from 'react';
import { Button } from '@/components/atoms';
import { useNavigate } from 'react-router-dom';
import useAuth from '@/hooks/useAuth';
import { getRankingsQuery, getSponsorShipQuery } from '@/api/queries';
import { USER_TYPE } from '@/constants';
import { useMemo } from 'react';
import { postSponsorshipSupport } from '@/api/sponsor';
import { postFunding } from '@/api/funding';

interface FundingPageProps {
  className?: string;
}

const FundingPage = ({ className }: FundingPageProps): JSX.Element => {
  const [scrollTop, setScrollTop] = useState<number>(0);
  const { isOpen, openToggle, closeToggle } = useModal();
  const { userInfo, refreshUserInfo } = useAuth();
  const navigate = useNavigate();
  const userType = userInfo?.userType ?? 0;
  const { data: fundings } =
    userType === USER_TYPE.아이들
      ? useQuery<Funding[], Error>(getFundingsQuery())
      : useQuery<Funding[], Error>(getSponsorShipQuery());
  const [item, setItem] = useState<any>();
  const [sponSwitch, setSponSwitch] = useState<boolean>(false);

  const { data: rankings } = useQuery<RankingType[]>(getRankingsQuery());
  const ranking = useMemo<RankingType | undefined>(() => {
    return rankings?.find((ranking) => ranking.type === '펀딩');
  }, [rankings]);

  const onModal = useCallback((i: Funding) => {
    setItem(i);
    setScrollTop(document.documentElement.scrollTop);
    openToggle();
  }, []);

  const sponBtn = (id: number, price: number) => {
    if (userType === USER_TYPE.아이들) {
      postFunding(id, price)
        .then(() => {
          alert('펀딩 성공!');
          refreshUserInfo();
        })
        .catch((err) => alert(err.response.data.msg));
    } else {
      postSponsorshipSupport(id, price)
        .then(() => {
          alert('후원 성공!');
          refreshUserInfo();
        })
        .catch((err) => alert(err.response.data.msg));
    }
    closeToggle();
  };

  const handleSponSwitch = () => {
    setSponSwitch(!sponSwitch);
  };

  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const timer = setTimeout(() => {
      setLoading(false);
    }, 700);

    return () => clearTimeout(timer);
  }, []);

  return (
    <>
      {loading && (
        <div className="page-loading">
          <img
            style={{ height: '250px', width: '250px' }}
            src="./img/loading/funding.gif"
          />
        </div>
      )}

      <div
        className={convertClassNameList(
          convertClassName(className, styles),
          styles['funding-page'],
          'flex-container-col',
        )}
      >
        {userType === USER_TYPE.원장쌤 && (
          <Button
            className={convertClassNameList(
              convertClassName(className, styles),
              'self-end m-1',
              'primary xsmall',
            )}
            label="등록하기"
            onClick={() => navigate(`/create`, { state: 'funding' })}
          />
        )}
        {ranking && <Ranking ranking={ranking} />}
        {userType !== USER_TYPE.아이들 && (
          <Button
            className="xsmall primary"
            onClick={handleSponSwitch}
            label={sponSwitch ? '펀딩' : '후원'}
          />
        )}
        {userType === USER_TYPE.아이들
          ? fundings
              ?.sort((a, b) => {
                return (
                  (b.fundingAmount / (b.itemUnitPrice * b.itemCount) / 3) *
                    10 *
                    100 -
                  (a.fundingAmount / (a.itemUnitPrice * a.itemCount) / 3) *
                    10 *
                    100
                );
              })
              .map((funding) => {
                return (
                  <FundingItem
                    key={funding.itemNo}
                    {...funding}
                    onModal={onModal}
                  />
                );
              })
          : fundings
              ?.filter((funding) => funding.isCompleted === sponSwitch)
              .sort((a, b) => {
                return a.isCompleted
                  ? (b.fundingAmount / (b.unitPrice * b.itemCount) / 3) *
                      10 *
                      100 -
                      (a.fundingAmount / (a.unitPrice * a.itemCount) / 3) *
                        10 *
                        100
                  : (b.sponsorshipAmount / (b.unitPrice * b.itemCount)) * 100 -
                      (a.sponsorshipAmount / (a.unitPrice * a.itemCount)) * 100;
              })
              .map((funding) => {
                return (
                  <FundingItem
                    key={funding.itemNo}
                    {...funding}
                    onModal={onModal}
                  />
                );
              })}
      </div>
      <Modal scrollTop={scrollTop} isOpen={isOpen} toggle={closeToggle}>
        <ModalFunding
          className={className}
          item={item}
          onClick={sponBtn}
          closeToggle={closeToggle}
        />
      </Modal>
    </>
  );
};

export default FundingPage;
