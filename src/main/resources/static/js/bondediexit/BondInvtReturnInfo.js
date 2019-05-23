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

sw.page.modules["bondediexit/BondInvtReturnInfo"] = sw.page.modules["bondediexit/BondInvtReturnInfo"] || {
    detailParam: {
        url: "",
        callBackUrl: "",
        isShowError: true,
        isEdit: "true",
        disableField: []
    },
    // 禁用字段
    disabledFieldInput: function () {
        var disableField = sw.page.modules["bondediexit/BondInvtReturnInfo"].detailParam.disableField;
        for (i = 0; i < disableField.length; i++) {
            $(".detailPage input[id^=" + disableField[i] + "],select[id^=" + disableField[i] + "]").attr("disabled", "disabled");
        }
    },
    // 装载表头信息
    fillBondInvtReturnInfo: function (bondInvtBsc) {
        $("#invt_preent_no").val(bondInvtBsc.invt_preent_no);
        $("#bond_invt_no").val(bondInvtBsc.bond_invt_no);
        $("#etps_inner_invt_no").val(bondInvtBsc.etps_inner_invt_no);
        $("#return_status").val(bondInvtBsc.return_status);
        $("#return_time").val(moment(bondInvtBsc.return_time).format("YYYY-MM-DD HH:mm:ss"));
        $("#return_info").val(bondInvtBsc.return_info);
    },
    // 查询订单详情
    query: function () {
        //从路径上找参数
        var param = sw.getPageParams("bondediexit/BondInvtReturnInfo");
        var id = param.id;
        var etps_inner_invt_no = param.etps_inner_invt_no;
        var data = {
            id: id,
            etps_inner_invt_no: etps_inner_invt_no
        };
        $.ajax({
            method: "GET",
            url: "api/bondediexit/seeBondInvtRec",
            data: data,
            success: function (data, status, xhr) {
                if (xhr.status == 200) {
                    var entryModule = sw.page.modules["bondediexit/BondInvtReturnInfo"];

                    var bondInvtBsc = data.data;

                    if (isNotEmpty(bondInvtBsc)) {
                        entryModule.fillBondInvtReturnInfo(bondInvtBsc);
                    }
                    entryModule.disabledFieldInput();
                }
            }
        });
    },

    init: function () {
        //从路径上获取参数
        var param = sw.getPageParams("bondediexit/BondInvtReturnInfo");
        var id = param.id;
        var etps_inner_invt_no = param.etps_inner_invt_no;

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





