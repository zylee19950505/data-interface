/**
 * Created by xcp on 2016/11/30.
 */

function isEmpty(str) {
    if (undefined == str || null == str || str.length == 0) return true;
    return false;
}

function fixSize() {
    var height = $(window).height();
    var width = $(window).width();

    if ((window.lastWidth == width) && (window.lastHeight == height))
        return;

    window.lastWidth = width;
    window.lastHeight = height;

    var bgRadio = 1400 / 800;
    var bgHeight;
    var bgWidth;

    height = height < 600 ? 600 : height;

    if (width < 460) {
        $(".login").css("width", "auto");
    } else {
        $(".login").css("width", 460);
    }

    $(".wrap").animate({width: width}, 200);
    $(".side,.bg").animate({height: height}, 200);

    if (width > height) {
        bgHeight = width / bgRadio;
        bgWidth = bgHeight * bgRadio;
        if (bgHeight < height) {
            bgHeight = height;
            bgWidth = bgHeight * bgRadio;
        }
    } else {
        bgWidth = height * bgRadio;
        bgHeight = height;
    }

    $("#loginBackground").width(bgWidth).height(bgHeight).fadeIn(100);
}

$(function () {
    fixSize();
    window.setInterval(fixSize, 200);

    $("[name='loginName']").focus().keydown(function (event) {
        if (event.keyCode == 13) {
            $("[name='loginPwd']").focus();
        }
    });

    $("[name='loginPwd']").keydown(function (event) {
        if (event.keyCode == 13) {
            $("#loginBtn").click();
        }
    });

    $("#vCodeLink").click(function () {
        $(this).find("img:first").attr("src", 'login/getAuthCode?abc=' + Math.random());
    });

    // $("#loginBtn").click(function () {
    //     console.log(11);
    //     var params = webSquid.serialize("form");
    //     sw.ajax("/admin/login", "POST", params, function (rsp) {
    //         window.location = rsp;
    //         location.href
    //     }, function (status, err, rsp) {
    //         if (rsp.status == 404) {
    //             webSquid.alert(rsp.data);
    //             $("#vCodeLink").click();
    //         }
    //     });
    // });
});
