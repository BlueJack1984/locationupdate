package com.iot.otaBean.lUDownMessageRecord;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class LUDownMessageRecord implements Serializable {

    private Long id;

    private Long uploadRecordId;

    private String tradeNo;

    private Date createTime;

    private String downMessage;

    private String assetId;
}
