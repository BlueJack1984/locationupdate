package com.iot.controller.statistics;

import com.iot.service.interfaces.IAssetMtService;
import com.iot.service.interfaces.ILUUploadRecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    /**
     *
     * @return
     */
    @PostMapping("/activity")
    public String getActivitySuccessRatio() {
        return null;
    }

    /**
     *
     * @return
     */
    @PostMapping("/order")
    public String getOrderSuccessRatio() {
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
