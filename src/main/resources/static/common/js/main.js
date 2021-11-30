var diviceWidth = document.documentElement.clientWidth;
var diviceHeight = document.documentElement.clientHeight;
var theUrl = "www.gxm2000.cn"
// var theUrl ="localhost:8080"
$(function (){
    $("#main").width(diviceWidth)
    $("#main").height(diviceHeight)
})
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
}

