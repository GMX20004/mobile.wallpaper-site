var diviceWidth = document.documentElement.clientWidth;
var diviceHeight = document.documentElement.clientHeight;
// 阿里云
// var theUrl = "8.136.185.236:80"
// 本地
// var theUrl ="localhost:8080"
// 腾讯云
var theUrl ="101.43.88.137:80"
//本地
// var openUrl = ""
//远程链接服务器端口
// var openUrl = "http://8.136.185.236"
var openUrl = ""
$(function (){
    $("#main").width(diviceWidth)
    $("#main").height(diviceHeight)
})
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
}

