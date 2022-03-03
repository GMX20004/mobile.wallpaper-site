var ImgFile= {};
var title = {};
var label = {};
var size = {};
var countID = 0;
var count = 0;
function fileAdd(){
    $("#file").click()
}//选择图片方法
$("#file").change(function(){
    $("#a1Div").hide();
    $("#a2").show();
    $("#subsequentAdd").remove();
    $.each($(file.files),function (index,val){
        var reader = new FileReader();
        reader.readAsDataURL(val);
        reader.onload = function (){
            size[countID] = val.size;
            ImgFile[countID] = val;
            $("#a1").append('<span id="a1Span'+countID+'" class="a1Span">\n' +
                '               <div class="a1Img">\n' +
                '                   <img class="img" src="'+this.result+'">\n' +
                '               </div>\n' +
                '               <div class="a1Content">\n' +
                '                   <span class="a1ContentSpan1">\n' +
                '                       <input placeholder="标题" onmouseleave="titleAdd('+countID+',this.value)">\n' +
                '                       <input placeholder="标签" onmouseleave="labelAdd('+countID+',this.value)">\n' +
                '                   </span>\n' +
                '                   <span class="a1ContentSpan2">\n' +
                '                       <input type="button" value="删除" onclick="fileDelete('+countID+')">\n' +
                '                   </span>\n' +
                '               </div>\n' +
                '           </span>')
            countID++;
            count++;
            if (index === file.files.length-1){
                $("#a1").append('<span id="subsequentAdd" class="a1Span">\n' +
                    '               <div class="a1Img">\n' +
                    '                   <img onclick="fileAdd()" class="img" src="../common/img/icon/add.svg">\n' +
                    '               </div>\n' +
                    '           </span>')
            }
        }
    })
});//显示上传图片样式
function fileDelete(index){
    ImgFile[index] = null;
    $("#a1Span"+index).remove();
    count--;
    if (count===0){
        reset()
    }
}//删除图片上传
function titleAdd(index,val){
    title[index] = val;
}//添加标题
function labelAdd(index,val){
    label[index] = val;
}//添加标签
function upload(){
    $("#upload").hide();
    $("#progress").show();
    var formData = new FormData();
    for (var i=0;i<countID;i++){
        formData.append('file', ImgFile[i]);
        formData.append("theTitle",title[i]);
        formData.append("theLabel",label[i]);
        formData.append('size',size[i]);
    }
    formData.append("userId",1);
    jQuery.ajax({
        url: openUrl+"/admin/img",
        type: 'POST',
        cache: false,
        data: formData,
        processData: false,
        contentType: false,
        xhr:function (){
            var xhr = new XMLHttpRequest();
            xhr.upload.addEventListener('progress', function (e) {
                var progressRate = (e.loaded / e.total) * 100 + '%';
                $('#progress div').css('width', progressRate);
            })
            return xhr;
        }
    }).done(function(res) {
        if (res){
            alert("上传成功！感谢您的分享")
            reset();
            $("#upload").show();
            $("#progress").hide();
        } else{
            alert("上传失败,请稍后在试或联系管理员");
            $("#progress").hide();
            $('#progress div').css('width', "0px");
            $("#upload").show();
        }
    });
}//上传按钮
function reset(){
    ImgFile = {};
    title = {};
    label = {};
    size = {};
    countID = 0;
    $("#a1Div").show();
    $("#subsequentAdd").remove();
    $("#a2").hide();
    $("#a1 span").remove();
    $('#progress div').css('width', "0px");
}//重置