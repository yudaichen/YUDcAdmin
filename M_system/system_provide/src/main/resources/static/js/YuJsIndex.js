
    $(document).ready(function() {
        console.log("开始获取菜单List");
        $.ajax({
            async: true,
            url: '/system/sysuser/getMenu',
            type: "post",
            contentType: "application/json;charsetset=UTF-8",//必须
            dataType: "json",//必须
            success: function (data) {
                if (data==0||data==null)
                    alert("登录过期，请重新登录")
                else {
                    var html = "";
                    for (var i = 0; i < data.length; i++) {
                        if (data[i].visible !== "1" && data[i].parentId === 0) {
                            html += "<li class='" + data[i].menuId + "'>";
                            html +="<a href=\"javascript:void(0)\" onclick=\"createTab('"+data[i].menuId+"','"+data[i].url+"','"+data[i].menuName+"')\">";
                            html += "<i class='" + data[i].icon + "'></i>";
                            html += data[i].menuName;
                            html += "</a><b class='arrow'></b></li>"
                        }
                    }
                }
                $(".submenu").append(html);
                //改变当前菜单样式
            },
            error:function () {
                alert("请重新登录");
            }
        });
    })

    /*创建选项卡*/
    function createTab(MenuId,MenuUrl,MenuName) {
        let flagMenuTab=$("."+MenuId+"Tab").val();

        if(flagMenuTab===undefined){//创建选项卡
            var TabHtml="";
            TabHtml+="<li class='"+MenuId+"Tab'>";
            TabHtml+="<a onclick=\"TabGetIframe('"+MenuId+"')\">"+MenuName+"</a>";
            TabHtml+="<a><i class=\"glyphicon glyphicon-remove\" onclick=\"DeleteTab('"+MenuId+"')\"></i></a>";
            TabHtml+="</li>"
            $(".breadcrumb").append(TabHtml);
            IframeOperation(MenuId,MenuUrl);
            TabCssOperation(MenuId);
        }
        else{//如果选项阿卡存在直接打开iframe
            TabCssOperation(MenuId);
            $("iframe").hide();//让所有iframe隐藏
            $("."+MenuId+"Iframe").show();
        }
    }
    /*通过Tab获得Iframe*/
    function TabGetIframe(MenuId){
        TabCssOperation(MenuId);
        $("iframe").hide();//让所有iframe隐藏
        $("."+MenuId+"Iframe").show();
    }
    /*删除Tab*/
    function DeleteTab(MenuId) {
        $("."+MenuId+"Tab").siblings().css({"background-color":"","padding":"10px 10px 10px 10px"});
        $("."+MenuId+"Tab").next().css({"background-color":"#cddae2","padding":"10px 10px 10px 10px","high":"100%"});
        $("."+MenuId+"Iframe").siblings().hide();
        $("."+MenuId+"Iframe").next().show();
        $("."+MenuId+"Tab").remove();
        destroyIframe(MenuId);
    }
    /*销毁iframe*/
    function destroyIframe(iframeID){
        var iframeClass = document.getElementsByClassName("."+iframeID+"Iframe");
        iframeClass.src = 'about:blank';
        try{
            iframeClass.contentWindow.document.write('');
            iframeClass.contentWindow.document.clear();
        }catch(e){}

        //把iframe从页面移除

        $("."+iframeID+"Iframe").remove();
    }

    /*操作Iframe*/
    function IframeOperation(MenuId, MenuUrl) {
        let flagIframeClass=$("."+MenuId+"Iframe").val();
        $("iframe").hide();//让所有iframe隐藏
        if(flagIframeClass===undefined) {//如果没有就创建
            let IframeHtml='';
            IframeHtml+=" <iframe class=\'"+MenuId+"Iframe\' onload=\"this.height=this.contentWindow.document.documentElement.scrollHeight+500;\" style=\"overflow: auto; width: calc(100% + 10px);\" data-id=\'"+MenuUrl+"\' scrolling=\"no\" src=\'"+MenuUrl+"\' frameborder='0' seamless></iframe>" ;
            $(".MyIframe").append(IframeHtml);
            console.log(IframeHtml)
        }
        else {//如果有就打开
            TabCssOperation(MenuId);
            $("."+flagIframeClass).show();
        }
    }
    /*操作Tab样式*/
    function TabCssOperation(MenuId){
        $("."+MenuId+"Tab").siblings().css({"background-color":"","padding":"10px 10px 10px 10px"});
        $("."+MenuId+"Tab").css({"background-color":"#cddae2","padding":"10px 10px 10px 10px","high":"100%"});
    }
