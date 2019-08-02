package com.iot.service.interfaces;

import com.iot.otaBean.io.request.LUInput;
import com.iot.otaBean.lUUploadRecord.LUUploadRecord;

/**
 *
 */
public interface ILUUploadRecordService {

    /**
     *插入一条数据到记录表中
     * @return 返回插入数据的id主键
     */
    Long insert(LUInput luInput);

    /**
     * 根据id更改
     *根据执行状态不同更新一条数据的businessType字段
     * @return 无
     */
    void updateBusinessType(Long id, Integer businessType);

    /**
     * 根据id查询数据
     * @return 返回查询到的结果
     */
    LUUploadRecord getById(Long id);
}
