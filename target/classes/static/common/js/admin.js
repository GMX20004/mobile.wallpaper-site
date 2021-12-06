var userId;
$(function (){
    $("#body").css("background-image","url('http://"+theUrl+"/image/Websitebackground/admin.jpg')")
    $.post("/admin/443139dfab264464a40e7f7425588469",{
        email:0,
        uuid:$.cookie("adminUuid")
    },function (data) {
        if (data){
            $("#login").hide();
            $("#main").show();
            $("#a1Span1").click();
        }else {
            $("#main").hide();
            $("#login").show();
        }
    })
})
$("#loginPassword").keyup(function (event){
    if (event.keyCode==13){
        var password = $.trim($("#loginPassword").val())
        $.post("/admin/e4c984df9f364376992066fd393d89fe",{
            password:password
        },function (data) {
            if (data[0]!=false){
                $.cookie("adminUuid",data[1]);
                $("#login").hide();
                $("#main").show();
                $("#a1Span1").click();
            }else {
                alert("密码错误");
            }
        })
    }
})//admin登入
$("#a1Span1").click(function (){
    borderHide();
    $("#a1Span1").css("border","1px solid #e38d13");
    $("#a0").show();
    $.get("/L/number",function (data) {
        $("#a0B1").text(data[0].userNumber);
        $("#a0B2").text(data[0].wallpaperNumber);
        $("#a0B3").text(data[0].testWallpaperNumber);
        $("#a0B4").text(data[0].feedbackNumber);
    })
})//首页
$("#a1Span2").click(function (){
    borderHide();
    $("#a1Span2").css("border","1px solid #e38d13");
    $(".UserDiv").remove()
    $("#a2").show();
    $.get("/admin/c896d9988afd44939906b45e8703df3a",function (data) {
        for (var i=0;i<data.length;i++){
            $("#a2Div2").append("<div class='UserDiv'>" +
                "<span class=\"UserSpan0\">"+data[i].id+"</span>\n" +
                "<span class=\"UserSpan1\">"+data[i].email+"</span>\n" +
                "<span class=\"UserSpan1\">"+data[i].name+"</span>\n" +
                "<span class=\"UserSpan3\">"+data[i].instructions+"</span>\n" +
                "<span class=\"UserSpan0\">"+data[i].sex+"</span>\n" +
                "<span class=\"UserSpan0\">"+data[i].praise+"</span>\n" +
                "<span class=\"UserSpan0\">"+data[i].fans+"</span>\n" +
                "<span class=\"UserSpan0\">"+data[i].focusOn+"</span>\n" +
                "<span class=\"UserSpan0\">"+data[i].contribute+"</span>\n" +
                "<span class=\"UserSpan2\">"+data[i].recentLogin+"</span>\n" +
                "<span class=\"UserSpan2\">"+data[i].creationTime+"</span>\n" +
                "<span class=\"UserSpan0\">"+data[i].start+"</span>\n" +
                "<span class=\"UserSpan0\">"+data[i].headPortrait+"</span>\n" +
                "<span class=\"UserSpan0\"><input type=\"button\"onclick=\"message("+data[i].id+")\"value=\"留言\"class=\"UserInput\"></span>"+
                "</div>")
        }
    })
})//用户
$("#a1Span3").click(function (){
    borderHide();
    $("#a1Span3").css("border","1px solid #e38d13");
    $(".a3Img").remove()
    $("#a3").show();
    $.get("/admin/0529588ecb8d4246bc0dc5302643b62d",function (data){
        var c = true;
        for (var i=0;i<data.length;i++){
            if (c){
                $("#a3Div1").append("<a href='admin-cs.html?img="+data[i].id+"'><img src='http://"+theUrl+"/image/cs/"+data[i].id+"."+data[i].type+"' class='a3Img'></a>")
                c=false;
            }else {
                $("#a3Div2").append("<a href='admin-cs.html?img="+data[i].id+"'><img src='http://"+theUrl+"/image/cs/"+data[i].id+"."+data[i].type+"' class='a3Img'></a>")
                c=true;
            }
        }
    })
})//测试壁纸
$("#a1Span4").click(function (){
    borderHide();
    $("#a1Span4").css("border","1px solid #e38d13");
    $(".a4DivD").remove()
    $("#a4").show();
    $.get("/admin/f91bcfccb27d4f02ac249733e495d518",function (data) {
        for (var i=0;i<data.length;i++){
            $("#a4Div1").append("<div class=\"a4DivD\">\n" +
                "                <span class=\"a4Span0\">"+data[i].id+"</span>\n" +
                "                <span class=\"a4Span1\">"+data[i].type+"</span>\n" +
                "                <span class=\"a4Span3\">"+data[i].instructions+"</span>\n" +
                "                <span class=\"a4Span0\">"+data[i].is+"</span>\n" +
                "                <span class=\"a4Span2\">"+data[i].contact+"</span>\n" +
                "                <span class=\"a4Span1\"><input type=\"button\"onclick=\"Delete("+data[i].id+")\"value=\"删除\"class=\"a4Input\"></span>\n" +
                "            </div>")
        }
    })
})//用户反馈
$("#a0Input").click(function (){
    $.get("/admin/576f7da7bc264e63a923bfa16d0f133d",function (data) {
        if (data){
            alert("更新成功")
        }else {
            alert("更新失败")
        }
    })
})//每日壁纸显示更新按钮
$("#a5Input").click(function (){
 $.post("/L/sendAMessage",{
     message:$("#a5Text").val(),
     accept:userId,
     send:0,
     level:1
 },function (data) {
     if (data) alert("发送成功")
     else alert("发送失败")
 })
})//提交发送消息按钮
function borderHide(){
    $("#a1Span1").css("border","none")
    $("#a1Span2").css("border","none")
    $("#a1Span3").css("border","none")
    $("#a1Span4").css("border","none")
    $("#a0").hide();
    $("#a2").hide();
    $("#a3").hide();
    $("#a4").hide();
    $("#a5").hide();
}//边框消失和内容隐藏方法
function message(id){
    borderHide();
    $("#a5").show();
    $("#a5Text").text('管理员:')
    userId=id;
}//点击发送消息方法
function Delete(id){
    $.post("/admin/90029510feae426aaa31c8560d4ee6a2",{
        id:id
    },function (data) {
        if (data==false)alert("失败")
    })
    $("#a1Span4").click()
}//反馈消息删除方法