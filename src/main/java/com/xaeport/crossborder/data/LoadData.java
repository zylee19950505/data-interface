package com.xaeport.crossborder.data;


import com.xaeport.crossborder.configuration.SystemConstants;
import com.xaeport.crossborder.data.entity.Code;
import com.xaeport.crossborder.data.entity.ProduecCode;
import com.xaeport.crossborder.service.LoadService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xcp on 2017/9/5.
 */
@Component
public class LoadData {

    @Autowired
    LoadService loadService;

    private Log log = LogFactory.getLog(this.getClass());

    // 行邮税率map，key为行邮税号，value为税率
    Map<String, Double> postalRateMap = new HashMap<>();
    //行邮税完税价格map,key为行邮税号，value为完税价格
    Map<String, String> postalPtpriceMap = new HashMap<>();
    // 行邮税率map，key为行邮税号，value为单位
    Map<String, String> postalUnitMap = new HashMap<>();
    // 申报计量单位map，key为计量编码/计量单位，value为计量单位/计量编码
    Map<String, String> unitCodeMap = new HashMap<>();
    // 申报计量单位map，key为计量编码，value为计量单位名称
    Map<String, String> unitCodesMap = new HashMap<>();
    // 运输方式map，key为运输方式代码，value为运输方式名称
    Map<String, String> trafModeMap = new HashMap<>();
    // 港口代码map，key为港口代码，value为港口名称
    Map<String, String> portMap = new HashMap<>();
    // 包装类型map，key为包装类型代码，value为包装类型名称
    Map<String, String> packTypeMap = new HashMap<>();

    // 陕西关区代码map，key为关区代码，value为关区名称
    Map<String, String> customsMap = new HashMap<>();
    // 全部关区代码map，key为关区代码，value为关区名称
    Map<String, String> allCustomsMap = new HashMap<>();
    // 征减免税方式代码map，key为关区代码，value为关区名称
    Map<String, String> taxReliefsModeMap = new HashMap<>();

    // 币制代码map，key为币制代码，value为币制名称
    Map<String, String> currencyMap = new HashMap<>();
    // 国家地区代码map，key为国家地区代码，value为国家地区名称
    Map<String, String> countryAreaMap = new HashMap<>();
    // 证件类型代码map，key为证件类型代码，value为证件类型名称
    Map<String, String> certificateTypeMap = new HashMap<>();
    // 企业类别代码map，key为企业类别代码，value为企业类别名称
    Map<String, String> agentTypeMap = new HashMap<>();
    // 企业性质代码map，key为企业性质代码，value为企业性质名称
    Map<String, String> agentNatureMap = new HashMap<>();
    // 企业分类代码map，key为企业分类代码，value为企业分类名称
    Map<String, String> agentClassifyMap = new HashMap<>();
    // 状态代码map，key为状态代码，value为状态名称
    Map<String, String> statusMap = new HashMap<>();
    //海关商品编码map ，key为海关编码，value为第一计量单位
    Map<String, String> productCodeUnit1Map = new HashMap<>();
    //海关商品编码map ，key为海关编码，value为第二计量单位
    Map<String, String> productCodeUnit2Map = new HashMap<>();
    //海关商品编码map ，key为海关编码，value为增值税
    Map<String, Double> productAddedTaxMap = new HashMap<>();
    //监管方式map ，key为监管方式代码，value为监管方式名称
    private Map<String, String> tradeModeMap = new HashMap<>();
    //成交方式map ，key为成交方式代码，value为成交方式名称
    private Map<String, String> transModeMap = new HashMap<>();
    //海关商品编码map ，key为海关编码，value为海关编码名称
    private Map<String, String> productCodeMap = new HashMap<String, String>();

    // 报关类别集合
    public static List<String> entryTypeList = new ArrayList<String>();

    static {
        //跨境直购
        entryTypeList.add(SystemConstants.T_IMP_ORDER);
        entryTypeList.add(SystemConstants.T_IMP_PAYMENT);
        entryTypeList.add(SystemConstants.T_IMP_LOGISTICS);
        entryTypeList.add(SystemConstants.T_IMP_INVENTORY);
        //跨境保税
        entryTypeList.add(SystemConstants.T_IMP_BOND_ORDER);
        entryTypeList.add(SystemConstants.T_IMP_BOND_INVEN);
        entryTypeList.add(SystemConstants.T_BOND_INVT);
        entryTypeList.add(SystemConstants.T_PASS_PORT);
    }

    @PostConstruct
    public void initData() {

        // 初始化申报计量单位map
        List<Code> unitCodeList = this.loadService.getUnitCodeList();
        if (!CollectionUtils.isEmpty(unitCodeList)) {
            for (Code code : unitCodeList) {
                // key为计量编码，value为计量单位名称
                this.setUnitCode(code.getCodeNo(), code.getCodeName());
                // key为计量单位名称，value为计量编码
                this.setUnitCode(code.getCodeName(), code.getCodeNo());
            }
            this.log.debug("初始化申报计量单位数据map：" + unitCodeList.size() + " 条，执行完毕");
        }
        List<Code> unitCodesList = this.loadService.getUnitCodeList();
        if (!CollectionUtils.isEmpty(unitCodesList)) {
            for (Code code : unitCodesList) {
                // key为计量编码，value为计量单位名称
                this.setUnitCodes(code.getCodeNo(), code.getCodeName());
            }
            this.log.debug("初始化申报计量单位单向数据map：" + unitCodesList.size() + " 条，执行完毕");
        }

        // 初始化运输方式map
        List<Code> trafModeList = this.loadService.getTrafModeList();
        if (!CollectionUtils.isEmpty(trafModeList)) {
            for (Code code : trafModeList) {
                // key为运输方式编码，value为运输方式名称
                this.setTrafMode(code.getCodeNo(), code.getCodeName());
            }
            this.log.debug("初始化运输方式数据map：" + trafModeList.size() + " 条，执行完毕");
        }

        // 初始化港口代码map
        List<Code> portList = this.loadService.getPortList();
        if (!CollectionUtils.isEmpty(portList)) {
            for (Code code : portList) {
                // key为港口编码，value为港口名称
                this.setPort(code.getCodeNo(), code.getCodeName());
            }
            this.log.debug("初始化港口代码数据map：" + portList.size() + " 条，执行完毕");
        }

        // 初始化包装类型代码map
        List<Code> packTypeList = this.loadService.getPackTypeList();
        if (!CollectionUtils.isEmpty(packTypeList)) {
            for (Code code : packTypeList) {
                // key为包装类型编码，value为包装类型名称
                this.setPackType(code.getCodeNo(), code.getCodeName());
            }
            this.log.debug("初始化包装类型代码数据map：" + packTypeList.size() + " 条，执行完毕");
        }

        // 初始化征减免税方式代码map
        List<Code> taxReliefsModeList = this.loadService.getTaxReliefsModeList();
        if (!CollectionUtils.isEmpty(taxReliefsModeList)) {
            for (Code code : taxReliefsModeList) {
                // key为征减免税方式代码，value为征减免税方式名称
                this.setTaxReliefsMode(code.getCodeNo(), code.getCodeName());
            }
            this.log.debug("初始化征减免税方式代码数据map：" + taxReliefsModeList.size() + " 条，执行完毕");
        }

        // 初始化全部关区代码map
        List<Code> allCustomsList = this.loadService.getAllCustomsList();
        if (!CollectionUtils.isEmpty(allCustomsList)) {
            for (Code code : allCustomsList) {
                // key为关区编码，value为关区名称
                this.setAllCustoms(code.getCodeNo(), code.getCodeName());
            }
            this.log.debug("初始化全部关区代码数据map：" + allCustomsList.size() + " 条，执行完毕");
        }

        // 初始化陕西省关区代码map
        List<Code> customsList = this.loadService.getCustomsList();
        if (!CollectionUtils.isEmpty(customsList)) {
            for (Code code : customsList) {
                // key为关区编码，value为关区名称
                this.setCustoms(code.getCodeNo(), code.getCodeName());
            }
            this.log.debug("初始化陕西省关区代码数据map：" + customsList.size() + " 条，执行完毕");
        }

        // 初始化币制代码map
        List<Code> currencyList = this.loadService.getCurrencyList();
        if (!CollectionUtils.isEmpty(currencyList)) {
            for (Code code : currencyList) {
                // key为币制编码，value为币制名称
                this.setCurrency(code.getCodeNo(), code.getCodeName());
            }
            this.log.debug("初始化币制代码数据map：" + currencyList.size() + " 条，执行完毕");
        }

        // 初始化国家地区代码map
        List<Code> countryAreaList = this.loadService.getCountryAreaList();
        if (!CollectionUtils.isEmpty(countryAreaList)) {
            for (Code code : countryAreaList) {
                // key为国家地区编码，value为国家地区名称
                this.setCountryArea(code.getCodeNo(), code.getCodeName());
            }
            this.log.debug("初始化国家地区代码数据map：" + countryAreaList.size() + " 条，执行完毕");
        }

        // 初始化证件类型代码map
        List<Code> certificateTypeList = this.loadService.getCertificateTypeList();
        if (!CollectionUtils.isEmpty(certificateTypeList)) {
            for (Code code : certificateTypeList) {
                // key为证件类型编码，value为证件类型名称
                this.setCertificateType(code.getCodeNo(), code.getCodeName());
            }
            this.log.debug("初始化证件类型代码数据map：" + certificateTypeList.size() + " 条，执行完毕");
        }

        // 初始化企业类别代码map
        List<Code> agentTypeList = this.loadService.getAgentTypeList();
        if (!CollectionUtils.isEmpty(agentTypeList)) {
            for (Code code : agentTypeList) {
                // key为企业类别编码，value为企业类别名称
                this.setAgentType(code.getCodeNo(), code.getCodeName());
            }
            this.log.debug("初始化企业类别代码数据map：" + agentTypeList.size() + " 条，执行完毕");
        }

        // 初始化企业性质代码map
        List<Code> agentNatureList = this.loadService.getAgentNatureList();
        if (!CollectionUtils.isEmpty(agentNatureList)) {
            for (Code code : agentNatureList) {
                // key为企业性质编码，value为企业性质名称
                this.setAgentNature(code.getCodeNo(), code.getCodeName());
            }
            this.log.debug("初始化企业性质代码数据map：" + agentNatureList.size() + " 条，执行完毕");
        }

        // 初始化企业分类代码map
        List<Code> agentClassifyList = this.loadService.getAgentClassifyList();
        if (!CollectionUtils.isEmpty(agentClassifyList)) {
            for (Code code : agentClassifyList) {
                // key为企业分类编码，value为企业分类名称
                this.setAgentClassify(code.getCodeNo(), code.getCodeName());
            }
            this.log.debug("初始化企业分类代码数据map：" + agentClassifyList.size() + " 条，执行完毕");
        }

        // 初始化状态代码map
        List<Code> statusList = this.loadService.getStatusList();
        if (!CollectionUtils.isEmpty(statusList)) {
            for (Code code : statusList) {
                // key为企业分类编码，value为企业分类名称
                this.setStatus(code.getCodeNo(), code.getCodeName());
            }
            this.log.debug("初始化状态代码数据map：" + statusList.size() + " 条，执行完毕");
        }

        //初始化海关商品编码map
        List<ProduecCode> produceCodeList = this.loadService.getProduceCodeList();
        if (!CollectionUtils.isEmpty(produceCodeList)) {
            for (ProduecCode produecCode : produceCodeList) {
                //key为海关商品编码，value为第一计量单位
                this.setProductCodeUnit1(produecCode.getCustomsCode(), produecCode.getUnit1());
                //key为海关商品编码，value为第二计量单位
                this.setProductCodeUnit2(produecCode.getCustomsCode(), produecCode.getUnit2());
                //key为海关商品编码，value为海关商品编码名称
                this.setProductCode(produecCode.getCustomsCode(), produecCode.getProductName());
                this.setProductAddedTax(produecCode.getCustomsCode(), produecCode.getAddedTax());
            }
            this.log.debug(String.format("初始化海关商品代码数据map： %d条，执行完毕", produceCodeList.size()));
        }

//        //初始化成监管方式编码map
//        List<Code> tradeModeList = this.loadService.getSysCodeListByParent(SystemConstants.CODE_TYPE_TRADE_MODE);
//        if (!CollectionUtils.isEmpty(tradeModeList)) {
//            for (Code code : tradeModeList) {
//                this.setTradeMode(code.getCodeNo(), code.getCodeName());
//            }
//            this.log.debug(String.format("初始化监管方式代码数据map： %d条，执行完毕", tradeModeList.size()));
//        }
        // 初始化成交方式编码map
//        List<Code> transModeList = this.loadService.getSysCodeListByParent(SystemConstant.CODE_TYPE_TRANS_MODE);
//        if (!CollectionUtils.isEmpty(transModeList)) {
//            for (Code code : transModeList) {
//                this.setTransMode(code.getCodeNo(), code.getCodeName());
//            }
//            this.log.debug(String.format("初始化成交方式代码数据map： %d条，执行完毕", transModeList.size()));
//        }
    }

    // 行邮税率
    public double getPostalRate(String key) {
        double rate = 0;
        if (this.postalRateMap.containsKey(key)) rate = this.postalRateMap.get(key);
        return rate;
    }

    //完税价格
    public String getPostalPtpriceMap(String key) {
        String price = "";
        if (this.postalPtpriceMap.containsKey(key)) price = this.postalPtpriceMap.get(key);
        return price;
    }

    public void setPostalRate(String key, double value) {
        this.postalRateMap.put(key, value);
    }

    public void setPostPrice(String key, String value) {
        this.postalPtpriceMap.put(key, value);
    }

    private void setPostalUnit(String key, String value) {
        this.postalUnitMap.put(key, value);
    }

    // 申报单位代码
    public String getUnitCode(String key) {
        String unitCode = "";
        if (this.unitCodeMap.containsKey(key)) unitCode = this.unitCodeMap.get(key);
        return unitCode;
    }

    public void setUnitCode(String key, String value) {
        this.unitCodeMap.put(key, value);
    }

    public void setUnitCodes(String key, String value) {
        this.unitCodesMap.put(key, value);
    }


    // 运输方式
    public String getTrafMode(String key) {
        String trafMode = "";
        if (this.trafModeMap.containsKey(key)) trafMode = this.trafModeMap.get(key);
        return trafMode;
    }

    public void setTrafMode(String key, String value) {
        this.trafModeMap.put(key, value);
    }


    // 运输方式
    public String getPort(String key) {
        String port = "";
        if (this.portMap.containsKey(key)) port = this.portMap.get(key);
        return port;
    }


    public void setPort(String key, String value) {
        this.portMap.put(key, value);
    }


    // 包装类型
    public String getPackType(String key) {
        String packType = "";
        if (this.packTypeMap.containsKey(key)) packType = this.packTypeMap.get(key);
        return packType;
    }

    public void setPackType(String key, String value) {
        this.packTypeMap.put(key, value);
    }


    // 征减免税方式代码
    public String getTaxReliefsMode(String key) {
        String taxReliefsMode = "";
        if (this.taxReliefsModeMap.containsKey(key)) taxReliefsMode = this.taxReliefsModeMap.get(key);
        return taxReliefsMode;
    }

    public void setTaxReliefsMode(String key, String value) {
        this.taxReliefsModeMap.put(key, value);
    }

    // 陕西关区代码
    public String getCustoms(String key) {
        String customs = "";
        if (this.customsMap.containsKey(key)) customs = this.customsMap.get(key);
        return customs;
    }

    public void setCustoms(String key, String value) {
        this.customsMap.put(key, value);
    }


    // 全部关区代码
    public String getAllCustoms(String key) {
        String customs = "";
        if (this.allCustomsMap.containsKey(key)) customs = this.allCustomsMap.get(key);
        return customs;
    }

    public void setAllCustoms(String key, String value) {
        this.allCustomsMap.put(key, value);
    }


    // 币制代码
    public String getCurrency(String key) {
        String currency = "";
        if (this.currencyMap.containsKey(key)) currency = this.currencyMap.get(key);
        return currency;
    }

    public void setCurrency(String key, String value) {
        this.currencyMap.put(key, value);
    }


    // 国家地区代码
    public String getCountryArea(String key) {
        String countryArea = "";
        if (this.countryAreaMap.containsKey(key)) countryArea = this.countryAreaMap.get(key);
        return countryArea;
    }

    public void setCountryArea(String key, String value) {
        this.countryAreaMap.put(key, value);
    }


    // 证件类型
    public String getCertificateType(String key) {
        String certificateType = "";
        if (this.certificateTypeMap.containsKey(key)) certificateType = this.certificateTypeMap.get(key);
        return certificateType;
    }

    public void setCertificateType(String key, String value) {
        this.certificateTypeMap.put(key, value);
    }


    // 企业类别
    public String getAgentType(String key) {
        String agentType = "";
        if (this.agentTypeMap.containsKey(key)) agentType = this.agentTypeMap.get(key);
        return agentType;
    }

    public void setAgentType(String key, String value) {
        this.agentTypeMap.put(key, value);
    }


    // 企业性质
    public String getAgentNature(String key) {
        String agentNature = "";
        if (this.agentNatureMap.containsKey(key)) agentNature = this.agentNatureMap.get(key);
        return agentNature;
    }

    public void setAgentNature(String key, String value) {
        this.agentNatureMap.put(key, value);
    }


    // 企业分类
    public String getAgentClassify(String key) {
        String agentClassify = "";
        if (this.agentClassifyMap.containsKey(key)) agentClassify = this.agentClassifyMap.get(key);
        return agentClassify;
    }

    public void setAgentClassify(String key, String value) {
        this.agentClassifyMap.put(key, value);
    }

    // 状态代码
    public String getStatus(String key) {
        String status = "";
        if (this.statusMap.containsKey(key)) status = this.statusMap.get(key);
        return status;
    }

    public void setStatus(String key, String value) {
        this.statusMap.put(key, value);
    }

    public String getProductCodeUnit1(String key) {
        String unit1 = "";
        if (this.productCodeUnit1Map.containsKey(key)) unit1 = this.productCodeUnit1Map.get(key);
        return unit1;
    }

    public void setProductCodeUnit1(String key, String value) {
        this.productCodeUnit1Map.put(key, value);
    }

    public double getProductAddedTax(String key) {
        double addedTax = 0;
        if (this.productAddedTaxMap.containsKey(key)) addedTax = this.productAddedTaxMap.get(key);
        return addedTax;
    }

    public void setProductAddedTax(String key, Double value) {
        this.productAddedTaxMap.put(key, value);
    }

    public void setProductCode(String key, String value) {
        this.productCodeMap.put(key, value);
    }


    public String getProductCodeUnit2(String key) {
        String unit2 = "";
        if (this.productCodeUnit2Map.containsKey(key)) unit2 = this.productCodeUnit2Map.get(key);
        return unit2;
    }

    public void setProductCodeUnit2(String key, String value) {
        this.productCodeUnit2Map.put(key, value);
    }


    public void setTradeMode(String key, String value) {
        this.tradeModeMap.put(key, value);
    }

    public void setTransMode(String key, String value) {
        this.transModeMap.put(key, value);
    }


    // set get 方法
    public Map<String, Double> getPostalRateMap() {
        return postalRateMap;
    }

    public void setPostalRateMap(Map<String, Double> postalRateMap) {
        this.postalRateMap = postalRateMap;
    }

    public void setPostalPtpriceMap(Map<String, String> postalPtpriceMap) {
        this.postalPtpriceMap = postalPtpriceMap;
    }

    public Map<String, String> getUnitCodeMap() {
        return unitCodeMap;
    }

    public void setUnitCodeMap(Map<String, String> unitCodeMap) {
        this.unitCodeMap = unitCodeMap;
    }

    public Map<String, String> getTrafModeMap() {
        return trafModeMap;
    }

    public void setTrafModeMap(Map<String, String> trafModeMap) {
        this.trafModeMap = trafModeMap;
    }

    public Map<String, String> getPortMap() {
        return portMap;
    }

    public void setPortMap(Map<String, String> portMap) {
        this.portMap = portMap;
    }

    public Map<String, String> getPackTypeMap() {
        return packTypeMap;
    }

    public void setPackTypeMap(Map<String, String> packTypeMap) {
        this.packTypeMap = packTypeMap;
    }

    public Map<String, String> getTaxReliefsModeMap() {
        return taxReliefsModeMap;
    }

    public void setTaxReliefsModeMap(Map<String, String> taxReliefsModeMap) {
        this.taxReliefsModeMap = taxReliefsModeMap;
    }

    public Map<String, String> getCustomsMap() {
        return customsMap;
    }

    public void setCustomsMap(Map<String, String> customsMap) {
        this.customsMap = customsMap;
    }

    public Map<String, String> getAllCustomsMap() {
        return allCustomsMap;
    }

    public void setAllCustomsMap(Map<String, String> allCustomsMap) {
        this.allCustomsMap = allCustomsMap;
    }

    public Map<String, String> getCurrencyMap() {
        return currencyMap;
    }

    public void setCurrencyMap(Map<String, String> currencyMap) {
        this.currencyMap = currencyMap;
    }

    public Map<String, String> getCountryAreaMap() {
        return countryAreaMap;
    }

    public void setCountryAreaMap(Map<String, String> countryAreaMap) {
        this.countryAreaMap = countryAreaMap;
    }

    public Map<String, String> getCertificateTypeMap() {
        return certificateTypeMap;
    }

    public void setCertificateTypeMap(Map<String, String> certificateTypeMap) {
        this.certificateTypeMap = certificateTypeMap;
    }

    public Map<String, String> getAgentTypeMap() {
        return agentTypeMap;
    }

    public void setAgentTypeMap(Map<String, String> agentTypeMap) {
        this.agentTypeMap = agentTypeMap;
    }

    public Map<String, String> getAgentNatureMap() {
        return agentNatureMap;
    }

    public void setAgentNatureMap(Map<String, String> agentNatureMap) {
        this.agentNatureMap = agentNatureMap;
    }

    public Map<String, String> getAgentClassifyMap() {
        return agentClassifyMap;
    }

    public void setAgentClassifyMap(Map<String, String> agentClassifyMap) {
        this.agentClassifyMap = agentClassifyMap;
    }

    public Map<String, String> getStatusMap() {
        return statusMap;
    }

    public void setStatusMap(Map<String, String> statusMap) {
        this.statusMap = statusMap;
    }

    public Map<String, String> getUnitCodesMap() {
        return unitCodesMap;
    }

    public Map<String, String> getPostalUnitMap() {
        return postalUnitMap;
    }

    public void setPostalUnitMap(Map<String, String> postalUnitMap) {
        this.postalUnitMap = postalUnitMap;
    }

    public Map<String, String> getProductCodeUnit1Map() {
        return productCodeUnit1Map;
    }

    public void setProductCodeUnit1Map(Map<String, String> productCodeUnit1Map) {
        this.productCodeUnit1Map = productCodeUnit1Map;
    }

    public Map<String, String> getProductCodeUnit2Map() {
        return productCodeUnit2Map;
    }

    public void setProductCodeUnit2Map(Map<String, String> productCodeUnit2Map) {
        this.productCodeUnit2Map = productCodeUnit2Map;
    }

    public Map<String, String> getTradeModeMap() {
        return tradeModeMap;
    }

    public void setTradeModeMap(Map<String, String> tradeModeMap) {
        this.tradeModeMap = tradeModeMap;
    }

    public Map<String, String> getTransModeMap() {
        return transModeMap;
    }

    public void setTransModeMap(Map<String, String> transModeMap) {
        this.transModeMap = transModeMap;
    }

    public Map<String, String> getProductCodeMap() {
        return productCodeMap;
    }

    public Map<String, Double> getProductAddedTaxMap() {
        return productAddedTaxMap;
    }

    public void setProductAddedTaxMap(Map<String, Double> productAddedTaxMap) {
        this.productAddedTaxMap = productAddedTaxMap;
    }


}