package com.iot.service.interfaces;

import com.iot.otaBean.mt.LUMtData;
import com.iot.otaBean.mt.MtData;

public interface USSDPackService {
    public String ussdBusiServicePack(MtData mtData) throws Exception;

    /**
     * LU服务
     * @param luMtData
     * @return
     * @throws Exception
     */
    public String ussdLUBusiServicePack(LUMtData luMtData) throws Exception;
}
