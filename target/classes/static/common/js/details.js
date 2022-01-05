var wd=1
var praise;
var collection;
var userId;
$(function (){

    $.post("/Wallpaper/wallpaper",{
        id: getUrlParam("img"),
        userId: $.cookie("userId")
    },function (data) {
        userId = data[0].userId;
        $("#a1Img").attr("src","http://"+theUrl+"/image/headPortrait/"+data[0].headPortrait);
        $("#a1Span2Div1S").text(data[0].name);
        if (data[0].isPraise==0){
            $("#a1Span1Svg2").hide();
            $("#a1Span1Svg1").show();
        }else {
            $("#a1Span1Svg1").hide();
            $("#a1Span1Svg2").show();
        }
        if (data[0].isCollection==0){
            $("#a1Span2Svg2").hide();
            $("#a1Span2Svg1").show();
        }else {
            $("#a1Span2Svg1").hide();
            $("#a1Span2Svg2").show();
        }
        praise = data[0].praise;
        collection = data[0].collection;
        $("#a1SpanDiv2B1").text(praise);
        $("#a1SpanDiv2B2").text(collection);
        $(".img").attr("src","http://"+theUrl+"/image/"+data[0].storageLocation+"/"+data[0].id+"."+data[0].type)
    })
})

$("#a1Span1Svg1").click(function (){
    if ($.cookie("userId")==null) window.location.href="login.html"
    else{
        $.post("/Wallpaper/obtainCancel",{
            id: getUrlParam("img"),
            userId: $.cookie("userId"),
            type: 1,
            is: 1
        },function (data) {
            if (data==true){
                $("#a1Span1Svg1").hide();
                $("#a1Span1Svg2").show();
                praise = praise+1;
                $("#a1SpanDiv2B1").text(praise);
            }
        })
    }
})//点赞
$("#a1Span1Svg2").click(function (){
    $.post("/Wallpaper/obtainCancel",{
        id: getUrlParam("img"),
        userId: $.cookie("userId"),
        type: 1,
        is: 0
    },function (data) {
        if (data==true){
            $("#a1Span1Svg2").hide();
            $("#a1Span1Svg1").show();
            praise = praise-1;
            $("#a1SpanDiv2B1").text(praise);
        }
    })
})//取消点赞
$("#a1Span2Svg1").click(function (){
    if ($.cookie("userId")==null) window.location.href="login.html"
    else{
        $.post("/Wallpaper/obtainCancel",{
            id: getUrlParam("img"),
            userId: $.cookie("userId"),
            type: 0,
            is: 1
        },function (data) {
            if (data==true){
                $("#a1Span2Svg1").hide();
                $("#a1Span2Svg2").show();
                collection = collection+1;
                $("#a1SpanDiv2B2").text(collection);
            }
        })
    }
})//收藏
$("#a1Span2Svg2").click(function (){
    $.post("/Wallpaper/obtainCancel",{
        id: getUrlParam("img"),
        userId: $.cookie("userId"),
        type: 0,
        is: 0
    },function (data) {
        if (data==true){
            $("#a1Span2Svg2").hide();
            $("#a1Span2Svg1").show();
            collection = collection-1;
            $("#a1SpanDiv2B2").text(collection);
        }
    })
})//取消收藏
$("#a1Img").click(function (){
    $.cookie("othersId",userId);
    window.location.href="user.html?type=1"
})//点击头像
$(".img").click(function (){
    BrowserFullScreen();
    if (wd==1){
        $("#a0").hide();
        $("#a1").hide();

        $("#a2").css("height","100%")
        $(".img").css("margin-top",($("#a2").height()-$(".img").height())/2)
        wd=2
    }else {
        $("#a2").css("height","80%")
        $(".img").css("margin-top",($("#a2").height()-$(".img").height())/2)
        $("#a0").show();
        $("#a1").show();
        wd=1
    }
})//壁纸全屏
