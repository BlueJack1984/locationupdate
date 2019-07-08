package com.iot.dao.IPlmnIndexDao;

import com.iot.otaBean.plmn.PlmnIndex;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IPlmnIndexDao {
    public List<PlmnIndex> getAllPlmnIndex();
}
