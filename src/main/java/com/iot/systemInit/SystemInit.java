package com.iot.systemInit;

//import com.common.tools.key.KeyUtil;
/*import com.iot.bean.db.production.manufacturer.ManufacturerKey;
import com.iot.bean.db.softsim.MccBitMap;
import com.iot.bean.enumeration.production.ManufacturerEnmu.ManufacturerKeyType;
import com.iot.ota.constant.SysConstants;
import com.iot.ota.dao.interf.IManufacturerKeyDao;
import com.iot.ota.dao.interf.IMccBitMapDao;
import com.iot.ota.jmsQueue.MoMsgConsumer;
import com.iot.ota.util.StaticLoggerUtils;*/
import com.iot.constant.SysConstants;
import com.iot.dao.IPlmnIndexDao.IPlmnIndexDao;
import com.iot.dao.manufacturerKeyDao.IManufacturerKeyDao;
import com.iot.dao.mccBitMapDao.IMccBitMapDao;
import com.iot.otaBean.manufacturerEnmu.ManufacturerEnmu;
import com.iot.otaBean.manufacturerKey.ManufacturerKey;
import com.iot.otaBean.mccBitMap.MccBitMap;
import com.iot.otaBean.plmn.PlmnIndex;
import com.iot.util.KeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * SystemInit.java
 * 
 * @description：系统初始化启动模块 加载密钥、启动mq消费者监听
 * @author: Date Dec 28, 2014
 * @version: 1.0.0
 * @modify:
 */
@Component
@Order(1)
public class SystemInit implements ApplicationRunner {
	@Autowired
	private IManufacturerKeyDao iManufacturerKeyDao;

	@Autowired
	private IMccBitMapDao mccBitMapDao;

	@Autowired
	private IPlmnIndexDao plmnIndexDao;

	@Override
	public void run(ApplicationArguments applicationArguments) throws Exception {

		for (MccBitMap bitMap : mccBitMapDao.getAllMccBitMap()) {
			SysConstants.MCC_BIT_MAP.put(bitMap.getMcc(),
					bitMap.getBitmapCode());
		}
		for (PlmnIndex plmnIndex : plmnIndexDao.getAllPlmnIndex()) {
			SysConstants.PLMN_INDEX_MAP.put(plmnIndex.getPlmn(),plmnIndex);
		}
		// 获取卡片通信密钥
		String otaKeyManuCode = "";
		List<ManufacturerKey> otaCommKeyList = iManufacturerKeyDao
				.getKeyByType(ManufacturerEnmu.ManufacturerKeyType.OTA_COMM_KEY.getTypeCode());
		List<String> keyList = new ArrayList<String>();
		for (int i = 0; i < otaCommKeyList.size(); i++) {
			ManufacturerKey key = otaCommKeyList.get(i);
			if (!otaKeyManuCode.equals(key.getManufacturerCode())) {
				if (keyList.size() > 0) {
					SysConstants.OTA_COMM_KEY_MAP.put(otaKeyManuCode,
							keyList.toArray(new String[keyList.size()]));
					keyList = new ArrayList<String>();
				}
				otaKeyManuCode = key.getManufacturerCode();
				if (keyList.size() == 0) {
					keyList.add(KeyUtil.decryptKeyBy3DesEcb(key.getKeyValue()));
				}
			} else {
				keyList.add(KeyUtil.decryptKeyBy3DesEcb(key.getKeyValue()));
			}
			if (i == (otaCommKeyList.size() - 1)) {
				SysConstants.OTA_COMM_KEY_MAP.put(otaKeyManuCode,
						keyList.toArray(new String[keyList.size()]));
			}
		}
		System.out.println("OTA通信密钥数量" + SysConstants.OTA_COMM_KEY_MAP.size());
		if (SysConstants.OTA_COMM_KEY_MAP.size() == 0) {
			throw new RuntimeException("OTA通信密钥配置错误!");
		}
		// 获取卡片数据密钥
		String persdataKeyManuCode = "";
		List<ManufacturerKey> persdataKeyList = iManufacturerKeyDao
				.getKeyByType(ManufacturerEnmu.ManufacturerKeyType.PERSONAL_DAY_KEY
						.getTypeCode());
		keyList = new ArrayList<String>();
		for (int i = 0; i < persdataKeyList.size(); i++) {
			ManufacturerKey key = persdataKeyList.get(i);
			if (!persdataKeyManuCode.equals(key.getManufacturerCode())) {
				if (keyList.size() > 0) {
					SysConstants.PERS_DATA_KEY.put(persdataKeyManuCode,
							keyList.toArray(new String[keyList.size()]));
					keyList = new ArrayList<String>();
				}
				persdataKeyManuCode = key.getManufacturerCode();
				if (keyList.size() == 0) {
					keyList.add(KeyUtil.decryptKeyBy3DesEcb(key.getKeyValue()));
				}
			} else {
				keyList.add(KeyUtil.decryptKeyBy3DesEcb(key.getKeyValue()));
			}
			if (i == (persdataKeyList.size() - 1)) {
				SysConstants.PERS_DATA_KEY.put(persdataKeyManuCode,
						keyList.toArray(new String[keyList.size()]));
			}
		}
		System.out.println("数据密钥数量" + SysConstants.PERS_DATA_KEY.size());
		if (SysConstants.PERS_DATA_KEY.size() == 0) {
			throw new RuntimeException("数据密钥配置错误!");
		}
	}
}
