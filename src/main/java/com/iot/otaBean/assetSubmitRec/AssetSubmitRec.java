package com.iot.otaBean.assetSubmitRec;

public class AssetSubmitRec {
    /**
     * 记录ID
     */
    private Long recId;

    /**
     * 对应的交易ID，即下行短信中的OTA trade No
     */
    private String tradeId;

    /**
     * 上报内容的类型:00-上报位置信息01-上报POR 99-解析失败，未知
     */
    private String submitType;

    /**
     * 设备唯一标识
     */
    private String assetId;

    /**
     * 设备主号手机号码
     */
    private String phoneNum;

    /**
     * 设备主号imsi
     */
    private String mainImsi;

    /**
     * 设备主号主号ICCID
     */
    private String mainIccid;

    /**
     * 上报的短信内容
     */
    private String submitContent;

    /**
     * 网络码
     */
    private String mnc;

    /**
     * 国家码
     */
    private String mcc;

    /**
     * 信息入库时间
     */
    private String submitDate;

    /**
     * 上行解析是否成功标识：0-解析成功；1-解析失败；2-乱码
     */
    private String normalFlag;

    /**
     * 主号归属的运营商编码
     */
    private String operatorCode;

    /**
     * 主号归属的供应商编码
     */
    private String supplierCode;
    
    /**
     * 通道类型: 1:USSD 2:BIP
     */
    private String channelType;
    private String imei;
    private String cellId;
    private String carryingCapacity;

    /**
     * 获取记录ID
     *
     * @return rec_id - 记录ID
     */
    public Long getRecId() {
        return recId;
    }

    /**
     * 设置记录ID
     *
     * @param recId 记录ID
     */
    public void setRecId(Long recId) {
        this.recId = recId;
    }

    /**
     * 获取对应的交易ID，即下行短信中的OTA trade No
     *
     * @return trade_id - 对应的交易ID，即下行短信中的OTA trade No
     */
    public String getTradeId() {
        return tradeId;
    }

    /**
     * 设置对应的交易ID，即下行短信中的OTA trade No
     *
     * @param tradeId 对应的交易ID，即下行短信中的OTA trade No
     */
    public void setTradeId(String tradeId) {
        this.tradeId = tradeId == null ? null : tradeId.trim();
    }

    /**
     * 获取上报内容的类型:00-上报位置信息01-上报POR 99-解析失败，未知
     *
     * @return submit_type - 上报内容的类型:00-上报位置信息01-上报POR 99-解析失败，未知
     */
    public String getSubmitType() {
        return submitType;
    }

    /**
     * 设置上报内容的类型:00-上报位置信息01-上报POR 99-解析失败，未知
     *
     * @param submitType 上报内容的类型:00-上报位置信息01-上报POR 99-解析失败，未知
     */
    public void setSubmitType(String submitType) {
        this.submitType = submitType == null ? null : submitType.trim();
    }

    /**
     * 获取设备唯一标识
     *
     * @return asset_id - 设备唯一标识
     */
    public String getAssetId() {
        return assetId;
    }

    /**
     * 设置设备唯一标识
     *
     * @param assetId 设备唯一标识
     */
    public void setAssetId(String assetId) {
        this.assetId = assetId == null ? null : assetId.trim();
    }

    /**
     * 获取设备主号手机号码
     *
     * @return phone_num - 设备主号手机号码
     */
    public String getPhoneNum() {
        return phoneNum;
    }

    /**
     * 设置设备主号手机号码
     *
     * @param phoneNum 设备主号手机号码
     */
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum == null ? null : phoneNum.trim();
    }

    /**
     * 获取设备主号imsi
     *
     * @return main_imsi - 设备主号imsi
     */
    public String getMainImsi() {
        return mainImsi;
    }

    /**
     * 设置设备主号imsi
     *
     * @param mainImsi 设备主号imsi
     */
    public void setMainImsi(String mainImsi) {
        this.mainImsi = mainImsi == null ? null : mainImsi.trim();
    }

    /**
     * 获取设备主号主号ICCID
     *
     * @return main_iccid - 设备主号主号ICCID
     */
    public String getMainIccid() {
        return mainIccid;
    }

    /**
     * 设置设备主号主号ICCID
     *
     * @param mainIccid 设备主号主号ICCID
     */
    public void setMainIccid(String mainIccid) {
        this.mainIccid = mainIccid == null ? null : mainIccid.trim();
    }

    /**
     * 获取上报的短信内容
     *
     * @return submit_content - 上报的短信内容
     */
    public String getSubmitContent() {
        return submitContent;
    }

    /**
     * 设置上报的短信内容
     *
     * @param submitContent 上报的短信内容
     */
    public void setSubmitContent(String submitContent) {
        this.submitContent = submitContent == null ? null : submitContent.trim();
    }

    /**
     * 获取网络码
     *
     * @return mnc - 网络码
     */
    public String getMnc() {
        return mnc;
    }

    /**
     * 设置网络码
     *
     * @param mnc 网络码
     */
    public void setMnc(String mnc) {
        this.mnc = mnc == null ? null : mnc.trim();
    }

    /**
     * 获取国家码
     *
     * @return mcc - 国家码
     */
    public String getMcc() {
        return mcc;
    }

    /**
     * 设置国家码
     *
     * @param mcc 国家码
     */
    public void setMcc(String mcc) {
        this.mcc = mcc == null ? null : mcc.trim();
    }

    /**
     * 获取信息入库时间
     *
     * @return submit_date - 信息入库时间
     */
    public String getSubmitDate() {
        return submitDate;
    }

    /**
     * 设置信息入库时间
     *
     * @param submitDate 信息入库时间
     */
    public void setSubmitDate(String submitDate) {
        this.submitDate = submitDate == null ? null : submitDate.trim();
    }

    /**
     * 获取上行解析是否成功标识：0-解析成功；1-解析失败；2-乱码
     *
     * @return normal_flag - 上行解析是否成功标识：0-解析成功；1-解析失败；2-乱码
     */
    public String getNormalFlag() {
        return normalFlag;
    }

    /**
     * 设置上行解析是否成功标识：0-解析成功；1-解析失败；2-乱码
     *
     * @param normalFlag 上行解析是否成功标识：0-解析成功；1-解析失败；2-乱码
     */
    public void setNormalFlag(String normalFlag) {
        this.normalFlag = normalFlag == null ? null : normalFlag.trim();
    }

    /**
     * 获取主号归属的运营商编码
     *
     * @return operator_code - 主号归属的运营商编码
     */
    public String getOperatorCode() {
        return operatorCode;
    }

    /**
     * 设置主号归属的运营商编码
     *
     * @param operatorCode 主号归属的运营商编码
     */
    public void setOperatorCode(String operatorCode) {
        this.operatorCode = operatorCode == null ? null : operatorCode.trim();
    }

    /**
     * 获取主号归属的供应商编码
     *
     * @return supplier_code - 主号归属的供应商编码
     */
    public String getSupplierCode() {
        return supplierCode;
    }

    /**
     * 设置主号归属的供应商编码
     *
     * @param supplierCode 主号归属的供应商编码
     */
    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode == null ? null : supplierCode.trim();
    }

	public String getChannelType() {
		return channelType;
	}

	public String getCellId() {
		return cellId;
	}

	public void setCellId(String cellId) {
		this.cellId = cellId;
	}

	public String getCarryingCapacity() {
		return carryingCapacity;
	}

	public void setCarryingCapacity(String carryingCapacity) {
		this.carryingCapacity = carryingCapacity;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}
}