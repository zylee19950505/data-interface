/**
 * 身份证校验记录表
 * Created by zwj on 2017/11/6.
 */
sw.page.modules["ordermanage/idcardVerifyRecord"] = sw.page.modules["ordermanage/idcardVerifyRecord"] || {
        init: function () {
            this.query();
            $("[ws-download]").prop('disabled', true);
            $("[ws-download]").unbind("click").click(this.download);
        },

        query: function () {
            var url = sw.serializeObjectToURL($("[ws-search]").attr("ws-search"), {
                billNo: $("[name='billNo']").val(),
                assBillNo: $("[name='assBillNo']").val(),
                idCardStatus: $("[name='idCardStatus']").val(),
                name:$("[name='reName']").val(),
                idcard:$("[name='reID']").val(),
                type: "record"
            });

            // 查询累计校验次数
            $("#verify_count").html("0");
            sw.ajax("api/verify/count/", "GET", {}, function (rsp) {
                if (rsp.status == 200) {
                    var count =rsp.data.count;
                    var surplus = rsp.data.surplus;
                    $("#verify_surplus").html(surplus);
                    $("#verify_count").html(count);
                }
            });

            sw.datatable("#query-idCardVerify-table", {
                ajax: sw.resolve("api", url),
                lengthMenu: [[200, 500, 1000], [200, 500, 1000]],
                searching: false,
                columns: [
                    {data: "billNo", label: "主运单号"},
                    {data: "assBillNo", label: "分运单号"},
                    {
                        label: "证件号", render: function (data, type, row) {
                        return JSON.parse(row.entryMessage).sendId;
                    }
                    },
                    {
                        label: "收件人", render: function (data, type, row) {
                        return JSON.parse(row.entryMessage).receiveName;
                    }
                    },
                    {
                        label: "检验状态", render: function (data, type, row) {
                        var message;
                        if (row.status === "Y") {
                            message = "通过"
                        } else if (row.status === "N") {
                            message = "未通过"
                        } else {
                            message = "未校验"
                        }
                        return message;
                    }
                    },
                    {data: "result", label: "检验信息"},
                    {
                        label: "操作时间", render: function (data, type, row) {
                        return moment(row.createTime).format("YYYYMMDD");
                    }
                    }
                ]
            });
        },

        setButton: function () {
            var status = $("[name='idCardStatus']").val();
            if (status === "Y") {
                $("[ws-download]").prop('disabled', false);
            } else {
                $("[ws-download]").prop('disabled', true);
            }
        },

        //查询身份证校验记录信息
        LoadTable: function () {
            this.query();
        },

        //身份证校验下载
        download: function () {
            sw.ajax("api/verify/verifyRecord", "GET", {
                billNo: $("[name='billNo']").val(),
                assBillNo: $("[name='assBillNo']").val(),
                idCardStatus: $("[name='idCardStatus']").val(),
                name:$("[name='reName']").val(),
                idcard:$("[name='reID']").val(),
                type: "load"
            }, function (rsp) {
                if (rsp.status == 200) {
                    var fileName = rsp.data;
                    window.location.href = "/api/downloadFile?fileName=" + fileName;
                }
            });
        }
    };