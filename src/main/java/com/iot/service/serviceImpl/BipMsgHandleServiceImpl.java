package com.iot.service.serviceImpl;

import com.iot.constant.SysConstants;
import com.iot.dao.IAssetSoftsimUsageDao.IAssetSoftsimUsageDao;
import com.iot.dao.assetManageBusiDao.IAssetManageBusiDao;
import com.iot.dao.assetOrderDao.IAssetOrderDao;
import com.iot.dao.deviceInitRecDao.IDeviceInitRecDao;
import com.iot.otaBean.assetManageBusiTypeEnum.AssetManageBusiTypeEnum;
import com.iot.otaBean.assetOrder.AssetOrder;
import com.iot.otaBean.deviceInitRec.DeviceInitRec;
import com.iot.otaBean.mo.BaseMo;
import com.iot.otaBean.mo.PorMo;
import com.iot.otaBean.mo.PositionMo;
import com.iot.otaBean.mt.MtData;
import com.iot.otaBean.mt.PlainDataMt;
import com.iot.service.interfaces.*;
import com.iot.util.DateUtils;
import com.packer.commons.sms.util.StringUtil;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("bipMsgHandleService")
public class BipMsgHandleServiceImpl implements BipMsgHandleService {
    private static final Log logger = LogFactory.getLog(BipMsgHandleServiceImpl.class);
    @Autowired
    USSDUnpackService ussdUnpackService;
    @Autowired
    IDeviceInitRecDao deviceInitRecDao;
    @Autowired
    SelectNumberService selectNumberService;
    @Autowired
    PushCommandService pushCommandService;
    @Autowired
    protected USSDPackService ussdBusiServicePack;
    @Autowired
    protected IAssetOrderDao assetOrderDao;
    @Autowired
    protected SelectOrderService selectOrderService;
    @Autowired
    private IAssetManageBusiDao assetManageBusiDao;
    @Autowired
    private IAssetSoftsimUsageDao assetSoftsimUsageDao;

    @Transactional(rollbackFor = Exception.class)
    public String bipMsgHandleService(String msg) throws Exception{
        String SMS = "";
        //在这里把tradeNo抽取出来，保证主号副号一致
        String tradeNo = getOtaTradeNo();
        BaseMo baseMo = ussdUnpackService.ussdBusiServiceUnpack(msg);
        if(baseMo.getCmdType().equals("31") || baseMo.getCmdType().equals("32")
                || baseMo.getCmdType().equals("33")) {
            PorMo porMo = (PorMo) baseMo;
            logger.info("Por上行消息：" + porMo.toString());
            List<DeviceInitRec> deviceInitRecs = deviceInitRecDao.queryInitRecByDeviceId(porMo.getImei());
            //检查上报信息
            if(!selectNumberService.checkMccAndDevice(null, deviceInitRecs)){
                return null;
            }
            DeviceInitRec deviceInitRec = deviceInitRecs.get(0);
            //选订单
            List<AssetOrder> startedOrders = assetOrderDao.queryStartedOrders(porMo.getImei(),
                    DateUtils.format(new Date(), DateUtils.YMD_DASH_WITH_TIME_SECOND));
            if(0 == startedOrders.size()){
                logger.error("设备" + porMo.getImei() + "的por没有已启动的订单");
                return null;
            }
            for(int i = 0; i < startedOrders.size(); i++){
                if("2".equals(startedOrders.get(i).getOrderPurpose())){
                    updateOrderException(startedOrders.get(i).getOrderId());
                }
            }
            assetSoftsimUsageDao.removeSeedIccidRecord(deviceInitRec);
            assetSoftsimUsageDao.insertFormalIccidRecord(deviceInitRec, "00000000000000000000",
                    "2", null);
        }else if(baseMo.getCmdType().equals("30")){
            PositionMo positionMo = (PositionMo) baseMo;
            List<DeviceInitRec> deviceInitRecs = deviceInitRecDao.queryInitRecByDeviceId(positionMo.getImei());
            //检查上报信息
            if(!selectNumberService.checkMccAndDevice(positionMo.getMcc(), deviceInitRecs)){
                return null;
            }
            DeviceInitRec deviceInitRec = deviceInitRecs.get(0);
            //ota设备的订单关联的副号被释放，pushcommand切主号

            if(pushCommandService.softsimRelease(deviceInitRec.getImei())
                    || pushCommandService.getPushCommand(deviceInitRec.getImei(),
                    AssetManageBusiTypeEnum.SWITCH_TO_GLOBAL.getBusiType())){
               return pushCommandService.switchGlobalBusi(positionMo, deviceInitRec.getImei());
            }
            if(positionMo.getpIccid().equals(deviceInitRec.getSeedIccid())){ //种子主号
                if(deviceInitRec.getSoftsimType().equals("1")){ //种子主号下主号
                    logger.info("种子主号下主号及副号一起返回");
                    MtData mtData = selectNumberService.selectNumber(tradeNo, deviceInitRec.getIccid(),
                            positionMo, deviceInitRec);
                    SMS = ussdBusiServicePack.ussdBusiServicePack(mtData);
                    return SMS;
                }else if(deviceInitRec.getSoftsimType().equals("2")){ //种子主号下副号
                    logger.info("种子主号下副号，只返回副号");
                    //选订单
                    AssetOrder assetOrder = selectOrderService.selectOrder(positionMo, deviceInitRec);
                    //选号码
                    PlainDataMt plainDataMt = selectNumberService.selectLocalNumber(tradeNo, assetOrder, positionMo,
                            deviceInitRec);
                    List<PlainDataMt> plainDatas = new ArrayList<>();
                    plainDatas.add(plainDataMt);
                    MtData mtData = new MtData();
                    mtData.setPlainDatas(plainDatas);
                    //添加的代码
                    mtData.setBusiType(positionMo.getBusiType());
                    mtData.setKeyIndex("0" + positionMo.getKeyIndex());
                    mtData.setCheckNum(positionMo.getCheckNum());
                    mtData.setManuFlag(positionMo.getManuFlag());
                    SMS = ussdBusiServicePack.ussdBusiServicePack(mtData);
                    return SMS;
                }else {
                    logger.error("设备状态不正确，softsimType必须是1或者2");
                    return null;
                }
            }else if(positionMo.getpIccid().equals(deviceInitRec.getNumberIccid())){ //主号下副号
                logger.info("主号下副号，只返回副号");
                //选订单
                AssetOrder assetOrder = selectOrderService.selectOrder(positionMo, deviceInitRec);
                //选号码
                PlainDataMt plainDataMt = selectNumberService.selectLocalNumber(tradeNo, assetOrder, positionMo,
                        deviceInitRec);
                List<PlainDataMt> plainDatas = new ArrayList<>();
                plainDatas.add(plainDataMt);
                MtData mtData = new MtData();
                mtData.setPlainDatas(plainDatas);
                //添加的代码
                mtData.setBusiType(positionMo.getBusiType());
                mtData.setKeyIndex("0" + positionMo.getKeyIndex());
                mtData.setCheckNum(positionMo.getCheckNum());
                mtData.setManuFlag(positionMo.getManuFlag());
                SMS = ussdBusiServicePack.ussdBusiServicePack(mtData);
                return SMS;
            }else {
                logger.error("慧银处理种子主号下主号");
                MtData mtData = selectNumberService.selectNumber(tradeNo, deviceInitRec.getIccid(),
                        positionMo, deviceInitRec);
                SMS = ussdBusiServicePack.ussdBusiServicePack(mtData);
                return SMS;
            }

        }else if(baseMo.getCmdType().equals("39")){

        }else {

        }
        return null;
    }
    private void updateOrderException(String orderCode) {
        AssetOrder order = assetOrderDao.queryOrderByCode(orderCode);
        String exceptionFlag = order.getExceptionFlag();
        String exceptionCause = order.getExceptionCause();
        String newFlag = null;
        String newCause = null;
        if(exceptionCause!=null && exceptionCause.contains(SysConstants.ORDER_POR_EXCEPTION)){
            if(exceptionCause.length() > 2){
                String[] causeArr = exceptionCause.split(";");
                while(ArrayUtils.contains(causeArr, SysConstants.ORDER_POR_EXCEPTION)){
                    causeArr = (String[]) ArrayUtils.removeElement(causeArr, SysConstants.ORDER_POR_EXCEPTION);
                }
                newCause = toString(causeArr);
                newFlag = exceptionFlag;
            }else{
                newFlag = SysConstants.ORDER_EXCEPTION_FLAG_NORMAL;
            }
            assetOrderDao.updateException(orderCode, newFlag, newCause);
        }

    }

    private String toString(String[] causeArr) {
        StringBuffer sb = new StringBuffer();
        for(String s: causeArr) {
            sb.append(s + ";");
        }
        return sb.toString().substring(0, sb.length() - 1);
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
