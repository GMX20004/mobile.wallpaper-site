var page = 1;//当前页数
var zopage;//总页数
var url;
$(function (){
    $("#body").css("background-image","url('http://"+theUrl+"/image/background/1.jpg')")
    url = "/Wallpaper/hot?page=";
    $.get(url+page,
        function (data) {
            zopage=Math.ceil(data[data.length-1]/10);
            var c = true;
            for (var i=0;i<data.length-1;i++){
                if (c){
                    $("#a3SpanDiv1").append("<a href='details.html?img="+data[i].id+"'><img src='http://"+theUrl+"/image/"+data[i].storageLocation+"/"+data[i].id+"."+data[i].type+"' class='a3DivImg'></a>")
                    c=false;
                }else {
                    $("#a3SpanDiv2").append("<a href='details.html?img="+data[i].id+"'><img src='http://"+theUrl+"/image/"+data[i].storageLocation+"/"+data[i].id+"."+data[i].type+"' class='a3DivImg'></a>")
                    c=true;
                }
            }
    })
})
$("#a2Span1").click(function (){
    $("#a2Span2").css("border","none")
    $("#a2Span3").css("border","none")
    $("#a2Span4").css("border","none")
    $("#a2Span1").css("border","1px solid #ffffff")
    $("#a3SpanDiv1").html('');
    $("#a3SpanDiv2").html('');
    $("#a4Input").val("下一页");
    url = "/Wallpaper/hot?page=";
    page=1;
    $.get(url+page,
        function (data) {
            zopage=Math.ceil(data[data.length-1]/10);
            var c = true;
            for (var i=0;i<data.length-1;i++){
                if (c){
                    $("#a3SpanDiv1").append("<a href='details.html?img="+data[i].id+"'><img src='http://"+theUrl+"/image/"+data[i].storageLocation+"/"+data[i].id+"."+data[i].type+"' class='a3DivImg'></a>")
                    c=false;
                }else {
                    $("#a3SpanDiv2").append("<a href='details.html?img="+data[i].id+"'><img src='http://"+theUrl+"/image/"+data[i].storageLocation+"/"+data[i].id+"."+data[i].type+"' class='a3DivImg'></a>")
                    c=true;
                }
            }
        })
})//今日热门
$("#a2Span2").click(function (){
    $("#a2Span1").css("border","none")
    $("#a2Span3").css("border","none")
    $("#a2Span4").css("border","none")
    $("#a2Span2").css("border","1px solid #ffffff")
    $("#a3SpanDiv1").html('');
    $("#a3SpanDiv2").html('');
    $("#a4Input").val("下一页");
    url = "/Wallpaper/latest?page=";
    page=1;
    $.get(url+page,
        function (data) {
            zopage=Math.ceil(data[data.length-1]/10);
            var c = true;
            for (var i=0;i<data.length-1;i++){
                if (c){
                    $("#a3SpanDiv1").append("<a href='details.html?img="+data[i].id+"'><img src='http://"+theUrl+"/image/"+data[i].storageLocation+"/"+data[i].id+"."+data[i].type+"' class='a3DivImg'></a>")
                    c=false;
                }else {
                    $("#a3SpanDiv2").append("<a href='details.html?img="+data[i].id+"'><img src='http://"+theUrl+"/image/"+data[i].storageLocation+"/"+data[i].id+"."+data[i].type+"' class='a3DivImg'></a>")
                    c=true;
                }
            }
        })
})//最新上架
$("#a2Span3").click(function (){
    $("#a2Span2").css("border","none")
    $("#a2Span1").css("border","none")
    $("#a2Span4").css("border","none")
    $("#a2Span3").css("border","1px solid #ffffff")
    $("#a3SpanDiv1").html('');
    $("#a3SpanDiv2").html('');
    $("#a4Input").val("下一页");
    url = "/Wallpaper/collection?page=";
    page=1;
    $.get(url+page,
        function (data) {
            zopage=Math.ceil(data[data.length-1]/10);
            var c = true;
            for (var i=0;i<data.length-1;i++){
                if (c){
                    $("#a3SpanDiv1").append("<a href='details.html?img="+data[i].id+"'><img src='http://"+theUrl+"/image/"+data[i].storageLocation+"/"+data[i].id+"."+data[i].type+"' class='a3DivImg'></a>")
                    c=false;
                }else {
                    $("#a3SpanDiv2").append("<a href='details.html?img="+data[i].id+"'><img src='http://"+theUrl+"/image/"+data[i].storageLocation+"/"+data[i].id+"."+data[i].type+"' class='a3DivImg'></a>")
                    c=true;
                }
            }
        })
})//最多收藏
$("#a2Span4").click(function (){
    $("#a2Span2").css("border","none")
    $("#a2Span3").css("border","none")
    $("#a2Span1").css("border","none")
    $("#a2Span4").css("border","1px solid #ffffff")
    $("#a3SpanDiv1").html('');
    $("#a3SpanDiv2").html('');
    $("#a4Input").val("下一页");
    url = "/Wallpaper/praise?page=";
    page=1;
    $.get(url+page,
        function (data) {
            zopage=Math.ceil(data[data.length-1]/10);
            var c = true;
            for (var i=0;i<data.length-1;i++){
                if (c){
                    $("#a3SpanDiv1").append("<a href='details.html?img="+data[i].id+"'><img src='http://"+theUrl+"/image/"+data[i].storageLocation+"/"+data[i].id+"."+data[i].type+"' class='a3DivImg'></a>")
                    c=false;
                }else {
                    $("#a3SpanDiv2").append("<a href='details.html?img="+data[i].id+"'><img src='http://"+theUrl+"/image/"+data[i].storageLocation+"/"+data[i].id+"."+data[i].type+"' class='a3DivImg'></a>")
                    c=true;
                }
            }
        })
})//最多点赞

$("#a4Input").click(function (){
    page=page+1;
    $.get(url+page,
        function (data) {
            zopage=Math.ceil(data[data.length-1]/10);
            var c = true;
            for (var i=0;i<data.length-1;i++){
                if (c){
                    $("#a3SpanDiv1").append("<a href='details.html?img="+data[i].id+"'><img src='http://"+theUrl+"/image/"+data[i].storageLocation+"/"+data[i].id+"."+data[i].type+"' class='a3DivImg'></a>")
                    c=false;
                }else {
                    $("#a3SpanDiv2").append("<a href='details.html?img="+data[i].id+"'><img src='http://"+theUrl+"/image/"+data[i].storageLocation+"/"+data[i].id+"."+data[i].type+"' class='a3DivImg'></a>")
                    c=true;
                }
            }
        })
    if (page>=zopage)$("#a4Input").val("不好意思啊，已经到底啦")
})//下一页