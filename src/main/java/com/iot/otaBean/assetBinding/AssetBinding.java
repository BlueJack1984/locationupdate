package com.iot.otaBean.assetBinding;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AssetBinding implements Serializable {

    private String id;
    private String assetId;
    private String imei;
}
