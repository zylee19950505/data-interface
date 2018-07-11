package com.xaeport.crossborder.service.parametermanage;

import com.xaeport.crossborder.data.entity.ProduecCode;
import com.xaeport.crossborder.data.mapper.ProductCodeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by zwj on 2017/10/24.
 */
@Service
public class ProductCodeService {
    @Autowired
    ProductCodeMapper productCodeMapper;

    /**
     * 根据查询参数获取行邮税信息集合
     *
     * @param paramMap 参数Map
     * @return 海关商品编码信息集合
     */
    public List<ProduecCode> getProductCodeList(Map<String, String> paramMap) {
        return this.productCodeMapper.getProductCodList(paramMap);
    }
}
