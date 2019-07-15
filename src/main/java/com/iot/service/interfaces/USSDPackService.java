package com.iot.service.interfaces;

import com.iot.otaBean.mt.MtData;

public interface USSDPackService {
    public String ussdBusiServicePack(MtData mtData) throws Exception;

    public String ussdLUBusiServicePack(MtData mtData) throws Exception;
}
