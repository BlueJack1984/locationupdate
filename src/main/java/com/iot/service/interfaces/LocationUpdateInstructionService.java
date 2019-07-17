package com.iot.service.interfaces;

import com.iot.otaBean.locationUpdateInstruction.LocationUpdateInstruction;

import java.util.List;

/**
 *
 */
public interface LocationUpdateInstructionService {

    /**
     * 返回列表数据
     * @return
     */
    List<LocationUpdateInstruction> getList();

    int removeList(List<LocationUpdateInstruction> instructionList);
}
