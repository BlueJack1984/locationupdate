package com.iot.dao.lUUploadRecordDao;

import com.iot.otaBean.lUUploadRecord.LUUploadRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

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

    /**
     * 查询一天内所有的LU上行消息记录
     * @param startTime 某一天的开始时间
     * @param endTime 某一天的结束时间
     * @return 返回特定某一天的所有上行消息记录
     */
    List<LUUploadRecord> getListOneDay(@Param("startTime") Date startTime, @Param("endTime")Date endTime);
}
