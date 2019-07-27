package com.iot.otaBean.assetBound;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AssetBound implements Serializable {

    private Long id;

    private String assetId;

    private String imei;

    private String updateTime;

    private String boundTime;
}
