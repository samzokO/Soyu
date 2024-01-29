package com.ssafy.soyu.locker.service;

import com.ssafy.soyu.locker.Locker;
import com.ssafy.soyu.locker.LockerRepository;
import com.ssafy.soyu.locker.LockerStatus;
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
        List<Locker> lockerList = lockerRepository.findByStationId(stationId);
        return lockerList.stream()
                .map(l -> new LockerListResponse(l.getId(), l.getItem().getId(), l.getCode(), l.getIsLight()
                        , l.getIsVisible(), l.getStatus(), l.getLocation(), l.getTime())).collect(Collectors.toList());

    }

    public void checkLocker(Long lockerId) {
        Locker locker = lockerRepository.findById(lockerId).get();
        if(locker.getStatus() != LockerStatus.AVAILABLE){
            throw new CustomException(ErrorCode.IN_USE_LOCKER);
        }
    }
}
