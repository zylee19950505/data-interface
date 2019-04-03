package com.xaeport.crossborder.generated.thread;

import com.xaeport.crossborder.configuration.AppConfiguration;
import com.xaeport.crossborder.configuration.SystemConstants;
import com.xaeport.crossborder.data.entity.*;
import com.xaeport.crossborder.data.mapper.BuilderDetailMapper;
import com.xaeport.crossborder.data.mapper.EnterpriseMapper;
import com.xaeport.crossborder.data.status.StatusCode;
import com.xaeport.crossborder.service.sysmanage.UserManageService;
import com.xaeport.crossborder.tools.IdUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.text.DecimalFormat;
import java.util.*;

public class BuilderDetailThread implements Runnable {

    private Log logger = LogFactory.getLog(this.getClass());
    private BuilderDetailMapper builderDetailMapper;
    private EnterpriseMapper enterpriseMapper;
    private AppConfiguration appConfiguration;

    /*@Autowired
    EnterpriseMapper enterpriseMapper;*/

    @Autowired
    protected UserManageService userMaService;

    //无参数的构造方法。
    private BuilderDetailThread() {
    }

    //有参数的构造方法。
    public BuilderDetailThread(BuilderDetailMapper builderDetailMapper, EnterpriseMapper enterpriseMapper, AppConfiguration appConfiguration) {
        this.builderDetailMapper = builderDetailMapper;
        this.enterpriseMapper = enterpriseMapper;
        this.appConfiguration = appConfiguration;


    }

    @Override
    public void run() {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("dataStatus", "QDSCZ");//清单生成中

        //HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();;
        List<String> orderList;
        ImpOrderHead imporderHead;
        List<ImpOrderBody> impOrderBodyList;
        ImpLogistics impLogistics;
        List<ImpInventoryBody> impInventoryBodyList = null;
        ImpInventoryHead impInventoryHead = new ImpInventoryHead();
        while (true) {
            try {
                orderList = this.builderDetailMapper.findBuilderOrderNo(paramMap);
                if (CollectionUtils.isEmpty(orderList)) {
                    // 如无待生成数据，则等待3s后重新确认
                    try {
                        Thread.sleep(3000);
                        logger.debug("未发现需生成清单的数据，中等待3秒");
                    } catch (InterruptedException e) {
                        logger.error("生成清单暂停时发生异常", e);
                    }
                    continue;
                }
                for (String orderNo : orderList) {
                    //利用订单编号查询出来订单数据
                    imporderHead = this.builderDetailMapper.queryOrderHead(orderNo);
                    impOrderBodyList = this.builderDetailMapper.queryOrderList(orderNo);
                    //查询出来运单数据
                    impLogistics = this.builderDetailMapper.queryLogistics(orderNo);
                    //将订单数据和运单数据整合生成清单数据
                    //根据订单的商品料号去查账册备案清单,找出法定数量,法定计量单位
                    //封装订单和运单里的数据
                    String ent_id = imporderHead.getEnt_id();
                    //Enterprise enterpriseDetail = enterpriseMapper.getEnterpriseDetail(ent_id);
                    Enterprise enterpriseDetail = builderDetailMapper.getEnterpriseDetail(ent_id);
                    try {
                        impInventoryHead = this.getInventoryData(imporderHead, impOrderBodyList, impLogistics, enterpriseDetail);
                        impInventoryBodyList = this.getInventoryBodyData(impOrderBodyList, imporderHead.getBusiness_type(), enterpriseDetail, impInventoryHead.getGuid());
                    } catch (Exception e) {
                        logger.error("生成清单["+orderNo+"]时发生异常，等待5秒重新开始获取数据", e);
                        String dataStatus = "QDSCSB";//清单生成失败
                        builderDetailMapper.updateBuilderCacheByOrderNo(orderNo,dataStatus);
                        continue;
                    }

                    //先在这里进行库存检查
                    //CountLoader countLoader = new CountBudDetail();
                    //int flag = countLoader.count(impInventoryBodyList, enterpriseDetail);

                    //if (flag == 0) {}
                        //信息整合完毕后,进行插入操作
                    this.builderDetailMapper.insertImpInventoryHead(impInventoryHead);
                    for (ImpInventoryBody impInventoryBody : impInventoryBodyList) {
                        this.builderDetailMapper.insertImpInventoryBody(impInventoryBody);
                    }
                    //再将缓存数据库的数据状态改为已经生成;
                    String dataStatus = "QDYSC";//清单已生成
                    this.builderDetailMapper.updateBuilderCacheByOrderNo(orderNo, dataStatus);

                }
            } catch (Exception e) {
                try {
                    Thread.sleep(5000);
                    logger.error("生成清单时发生异常，等待5秒重新开始获取数据", e);
                } catch (InterruptedException ie) {
                    logger.error("生成清单时发生异常", ie);
                }
            }
        }


    }

    private List<ImpInventoryBody> getInventoryBodyData(List<ImpOrderBody> impOrderBodyList, String business_type, Enterprise enterpriseDetail, String guid) {

        //查找账册信息里企业id是订单的企业id的情况
        //String bws_no = this.builderDetailMapper.queryBwsNoByEntId(enterpriseDetail.getId(), enterpriseDetail.getEnt_name());

        //账册企业信息的企业信息
        Enterprise enterprise = this.builderDetailMapper.queryAreaenterprise(enterpriseDetail.getArea_code());
        String emsNo = this.builderDetailMapper.queryBwlHeadType(enterprise.getId());
        List<ImpInventoryBody> impInventoryBodyList = new ArrayList<>();
        int count = 0;
        for (ImpOrderBody impOrderBody : impOrderBodyList) {
            count++;
            ImpInventoryBody impInventoryBody = new ImpInventoryBody();
            //根据账册号找账册表体信息,通过商品货号确定商品账册信息
            BwlListType bwlListType = this.builderDetailMapper.queryBwsListByEntBwsNo(emsNo, impOrderBody.getItem_No(), enterpriseDetail.getBrevity_code());
            impInventoryBody.setHead_guid(guid);
            impInventoryBody.setCurrency("142");//币制
            impInventoryBody.setG_num(count);//商品序号
            DecimalFormat df = new DecimalFormat("0.00000");
            impInventoryBody.setOrder_no(impOrderBody.getOrder_No());//订单编号
            if ("BONDORDER".equals(business_type)) {
                //impInventoryBody.setItem_record_no(bws_no);//账册备案料号: 保税进口必填()

                impInventoryBody.setItem_record_no(bwlListType.getGds_mtno());//账册备案料号: 保税进口必填()
                //impInventoryBody.setItem_record_no(impInventoryBody.getItem_no());
            }
            impInventoryBody.setItem_no(impOrderBody.getItem_No());//企业商品货号: 电商企业自定义的商品货号（SKU）。
            impInventoryBody.setItem_name(impOrderBody.getItem_Name());//企业商品品名: 交易平台销售商品的中文名称。
            impInventoryBody.setG_code(bwlListType.getGdecd());//商品编码: 按商品分类编码规则确定的进出口商品的商品编号，分为商品编号和附加编号，其中商品编号栏应填报《中华人民共和国进出口税则》8位税则号列，附加编号应填报商品编号，附加编号第9、10位。
            impInventoryBody.setG_name(impOrderBody.getItem_Name());//商品名称: 商品名称应据实填报，与电子订单一致。
            impInventoryBody.setG_model(impOrderBody.getG_Model());//商品规格型号: 满足海关归类、审价以及监管的要求为准。包括：品牌、规格、型号等。
            impInventoryBody.setBar_code(impOrderBody.getBar_Code());//条形码: 商品条形码一般由前缀部分、制造厂商代码、商品代码和校验码组成。
            impInventoryBody.setCountry(impOrderBody.getCountry());//原产国（地区）: 填写海关标准的参数代码，参照《JGS-20 海关业务代码集》- 国家（地区）代码表。
            impInventoryBody.setUnit(impOrderBody.getUnit());//计量单位


            impInventoryBody.setUnit1(bwlListType.getLawf_unitcd());//第一计量单位
            if (!StringUtils.isEmpty(bwlListType.getSecd_lawf_unitcd())){
                impInventoryBody.setUnit2(bwlListType.getSecd_lawf_unitcd());//第二计量单位
            }

            impInventoryBody.setNote(impOrderBody.getNote());//促销活动，商品单价偏离市场价格的，可以在此说明。
            impInventoryBody.setQuantity(Double.parseDouble(impOrderBody.getQty()));
            impInventoryBody.setQty(impOrderBody.getQty());//商品实际数量
            impInventoryBody.setQty1(bwlListType.getIn_lawf_qty());//第一法定数量
            if (!StringUtils.isEmpty(bwlListType.getIn_secd_lawf_qty())){
                impInventoryBody.setQty2(bwlListType.getIn_secd_lawf_qty());//第二法定数量
            }
            impInventoryBody.setTotal_price(impOrderBody.getTotal_Price());//总价

            double Price = Double.parseDouble(impInventoryBody.getTotal_price()) / Double.parseDouble(impInventoryBody.getQty());
            if (!StringUtils.isEmpty(Price)) {
                String price = df.format(Price);
                impInventoryBody.setPrice(price);//单价
            }
//            this.builderDetailMapper.insertImpInventoryBody(impInventoryBody);
            impInventoryBodyList.add(impInventoryBody);
        }
        return impInventoryBodyList;
    }


    private ImpInventoryHead getInventoryData(ImpOrderHead imporderHead, List<ImpOrderBody> impOrderBodyList, ImpLogistics impLogistics, Enterprise enterpriseDetail) {
        String crt_id = imporderHead.getCrt_id();


        ImpInventoryHead impInventoryHead = new ImpInventoryHead();
        impInventoryHead.setOrder_no(imporderHead.getOrder_No());//交易平台的订单编号，同一交易平台的订单编号应唯一。订单编号长度不能超过60位。
        impInventoryHead.setEbp_code(imporderHead.getEbp_Code());//电商平台的海关注册登记编号；电商平台未在海关注册登记，由电商企业发送订单的，以中国电子口岸发布的电商平台标识编号为准。
        impInventoryHead.setEbp_name(imporderHead.getEbp_Name());//电商平台的海关注册登记名称；电商平台未在海关注册登记，由电商企业发送订单的，以中国电子口岸发布的电商平台名称为准。
        impInventoryHead.setEbc_code(imporderHead.getEbc_Code());//电商企业的海关注册登记编号。
        impInventoryHead.setEbc_name(imporderHead.getEbc_Name());//电商企业的海关注册登记名称。
        impInventoryHead.setLogistics_no(impLogistics.getLogistics_no());//物流企业的运单包裹面单号。同一物流企业的运单编号在6个月内不重复。运单编号长度不能超过60位。
        impInventoryHead.setLogistics_code(impLogistics.getLogistics_code());//物流企业的海关注册登记编号。
        impInventoryHead.setLogistics_name(impLogistics.getLogistics_name());//物流企业在海关注册登记的名称。
//            impInventoryHead.setCop_no(value.get(copNoIndex));//企业内部标识单证的编号。
//            impInventoryHead.setPre_no("");//电子口岸标识单证的编号。
        impInventoryHead.setAssure_code(enterpriseDetail.getAssure_ent_code());//担保扣税的企业海关注册登记编号，只限清单的电商平台企业、电商企业、物流企业。

//            impInventoryHead.setInvt_no("");//海关接受申报生成的清单编号。
        impInventoryHead.setCustoms_code("");//接受清单申报的海关关区代码，参照JGS/T 18《海关关区代码》。
        impInventoryHead.setPort_code("");//商品实际进出我国关境口岸海关的关区代码，参照JGS/T 18《海关关区代码》。
//            impInventoryHead.setIe_date("");//运载所申报商品的运输工具申报进境的日期，进口申报时无法确知相应的运输工具的实际进境日期时，免填。格式:YYYYMMDD
        impInventoryHead.setBuyer_id_number(imporderHead.getBuyer_Id_Number());//订购人的身份证件号码。
        impInventoryHead.setBuyer_name(imporderHead.getBuyer_Name());//订购人的真实姓名。
        impInventoryHead.setBuyer_telephone(imporderHead.getBuyer_TelePhone());//订购人电话。
        impInventoryHead.setConsignee_address(imporderHead.getConsignee_Address());//收货地址
        impInventoryHead.setAgent_code(enterpriseDetail.getEnt_code());//申报单位的海关注册登记编号。
        impInventoryHead.setAgent_name(enterpriseDetail.getEnt_name());//申报单位在海关注册登记的名称。
//        impInventoryHead.setArea_code(enterpriseDetail.getArea_code());//保税模式必填，区内仓储企业的海关注册登记编号。
//        impInventoryHead.setArea_name(enterpriseDetail.getArea_name());//保税模式必填，区内仓储企业在海关注册登记的名称。

//            impInventoryHead.setVoyage_no(value.get(flightVoyageIndex));//直购进口必填。货物进出境的运输工具的航次编号。保税进口免填。
//            impInventoryHead.setBill_no(value.get(billNoIndex));//直购进口必填。货物提单或运单的编号，保税进口免填。

//            impInventoryHead.setLoct_no("");//针对同一申报地海关下有多个跨境电子商务的监管场所,需要填写区分
//            impInventoryHead.setLicense_no("");//商务主管部门及其授权发证机关签发的进出口货物许可证件的编号
        impInventoryHead.setCountry(impOrderBodyList.get(0).getCountry());//直购进口填写起始发出国家（地区）代码，参照《JGS-20 海关业务代码集》的国家（地区）代码表；保税进口填写代码“142”。
//            impInventoryHead.setWrap_type("");//海关对进出口货物实际采用的外部包装方式的标识代码，采用1 位数字表示，如：木箱、纸箱、桶装、散装、托盘、包、油罐车等
//            impInventoryHead.setNote(value.get(noteIndex));//备注
        impInventoryHead.setWrap_type("");//包装种类

        impInventoryHead.setInsured_fee(impLogistics.getInsured_fee());//物流企业实际收取的商品保价费用。
        impInventoryHead.setFreight(impLogistics.getFreight());//运杂费


        double totalPrices = 0;
        for (ImpOrderBody impOrderBody : impOrderBodyList) {
            totalPrices += Double.parseDouble(impOrderBody.getTotal_Price());
        }
        impInventoryHead.setTotal_prices(String.valueOf(totalPrices));//取表体商品总价之和


        //表头自生成信息
        impInventoryHead.setGuid(IdUtils.getUUId());//企业系统生成36 位唯一序号（英文字母大写）
        impInventoryHead.setCop_no(enterpriseDetail.getCustoms_code() + IdUtils.getShortUUId().substring(0, 10));
        impInventoryHead.setApp_type("1");//企业报送类型。1-新增2-变更3-删除。默认为1。
        impInventoryHead.setApp_status("2");//业务状态:1-暂存,2-申报,默认为2。
        impInventoryHead.setIe_flag("I");//电子订单类型：I进口
        impInventoryHead.setBuyer_id_type("1");//订购人证件类型
        impInventoryHead.setTrade_mode("9610");//贸易方式
        impInventoryHead.setCurrency("142");//币制
        impInventoryHead.setPack_no("1");//件数
        //运载所申报商品的运输工具申报进境的日期，进口申报时无法确知相应的运输工具的实际进境日期时，免填。格式:YYYYMMDD
        impInventoryHead.setIe_date(new Date());//进口日期


        impInventoryHead.setBill_no(imporderHead.getBill_No().trim());

        impInventoryHead.setCrt_id(StringUtils.isEmpty(crt_id) ? "" : crt_id);//创建人
        impInventoryHead.setCrt_tm(new Date());//创建时间
        impInventoryHead.setUpd_id(StringUtils.isEmpty(crt_id) ? "" : crt_id);//更新人
        impInventoryHead.setUpd_tm(new Date());//更新时间
        impInventoryHead.setEnt_id(enterpriseDetail.getId());
        impInventoryHead.setEnt_name(enterpriseDetail.getEnt_name());
        impInventoryHead.setEnt_customs_code(enterpriseDetail.getCustoms_code());
        if ("ORDER".equals(imporderHead.getBusiness_type())) {
            //直购的毛重和净重从运单里获取
            impInventoryHead.setGross_weight(Double.toString(Double.parseDouble(impLogistics.getWeight()) / 1.1));//货物及其包装材料的重量之和，计量单位为千克。
            impInventoryHead.setNet_weight(impLogistics.getWeight());//货物的毛重减去外包装材料后的重量，即货物本身的实际重量，计量单位为千克。
            impInventoryHead.setVoyage_no(StringUtils.isEmpty(impLogistics.getVoyage_no()) ? "" : impLogistics.getVoyage_no());
            impInventoryHead.setTraf_mode("");//填写海关标准的参数代码，参照《JGS-20 海关业务代码集》- 运输方式代码。直购进口指跨境段物流运输方式，保税进口指二线出区物流运输方式。
            impInventoryHead.setTraf_no("");//直购进口必填。货物进出境的运输工具的名称或运输工具编号。填报内容应与运输部门向海关申报的载货清单所列相应内容一致；同报关单填制规范。保税进口免填。

            impInventoryHead.setBusiness_type(SystemConstants.T_IMP_INVENTORY);
            impInventoryHead.setData_status(StatusCode.QDDSB);//数据状态(暂存)
        } else if ("BONDORDER".equals(imporderHead.getBusiness_type())) {
            //保税的毛重和净重从订单获取
            impInventoryHead.setGross_weight(imporderHead.getGross_weight());//货物及其包装材料的重量之和，计量单位为千克。
            impInventoryHead.setNet_weight(imporderHead.getNet_weight());//货物的毛重减去外包装材料后的重量，即货物本身的实际重量，计量单位为千克。

            //查找企业信息里的区内企业信息
            //再在账册表头里查找创建账册的创建人所属企业ID匹配账册信息
            Enterprise enterprise = this.builderDetailMapper.queryAreaenterprise(enterpriseDetail.getArea_code());
            String bws_no = this.builderDetailMapper.queryBwlHeadType(enterprise.getId());
            //String bws_no = this.builderDetailMapper.queryBwsNoByEntId(enterpriseDetail.getEnt_code(), enterpriseDetail.getEnt_name());

            //账册编号通过查找企业信息的区内企业名称和区内企业编码,去账册信息里查找
            impInventoryHead.setEms_no(bws_no);//保税模式必填，填写区内仓储企业在海关备案的账册编号，用于保税进口业务在特殊区域辅助系统记账（二线出区核减）。
            impInventoryHead.setArea_code(enterprise.getArea_code());//保税模式必填，区内仓储企业的海关注册登记编号。(企业信息里有)
            impInventoryHead.setArea_name(enterprise.getArea_name());//保税模式必填，区内仓储企业在海关注册登记的名称。(企业信息里有)
            impInventoryHead.setBusiness_type(SystemConstants.T_IMP_BOND_INVEN);
            impInventoryHead.setData_status(StatusCode.BSQDDSB);//数据状态(暂存)
        }
        return impInventoryHead;
    }
}


