package com.iot.otaBean.mo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class LUMo implements Serializable {

    /**
     *
     */
    private String currVLR;
    /**
     *码号唯一识别标识
     */
    private String imsi;
    /**
     *
     */
    private String luTime;
    /**
     *覆盖国家范围及运营商
     */
    private String mccmnc;
    /**
     *
     */
    private String referenceId;
}
