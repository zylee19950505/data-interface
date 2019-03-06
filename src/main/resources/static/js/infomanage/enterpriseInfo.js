/**
 * Created by zwj on 2017/7/24.
 */
sw.page.modules["infomanage/enterpriseInfo"] = sw.page.modules["infomanage/enterpriseInfo"] || {
    init: function () {
        // this.loadSelectCode();
        $("input[name='u_id']").val(sw.user.account);
        $("#enterpriseId").find("input[type='text'],select").each(function () {
            $(this).attr("disabled", "disabled");
        });
        this.loadEnterprise();
    },

    // loadSelectCode: function () {
    //     sw.selectOptionByType("enterprise_classification","AGENT_CODE");
    //     sw.selectOptionByType("business_category","AGENT_TYPE");
    //     sw.selectOptionByType("competent_customs","CUSTOMS_CODE");
    //     sw.selectOptionByType("enterprise_nature", "AGENT_NATURE");
    // },

    loadEnterprise: function () {
        var id = sw.user.enterpriseId;
        sw.ajax("enterprise/load/", "", "GET", function (rsp) {
            var data = rsp.data;
            if (data !== null) {
                $("input[name='ent_name']").val(data.ent_name);
                $("input[name='ent_code']").val(data.ent_code);
                $("input[name='ent_legal']").val(data.ent_legal);
                $("input[name='ent_phone']").val(data.ent_phone);
                $("input[name='ent_unique_code']").val(data.ent_unique_code);
                $("input[name='org_code']").val(data.org_code);
                $("input[name='business_code']").val(data.business_code);
                $("input[name='tax_code']").val(data.tax_code);
                $("input[name='credit_code']").val(data.credit_code);
                switch (data.ent_type) {
                    case "0": {
                        $("input[name='ent_type']").val("企业");
                        break;
                    }
                    case "1": {
                        $("input[name='ent_type']").val("自然人");
                        break;
                    }
                    default :
                        "未知";
                }
                /*$("input[name='ent_type']").val(data.ent_type);*/
                switch (data.ent_nature) {
                    case "1": {
                        $("input[name='ent_nature']").val("国营");
                        break;
                    }
                    case "2": {
                        $("input[name='ent_nature']").val("合作");
                        break;
                    }
                    case "3": {
                        $("input[name='ent_nature']").val("合资");
                        break;
                    }
                    case "4": {
                        $("input[name='ent_nature']").val("独资");
                        break;
                    }
                    case "5": {
                        $("input[name='ent_nature']").val("集体");
                        break;
                    }
                    case "6": {
                        $("input[name='ent_nature']").val("私营");
                        break;
                    }
                    case "7": {
                        $("input[name='ent_nature']").val("个体");
                        break;
                    }
                    case "8": {
                        $("input[name='ent_nature']").val("报关");
                        break;
                    }
                    case "9": {
                        $("input[name='ent_nature']").val("其他");
                        break;
                    }
                    default :
                        "未知";
                }

                /* $("input[name='ent_nature']").val(data.ent_nature);*/

                $("input[name='port']").val(data.port);
                switch (data.port) {
                    case "9000": {
                        $("input[name='port']").val("西安关区");
                        break;
                    }
                    case "9001": {
                        $("input[name='port']").val("关中海关");
                        break;
                    }
                    case "9002": {
                        $("input[name='port']").val("咸阳机场");
                        break;
                    }
                    case "9003": {
                        $("input[name='port']").val("宝鸡海关");
                        break;
                    }
                    case "9004": {
                        $("input[name='port']").val("邮局海关");
                        break;
                    }
                    case "9005": {
                        $("input[name='port']").val("陕加工A");
                        break;
                    }
                    case "9006": {
                        $("input[name='port']").val("陕加工B");
                        break;
                    }
                    case "9007": {
                        $("input[name='port']").val("西安综保");
                        break;
                    }
                    case "9008": {
                        $("input[name='port']").val("高新综保");
                        break;
                    }
                    case "9009": {
                        $("input[name='port']").val("车站海关");
                        break;
                    }
                    case "9010": {
                        $("input[name='port']").val("延安海关");
                        break;
                    }
                    case "9011": {
                        $("input[name='port']").val("渭南海关");
                        break;
                    }
                    case "9012": {
                        $("input[name='port']").val("榆林海关");
                        break;
                    }
                    case "9013": {
                        $("input[name='port']").val("西咸物流");
                        break;
                    }
                    case "9014": {
                        $("input[name='port']").val("汉中海关");
                        break;
                    }
                    case "9015": {
                        $("input[name='port']").val("商洛海关");
                        break;
                    }
                    default :
                        "未知";
                }

                $("input[name='customs_code']").val(data.customs_code);
                $("input[name='ent_classify']").val(data.ent_classify);
                $("input[name='dxp_id']").val(data.dxp_id);
                //ent_business_type
                $("input[name='ent_business_type']").val(data.ent_business_type);
            }
        });
    }

};