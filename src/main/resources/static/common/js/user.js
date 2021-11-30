var img;//头像
var name;//昵称
var praise;//获赞数
var fans;//粉丝数
var focusOn;//关注数
var contribute;//投稿数
var sex;//性别
var instructions;//个人说明
var creationTime;//创建时间
var recentLogin;//最近登入时间
var userId; //用户id
$(function (){
    $("#body").css("background-image","url('http://"+theUrl+"/image/background/1.jpg')")
    if(getUrlParam("type")==1){
        if ($.cookie("othersId")==$.cookie("userId")){
            userId=$.cookie("userId")
        }else {
            userId=$.cookie("othersId")
            $("#a1Svg2").hide();
        }
    }
    else userId=$.cookie("userId")
    $.post("/User/user",{
           id:userId
        },
        function(data){
            img = "/image/headPortrait/"+data[0].headPortrait;
            name = data[0].name;
            praise = data[0].praise;
            fans = data[0].fans;
            focusOn = data[0].focusOn;
            contribute = data[0].contribute;
            sex = data[0].sex;
            instructions = data[0].instructions;
            creationTime = data[0].creationTime;
            recentLogin = data[0].recentLogin;
            $("#a2DivImg").attr("src",img)
            $("#a2P1").text(name);
            $("#a31Text1").text(praise);
            $("#a31Text2").text(fans);
            $("#a31Text3").text(focusOn);
            $("#a31Text4").text(contribute);
            $("#a3Text1").text(sex);
            $("#a3Text2").text(instructions);
            $("#a3Text3").text(creationTime);
            $("#a3Text4").text(recentLogin);
        });
    if (getUrlParam("type")==0 || $.cookie("othersId")==$.cookie("userId")){
        $.post("/admin/ab7da92a50e94363a19fb6740b2de54e",{
            id:userId
        },function (data) {
            if (data[0] != null && data[0] != ''){
                for (var i=0;i<data.length;i++){
                    alert(data[i].message)
                }
            }
        })
    }
})//初始化信息获取
$("#a3DivSpan2P2").click(function (){
    $("#a3DivSpan1").css("border","none")
    $("#a3DivSpan2").css("border","solid")
    $("#a3Div3").hide()
    $("#a3Div4").show()
    $("#a3Div4SpanDiv1").html('')
    $("#a3Div4SpanDiv2").html('')
    $.post("/Wallpaper/collectionContribute",{
        userId: userId,
        type: 0
    },
        function(data){
            var c = true;
            for (var i=0;i<data.length;i++){
                if (c){
                    $("#a3Div4SpanDiv1").append("<a href='details.html?img="+data[i].id+"'><img src='http://"+theUrl+"/image/"+data[i].storageLocation+"/"+data[i].id+"."+data[i].type+"' class='a3Div4Img'></a>")
                    c=false;
                }else {
                    $("#a3Div4SpanDiv2").append("<a href='details.html?img="+data[i].id+"'><img src='http://"+theUrl+"/image/"+data[i].storageLocation+"/"+data[i].id+"."+data[i].type+"' class='a3Div4Img'></a>")
                    c=true;
                }
            }
        })
})//收藏界面
$("#a3DivSpan2P1").click(function (){
    $("#a3DivSpan2").css("border","none")
    $("#a3DivSpan1").css("border","solid")
    $("#a3Div4").hide()
    $("#a3Div3").show()
    $("#a3Div4SpanDiv1").html('')
    $("#a3Div4SpanDiv2").html('')
})//个人信息界面
$("#a1Svg2").click(function (){
    $("#a3Div4").hide()
    $("#a3Div3").show()
    $("#a3DivSpan2").css("border","none")
    $("#a3DivSpan1").css("border","solid")
    $("input[type='radio'][name='radio'][value='"+sex+"']").attr("checked",true);
    see()
})//信息修改界面
$("#a1B1").click(function (){
    $("#a3DivSpan2").css("border","none")
    $("#a3DivSpan1").css("border","solid")
    $("#a2DivImg").attr("src",img)
    modify()
})//信息修改界面返回
$("#a3Button").click(function (){
    $("#a2P1").text($("#a2Input").val())
    $("#a3Text2").text($("#a3Input").val())
    $("#a3Text1").text($("input[name='radio']:checked").val())
    modify()
})//信息修改
$("#button").click(function (){
    $("#file").click()
})//修改头像
$("#a3Button").click(function (){
    var name1 = $("#a2Input").val();
    var instructions1 = $("#a3Input").val();
    var sex1 = $("input[name='radio']:checked").val();
    var fileObj = document.getElementById("file").files[0];//可以正常获取文件对象
    var formData = new FormData();
    name1 = $.trim(name1)
    instructions1 = $.trim(instructions1)

    if (fileObj != null){
        if(fileObj.size>2*1024*1024){
            alert("头像大于2M")
        }else {
            formData.append("file", fileObj);
        }
    }
    formData.append("userId", $.cookie("userId"));
    if (name1 !=null && name1 != '')
    formData.append("name", name1);
    if (instructions1 !=null && instructions1 != '')
    formData.append("instructions", instructions1);
    formData.append("sex", sex1);
    $.ajax({
        url: "/User/userModify",
        type: 'POST',
        cache: false,
        data: formData,
        processData: false,
        contentType: false
    }).done(function(res) {
        if (res){
            alert("上传成功！感谢您的分享")
        } else alert("上传失败,请稍后在试或联系管理员")
    }).fail(function(res) {
    });
})//修改个人信息
$("#file").change(function(){
    $("#a2DivImg").attr("src",URL.createObjectURL($(this)[0].files[0]));
});//修改头像显示
function modify() {
    $("#a1B1").hide()
    $("#a2Input").hide()
    $("#button").hide()
    $("#a3Button").hide()
    $(".a3Div3H1").hide()
    $("#a1Svg2").show()
    $("#a2P1").show()
    $("#a3Div2").show()
    $(".a3Div1Span1").show()
    $("#a3Div3H1").show()
    $(".a3Div3H").show()
}//信息修改界面返回方法
function see() {
    $("#a1Svg2").hide()
    $("#a2P1").hide()
    $("#a3Div2").hide()
    $(".a3Div1Span1").hide()
    $("#a3Div3H1").hide()
    $(".a3Div3H").hide()
    $("#a1B1").show()
    $("#a2Input").attr("value",$("#a2P1").text())
    $("#a2Input").show()
    $("#button").show()
    $("#a3Button").show()
    $(".a3Div3H1").show()
}//进入信息修改界面
