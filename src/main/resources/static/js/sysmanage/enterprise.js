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
                    {data: "ent_business_type", label: "业务类型"},

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
                                '<button class="btn btn-sm btn-primary" title="信息修改" onclick="'
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
            var statusStr = status == "1"?"已冻结":"已激活";
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
                    $("input[name='ent_code']").val(data.ent_code);
                    $("input[name='ent_legal']").val(data.ent_legal);
                    $("input[name='ent_phone']").val(data.ent_phone);
                    $("input[name='ent_unique_code']").val(data.ent_unique_code);
                    $("input[name='org_code']").val(data.org_code);
                    $("input[name='business_code']").val(data.business_code);

                    $("input[name='tax_code']").val(data.tax_code);
                    $("input[name='credit_code']").val(data.credit_code);
                    $("select[name='ent_type']").val(data.ent_type);
                    $("select[name='ent_nature']").val(data.ent_nature);
                    $("select[name='port']").val(data.port);
                    $("input[name='customs_code']").val(data.customs_code);
                    $("select[name='ent_classify']").val(data.ent_classify);
                    $("input[name='dxp_id']").val(data.dxp_id);

                    $("select[name='ent_business_type']").val(data.ent_business_type);

                    // $("input[name='business_license']").attr("disabled", "disabled");
                    // $("input[name='organization_code']").attr("disabled", "disabled");
                    // $("input[name='tax_registration_code']").attr("disabled", "disabled");
                    // $("input[name='corporate_customs_code']").attr("disabled", "disabled");
                }
            });
        },
        validateEntForm: function () {
            var ent_name = $("input[name='ent_name']").val();
            var ent_code = $("input[name='ent_code']").val();
            var ent_legal = $("input[name='ent_legal']").val();
            var ent_phone = $("input[name='ent_phone']").val();
            var ent_unique_code = $("input[name='ent_unique_code']").val();
            var org_code = $("input[name='org_code']").val();
            var business_code = $("input[name='business_code']").val();

            var tax_code = $("input[name='tax_code']").val();
            var credit_code = $("input[name='credit_code']").val();
            var customs_code = $("input[name='customs_code']").val();
            var ent_type = $("select[name='ent_type']").val();
            var ent_nature = $("select[name='ent_nature']").val();
            var ent_classify = $("select[name='ent_classify']").val();
            var dxp_id = $("input[name='dxp_id']").val();
            var port = $("select[name='port']").val();

            var ent_business_type = $("select[name='ent_business_type']").val();

            if (isEmpty(dxp_id) || ent_name.length < 3) {
                hasErrorEnterprise("input[name='dxp_id'", "企业DXPID不能为空或小于3个字符");
                return false;
            }
            if (isEmpty(ent_name) || ent_name.length < 3) {
                hasErrorEnterprise("input[name='ent_name'", "企业名称不能为空或小于3个字符");
                return false;
            }
            if (isEmpty(ent_code)) {
                hasErrorEnterprise("input[name='ent_code'", "企业代码不能为空");
                return false;
            }
            if (isEmpty(ent_legal)) {
                hasErrorEnterprise("input[name='ent_legal'", "企业法人不能为空");
                return false;
            }
            if (isEmpty(ent_phone)) {
                hasErrorEnterprise("input[name='ent_phone'", "企业电话不能为空");
                return false;
            }
            if (isEmpty(ent_unique_code)) {
                hasErrorEnterprise("input[name='ent_unique_code'", "企业唯一编号不能为空");
                return false;
            }
            if (isEmpty(credit_code)) {
                hasErrorEnterprise("input[name='credit_code'", "企业统一社会信用代码不能为空");
                return false;
            }
            if (isEmpty(business_code) || business_code.length < 3) {
                hasErrorEnterprise("input[name='business_code'", "工商营业执照号不能为空或小于3个字符");
                return false;
            }
            if (isEmpty(org_code) || org_code.length < 3) {
                hasErrorEnterprise("input[name='org_code'", "组织机构代码不能为空或小于3个字符");
                return false;
            }
            if (isEmpty(tax_code) || tax_code.length < 3) {
                hasErrorEnterprise("input[name='tax_code'", "税务登记代码不能为空或小于3个字符");
                return false;
            }
            if (isEmpty(customs_code) || customs_code.length != 10) {
                hasErrorEnterprise("input[name='customs_code'", "请填写海关10位企业代码")
                return false;
            }
            if (isEmpty(ent_nature)) {
                hasErrorEnterprise("select[name='ent_nature'", "请选择企业性质");
                return false;
            }
            if (isEmpty(port)) {
                hasErrorEnterprise("select[name='port'", "请选择主管海关");
                return false;
            }
            if (isEmpty(ent_type)) {
                hasErrorEnterprise("select[name='ent_type'", "企业类别不能为空");
                return false;
            }
            if (isEmpty(ent_classify)) {
                hasErrorEnterprise("select[name='ent_classify'", "企业分类不能为空");
                return false;
            }
            // if (isEmpty(ent_business_type)) {
            //     hasErrorEnterprise("select[name='ent_business_type'", "企业业务类别");
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
            $("input[name='ent_code']").val("");
            $("input[name='ent_legal']").val("");
            $("input[name='ent_phone']").val("");
            $("input[name='ent_unique_code']").val("");
            $("input[name='org_code']").val("");
            $("input[name='business_code']").val("");

            $("input[name='tax_code']").val("");
            $("input[name='credit_code']").val("");
            $("select[name='ent_type']").val("");
            $("select[name='ent_nature']").val("");
            $("select[name='port']").val("");
            $("input[name='customs_code']").val("");
            $("select[name='ent_classify']").val("");
            $("input[name='dxp_id']").val("");

            $("select[name='ent_business_type']").val("");
        },
        loadSelectCode: function () {
            sw.selectOptionByType("ent_classify", "AGENT_CODE");
            sw.selectOptionByType("ent_type", "AGENT_TYPE");
            sw.selectOptionByType("port", "CUSTOMS_CODE");
            sw.selectOptionByType("ent_nature", "AGENT_NATURE");

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
    }


function hasErrorEnterprise(selecter, errorMsg) {
    $(selecter).parent().addClass("has-error");
    $(selecter).parent().find(".help-block").removeClass("hidden").html(errorMsg);
    $(selecter).focus();
}