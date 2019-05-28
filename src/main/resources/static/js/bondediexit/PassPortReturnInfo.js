function isNotEmpty(obj) {
    if (typeof(obj) == "undefined" || null == obj || "" == obj) {
        return false;
    }
    return true;
}

function hasError(errorMsg) {
    $("#errorMsg").html(errorMsg).removeClass("hidden");
}

function clearError() {
    $("#errorMsg").html("").addClass("hidden");
}

function selecterInitB(selectId, value, data) {
    $("#" + selectId).select2({
        data: $.map(data, function (val, key) {
            var obj = {
                id: key,
                text: val + "[" + key + "]"
            };
            return obj;
        }),
        placeholder: value,
        dropdownParent: $("#dialog-popup")
    }).val(value).trigger('change');
}


sw.page.modules["bondediexit/PassPortReturnInfo"] = sw.page.modules["bondediexit/PassPortReturnInfo"] || {
    detailParam: {
        url: "",
        callBackUrl: "",
        isShowError: true,
        isEdit: "true",
        disableField: []
    },
    // 禁用字段
    disabledFieldInput: function () {
        var disableField = sw.page.modules["bondediexit/PassPortReturnInfo"].detailParam.disableField;
        for (i = 0; i < disableField.length; i++) {
            $(".detailPage input[id^=" + disableField[i] + "],select[id^=" + disableField[i] + "]").attr("disabled", "disabled");
        }
    },
    // 装载表头信息
    fillPassPortHeadReturnInfo: function (passPortHead) {
        $("#sas_passport_preent_no").val(passPortHead.sas_passport_preent_no);
        $("#passport_no").val(passPortHead.passport_no);
        $("#etps_preent_no").val(passPortHead.etps_preent_no);
        $("#return_status").val(passPortHead.return_status);
        $("#return_date").val(moment(passPortHead.return_date).format("YYYY-MM-DD HH:mm:ss"));
        $("#return_info").val(passPortHead.return_info);
    },
    // 查询订单详情
    query: function () {
        //从路径上找参数
        var param = sw.getPageParams("bondediexit/PassPortReturnInfo");
        var id = param.id;
        var etps_preent_no = param.etps_preent_no;
        var data = {
            id: id,
            etps_preent_no: etps_preent_no
        };
        $.ajax({
            method: "GET",
            url: "api/bondediexit/seePassPortRec",
            data: data,
            success: function (data, status, xhr) {
                if (xhr.status == 200) {
                    var entryModule = sw.page.modules["bondediexit/PassPortReturnInfo"];

                    var passPortHead = data.data;

                    if (isNotEmpty(passPortHead)) {
                        entryModule.fillPassPortHeadReturnInfo(passPortHead);
                    }
                    entryModule.disabledFieldInput();
                }
            }
        });
    },

    init: function () {
        //从路径上获取参数
        var param = sw.getPageParams("bondediexit/PassPortReturnInfo");
        var id = param.id;
        var etps_preent_no = param.etps_preent_no;

        $(".input-daterange").datepicker({
            language: "zh-CN",
            todayHighlight: true,
            format: "yyyy-mm-dd",
            autoclose: true
        });

        // 查询详情
        this.query();
    }
};





