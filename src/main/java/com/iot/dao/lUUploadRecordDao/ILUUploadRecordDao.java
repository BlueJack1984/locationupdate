package com.iot.dao.lUUploadRecordDao;

import com.iot.otaBean.lUUploadRecord.LUUploadRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ILUUploadRecordDao {

    /**
     *
     * @param luUploadRecord
     * @return
     */
    int insert(@Param("luUploadRecord") LUUploadRecord luUploadRecord);

    /**
     *
     * @param id
     * @return
     */
    LUUploadRecord getById(@Param("id") Long id);
}
