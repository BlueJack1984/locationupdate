package com.iot.otaBean.assetInfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssetInfo implements Serializable {

    private Long id;
    private String assetId;
    private String iccid;
    private String imsi;
    private String msisdn;
    private String outsideAssetId;
    private String assetFootprint;
    private String roamingsimCode;
    private String version;
    private String crc;
    private String chipModel;
    private String assetName;
    private String pn;
    private String lifecycle;
    private String lifecycleStartTime;
    private String lifecycleEndTime;
    private String taskNo;
    private String manufacturerCode;
    private String manufacturerName;
    private String inTime;
    private String activateFlag;
    private String activateTime;
    private String partnerName;
    private String partnerCode;
    private String lastReportTime;
    private String lastReportPositionMcc;
    private String lastReportPositionName;
    private String lastReportOperatorCode;
    private String lastReportOperatorName;
    private String abandonedTime;
    private String abandonedCause;
    private String dataEncryptFactor;
    private String packageType;
    private String packageCode;
    private String packageName;
    private String packageFlow;
    private String orderUnintDt;
    private String packageUseRate;
    private String assignTime;
    private String orderId;
    private String isOnline;
    private String lastLoginCountry;
    private String lastLoginOperator;
    private String lastLoginTime;
    private String silentCycle;
    private String stopServicePeriod;
    private String remark;
    private String operatorCode;
    private String operatorName;
    private String supplierCode;
    private String supplierName;
    private String initFlag;
    private String otaFlag;
    private String bipFlag;
    private String inuseIndustry;
    private String moreImsiFlag;
    private String deviceForm;
    private String manuType;
    private String otaProtocolVersion;
    private String bipParamName;
    private String deviceStatus;
    private String cardMaterialCode;
    private String bipIsUpdate;
    private String isPurchase;
}
