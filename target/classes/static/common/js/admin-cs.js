var wd =1;
var id;//壁纸id
var userId;//作者id
$(function (){
    id = getUrlParam("img");
    $("#a1Span2B1").text('');
    $("#a1Span2B2").text('');
    $.post(openUrl+"/admin/efa0fc9f51224275943c116038fdcd6b",{
        id:getUrlParam("img"),
        uuid:$.cookie("adminUuid")
    },function (data) {
        $("#img1").attr("src","http://"+theUrl+"/image/headPortrait/"+data[0].headPortrait);
        $("#a1Span2B1").text(data[0].theTitle)
        $("#a1Span2B2").text(data[0].theLabel)
        $("#img2").attr("src","http://"+theUrl+"/image/cs/"+data[0].id+"."+data[0].type);
    })
})
$("#input1").click(function (){
    $(".a3Span").hide()
    $("#a3Div1").show()
    Select();
})//审核通过
$("#input2").click(function (){
    $.post(openUrl+"/admin/ec453f2adc63493db651d8a7e7e98191",{
        id:id,
        uuid:$.cookie("adminUuid")
    },function (data){
        if (data){
            alert("成功")
            window.location.href="admin-mobile.html?type=3"
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
    $("#input5").show()
})//内容修改
$("#input4").click(function (){
    $.post(openUrl+"/admin/ccef83e1d2ff455fae16680c906f2239",{
        id:id,
        theTitle:$("#a1input1").val(),
        theLabel:$("#a1input2").val(),
        uuid:$.cookie("adminUuid")
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
    $("#input5").hide()
    $("#a1Span2B1").show()
    $("#a1Span2B2").show()
    $(".a3Span").show()
})//提交修改
$("#input5").click(function (){
    $(".a1Input").hide()
    $("#input4").hide()
    $("#input5").hide()
    $("#a1Span2B1").show()
    $("#a1Span2B2").show()
    $(".a3Span").show()
})//取消修改
$("#img1").click(function (){
    $.cookie("othersId",userId);
    window.location.href="user.html?type=1"
})//点击头像
$("#a3Div1Input1").click(function (){
    $.post(openUrl+"/admin/1e715da537b946cba23fb03828537529",{
        id:id,
        storageLocation:$("#a3Div1Select").val(),
        uuid:$.cookie("adminUuid")
    },function (data){
        if (data){
            alert("成功")
            $("#a3Div1").hide()
            $(".a3Span").show()
            window.location.href="admin-mobile.html?type=3"
        }else {
            alert("失败")
        }
    })
})//审核通过提交
$("#a3Div1Input2").click(function (){
    $("#a3Div1").hide()
    $("#a3Div2").show()
    $("#a3Div2-1").val('');
})//审核通过新增文件夹
$("#a3Div2Input3").click(function (){
    $("#a3Div1").hide()
    $("#a3Div2").show()
    var a = $.trim($("#a3Div2Input1").val());
    var b = $.trim($("#a3Div2Input2").val());
    if (a!=null && a!='' && b!=null && b!= ''){
        $.post(openUrl+"/admin/a88b00a562624a51badbbb509d0e3b92",{
            folder: b,
            note: a,
            uuid:$.cookie("adminUuid")
        },function (data) {
            if (data==1)alert("添加成功");
            else alert("添加失败");
            $("#a3Div2").hide()
            $("#a3Div1").show()
            Select();
        })
    }else {
        alert("字段不能为空!");
    }

})//审核通过新增文件夹提交
$("#a3Div2Input4").click(function (){
    $("#a3Div2").hide()
    $("#a3Div1").show()
})//审核通过新增文件夹取消
function Img(){
    BrowserFullScreen();
    MonitorBrowserResolution();
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
function Select(){
    $("#a3Div1Select").html('');
    $.get(openUrl+"/admin/586c0e7bda874d5fa1749c56963077dc?uuid="+$.cookie("adminUuid"),function (data) {
        for (var i=0;i<data.length;i++){
            $("#a3Div1Select").append("<option value='"+data[i].folder+"'>"+data[i].note+"</option>")
        }
    })
}//获取壁纸文件夹方法