/**
 * Created by zwj on 2017/7/24.
 */
sw.page.modules["infomanage/enterpriseInfo"] = sw.page.modules["infomanage/enterpriseInfo"] || {
        init: function () {
            this.loadSelectCode();
            $("input[name='u_id']").val(sw.user.account);
            $("#enterpriseId").find("input[type='text'],select").each(function () {
                $(this).attr("disabled", "disabled");
            });
            $("#cancelId,#saveId,#editId").hide();
            setTimeout(function(){sw.page.modules["information/enterprise_info"].loadEnterprise()}, 500);
        },

        cancelEnterprise: function () {
            $("#cancelId,#saveId").hide();
            $("#editId").show();
            $("#enterpriseId").find("input[type='text']").each(function () {
                $(this).attr("disabled", "disabled");
            });
        },

        editEnterprise: function () {
            $("#cancelId,#saveId").show();
            $("#editId").hide();
            $("#enterpriseId").find("input[type='text']").each(function () {
                $(this).removeAttr("disabled");
            });
        },

        saveEnterprise: function () {
            var formData = sw.serialize("#enterpriseId");
            sw.ajax("enterprise/edit", "POST", formData, function (rep) {
                sw.page.modules["information/enterprise_info"].loadEnterprise();
                $("#cancelId,#saveId").hide();
                $("#editId").show();
                $("#enterpriseId").find("input[type='text']").each(function () {
                    $(this).attr("disabled", "disabled");
                });
            }, function (status, err, xhr) {
                sw.alert(xhr.data, "提示", null, "modal-info");
                return;
            });
        },
        loadSelectCode: function () {
            sw.selectOptionByType("enterprise_classification","AGENT_CODE");
            sw.selectOptionByType("business_category","AGENT_TYPE");
            sw.selectOptionByType("competent_customs","CUSTOMS_CODE");
            sw.selectOptionByType("enterprise_nature", "AGENT_NATURE");
        },
        loadEnterprise: function () {
            var id = sw.user.enterpriseId;
            sw.ajax("enterprise/load/" + id, "", "GET", function (rsp) {
                var data = rsp.data;
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
                }
            });
        }

    };