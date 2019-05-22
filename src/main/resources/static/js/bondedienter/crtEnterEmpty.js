//入区核放单
sw.page.modules["bondedienter/crtEnterEmpty"] = sw.page.modules["bondedienter/crtEnterEmpty"] || {
    query: function () {
        // 获取查询表单参数
        var vehicle_no = $("[name='vehicle_no']").val();

        // 拼接URL及参数
        var url = sw.serializeObjectToURL("api/crtEnterEmpty/queryEnterEmpty", {
            vehicle_no: vehicle_no//车牌号
        });

        // 数据表
        sw.datatable("#query-crtEnterEmpty-table", {
            ordering: false,
            bSort: false, //排序功能
            serverSide: true,////服务器端获取数据
            pagingType: 'simple_numbers',
            ajax: function (data, callback, setting) {
                $.ajax({
                    type: 'GET',
                    url: sw.resolve(url),
                    data: data,
                    cache: false,
                    dataType: "json",
                    beforeSend: function () {
                        $("tbody").html('<tr class="odd"><td valign="top" colspan="13" class="dataTables_empty">载入中...</td></tr>');
                    },
                    success: function (res) {
                        var returnData = {};
                        returnData.data = res.data.data;
                        returnData.recordsFiltered = res.data.recordsFiltered;
                        returnData.draw = res.data.draw;
                        returnData.recordsTotal = res.data.recordsTotal;
                        returnData.start = data.start;
                        returnData.length = data.length;
                        callback(returnData);
                    },
                    error: function (xhr, status, error) {
                        sw.showErrorMessage(xhr, status, error);
                    }
                });
            },
            lengthMenu: [[50, 100, 1000, -1], [50, 100, 1000, "所有"]],
            searching: false,//开启本地搜索
            columns: [
                {
                    label: '<input type="checkbox" name="cb-check-all"/>',
                    orderable: false,
                    data: null,
                    render: function (data, type, row) {
                        if (row.status == "BDDS73" || row.status == "BDDS7") {
                            return '<input type="checkbox" class="submitKey" value="' +
                                row.etps_preent_no + '" />';
                        }
                        else {
                            return "";
                        }
                    }
                },
                {
                    label: "企业内部编号", render: function (data, type, row) {
                        return '<a href="javascript:void(0)"  onclick="' + "javascript:sw.pageModule('bondedienter/crtEnterEmpty').seeEnterPassportDetail('" + row.etps_preent_no + "')" + '">' + row.etps_preent_no + '</a>'
                    }
                },
                {
                    data: "vehicle_no", label: "车牌号"
                },
                {
                    label: "当前状态", render: function (data, type, row) {
                    var value = "";
                    var textColor = "";
                    switch (row.status) {
                        case "BDDS7":
                            value = "入区空车核放单待申报";
                            textColor = "text-yellow";
                            break;
                        case "BDDS70":
                            value = "入区空车核放单申报中";
                            textColor = "text-green";
                            break;
                        case "BDDS71":
                            value = "入区空车核放单正在发往海关";
                            textColor = "text-green";
                            break;
                        case "BDDS72":
                            value = "入区空车核放单申报成功";
                            textColor = "text-green";
                            break;
                        case "BDDS73":
                            value = "入区空车核放单暂存";
                            textColor = "text-red";
                            break;
                    }
                    var result = "<span class=" + textColor + ">" + value + "</span>";
                    return result
                }
                },
                {
                    label: "创建时间", render: function (data, type, row) {
                    if (!isEmpty(row.crt_time)) {
                        return moment(row.crt_time).format("YYYY-MM-DD HH:mm:ss");
                    }
                    return "";
                }
                },
               /* {
                    label: "操作", render: function (data, type, row) {
                        return item =
                            '<button class="btn btn-sm btn-danger" title="删除" ' +
                            'onclick="' + "javascript:sw.page.modules['bondedienter/crtEnterEmpty'].emptyDelete('" + row.id + "','"+row.vehicle_no+"')" + '">' +
                            '<i class="fa fa-remove">删除</i> </button> ';
                    }, width: "300px"
                }*/
            ]
        });
    },
    emptyDelete: function (id,vehicle_no) {
        sw.confirm("确认删除空车核放单号车牌号:【" + vehicle_no + "】", "确认", function () {
            sw.ajax("api/crtEnterEmpty/deleteEnterEmpty/" + id, "DELETE", {}, function () {
                sw.page.modules["bondedienter/crtEnterEmpty"].query();
            })
        })
    },
    //删除核放单
    deleteEmpty: function () {
        var submitKeys = "";
        $(".submitKey:checked").each(function () {
            submitKeys += "," + $(this).val();
        });
        if (submitKeys.length > 0) {
            submitKeys = submitKeys.substring(1);
        } else {
            sw.alert("请先勾选要删除的入区核放单信息！");
            return;
        }
        var postData = {
            submitKeys: submitKeys
        };
        sw.confirm("确定删除该入区核放单", "确认", function () {
            sw.ajax("api/enterManifest/deleteEnterManifest", "POST", postData, function (rsp) {
                sw.pageModule("bondedienter/crtEnterEmpty").query();
            });
        });
    },

    // 提交海关
    submitCustomByCode: function () {
        var submitKeys = "";
        $(".submitKey:checked").each(function () {
            submitKeys += "," + $(this).val();
        });
        if (submitKeys.length > 0) {
            submitKeys = submitKeys.substring(1);
        } else {
            sw.alert("请先勾选要提交海关的入区核放单信息！");
            return;
        }

        sw.confirm("请确认数据无误并提交海关", "确认", function () {
            sw.blockPage();
            var postData = {
                submitKeys: submitKeys
            };
            $("#submitCustom").prop("disabled", true);
            sw.ajax("api/crtEnterEmpty/submitEmptyCustom", "POST", postData, function (rsp) {
                if (rsp.data.result == "true") {
                    sw.alert("提交海关成功", "提示", function () {
                    }, "modal-success");
                    $("#submitCustom").prop("disabled", false);
                    sw.page.modules["bondedienter/crtEnterEmpty"].query();
                } else {
                    sw.alert(rsp.data.msg);
                }
                $.unblockUI();
            });
        });
    },

    createEnterEmpty: function () {
        var url = "bondedienter/seeCreateEnterEmpty?type=RQKC&isEdit=true&etps_preent_no=0";
        sw.modelPopup(url, "创建入区空车核放单", false, 900, 400);
    },
    seeEnterPassportDetail: function (etps_preent_no) {
        var url = "bondedienter/seeCreateEnterEmpty?type=RQKC&isEdit=true&etps_preent_no=" + etps_preent_no;
        sw.modelPopup(url, "查看入区空车核放单详情", false, 900, 400);
    },
    init: function () {
        $(".input-daterange").datepicker({
            language: "zh-CN",
            todayHighlight: true,
            format: "yyyy-mm-dd",
            autoclose: true
        });
        $("[ws-search]").unbind("click").click(this.query).click();
        //点击创建核放单
        $("#crtEnterEmpty").unbind("click").click(this.createEnterEmpty);
        //提交海关
        $("#submitCustom").unbind("click").click(this.submitCustomByCode);
        //删除入区空车核放单
        $("#deleteEmpty").unbind("click").click(this.deleteEmpty);

        $table = $("#query-crtEnterEmpty-table");
        $table.on("change", ":checkbox", function () {
            if ($(this).is("[name='cb-check-all']")) {
                //全选
                $(":checkbox", $table).prop("checked", $(this).prop("checked"));
            } else {
                //复选
                var checkbox = $("tbody :checkbox", $table);
                $(":checkbox[name='cb-check-all']", $table).prop('checked', checkbox.length == checkbox.filter(':checked').length);
            }
        });
    }

};

