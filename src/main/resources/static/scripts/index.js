$(function () {
    initUserChart();
    initRelationChart();
    initCounts();
});

function initUserChart() {
    /*调用echarts*/
    $.ajax({
        type: "GET",
        url: basePath + "/system/user/count",
        success: function (result) {
            var dataBeast = result.dataBeast;
            var xAxisData = result.xAxisData;
            exampleUserTop("exampleChart", dataBeast, xAxisData);
        }
    });
}

function initRelationChart() {
    $.ajax({
        type: "GET",
        url: basePath + "/system/relation",
        success: function (data) {
            relation(data.data, "relationChart")
        }
    });
}

function initCounts() {
    $.ajax({
        type: "GET",
        url: basePath + "/system/info",
        success: function (data) {
            var result = data.data;
            $("#studentNum").html(result.student);
            $("#facultyNum").html(result.faculty);
            $("#courseNum").html(result.course);
        }
    });
}

function exampleUserTop(divUrl, data1, data2) {
    var myChart = echarts.init(document.getElementById(divUrl));
    var option = {
        // title : {
        //     text: 'Pioneer User Chart',
        //     subtext: 'Realtime',
        //     x:'center'
        // },
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            x: 'center',
            y: 'bottom',
            data: ['1', '2', '3', '4', '5', '6', '7', '8']
        },
        toolbox: {
            show: true,
            feature: {
                mark: {show: true},
                dataView: {show: true, readOnly: false},
                magicType: {
                    show: true,
                    type: ['pie', 'funnel']
                },
                restore: {show: true},
                saveAsImage: {show: true}
            }
        },
        calculable: true,
        series: [
            {
                name: 'User Counts',
                type: 'pie',
                radius: [20, 135],
                center: ['25%', '50%'],
                roseType: 'radius',
                label: {
                    normal: {
                        show: true
                    },
                    emphasis: {
                        show: true
                    }
                },
                lableLine: {
                    normal: {
                        show: true
                    },
                    emphasis: {
                        show: true
                    }
                },
                data: data1
            },
            {
                name: 'Gender Counts',
                type: 'pie',
                radius: [30, 135],
                center: ['75%', '50%'],
                roseType: 'radius',
                data: data2
            }
        ]
    };

    myChart.setOption(option);
    return myChart;
}

function relation(data, divUrl) {
    var myChart = echarts.init(document.getElementById(divUrl));
    myChart.showLoading();
    if (data !== "") {
        myChart.hideLoading();

        var graph = echarts.dataTool.gexf.parse(data);
        var categories = [{name: '课程'}, {name: '学生'}, {name: '教师'}];

        graph.nodes.forEach(function (node) {
            node.itemStyle = null;
            node.value = (node.symbolSize - 10) / 5;
            node.label = {
                normal: {
                    show: node.symbolSize > 10
                }
            };
            node.category = node.attributes.type;
        });
        var option = {
            // title: {
            //     text: 'Pioneer SFC Relations',
            //     subtext: 'Student-Faculty-Course',
            //     top: 'bottom',
            //     left: 'right'
            // },
            tooltip: {},
            legend: [{
                // selectedMode: 'single',
                data: categories.map(function (a) {
                    return a.name;
                })
            }],
            toolbox: {
                show: true,
                feature: {
                    mark: {show: true},
                    dataView: {show: true, readOnly: false},
                    magicType: {
                        show: true,
                        type: ['pie', 'funnel']
                    },
                    restore: {show: true},
                    saveAsImage: {show: true}
                }
            },
            animationDurationUpdate: 1500,
            animationEasingUpdate: 'quinticInOut',
            series: [
                {

                    type: 'graph',
                    layout: 'circular',
                    circular: {
                        rotateLabel: true
                    },
                    data: graph.nodes,
                    links: graph.links,
                    categories: categories,
                    roam: false,
                    label: {
                        normal: {
                            position: 'right',
                            formatter: '{b}'
                        }
                    },
                    lineStyle: {
                        normal: {
                            color: 'source',
                            curveness: 0.3
                        }
                    }
                }
            ]
        };
        myChart.setOption(option);
    }
}
