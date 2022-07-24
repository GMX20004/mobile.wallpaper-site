
$("#a99a1").click(function (){
    alert("QQ:1478588530");
})

$("#backImg").click(function (){
    if (getUrlParam("source")=="login")
        window.history.go(-2);
    else
        window.history.go(-1);
})