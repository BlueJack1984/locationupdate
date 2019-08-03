package com.iot.service.serviceImpl;

import com.iot.dao.lUDownMessageRecordDao.ILUDownMessageRecordDao;
import com.iot.otaBean.lUDownMessageRecord.LUDownMessageRecord;
import com.iot.service.interfaces.ILUDownMessageRecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 *
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class LUDownMessageRecordServiceImpl implements ILUDownMessageRecordService {

    private final ILUDownMessageRecordDao luDownMessageRecordDao;
    /**
     * 插入一条数据到下行信息记录表中
     * @param uploadRecordId 上行记录id
     * @param tradeNo 交易流水号
     * @param downMessage 下行消息
     * @param assetId 设备唯一识别标识
     */
    @Override
    public void insert(Long uploadRecordId, String tradeNo, String downMessage, String assetId) {
        LUDownMessageRecord luDownMessageRecord = new LUDownMessageRecord();
        luDownMessageRecord.setUploadRecordId(uploadRecordId);
        luDownMessageRecord.setTradeNo(tradeNo);
        luDownMessageRecord.setDownMessage(downMessage);
        luDownMessageRecord.setAssetId(assetId);
        luDownMessageRecord.setCreateTime(new Date());
        luDownMessageRecordDao.insert(luDownMessageRecord);
    }
}
