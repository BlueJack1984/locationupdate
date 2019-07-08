package com.iot.controller;

import com.iot.dao.assetManageBusiDao.IAssetManageBusiDao;
import com.iot.otaBean.io.request.LUInput;
import com.iot.otaBean.assetOrder.AssetOrder;
import com.iot.otaBean.assetSoftsimUsage.AssetSoftsimUsage;
import com.iot.otaBean.locationUpdateInstruction.LocationUpdateInstruction;
import com.iot.otaBean.mt.MtData;
import com.iot.otaBean.mt.PlainDataMt;
import com.iot.service.interfaces.*;
import com.iot.util.DateUtils;
import com.packer.commons.sms.util.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 订单业务
 * @author lushusheng
 * @date 2019-06-18
 * @description
 */
@RestController
@RequestMapping("/lu")
@RequiredArgsConstructor
@Slf4j
public class LUController {

    private final LocationUpdateInstructionService instructionService;
    private final AssetSoftsimUsageService assetSoftsimUsageService;
    private final AssetOrderService assetOrderService;
    private final PreStartOrderService preStartOrderService;
    protected final SelectOrderService selectOrderService;
    private final SelectNumberService selectNumberService;
    private final USSDPackService ussdBusiServicePack;
    private final IAssetManageBusiDao assetManageBusiDao;

    @PostMapping("/luMsgHandle")
    public List<String> LUHandle(@RequestBody @Valid LUInput luInput) throws Exception{

        List<String> SMS = new ArrayList<>();
        //查询location_position_instruction_t是否有写入下发要求
        List<LocationUpdateInstruction> instructionList = instructionService.getList();
        if(null != instructionList && instructionList.size() > 0) {
            //这里设置订单为启用状态
            //把checksum置位AA55下发副号信息
            return handleOrderAndAccessoryImsi(instructionList);
        }
        String imsi = luInput.getImsi();
        List<AssetSoftsimUsage> assetSoftsimUsageList = assetSoftsimUsageService.getListByImsi(imsi);
        if(null == assetSoftsimUsageList || assetSoftsimUsageList.size() < 1) {
            log.info("提示设备没有生产");
            return null;
        }
        //取出所有旅游卡iccid
        List<String> iccidList = new ArrayList<>();
        for(AssetSoftsimUsage assetSoftsimUsage : assetSoftsimUsageList) {
            String iccid = assetSoftsimUsage.getIccid();
            if(null != iccid && ! "".equals(iccid)) {
                iccidList.add(iccid);
            }
        }
        //根据iccid列表查出订单列表
        //mcc三位，mnc两位
        String mcc = luInput.getMccmnc().substring(0, 3);
        AssetOrder assetOrder = getByIccids(iccidList, mcc);
        if(null == assetOrder) {
            log.info("无法判断订单，不能下发副号");
            return null;
        }
        //设置订单为预启用状态，生成记录
        String iccid = assetOrder.getAssetId();
        String orderId = assetOrder.getOrderId();
        String accessoryImsi = "";
        String expectedFinishTime = assetOrder.getPlannedEndTime();
        preStartOrderService.insert(iccid, imsi, orderId, accessoryImsi, expectedFinishTime);
        //组织数据下发副号
        // 选号码
        String tradeNo = getOtaTradeNo();
        PlainDataMt plainDataMt = selectNumberService.selectAccessoryNumber(tradeNo, assetOrder, iccid, mcc);
        List<PlainDataMt> plainDatas = new ArrayList<>();
        plainDatas.add(plainDataMt);
        MtData mtData = new MtData();
        mtData.setPlainDatas(plainDatas);
        String sms = ussdBusiServicePack.ussdBusiServicePack(mtData);
        SMS.add(sms);
        return SMS;
    }

    /**
     *
     * @param instructionList
     * @return
     */
    private List<String> handleOrderAndAccessoryImsi(List<LocationUpdateInstruction> instructionList) throws Exception{
        if(null == instructionList || instructionList.size() < 1) {
            return null;
        }
        String SMS = "";
        List<String> cache = new ArrayList<>();
        for(LocationUpdateInstruction luInstruction: instructionList) {
            String iccid = luInstruction.getIccid();
            String mcc = luInstruction.getMcc();
            if(null == iccid || null == mcc) {
                continue;
            }
            String tradeNo = getOtaTradeNo();
            //获取订单信息
            AssetOrder assetOrder = selectOrderService.getOrder(iccid, mcc);
            //选号码
            PlainDataMt plainDataMt = selectNumberService.selectAccessoryNumber(tradeNo, assetOrder, iccid, mcc);
            List<PlainDataMt> plainDatas = new ArrayList<>();
            plainDatas.add(plainDataMt);
                    MtData mtData = new MtData();
            mtData.setPlainDatas(plainDatas);
            SMS = ussdBusiServicePack.ussdBusiServicePack(mtData);
            cache.add(SMS);
        }
        //设置订单为启用状态
        //下发副号相关信息
        return cache;
    }

    /**
     *
     * @param iccidList
     * @param mcc
     * @return
     */
    private AssetOrder getByIccids(List<String> iccidList, String mcc) {
        List<AssetOrder> cache = new ArrayList<>();
        List<AssetOrder> list = assetOrderService.getListByIccids(iccidList);
        for(AssetOrder assetOrder: list) {
            String coverCountry = assetOrder.getCoverCountry();
            if(null != coverCountry && coverCountry.contains(mcc)) {
                cache.add(assetOrder);
                if(cache.size() > 1) {
                    return null;
                }
            }
        }
        if(cache.size() < 1) {
            return null;
        }
        return cache.get(0);
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
