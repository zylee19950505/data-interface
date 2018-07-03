/**
 * 密码修改
 * Created by xcp on 2017/07/24.
 */

// 用户管理主页 表格
sw.page.modules["password"] = sw.page.modules["password"] || {

        init: function () {
            var user = sw.user;
            console.log(user);
            $("#profile_uid").val(user.id);
            $("input[name='userId']").val(user.id);
            $("input[name='loginName']").val(user.loginName);
            $("input[name='phone']").val(user.phone);
            $("input[name='email']").val(user.email);
            if (user.userType==="1"){
                $("input[name='userType']").val("普通用户");
            }else if (user.userType==="2"){
                $("input[name='userType']").val("设备编码");
            }
           /* $("input[name='userType']").val(user.userType);*/
            $("#ws-page-apply").click(this.modifyPassword);
        },
        modifyPassword: function () {
            var uid = $("#profile_uid").val();
            var old_password = $("input[name='old_password']").val();
            var new_password = $("input[name='new_password']").val();
            var confirm_password = $("input[name='confirm_password']").val();
            if ('visitor'==uid){
                sw.alert("体验账号不允许修改密码", "错误提示")
                return;
            }
            if (old_password == '' || old_password == undefined || old_password == null) {
                sw.alert("未填写原始密码", "错误提示")
                return;
            }
            if (old_password.length < 6) {
                sw.alert("原始密码不得少于6位", "错误提示")
                return;
            }
            if (new_password == '' || new_password == undefined || new_password == null) {
                sw.alert("未填写新密码", "错误提示")
                return;
            }
            if (new_password.length < 6) {
                sw.alert("新密码不得少于6位", "错误提示")
                return;
            }
            if (confirm_password == '' || confirm_password == undefined || confirm_password == null) {
                sw.alert("未确认新密码", "错误提示")
                return;
            }
            if (confirm_password.length < 6) {
                sw.alert("确认新密码不得少于6位", "错误提示")
                return;
            }
            if (new_password != confirm_password) {
                sw.alert("确认新密码与新密码不一致", "错误提示")
                return;
            }
            sw.ajax("api/modifyPassword", "POST", {
                userId: uid,
                old_password: old_password,
                new_password: new_password
            }, function (rsp) {
                var result = rsp.data.result;
                var msg = rsp.data.msg;
                if (result) {
                    sw.alert(msg, "温馨提示", function () {
                        $("#logout").click();
                        // location.href = "";
                    });
                } else {
                    sw.alert(msg, "错误提示");
                }
            }, null, $("input[name='_csrf']").val());
        }
    };



