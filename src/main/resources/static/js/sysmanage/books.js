// 企业管理主页 表格
sw.page.modules["sysmanage/books"] = sw.page.modules["sysmanage/books"] || {
    query: function () {
        var booksInfo = $("[name='booksInfo']").val();

        // 拼接URL及参数
        var url = sw.serializeObjectToURL("/booksManage/allBooksInfo", {
            booksInfo: booksInfo
        });

        sw.datatable("#query-books-table", {
            ajax: url,
            searching: false,
            columns: [
                {data: "bws_no", label: "仓库账册号"},
                {data: "master_cuscd", label: "主管海关"},
                {data: "house_no", label: "仓库编号"},
                {data: "house_nm", label: "仓库名称"},
                {data: "dcl_etps_nm", label: "申报企业名称"},
                {
                    label: "创建时间",
                    render: function (data, type, row) {
                        return moment(row.crt_time).format("YYYY-MM-DD HH:mm:ss");
                    }
                },
                {
                    label: "修改时间",
                    render: function (data, type, row) {
                        return moment(row.upd_time).format("YYYY-MM-DD HH:mm:ss");
                    }
                },
                {
                    label: "操作",
                    render: function (data, type, row) {
                        var code =
                            '<button class="btn btn-sm btn-info" title="信息修改" onclick="'
                            + "javascript:sw.loadWorkspace('sysmanage/booksEdit?id=" + row.id
                            + "');" + '"><i class="fa fa-edit"></i> </button> ';
                        return code;
                    }
                }
            ]
        });
    },

    init: function () {
        $("[ws-search]").unbind("click").click(this.query).click();
    }
};


// 用户 新增用户，修改用户
sw.page.modules["sysmanage/booksEdit"] = sw.page.modules["sysmanage/booksEdit"] || {
    back: function () {
        sw.showPageQuery();
    },
    loadBooksInfo: function (id) {
        sw.ajax("booksManage/loadBooks/" + id, "", "GET", function (rsp) {
            var data = rsp.data;
            console.log(data);
            if (data !== null) {
                $("input[name='id']").val(data.id);

                $("input[name='etps_preent_no']").val(data.etps_preent_no);
                $("input[name='bws_no']").val(data.bws_no);
                $("select[name='dcl_typecd']").val(data.dcl_typecd);
                $("select[name='bwl_typecd']").val(data.bwl_typecd);
                $("select[name='master_cuscd']").val(data.master_cuscd);
                $("input[name='bizop_etpsno']").val(data.bizop_etpsno);
                $("input[name='bizop_etps_nm']").val(data.bizop_etps_nm);
                $("input[name='bizop_etps_sccd']").val(data.bizop_etps_sccd);
                $("input[name='house_no']").val(data.house_no);
                $("input[name='house_nm']").val(data.house_nm);
                $("input[name='dcl_etpsno']").val(data.dcl_etpsno);
                $("input[name='dcl_etps_nm']").val(data.dcl_etps_nm);
                $("input[name='chg_tms_cnt']").val(data.chg_tms_cnt);

                $("input[name='dcl_etps_sccd']").val(data.dcl_etps_sccd);
                $("select[name='dcl_etps_typecd']").val(data.dcl_etps_typecd);
                $("input[name='contact_er']").val(data.contact_er);
                $("input[name='contact_tele']").val(data.contact_tele);
                $("select[name='house_typecd']").val(data.house_typecd);
                $("input[name='house_area']").val(data.house_area);
                $("input[name='house_volume']").val(data.house_volume);
                $("input[name='house_address']").val(data.house_address);
                // $("input[name='finish_valid_date']").val(data.finish_valid_date);
                $("input[name='finish_valid_date']").val(moment(data.finish_valid_date).format("YYYY-MM-DD"));
                $("select[name='append_typecd']").val(data.append_typecd);
                $("select[name='usage_typecd']").val(data.usage_typecd);
                $("input[name='rmk']").val(data.rmk);

            }
        });
    },

    validateBooksInfo: function () {
        var etps_preent_no = $("input[name='etps_preent_no']").val();
        var bws_no = $("input[name='bws_no']").val();
        var dcl_typecd = $("select[name='dcl_typecd']").val();
        var bwl_typecd = $("select[name='bwl_typecd']").val();
        var master_cuscd = $("select[name='master_cuscd']").val();
        var bizop_etpsno = $("input[name='bizop_etpsno']").val();
        var bizop_etps_nm = $("input[name='bizop_etps_nm']").val();
        var bizop_etps_sccd = $("input[name='bizop_etps_sccd']").val();
        var house_no = $("input[name='house_no']").val();
        var house_nm = $("input[name='house_nm']").val();
        var dcl_etpsno = $("input[name='dcl_etpsno']").val();
        var dcl_etps_nm = $("input[name='dcl_etps_nm']").val();

        var dcl_etps_sccd = $("input[name='dcl_etps_sccd']").val();
        var dcl_etps_typecd = $("select[name='dcl_etps_typecd']").val();
        var contact_er = $("input[name='contact_er']").val();
        var contact_tele = $("input[name='contact_tele']").val();
        var house_typecd = $("select[name='house_typecd']").val();
        var house_area = $("input[name='house_area']").val();
        var house_volume = $("input[name='house_volume']").val();
        var house_address = $("input[name='house_address']").val();
        var finish_valid_date = $("input[name='finish_valid_date']").val();
        var append_typecd = $("select[name='append_typecd']").val();
        var usage_typecd = $("select[name='usage_typecd']").val();
        var rmk = $("input[name='rmk']").val();

        if (isEmpty(etps_preent_no)) {
            hasErrorAccount("input[name='etps_preent_no'", "统一编号不能为空");
            return false;
        }
        if (isEmpty(bws_no)) {
            hasErrorAccount("input[name='bws_no'", "仓库账册号不能为空");
            return false;
        }
        if (isEmpty(dcl_typecd)) {
            hasErrorAccount("select[name='dcl_typecd'", "申报类型代码不能为空");
            return false;
        }
        if (isEmpty(bwl_typecd)) {
            hasErrorAccount("select[name='bwl_typecd'", "区域场所类别不能为空");
            return false;
        }
        if (isEmpty(master_cuscd)) {
            hasErrorAccount("select[name='master_cuscd'", "主管海关不能为空");
            return false;
        }
        if (isEmpty(bizop_etpsno)) {
            hasErrorAccount("input[name='bizop_etpsno'", "经营企业编号不能为空");
            return false;
        }
        if (isEmpty(bizop_etps_nm)) {
            hasErrorAccount("input[name='bizop_etps_nm'", "经营企业名称不能为空");
            return false;
        }
        if (isEmpty(bizop_etps_sccd)) {
            hasErrorAccount("input[name='bizop_etps_sccd'", "经营企业社会信用代码不能为空");
            return false;
        }
        if (isEmpty(house_no)) {
            hasErrorAccount("input[name='house_no'", "仓库编号不能为空");
            return false;
        }
        if (isEmpty(house_nm)) {
            hasErrorAccount("input[name='house_nm'", "仓库名称不能为空");
            return false;
        }
        if (isEmpty(dcl_etpsno)) {
            hasErrorAccount("input[name='dcl_etpsno'", "申报企业编号不能为空");
            return false;
        }
        if (isEmpty(dcl_etps_nm)) {
            hasErrorAccount("input[name='dcl_etps_nm'", "申报企业名称不能为空");
            return false;
        }


        if (isEmpty(dcl_etps_sccd)) {
            hasErrorAccount("input[name='dcl_etps_sccd'", "申报企业社会信用代码不能为空");
            return false;
        }
        if (isEmpty(dcl_etps_typecd)) {
            hasErrorAccount("select[name='dcl_etps_typecd'", "申报单位类型代码不能为空");
            return false;
        }
        if (isEmpty(contact_er)) {
            hasErrorAccount("input[name='contact_er'", "联系人不能为空");
            return false;
        }
        if (isEmpty(contact_tele)) {
            hasErrorAccount("input[name='contact_tele'", "联系电话不能为空");
            return false;
        }
        if (isEmpty(house_typecd)) {
            hasErrorAccount("select[name='house_typecd'", "企业类型代码不能为空");
            return false;
        }
        if (isEmpty(house_area)) {
            hasErrorAccount("input[name='house_area'", "仓库面积(平方米)不能为空");
            return false;
        }
        if (isEmpty(house_volume)) {
            hasErrorAccount("input[name='house_volume'", "仓库容积(立方米)不能为空");
            return false;
        }
        if (isEmpty(house_address)) {
            hasErrorAccount("input[name='house_address'", "仓库地址不能为空");
            return false;
        }
        if (isEmpty(finish_valid_date)) {
            hasErrorAccount("input[name='finish_valid_date'", "结束有效日期不能为空");
            return false;
        }
        if (isEmpty(append_typecd)) {
            hasErrorAccount("select[name='append_typecd'", "记账模式代码不能为空");
            return false;
        }
        if (isEmpty(usage_typecd)) {
            hasErrorAccount("select[name='usage_typecd'", "账册用途不能为空");
            return false;
        }

        return true;
    },

    booksCreate: function () {
        var entData = sw.serialize("#booksInfo");
        if (!sw.page.modules["sysmanage/booksEdit"].validateBooksInfo()) {
            return;
        }
        sw.ajax("/booksManage/crtBooksInfo", "POST", entData, function (rsp) {
            if (rsp.data.result == "true") {
                sw.alert(rsp.data.msg, "提示", null, "modal-info");
                sw.pageModule("sysmanage/booksEdit").back();
                sw.page.modules['sysmanage/books'].query();
                sw.page.modules['sysmanage/books'].clearEntInfo();
            } else {
                $("#errorMsg").html(rsp.data.msg).removeClass("hidden");
            }
        }, function (status, err, xhr) {
            sw.alert(xhr.data, "提示", null, "modal-info");
            return;
        });
    },

    booksUpdate: function () {
        var params = sw.getPageParams("sysmanage/booksEdit");
        var entData = sw.serialize("#booksInfo");
        if (!sw.page.modules["sysmanage/booksEdit"].validateBooksInfo()) {
            return;
        }
        sw.ajax("/booksManage/booksUpdate/" + params.id, "PUT", entData, function (rsp) {
            if (rsp.data.result == "true") {
                sw.alert(rsp.data.msg, "提示", null, "modal-info");
                sw.pageModule("sysmanage/booksEdit").back();
                sw.page.modules['sysmanage/books'].query();
                // sw.page.modules['sysmanage/books'].clearBooksInfo();
            } else {
                $("#errorMsg").html(rsp.data.msg).removeClass("hidden");
            }
        }, function (status, err, xhr) {
            sw.alert(xhr.data, "提示", null, "modal-info");
            return;
        });
    },
    clearBooksInfo: function () {
        $("input[name='id']").val("");

        $("input[name='etps_preent_no']").val("");
        $("input[name='bws_no']").val("");
        $("select[name='dcl_typecd']").val("");
        $("select[name='bwl_typecd']").val("");
        $("select[name='master_cuscd']").val("");
        $("input[name='bizop_etpsno']").val("");
        $("input[name='bizop_etps_nm']").val("");
        $("input[name='bizop_etps_sccd']").val("");
        $("input[name='house_no']").val("");
        $("input[name='house_nm']").val("");
        $("input[name='dcl_etpsno']").val("");
        $("input[name='dcl_etps_nm']").val("");
        $("input[name='chg_tms_cnt']").val("");

        $("input[name='dcl_etps_sccd']").val("");
        $("select[name='dcl_etps_typecd']").val("");
        $("input[name='contact_er']").val("");
        $("input[name='contact_tele']").val("");
        $("select[name='house_typecd']").val("");
        $("input[name='house_area']").val("");
        $("input[name='house_volume']").val("");
        $("input[name='house_address']").val("");
        $("input[name='finish_valid_date']").val("");
        $("select[name='append_typecd']").val("");
        $("select[name='usage_typecd']").val("");
        $("input[name='rmk']").val("");
    },
    loadSelectCode: function () {
        // sw.selectOptionByType("ent_classify", "AGENT_CODE");
        // sw.selectOptionByType("ent_type", "AGENT_TYPE");
        sw.selectOptionByType("master_cuscd", "CUSTOMS_CODE");
        // sw.selectOptionByType("ent_nature", "AGENT_NATURE");

    },
    init: function () {
        $(".input-daterange").datepicker({
            language: "zh-CN",
            todayHighlight: true,
            format: "yyyy-mm-dd",
            autoclose: true
        });
        var params = sw.getPageParams("sysmanage/booksEdit");
        this.loadSelectCode();
        if (!params) {
            $("#ws-work-title").text("新增账册");
            $("#ws-page-apply").click(this.booksCreate);

        } else {
            $("#ws-work-title").text("修改账册");
            $("#ws-page-apply").unbind("click").click(this.booksUpdate);
            // 等待0.5秒，以便下拉菜单加载完毕
            setTimeout(function () {
                sw.page.modules["sysmanage/booksEdit"].loadBooksInfo(params.id)
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


function hasErrorAccount(selecter, errorMsg) {
    $(selecter).parent().addClass("has-error");
    $(selecter).parent().find(".help-block").removeClass("hidden").html(errorMsg);
    $(selecter).focus();
};