package com.iot.service.serviceImpl;

import com.iot.constant.SysConstants;
import com.iot.dao.assetManageBusiDao.IAssetManageBusiDao;
import com.iot.dao.assetOrderSoftsimUsageDao.IAssetOrderSoftsimUsageDao;
import com.iot.dao.primaryResourceImsiDao.IPrimaryResourceImsiDao;
import com.iot.dao.primaryResourceNumberDao.IPrimaryResourceNumberDao;
import com.iot.dao.softSimResourceImsiDao.ISoftSimResourceImsiDao;
import com.iot.dao.softSimResourceInfoDao.ISoftSimResourceInfoDao;
import com.iot.dao.supplierDao.ISupplierDao;
import com.iot.otaBean.assetOrder.AssetOrder;
import com.iot.otaBean.assetOrderSoftsimUsage.AssetOrderSoftsimUsage;
import com.iot.otaBean.cmdTypeEnum.CmdTypeEnum;
import com.iot.otaBean.deviceInitRec.DeviceInitRec;
import com.iot.otaBean.mo.LUMo;
import com.iot.otaBean.mo.PositionMo;
import com.iot.otaBean.mt.CmdParamData;
import com.iot.otaBean.mt.MtData;
import com.iot.otaBean.mt.PlainDataMt;
import com.iot.otaBean.orderSys.selectLocalSoftSimResponse.SelectLocalSoftSimResponse;
import com.iot.otaBean.primaryResourceImsi.PrimaryResourceImsi;
import com.iot.otaBean.primaryResourceNumber.PrimaryResourceNumber;
import com.iot.otaBean.selectLocalSoftSimRequest.SelectLocalSoftSimRequest;
import com.iot.otaBean.softSimResourceImsi.SoftSimResourceImsi;
import com.iot.otaBean.softSimResourceInfo.SoftSimResourceInfo;
import com.iot.otaBean.supplier.Supplier;
import com.iot.service.interfaces.SelectNumberService;
import com.iot.service.interfaces.SelectOrderService;
import com.iot.util.*;
import com.packer.commons.sms.crypto.LF3DesCryptoUtil;
import com.packer.commons.sms.jce.JceBase;
import com.packer.commons.sms.util.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//@Service("SelectNumberService")
@Service
public class SelectNumberServiceImpl implements SelectNumberService {
    private static final Log logger = LogFactory.getLog(SelectNumberServiceImpl.class);
    @Autowired
    IPrimaryResourceNumberDao primaryResourceNumberDao;
    @Autowired
    IAssetManageBusiDao assetManageBusiDao;
    @Autowired
    IPrimaryResourceImsiDao primaryResourceImsiDao;
    @Autowired
    ISupplierDao supplierDao;
    @Autowired
    ISoftSimResourceInfoDao softSimResourceInfoDao;
    @Autowired
    ISoftSimResourceImsiDao softSimResourceImsiDao;
    @Autowired
    SelectOrderService selectOrderService;
    @Autowired
    private IAssetOrderSoftsimUsageDao assetOrderSoftsimUsageDao;

    public MtData selectNumber(String tradeNo, String iccid, PositionMo positionMo, DeviceInitRec deviceInitRec) throws Exception {
        logger.info("组织主号下发短信！");
        MtData mtData = new MtData();
        List<PlainDataMt> plainDatas = new ArrayList<>();
        List<PrimaryResourceNumber> primaryResourceNumbers = primaryResourceNumberDao.queryPrimaryByIccid(iccid);
        if(1 != primaryResourceNumbers.size()){
            logger.info("iccid为"+iccid+"的主号在主号表中存在多个或者不存在！");
            return null;
        }
        PrimaryResourceNumber primaryResourceNumber = primaryResourceNumbers.get(0);
        plainDatas = getPlainDatasObj(tradeNo, primaryResourceNumber, positionMo, deviceInitRec);
        mtData.setPlainDatas(plainDatas);
        //mtData.
        mtData.setBusiType(positionMo.getBusiType());
        mtData.setKeyIndex("0" + positionMo.getKeyIndex());
        mtData.setCheckNum(positionMo.getCheckNum());
        mtData.setManuFlag(positionMo.getManuFlag());
        return mtData;
    }
    private List<PlainDataMt> getPlainDatasObj(String tradeNo, PrimaryResourceNumber primaryResourceNumber,
                                               PositionMo positionMo, DeviceInitRec deviceInitRec) throws Exception {
        List<PlainDataMt> plainDatas = new ArrayList<>();
        plainDatas.add(getPlainDataMtObj(tradeNo, primaryResourceNumber,positionMo,
                deviceInitRec,primaryResourceNumber.getImsi()));
        if(!"FFFFFFFFFFFFFFF".equals(primaryResourceNumber.getImsi2())){
            plainDatas.add(getPlainDataMtObj(tradeNo,primaryResourceNumber,positionMo,
                    deviceInitRec, primaryResourceNumber.getImsi2()));
        }
        if(!"FFFFFFFFFFFFFFF".equals(primaryResourceNumber.getImsi3())){
            plainDatas.add(getPlainDataMtObj(tradeNo,primaryResourceNumber,positionMo,
                    deviceInitRec, primaryResourceNumber.getImsi3()));
        }
        if(!"FFFFFFFFFFFFFFF".equals(primaryResourceNumber.getImsi4())){
            plainDatas.add(getPlainDataMtObj(tradeNo,primaryResourceNumber,positionMo,
                    deviceInitRec, primaryResourceNumber.getImsi4()));
        }
        if(!"FFFFFFFFFFFFFFF".equals(primaryResourceNumber.getImsi5())){
            plainDatas.add(getPlainDataMtObj(tradeNo,primaryResourceNumber,positionMo,
                    deviceInitRec, primaryResourceNumber.getImsi5()));
        }
        //选订单
        AssetOrder assetOrder = selectOrderService.selectOrder(positionMo, deviceInitRec);
        //选号码
        if(null != assetOrder) {
            plainDatas.add(selectLocalNumber(tradeNo, assetOrder, positionMo, deviceInitRec));
        }
        return plainDatas;
    }

    private PlainDataMt getPlainDataMtObj(String tradeNo, PrimaryResourceNumber primaryResourceNumber,
                                       PositionMo positionMo, DeviceInitRec deviceInitRec, String imsi) throws Exception {
        String UssdPre = "";
        String plmn = "";
        PlainDataMt plainDataMt = new PlainDataMt();
        CmdParamData cmdParamData = new CmdParamData();
        cmdParamData.setOtaTradeNo(tradeNo);
        cmdParamData.setOldIccid(positionMo.getpIccid());
        cmdParamData.setNewIccid(deviceInitRec.getNumberIccid());
        cmdParamData.setpIccid(positionMo.getpIccid());
        cmdParamData.setImsi(ResourceUtil.getEfImsi(primaryResourceNumber,imsi));
        cmdParamData.setDataKeyIndex(positionMo.getKeyIndex());
        cmdParamData.setCallControl(primaryResourceNumber.getCallFlag());
        List<PrimaryResourceImsi> pris = primaryResourceImsiDao.queryPriByIccidAndImsi(
                primaryResourceNumber.getIccid(), imsi);
        if(1 != pris.size()){
            logger.error("iccid为"+primaryResourceNumber.getIccid()+"imsi为"+imsi+"的主号多于一个或者不存在！");
            return null;
        }
        PrimaryResourceImsi pri = pris.get(0);
        cmdParamData.setAlgFlag(StringUtil.paddingHeadZero(pri.getAlgFlag(),2));
        //USSD prefix
        Supplier supplier = supplierDao.querySupplierByCode(primaryResourceNumber.getSupplierCode());
        if(!StringHelper.isEmpty(supplier.getUssdPre())){
            UssdPre = supplier.getUssdPre();
        }
        UssdPre = StringUtil.paddingTailZero(StringUtil.asc2hex(UssdPre), 36);
        cmdParamData.setUssdPrefix(UssdPre);
        if(!StringHelper.isEmpty(pri.getPlmn())){
            if(pri.getPlmn().length()> SysConstants.MAX_MAIN_PLMN_FIRST_LENGTH){
                plmn = pri.getPlmn().substring(0,SysConstants.MAX_MAIN_PLMN_FIRST_LENGTH);
            }else{
                plmn = StringUtil.paddingTail(pri.getPlmn(), SysConstants.MAX_MAIN_PLMN_FIRST_LENGTH, "FF");
            }
        }else{
            plmn="";
        }
        cmdParamData.setPlmn(ResourceUtil.generatePLMNIndexList(plmn, pri.getCoverCountry()));
        String dataKeyIndex = "0" + (new Random().nextInt(5) + 1);
        cmdParamData.setDataKeyIndex(dataKeyIndex);
        String deKI = KeyUtil.decryptKIorOPC(
                Integer.parseInt(primaryResourceNumber.getKeyIndex()),
                primaryResourceNumber.getKi()).substring(0, 32);
        String deOPC = KeyUtil.decryptKIorOPC(
                Integer.parseInt(primaryResourceNumber.getKeyIndex()),
                primaryResourceNumber.getOpc()).substring(0, 32);
        String sessionKey = ResourceUtil
                .calcSessionKey(SysConstants.PERS_DATA_KEY.get(positionMo
                                .getManuFlag())[Integer.parseInt(dataKeyIndex) - 1],
                        positionMo.getpIccid(), tradeNo);//首次更新
        String keyData = LF3DesCryptoUtil.ecb_encrypt(sessionKey, deKI + deOPC,
                JceBase.Padding.NoPadding);
        cmdParamData.setKeyData(keyData);
        cmdParamData.setCoverMcc(pri.getCoverCountry());
        cmdParamData.setApn(primaryResourceNumber.getApn());
        String[] bipIps = deviceInitRec.getBipIp().split("\\.");
        String cmdParam = StringUtil.paddingHeadZero(Integer.toString(deviceInitRec.getRepeatReportRate().intValue(), 16), 2)
                +StringUtil.paddingHeadZero(Integer.toString(deviceInitRec.getRetryCount().intValue(),16),2)
                +StringUtil.paddingHeadZero(Integer.toString(deviceInitRec.getReportRegularlyRate().intValue(),16),4)
                +Integer.toHexString(Integer.parseInt(bipIps[0]))
                +Integer.toHexString(Integer.parseInt(bipIps[1]))
                +Integer.toHexString(Integer.parseInt(bipIps[2]))
                +Integer.toHexString(Integer.parseInt(bipIps[3]))
                +Integer.toHexString(deviceInitRec.getBipPort().intValue());
        cmdParamData.setBipParam(cmdParam);
        cmdParamData.setFplmn(pri.getFplmn());
        cmdParamData.setSca(primaryResourceNumber.getSca());
        if("0".equals(primaryResourceNumber.getTelecommunicationsFlag())){
            cmdParamData.setTelData("");
        }else {
            //cmdParamData.setTelData();
        }
        String cmdStr = JsonUtil.getJSONString(cmdParamData);
        plainDataMt.setCmdParams(cmdParamData);
        plainDataMt.setCmdType(CmdTypeEnum.UPDATEICCID_POR
                .getCmdType());
        plainDataMt.setCmdLength(String.valueOf(cmdStr.length()));
        return plainDataMt;
    }
    //选择副号
    public PlainDataMt selectLocalNumber(String tradeNo, AssetOrder assetOrder, PositionMo positionMo,
                                         DeviceInitRec deviceInitRec) throws Exception {
        logger.info(positionMo.getImei() + positionMo.getpIccid() + positionMo.getMsisdn() + "下发新副号");
        if(null == assetOrder) {
            logger.info("assetOrder查询为空");
            return null;
        }
        //在这里添加判断逻辑
        String simIccid = "";
        String simImsi = "";
        SelectLocalSoftSimResponse response = null;

        List<AssetOrderSoftsimUsage> orderSoftsimUsageList = assetOrderSoftsimUsageDao.getList(positionMo.getImei(), assetOrder.getOrderId());
        if(null == orderSoftsimUsageList || orderSoftsimUsageList.size() < 1) {
            response = selectLocalSoftSim(assetOrder.getOrderId(), positionMo.getMcc());
            if(response == null || response.getError() == null || response.getRespData() == null || response.getRespData().getSimIccid() == null ||response.getRespData().getSimImsi() == null){
                logger.info("调用选择副号接口错误，未返回副号信息");
                return null;
            }
            simIccid = response.getRespData().getSimIccid();
            simImsi = response.getRespData().getSimImsi();
        }else if(orderSoftsimUsageList.size() > 1){
            logger.info("查询到的订单数量大于一个");
            return null;
        }else {
            AssetOrderSoftsimUsage assetOrderSoftsimUsage = orderSoftsimUsageList.get(0);
            //response = selectLocalSoftSim(assetOrderSoftsimUsage.getOrderId(), assetOrderSoftsimUsage.getCoverMcc());
            simIccid = assetOrderSoftsimUsage.getIccid();
            simImsi = assetOrderSoftsimUsage.getImsi();
        }
        List<SoftSimResourceInfo> softSimResourceInfos = softSimResourceInfoDao.querySoftsimByIccid(simIccid);
        if(1 != softSimResourceInfos.size()){
            logger.error("iccid为" + simIccid + "的资源多于1个或者不存在！");
            return null;
        }
        //String tradeNo = getOtaTradeNo();
        PlainDataMt plainDataMt = getLocalPlainDataMtObj(assetOrder, tradeNo, softSimResourceInfos.get(0),
                positionMo, simImsi, deviceInitRec);
        return plainDataMt;
    }
    PlainDataMt getLocalPlainDataMtObj(AssetOrder assetOrder, String tradeNo, SoftSimResourceInfo softSimResourceInfo,
                                       PositionMo positionMo, String imsi, DeviceInitRec deviceInitRec) throws Exception {
        String UssdPre = "";
        String plmn = "";
        PlainDataMt plainDataMt = new PlainDataMt();
        CmdParamData cmdParamData = new CmdParamData();
        cmdParamData.setOtaTradeNo(tradeNo);
        cmdParamData.setOldIccid(positionMo.getpIccid());
        cmdParamData.setNewIccid(deviceInitRec.getNumberIccid());
        cmdParamData.setpIccid(positionMo.getpIccid());
        cmdParamData.setImsi(ResourceUtil.getEfImsi(softSimResourceInfo,imsi));
        cmdParamData.setDataKeyIndex(positionMo.getKeyIndex());
        cmdParamData.setCallControl(softSimResourceInfo.getCallFlag());
        List<SoftSimResourceImsi> softSimResourceImsis = softSimResourceImsiDao.querySoftsimResourceImsi(
                softSimResourceInfo.getIccid(), imsi);
        if(1 != softSimResourceImsis.size()){
            logger.error("iccid为"+softSimResourceInfo.getIccid()+"imsi为"+imsi+"的主号多于一个或者不存在！");
            return null;
        }
        SoftSimResourceImsi softSimResourceImsi = softSimResourceImsis.get(0);
        cmdParamData.setAlgFlag(StringUtil.paddingHeadZero(softSimResourceImsi.getAlgFlag(),2));
        //USSD prefix
        Supplier supplier = supplierDao.querySupplierByCode(softSimResourceInfo.getSupplierCode());
        if(!StringHelper.isEmpty(supplier.getUssdPre())){
            UssdPre = supplier.getUssdPre();
        }
        UssdPre = StringUtil.paddingTailZero(StringUtil.asc2hex(UssdPre), 36);
        cmdParamData.setUssdPrefix(UssdPre);
        if(!StringHelper.isEmpty(softSimResourceImsi.getPlmn())){
            if(softSimResourceImsi.getPlmn().length()> SysConstants.MAX_MAIN_PLMN_FIRST_LENGTH){
                plmn = softSimResourceImsi.getPlmn().substring(0,SysConstants.MAX_MAIN_PLMN_FIRST_LENGTH);
            }else{
                plmn = StringUtil.paddingTail(softSimResourceImsi.getPlmn(), SysConstants.MAX_MAIN_PLMN_FIRST_LENGTH, "FF");
            }
        }else{
            plmn="";
        }
        cmdParamData.setPlmn(ResourceUtil.generatePLMNIndexList(plmn, softSimResourceImsi.getCoverCountry()));
        String dataKeyIndex = "0" + (new Random().nextInt(5) + 1);
        cmdParamData.setDataKeyIndex(dataKeyIndex);
        String deKI = KeyUtil.decryptKIorOPC(
                Integer.parseInt(softSimResourceInfo.getKeyIndex()),
                softSimResourceInfo.getKi()).substring(0, 32);
        String deOPC = KeyUtil.decryptKIorOPC(
                Integer.parseInt(softSimResourceInfo.getKeyIndex()),
                softSimResourceInfo.getOpc()).substring(0, 32);
        String sessionKey = ResourceUtil
                .calcSessionKey(SysConstants.PERS_DATA_KEY.get(positionMo
                                .getManuFlag())[Integer.parseInt(dataKeyIndex) - 1],
                        positionMo.getpIccid(), tradeNo);//首次更新
        String keyData = LF3DesCryptoUtil.ecb_encrypt(sessionKey, deKI + deOPC,
                JceBase.Padding.NoPadding);
        cmdParamData.setKeyData(keyData);
        //在这里添加副号的过期时间
        String expTime = "";
        if(assetOrder.getPlannedEndTime() != null && assetOrder.getPlannedEndTime().trim().length() > 0) {
            expTime = assetOrder.getPlannedEndTime().replaceAll("-", "")
                    .replaceAll(":", "").replaceAll(" ", "");
        }else {
            logger.info("订单预计结束时间为空");
        }
        cmdParamData.setExpTime(StringUtil.string2ADN(expTime));
        cmdParamData.setCoverMcc(softSimResourceImsi.getCoverCountry());
        cmdParamData.setApn(softSimResourceInfo.getApn());
        String[] bipIps = deviceInitRec.getBipIp().split("\\.");
        String cmdParam = StringUtil.paddingHeadZero(Integer.toString(deviceInitRec.getRepeatReportRate().intValue(), 16), 2)
                +StringUtil.paddingHeadZero(Integer.toString(deviceInitRec.getRetryCount().intValue(),16),2)
                +StringUtil.paddingHeadZero(Integer.toString(deviceInitRec.getReportRegularlyRate().intValue(),16),4)
                +Integer.toHexString(Integer.parseInt(bipIps[0]))
                +Integer.toHexString(Integer.parseInt(bipIps[1]))
                +Integer.toHexString(Integer.parseInt(bipIps[2]))
                +Integer.toHexString(Integer.parseInt(bipIps[3]))
                +Integer.toHexString(deviceInitRec.getBipPort().intValue());
        cmdParamData.setBipParam(cmdParam);
        cmdParamData.setFplmn(softSimResourceImsi.getFplmn());
        cmdParamData.setSca(softSimResourceInfo.getSca());
        if("0".equals(softSimResourceInfo.getTelecommunicationsFlag())){
            cmdParamData.setTelData("");
        }else {
            //cmdParamData.setTelData();
        }
        String cmdStr = JsonUtil.getJSONString(cmdParamData);
        plainDataMt.setCmdParams(cmdParamData);
        plainDataMt.setCmdType(CmdTypeEnum.LOCALDATA_POR
                .getCmdType());
        plainDataMt.setCmdLength(String.valueOf(cmdStr.length()));
        return plainDataMt;
    }
    public boolean checkMccAndDevice(String mcc, List<DeviceInitRec> deviceInitRecs){
        if(deviceInitRecs.size() > 1){
            logger.info("设备初始化次数超过1次！");
            return false;
        }else if(deviceInitRecs.size() == 0){
            logger.info("设备没有初始化！");
            return false;
        }
        //如果上报国家全F，发送同步时间参数短信
        if("FFF".equals(mcc)){
            logger.info("上报国家全F:" + mcc);
            return false;
        }
        return true;
    }

    /**
     * 生成ota交易流水号
     *
     * @return
     */
//    public String getOtaTradeNo() {
//        Long nextVal = assetManageBusiDao.getOtaTradeNo();
//        String tempId = Long.toString(nextVal);
//        if (tempId.length() > 6) {
//            tempId = tempId.substring(tempId.length() - 6, tempId.length());
//        } else {
//            tempId = StringUtil.paddingHeadZero(tempId, 6);
//        }
//
//        String sysTimeStr = DateUtils.format(new Date(), "yyyyMMddHHmmss");
//        String tradeId = sysTimeStr + tempId;
//        return tradeId;
//    }

    /**
     * 为OTA设备的副号套餐订单选择副号
     * @param orderCode
     * @param curPostion
     * @return
     */
    /**ota系统的内部系统编码*/
    @Value("2")
    private String otaSysCode;
    @Value("utf-8")
    private String orderCharset;
    /**为OTA设备的副号套餐订单选择副号*/
    @Value("http://47.52.192.207:6035/orderSys/startOtaDeviceOrder")
    private String selectSoftsimUrl;
    public SelectLocalSoftSimResponse selectLocalSoftSim(String orderCode,String curPostion) {
        //组装请求报文
        SelectLocalSoftSimRequest request = new SelectLocalSoftSimRequest();
        request.setOrderCode(orderCode);
        request.setCurPostion(curPostion);
        request.setSysName(otaSysCode);

        String requestStr = null;
        try {
            requestStr = JsonUtil.getJSONString(request);
        } catch (Exception e) {
            logger.error("为OTA设备的副号套餐订单选择副号请求报文转json失败:" + e);
        }
        logger.info("订单系统请求url:" + selectSoftsimUrl);
        logger.info("订单系统请求报文:" + requestStr);
        String responseStr = HttpClientUtil.doPost(selectSoftsimUrl, requestStr, orderCharset, null);
        logger.info("订单系统反馈报文:" + responseStr);
        SelectLocalSoftSimResponse response = (SelectLocalSoftSimResponse) JsonUtil.getDTO(responseStr, SelectLocalSoftSimResponse.class);
        //checkAndSaveError(selectSoftsimUrl,requestStr,responseStr);
        return response;
    }


    /**
     * 针对旅游卡的选副号服务
     */
    @Override
    public PlainDataMt selectAccessoryNumber(String tradeNo, AssetOrder assetOrder, String iccid, String mcc) throws Exception {
        String simIccid = "";
        String simImsi = "";
        SelectLocalSoftSimResponse response = null;
        if(null == assetOrder) {
            logger.info("assetOrder查询为空");
            return null;
        }
        List<AssetOrderSoftsimUsage> orderSoftsimUsageList = assetOrderSoftsimUsageDao.getList(iccid, assetOrder.getOrderId());
        if(null == orderSoftsimUsageList || orderSoftsimUsageList.size() < 1) {
            response = selectLocalSoftSim(assetOrder.getOrderId(), mcc);
            if(response == null || response.getError() == null || response.getRespData() == null || response.getRespData().getSimIccid() == null ||response.getRespData().getSimImsi() == null){
                logger.info("调用选择副号接口错误，未返回副号信息");
                return null;
            }
            simIccid = response.getRespData().getSimIccid();
            simImsi = response.getRespData().getSimImsi();
        }else if(orderSoftsimUsageList.size() > 1) {
            logger.info("查询到的订单数量大于一个");
            return null;
        }else {
            //查询到正在使用的副号
            AssetOrderSoftsimUsage assetOrderSoftsimUsage = orderSoftsimUsageList.get(0);
            simIccid = assetOrderSoftsimUsage.getIccid();
            simImsi = assetOrderSoftsimUsage.getImsi();
        }
        List<SoftSimResourceInfo> softSimResourceInfos = softSimResourceInfoDao.querySoftsimByIccid(simIccid);
        if(1 != softSimResourceInfos.size()){
            logger.error("iccid为" + simIccid + "的资源多于1个或者不存在！");
            return null;
        }
        PlainDataMt plainDataMt = getAccessoryNumberObj(assetOrder, tradeNo, softSimResourceInfos.get(0),
                new LUMo(), simIccid, simImsi);
        return plainDataMt;
    }

    /**
     * 旅游卡获取副号
     */
    private PlainDataMt getAccessoryNumberObj(AssetOrder assetOrder, String tradeNo, SoftSimResourceInfo softSimResourceInfo, LUMo luMo, String iccid, String imsi) throws Exception{
        String UssdPre = "";
        String plmn = "";
        PlainDataMt plainDataMt = new PlainDataMt();
        CmdParamData cmdParamData = new CmdParamData();
        cmdParamData.setOtaTradeNo(tradeNo);
        cmdParamData.setOldIccid(iccid);
        cmdParamData.setNewIccid(iccid);
        cmdParamData.setpIccid(iccid);
        cmdParamData.setImsi(ResourceUtil.getEfImsi(softSimResourceInfo,imsi));
        //cmdParamData.setDataKeyIndex(null);
        cmdParamData.setCallControl(softSimResourceInfo.getCallFlag());
        List<SoftSimResourceImsi> softSimResourceImsis = softSimResourceImsiDao.querySoftsimResourceImsi(
                softSimResourceInfo.getIccid(), imsi);
        if(1 != softSimResourceImsis.size()){
            logger.error("iccid为"+softSimResourceInfo.getIccid()+"imsi为"+imsi+"的主号多于一个或者不存在！");
            return null;
        }
        SoftSimResourceImsi softSimResourceImsi = softSimResourceImsis.get(0);
        cmdParamData.setAlgFlag(StringUtil.paddingHeadZero(softSimResourceImsi.getAlgFlag(),2));
        //USSD prefix
        Supplier supplier = supplierDao.querySupplierByCode(softSimResourceInfo.getSupplierCode());
        if(!StringHelper.isEmpty(supplier.getUssdPre())){
            UssdPre = supplier.getUssdPre();
        }
        UssdPre = StringUtil.paddingTailZero(StringUtil.asc2hex(UssdPre), 36);
        cmdParamData.setUssdPrefix(UssdPre);
        if(!StringHelper.isEmpty(softSimResourceImsi.getPlmn())){
            if(softSimResourceImsi.getPlmn().length()> SysConstants.MAX_MAIN_PLMN_FIRST_LENGTH){
                plmn = softSimResourceImsi.getPlmn().substring(0,SysConstants.MAX_MAIN_PLMN_FIRST_LENGTH);
            }else{
                plmn = StringUtil.paddingTail(softSimResourceImsi.getPlmn(), SysConstants.MAX_MAIN_PLMN_FIRST_LENGTH, "FF");
            }
        }else{
            plmn="";
        }
        cmdParamData.setPlmn(ResourceUtil.generatePLMNIndexList(plmn, softSimResourceImsi.getCoverCountry()));
        String dataKeyIndex = "0" + (new Random().nextInt(5) + 1);
        cmdParamData.setDataKeyIndex(dataKeyIndex);
        String deKI = KeyUtil.decryptKIorOPC(
                Integer.parseInt(softSimResourceInfo.getKeyIndex()),
                softSimResourceInfo.getKi()).substring(0, 32);
        String deOPC = KeyUtil.decryptKIorOPC(
                Integer.parseInt(softSimResourceInfo.getKeyIndex()),
                softSimResourceInfo.getOpc()).substring(0, 32);
//        String sessionKey = ResourceUtil
//                .calcSessionKey(SysConstants.PERS_DATA_KEY.get(positionMo
//                                .getManuFlag())[Integer.parseInt(dataKeyIndex) - 1],
//                        positionMo.getpIccid(), tradeNo);//首次更新
//        String keyData = LF3DesCryptoUtil.ecb_encrypt(sessionKey, deKI + deOPC,
//                JceBase.Padding.NoPadding);
//        cmdParamData.setKeyData(keyData);
        //在这里添加副号的过期时间
        String expTime = "";
        if(assetOrder.getPlannedEndTime() != null && assetOrder.getPlannedEndTime().trim().length() > 0) {
            expTime = assetOrder.getPlannedEndTime().replaceAll("-", "")
                    .replaceAll(":", "").replaceAll(" ", "");
        }else {
            logger.info("订单预计结束时间为空");
        }
        cmdParamData.setExpTime(StringUtil.string2ADN(expTime));
        cmdParamData.setCoverMcc(softSimResourceImsi.getCoverCountry());
        cmdParamData.setApn(softSimResourceInfo.getApn());
        //String[] bipIps = deviceInitRec.getBipIp().split("\\.");
//        String cmdParam = StringUtil.paddingHeadZero(Integer.toString(deviceInitRec.getRepeatReportRate().intValue(), 16), 2)
//                +StringUtil.paddingHeadZero(Integer.toString(deviceInitRec.getRetryCount().intValue(),16),2)
//                +StringUtil.paddingHeadZero(Integer.toString(deviceInitRec.getReportRegularlyRate().intValue(),16),4)
//                +Integer.toHexString(Integer.parseInt(bipIps[0]))
//                +Integer.toHexString(Integer.parseInt(bipIps[1]))
//                +Integer.toHexString(Integer.parseInt(bipIps[2]))
//                +Integer.toHexString(Integer.parseInt(bipIps[3]))
//                +Integer.toHexString(deviceInitRec.getBipPort().intValue());
//        cmdParamData.setBipParam(cmdParam);
        cmdParamData.setFplmn(softSimResourceImsi.getFplmn());
        cmdParamData.setSca(softSimResourceInfo.getSca());
        if("0".equals(softSimResourceInfo.getTelecommunicationsFlag())){
            cmdParamData.setTelData("");
        }else {
            //cmdParamData.setTelData();
        }
        String cmdStr = JsonUtil.getJSONString(cmdParamData);
        plainDataMt.setCmdParams(cmdParamData);
        plainDataMt.setCmdType(CmdTypeEnum.LOCALDATA_POR
                .getCmdType());
        plainDataMt.setCmdLength(String.valueOf(cmdStr.length()));
        return plainDataMt;
    }
}
