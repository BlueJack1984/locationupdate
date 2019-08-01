package com.iot.service.serviceImpl;

import com.iot.dao.IAssetSoftsimUsageDao.IAssetSoftsimUsageDao;
import com.iot.otaBean.assetSoftsimUsage.AssetSoftsimUsage;
import com.iot.service.interfaces.AssetSoftsimUsageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AssetSoftsimUsageServiceImpl implements AssetSoftsimUsageService {


    private final IAssetSoftsimUsageDao assetSoftsimUsageDao;
    /**
     *
     * @param imsi
     * @return
     */
    @Override
    public List<AssetSoftsimUsage> getListByImsi(String imsi) {

        List<AssetSoftsimUsage> list = assetSoftsimUsageDao.getListByImsi(imsi);
        return list;
    }

    /**
     * 根据asset_id获取唯一一条数据
     */
    @Override
    public AssetSoftsimUsage getByAssetId(String assetId) {
        if(StringUtils.isEmpty(assetId)) {
            return null;
        }
        AssetSoftsimUsage assetSoftsimUsage = assetSoftsimUsageDao.getByAssetId(assetId);
        return assetSoftsimUsage;
    }
}
