package com.example.pium.service;

import com.example.pium.dto.FundingProgressDto;
import com.example.pium.dto.ItemRecordDto;
import com.example.pium.dto.MyFundingDto;
import com.example.pium.entity.*;
import com.example.pium.repository.BalanceSheetRepository;
import com.example.pium.repository.ItemListRepository;
import com.example.pium.repository.PointTypeRepository;
import com.example.pium.repository.SponsorFundingHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FundingServiceImp {
    private final ItemListRepository itemListRepository;
    private final SponsorFundingHistoryRepository sponsorFundingHistoryRepository;
    private final UserServiceImp userService;
    private final PointServiceImp pointService;
    private final PointTypeRepository pointTypeRepository;
    private final BalanceSheetRepository balanceSheetRepository;
    private final SponsorShipServiceImp sponsorShipService;

    public FundingProgressDto getFundingProgress() {
        Integer sponsorPrice = itemListRepository.findTotalSponsorshipAmountForCompletedItems() * 3 /10;
        Integer fundingPrice = itemListRepository.findTotalFundingAmountForCompletedItems();
        FundingProgressDto getData = new FundingProgressDto();
        getData.setTotalFundingPrice(sponsorPrice);
        getData.setFundingPrice(fundingPrice);

        return getData;
    }

    public Boolean checkFundingNow(Integer itemNo, Integer money) {
        ItemListEntity itemDetail = itemListRepository.findByItemNo(itemNo);
        Integer canFunding = (itemDetail.getItemUnitPrice() * itemDetail.getItemCount() * 3 /10) - itemDetail.getFundingAmount();
        if (canFunding >= money) {
            return  true;
        } else  {
            return false;
        }
    }

    public List<MyFundingDto> getMyFunding(Integer user) {
        List<MyFundingDto> getData = sponsorFundingHistoryRepository.findMyFundingByUserNo(user);
        return getData;
    }

    public List<MyFundingDto> getFunding() {
        List<MyFundingDto> getData = itemListRepository.findFunding();
        return getData;
    }

    public void setBalance(UserEntity user){
        BalanceSheetEntity sellerBalance = balanceSheetRepository.findByUserNo(user).get();
        sellerBalance.setPoint(user.getPoint());
        balanceSheetRepository.save(sellerBalance);
    }

    public void postFunding(Integer user, Integer itemNo, Integer money){
        UserEntity userData = userService.getUserInfo(user);
        // 펀딩/후원 기록에 추가
        SponsorFundingHistoryEntity newFunding = SponsorFundingHistoryEntity.builder()
                .price(money)
                .userNo(userData)
                .itemNo(itemListRepository.findByItemNo(itemNo))
                .build();
        sponsorFundingHistoryRepository.save(newFunding);

        //물품 정보 수정 (펀딩금액 수정)
        ItemListEntity itemDetail = itemListRepository.findByItemNo(itemNo);
        itemDetail.setFundingAmount(itemDetail.getFundingAmount()+money);
        itemListRepository.save(itemDetail);

        // 포인트 추가 ===> 포인트 감소
        pointService.changePointTable(userData, money);

        // 포인트 내역(포인트 획득)
        PointTypeEntity pointTypePoint = pointTypeRepository.findByPointType("펀딩").get();
        pointService.makePointRecord(userData, pointTypePoint, money);

        // 재무상태표 반영
        setBalance(userData);
    }


    public List<ItemRecordDto> getAllRecord() {
        List<SponsorFundingHistoryEntity> allRecordList = sponsorFundingHistoryRepository.findAllByPriceOverThanZero();
        List<ItemRecordDto> recordData = new ArrayList<>();
        for (SponsorFundingHistoryEntity eachData : allRecordList) {
            recordData.add(sponsorShipService.changeDtoToRecord(eachData));
        }
        return recordData;
    }
}
