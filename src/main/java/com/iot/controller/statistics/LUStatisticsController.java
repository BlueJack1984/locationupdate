package com.iot.controller.statistics;

import com.iot.otaBean.assetSubmitRec.AssetSubmitRec;
import com.iot.otaBean.lUUploadRecord.LUUploadRecord;
import com.iot.service.interfaces.IAssetMtService;
import com.iot.service.interfaces.ILUUploadPorService;
import com.iot.service.interfaces.ILUUploadRecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/lu/statistics")
public class LUStatisticsController {

    private final ILUUploadRecordService luUploadRecordService;
    private final IAssetMtService assetMtService;
    private final ILUUploadPorService luUploadPorService;
    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 23:59:59");

    /**
     * 获取活动成功率
     * @return
     */
    @PostMapping("/activity")
    public Double getActivitySuccessRatio(String date) {
        Date startTime = null;
        Date endTime = null;
        try {
            startTime = SDF.parse(date);
            endTime = sdf.parse(date);
        } catch (ParseException e) {
            log.info("传入的日期参数date格式不正确，无法正确转换为日期");
            return null;
        }
        //首先获取一天之内所有的LU请求
        List<LUUploadRecord> uploadRecordList = luUploadRecordService.getListOneDay(startTime, endTime);
        if(null == uploadRecordList || uploadRecordList.size() < 1) {
            log.info("传入的日期参数date:" + date + "日期的当天LU上报信息为0");
            return 0.0;
        }
        //计算所有的成功活动
        //第一部分是说有type不为0
        uploadRecordList
        //第二部分是type为0，表示成功下发，并且有POR上来的部分
        return null;
    }

    /**
     *获取活动成功率
     * @param date
     * 数据格式：2019-08-02
     * @return
     */
    @PostMapping("/order")
    public String getOrderSuccessRatio(String date) {
        //首先获取一天之内所有的LU请求


        return null;
    }

    /**
     *
     * @return
     */
    @PostMapping("/luorder")
    public String getLUOrderSuccessRatio() {
        return null;
    }
}
