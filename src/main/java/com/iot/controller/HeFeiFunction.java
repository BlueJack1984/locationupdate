package com.iot.controller;

import com.iot.dao.assetManageBusiDao.IAssetManageBusiDao;
import com.iot.dao.assetOrderDao.IAssetOrderDao;
import com.iot.otaBean.assetOrder.AssetOrder;
import com.iot.otaBean.mt.LUMtData;
import com.iot.service.interfaces.SelectNumberService;
import com.iot.service.interfaces.USSDPackService;
import com.iot.util.DateUtils;
import com.packer.commons.sms.util.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 下发副号
 * @author lushusheng
 * @date 2019-07-26
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class HeFeiFunction {

    private final IAssetOrderDao assetOrderDao;
    private final IAssetManageBusiDao assetManageBusiDao;
    private final USSDPackService ussdBusiServicePack;
    private final SelectNumberService selectNumberService;
    /**
     * 合肥分公司开发功能
     * @param assetId
     * @param orderId
     * @param mcc
     * @return
     */
    public String handle(String assetId, String orderId, String mcc) throws Exception{

        /**
         * 这里判断三个参数不为空：todo
         */
//        if(null == assetId || null == orderId || null == mcc
//                || assetId.trim().equals("") || orderId.trim().equals("") || mcc.trim().equals("")) {
//            log.info("请求参数不能为空");
//            return null;
//        }
        //查询订单
        AssetOrder assetOrder = assetOrderDao.queryOrderByCode(orderId);
        if(null == assetOrder) {
            log.info("查询到副号订单为空，不能下发副号");
            return null;
        }
        String coverCountry = assetOrder.getCoverCountry();
        if(null == coverCountry || ! coverCountry.contains(mcc)) {
            log.info("查询到的副号订单不包含mcc对应的覆盖国家，不能下发副号");
            return null;
        }
        //可以下发副号
        String tradeNo = getOtaTradeNo();
        LUMtData luMtData = selectNumberService.selectAccessoryNumber(tradeNo, assetOrder, assetId, mcc);
        if(null == luMtData) {
            log.info("选择副号服务下发副号失败");
            return null;
        }
        //包装
        String sms = ussdBusiServicePack.ussdLUBusiServicePack(luMtData);
        if(null == sms) {
            log.info("下发副号下行消息包装结果出错：null");
            return null;
        }
        log.info("下发副号下行消息包装结果：" + sms);
        return sms;
    }

    /**
     * 生成ota交易流水号
     *
     * @return
     */
    public String getOtaTradeNo() {
        Long nextVal = assetManageBusiDao.getOtaTradeNo();
        String tempId = Long.toString(nextVal);
        if (tempId.length() > 6) {
            tempId = tempId.substring(tempId.length() - 6, tempId.length());
        } else {
            tempId = StringUtil.paddingHeadZero(tempId, 6);
        }

        String sysTimeStr = DateUtils.format(new Date(), "yyyyMMddHHmmss");
        String tradeId = sysTimeStr + tempId;
        return tradeId;
    }
}
