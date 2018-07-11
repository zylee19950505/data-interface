package com.xaeport.crossborder.controller.api.paymentmanage;

import com.xaeport.crossborder.controller.api.BaseApi;
import com.xaeport.crossborder.data.ResponseData;
import com.xaeport.crossborder.data.entity.ProduecCode;
import com.xaeport.crossborder.service.parametermanage.ProductCodeService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品编码
 * Created by zwj on 2017/10/24.
 */
@RestController
public class ProductCodeApi extends BaseApi {
    private Log log = LogFactory.getLog(this.getClass());

    @Autowired
    ProductCodeService productCodeService;

    @RequestMapping(value = "/productCode", method = RequestMethod.GET)
    public ResponseData getPostalTaxList(@RequestParam(required = false) String customsCode) {
        this.log.debug(String.format("商品编码查询条件%s", customsCode));
        Map<String, String> paramMap = new HashMap<String, String>();
        // 查询参数
        paramMap.put("customsCode", customsCode);

        List<ProduecCode> postalTaxList = this.productCodeService.getProductCodeList(paramMap);
        return new ResponseData(postalTaxList);
    }


}
