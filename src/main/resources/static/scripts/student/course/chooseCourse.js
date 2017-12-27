var pinNumForm = $("#pinNumForm").validate({});
var courseChooseForm = $("#courseChooseForm").validate({});

$(function () {
    init();
});

function init() {
    $("#courseChooseForm").css({display: "none"});
    $.ajax({
        url: basePath + "/pin/session/verify",
        type: "GET",
        success: function (data) {
            if (data.code === 2001) {
                initStudentInfo();
                courseTable.draw();
                $("#courseChooseForm").css({display: "block"});
            }
        }
    });
}

$("#pinValidate").click(function () {
    if (pinNumForm.form()) {
        /*删除操作*/
        $.ajax({
            url: basePath + "/pin/validate?pin=" + $("#pin").val(),
            type: "GET",
            success: function (data) {
                validate(data.code);
            }
        });
    }
});

function validate(code) {
    if (code === 2001) {
        initStudentInfo();
        courseTable.draw();
        $("#courseChooseForm").css({display: "block"});
    }
    else
        Showbo.Msg.alert("验证失败!", function () {
        });
}

function initStudentInfo() {

    $.ajax({
        url: basePath + "/admin/get/current",
        type: "GET",
        success: function (data) {
            initStudent(data.data.userid);
        }
    });
}

var tol_credits = 0;
var use_credits = 0;
var ava_credits = 0;

function initStudent(studentid) {
    $.ajax({
        url: basePath + "/student/available/credit?studentid=" + studentid,
        type: "GET",
        success: function (data) {
            if (data.code === 2001) {
                tol_credits = data.data.tol_credits;
                use_credits = data.data.use_credits;
                ava_credits = data.data.ava_credits;
                setCredits();
            }
            else
                Showbo.Msg.alert("获取学生信息失败!", function () {
                });
        }
    });
}

function setCredits() {
    $("#tol_credits").val(tol_credits);
    $("#ava_credits").val(ava_credits);
    $("#use_credits").val(use_credits);
}

var courseTable = $("#newCourseTable").DataTable({

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
        url: basePath + "/teach/list",
        data: function (d) {
            d.mode = "choose";
        }
    },
    columns: [
        {"data": "crn", "title": "编号"},
        {"data": "name", "title": "课名"},
        {
            "data": null, "title": "等级-班级", "createdCell": function (nTd, rowData) {
            $(nTd).html('<p style="line-height: 1.42857143; padding-top: 0; font-size:8px; color:blue; ">' + rowData.coulev + "-" + rowData.cousec + '</p>');
        }
        },
        {"data": "credits", "title": "学分"},
        {
            "data": null, "title": "容量/剩余", "createdCell": function (nTd, rowData) {
            $(nTd).html('<p style="line-height: 1.42857143; padding-top: 0; font-size:8px; color:blue; ">' + rowData.capa + "/" + rowData.remain + '</p>');
        }
        },
        {
            "data": "status", "title": "状态", "createdCell": function (nTd, rowData) {
            if (rowData === "1")
                $(nTd).html('<p style="line-height: 1.42857143; padding-top: 0; font-size:8px; color:blue; ">未开始</p>');
            else if (rowData === "0")
                $(nTd).html('<p style="line-height: 1.42857143; padding-top: 0; font-size:8px; color:green; ">进行中</p>');
            else if (rowData === "-1")
                $(nTd).html('<p style="line-height: 1.42857143; padding-top: 0; font-size:8px; color:red; ">已结课</p>');
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

var crn = "";
var counter = 0;
var crnList = [];

function showInfo(crn) {
    window.location.href = basePath + "/student/teach/view?crn=" + crn;
}

function isAvaCreditsEnough(credits) {
    return (tol_credits - use_credits - credits) >= 0;//true:enough, false:not enough
}

function isSelectAgain(crn) {
    var newId = "input_" + crn;
    var input = document.getElementById(newId);
    return input !== null; //true:again, false:not again
}

function addToWorkSheet(crn, credits) {
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
    counter++;
    crnList.push(crn);

    var worksheet = $("#worksheet").html() +
        '<div id="form_' + crn + '" class="form-group">' +
        '   <div class="col-sm-1">' +
        '       <i id="remove_' + crn + '" class="fa fa-minus-circle fa-3x" style="color: red; cursor: pointer; margin-top: 3px;" ' +
        '          onclick="removeFromWorkSheet(\'' + crn + '\',\'' + credits + '\')"></i>' +
        '   </div>' +
        '   <div class="col-sm-4">' +
        '       <label for="input_' + crn + '" class="control-label" style="width:100%;">已选课程:</label>' +
        '   </div>' +
        '   <div class="col-sm-6" style="width: 58%;">' +
        '        <input name="course_choose" id="input_' + crn + '" class="form-control" minlength="6" maxlength="6" value="' + crn + '" required disabled/>' +
        '   </div>' +
        '</div>';


    use_credits += parseInt(credits);
    ava_credits = tol_credits - use_credits;
    setCredits();


    $("#worksheet").html(worksheet);
}

function removeFromWorkSheet(crn, credits) {
    var newId = "#form_" + crn;
    $(newId).remove();
    use_credits -= parseInt(credits);
    ava_credits = tol_credits - use_credits;
    setCredits();
}

$("#reset").click(function () {
    $("#worksheet").html("");
    initStudentInfo();
});

$("#submit").click(function () {
    var choiceList = [];
    if (crnList.length === 0) {
        Showbo.Msg.alert("没有选择任何课程!", function () {
        });
        return;
    }
    for (var i = 0; i < crnList.length; i++) {
        var newId = "input_" + crnList[i];
        var input = document.getElementById(newId);
        if (input !== null) {
            choiceList.push(crnList[i]);
        }
    }
    sendChoiceListAjax(choiceList);
});

function sendChoiceListAjax(choiceList) {
    $.ajax({
        url: basePath + "/teach/choose",
        type: "POST",
        data: {
            "choiceList": choiceList
        },
        success: function (data) {
            var failList = data.data.failList;
            if (failList.length === 0)
                Showbo.Msg.alert("全部注册成功!", function () {
                    $("#worksheet").html("");
                    initStudentInfo();
                });
            else {
                var html = '<table style="text-align: left">';
                for (var i = 0; i < failList.length; i++) {
                    html += '<tr><td>' + failList[i] + '</td></tr>';
                }
                var input = '<p style="color: red">课程注册失败详情</p>' + html + '</table>';
                Showbo.Msg.show({
                    buttons: {yes: '确定'}, msg: input, title: '注意', fn: function () {
                        $("#worksheet").html("");
                        initStudentInfo();
                    }
                });
            }
        }
    });
}