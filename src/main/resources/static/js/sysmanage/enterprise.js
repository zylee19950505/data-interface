/**
 * 企业管理
 * Created by 鲍喆 on 2017/08/30.
 */

// 企业管理主页 表格
sw.page.modules["sysmanage/enterprise"] = sw.page.modules["sysmanage/enterprise"] || {
    query: function () {
        var entInfo = $("[name='entInfo']").val();

        // 拼接URL及参数
        var url = sw.serializeObjectToURL("/entManage/allEntInfo", {
            entInfo: entInfo
        });

        sw.datatable("#query-enterprise-table", {
            ajax: url,
            searching: false,
            columns: [
                {data: "ent_name", label: "企业名称"},
                {
                    label: "业务类型", render: function (data, type, row) {
                    switch (row.ent_business_type) {
                        case "E-business":
                            row.ent_business_type = "电商企业";
                            break;
                        case "EB-platform":
                            row.ent_business_type = "电商平台";
                            break;
                        case "Payment":
                            row.ent_business_type = "支付企业";
                            break;
                        case "Logistics":
                            row.ent_business_type = "物流企业";
                            break;
                        case "Storage":
                            row.ent_business_type = "仓储企业";
                            break;
                        default:
                            row.ent_business_type = "";
                            break;
                    }
                    return row.ent_business_type;
                }
                },
                {data: "brevity_code", label: "企业简码"},
                {data: "port_str", label: "主管海关"},
                {data: "customs_code", label: "企业海关代码"},
                {data: "ent_status", label: "企业状态"},
                {
                    label: "创建时间",
                    render: function (data, type, row) {
                        return moment(row.crt_tm).format("YYYY-MM-DD HH:mm:ss");
                    }
                },
                {
                    label: "操作",
                    render: function (data, type, row) {
                        var code =
                            '<button class="btn btn-sm btn-info" title="信息修改" onclick="'
                            + "javascript:sw.loadWorkspace('sysmanage/entEdit?eId=" + row.id
                            + "');" + '"><i class="fa fa-edit"></i> </button> ';
                        if (row.status == "1") {
                            code += '<button class="btn btn-sm btn-danger" title="冻结" onclick="'
                                + "javascript:sw.page.modules['sysmanage/enterprise'].changeStatus('" + row.id
                                + "', '" + row.ent_name + "', '" + row.status + "')"
                                + '"><i class="fa fa-ban"></i> </button> ';
                        } else {
                            code += '<button class="btn btn-sm btn-success" title="激活" onclick="'
                                + "javascript:sw.page.modules['sysmanage/enterprise'].changeStatus('" + row.id
                                + "', '" + row.ent_name + "', '" + row.status + "')"
                                + '"><i class="fa fa-check-circle-o"></i> </button> ';
                        }
                        return code;
                    }
                }
            ]
        });
    },

    changeStatus: function (ent_Id, ent_Name, status) {
        var statusStr = status == "1" ? "已冻结" : "已激活";
        sw.confirm("确定变更企业 \"" + ent_Name + "\"状态为：" + statusStr, "确认", function () {
            sw.ajax("/entManage/editEnterprise/" + ent_Id, "GET", {}, function (rsp) {
                sw.alert(rsp.data.msg, "提示", null, "modal-info");
                sw.page.modules['sysmanage/enterprise'].query();
            });
        });
    },

    init: function () {
        $("[ws-search]").unbind("click").click(this.query).click();
    }
};


// 用户 新增用户，修改用户
sw.page.modules["sysmanage/entEdit"] = sw.page.modules["sysmanage/entEdit"] || {
    back: function () {
        sw.showPageQuery();
    },

    loadEntInfo: function (eId) {
        sw.ajax("entManage/load/" + eId, "", "GET", function (rsp) {
            var data = rsp.data;
            console.log(data);
            if (data !== null) {
                $("input[name='id']").val(data.id);

                $("input[name='ent_name']").val(data.ent_name);
                $("input[name='credit_code']").val(data.credit_code);
                $("input[name='ent_legal']").val(data.ent_legal);
                $("select[name='port']").val(data.port);
                $("input[name='declare_ent_name']").val(data.declare_ent_name);
                $("input[name='assure_ent_name']").val(data.assure_ent_name);
                $("input[name='dxp_id']").val(data.dxp_id);
                $("input[name='area_name']").val(data.area_name);

                $("input[name='brevity_code']").val(data.brevity_code);
                $("input[name='customs_code']").val(data.customs_code);
                $("input[name='ent_phone']").val(data.ent_phone);
                $("select[name='ent_business_type']").val(data.ent_business_type);
                $("input[name='declare_ent_code']").val(data.declare_ent_code);
                $("input[name='assure_ent_code']").val(data.assure_ent_code);
                $("input[name='area_code']").val(data.area_code);
            }
        });
    },

    validateEntForm: function () {
        var ent_name = $("input[name='ent_name']").val();
        var credit_code = $("input[name='credit_code']").val();
        var ent_legal = $("input[name='ent_legal']").val();
        var port = $("select[name='port']").val();
        var declare_ent_name = $("input[name='declare_ent_name']").val();
        var assure_ent_name = $("input[name='assure_ent_name']").val();
        var area_name = $("input[name='area_name']").val();
        var dxp_id = $("input[name='dxp_id']").val();

        var brevity_code = $("input[name='brevity_code']").val();
        var customs_code = $("input[name='customs_code']").val();
        var ent_phone = $("input[name='ent_phone']").val();
        var ent_business_type = $("select[name='ent_business_type']").val();
        var declare_ent_code = $("input[name='declare_ent_code']").val();
        var assure_ent_code = $("input[name='assure_ent_code']").val();
        var area_code = $("input[name='area_code']").val();

        if (isEmpty(ent_name) || ent_name.length < 3) {
            hasErrorEnterprise("input[name='ent_name'", "企业名称不能为空或小于3个字符");
            return false;
        }
        if (isEmpty(credit_code)) {
            hasErrorEnterprise("input[name='credit_code'", "统一社会信用代码不能为空");
            return false;
        }
        if (isEmpty(ent_legal)) {
            hasErrorEnterprise("input[name='ent_legal'", "法定代表人不能为空");
            return false;
        }
        if (isEmpty(port)) {
            hasErrorEnterprise("select[name='port'", "请选择主管海关");
            return false;
        }
        if (isEmpty(declare_ent_name)) {
            hasErrorEnterprise("input[name='declare_ent_name'", "申报企业名称不能为空");
            return false;
        }
        // if (isEmpty(assure_ent_name)) {
        //     hasErrorEnterprise("input[name='assure_ent_name'", "担保企业名称不能为空");
        //     return false;
        // }
        if (isEmpty(dxp_id) || ent_name.length < 3) {
            hasErrorEnterprise("input[name='dxp_id'", "企业DXPID不能为空或小于3个字符");
            return false;
        }

        if (isEmpty(brevity_code)) {
            hasErrorEnterprise("input[name='brevity_code'", "企业简码不能为空");
            return false;
        }
        if (isEmpty(customs_code) || customs_code.length != 10) {
            hasErrorEnterprise("input[name='customs_code'", "海关注册编码不能为空");
            return false;
        }
        if (isEmpty(ent_phone)) {
            hasErrorEnterprise("input[name='ent_phone'", "联系电话不能为空");
            return false;
        }
        if (isEmpty(declare_ent_code)) {
            hasErrorEnterprise("input[name='declare_ent_code'", "申报企业海关注册编码不能为空");
            return false;
        }
        // if (isEmpty(assure_ent_code)) {
        //     hasErrorEnterprise("input[name='assure_ent_code'", "担保企业海关注册编码不能为空");
        //     return false;
        // }

        return true;
    },

    entCreate: function () {
        var entData = sw.serialize("#enterpriseInfo");
        if (!sw.page.modules["sysmanage/entEdit"].validateEntForm()) {
            return;
        }
        sw.ajax("/entManage/createEntInfo", "POST", entData, function (rsp) {
            if (rsp.data.result == "true") {
                sw.alert(rsp.data.msg, "提示", null, "modal-info");
                sw.pageModule("sysmanage/entEdit").back();
                sw.page.modules['sysmanage/enterprise'].query();
                sw.page.modules['sysmanage/enterprise'].clearEntInfo();
            } else {
                $("#errorMsg").html(rsp.data.msg).removeClass("hidden");
            }

        }, function (status, err, xhr) {
            sw.alert(xhr.data, "提示", null, "modal-info");
            return;
        });
    },

    entUpdate: function () {
        var params = sw.getPageParams("sysmanage/entEdit");
        var entData = sw.serialize("#enterpriseInfo");
        if (!sw.page.modules["sysmanage/entEdit"].validateEntForm()) {
            return;
        }
        sw.ajax("/entManage/enterprise/" + params.uId, "PUT", entData, function (rsp) {
            if (rsp.data.result == "true") {
                sw.alert(rsp.data.msg, "提示", null, "modal-info");
                sw.pageModule("sysmanage/entEdit").back();
                sw.page.modules['sysmanage/enterprise'].query();
                sw.page.modules['sysmanage/enterprise'].clearEntInfo();
            } else {
                $("#errorMsg").html(rsp.data.msg).removeClass("hidden");
            }
        }, function (status, err, xhr) {
            sw.alert(xhr.data, "提示", null, "modal-info");
            return;
        });
    },

    clearEntInfo: function () {
        $("input[name='id']").val("");

        $("input[name='ent_name']").val("");
        $("input[name='credit_code']").val("");
        $("input[name='ent_legal']").val("");
        $("select[name='port']").val("");
        $("input[name='declare_ent_name']").val("");
        $("input[name='assure_ent_name']").val("");
        $("input[name='dxp_id']").val("");
        $("input[name='area_name']").val("");

        $("input[name='brevity_code']").val("");
        $("input[name='customs_code']").val("");
        $("input[name='ent_phone']").val("");
        $("select[name='ent_business_type']").val("");
        $("input[name='declare_ent_code']").val("");
        $("input[name='assure_ent_code']").val("");
        $("input[name='area_code']").val("");
    },

    loadSelectCode: function () {
        sw.selectOptionByType("port", "CUSTOMS_CODE");
    },

    init: function () {
        var params = sw.getPageParams("sysmanage/entEdit");
        this.loadSelectCode();
        if (!params) {
            $("#ws-work-title").text("新增企业");
            $("#ws-page-apply").click(this.entCreate);

        } else {
            $("#ws-work-title").text("编辑企业");
            $("#ws-page-apply").unbind("click").click(this.entUpdate);
            // 等待0.5秒，以便下拉菜单加载完毕
            setTimeout(function () {
                sw.page.modules["sysmanage/entEdit"].loadEntInfo(params.eId)
            }, 500);
        }
        $("input,select").change(function () {
            $(this).parent().removeClass("has-error");
            $(this).parent().find(".help-block").addClass("hidden").html("");
        }).focus(function () {
            $("#errorMsg").html("").addClass("hidden");
        });
        $("#ws-page-back").click(this.back);
    }
};

function hasErrorEnterprise(selecter, errorMsg) {
    $(selecter).parent().addClass("has-error");
    $(selecter).parent().find(".help-block").removeClass("hidden").html(errorMsg);
    $(selecter).focus();
};