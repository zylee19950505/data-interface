/**
 * 企业管理
 * Created by 鲍喆 on 2017/08/30.
 */

// 企业管理主页 表格
sw.page.modules["sysmanage/enterprise"] = sw.page.modules["sysmanage/enterprise"] || {
        query: function () {
            var entInfo = $("[name='entInfo']").val();

            // 拼接URL及参数
            var url = sw.serializeObjectToURL("api/auth/enterprise", {
                entInfo: entInfo
            });

            sw.datatable("#query-enterprise-table", {
                ajax: url,
                searching: false,
                columns: [
                    {data: "ENTERPRISE_NAME", label: "企业名称"},
                    {data: "COMPETENT_CUSTOMS_STR", label: "主管海关"},
                    {data: "CORPORATE_CUSTOMS_CODE", label: "企业海关代码"},
                    {data: "ENT_STATUS_STR", label: "企业状态"},
                    {
                        label: "创建时间",
                        render: function (data, type, row) {
                            return moment(row.CREATE_TIME).format("YYYY-MM-DD HH:mm:ss");
                        }
                    },
                    {
                        label: "身份证校验",
                        render: function (data, type, row) {
                            var pay = "isPay", Class, Color, title;
                            var value = row.VALIDATETIMES;
                            if (row.ISPAY === "Y") {
                                Class = 'fa fa-toggle-on';
                                Color = 'green';
                                title = "已缴费";
                            } else {
                                Class = 'fa fa-toggle-off';
                                Color = 'red';
                                title = "未交费";
                            }
                            var isPay;
                            var code;
                            var vaidateTimes = sw.page.modules['sysmanage/enterprise'].validateTimes(row.ENT_ID, row.ISPAY, row.ENT_STATUS, value);
                            if (row.ENT_STATUS == "0") {
                                isPay = '<button class="btn btn-sm" title="' + title + '" disabled onclick="'
                                    + "javascript:sw.page.modules['sysmanage/enterprise'].isEnablement('" + row.ENT_ID + "','" + row.ISPAY + "','" + pay + "');" + '">' +
                                    '<i class="' + Class + '" style="color:' + Color + '"></i></button> ';

                                code = ' <button class="btn btn-sm btn-primary" title="保存" disabled  onclick="'
                                    + "javascript:sw.page.modules['sysmanage/enterprise'].saveValidateTimes('" + row.ENT_ID + "')" + '"><i class="fa fa-check"></i> </button> ';
                            } else {
                                isPay = '<button class="btn btn-sm" title="' + title + '" onclick="'
                                    + "javascript:sw.page.modules['sysmanage/enterprise'].isEnablement('" + row.ENT_ID + "','" + row.ISPAY + "','" + pay + "');" + '">' +
                                    '<i class="' + Class + '" style="color:' + Color + '"></i></button> ';

                                code = ' <button class="btn btn-sm btn-primary" title="保存" onclick="'
                                    + "javascript:sw.page.modules['sysmanage/enterprise'].saveValidateTimes('" + row.ENT_ID + "')" + '"><i class="fa fa-check"></i> </button> ';
                            }
                            return isPay + vaidateTimes + code;
                        }
                    },
                    {
                        label: "操作",
                        render: function (data, type, row) {
                            var validate = "isValidate";
                            var code =
                                '<button class="btn btn-sm btn-primary" title="信息修改" onclick="'
                                + "javascript:sw.loadWorkspace('sysmanage/enterpriseEdit?eId=" + row.ENT_ID
                                + "');" + '"><i class="fa fa-edit"></i> </button> ';
                            if (row.ENT_STATUS == "1") {
                                code += '<button class="btn btn-sm btn-danger" title="冻结" onclick="'
                                    + "javascript:sw.page.modules['sysmanage/enterprise'].changeStatus('" + row.ENT_ID
                                    + "', '" + row.ENTERPRISE_NAME + "', '" + row.ENT_STATUS + "')"
                                    + '"><i class="fa fa-ban"></i> </button> ';
                            } else {
                                code += '<button class="btn btn-sm btn-success" title="激活" onclick="'
                                    + "javascript:sw.page.modules['sysmanage/enterprise'].changeStatus('" + row.ENT_ID
                                    + "', '" + row.ENTERPRISE_NAME + "', '" + row.ENT_STATUS + "')"
                                    + '"><i class="fa fa-check-circle-o"></i> </button> ';
                            }
                            var icon, alt;
                            if (row.ISVALIDATE === "Y") {
                                alt = "已启用设为校验按钮";
                                icon = "fa  fa-unlock-alt";
                            } else {
                                alt = "未启用设为校验按钮";
                                icon = "fa fa-lock";
                            }
                            var isValidate = '<button class="btn btn-sm btn-info" title="' + alt + '"  onclick="'
                                + "javascript:sw.page.modules['sysmanage/enterprise'].isEnablement('" + row.ENT_ID + "','" + row.ISVALIDATE + "','" + validate + "');" + '"><i class="' + icon + '" aria-hidden="true"></i></button> ';

                            return isValidate + code;
                        }
                    }
                ]
            });
        },
        changeStatus: function (eId, entName, status) {
            var statusStr = status == "1" ? "已冻结" : "已激活";
            sw.confirm("确定变更企业 \"" + entName + "\"状态为：" + statusStr, "确认", function () {
                sw.ajax("api/auth/enterprise/" + eId, "GET", {}, function (rsp) {
                    sw.alert(rsp.data.msg, "提示", null, "modal-info");
                    sw.page.modules['sysmanage/enterprise'].query();
                });
            });
        },
        //设置按钮的值
        isEnablement: function (enId, value, type) {
            var status;
            if (value === "null" || value === "N") {
                status = "Y";
            } else {
                status = "N";
            }
            sw.ajax("enterprise/isEnablement/" + enId, "PUT", {value: status, type: type}, function (rsp) {
                sw.page.modules['sysmanage/enterprise'].query();
            });
        },
        //保存总校验次数
        saveValidateTimes: function (enId) {
            var times = $("#validateTimes_" + enId).val();
            sw.ajax("enterprise/validateTimes/" + enId, "PUT", {value: times}, function (rsp) {
                if (rsp.status === 200) {
                    sw.alert(rsp.data, "提示", "", "modal-info");
                    sw.page.modules['sysmanage/enterprise'].query();
                }
            });
        },
        //设置校验次数输入框的状态
        validateTimes: function (ent_id, isPayStatus, entStatus, value) {
            var code;
            var id = "validateTimes_" + ent_id;
            //身份证校验开关关闭、企业冻结
            if (isPayStatus === "N" || entStatus === "0") {
                code = '<input type="text"  id="' + id + '" value="' + value + '" disabled>';
            }
            else {
                code = '<input type="text" value="' + value + '" id="' + id + '">';
            }
            return code;
        },

        init: function () {
            $("[ws-search]").unbind("click").click(this.query).click();
        }
    };


// 用户 新增用户，修改用户
sw.page.modules["sysmanage/enterpriseEdit"] = sw.page.modules["sysmanage/enterpriseEdit"] || {
        back: function () {
            sw.showPageQuery();
        },
        loadEntInfo: function (eId) {
            sw.ajax("enterprise/load/" + eId, "", "GET", function (rsp) {
                var data = rsp.data;
                console.log(data);
                if (data !== null) {
                    $("input[name='ent_id']").val(data.ent_id);
                    $("input[name='enterprise_name']").val(data.enterprise_name);
                    $("input[name='business_license']").val(data.business_license);
                    $("input[name='organization_code']").val(data.organization_code);
                    $("input[name='tax_registration_code']").val(data.tax_registration_code);
                    $("select[name='business_category']").val(data.business_category);
                    $("select[name='enterprise_nature']").val(data.enterprise_nature);
                    $("select[name='competent_customs']").val(data.competent_customs);
                    $("input[name='corporate_customs_code']").val(data.corporate_customs_code);
                    $("select[name='enterprise_classification']").val(data.enterprise_classification);
                    $("input[name='corporate_presence']").val(data.corporate_presence);
                    $("input[name='certificate_type']").val(data.certificate_type);
                    $("input[name='certificate_no']").val(data.certificate_no);

                    $("input[name='business_license']").attr("disabled", "disabled");
                    $("input[name='organization_code']").attr("disabled", "disabled");
                    $("input[name='tax_registration_code']").attr("disabled", "disabled");
                    $("input[name='corporate_customs_code']").attr("disabled", "disabled");
                }
            });
        },
        validateEntForm: function () {
            var enterprise_name = $("input[name='enterprise_name']").val();
            var business_license = $("input[name='business_license']").val();
            var organization_code = $("input[name='organization_code']").val();
            var tax_registration_code = $("input[name='tax_registration_code']").val();
            var competent_customs = $("select[name='competent_customs']").val();
            var corporate_customs_code = $("input[name='corporate_customs_code']").val();
            var enterprise_nature = $("select[name='enterprise_nature']").val();

            if (isEmpty(enterprise_name) || enterprise_name.length < 3) {
                hasErrorEnterprise("input[name='enterprise_name'", "企业名称不能为空或小于3个字符");
                return false;
            }
            if (isEmpty(business_license) || business_license.length < 3) {
                hasErrorEnterprise("input[name='business_license'", "工商营业执照号不能为空或小于3个字符");
                return false;
            }
            if (isEmpty(organization_code) || organization_code.length < 3) {
                hasErrorEnterprise("input[name='organization_code'", "组织机构代码不能为空或小于3个字符");
                return false;
            }
            if (isEmpty(tax_registration_code) || tax_registration_code.length < 3) {
                hasErrorEnterprise("input[name='tax_registration_code'", "税务登记代码不能为空或小于3个字符");
                return false;
            }
            if (isEmpty(enterprise_nature)) {
                hasErrorEnterprise("select[name='enterprise_nature'", "请选择企业性质");
                return false;
            }
            if (isEmpty(competent_customs)) {
                hasErrorEnterprise("select[name='competent_customs'", "请选择主管海关");
                return false;
            }
            if (isEmpty(corporate_customs_code) || corporate_customs_code.length != 10) {
                hasErrorEnterprise("input[name='corporate_customs_code'", "请填写海关10位企业代码")
                return false;
            }
            return true;
        },
        entCreate: function () {
            var entData = sw.serialize("#sw-entInfo");
            if (!sw.page.modules["sysmanage/enterpriseEdit"].validateEntForm()) {
                return;
            }
            sw.ajax("api/auth/enterprise", "POST", entData, function (rsp) {
                if (rsp.data.result == "true") {
                    sw.alert(rsp.data.msg, "提示", null, "modal-info");
                    sw.pageModule("sysmanage/enterpriseEdit").back();
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
            var params = sw.getPageParams("sysmanage/enterpriseEdit");
            var entData = sw.serialize("#sw-entInfo");
            if (!sw.page.modules["sysmanage/enterpriseEdit"].validateEntForm()) {
                return;
            }
            sw.ajax("api/auth/enterprise/" + params.uId, "PUT", entData, function (rsp) {
                if (rsp.data.result == "true") {
                    sw.alert(rsp.data.msg, "提示", null, "modal-info");
                    sw.pageModule("sysmanage/enterpriseEdit").back();
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
            $("input[name='ent_id']").val("");
            $("input[name='enterprise_name']").val("");
            $("input[name='business_license']").val("");
            $("input[name='organization_code']").val("");
            $("input[name='tax_registration_code']").val("");
            $("select[name='business_category']").val("");
            $("select[name='enterprise_nature']").val("");
            $("select[name='competent_customs']").val("");
            $("input[name='corporate_customs_code']").val("");
            $("select[name='enterprise_classification']").val("");
            $("input[name='corporate_presence']").val("");
            $("input[name='certificate_type']").val("");
            $("input[name='certificate_no']").val("");
        },
        loadSelectCode: function () {
            sw.selectOptionByType("enterprise_classification", "AGENT_CODE");
            sw.selectOptionByType("business_category", "AGENT_TYPE");
            sw.selectOptionByType("competent_customs", "CUSTOMS_CODE");
            sw.selectOptionByType("enterprise_nature", "AGENT_NATURE");

        },
        init: function () {
            var params = sw.getPageParams("sysmanage/enterpriseEdit");
            this.loadSelectCode();
            if (!params) {
                $("#ws-work-title").text("新增企业");
                $("#ws-page-apply").click(this.entCreate);

            } else {
                $("#ws-work-title").text("编辑企业");
                $("#ws-page-apply").unbind("click").click(this.entUpdate);
                // 等待0.5秒，以便下拉菜单加载完毕
                setTimeout(function () {
                    sw.page.modules["sysmanage/enterpriseEdit"].loadEntInfo(params.eId)
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