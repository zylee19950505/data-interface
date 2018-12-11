sw.page.modules["booksAndRecords/accountRecord"] = sw.page.modules["booksAndRecords/accountRecord"] || {

    init: function () {
        $("#abcdef").click(this.crtblock);

    },

    crtblock: function () {
        alert("进来了!");
        var url = "bondedIEnter/seeEnterInventoryDetail";
        // var url = "bondedIEnter/seeRecordDetail";
        // var url = "bondedIEnter/seeEnterManifestDetail";
        // var url = "bondedIExit/seeExitInventoryDetail";
        // var url = "bondedIExit/seeExitManifestDetail";

        sw.modelPopup(url, "新建入区核注清单", false, 1000, 1000);
    }

};

sw.page.modules["booksAndRecords/accountEdit"] = sw.page.modules["booksAndRecords/accountEdit"] || {

    back: function () {
        sw.showPageQuery();
    },

    init: function () {
        var params = sw.getPageParams("booksAndRecords/accountEdit");
        // this.loadSelectCode();

        if(!params){
            $("#ws-work-title").text("新增企业");
            $("#ws-page-apply").click();
        }


        // $("input,select").change(function () {
        //     $(this).parent().removeClass("has-error");
        //     $(this).parent().find(".help-block").addClass("hidden").html("");
        // }).focus(function () {
        //     $("#errorMsg").html("").addClass("hidden");
        // });
        $("#ws-page-back").click(this.back);
    }

};