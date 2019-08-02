package com.iot.service.serviceImpl;

import com.iot.dao.lUUploadRecordDao.ILUUploadRecordDao;
import com.iot.otaBean.io.request.LUInput;
import com.iot.otaBean.lUUploadRecord.LUUploadRecord;
import com.iot.service.interfaces.ILUUploadRecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
@RequiredArgsConstructor
public class LUUploadRecordServiceImpl implements ILUUploadRecordService {

    private final ILUUploadRecordDao luUploadRecordDao;
    /**
     *插入一条数据到记录表中
     */
    @Override
    public Long insert(LUInput luInput) {
        LUUploadRecord luUploadRecord = new LUUploadRecord();
        //初始状态为0,表示会正常下发副号信息
        luUploadRecord.setBusinessType(0);
        Date now = new Date();
        luUploadRecord.setCreateTime(now);
        luUploadRecord.setUpdateTime(now);
        luUploadRecord.setCurrentVLR(luInput.getCurrVLR());
        luUploadRecord.setImsi(luInput.getImsi());
        luUploadRecord.setLuTime(luInput.getLuTime());
        luUploadRecord.setMccMnc(luInput.getMccmnc());
        luUploadRecord.setReferenceId(luInput.getReferenceId());
        luUploadRecordDao.insert(luUploadRecord);
        Long id = luUploadRecord.getId();
        return id;
    }


    /**
     * 根据id更改
     *根据执行状态不同更新一条数据的businessType字段
     * @return 无
     */
    @Override
    public void updateBusinessType(Long id, Integer businessType) {
        if(null == id || null == businessType) {
            log.info("id或者businessType更新参数为空，无法操作！");
            return;
        }
        //首先查询出来
        LUUploadRecord luUploadRecord = getById(id);
        if(null == luUploadRecord) {
            log.info("数据库中没有查询到要更新的一条数据，无法操作！");
            return;
        }
        luUploadRecord.setBusinessType(businessType);
        luUploadRecord.setUpdateTime(new Date());
        luUploadRecordDao.insert(luUploadRecord);
    }
    /**
     * 根据id查询数据
     * @return 返回查询到的结果
     */
    @Override
    public LUUploadRecord getById(Long id) {

        if(null == id) {
            log.info("要查询的LUUploadRecord传入的id参数为空，查询为空！");
            return null;
        }
        LUUploadRecord luUploadRecord = luUploadRecordDao.getById(id);
        return luUploadRecord;
    }
}
