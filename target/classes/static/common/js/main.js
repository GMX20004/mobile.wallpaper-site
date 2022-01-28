var diviceWidth = document.documentElement.clientWidth;
var diviceHeight = document.documentElement.clientHeight;
// 腾讯云
var theUrl ="localhost:80"
//本地
var openUrl = ""
//远程链接服务器端口
// var openUrl = "http://8.136.185.236"
$(function (){
    $("#main").width(diviceWidth)
    $("#main").height(diviceHeight)
})
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
}
function BrowserFullScreen(){
    var element = document.documentElement;		// 返回 html dom 中的root 节点 <html>
    if(!$('body').hasClass('full-screen')) {
        $('body').addClass('full-screen');
        $('#alarm-fullscreen-toggler').addClass('active');
        // 判断浏览器设备类型
        if(element.requestFullscreen) {
            element.requestFullscreen();
        } else if (element.mozRequestFullScreen){	// 兼容火狐
            element.mozRequestFullScreen();
        } else if(element.webkitRequestFullscreen) {	// 兼容谷歌
            element.webkitRequestFullscreen();
        } else if (element.msRequestFullscreen) {	// 兼容IE
            element.msRequestFullscreen();
        }
    } else {			// 退出全屏
        console.log(document);
        $('body').removeClass('full-screen');
        $('#alarm-fullscreen-toggler').removeClass('active');
        //	退出全屏
        if(document.exitFullscreen) {
            document.exitFullscreen();
        } else if (document.mozCancelFullScreen) {
            document.mozCancelFullScreen();
        } else if (document.webkitCancelFullScreen) {
            document.webkitCancelFullScreen();
        } else if (document.msExitFullscreen) {
            document.msExitFullscreen();
        }
    }
}

