package com.iot.otaBean.manufacturerKey;


import lombok.Data;

import java.io.Serializable;

/**
 * 生产厂商密钥信息
 *
 * @author
 *
 */
//@Data
public class ManufacturerKey implements Serializable {

    private static final long serialVersionUID = -3557871420985089472L;

    private Long recId;

    private String manufacturerCode;

    private String manufacturerName;

    private String keyType;

    private String keyIndex;

    private String keyValue;

    private String createTime;

    public Long getRecId() {
        return recId;
    }

    public void setRecId(Long recId) {
        this.recId = recId;
    }

    public String getManufacturerCode() {
        return manufacturerCode;
    }

    public void setManufacturerCode(String manufacturerCode) {
        this.manufacturerCode = manufacturerCode;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public String getKeyType() {
        return keyType;
    }

    public void setKeyType(String keyType) {
        this.keyType = keyType;
    }

    public String getKeyIndex() {
        return keyIndex;
    }

    public void setKeyIndex(String keyIndex) {
        this.keyIndex = keyIndex;
    }

    public String getKeyValue() {
        return keyValue;
    }

    public void setKeyValue(String keyValue) {
        this.keyValue = keyValue;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
