package com.iot.otaBean.io.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class HeFeiInput implements Serializable {

    /**
     * 设备id
     */
    @NotBlank(message = "设备id参数不能为空")
    private String assetId;
    /**
     * 订单id
     */
    @NotBlank(message = "订单id参数不能为空")
    private String orderId;
    /**
     * 覆盖国家
     */
    @NotBlank(message = "覆盖国家mcc参数不能为空")
    private String mcc;
}
