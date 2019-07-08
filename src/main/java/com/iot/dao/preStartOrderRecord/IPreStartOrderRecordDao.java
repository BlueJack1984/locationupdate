package com.iot.dao.preStartOrderRecord;

import com.iot.otaBean.preStartOrderRecord.PreStartOrderRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 *
 */
@Mapper
public interface IPreStartOrderRecordDao {

    /**
     *
     * @param preStartOrderRecord
     * @return
     */
    int insert(@Param("preStartOrderRecord") PreStartOrderRecord preStartOrderRecord);

}
