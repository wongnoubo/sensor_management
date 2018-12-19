var myChart = echarts.init(document.getElementById('temp'));

time=[],mq2=[],temp=[],all=[];

myChart.setOption({
    title:{
        text:'温度'
    },
    tooltip:{
        // show:true
    },
    legend:{
        data:['温度']
    },
    xAxis: [{
        type:'category',
        data:[]
    }],
    yAxis: [{
        type:'value'
    }],
    series: [{
        name:"温度",
        type:"line",
        data:mq2,
        smooth:true
    }]
});

myChart.showLoading();

function arrTest() {
    $.ajax({
        type:"post",
        async:false,
        url:"ajax.php",
        data:{},
        dataType: "json",
        success:function (result) {
            if(result){
                for(let i=0; i<result.length; i++) {
                    time.push(result[i].time);
                    // mq2.push(result[i].flame);
                    temp.push(result[i].temp);

                }
                for(let i=0;i<result.length;i++){
                    all.push(result[result.length-1].temp);
                }
                myChart.hideLoading();
                myChart.setOption({
                    xAxis: {
                        data:time
                    },
                    series: [{
                        name:'温度',
                        data:temp
                    }]
                })
            }
        },
        error:function () {
            alert("请求数据失败");
            myChart.hideLoading();
        }
    });
    //return id,mq2;
}

arrTest();







