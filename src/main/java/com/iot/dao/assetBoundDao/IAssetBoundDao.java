package com.iot.dao.assetBoundDao;

import com.iot.otaBean.assetBound.AssetBound;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IAssetBoundDao {
    /**
     * 获取列表
     * @return
     */
    List<AssetBound> getList();
}
