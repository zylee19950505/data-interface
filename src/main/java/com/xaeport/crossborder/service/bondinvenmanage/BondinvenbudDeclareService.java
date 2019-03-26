package com.xaeport.crossborder.service.bondinvenmanage;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.xaeport.crossborder.data.entity.ImpInventory;
import com.xaeport.crossborder.data.mapper.BondinvenbudDeclareMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class BondinvenbudDeclareService {


    @Autowired
    BondinvenbudDeclareMapper bondinvenbudDeclareMapper;

    private Log logger = LogFactory.getLog(this.getClass());

    public List<ImpInventory> queryBondinvenbudDeclare(Map<String, String> paramMap) {

        int hashValue;
        String str;
        List<ImpInventory> impInventoryList = this.bondinvenbudDeclareMapper.queryBondinvenbudDeclareList(paramMap);
        List<ImpInventory> result = new ArrayList<>();
        List<Integer> list = new ArrayList<>();

        if(!StringUtils.isEmpty(impInventoryList)){
            for(ImpInventory impInventory :impInventoryList){
                str = String.format("%s%s",impInventory.getBill_no(),impInventory.getSum());
                hashValue = str.hashCode();
                if(list.contains(hashValue)){
                    impInventory.setNo("0");
                    result.add(impInventory);
                }else{
                    list.add(hashValue);
                    impInventory.setNo("1");
                    result.add(impInventory);
                }
            }
        }
        list.clear();

        return result;
    }

    @Transactional
    public boolean updateSubmitCustom(Map<String, String> paramMap) {
        boolean flag;
        try {
            this.bondinvenbudDeclareMapper.updateSubmitCustom(paramMap);
            flag = true;
        } catch (Exception e) {
            flag = false;
            String exceptionMsg = String.format("处理清单[orderNo: %s]时发生异常", paramMap.get("submitKeys"));
            logger.error(exceptionMsg, e);
        }
        return flag;
    }
}
