// 物流协同查询
sw.page.modules["serviceMenu/coordinationQuery"] = sw.page.modules["serviceMenu/coordinationQuery"] || {
    query: function () {
        var delivery = $("[name='delivery']").val();//提运单号
        var entryId = $("[name='entryId']").val();//报关单号

        if (isEmpty(delivery)&&isEmpty(entryId)) {
            sw.alert("报关单号,提运单号必须输入一个", "提示", "", "modal-info");
            return;
        }

        var postData =  {
            delivery: delivery,
            entryId: entryId //,
            // customs:customs,
            // newBill:newBill,
            // conveyance:conveyance
        };

        sw.ajax("/coordination/queryCoordination", "POST", postData, function (rsp) {
            console.log(rsp);
            var data = rsp.data;
            var timeLine = $("#timeLine").empty();
            var imgs;
            var isShow;
            if(data){
                var none = $("<li/>")
                    .append($("<i/>").addClass("fa fa-exclamation-circle text-red"))
                    .append($("<div/>").addClass("timeline-item").append($("<h3></h3>").addClass("timeline-header").append($("<span></span>").addClass("text-sm").append("未能查找到数据"))));
                if(data.length < 1){
                    timeLine.append(none);
                    timeLine.append($("<li></li>").append($("<i></i>").addClass("fa fa-clock-o bg-gray")));
                    return ;
                }
                for(i in data){
                    if(data[i].HEAD_MESSAGETYPE == ""){
                        continue;
                    }
                    isShow = "hidden";
                    if(data[i].TYPE == "ysgj"){
                        if($("#chkCam").is(":checked")){
                            isShow = "show"
                        }
                        imgs = "fa fa-plane text-yellow";
                    }if(data[i].TYPE == "bgd"){
                        if($("#chkCus").is(":checked")){
                            isShow = "show"
                        }
                        imgs = "fa fa-file-text-o text-blue";
                    }if(data[i].TYPE == "xcd"){
                        if($("#chkMft").is(":checked")){
                            isShow = "show"
                        }
                        imgs = "fa fa-archive text-green";
                    }

                    var imgI = $("<i></i>").addClass(imgs);
                    var divI = $("<div></div>").addClass("timeline-item");
                    divI.append($("<span></span>").addClass("time").append($("<i></i>").addClass("fa fa-clock-o")).append(data[i].HEAD_SENDTIME));
                    divI.append($("<h3></h3>").addClass("timeline-header").append($("<span></span>").addClass("text-sm").append(data[i].HEAD_MESSAGETYPE)));
                    timeLine.append($("<li></li>").addClass(isShow).append(imgI).append(divI));
                }
            }else{
                timeLine.append(none);
            }
            timeLine.append($("<li></li>").append($("<i></i>").addClass("fa fa-clock-o bg-gray")));
        });
    },

    init: function () {
        $("[ws-search]").unbind("click").click(this.query);
        $("input[name=tool]").click(function(){
           var type = $(this).val();
           var isChecked = $(this).is(':checked');
            var subCls = "";
            var rmCls = "";
            var clsName = "";
           if(isChecked){
               // 选中
               subCls = "show";
               rmCls ="hidden";
           }else{
               subCls = "hidden";
               rmCls ="show";
           }
            switch(type){
               case "customs": clsName = "fa-file-text-o";break;
               case "newBill": clsName = "fa-archive";break;
               case "conveyance":clsName = "fa-plane";break;
           }
            $("."+clsName).each(function(){
                var parentNode = $(this).parent();
                if(parentNode[0].nodeName == "LI"){
                    parentNode.removeClass(rmCls).addClass(subCls);
                }
            });
        });
    },

    editDeclareInfo: function (MAILNO) {
        var url = "mailenter/editDeclareInfo?mailNo=" + MAILNO;
        sw.modelPopup(url, "邮局申报信息", false, 1200, 600);
    },

    choose : function (image_url) {
        $("#image").attr('src',image_url+"?time="+(new Date()).getTime())
    }

};



