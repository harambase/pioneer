function edit(sid, sname, max_credits) {
    $("#editCredits").modal('show');
    $("#sid").val(sid);
    $("#sname").val(sname);
    $("#credits").val(max_credits);
}

$("#confirm").click(function () {
    var formdata = {
        studentid: $("#sid").val(),
        maxCredits: $("#credits").val()
    };
    $.ajax({
        url: basePath + "/student/update",
        type: "POST",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(formdata),
        success: function (data) {
            if (data.code === 2001)
                Showbo.Msg.alert("更新成功!", function () {
                    $("#editCredits").modal('hide');
                    curStuTable.draw();
                });
            else
                Showbo.Msg.alert(data.msg, function () {
                });
        }
    });
});

var curStuTable = $("#studentTable").DataTable({

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
        url: basePath + "/student/list",

        data: function (d) {
            d.status = "1";
        }
    },
    columns: [
        {"data": "studentid", "title": "学生ID"},
        {"data": "lastname", "title": "姓"},
        {"data": "firstname", "title": "名"},
        {"data": "max_credits", "title": "学分上限"},
        {"data": "complete", "title": "已完成"},
        {"data": "progress", "title": "进行中"},
        {"data": "incomplete", "title": "未完成"},
        {
            "data": null, "title": "操作", "createdCell": function (nTd, rowData) {
            var sname = rowData.lastname + ", " + rowData.firstname;
            $(nTd).html('<button style="width: 100%" class="btn btn-info" ' +
                'onclick="edit(\'' + rowData.studentid + '\',\'' + sname + '\',\'' + rowData.max_credits + '\')">修改上限</button>');
        }, "width": "100px"
        }
    ],
    "columnDefs": [{
        orderable: false,
        targets: [7]
    }, {
        "defaultContent": "",
        "targets": "_all"
    }]
});