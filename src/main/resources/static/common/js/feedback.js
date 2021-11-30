
$("#a2Div3Input1").click(function (){
    $("#a2Div4Input").val('');
    $("#a2Div4Input").show();
})//单选框-是
$("#a2Div3Input2").click(function (){
    $("#a2Div4Input").val('');
    $("#a2Div4Input").hide();
})//单选框否
$("#a2Div2Textarea").mouseleave(function (){
    $("#a2Div2Textarea").css("border","1px solid #e38d13")
})
$("#a2Div5Input").click(function (){
    var type = $("#a2Div1Select").val();
    var describe = $("#a2Div2Textarea").val();
    var is = $("input[name='sf']:checked").val();
    var contact = $("#a2Div4Input").val();
    describe = $.trim(describe);
    if (describe != null && describe != ''){
        $.post("/L/submitFeedback",{
            type:type,
            instructions:describe,
            is:is,
            contact:contact
        },function (data) {
            if (data){
                alert("感谢您提出的宝贵意见，我们会认真对待")
                window.location.href="homePage.html"
            }else {
                alert("反馈失败，请稍后再试或联系管理员")
            }
        })
    }else{
        $("#a2Div2Textarea").css("border","1px solid red")
    }

})//提交按钮
