package com.iot.service.serviceImpl;

import com.iot.DEScrypto.DESCrypto;
import com.iot.constant.SysConstants;
import com.iot.controller.USSDController;
import com.iot.otaBean.mo.BaseMo;
import com.iot.otaBean.mo.PorMo;
import com.iot.otaBean.mo.PositionMo;
import com.iot.otaBean.mo.RecieveDataPorMo;
import com.iot.otaBean.mt.*;
import com.iot.service.interfaces.USSDPackService;
import com.iot.util.JsonUtil;
import com.iot.util.ResourceUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("ussdBusiServicePack")
public class USSDPackServiceImpl implements USSDPackService {
    private static final Log logger = LogFactory.getLog(USSDController.class);

    public String ussdBusiServicePack(MtData mtData) throws Exception {
        List<PlainDataMt> plainDataMtList = mtData.getPlainDatas();
        PlainDataMt plainDataMt = new PlainDataMt();
        //String cmdType = plainDataMt.getCmdType();
        String checkNum = mtData.getCheckNum();
        String deliverData = null;
        String userData = "";
        String HexIV = "0000000000000000";
        int cipherdataLen = 0;
        String MAC = null;
        String SMS = null;

        for(int i = 0; i < plainDataMtList.size(); i++){
            plainDataMt = plainDataMtList.get(i);
            if(plainDataMt.getCmdType().equals("33")){
                CmdParamData cmdParamData = (CmdParamData) plainDataMt.getCmdParams();
                deliverData = cmdParamData.getOtaTradeNo() +
                        (null == cmdParamData.getCallControl() ? "03" : cmdParamData.getCallControl()) +
                        cmdParamData.getpIccid();
                CascadePushCommandCMD cascadePushCommandCMD = null;
                for(int j = 0; j < cmdParamData.getCmds().size(); j++){
                    cascadePushCommandCMD = cmdParamData.getCmds().get(j);
                    deliverData += cascadePushCommandCMD.getTag() +
                            cascadePushCommandCMD.getLength() +
                            cascadePushCommandCMD.getValue();
                }
                userData += plainDataMt.getCmdType() + getStrLength(deliverData) + deliverData;
            }else if(plainDataMt.getCmdType().equals("31") || plainDataMt.getCmdType().equals("32")){
                CmdParamData cmdParamData = (CmdParamData) plainDataMt.getCmdParams();
                //处理国家码 added by lushusheng
                String bitMapMcc = "";
                if(null != cmdParamData.getCoverMcc()) {
                    bitMapMcc = ResourceUtil.genMccBitMap(cmdParamData.getCoverMcc());
                }
                String apn = ResourceUtil.changeApn(cmdParamData.getApn());
                deliverData = cmdParamData.getOtaTradeNo() +
                        (null == cmdParamData.getCallControl() ? "03" : cmdParamData.getCallControl()) +
                        cmdParamData.getOldIccid() +
                        cmdParamData.getImsi() +
                        cmdParamData.getAlgFlag()+
                        cmdParamData.getDataKeyIndex() +
                        cmdParamData.getKeyData() +
                        organizeData(cmdParamData.getExpTime()) +
                        organizeData(bitMapMcc) +
                        organizeData(cmdParamData.getUssdPrefix()) +
                        organizeData(cmdParamData.getNewIccid()) +
                        organizeData(apn) +
                        organizeData(cmdParamData.getSca()) +
                        organizeData(cmdParamData.getTelData()) +
                        organizeData1(cmdParamData.getPlmn()) +
                        organizeData(cmdParamData.getBipParam()) +
                        organizeData(cmdParamData.getFplmn());
                userData += plainDataMt.getCmdType() + getStrLength(deliverData) +deliverData;
            }
        }
        userData = checkNum + userData;
        String[] keys = com.iot.constant.SysConstants.OTA_COMM_KEY_MAP.get(mtData.getManuFlag());
        String key = keys[Integer.parseInt(mtData.getKeyIndex()) - 1];
        String cipherdata = DESCrypto.des_cbc_encrypt(key, userData, HexIV);
        cipherdata = "0" + mtData.getBusiType() + mtData.getKeyIndex() + cipherdata;
        String len = getStrLength(cipherdata + "00000000"); //"00000000"补充MAC的4个字节
        cipherdata = len + cipherdata;
        String cipherdataMAC = DESCrypto.des_cbc_encrypt(key, cipherdata, HexIV);
        cipherdataLen = cipherdataMAC.length();
        MAC = cipherdataMAC.substring((cipherdataLen - 8), cipherdataLen);
        SMS = cipherdata + MAC;
        return SMS;
    }
    String getStrLength(String str){
        String strLen = null;
        strLen = Integer.toHexString(str.length()/2);
        if(1 == strLen.length()){
            strLen = "000" + strLen;
        }else if(2 == strLen.length()){
            strLen = "00" + strLen;
        }else if(3 == strLen.length()){
            strLen = "0" + strLen;
        }else if(4 == strLen.length()){
        }else {
            logger.info("数据长度超过两字节，无法处理！");
            return null;
        }
        return strLen;
    }
    private String organizeData(String str){
        String strLen = null;
        if((null == str) || ("".equals(str))){
            return "00";
        }else {
            if("00".equals(getStrLength(str).substring(0, 2))){
                strLen = getStrLength(str).substring(2, 4);
            }else {
                strLen = getStrLength(str);
            }
            return (strLen + str);
        }
    }
    private String organizeData1(String str){
        String strLen = null;
        if((null == str) || ("".equals(str))){
            return "00";
        }else {
            return str;
        }
    }
}
