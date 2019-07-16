package com.iot.service.interfaces;

import com.iot.otaBean.assetOrder.AssetOrder;
import com.iot.otaBean.deviceInitRec.DeviceInitRec;
import com.iot.otaBean.mo.PositionMo;
import com.iot.otaBean.mt.LUPlainDataMt;
import com.iot.otaBean.mt.MtData;
import com.iot.otaBean.mt.PlainDataMt;

import java.util.List;

public interface SelectNumberService {
    public MtData  selectNumber(String tradeNo, String iccid, PositionMo positionMo, DeviceInitRec deviceInitRec) throws Exception;
    public PlainDataMt selectLocalNumber(String tradeNo, AssetOrder assetOrder, PositionMo positionMo,
                                         DeviceInitRec deviceInitRec) throws Exception;
    public boolean checkMccAndDevice(String mcc, List<DeviceInitRec> deviceInitRecs);

    /**
     * 针对旅游卡的选副号服务
     */
    public LUPlainDataMt selectAccessoryNumber(String tradeNo, AssetOrder assetOrder, String iccid, String mcc) throws Exception;
}
