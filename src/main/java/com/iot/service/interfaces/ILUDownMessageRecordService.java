package com.iot.service.interfaces;

/**
 *
 */
public interface ILUDownMessageRecordService {

    /**
     * 插入一条数据到下行信息记录表中
     * @param uploadRecordId
     * @param otaTradeNo
     * @param downMessage
     * @param assetId
     */
    void insert(Long uploadRecordId, String otaTradeNo, String downMessage, String assetId);
}
