package com.xaeport.crossborder.service;

import com.xaeport.crossborder.data.entity.Code;
import com.xaeport.crossborder.data.entity.ProduecCode;
import com.xaeport.crossborder.data.mapper.LoadMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoadService {

    @Autowired
    LoadMapper loadMapper;


    /**
     * 获取申报计量单位信息集合
     *
     * @return 申报计量单位信息集合
     */
    public List<Code> getUnitCodeList() {
        return this.loadMapper.getUnitCodeList();
    }

    /**
     * 获取运输方式信息集合
     *
     * @return 运输方式信息集合
     */
    public List<Code> getTrafModeList() {
        return this.loadMapper.getTrafModeList();
    }

    /**
     * 获取港口信息集合
     *
     * @return 港口信息集合
     */
    public List<Code> getPortList() {
        return this.loadMapper.getPortList();
    }

    /**
     * 获取包装类型信息集合
     *
     * @return 包装类型信息集合
     */
    public List<Code> getPackTypeList() {
        return this.loadMapper.getPackTypeList();
    }

    /**
     * 获取关区代码信息集合
     *
     * @return 关区代码信息集合
     */
    public List<Code> getCustomsList() {
        return this.loadMapper.getCustomsList();
    }

    /**
     * 获取币制信息集合
     *
     * @return 币制信息集合
     */
    public List<Code> getCurrencyList() {
        return this.loadMapper.getCurrencyList();
    }

    /**
     * 获取国家地区信息集合
     *
     * @return 国家地区信息集合
     */
    public List<Code> getCountryAreaList() {
        return this.loadMapper.getCountryAreaList();
    }

    /**
     * 获取证件类型信息集合
     *
     * @return 证件类型信息集合
     */
    public List<Code> getCertificateTypeList() {
        return this.loadMapper.getCertificateTypeList();
    }

    /**
     * 获取企业类别信息集合（企业、自然人）
     *
     * @return 企业类别信息集合
     */
    public List<Code> getAgentTypeList() {
        return this.loadMapper.getAgentTypeList();
    }

    /**
     * 获取企业性质信息集合（国营、合资、私营、集体....）
     *
     * @return 企业性质信息集合
     */
    public List<Code> getAgentNatureList() {
        return this.loadMapper.getAgentNatureList();
    }

    /**
     * 获取企业分类信息集合（境内、境外）
     *
     * @return 企业分类信息集合
     */
    public List<Code> getAgentClassifyList() {
        return this.loadMapper.getAgentClassifyList();
    }

    /**
     * 获取状态代码信息集合
     *
     * @return 状态代码信息集合
     */
    public List<Code> getStatusList() {
        return this.loadMapper.getStatusList();
    }

    /**
     * 获取海关商品编码集合
     *
     * @return
     */
    public List<ProduecCode> getProduceCodeList() {
        return this.loadMapper.getProduceCodeList();
    }


//    /**
//     * 根据代码类型获取代码集
//     * @return
//     */
//    public List<Code> getSysCodeListByParent(String codeType){
//        return this.loadMapper.getSysCodeListByParent(codeType);
//    }

}
