var page=1;//显示页数
var zopage;//总页数
var url;//访问接口
$(function (){
    $("#body").css("background-image","url('http://"+theUrl+"/image/background/1.jpg')")
    url=openUrl+"/Wallpaper/daily?page=";
    $.get(url+page,function (data){
        zopage=Math.ceil(data[data.length-1]/10);
        for (var i=0;i<data.length-1;i++){
            if (i % 2 === 0){
                $("#a2Span1").append("<div><a href='details.html?img="+data[i].id+"'><img src='http://"+theUrl+"/image/"+data[i].storageLocation+"/"+data[i].id+"."+data[i].type+"'></a></div>")
            }else {
                $("#a2Span2").append("<div><a href='details.html?img="+data[i].id+"'><img src='http://"+theUrl+"/image/"+data[i].storageLocation+"/"+data[i].id+"."+data[i].type+"'></a></div>")
            }
        }
    })
})//页面初始化
$("#a1Div2Svg1").click(function (){
    $("#a1Div2Svg1").hide();
    $("#a1Div2Svg2").show();
    $("#a1Div3").slideDown("slow");
})//展开下拉列表
$("#a1Div2Svg2").click(function (){
    $("#a1Div2Svg2").hide();
    $("#a1Div2Svg1").show();
    $("#a1Div3").slideUp("slow");
})//收缩下拉列表
function openSearch(){
    $(".a1Div1Span").hide();
    $("#a1Div1Div").show();
    $("#searchContent").focus();
}//开启搜索功能
function closeSearch(){
    $("#a1Div1Div").hide();
    $("#searchContent").val("");
    $("#a1Div1DivSpan1").css("border","2px solid silver");
    $(".a1Div1Span").show();
}//关闭搜索功能
$("#changePage").click(function (){
    page++;
    $.get(url+page,function (data){
        for (var i=0;i<data.length-1;i++){
            if (i % 2 === 0){
                $("#a2Span1").append("<div><a href='details.html?img="+data[i].id+"'><img src='http://"+theUrl+"/image/"+data[i].storageLocation+"/"+data[i].id+"."+data[i].type+"'></a></div>")
            }else {
                $("#a2Span2").append("<div><a href='details.html?img="+data[i].id+"'><img src='http://"+theUrl+"/image/"+data[i].storageLocation+"/"+data[i].id+"."+data[i].type+"'></a></div>")
            }
        }
        JudgmentEndPage();
    })
})//下一页
$("#searchContent").keyup(function (event){
    if (event.keyCode==13)search();
})//回车搜索
function pictureType(val){
    page=1;
    url="/Wallpaper/label?value="+val+"&page=";
    $("#a2 span div").remove();
    $.get(url+page,function (data){
        zopage=Math.ceil(data[data.length-1]/10);
        for (var i=0;i<data.length-1;i++){
            if (i % 2 === 0){
                $("#a2Span1").append("<div><a href='details.html?img="+data[i].id+"'><img src='http://"+theUrl+"/image/"+data[i].storageLocation+"/"+data[i].id+"."+data[i].type+"'></a></div>")
            }else {
                $("#a2Span2").append("<div><a href='details.html?img="+data[i].id+"'><img src='http://"+theUrl+"/image/"+data[i].storageLocation+"/"+data[i].id+"."+data[i].type+"'></a></div>")
            }
        }
        JudgmentEndPage();
    })
}//标签搜索
function search(){
    let val = $("#searchContent").val();
    val = $.trim(val);
    if (val !== ""){
        $("#a1Div1DivSpan1").css("border","2px solid silver");
        page=1;
        url="/Wallpaper/search?value="+val+"&page=";
        $("#a2 span div").remove();
        $.get(url+page,function (data){
            zopage=Math.ceil(data[data.length-1]/10);
            for (var i=0;i<data.length-1;i++){
                if (i % 2 === 0){
                    $("#a2Span1").append("<div><a href='details.html?img="+data[i].id+"'><img src='http://"+theUrl+"/image/"+data[i].storageLocation+"/"+data[i].id+"."+data[i].type+"'></a></div>")
                }else {
                    $("#a2Span2").append("<div><a href='details.html?img="+data[i].id+"'><img src='http://"+theUrl+"/image/"+data[i].storageLocation+"/"+data[i].id+"."+data[i].type+"'></a></div>")
                }
            }
            JudgmentEndPage();
        })
    }else{
        $("#a1Div1DivSpan1").css("border","2px solid red");
    }
}//搜索
function JumpUser(){
    if ($.cookie("userId")==null) window.location.href="login.html"
    else window.location.href="user.html"
}//用户跳转
function JudgmentEndPage(){
    if (page>=zopage){
        $("#changePage").attr("disabled",true).val("已经是最后一页啦!");
    }else {
        $("#changePage").attr("disabled",false).val("下一页");
    }
}//末页判断


