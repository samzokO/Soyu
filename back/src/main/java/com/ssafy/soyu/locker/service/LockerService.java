package com.ssafy.soyu.locker.service;

import com.ssafy.soyu.item.entity.Item;
import com.ssafy.soyu.locker.Locker;
import com.ssafy.soyu.locker.LockerRepository;
import com.ssafy.soyu.locker.LockerStatus;
import com.ssafy.soyu.locker.dto.response.ItemResponse;
import com.ssafy.soyu.locker.dto.response.LockerListResponse;
import com.ssafy.soyu.util.response.ErrorCode;
import com.ssafy.soyu.util.response.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LockerService {
    private final LockerRepository lockerRepository;

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
}
