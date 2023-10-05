import styles from './ProfilePage.module.scss';
import {
  Funding,
  ProfileHeader,
  ProfileSection,
  UserCapital,
  UserList,
} from '@/components/organisms';
import {
  checkConditionClassName,
  checkConditionPointChange,
  convertClassName,
  convertClassNameList,
  convertDate,
  countFilter,
  priceFilter,
  priceFilterPlus,
  ratioFilter,
} from '@/utils';
import { Image, Table, Text } from '@/components/atoms';
import useAuth from '@/hooks/useAuth';
import { useMemo, useState, useCallback, useEffect } from 'react';
import { USER_TYPE } from '@/constants';
import {
  ArtistAuction,
  ChildProfile,
  FundingRecord,
  Funding as FundingType,
  Item,
  MyDeposit,
  MyStock,
  Point,
  Portfoilo,
  Purchase,
  SponsorshipDetail,
  TotalCapital,
} from '@/types';
import { useQuery } from '@tanstack/react-query';
import {
  AuctionCard,
  Modal,
  ProfileCard,
  Swiper,
} from '@/components/molecules';
import {
  getAuctionPurchaseQuery,
  getFollowingQuery,
  getFundingRecordsQuery,
  getMyFundingsQuery,
  getMyStocksQuery,
  getPointsQuery,
  getPortfolioQuery,
  getSponsorShipQuery,
  getSponsorShipRecordDetailQuery,
  getSponsorShipRecordsQuery,
  getUserArtistQuery,
  getUserDepositSavingQuery,
  getUserTotalCapitalQuery,
} from '@/api/queries';
import { useParams, useNavigate } from 'react-router-dom';

interface ProfilePageProps {
  className?: string;
}

// 후원자 프로필
const SponsorProfilePage = () => {
  // 후원 내역
  const { data: sponsorshipDetails } = useQuery<SponsorshipDetail[]>(
    getSponsorShipRecordDetailQuery(),
  );

  // 경매 내역
  const { data: auctionPurchases } = useQuery<Purchase[]>(
    getAuctionPurchaseQuery(),
  );

  // 팔로우
  const { data: childProfiles } = useQuery<ChildProfile[]>(getFollowingQuery());

  // 포인트 사용 내역
  const { data: pointRecords } = useQuery<Point[]>(getPointsQuery());

  const followings = useMemo(() => {
    const followings: JSX.Element[] = [];
    if (childProfiles) {
      const len = childProfiles?.length ?? 0;
      for (let index = 0; index < len / 4; index++) {
        followings.push(
          <div className={styles['profile-page__auction-container']}>
            {childProfiles
              .slice(index * 4, Math.min((index + 1) * 4, len))
              .map(({ imagePath, userName, userNo }) => (
                <ProfileCard
                  key={userNo}
                  src={imagePath}
                  alt={userName}
                  userNo={userNo}
                />
              ))}
          </div>,
        );
      }
    }
    return followings;
  }, [childProfiles]);

  return (
    <>
      <ProfileSection label="내가 구매한 그림">
        <div className="card-container jc-center">
          {auctionPurchases?.map(({ actionNo, imagePath, title }) => (
            <AuctionCard
              key={actionNo}
              itemImagePath={imagePath}
              title={title}
              noBtn={true}
            />
          ))}
        </div>
      </ProfileSection>
      <ProfileSection label="팔로우 한 아이들">
        <Swiper>{followings}</Swiper>
      </ProfileSection>
      <ProfileSection label="포인트 사용 내역">
        <Table
          data={
            pointRecords &&
            pointRecords.map(({ changedTime, pointChange, useType }) => {
              return {
                타입: useType,
                변동량: (
                  <Text
                    className={checkConditionPointChange(pointChange)}
                    text={priceFilterPlus(pointChange)}
                  />
                ),
                변동날짜: convertDate(changedTime),
              };
            })
          }
          size={10}
        />
      </ProfileSection>
      <ProfileSection label="나의 후원 목록">
        <Table
          data={
            sponsorshipDetails &&
            sponsorshipDetails.map(({ itemName, price }) => {
              return {
                물품명: itemName,
                변동량: priceFilter(price),
              };
            })
          }
          size={10}
        />
      </ProfileSection>
    </>
  );
};

const ChildProfilePage = ({ myPage }: { myPage?: boolean }) => {
  const { userNo } = useParams<string>();
  const navigate = useNavigate();

  // 내 예적금
  const { data: depositSavings } = myPage
    ? useQuery<MyDeposit[]>(getUserDepositSavingQuery())
    : { data: undefined };

  // 내 주식
  const { data: myStocks } = myPage
    ? useQuery<MyStock[]>(getMyStocksQuery())
    : { data: undefined };

  // 내 펀딩
  const { data: myFundings } = myPage
    ? useQuery<FundingType[]>(getMyFundingsQuery())
    : { data: undefined };

  // 포인트 사용 내역
  const { data: pointRecords } = myPage
    ? useQuery<Point[]>(getPointsQuery())
    : { data: undefined };

  // 내 작품
  const { data: myAuctions } = useQuery<ArtistAuction[]>(
    getUserArtistQuery(Number(userNo)),
  );

  // AI 포토폴리오
  const { data: portFolio } = useQuery<Portfoilo, Error>(
    getPortfolioQuery(Number(userNo)),
  );

  // Modal창 on, off
  const [portFolioModal, isPortFolioModal] = useState<boolean>(false);
  const handlePortFolioButtonClick = () => {
    isPortFolioModal(!portFolioModal);
  };

  const auction = useMemo(() => {
    const auction: JSX.Element[] = [];
    if (myAuctions) {
      const len = myAuctions?.length ?? 0;
      for (let index = 0; index < len / 4; index++) {
        auction.push(
          <div
            className={convertClassNameList(
              styles['profile-page__auction-container'],
              'flex-container',
            )}
          >
            {myAuctions
              .slice(index * 4, Math.min((index + 1) * 4, len))
              .map(({ auctionNo, imagePath, title, winner }) => (
                <AuctionCard
                  key={auctionNo}
                  className={checkConditionClassName(
                    winner,
                    styles['profile-page__auction'],
                  )}
                  {...{
                    auctionNo,
                    itemImagePath: imagePath,
                    title,
                    winner,
                    onClick: myPage
                      ? undefined
                      : () => {
                          navigate(`/auction/${auctionNo}`);
                        },
                  }}
                />
              ))}
          </div>,
        );
      }
    }
    return auction;
  }, [myAuctions]);

  return (
    <>
      {myPage && (
        <>
          <ProfileSection label="자산 모아보기">
            <div className={styles['profile-page__profile-section']}>
              <div
                className={
                  styles['profile-page__profile-section__tableWrapper']
                }
              >
                <div
                  className={
                    styles[
                      'profile-page__profile-section__tableWrapper__buttonWrapper'
                    ]
                  }
                ></div>
                <Table
                  data={
                    depositSavings &&
                    depositSavings.map(
                      ({
                        bankName,
                        interestRate,
                        primeInterestRate,
                        productType,
                        savingBalance,
                      }) => {
                        return {
                          은행명: bankName,
                          상품명: productType,
                          이자율: `${interestRate}% (+${primeInterestRate}%)`,
                          잔액: priceFilter(savingBalance),
                        };
                      },
                    )
                  }
                  size={5}
                />
              </div>
              <div
                className={
                  styles['profile-page__profile-section__tableWrapper']
                }
              >
                <div
                  className={
                    styles[
                      'profile-page__profile-section__tableWrapper__buttonWrapper'
                    ]
                  }
                >
                  <button onClick={handlePortFolioButtonClick}>
                    AI 포트폴리오 분석
                  </button>
                </div>
                <Table
                  data={
                    myStocks &&
                    myStocks.map(
                      ({
                        stockCount,
                        stockName,
                        stockNowPrice,
                        stockUnitPrice,
                      }) => {
                        return {
                          주식명: stockName,
                          보유주: stockCount,
                          평단가: priceFilter(stockUnitPrice),
                          현재가: priceFilter(stockNowPrice),
                        };
                      },
                    )
                  }
                  size={5}
                />
              </div>
            </div>
          </ProfileSection>
          <ProfileSection label="내 펀딩">
            <Funding fundings={myFundings} />
          </ProfileSection>
          {portFolio && (
            <div className={styles.modal}>
              <Modal
                aiModal={true}
                isOpen={portFolioModal}
                toggle={handlePortFolioButtonClick}
              >
                <div className={styles['aiPortFolio']}>
                  <div className={styles['aiPortFolio__title']}>
                    AI 포트폴리오 분석
                  </div>
                  <div>
                    <Table
                      data={
                        myStocks &&
                        myStocks.map(({ stockCount, stockName }) => {
                          return {
                            주식명: stockName,
                            보유주: stockCount,
                            AI기준: portFolio?.moneyBalance[stockName] ?? 0,
                            'AI기준 비율': `${Math.floor(
                              portFolio?.ratioBalance[stockName] * 100 ?? 0,
                            )}%`,
                          };
                        })
                      }
                      size={5}
                    />
                  </div>
                  <div className={styles['aiPortFolio__content']}>
                    현재 AI 포트폴리오에서 주식 투자의 비율을{' '}
                    {Object.keys(portFolio?.moneyBalance).map((item) => {
                      const ratio = portFolio?.ratioBalance[item];
                      if (ratio > 0) {
                        return (
                          <span>
                            {item}를{' '}
                            <span
                              className={
                                styles['aiPortFolio__content__percent']
                              }
                            >
                              {Math.floor(ratio * 100)}%
                            </span>{' '}
                          </span>
                        );
                      }
                    })}
                    갖도록 분석해줬어요
                  </div>
                </div>
              </Modal>
            </div>
          )}
        </>
      )}

      <ProfileSection label="그림">
        <Swiper className={styles['child-profile-page__swiper']}>
          {auction}
        </Swiper>
      </ProfileSection>
      {myPage && (
        <ProfileSection label="포인트 사용 내역">
          <Table
            data={
              pointRecords &&
              pointRecords.map(({ changedTime, pointChange, useType }) => {
                return {
                  타입: useType,
                  변동량: (
                    <Text
                      className={checkConditionPointChange(pointChange)}
                      text={priceFilterPlus(pointChange)}
                    />
                  ),
                  변동날짜: convertDate(changedTime),
                };
              })
            }
            size={10}
          />
        </ProfileSection>
      )}
    </>
  );
};

const AdminProfilePage = () => {
  const [curCapital, setCurCapital] = useState<TotalCapital>();

  const handleChangeChartState = useCallback((capital: TotalCapital) => {
    setCurCapital(capital);
  }, []);

  // 아이들 전체 자산
  const { data: totalCapitals } = useQuery<TotalCapital[]>(
    getUserTotalCapitalQuery(),
  );

  // 전체 후원 & 펀딩
  const { data: sponsorships } = useQuery<Item[]>(getSponsorShipQuery());

  // 후원 히스토리
  const { data: sponsorshipRecords } = useQuery<SponsorshipDetail[]>(
    getSponsorShipRecordsQuery(),
  );

  // 펀딩 히스토리
  const { data: fundingRecords } = useQuery<FundingRecord[]>(
    getFundingRecordsQuery(),
  );

  useEffect(() => {
    totalCapitals?.[0] && setCurCapital(totalCapitals[0]);
  }, [totalCapitals]);

  return (
    <>
      <div className={styles['profile-page__profile-section']}>
        <ProfileSection label="아이들 자산 현황">
          <UserList
            totalCapitals={totalCapitals}
            onClick={handleChangeChartState}
          />
        </ProfileSection>
        <ProfileSection label={`${curCapital?.userName}의 자산 상세`}>
          <UserCapital totalCapitals={totalCapitals} curCapital={curCapital} />
        </ProfileSection>
      </div>
      <div className={styles['profile-page__profile-section2']}>
        <ProfileSection label="후원품 목록">
          <Table
            data={
              sponsorships &&
              sponsorships
                .filter((sponsorship) => !sponsorship.isCompleted)
                .map(
                  ({
                    imagePath,
                    itemName,
                    itemCount,
                    unitPrice,
                    sponsorshipAmount,
                  }) => {
                    return {
                      사진: <Image src={imagePath} />,
                      이름: itemName,
                      개수: countFilter(itemCount),
                      후원금액: priceFilter(sponsorshipAmount),
                      후원률: ratioFilter(
                        sponsorshipAmount,
                        itemCount * unitPrice,
                      ),
                    };
                  },
                )
            }
            size={5}
          />
        </ProfileSection>
        <ProfileSection label="후원 히스토리">
          <Table
            data={
              sponsorshipRecords &&
              sponsorshipRecords.map(({ itemName, price, userName }) => {
                return {
                  '아이템 이름': itemName,
                  '후원한 사람': userName,
                  금액: priceFilter(price),
                };
              })
            }
            size={5}
          />
        </ProfileSection>
      </div>
      <div className={styles['profile-page__profile-section2']}>
        <ProfileSection label="펀딩 목록">
          <Table
            data={
              sponsorships &&
              sponsorships
                .filter((sponsorship) => sponsorship.isCompleted)
                .map(
                  ({
                    imagePath,
                    itemName,
                    itemCount,
                    unitPrice,
                    fundingAmount,
                  }) => {
                    return {
                      사진: <Image src={imagePath} />,
                      이름: itemName,
                      개수: countFilter(itemCount),
                      펀딩금액: priceFilter(fundingAmount),
                      펀딩률: ratioFilter(
                        fundingAmount,
                        itemCount * unitPrice * 0.3,
                      ),
                    };
                  },
                )
            }
            size={5}
          />
        </ProfileSection>
        <ProfileSection label="펀딩 히스토리">
          <Table
            data={
              fundingRecords &&
              fundingRecords.map(({ itemName, userName, price }) => {
                return {
                  '아이템 이름': itemName,
                  '펀딩한 사람': userName,
                  금액: priceFilter(price),
                };
              })
            }
            size={5}
          />
        </ProfileSection>
      </div>
    </>
  );
};

const ProfilePage = ({ className }: ProfilePageProps): JSX.Element => {
  const { userInfo } = useAuth();
  const { userNo } = useParams<string>();
  const myPage = useMemo(() => {
    if (!userInfo) return false;
    return userInfo.userNo === Number(userNo);
  }, [userNo, userInfo?.userNo]);

  const profile = useMemo(() => {
    if (!userInfo) return undefined;
    if (!myPage) return <ChildProfilePage />;
    switch (userInfo?.userType) {
      case USER_TYPE.아이들:
        return <ChildProfilePage myPage={myPage} />;
      case USER_TYPE.원장쌤:
        return <AdminProfilePage />;
      case USER_TYPE.후원자:
        return <SponsorProfilePage />;
      default:
        return undefined;
    }
  }, [userInfo, userNo]);

  return (
    <div
      className={convertClassNameList(
        convertClassName(className, styles),
        styles['profile-page'],
      )}
    >
      <ProfileHeader userInfo={userInfo} myPage={myPage} />
      {profile}
    </div>
  );
};

export default ProfilePage;
