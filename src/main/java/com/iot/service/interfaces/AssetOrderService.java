package com.iot.service.interfaces;

import com.iot.otaBean.assetOrder.AssetOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 */
public interface AssetOrderService {

    /**
     *
     */
    List<AssetOrder> getListByIccids(@Param("iccidList") List<String> iccidList);
}
