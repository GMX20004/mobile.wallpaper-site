var number = 1;
$(function (){
    $("#h1").text("PC端暂未制作，请使用移动端进行访问");
    $("#h3").text("点我全屏显示，点时钟切换样式");
    getCurrentDate(new Date());
    getClockClick();
})
function getCurrentDate(date){
    var hours = date.getHours();
    $(".hour-wrapper").css("transform","rotateZ("+ hours*30+"deg)");
    $(".min-wrapper").css("transform","rotateZ("+ date.getMinutes()*6+"deg)");
    $(".sec-wrapper").css("transform","rotateZ("+ date.getSeconds()*6+"deg)");
    if (hours==1 || hours==11 || hours==12 || hours==13 || hours==23 || hours==0){
        getLuminous("top");
    }else if (hours==2 || hours==3 || hours==4 || hours==14 || hours==15 || hours==16){
        getLuminous("right");
    }else if (hours==5 || hours==6 || hours==7 || hours==17 || hours==18 || hours==19){
        getLuminous("bottom");
    }else{
        getLuminous("left");
    }
    setTimeout("getCurrentDate(new Date());", 1000);
}
function getLuminous(value){
    var tt=document.styleSheets[0];
    tt.insertRule("@-webkit-keyframes  shineRed1 {\n" +
        "        0% {\n" +
        "            -webkit-box-shadow: 0 0 10px #00a67c,\n" +
        "            0 0 20px #00a67c,\n" +
        "            0 0 30px #00a67c,\n" +
        "            0 0 40px #00a67c,\n" +
        "            0 0 70px #00a67c;\n" +
        "            border-"+value+"-color: black;\n" +
        "        }\n" +
        "        25%{\n" +
        "            -webkit-box-shadow: 0 0 7px #00a67c,\n" +
        "            0 0 15px #00a67c,\n" +
        "            0 0 23px #00a67c,\n" +
        "            0 0 30px #00a67c,\n" +
        "            0 0 52px #00a67c;\n" +
        "            border-"+value+"-color: #bbbbbb;\n" +
        "        }\n" +
        "        50%{\n" +
        "            -webkit-box-shadow: 0 0 5px #00a67c,\n" +
        "            0 0 10px #00a67c,\n" +
        "            0 0 15px #00a67c,\n" +
        "            0 0 20px #00a67c,\n" +
        "            0 0 35px #00a67c;\n" +
        "            border-"+value+"-color: #fff;\n" +
        "        }\n" +
        "        75%{\n" +
        "            -webkit-box-shadow: 0 0 7px #00a67c,\n" +
        "            0 0 15px #00a67c,\n" +
        "            0 0 23px #00a67c,\n" +
        "            0 0 30px #00a67c,\n" +
        "            0 0 52px #00a67c;\n" +
        "            border-"+value+"-color: #bbbbbb;\n" +
        "        }\n" +
        "        100% {\n" +
        "            -webkit-box-shadow: 0 0 10px #00a67c,\n" +
        "            0 0 20px #00a67c,\n" +
        "            0 0 30px #00a67c,\n" +
        "            0 0 40px #00a67c,\n" +
        "            0 0 70px #00a67c;\n" +
        "            border-"+value+"-color: black;\n" +
        "        }\n" +
        "    }")
    tt.insertRule("@-webkit-keyframes  shineRed2 {\n" +
        "        0% {\n" +
        "            border-"+value+"-color: black;\n" +
        "        }\n" +
        "        25%{\n" +
        "            border-"+value+"-color: #bbbbbb;\n" +
        "        }\n" +
        "        50%{\n" +
        "            border-"+value+"-color: #fff;\n" +
        "        }\n" +
        "        75%{\n" +
        "            border-"+value+"-color: #bbbbbb;\n" +
        "        }\n" +
        "        100% {\n" +
        "            border-"+value+"-color: black;\n" +
        "        }\n" +
        "    }");
    tt.insertRule("@-webkit-keyframes  shineRed3 {\n" +
        "        0%{transform:rotateZ(0deg);border-right-color: #717171;}\n" +
        "        25%{transform:rotateZ(90deg);border-right-color: #959595;}\n" +
        "        50%{transform:rotateZ(180deg);border-right-color: #c4c4c4;}\n" +
        "        75%{transform:rotateZ(270deg);border-right-color: #959595;}\n" +
        "        100%{transform:rotateZ(360deg);border-right-color: #717171;}\n" +
        "    }"
    );
}
function getClockType(value){
    switch (value){
        case 1:$("#clockBorder").css("animation","shineRed1 8s linear infinite");break;
        case 2:$("#clockBorder").css("animation","shineRed2 8s linear infinite");break;
        case 3:$("#clockBorder").css("animation","shineRed3 12s linear infinite");break;
    }
    return value;
}
function getClockClick(){
    if (number == 1){
        getClockType(1);
        number++;
    }
    else if (number == 2){
        getClockType(2);
        number++;
    }
    else {
        getClockType(3);
        number=1;
    }
}
function getFullScreen(){
    $("#a1").hide();
    $("#a3").hide();
    fullScreen();
}
