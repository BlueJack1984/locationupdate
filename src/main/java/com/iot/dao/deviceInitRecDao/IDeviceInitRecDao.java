package com.iot.dao.deviceInitRecDao;

import com.iot.otaBean.deviceInitRec.DeviceInitRec;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IDeviceInitRecDao {
    public List<DeviceInitRec> queryInitRecByDeviceId(String deviceId);
//    public void endRecord(@Param("deviceInitRec")DeviceInitRec deviceInitRec,
//                          @Param("otaTradeNo")String otaTradeNo,
//                          @Param("moreImsiFlag")String moreImsiFlag,
//                          @Param("coverMcc")String coverMcc);
}
