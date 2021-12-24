
$("#a99a1").click(function (){
    $("#window").show();
})

$("#windowInput").click(function (){
    $("#window").hide();
})

$("#backImg").click(function (){
    if (getUrlParam("source")=="login")
        window.history.go(-2);
    else
        window.history.go(-1);
})