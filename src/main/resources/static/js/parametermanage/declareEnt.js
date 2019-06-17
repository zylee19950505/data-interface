sw.page.modules["parametermanage/declareEnt"] = sw.page.modules["parametermanage/declareEnt"] || {
    query: function () {
        var data = sw.serialize(".ws-query form");
        var url = $("[ws-search]").attr("ws-search");
        if (data.length > 0)
            url += "?" + data;
        // 数据表
        sw.datatable("#query-dcletps-table", {
            ajax: sw.resolve("api", url),
            lengthMenu: [[10, 50, 100, -1], [10, 50, 100, "所有"]],
            searching: false,
            columns: [
                {
                    label: "优先取值项", render: function (data, type, row) {
                    var defaultValue = "";
                    if (row.select_priority == "1") {
                        defaultValue = "默认此项";
                    }
                    return defaultValue;
                }
                },
                {
                    label: "企业名称", render: function (data, type, row) {
                    return (row.dcl_etps_name).replace(/\ /g, "&nbsp;");
                }
                },
                {
                    label: "海关编码", render: function (data, type, row) {
                    return (row.dcl_etps_customs_code).replace(/\ /g, "&nbsp;");
                }
                },
                {
                    label: "社会信用代码", render: function (data, type, row) {
                    return (row.dcl_etps_credit_code).replace(/\ /g, "&nbsp;");
                }
                },
                {
                    label: "IC卡号", render: function (data, type, row) {
                    return (row.dcl_etps_ic_no).replace(/\ /g, "&nbsp;");
                }
                },
                {
                    label: "主管海关", render: function (data, type, row) {
                    return (row.dcl_etps_port).replace(/\ /g, "&nbsp;");
                }
                },
                {
                    label: "操作",
                    render: function (data, type, row) {
                        return '<button class="btn btn-sm btn-info" title="置为默认值" onclick="' + "javascript:sw.page.modules['parametermanage/declareEnt'].setDefault('" + row.id + "', '" + row.dcl_etps_customs_code + "', '" + row.ent_id + "')" + '"><i class="fa fa-arrow-up"></i> </button> ' +
                            '<button class="btn btn-sm btn-danger" title="用户删除" onclick="' + "javascript:sw.page.modules['parametermanage/declareEnt'].delete('" + row.id + "', '" + row.dcl_etps_customs_code + "')" + '"><i class="fa fa-remove"></i> </button>';
                    }
                }
            ]
        });
    },

    setDefault: function (id, dcl_etps_customs_code, ent_id) {
        sw.confirm("确定删除该企业信息 " + dcl_etps_customs_code + "为默认值", "确认", function () {
            var params = {
                id: id,
                ent_id: ent_id
            };
            sw.ajax("api/dcletpsSetDefault/{id}", "POST", params, function (rsp) {
                sw.page.modules['parametermanage/declareEnt'].query();
            });
        });
    },

    delete: function (id, dcl_etps_customs_code) {
        sw.confirm("确定删除该企业信息 " + dcl_etps_customs_code, "确认", function () {
            sw.ajax("api/dcletpsDelete/" + id, "DELETE", {id: id}, function (rsp) {
                sw.page.modules['parametermanage/declareEnt'].query();
            });
        });
    },

    back: function () {
        sw.showPageQuery();
    },

    init: function () {
        $("[ws-search]").unbind("click").click(this.query);
        $(".btn[ws-search]").click();
    }
};


/**
 * 新增、修改运输工具
 */
sw.page.modules["parametermanage/declareEntAdd"] = sw.page.modules["parametermanage/declareEntAdd"] || {
    back: function () {
        sw.page.modules["parametermanage/declareEnt"].query();
        sw.showPageQuery();
    },

    //新增运输工具信息
    createdeclareEnt: function () {
        var formData = sw.serialize("#ws-host");
        var dcl_etps_name = $("#dcl_etps_name").val();
        var dcl_etps_customs_code = $("#dcl_etps_customs_code").val();
        var dcl_etps_credit_code = $("#dcl_etps_credit_code").val();
        var dcl_etps_ic_no = $("#dcl_etps_ic_no").val();
        var dcl_etps_port = $("#dcl_etps_port").val();

        if (typeof(dcl_etps_name) == "undefined" || null == dcl_etps_name || "" == dcl_etps_name) {
            sw.alert("企业名称必填", "提示", null, "modal-info");
            return;
        }
        if (typeof(dcl_etps_customs_code) == "undefined" || null == dcl_etps_customs_code || "" == dcl_etps_customs_code) {
            sw.alert("海关编码必填", "提示", null, "modal-info");
            return;
        }
        if (typeof(dcl_etps_credit_code) == "undefined" || null == dcl_etps_credit_code || "" == dcl_etps_credit_code) {
            sw.alert("社会信用代码必填", "提示", null, "modal-info");
            return;
        }
        if (typeof(dcl_etps_ic_no) == "undefined" || null == dcl_etps_ic_no || "" == dcl_etps_ic_no) {
            sw.alert("IC卡号必填", "提示", null, "modal-info");
            return;
        }
        if (typeof(dcl_etps_port) == "undefined" || null == dcl_etps_port || "" == dcl_etps_port) {
            sw.alert("主管海关必选", "提示", null, "modal-info");
            return;
        }

        sw.ajax("api/dcletpsAdd", "POST", formData, function (rep) {
            sw.pageModule("parametermanage/declareEntAdd").back();
            sw.page.modules['parametermanage/declareEnt'].query();
        }, function (status, err, xhr) {
            sw.alert(xhr.data, "提示", null, "modal-info");
            return;
        });
    },

    loadSelectCode: function () {
        sw.selectOptionByType("dcl_etps_port", "CUSTOMS_CODE");
    },

    init: function () {
        var params = sw.getPageParams("auth/menuEdit");
        this.loadSelectCode();
        if (!params) {
            $("#ws-work-title").text("新增申报企业信息");
            $("#ws-page-apply").click(this.createdeclareEnt);
        } else {
            $("#ws-work-title").text("编辑申报企业信息");
        }
        $("#ws-page-back").click(this.back);
    }
};

