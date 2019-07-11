package com.iot.dao.IAssetSoftsimUsageDao;

import com.iot.otaBean.assetSoftsimUsage.AssetSoftsimUsage;
import com.iot.otaBean.deviceInitRec.DeviceInitRec;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IAssetSoftsimUsageDao {

    int removeSeedIccidRecord(@Param("deviceInitRec")DeviceInitRec deviceInitRec);
    int insertFormalIccidRecord(@Param("deviceInitRec")DeviceInitRec deviceInitRec,
                                @Param("otaTradeNo")String otaTradeNo,
                                @Param("moreImsiFlag")String moreImsiFlag,
                                @Param("coverMcc")String coverMcc);

    /**
     * 根据imsi获取设备码号表数据集合
     * @param imsi
     * @return
     */
    List<AssetSoftsimUsage> getListByImsi(@Param("imsi") String imsi);
}
