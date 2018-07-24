/**
 * 用户管理
 * Created by xcp on 2016/12/2.
 */

// 用户管理主页 表格
sw.page.modules["sysmanage/user"] = sw.page.modules["sysmanage/user"] || {
    query: function () {
        var id = $("[name='id']").val();
        var userType = $("[name='userType']").val();
        // 拼接URL及参数
        var url = sw.serializeObjectToURL("api/sysmanage/user", {
            id: id,
            userType: userType
        });

        sw.datatable("#query-user-table", {
            ajax: url,
            searching: false,
            columns: [
                {
                    label: "序号", render: function (data, type, full, meta) {
                    return meta.row + 1 + meta.settings._iDisplayStart;
                }
                },
                {data: "ID", label: "用户帐号"},
                {data: "IC", label: "Ukey"},
                {data: "LOGINNAME", label: "用户名称"},
                {data: "ENT_NAME", label: "企业名称"},
                {data: "STATE", label: "用户状态"},
                {data: "PHONE", label: "电话"},
                {data: "EMAIL", label: "邮箱"},
                {
                    label: "操作",
                    render: function (data, type, row) {
                        if (row.LOGINNAME == 'admin') {
                            return " ";
                        }
                        var code =
                            '<button ' + ' class="btn btn-sm btn-primary" title="用户修改" onclick="'
                            + "javascript:sw.loadWorkspace('sysmanage/userEdit?id=" + row.ID
                            + "');" + '"><i class="fa fa-edit"></i> </button> ' +
                            '<button ' + ' class="btn btn-sm btn-warning" title="密码重置" onclick="'
                            + "javascript:sw.loadWorkspace('sysmanage/userChgPwd?id=" + row.ID
                            + "&loginName=" + row.LOGINNAME + "');"
                            + '"><i class="fa fa-repeat"></i> </button> ' +
                            '<button class="btn btn-sm btn-danger" title="用户删除" onclick="'
                            + "javascript:sw.page.modules['sysmanage/user'].delete('" + row.ID
                            + "', '" + row.LOGINNAME + "')"
                            + '"><i class="fa fa-remove"></i> </button>';
                        return code;
                    }
                }
            ]
        });
    },
    delete: function (ID, LOGINNAME) {
        console.log("id " + ID + " Loginname " + LOGINNAME)
        sw.confirm("确定删除用户 \"" + LOGINNAME + "\"", "确认", function () {
            sw.ajax("api/sysmanage/user/" + ID, "DELETE", {}, function (rsp) {
                sw.page.modules['sysmanage/user'].query();
            });
        });
    },
    init: function () {
        $("[ws-search]").unbind("click").click(this.query).click();
    }
};


// 用户 新增用户，修改用户
sw.page.modules["sysmanage/userEdit"] = sw.page.modules["sysmanage/userEdit"] || {
    back: function () {
        sw.showPageQuery();
    },
    validateFrom: function (isUpdate) {
        var id = $("#id").val();
        var loginName = $("#loginName").val();
        var password = $("#password").val();
        var ic = $("#ic").val();
        var state = $("#state").val();
        var phone = $("#phone").val();
        var email = $("#email").val();
        var role = $("#role").val();
        var entSelect = $("#entSelect").val();

        if (isEmpty(id) || id.length < 3) {
            hasErrorUser("#id", "登录账号不能为空或小于3个字符");
            return false;
        }
        if (isEmpty(loginName)) {
            hasErrorUser("#loginName", "用户姓名不能为空");
            return false;
        }
        if (!isUpdate && ( isEmpty(password) || password.length < 6)) {
            hasErrorUser("#password", "登录密码不能为空或小于6个字符");
            return false;
        }
        if (isEmpty(role)) {
            hasErrorUser("#authSelect","请选择角色信息");
            return false;
        }
        if (isEmpty(entSelect)) {
            hasErrorUser("#entSelect","请选择企业信息");
            return false;
        }
        return true;
    },
    userCreate: function () {
        var userData = sw.serialize("#sw-userInfo");
        if (!sw.page.modules["sysmanage/userEdit"].validateFrom(false)) {
            return;
        }
        sw.ajax("api/sysmanage/user", "POST", userData, function (rsp) {
            sw.pageModule("sysmanage/userEdit").back();
            sw.page.modules['sysmanage/user'].query();
        }, function (status, err, xhr) {
            sw.alert(xhr.data, "提示", null, "modal-info");
            return;
        });
    },
    userUpdate: function () {
        var params = sw.getPageParams("sysmanage/userEdit");
        var userData = sw.serialize("#sw-userInfo");
        if (!sw.page.modules["sysmanage/userEdit"].validateFrom(true)) {
            return;
        }
        sw.ajax("api/sysmanage/user/" + params.id, "PUT", userData, function (rsp) {
            sw.alert(rsp.data.msg, "提示", null, "modal-info");
            sw.pageModule("sysmanage/userEdit").back();
            sw.page.modules['sysmanage/user'].query();
        }, function (status, err, xhr) {
            sw.alert(xhr.data, "提示", null, "modal-info");
            return;
        });
    },

    loadEntSelect: function (entId) {
        sw.ajax("api/sysmanage/entSelect/", "GET", {}, function (rsp) {
            var list = rsp.data;
            $("#entSelect").html("");
            if (null != list && list.length > 0) {
                for (var i = 0; i < list.length; i++) {
                    var isSelected = !isEmpty(entId) && entId == list[i].ID ? "selected='selected'" : "";
                    $("#entSelect").append(
                        "<option value='" + list[i].ID + "' " + isSelected + ">" + list[i].NAME
                        + "</option>");
                }
            }
        });
    },

    loadRole: function (role) {
        sw.ajax("api/sysmanage/roleSelect/", "GET", {}, function (rsp) {
            var list = rsp.data;
            $("#role").html("");
            if (null != list && list.length > 0) {
                for (var i = 0; i < list.length; i++) {
                    var isSelected = !isEmpty(role) && role == list[i].R_ID
                        ? "selected='selected'" : "";
                    $("#role").append(
                        "<option value='" + list[i].R_ID + "' " + isSelected + ">"
                        + list[i].R_NAME + "</option>");
                }
            }
        });
    },

    init: function () {
        var params = sw.getPageParams("sysmanage/userEdit");
        if (!params) {
            $("#ws-work-title").text("新增用户");
            this.loadRole();
            this.loadEntSelect();
            $("#ws-page-apply").click(this.userCreate);

        } else {
            $("#ws-work-title").text("编辑用户");

            $("#ws-page-apply").unbind("click").click(this.userUpdate);
            $("#password-div").addClass("hidden");
            $("#id").attr("disabled", "disabled");

            sw.ajax("api/sysmanage/user/" + params.id, "GET", {id: params.id}, function (rsp) {
                var userData = rsp.data;
                $("#sw-userInfo [name='id']").val(userData.ID);
                $("#sw-userInfo [name='loginName']").val(userData.LOGINNAME);
                $("#sw-userInfo [name='password']").val(userData.PASSWORD);
                $("#sw-userInfo [name='ic']").val(userData.IC);
                $("#sw-userInfo [name='state']").val(userData.STATE);
                $("#sw-userInfo [name='phone']").val(userData.PHONE);
                $("#sw-userInfo [name='email']").val(userData.EMAIL);
                sw.page.modules["sysmanage/userEdit"].loadRole(userData.ROLEID);
                sw.page.modules["sysmanage/userEdit"].loadEntSelect(userData.ENT_ID);
            });
        }
        $("input,select").change(function () {
            $(this).parent().removeClass("has-error");
            $(this).parent().find(".help-block").addClass("hidden").html("");
        });
        $("#ws-page-back").click(this.back);
    }
};


// 用户密码重置
sw.page.modules["sysmanage/userChgPwd"] = sw.page.modules["sysmanage/userChgPwd"] || {
    back: function () {
        sw.showPageQuery();
    },
    chgPwd: function () {
        var id = $("#ws-host [name='id-chgpwd']").val();
        var password = $("#ws-host [name='password-chgpwd']").val();

        if (isEmpty(password) || password.length < 6) {
            sw.alert("密码不能为空且应大于6个字符");
            return false;
        }

        var param = {
            id: id,
            password: password
        };
        sw.ajax("api/sysmanage/reset", "PUT", param, function (rsp) {
            if (rsp.data.result == "true") {
                sw.alert("密码重置成功");
                sw.page.modules["sysmanage/userChgPwd"].back();
            } else {
                sw.alert(rsp.data.msg);
            }
        })
    },
    init: function () {
        $("#ws-work-title").text("重置密码");
        var params = sw.getPageParams("sysmanage/userChgPwd");
        // $("#ws-host [name='account-chgpwd']").val(params.account);
        $("#ws-host [name='id-chgpwd']").val(params.id);

        $("#ws-page-apply").unbind("click").click(this.chgPwd);
        $("#ws-page-back").unbind("click").click(this.back);
    }
};

function isEmpty(str) {
    if (undefined == str || null == str || str.length == 0) return true;
    return false;
}


function hasErrorUser(selecter, errorMsg) {
    $(selecter).parent().addClass("has-error");
    $(selecter).parent().find(".help-block").removeClass("hidden").html(errorMsg);
    $(selecter).focus();
}


