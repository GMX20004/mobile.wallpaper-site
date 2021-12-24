
$(function (){
    $("#body").css("background-image","url('http://"+theUrl+"/image/background/1.jpg')")
})

$("#file").val("")
$("#a2Div").click(function (){
    $("#file").click()
})//选择图片

$("#file").change(function(){
    $("#a2Div").hide()
    $(".a2Span").show()
    $("#img").attr("src",URL.createObjectURL($(this)[0].files[0]));
});//显示上传图片样式

$("#a2Svg").click(function (){
    $(".a2Span").hide()
    $("#a2Div").show()
    $("#file").val("");
    $("#a3Input1").val('');
    $("#a3Input2").val('');
})//取消上传图片
$("#button").click(function (){
   var a = $("#a3Input2").val()
    a = $.trim(a);
   if ($.cookie("userId")==null) window.location.href="login.html"
   else
       if ($("#file").val()==null || $("#file").val()=="") alert("上传图片为空")
       else
           if (a == null || a == "") $("#a3Input2").css("border","1px solid red")
            else upImg();
})//上传按钮
$("#a3Input2").mouseleave(function (){
    $("#a3Input2").css("border","1px solid black")
})
function upImg() {
    var fileObj = $('#file')[0].files[0];//可以正常获取文件对象
    if(!fileObj.type.match('image.*')){
        alert("请选择正确的图片");
    }else if(fileObj.size>5*1024*1024){
        alert("文件大小大于5M")
    }else{
        $("#button").val("上传中...")
        $("#button").css("background-color","#8c8c8c")
        $("#button").attr("disabled","false")
        var formData = new FormData();
        formData.append('file', fileObj);
        formData.append("userId",$.cookie("userId"));
        formData.append("theTitle",$("#a3Input1").val());
        formData.append("theLabel",$("#a3Input2").val());
        formData.append('size',fileObj.size);
        $.ajax({
            url: "/L/img",
            type: 'POST',
            cache: false,
            data: formData,
            processData: false,
            contentType: false
        }).done(function(res) {
            if (res){
                alert("上传成功！感谢您的分享")
                $("#a2Svg").click()
            } else alert("上传失败,请稍后在试或联系管理员")
            $("#button").val("上传")
            $("#button").css("background-color","#e38d13")
            $("#button").removeAttr("disabled")
        }).fail(function(res) {
        });
    }
}//图片上传方法