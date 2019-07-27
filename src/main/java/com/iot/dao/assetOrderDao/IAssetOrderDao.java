package com.iot.dao.assetOrderDao;

import com.iot.otaBean.assetOrder.AssetOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IAssetOrderDao {
    List<AssetOrder> queryStartedOrders(@Param("imei") String imei, @Param("now") String now);
    List<AssetOrder> queryPauseOrders(@Param("imei")String imei, @Param("mcc")String mcc, @Param("now")String now);
    List<AssetOrder> queryNotStartedOrders(@Param("imei")String imei, @Param("mcc")String mcc);
    AssetOrder queryOrderByCode(@Param("orderId")String orderId);
    void updateException(@Param("orderCode")String orderCode, @Param("newFlag")String newFlag, @Param("newCause")String newCause);
}
