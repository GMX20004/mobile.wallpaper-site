var page=1;//显示页数
var zopage;//总页数
var url;//访问接口
$(function (){
    $("#body").css("background-image","url('http://"+theUrl+"/image/background/1.jpg')")
    url=openUrl+"/Wallpaper/daily?page=";
    $.get(url+page,
        function(data){
            zopage=Math.ceil(data[data.length-1]/10);
            var c = true;
            for (var i=0;i<data.length-1;i++){
                if (c){
                    $("#a4SpanDiv1").append("<a href='details.html?img="+data[i].id+"'><img src='http://"+theUrl+"/image/"+data[i].storageLocation+"/"+data[i].id+"."+data[i].type+"' class='img'></a>")
                    c=false;
                }else {
                    $("#a4SpanDiv2").append("<a href='details.html?img="+data[i].id+"'><img src='http://"+theUrl+"/image/"+data[i].storageLocation+"/"+data[i].id+"."+data[i].type+"' class='img'></a>")
                    c=true;
                }
            }
        })
})//初始化页面
$("#a5Input").click(function (){
    if (page<zopage){
        page=page+1;
    $.get(url+page,
        function(data){
            var c = true;
            for (var i=0;i<data.length-1;i++){
                if (c){
                    $("#a4SpanDiv1").append("<a href='details.html?img="+data[i].id+"'><img src='http://"+theUrl+"/image/"+data[i].storageLocation+"/"+data[i].id+"."+data[i].type+"' class='img'></a>")
                    c=false;
                }else {
                    $("#a4SpanDiv2").append("<a href='details.html?img="+data[i].id+"'><img src='http://"+theUrl+"/image/"+data[i].storageLocation+"/"+data[i].id+"."+data[i].type+"' class='img'></a>")
                    c=true;
                }
            }
        })
        if (page>=zopage) $("#a5Input").val("不好意思啊，已经到底啦")
    }
})//下一页
$("#a1SpanSvg5").click(function (){
    if ($.cookie("userId")==null)window.location.href="login.html"
    else window.location.href="user.html?type=0"
})//跳转个人用户界面
$("#a1SpanSvg4").click(function (){
    $("#a1input").val('');
    $(".a1span").hide();
    $("#a1div").show();
    $("#a1input").focus();
})//打开搜索页面
$("#a1divspan2a").click(function (){
    $("#a1div").hide();
    $(".a1span").show();
    $("#a1divspan").css("border","3px solid #e38d13")
})//关闭搜索页面
$("#a2svg1").click(function (){
    $("#hr1").hide();
    $("#a2svg1").hide();
    $("#a3").show();
    $("#hr2").show();
    $("#a2svg2").show();
})//下拉选择类型
$("#a2svg2").click(function (){
    $("#a3").hide();
    $("#hr2").hide();
    $("#a2svg2").hide();
    $("#hr1").show();
    $("#a2svg1").show();
})//收缩类型
$("#a1divsvg").click(function (){
    var content = $("#a1input").val();
    content = $.trim(content);
    if (content == null || content == '') $("#a1divspan").css("border","3px solid red")
    else {
        $("#a1divspan").css("border","3px solid #e38d13")
        $("#a4SpanDiv1").html('');
        $("#a4SpanDiv2").html('');
        $("#a5Input").val("下一页");
        page=1;
        url=openUrl+"/Wallpaper/search?value="+content+"&page=";
        $.get(url+page,
            function(data){
                zopage=Math.ceil(data[data.length-1]/10);
                if (zopage<=page) $("#a5Input").val("不好意思啊，已经到底啦");
                var c = true;
                for (var i=0;i<data.length-1;i++){
                    if (c){
                        $("#a4SpanDiv1").append("<a href='details.html?img="+data[i].id+"'><img src='http://"+theUrl+"/image/"+data[i].storageLocation+"/"+data[i].id+"."+data[i].type+"' class='img'></a>")
                        c=false;
                    }else {
                        $("#a4SpanDiv2").append("<a href='details.html?img="+data[i].id+"'><img src='http://"+theUrl+"/image/"+data[i].storageLocation+"/"+data[i].id+"."+data[i].type+"' class='img'></a>")
                        c=true;
                    }
                }
            })
    }
})//搜索结果
$("#a1input").keyup(function (event) {
    if (event.keyCode==13) $("#a1divsvg").click()
})//搜索框回车
function pictureType(name){
    $("#a4SpanDiv1").html('');
    $("#a4SpanDiv2").html('');
    $("#a5Input").val("下一页");
    page=1;
    url="/Wallpaper/label?value="+name+"&page=";
    $.get(url+page,
        function(data){
            zopage=Math.ceil(data[data.length-1]/10);
            if (zopage<=page) $("#a5Input").val("不好意思啊，已经到底啦");
            var c = true;
            for (var i=0;i<data.length-1;i++){
                if (c){
                    $("#a4SpanDiv1").append("<a href='details.html?img="+data[i].id+"'><img src='http://"+theUrl+"/image/"+data[i].storageLocation+"/"+data[i].id+"."+data[i].type+"' class='img'></a>")
                    c=false;
                }else {
                    $("#a4SpanDiv2").append("<a href='details.html?img="+data[i].id+"'><img src='http://"+theUrl+"/image/"+data[i].storageLocation+"/"+data[i].id+"."+data[i].type+"' class='img'></a>")
                    c=true;
                }
            }
        })
    if (zopage<=page) $("#a5Input").val("不好意思啊，已经到底啦");
}//标签搜索方法

