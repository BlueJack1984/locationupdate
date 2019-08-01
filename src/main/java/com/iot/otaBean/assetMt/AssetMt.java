package com.iot.otaBean.assetMt;


public class AssetMt {
    /**
     * 记录ID
     */
    private Long mtSmsId;

    /**
     * OTA交易ID，即下行短信中的OTA trade No
     */
    private String tradeId;

    /**
     * 设备唯一标识
     */
    private String assetId;

    /**
     * 接收短信的手机号码
     */
    private String phoneNum;

    /**
     * 主号ICCID
     */
    private String mainIccid;

    /**
     * 下发的数据短信类型1-下发副号；2-更新主号；3-更新主号PLMN；4-锁定主号
     */
    private String mtType;

    /**
     * 下发短信内容
     */
    private String smsContent;

    /**
     * 短信下发时间
     */
    private String sendDate;

    /**
     * 主号归属的运营商编码
     */
    private String operatorCode;

    /**
     * 主号归属的供应商编码
     */
    private String supplierCode;

    /**
     * 下发短信的消息队列名称
     */
    private String queueName;

    /**
     * 发送次数，初始为1
     */
    private String sendCount;
    
    /**
     * dr接收时间
     */
    private String drReceiveDate;
    /**
     * dr提交时间
     */
    private String drSubmitDate;
    /**
     * ack时间
     */
    private String ack;
    /**
     * dr状态
     */
    private String dr;
    /**
     * seqId,领科生成,长度16进制8位
     */
    private String seqId;
    /**
     * 短信唯一id，运营商生成
     */
    private String smscId;

    private String imsi;
    

    private String protocolVersion;

    private String batchNumber;

    private String msgTotalNumber;

    private String msgIndex;

    /**
     * 获取记录ID
     *
     * @return mt_sms_id - 记录ID
     */
    public Long getMtSmsId() {
        return mtSmsId;
    }

    /**
     * 通道类型: 1:USSD 2:BIP
     */

    private String channelType;

    private String imei;
    /**
     * 设置记录ID
     *
     * @param mtSmsId 记录ID
     */
    public void setMtSmsId(Long mtSmsId) {
        this.mtSmsId = mtSmsId;
    }

    /**
     * 获取OTA交易ID，即下行短信中的OTA trade No
     *
     * @return trade_id - OTA交易ID，即下行短信中的OTA trade No
     */
    public String getTradeId() {
        return tradeId;
    }

    @Override
	public String toString() {
		return "AssetMt [mtSmsId=" + mtSmsId + ", tradeId=" + tradeId
				+ ", assetId=" + assetId + ", phoneNum=" + phoneNum
				+ ", mainIccid=" + mainIccid + ", mtType=" + mtType
				+ ", smsContent=" + smsContent + ", sendDate=" + sendDate
				+ ", operatorCode=" + operatorCode + ", supplierCode="
				+ supplierCode + ", queueName=" + queueName + ", sendCount="
				+ sendCount + ", drReceiveDate=" + drReceiveDate
				+ ", drSubmitDate=" + drSubmitDate + ", ack=" + ack + ", dr="
				+ dr + ", seqId=" + seqId + ", smscId=" + smscId + ", imsi="
				+ imsi + ", protocolVersion=" + protocolVersion
				+ ", batchNumber=" + batchNumber + ", msgTotalNumber="
				+ msgTotalNumber + ", msgIndex=" + msgIndex + ", channelType="
				+ channelType + ", imei=" + imei + "]";
	}

	/**
     * 设置OTA交易ID，即下行短信中的OTA trade No
     *
     * @param tradeId OTA交易ID，即下行短信中的OTA trade No
     */
    public void setTradeId(String tradeId) {
        this.tradeId = tradeId == null ? null : tradeId.trim();
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
     * 获取接收短信的手机号码
     *
     * @return phone_num - 接收短信的手机号码
     */
    public String getPhoneNum() {
        return phoneNum;
    }

    /**
     * 设置接收短信的手机号码
     *
     * @param phoneNum 接收短信的手机号码
     */
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum == null ? null : phoneNum.trim();
    }

    /**
     * 获取主号ICCID
     *
     * @return main_iccid - 主号ICCID
     */
    public String getMainIccid() {
        return mainIccid;
    }

    /**
     * 设置主号ICCID
     *
     * @param mainIccid 主号ICCID
     */
    public void setMainIccid(String mainIccid) {
        this.mainIccid = mainIccid == null ? null : mainIccid.trim();
    }

    /**
     * 获取下发的数据短信类型1-下发副号；2-更新主号；3-更新主号PLMN；4-锁定主号
     *
     * @return mt_type - 下发的数据短信类型1-下发副号；2-更新主号；3-更新主号PLMN；4-锁定主号
     */
    public String getMtType() {
        return mtType;
    }

    /**
     * 设置下发的数据短信类型1-下发副号；2-更新主号；3-更新主号PLMN；4-锁定主号
     *
     * @param mtType 下发的数据短信类型1-下发副号；2-更新主号；3-更新主号PLMN；4-锁定主号
     */
    public void setMtType(String mtType) {
        this.mtType = mtType == null ? null : mtType.trim();
    }

    /**
     * 获取下发短信内容
     *
     * @return sms_content - 下发短信内容
     */
    public String getSmsContent() {
        return smsContent;
    }

    /**
     * 设置下发短信内容
     *
     * @param smsContent 下发短信内容
     */
    public void setSmsContent(String smsContent) {
        this.smsContent = smsContent == null ? null : smsContent.trim();
    }

    /**
     * 获取短信下发时间
     *
     * @return send_date - 短信下发时间
     */
    public String getSendDate() {
        return sendDate;
    }

    /**
     * 设置短信下发时间
     *
     * @param sendDate 短信下发时间
     */
    public void setSendDate(String sendDate) {
        this.sendDate = sendDate == null ? null : sendDate.trim();
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

    public String getDrSubmitDate() {
		return drSubmitDate;
	}

	public void setDrSubmitDate(String drSubmitDate) {
		this.drSubmitDate = drSubmitDate;
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

    /**
     * 获取下发短信的消息队列名称
     *
     * @return queue_name - 下发短信的消息队列名称
     */
    public String getQueueName() {
        return queueName;
    }

    /**
     * 设置下发短信的消息队列名称
     *
     * @param queueName 下发短信的消息队列名称
     */
    public void setQueueName(String queueName) {
        this.queueName = queueName == null ? null : queueName.trim();
    }

    /**
     * 获取发送次数，初始为1
     *
     * @return send_count - 发送次数，初始为1
     */
    public String getSendCount() {
        return sendCount;
    }

    /**
     * 设置发送次数，初始为1
     *
     * @param sendCount 发送次数，初始为1
     */
    public void setSendCount(String sendCount) {
        this.sendCount = sendCount == null ? null : sendCount.trim();
    }

	public String getChannelType() {
		return channelType;
	}

	public String getSeqId() {
		return seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}

	public String getProtocolVersion() {
		return protocolVersion;
	}

	public void setProtocolVersion(String protocolVersion) {
		this.protocolVersion = protocolVersion;
	}

	public String getBatchNumber() {
		return batchNumber;
	}

	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}

	public String getMsgTotalNumber() {
		return msgTotalNumber;
	}

	public void setMsgTotalNumber(String msgTotalNumber) {
		this.msgTotalNumber = msgTotalNumber;
	}

	public String getMsgIndex() {
		return msgIndex;
	}

	public void setMsgIndex(String msgIndex) {
		this.msgIndex = msgIndex;
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public String getSmscId() {
		return smscId;
	}

	public void setSmscId(String smscId) {
		this.smscId = smscId;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public String getImei() {
		return imei;
	}

	public String getAck() {
		return ack;
	}

	public void setAck(String ack) {
		this.ack = ack;
	}

	public String getDr() {
		return dr;
	}

	public void setDr(String dr) {
		this.dr = dr;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getDrReceiveDate() {
		return drReceiveDate;
	}

	public void setDrReceiveDate(String drReceiveDate) {
		this.drReceiveDate = drReceiveDate;
	}
}