package com.iot.dao.assetInfoDao;

import com.iot.otaBean.assetInfo.AssetInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 */
@Mapper
public interface IAssetInfoDao {


    List<AssetInfo> queryAssetInfoByAssetId(@Param("assetId") String assetId);
}
