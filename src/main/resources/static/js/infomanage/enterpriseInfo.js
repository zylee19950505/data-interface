/**
 * Created by zwj on 2017/7/24.
 */
sw.page.modules["infomanage/enterpriseInfo"] = sw.page.modules["infomanage/enterpriseInfo"] || {
    init: function () {
        // this.loadSelectCode();
        $("input[name='u_id']").val(sw.user.account);
        $("#enterpriseId").find("input[type='text'],select").each(function () {
            $(this).attr("disabled", "disabled");
        });
        this.loadEnterprise();
    },

    // loadSelectCode: function () {
    //     sw.selectOptionByType("enterprise_classification","AGENT_CODE");
    //     sw.selectOptionByType("business_category","AGENT_TYPE");
    //     sw.selectOptionByType("competent_customs","CUSTOMS_CODE");
    //     sw.selectOptionByType("enterprise_nature", "AGENT_NATURE");
    // },

    loadEnterprise: function () {
        var id = sw.user.enterpriseId;
        sw.ajax("enterprise/load/", "", "GET", function (rsp) {
            var data = rsp.data;
            if (data !== null) {
                $("input[name='ent_name']").val(data.ent_name);
                $("input[name='credit_code']").val(data.credit_code);
                $("input[name='ent_legal']").val(data.ent_legal);
                $("input[name='port']").val(data.portCnName);
                $("input[name='declare_ent_name']").val(data.declare_ent_name);
                $("input[name='assure_ent_name']").val(data.assure_ent_name);
                $("input[name='dxp_id']").val(data.dxp_id);

                $("input[name='brevity_code']").val(data.brevity_code);
                $("input[name='customs_code']").val(data.customs_code);
                $("input[name='ent_phone']").val(data.ent_phone);
                $("select[name='ent_business_type']").val(data.ent_business_type);
                $("input[name='declare_ent_code']").val(data.declare_ent_code);
                $("input[name='assure_ent_code']").val(data.assure_ent_code);
            }
        });
    }

};