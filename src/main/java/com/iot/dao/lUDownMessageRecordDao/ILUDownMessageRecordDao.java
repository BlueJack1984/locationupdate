package com.iot.dao.lUDownMessageRecordDao;

import com.iot.otaBean.lUDownMessageRecord.LUDownMessageRecord;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ILUDownMessageRecordDao {

    /**
     * 将下行信息写入
     */
    int insert(LUDownMessageRecord luDownMessageRecord);
}
