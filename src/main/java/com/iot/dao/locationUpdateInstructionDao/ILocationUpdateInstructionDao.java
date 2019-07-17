package com.iot.dao.locationUpdateInstructionDao;

import com.iot.otaBean.locationUpdateInstruction.LocationUpdateInstruction;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author lushusheng
 * @date 2019-07-02
 */
@Mapper
public interface ILocationUpdateInstructionDao {

    /**
     * 获取位置更新指令表中数据列表
     */
    List<LocationUpdateInstruction> getList();

    /**
     * 插入一条位置更新指令数据
     * @param locationUpdateInstruction 需要插入的数据
     * @return 返回插入后的数据，便于回显
     */
    LocationUpdateInstruction insert(@Param("lu") LocationUpdateInstruction locationUpdateInstruction);

    /**
     *
     * @param instructionList
     * @return
     */
    int removeList(@Param("instructionList") List<LocationUpdateInstruction> instructionList);
}
