var wd =1;
var id;//壁纸id
var userId;//作者id
$(function (){
    id = getUrlParam("img");
    $("#a1Span2B1").text('');
    $("#a1Span2B2").text('');
    $.post("/admin/efa0fc9f51224275943c116038fdcd6b",{
        id:getUrlParam("img")
    },function (data) {
        $("#img1").attr("src","http://"+theUrl+"/image/headPortrait/"+data[0].headPortrait);
        $("#a1Span2B1").text(data[0].theTitle)
        $("#a1Span2B2").text(data[0].theLabel)
        $("#img2").attr("src","http://"+theUrl+"/image/cs/"+data[0].id+"."+data[0].type);
    })
})
$("#input1").click(function (){
   var a=prompt("请输入存储文件夹","ac");
   if (a != null){
       $.post("/admin/1e715da537b946cba23fb03828537529",{
           id:id,
           storageLocation:a
       },function (data){
           if (data){
               alert("成功")
               window.location.href="admin-login.html"
           }else {
               alert("失败")
           }
       })
   }
})//审核通过
$("#input2").click(function (){
    $.post("/admin/ec453f2adc63493db651d8a7e7e98191",{
        id:id
    },function (data){
        if (data){
            alert("成功")
            window.location.href="admin-login.html"
        }else {
            alert("失败")
        }
    }
    )
})//审核不通过
$("#input3").click(function (){
    $("#a1Span2B1").hide()
    $("#a1Span2B2").hide()
    $(".a3Span").hide()
    $(".a1Input").show()
    $("#a1input1").val($("#a1Span2B1").text())
    $("#a1input2").val($("#a1Span2B2").text())
    $("#input4").show()
})//内容修改
$("#input4").click(function (){
    $.post("/admin/ccef83e1d2ff455fae16680c906f2239",{
        id:id,
        theTitle:$("#a1input1").val(),
        theLabel:$("#a1input2").val()
    },function (data) {
        if (data){
            alert("修改成功")
            $("#a1Span2B1").text($("#a1input1").val())
            $("#a1Span2B2").text($("#a1input2").val())
        }
        else alert("修改失败")
    })
    $(".a1Input").hide()
    $("#input4").hide()
    $("#a1Span2B1").show()
    $("#a1Span2B2").show()
    $(".a3Span").show()
})//提交修改
$("#img1").click(function (){
    $.cookie("othersId",userId);
    window.location.href="user.html?type=1"
})//点击头像


function Img(){
    if (wd==1){
        $("#a1").hide();
        $("#a3").hide();
        $("#a2").css("height","100%")
        wd=2
    }else {
        $("#a2").css("height","70%")
        $("#a1").show();
        $("#a3").show();
        wd=1
    }
}//壁纸全屏方法