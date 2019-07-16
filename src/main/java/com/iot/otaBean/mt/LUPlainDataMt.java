package com.iot.otaBean.mt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用于包装待加密的LUCmdParamData实体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LUPlainDataMt implements Serializable {

    private String cmdType;//请求非空

    private String cmdLength;//请求为空，需服务计算

    private LUCmdParamData luCmdParamData;

}
