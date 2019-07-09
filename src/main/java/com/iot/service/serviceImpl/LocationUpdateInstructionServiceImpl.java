package com.iot.service.serviceImpl;

import com.iot.dao.locationUpdateInstructionDao.ILocationUpdateInstructionDao;
import com.iot.otaBean.locationUpdateInstruction.LocationUpdateInstruction;
import com.iot.service.interfaces.LocationUpdateInstructionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class LocationUpdateInstructionServiceImpl implements LocationUpdateInstructionService {

    private final ILocationUpdateInstructionDao locationUpdateInstructionDao;

    /**
     * 获取列表
     * @return
     */
    @Override
    public List<LocationUpdateInstruction> getList() {
        List<LocationUpdateInstruction> list = locationUpdateInstructionDao.getList();
        return list;
    }
}
