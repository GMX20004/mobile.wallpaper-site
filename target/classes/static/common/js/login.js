var time =60;
var Email;
var Type;
var yzm;
$("#button1").click(function (){
    $("#body").css("background-image","url('http://"+theUrl+"/image/background/1.jpg')")
    var is = true;
    var email = $("#input1").val();
    var password = $("#input2").val();
    email = $.trim(email);
    password = $.trim(password);
   if (email==null || email==''){
       $("#input1").css("border","1px solid red");
        is = false;
   }
   if (password==null || password==''){
       $("#input2").css("border","1px solid red");
       is = false;
   }
   if (is==true){
       $.post("/User/logInTo",{
               email:email,
               password:password
           },
            function(data){
               if (data[0]==false){
                   alert("账号或密码错误")
               }else{
                   if (data[0].state==1){
                       alert("您已被列入黑名单,详情请联系管理员")
                   }else{
                       $.cookie("userId",data[0].id,{ expires: 7, path: '/' })
                       window.location.href="user.html?type=0"
                   }
               }
           });
   }
})//登录
$("#input1").mouseleave(function (){
    $("#input1").css("border","1px solid #f0ad4e");
})//邮件输入框失去焦点是边框颜色变化
$("#input2").mouseleave(function (){
    $("#input2").css("border","1px solid #f0ad4e");
})//密码输入框失去焦点是边框颜色变化
$("#a3Div1P1").click(function (){
    register()
    $(".a2B").text("立\xa0即\xa0注\xa0册")
    Type=0;
})//立即注册跳转
$("#a3Div1P2").click(function (){
    register()
    $(".a2B").text("忘\xa0记\xa0密\xa0码")
    Type=1;
})//忘记密码跳转
$("#a3Div1P3").click(function (){
   login()
})//登录跳转
$("#a3Div1Button1").click(function (){
    var reg = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
    var val = $("#a3Div1input1").val();
    val = $.trim(val);
    if (val != null && val != ''){
        if (!reg.test(val)){
            $("#a3Div1input1").css("border","1px solid red")
        }
    }else {
        $("#a3Div1input1").css("border","1px solid red")
    }
    $.get("/L/RandomNumber",function (data){
        yzm=data
        countdown();
        $.post("/L/mail",{
            Email:val,
            Type:2,
            yzm:data
        },function (data2) {
            if (data2){
                Email = val;

            }else {
                alert("发送失败，请稍后在试或联系管理员处理")
            }
        })
    })
})//获取验证码
$("#a3Div1Button2").click(function (){
    var is = true;
    var pan = /^[a-zA-Z0-9]+$/;
    var input1 = $("#a3Div1input1").val();
    var input2 = $("#a3Div1input2").val();
    var input3 = $("#a3Div1input3").val();
    var input4 = $("#a3Div1input4").val();
    input1 = $.trim(input1);//邮箱
    input2 = $.trim(input2);//验证码
    input3 = $.trim(input3);//密码
    input4 = $.trim(input4);//重复密码
    if (input1 != Email){
        is =false;
        $("#a3Div1input1").css("border","1px solid red");
    }
    if (input2 != yzm){
        is =false;
        $("#a3Div1input2").css("border","1px solid red");
    }
    if (input3 =='' || input3==null){
        is =false;
        $("#a3Div1input3").css("border","1px solid red");
    }
    if (!pan.test(input3)){
        is =false;
        $("#a3Div1input3").css("border","1px solid red");
    }
    if (input3.length<6){
        is =false;
        $("#a3Div1input3").css("border","1px solid red");
    }
    if (input4 != input3){
        is =false;
        $("#a3Div1input4").css("border","1px solid red");
    }
    if (is){
        if (Type==0){
            $.post("/User/registered",{
                email:input1,
                password:input4
            },function (data) {
                if (data[0]==true){
                    User(input1,input4)
                    SuccessfulEmail(input1)
                }else {
                    alert(data[1])
                    yzm=asd156wad84148w6a4d13wa6d84aw531f6w6a84;
                    register()
                }
            })
        }else{
            $.post("/User/forgetPassword",{
                email:input1,
                password:input4
            },function (data) {
                if (data[0]==true){
                    User(input1,input4)
                }else {
                    alert(data[1])
                    yzm=asd156wad84148w6a4d13wa6d84aw531f6w6a84;
                    register()
                }
            })
        }
    }
})//提交按钮
$("#a3Div1input1").mouseleave(function (){
    $("#a3Div1input1").css("border","1px solid #f0ad4e")
})//邮箱框边框颜色变换
$("#a3Div1input2").mouseleave(function (){
    $("#a3Div1input2").css("border","1px solid #f0ad4e")
})//验证码框边框颜色变换
$("#a3Div1input3").mouseleave(function (){
    $("#a3Div1input3").css("border","1px solid #f0ad4e")
})//密码框边框颜色变换
$("#a3Div1input4").mouseleave(function (){
    $("#a3Div1input4").css("border","1px solid #f0ad4e")
})//重复密码码框边框颜色变换

function register() {
    $("#a3Div1input1").val("");
    $("#a3Div1input2").val("");
    $("#a3Div1input3").val("");
    $("#a3Div1input4").val("");
    $(".a3Div1Div1").hide();
    $("#a3Div1P").hide();
    $("#a3").css("padding-top","5%")
    $(".a3Div1Div").show();
    $("#a3Div1P3").show();
    $(".a2B").css("font-size","60px")
}//切换立即注册或忘记密码方法
function login(){
    $(".a3Div1Div").hide();
    $("#a3Div1P3").hide();
    $("#a3").css("padding-top","20%")
    $(".a3Div1Div1").show();
    $("#a3Div1P").show();
    $(".a2B").css("font-size","80px")
    $(".a2B").text("登\xa0录")
}//跳转登录界面
function countdown(){
    var time1=setInterval(function(){
        if(time==0){
            $("#a3Div1Button1").val("重新发送");
            $("#a3Div1Button1").removeAttr("disabled");
            time=60;
            clearInterval(time1);
        }else{
            $("#a3Div1Button1").attr("disabled","true");
            $("#a3Div1Button1").val("重新发送("+time+")");
            time--;
        }
    }, 1000);
}//验证码倒计时方法
function User(email,password){
    $.post("/User/logInTo",{
        email:email,
        password:password
    },function (data) {
        if (data[0]==false){
            alert("跳转登录失败!请自主登录")
            login()
        }else {
            $.cookie("userId",data[0].id,{ expires: 7, path: '/' })
            alert("已为您自动登录")
            window.location.href="user.html?type=0"
        }
    })
}//自动登录
function SuccessfulEmail(email){
    $.post("/L/mail",{
        Email:email,
        Type:1,
        yzm:1
    })
}//成功邮件
