package com.iot.otaBean.assetInfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssetInfo implements Serializable {

    `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `asset_id` varchar(30) NOT NULL COMMENT '???????',
            `iccid` varchar(20) DEFAULT NULL COMMENT '????ICCID',
            `imsi` varchar(15) DEFAULT NULL COMMENT '????imsi',
            `msisdn` varchar(20) DEFAULT NULL COMMENT 'msisdn',
            `outside_asset_id` varchar(30) DEFAULT NULL COMMENT '?????????',
            `asset_footprint` varchar(30) DEFAULT NULL COMMENT '?',
            `roamingsim_code` varchar(50) DEFAULT NULL COMMENT 'Roamingsim',
            `version` varchar(30) DEFAULT NULL COMMENT 'Roamingsim????',
            `crc` varchar(10) DEFAULT NULL COMMENT 'crc',
            `chip_model` varchar(50) DEFAULT NULL COMMENT '??',
            `asset_name` varchar(30) DEFAULT NULL,
  `pn` varchar(50) DEFAULT NULL COMMENT 'pn',
            `lifecycle` varchar(2) DEFAULT NULL COMMENT '?',
            `lifecycle_start_time` varchar(30) DEFAULT NULL,
  `lifecycle_end_time` varchar(30) DEFAULT NULL COMMENT '??????????????????YYYYMMDD',
            `task_no` varchar(100) DEFAULT NULL,
  `manufacturer_code` varchar(6) DEFAULT NULL COMMENT '???????????',
            `manufacturer_name` varchar(50) DEFAULT NULL,
  `in_time` varchar(30) DEFAULT NULL,
  `activate_flag` varchar(2) DEFAULT '0' COMMENT '?????????????????????????????????????????????????????????????????�?\r\n0-?????\r\n1-?????\r\n',
            `activate_time` varchar(30) DEFAULT NULL COMMENT '???????',
            `partner_name` varchar(50) DEFAULT NULL COMMENT '?????????????????',
            `partner_code` varchar(32) DEFAULT NULL COMMENT '????????????',
            `last_report_time` varchar(30) DEFAULT NULL COMMENT '???????????????????????',
            `last_report_position_mcc` varchar(20) DEFAULT NULL COMMENT '????????????�??????????????',
            `last_report_position_name` varchar(20) DEFAULT NULL COMMENT '????????????�?????????????',
            `last_report_operator_code` varchar(20) DEFAULT NULL COMMENT '????????????�??????????',
            `last_report_operator_name` varchar(50) DEFAULT NULL COMMENT '????????????�???????????',
            `abandoned_time` varchar(30) DEFAULT NULL COMMENT '???????????',
            `abandoned_cause` varchar(100) DEFAULT NULL COMMENT '?????????????',
            `data_encrypt_factor` varchar(20) DEFAULT NULL COMMENT '?????????????????????????????????iccid?????',
            `package_type` varchar(2) DEFAULT NULL COMMENT '?????????????????0-?????? 1-??????????2-??????????3-????????????4-????????????5-??????????',
            `package_code` varchar(30) DEFAULT NULL COMMENT '??????�???????????',
            `package_name` varchar(128) DEFAULT NULL,
  `package_flow` varchar(8) DEFAULT NULL COMMENT '?????????????(???MB)',
            `order_unint_dt` varchar(30) DEFAULT '\\' COMMENT '???????????????�????????(???KB)',
            `package_use_rate` varchar(20) DEFAULT '\\' COMMENT '?????????',
            `assign_time` varchar(30) DEFAULT NULL COMMENT '???????',
            `order_id` varchar(20) DEFAULT NULL COMMENT '?????????ID????????YYYYMMDD+10???????',
            `is_online` varchar(2) DEFAULT '0' COMMENT ' ??????? 0-?????? 1-????',
            `last_login_country` varchar(100) DEFAULT NULL COMMENT '???????????????? ??????-?????',
            `last_login_operator` varchar(100) DEFAULT NULL COMMENT '??????�?????????? ??????-?????',
            `last_login_time` varchar(30) DEFAULT NULL COMMENT '??????�?????',
            `silent_cycle` varchar(100) DEFAULT NULL COMMENT '???????',
            `stop_service_period` varchar(100) DEFAULT NULL COMMENT '???????????',
            `remark` varchar(300) DEFAULT NULL COMMENT '??????????',
            `operator_code` varchar(4) DEFAULT NULL COMMENT '?????????????????',
            `operator_name` varchar(50) DEFAULT NULL COMMENT '??????????????????',
            `supplier_code` varchar(4) DEFAULT NULL COMMENT '????????????????',
            `supplier_name` varchar(50) DEFAULT NULL COMMENT '?????????????????',
            `init_flag` varchar(2) DEFAULT '0' COMMENT '??????????0-?????????(??????????????);1-??????; 2-??????',
            `ota_flag` varchar(2) DEFAULT '2' COMMENT '?????OTA????:2-�??; 1-??',
            `bip_flag` varchar(2) DEFAULT '0' COMMENT '?????BIP????:0-�??; 1-??',
            `inuse_industry` varchar(50) DEFAULT NULL COMMENT '???????:1-????????2-????????3-????????4-??????5-??????/?????6-????????7-?????????8-????????9-???????10-??????11-?????12-????????13???? 14????????? 15????�?????? 16????????? 17 ??????? 18?????�???? 19?? ?????�??? 20-?????�????',
            `more_imsi_flag` varchar(2) DEFAULT NULL COMMENT '??????imsi????0- ????1-??',
            `device_form` varchar(2) DEFAULT NULL,
  `manu_type` varchar(20) DEFAULT NULL COMMENT '???????:1-?????????????????2-?????????????????3-????????????????????4-??????????????????5-??????6-OTA?????????7-??????8-?????9-?????????;',
            `ota_protocol_version` varchar(10) DEFAULT NULL COMMENT 'ota???????',
            `bip_param_name` varchar(50) DEFAULT NULL COMMENT 'bip???????????',
            `device_status` varchar(2) DEFAULT NULL COMMENT '???? 1-??? 2-???',
            `card_material_code` varchar(20) DEFAULT NULL COMMENT '???????',
            `bip_is_update` varchar(20) DEFAULT '0' COMMENT '???bip???????:0??? 1???',
            `is_purchase` varchar(2) DEFAULT NULL COMMENT '???????0??
}
