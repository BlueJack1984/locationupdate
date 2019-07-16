package com.iot.otaBean.mt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 包装LU协议实体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LUMtData implements Serializable {

    private String busiType;//请求非空
    private String keyIndex;//请求非空
    private String checkNum;//请求非空
    private List<LUPlainDataMt> luPlainDataMtList = new ArrayList<>();//请求非空
    private String mac;//请求为空,需服务计算
    private String manuFlag;//厂商编码
}
