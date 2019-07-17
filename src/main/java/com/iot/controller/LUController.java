package com.iot.controller;

import com.iot.dao.assetManageBusiDao.IAssetManageBusiDao;
import com.iot.otaBean.deviceInitRec.DeviceInitRec;
import com.iot.otaBean.io.request.LUInput;
import com.iot.otaBean.assetOrder.AssetOrder;
import com.iot.otaBean.assetSoftsimUsage.AssetSoftsimUsage;
import com.iot.otaBean.locationUpdateInstruction.LocationUpdateInstruction;
import com.iot.otaBean.mo.PositionMo;
import com.iot.otaBean.mt.LUMtData;
import com.iot.otaBean.mt.LUPlainDataMt;
import com.iot.service.interfaces.*;
import com.iot.util.DateUtils;
import com.packer.commons.sms.util.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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
    private final PreStartOrderService preStartOrderService;
    protected final SelectOrderService selectOrderService;
    private final SelectNumberService selectNumberService;
    private final USSDPackService ussdBusiServicePack;
    private final IAssetManageBusiDao assetManageBusiDao;
    //定义锁
    private static final Lock lock = new ReentrantLock();

    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/luMsgHandle")
    public List<String> LUHandle(@RequestBody @Valid LUInput luInput) throws Exception{

        log.info("LU服务接收到的lu实体信息：" + luInput);
        List<String> SMS = new ArrayList<>();
        //查询location_position_instruction_t是否有写入下发要求
        List<LocationUpdateInstruction> instructionList = null;
        lock.lock();
        try {
            //数据量大时可以分批查询
            instructionList = instructionService.getList();
            if(null != instructionList && instructionList.size() > 0) {
                //将数据库的数据进行逻辑删除，保证其他线程查不到重复数据
                instructionService.removeList(instructionList);
            }
        }finally {
            lock.unlock();
        }
        if(null != instructionList && instructionList.size() > 0) {
            //把checksum置位AA55下发副号信息
            return handleOrderAndAccessoryImsi(instructionList);
        }
        log.info("location_update_instruction_t表数据为空");
        String imsi = luInput.getImsi();
        List<AssetSoftsimUsage> assetSoftsimUsageList = assetSoftsimUsageService.getListByImsi(imsi);
        if(null == assetSoftsimUsageList || assetSoftsimUsageList.size() < 1) {
            log.info("提示设备没有生产");
            return null;
        }
        //取出所有旅游卡iccid
        Set<String> iccidList = new HashSet<>();
        for(AssetSoftsimUsage assetSoftsimUsage : assetSoftsimUsageList) {
            String iccid = assetSoftsimUsage.getIccid();
            if(null != iccid && ! "".equals(iccid)) {
                iccidList.add(iccid);
            }
        }
        //根据iccid列表查出订单列表
        log.info("查询到的所有旅游卡iccid列表：" + iccidList);
        //mcc三位，mnc两位
        String mcc = luInput.getMccmnc().substring(0, 3);
        AssetOrder assetOrder = getByIccids(iccidList, mcc);
        if(null == assetOrder) {
            log.info("无法判断订单，不能下发副号");
            return null;
        }
        //组织数据下发副号,设置订单为预启用状态
        // 选号码
        String iccid = assetOrder.getAssetId();
        String orderId = assetOrder.getOrderId();
        String tradeNo = getOtaTradeNo();
        LUMtData luMtData = selectNumberService.selectAccessoryNumber(tradeNo, assetOrder, iccid, mcc);

        if(null == luMtData) {
            log.info("LU服务下发副号失败");
            return null;
        }
        //生成记录
        String accessoryImsi = luMtData.getLuPlainDataMtList().get(0).getLuCmdParamData().getImsi();
        String expectedFinishTime = assetOrder.getPlannedEndTime();
        preStartOrderService.insert(iccid, imsi, orderId, accessoryImsi, expectedFinishTime);
        //包装
        String sms = ussdBusiServicePack.ussdLUBusiServicePack(luMtData);
        log.info("LU下行消息：" + sms);
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
            log.info("");
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
            LUMtData luMtData = selectNumberService.selectAccessoryNumber(tradeNo, assetOrder, iccid, mcc);
            SMS = ussdBusiServicePack.ussdLUBusiServicePack(luMtData);
            log.info("LU下行消息集合：" + SMS);
            cache.add(SMS);
        }
        //返回下发副号相关信息
        return cache;
    }

    /**
     *
     * @param iccidList
     * @param mcc
     * @return
     */
    private AssetOrder getByIccids(Set<String> iccidList, String mcc) throws Exception{
        List<AssetOrder> cache = new ArrayList<>();
        if(null == iccidList || iccidList.size() < 1) {
            log.info("查询订单参数iccidList集合元素为空");
            return null;
        }
        for(String iccid: iccidList) {
            //复用原bip功能代码
            PositionMo positionMo = new PositionMo();
            DeviceInitRec deviceInitRec = new DeviceInitRec();
            positionMo.setImei(iccid);
            positionMo.setMcc(mcc);
            AssetOrder assetOrder = selectOrderService.selectOrder(positionMo, deviceInitRec);
            if(null != assetOrder) {
                String coverCountry = assetOrder.getCoverCountry();
                if(null != coverCountry && coverCountry.contains(mcc)) {
                    cache.add(assetOrder);
                    if(cache.size() > 1) {
                        return null;
                    }
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
