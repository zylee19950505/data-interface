package com.xaeport.crossborder.controller.api;

import com.xaeport.crossborder.data.LoadData;
import com.xaeport.crossborder.data.ResponseData;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 加载字典数据api
 * Created by xcp on 2017/9/6.
 */
@RestController
public class LoadDataApi extends BaseApi {
    private Log log = LogFactory.getLog(this.getClass());

    @Autowired
    LoadData loadData;


    @RequestMapping(value = "/loadData", method = RequestMethod.GET)
    public ResponseData loadData() {
        return new ResponseData(this.loadData);
    }
}
