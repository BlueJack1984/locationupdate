package com.iot.otaBean.io.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
/**
 * LU的数据输入实体，格式参考如下：
 * {"currVLR":"8613442460","imsi":"454006310608183","luTime":"2019-06-20 11:27:07","mccmnc":"46000","referenceId":"-1543503414"}
 * {"currVLR":"84980202017","imsi":"454006310603351","luTime":"2019-06-20 11:26:52","mccmnc":"45204","referenceId":"2030305679"}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LUInput implements Serializable {

    /**
     *
     */
    @NotBlank(message = "currVLR参数不能为空")
    private String currVLR;
    /**
     *码号唯一识别标识
     */
    @NotBlank(message = "imsi参数不能为空")
    private String imsi;
    /**
     *
     */
    @NotBlank(message = "luTime参数不能为空")
    private String luTime;
    /**
     *覆盖国家范围及运营商
     */
    @NotBlank(message = "mccmnc参数不能为空")
    private String mccmnc;
    /**
     *
     */
    @NotBlank(message = "referenceId参数不能为空")
    private String referenceId;
}
