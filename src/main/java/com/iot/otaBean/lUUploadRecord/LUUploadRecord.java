package com.iot.otaBean.lUUploadRecord;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.Date;

/**
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class LUUploadRecord implements Serializable {

    private Long id;

    private Integer businessType;

    private Date createTime;

    private Date updateTime;

    private String currentVLR;

    private String imsi;

    private String luTime;

    private String mccMnc;

    private String referenceId;
}
