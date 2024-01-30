package com.ssafy.soyu.locker.service;

import com.ssafy.soyu.history.domain.History;
import com.ssafy.soyu.history.repository.HistoryRepository;
import com.ssafy.soyu.item.entity.Item;
import com.ssafy.soyu.item.service.ItemService;
import com.ssafy.soyu.locker.Locker;
import com.ssafy.soyu.locker.LockerRepository;
import com.ssafy.soyu.locker.LockerStatus;
import com.ssafy.soyu.locker.dto.response.ItemResponse;
import com.ssafy.soyu.locker.dto.response.LockerBuyResponse;
import com.ssafy.soyu.locker.dto.response.LockerListResponse;
import com.ssafy.soyu.notice.domain.NoticeType;
import com.ssafy.soyu.notice.dto.request.NoticeRequestDto;
import com.ssafy.soyu.notice.service.NoticeService;
import com.ssafy.soyu.util.response.ErrorCode;
import com.ssafy.soyu.util.response.exception.CustomException;
import com.ssafy.soyu.util.soyu.SoyuProperties;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LockerService {
    private final LockerRepository lockerRepository;
    private final ItemService itemService;
    private final NoticeService noticeService;
    private final HistoryRepository historyRepository;

    private final SoyuProperties soyuProperties;
    public List<LockerListResponse> getLockers(Long stationId) {
        List<Locker> lockerList = lockerRepository.findByStationIdWithItem(stationId);

        return lockerList.stream()
                .map(l -> {
                    Item item = l.getItem();
                    ItemResponse itemResponse = null;
                    if (item != null) {
                        itemResponse = ItemResponse.builder()
                                .id(item.getId())
                                .title(item.getTitle())
                                .content(item.getContent())
                                .regDate(item.getRegDate())
                                .price(item.getPrice())
                                .itemStatus(item.getItemStatus())
                                .itemCategories(item.getItemCategories())
                                .build();
                    }
                    return new LockerListResponse(l.getId(), itemResponse, l.getCode(), l.getIsLight(), l.getIsVisible(), l.getStatus(), l.getLocation(), l.getTime());
                }).collect(Collectors.toList());
    }

    public void checkLocker(Long lockerId) {
        Locker locker = lockerRepository.findById(lockerId).get();
        if(locker.getStatus() != LockerStatus.AVAILABLE){
            throw new CustomException(ErrorCode.IN_USE_LOCKER);
        }
    }

    @Transactional
    public void insertSellCode(String code) {
        Optional<Locker> optionalLocker = lockerRepository.findByCode(code);
        if(!optionalLocker.isPresent()){
            throw new CustomException(ErrorCode.INVALID_AUTH_CODE);
        }

        Locker locker = optionalLocker.get();
        String newCode = itemService.generateRandomCode();
        lockerRepository.updateLockerStatusAndCode(locker.getId(), LockerStatus.READY, newCode);

        //구매자에게 코드 알림
        History history = historyRepository.findByItemIdNotDeleted(locker.getItem().getId());
        noticeService.createNotice(history.getMember().getId(), new NoticeRequestDto(history.getItem(), NoticeType.BUY, newCode));
    }

    public LockerBuyResponse insertBuyCode(Long stationId, String code) {
        //DP 판매 코드를 입력한 경우
        Locker locker = null;
        Optional<Locker> optionalLocker;
        if(code.length() != 6){
            optionalLocker = lockerRepository.findByLocation(stationId, Integer.parseInt(code));
            if(optionalLocker.isPresent()){
                 locker = optionalLocker.get();
                if(locker.getStatus() == LockerStatus.AVAILABLE){
                    throw new CustomException(ErrorCode.EMPTY_ITEM_LOCKER);
                }
                if(locker.getStatus() != LockerStatus.DP){
                    throw new CustomException(ErrorCode.NOT_DP_ITEM);
                }
            }
        }
        else{ //거래 예약 구매자인 경우
            optionalLocker = lockerRepository.findByCode(code);

            if(!optionalLocker.isPresent()){
                throw new CustomException(ErrorCode.INVALID_AUTH_CODE);
            }

            locker = optionalLocker.get();
        }
        return new LockerBuyResponse(soyuProperties.getBankName(), soyuProperties.getAccountNumber(), locker.getItem().getPrice());

    }

    @Transactional
    public void insertWithdrawCode(String code) {
        Optional<Locker> optionalLocker = lockerRepository.findByCode(code);

        if(!optionalLocker.isPresent()){
            throw new CustomException(ErrorCode.INVALID_AUTH_CODE);
        }
        Locker locker = optionalLocker.get();
        lockerRepository.updateLocker(locker.getId(), LockerStatus.AVAILABLE, null, null, null);
    }
}
