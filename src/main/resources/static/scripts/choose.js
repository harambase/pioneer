let chooseVue = new Vue({
    el: "#choosePage",
    data: {
        pin: "",
        pinValidate: false,
        tol_credits: 0,
        use_credits: 0,
        ava_credits: 0,
        counter: 0,
        crnList: [],
        worksheet: ""
    },
    mounted: function () {
        initPin();
    },
    methods: {

        reset: function () {
            this.worksheet = "";
            initStudentInfo();
        },

        submit: function () {

            let choiceList = [];
            if (this.crnList.length === 0) {
                Showbo.Msg.alert("没有选择任何课程!", function () {
                });
                return;
            }
            for (let i = 0; i < this.crnList.length; i++) {
                let newId = "input_" + this.crnList[i];
                let input = document.getElementById(newId);
                if (input !== null) {
                    choiceList.push(this.crnList[i]);
                }
            }
            //todo: 数组发送问题
            axios.post('/course/choose', choiceList).then(function (response) {
                let failList = response.data.data.failList;
                if (failList.length === 0)
                    Showbo.Msg.alert("全部注册成功!", function () {
                        this.worksheet = "";
                        initStudentInfo();
                    });
                else {
                    let html = '<table style="text-align: left">';
                    for (let i = 0; i < failList.length; i++) {
                        html += '<tr><td>' + failList[i] + '</td></tr>';
                    }
                    let input = '<p style="color: red">课程注册失败详情</p>' + html + '</table>';
                    Showbo.Msg.show({
                        buttons: {yes: '确定'}, msg: input, title: '注意', fn: function () {
                            this.worksheet = "";
                            initStudentInfo();
                        }
                    });
                }
            });
        }
    }
});

function initPin() {
    axios.get('/pin/session').then(function (response) {
        if (response.data.code === 2001) {
            initStudentInfo();
            courseTable.draw();
            chooseVue.$data.pinValidate = true;
        }
    })
}

function validate() {
    axios.get('/pin/' + chooseVue.$data.pin).then(function (response) {
        if (response.data.code === 2001) {
            initStudentInfo();
            courseTable.draw();
            chooseVue.$data.pinValidate = true;
        } else
            Showbo.Msg.alert("验证失败!", function () {
            });
    })
}

function initStudentInfo() {
    axios.get('/user/current').then(function (response) {
        initStudent(response.data.data.userId);
    });

    function initStudent(studentId) {
        axios.get("/student/" + studentId + "/available/credit").then(function (response) {
            if (response.data.code === 2001) {
                chooseVue.$data.tol_credits = response.data.data.tol_credits;
                chooseVue.$data.use_credits = response.data.data.use_credits;
                chooseVue.$data.ava_credits = response.data.data.ava_credits;
            } else
                Showbo.Msg.alert("获取学生信息失败!", function () {
                });
        });
    }
}

function showInfo(crn) {
    window.location.href = basePath + "/course/view?crn=" + crn;
}

function addToWorkSheet(crn, credits) {

    function isAvaCreditsEnough(credits) {
        return (chooseVue.$data.tol_credits - chooseVue.$data.use_credits - credits) >= 0;
    }

    function isSelectAgain(crn) {
        let newId = "input_" + crn;
        let input = document.getElementById(newId);
        return input !== null; //true:again, false:not again
    }

    if (!isAvaCreditsEnough(credits)) {
        Showbo.Msg.alert("学分不足!", function () {
        });
        return;
    }
    if (isSelectAgain(crn)) {
        Showbo.Msg.alert("不可重复选!", function () {
        });
        return;
    }
    chooseVue.$data.counter++;
    chooseVue.$data.crnList.push(crn);

    chooseVue.$data.worksheet = chooseVue.$data.worksheet +
        '<div id="form_' + crn + '" class="form-group">' +
        '   <div class="col-sm-1">' +
        '       <i id="remove_' + crn + '" class="fa fa-minus-circle fa-2x" style="color: red; cursor: pointer; margin-top: 3px;" ' +
        '          onclick="removeFromWorkSheet(\'' + crn + '\',\'' + credits + '\')"></i>' +
        '   </div>' +
        '   <div class="col-sm-4">' +
        '       <label for="input_' + crn + '" class="control-label">已选课程:</label>' +
        '   </div>' +
        '   <div class="col-sm-6">' +
        '        <input name="course_choose" id="input_' + crn + '" class="form-control" value="' + crn + '" disabled/>' +
        '   </div>' +
        '</div>';

    chooseVue.$data.use_credits += parseInt(credits);
    chooseVue.$data.ava_credits = chooseVue.$data.tol_credits - chooseVue.$data.use_credits;
}

function removeFromWorkSheet(crn, credits) {
    let input = document.getElementById("form_" + crn);
    input.parentNode.removeChild(input);
    chooseVue.$data.crnList.splice($.inArray(crn, chooseVue.$data.crnList), 1);
    chooseVue.$data.worksheet = document.getElementById("worksheet").innerHTML;
    chooseVue.$data.use_credits -= parseInt(credits);
    chooseVue.$data.ava_credits = chooseVue.$data.tol_credits - chooseVue.$data.use_credits;
}

let courseTable = $("#newCourseTable").DataTable({

    "language": {
        "aria": {
            "sortAscending": ": activate to sort column ascending",
            "sortDescending": ": activate to sort column descending"
        },
        "emptyTable": "没有数据！",
        "info": "显示 _START_ 至 _END_ 条 ，总共_TOTAL_ 条数据",
        "infoEmpty": "没有发现记录！",
        "infoFiltered": "(从_MAX_条记录中搜索)",
        "lengthMenu": "显示: _MENU_",
        "search": "搜索:",
        "zeroRecords": "没有找到匹配的记录！",
        "paginate": {
            "previous": "上一页",
            "next": "下一页",
            "last": "尾页",
            "first": "首页"
        }
    },
    "pagingType": "full_numbers",
    "lengthMenu": [
        [5],
        [5]
    ],
    pageLength: 5,
    processing: true,
    serverSide: true,

    ajax: {
        url: basePath + "/course",
        data: function (d) {
            d.mode = "choose";
        }
    },
    columns: [
        {"data": "crn", "title": "编号"},
        {"data": "name", "title": "课名"},
        {
            "data": null, "title": "等级-班级", "createdCell": function (nTd, rowData) {
                $(nTd).html('<p style="line-height: 1.42857143; padding-top: 0; color:blue; ">' + rowData.level + "-" + rowData.section + '</p>');
            }
        },
        {"data": "credits", "title": "学分"},
        {
            "data": null, "title": "容量/剩余", "createdCell": function (nTd, rowData) {
                $(nTd).html('<p style="line-height: 1.42857143; padding-top: 0; color:blue; ">' + rowData.capacity + "/" + rowData.remain + '</p>');
            }
        },
        {
            "data": "status", "title": "状态", "createdCell": function (nTd, rowData) {
                if (rowData === 1)
                    $(nTd).html('<p style="line-height: 1.42857143; padding-top: 0; color:blue; ">未开始</p>');
                else if (rowData === 0)
                    $(nTd).html('<p style="line-height: 1.42857143; padding-top: 0; color:green; ">进行中</p>');
                else if (rowData === -1)
                    $(nTd).html('<p style="line-height: 1.42857143; padding-top: 0; color:red; ">已结课</p>');
            }
        },
        {"data": "date", "title": "起止时间"},
        {"data": "time", "title": "上课时间"},
        {"data": "day", "title": "星期"},
        {"data": "faculty", "title": "授课老师"},
        {
            "data": null, "title": "操作", "createdCell": function (nTd, rowData) {
                $(nTd).html(
                    '<i href="#" style="color: black;" class="fa fa-search" title="详情">' +
                    '   <span style="cursor: pointer" class="info" onclick="showInfo(\'' + rowData.crn + '\')">详情</span>' +
                    '</i>' +
                    '<br/>' +
                    '<i href="#" style="margin-top:5px; color: green;" class="fa fa-plus" title="添入工作表">' +
                    '   <span style="cursor: pointer" class="info" onclick="addToWorkSheet(\'' + rowData.crn + '\',\'' + rowData.credits + '\')">添入工作表</span>' +
                    '</i>'
                );
            }, "width": "80px"
        }
    ],
    "columnDefs": [{
        orderable: false,
        targets: [10]
    }, {
        "defaultContent": "",
        "targets": "_all"
    }]
});