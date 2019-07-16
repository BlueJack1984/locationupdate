package com.iot.otaBean.mt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 针对lu服务下发副号通信协议实体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LUCmdParamData implements Serializable {

    private String primaryIccidSuffix;

    private String callControlFlag;

    private String expTime;

    private String otaTradeNo;

    private String coverMcc;

    private String plmn;

    private String imsi;

    private String algorithm;

    private String dataKeyIndex;

    private String keyData;
}
