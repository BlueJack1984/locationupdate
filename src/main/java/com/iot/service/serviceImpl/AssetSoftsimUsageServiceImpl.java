package com.iot.service.serviceImpl;

import com.iot.dao.IAssetSoftsimUsageDao.IAssetSoftsimUsageDao;
import com.iot.otaBean.assetSoftsimUsage.AssetSoftsimUsage;
import com.iot.service.interfaces.AssetSoftsimUsageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
}
