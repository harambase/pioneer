var userType = "s";
var studentid = null;
var crn = null;
var complete = false;
var inProgress = false;
var nComplete = false;

var editTranscriptForm = $("#editTranscriptForm").validate({});

function edit(sid, crn, sname, cname, credits, grade, isComplete) {
    $("#editTranscript").modal('show');
    $("#sid").val(sid);
    $("#crn").val(crn);
    $("#sname").val(sname);
    $("#cname").val(cname);
    $("#credits").val(credits);
    $("#grade").val(grade);

    if (isComplete === "1") {
        $("#complete").prop("checked", !complete);
        complete = !complete;
    }
    else if (isComplete === "0") {
        $("#process").prop("checked", !inProgress);
        inProgress = !inProgress;
    }
    else {
        $("#nComplete").prop("checked", !nComplete);
        nComplete = !nComplete;
    }
}

$("#complete-div").click(function () {
    $("#complete").prop("checked", !complete);
    complete = !complete;
    $(this).off("click");
});
$("#process-div").click(function () {
    $("#process").prop("checked", !inProgress);
    inProgress = !inProgress;
    $(this).off("click");
});
$("#nComplete-div").click(function () {
    $("#nComplete").prop("checked", !nComplete);
    nComplete = !nComplete;
    $(this).off("click");
});

$("#overall").click(function () {
    studentid = null;
    crn = null;
    transTable.draw();
});

$("#userTable").on("click", ".btn.btn-info", function () {
    crn = null;
    studentid = $(this).parents("tr").find("td").eq(0).html();
    transTable.draw();
});

$("#classTable").on("click", ".btn.btn-info", function () {
    studentid = null;
    crn = $(this).parents("tr").find("td").eq(0).html();
    transTable.draw();
});

//更新成绩
$("#confirm").click(function () {
    if (editTranscriptForm.form()) {
        var valueOfComplete = "0";
        $('input[name="complete"]:checked').each(function () {
            valueOfComplete = $(this).val();
        });
        var formdata = {
            studentid: $("#sid").val(),
            crn: $("#crn").val(),
            grade: $("#grade").val(),
            complete: valueOfComplete
        };
        $.ajax({
            url: basePath + "/teach/transcript/update",
            type: "POST",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(formdata),
            success: function (data) {
                if (data.code === 2001)
                    Showbo.Msg.alert("更新成功!", function () {
                        studentid = crn = null;
                        transTable.draw();
                        $("#editTranscript").modal('hide');
                    });
                else
                    Showbo.Msg.alert(data.msg, function () {
                    });
            }
        })
    }
});

var userTable = $("#userTable").DataTable({

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
    "lengthMenu": [
        [5],
        [5]
    ],
    pageLength: 5,
    processing: true,
    serverSide: true,

    ajax: {
        url: basePath + "/admin/user/list",
        data: function (d) {
            d.type = userType;
        }
    },
    columns: [
        {"data": "userid", "title": "学生ID"},
        {"data": "lastname", "title": "姓"},
        {"data": "firstname", "title": "名"},
        {
            "data": null, "title": "操作", "createdCell": function (nTd) {
            $(nTd).html('<button class="btn btn-info" style="width:100%">选择</button>');
        }, "width": "100px"
        }
    ],
    "columnDefs": [{
        orderable: false,
        targets: [3]
    }, {
        "defaultContent": "",
        "targets": "_all"
    }]
});
var classTable = $("#classTable").DataTable({

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
    "lengthMenu": [
        [5],
        [5]
    ],
    pageLength: 5,
    processing: true,
    serverSide: true,

    ajax: {
        url: basePath + "/teach/list"
    },

    columns: [
        {"data": "crn", "title": "编号"},
        {"data": "name", "title": "课名"},
        {"data": "coulev", "title": "等级"},
        {"data": "cousec", "title": "班级"},
        {"data": "faculty", "title": "教师"},
        {
            "data": "status", "title": "状态", "createdCell": function (nTd, rowData) {
            if (rowData === "1")
                $(nTd).html('<p style="line-height: 1.42857143; padding-top: 0; color:black; ">未开始</p>');
            else if (rowData === "0")
                $(nTd).html('<p style="line-height: 1.42857143; padding-top: 0; color:green; ">进行中</p>');
            else if (rowData === "-1")
                $(nTd).html('<p style="line-height: 1.42857143; padding-top: 0; color:red; ">已结课</p>');

        }
        },
        {
            "data": null, "title": "操作", "createdCell": function (nTd) {
            $(nTd).html('<button class="btn btn-info layui-layer-btn" style="width:100%">选择</button>');
        }, "width": "60px"
        }
    ],
    "columnDefs": [{
        orderable: false,
        targets: [6]
    }, {
        "defaultContent": "",
        "targets": "_all"
    }]
});
var transTable = $("#transTable").DataTable({

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
        [10, 20, 50],
        [10, 20, 50]
    ],
    pageLength: 10,
    processing: true,
    serverSide: true,

    ajax: {
        url: basePath + "/teach/transcript/list",
        data: function (d) {
            d.studentid = studentid;
            d.crn = crn;
        }
    },
    columns: [
        {"data": "id", "title": "序号", "bVisible": false},
        {"data": "studentid", "title": "学生ID"},
        {"data": "sname", "title": "姓名"},
        {"data": "crn", "title": "课码"},
        {"data": "coursename", "title": "课名"},
        {"data": "fname", "title": "授课人"},
        {"data": "grade", "title": "成绩"},
        {
            "data": "complete", "title": "完成情况", "createdCell": function (nTd, rowData) {
            if (rowData === "1")
                $(nTd).html('<p style="line-height: 1.42857143; padding-top: 0; color:green; ">完成</p>');
            else if (rowData === "0")
                $(nTd).html('<p style="line-height: 1.42857143; padding-top: 0; color:blue; ">进行中</p>');
            else if (rowData === "-1")
                $(nTd).html('<p style="line-height: 1.42857143; padding-top: 0; color:red; ">挂科</p>');
        }
        },
        {"data": "assigntime", "title": "提交时间"},
        {
            "data": null, "title": "操作", "createdCell": function (nTd, rowData) {
            $(nTd).html('<button class="btn btn-edit" style="width: 100%" ' +
                'onclick="edit(\'' + rowData.studentid + '\',\'' + rowData.crn + '\',\'' + rowData.sname + '\',\'' + rowData.coursename + '\',\''
                + rowData.credits + '\',\'' + rowData.grade + '\',\'' + rowData.complete + '\')" >修改</button>');
        }, "width": "100px"
        }
    ],
    "columnDefs": [{
        orderable: false,
        targets: [9]
    }, {
        "defaultContent": "",
        "targets": "_all"
    }]
});