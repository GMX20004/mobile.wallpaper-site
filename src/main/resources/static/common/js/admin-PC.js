$(function (){
    $("#a2S1D1").click()
});
$("#a2S1D1").click(function (){
    switchLabel("#a2S1D1","#a2S2D1");
    $('#container').highcharts({
        credits:{
            text: 'GXM',
            href: 'http://www.gxm2000.cn',
            enabled: false
        },
        exporting:{
            enabled: false
        },
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false
        },
        title: {
            text: '壁纸和审核壁纸数量',
            enabled: false
        },
        tooltip: {
            headerFormat: '{series.name}<br>',
            pointFormat: '{point.name}: <b>{point.percentage:.1f}%</b>'
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: true,
                    // 通过 format 或 formatter 来格式化数据标签显示
                    //format: '值: {point.y} 占比 {point.percentage} %',
                    formatter: function() {
                        //this 为当前的点（扇区）对象，可以通过  console.log(this) 来查看详细信息
                        return '<span style="color: ' + this.point.color + '">' + this.y +'</span>';
                    }
                },
                showInLegend: true  // 显示在图例中
            }
        },
        series: [{
            type: 'pie',
            name: '浏览器访问量占比',
            data: [
                ['过审壁纸',   45.0],
                ['审核壁纸',       26.8],
            ]
        }]
    });
})
function switchLabel(name,value){
    $("#a2S2D1").hide();
    $("#a2S2D2").hide();
    $("#a2S2D3").hide();
    $("#a2S2D4").hide();
    $("#a2S1D1").css({"background-color":"#ffffff","color":"#000000"});
    $("#a2S1D2").css({"background-color":"#ffffff","color":"#000000"});
    $("#a2S1D3").css({"background-color":"#ffffff","color":"#000000"});
    $("#a2S1D4").css({"background-color":"#ffffff","color":"#000000"});
    $(name).css({"background-color":"#1A58E8","color":"#ffffff"});
    $(value).show();
}
