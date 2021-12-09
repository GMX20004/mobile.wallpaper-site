var page =1;
var url = "/video/viewList?page="
$(function (){
    $("#a2Div1").html('');
    $("#a2Input").removeAttr("disabled");
    $("#a2Input").val("下一页");
    Video();
})
$("#a2Input").click(function (){

})//下一页
function Video(){
    $.get(url+page,function (data) {
        for (var i=0;i<data.length-1;i++){
            $("#a2Div1").append("<embed src='http://"+theUrl+"/image/"+data[i].storageLocation+"/"+data[i].id+"."+data[i].type+"'>");
        }
        if (data[data.length-1]){
            $("#a2Input").attr("disabled","true");
            $("#a2Input").val("不好意思啊，已经到底啦");
        }
        console.log(data,data.length)
    })
}//获取视频方法