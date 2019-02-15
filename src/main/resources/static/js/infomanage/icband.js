/**
 * IC卡绑定
 */
sw.page.modules["infomanage/icband"] = sw.page.modules["infomanage/icband"] || {
        nextStep: function () {
            loadCardInfo();
        },
        reloadIc: function () {
            loadCardInfo();
        },
        changePwd: function () {
            var iccard=$("#iccard_no").val();
            var pwd=$("#iccard_pwd").val();
            if (isEmpty(iccard)){
                warringAlert("IC卡号不能为空，请尝试重新读取");
                return;
            }
            if (isEmpty(pwd)){
                warringAlert("密码不能为空，请先输入密码");
                return;
            }
            var data = {
                pwd: pwd
            };
            sw.ajax("api/convertPwd","GET",data,function(rsp){
                if (rsp.data.result == "true"){
                    var pwd = rsp.data.msg;
                    // 显示Loading图标
                    $("#loadingDiv").removeClass("hidden");
                    window.xaeportAgent.verify(pwd,function (rsp) {
                        if (rsp.status == 200) {
                            var param = {
                                icCardNo: iccard,
                                icCardPwd: pwd
                            }
                            // 绑定IC卡
                            sw.ajax("api/saveBandIc","PUT",param,function(rsp){
                                if (rsp.data.result == "true"){
                                    $("#dialog-checkinfo").modal('hide');
                                    sw.alert("绑定IC卡成功","提示",null,"modal-success");
                                    $("#iccard_pwd").val();
                                    sw.page.modules["infomanage/icbandInfo"].loadUserIc();
                                    sw.page.modules["infomanage/icbandInfo"].loadBandOprHis();
                                }else{
                                    warringAlert(rsp.data.msg);
                                }
                                $("#loadingDiv").addClass("hidden");
                            });
                        }else{
                            warringAlert(rsp.message);
                            // 移除Loading图标
                            $("#loadingDiv").addClass("hidden");
                        }
                    });
                }else{
                    warringAlert(rsp.data.msg);
                }
            });

        },
        init: function () {
            $("[ws-nextStep]").unbind("click").click(this.nextStep);
            $("[ws-changePwd]").unbind("click").click(this.changePwd);
            $("[ws-reloadIc]").unbind("click").click(this.reloadIc);
            // 检测控件是否有效，如果有效则进入IC卡绑定界面，如果无效则进入IC卡操作说明界面
            loadCardInfo();
        }
    };
/**
 * 显示提示框
 * @param msg
 */
function warringAlert(msg) {
    $("#waringAlert").removeClass("hidden");
    $("#alertMsg").html(msg);
    // setTimeout("closeWarringAlert();",3000);
}

/**
 * 关闭提示框
 */
function closeWarringAlert() {
    $("#waringAlert").addClass("hidden");
}

/**
 * 加载卡信息
 */
function loadCardInfo() {
    var chkFlag = agentCheck();
    if (chkFlag) {
        loadIcInfo();
    } else {
        warringAlert("驱动程序无效，请按照操作说明安装驱动程序！安装完毕后刷新页面重试！");
        showBandCardInfo();
    }
}

/**
 * 校验是否为空
 * @param str
 * @returns {boolean}
 */
function isEmpty(str) {
    if (undefined == str || null == str || str.length == 0) return true;
    return false;
}
/**
 * 显示绑卡操作说明界面
 */
function showBandCardInfo() {
    $("#bandCardInfo").removeClass("hidden");
    $("#bandICCard").addClass("hidden");
}
/**
 * 显示读卡信息界面
 */
function showICCard() {
    $("#bandCardInfo").addClass("hidden");
    $("#bandICCard").removeClass("hidden");
}
/**
 * 读卡控件检查
 * @returns {boolean}
 */
function agentCheck() {
    if (typeof window.xaeportAgent != "object") {
        warringAlert("驱动程序初始化失败");
        return false;
    }
    console.log("驱动程序初始化成功");
    return true;
}
/**
 * 加载IC卡驱动及IC卡信息
 */
function loadIcInfo() {
    showICCard();
    // 显示Loading图标
    $("#loadingDiv").removeClass("hidden");
    window.xaeportAgent.getCard(function (rsp) {
        if (rsp.status == 200) {
            var data = {
                icCardNo: rsp.data[0]
            };
            sw.ajax("api/convertIcNo","GET",data,function(rsp){
                if (rsp.data.result == "true"){
                    // 填充IC卡号
                    $("#iccard_no").val(rsp.data.msg);
                }else{
                    warringAlert(rsp.data.msg);
                }
            });
        }else{
            var msg = rsp.message;
            if (isEmpty(rsp)){
                showBandCardInfo();
                msg = "请检查驱动程序或刷新页面后重试";
            }
            warringAlert(msg);
        }
        // 移除Loading图标
        $("#loadingDiv").addClass("hidden");
    });
}
