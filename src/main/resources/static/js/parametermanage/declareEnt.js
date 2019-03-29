/**
 * 运输工具管理
 *
 */
sw.page.modules["parametermanage/declareEnt"] = sw.page.modules["parametermanage/declareEnt"] || {

    query: function () {
        var data = sw.serialize(".ws-query form");
        var url = $("[ws-search]").attr("ws-search");
        if (data.length > 0)
            url += "?" + data;
        // 数据表
        sw.datatable("#query-declareEnt-table", {
            ajax: sw.resolve("api", url),
            lengthMenu: [[10, 50, 100, -1], [10, 50, 100, "所有"]],
            searching: false,
            columns: [
                {
                    label: "进出口", render: function (data, type, row) {
                    var i_e_flag = row.i_e_flag;
                    if (i_e_flag == "I") {
                        return "进口";
                    } else if (i_e_flag == "E") {
                        return "出口";
                    }
                }
                },
                {
                  label: "发件人名称", render:function (data,type,row) {
                    return (row.send_name).replace(/\ /g,"&nbsp;");
                }
                },
                {
                    label: "英文发件人名称", render: function (data, type, row) {
                    return (row.send_name_en).replace(/\ /g,"&nbsp;");
                }
                },
                {
                    label: "操作",
                    render: function (data, type, row) {
                        return '<button class="btn btn-sm btn-danger" title="用户删除" onclick="' + "javascript:sw.page.modules['parametermanage/declareEnt'].delete('" + row.sn_id + "', '" + row.send_name + "')" + '"><i class="fa fa-remove"></i> </button>';
                    }
                }
            ]
        });
    },

    delete: function (sn_id, send_name) {
        sw.confirm("确定删除该发件人信息 " + send_name, "确认", function () {
            sw.ajax("api/declareEnt/" + sn_id, "DELETE", {sn_id: sn_id}, function (rsp) {
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
        var i_e_flag = $("#i_e_flag").val();
        var entry_type = $("#entry_type").val();
        var send_name = $("#send_name").val();
        var send_name_en = $("#send_name_en").val();

        if (typeof(i_e_flag) == "undefined" || null == i_e_flag || "" == i_e_flag) {
            sw.alert("进出口必选", "提示", null, "modal-info");
            return;
        }
        if (typeof(entry_type) == "undefined" || null == entry_type || "" == entry_type) {
            sw.alert("报关类别必填", "提示", null, "modal-info");
            return;
        }
        if (typeof(send_name) == "undefined" || null == send_name || "" == send_name) {
            sw.alert("发件人名称必填", "提示", null, "modal-info");
            return;
        }
        if (typeof(send_name_en) == "undefined" || null == send_name_en || "" == send_name_en) {
            sw.alert("英文发件人名称必填", "提示", null, "modal-info");
            return;
        }

        sw.ajax("api/declareEntAdd", "POST", formData, function (rep) {
            sw.pageModule("parametermanage/declareEntAdd").back();
            sw.page.modules['parametermanage/declareEnt'].query();
        }, function (status, err, xhr) {
            sw.alert(xhr.data, "提示", null, "modal-info");
            return;
        });
    },

    init: function () {
        var params = sw.getPageParams("auth/menuEdit");
        if (!params) {
            $("#ws-work-title").text("新增发件人信息");
            $("#ws-page-apply").click(this.createdeclareEnt);
        } else {
            $("#ws-work-title").text("编辑发件人信息");
        }
        $("#ws-page-back").click(this.back);
    }
};

