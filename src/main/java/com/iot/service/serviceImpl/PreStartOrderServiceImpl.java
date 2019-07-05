package com.iot.service.serviceImpl;

import com.iot.dao.preStartOrderRecord.PreStartOrderRecordDao;
import com.iot.otaBean.preStartOrderRecord.PreStartOrderRecord;
import com.iot.service.interfaces.PreStartOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Slf4j
@RequiredArgsConstructor
public class PreStartOrderServiceImpl implements PreStartOrderService {

    private final PreStartOrderRecordDao preStartOrderRecordDao;
    /**
     *
     * @param iccid
     * @param imsi
     * @param orderId
     * @param accessoryImsi
     * @param expectedFinishTime
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void insert(String iccid, String imsi, String orderId, String accessoryImsi, String expectedFinishTime) {
        PreStartOrderRecord preStartOrderRecord = new PreStartOrderRecord();
        preStartOrderRecord.setId(1234L);
        preStartOrderRecord.setIccid(iccid);
        preStartOrderRecord.setImsi(imsi);
        preStartOrderRecord.setOrderId(orderId);
        preStartOrderRecord.setAccessoryImsi(accessoryImsi);
        preStartOrderRecord.setExpectedFinishTime(expectedFinishTime);
        preStartOrderRecord.setStartTime("");
        preStartOrderRecord.setStatus("0");
        Date now = new Date();
        preStartOrderRecord.setCreateTime(now);
        preStartOrderRecord.setUpdateTime(now);
        preStartOrderRecordDao.insert(preStartOrderRecord);
    }
}
