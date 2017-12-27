$("#confirm").click(function () {
    var formdata = {
        crn: $("#crn2").val(),
        info: $("#year-semester2").val(),
        name: $("#name2").val(),
        credits: $("#credits2").val(),
        coulev: $("#coulev2").val(),
        cousec: $("#cousec2").val(),
        startdate: $("#startdate2").val(),
        enddate: $("#enddate2").val(),
        starttime: $("#starttime2").val(),
        endtime: $("#endtime2").val(),
        capa: $("#capa2").val(),
        classroom: $("#classroom2").val(),
        comment: $("#comment2").val()
    };
    $.ajax({
        url: basePath + "/teach/update",
        type: "POST",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(formdata),
        success: function (data) {
            if (data.code === 2001)
                Showbo.Msg.alert("更新成功!", function () {
                    logTable.draw();
                });
            else
                Showbo.Msg.alert(data.msg, function () {
                });
        }
    })
});

$("#courseTable").on("click", ".btn.btn-info", function () {
    crn = $(this).parents("tr").find("td").eq(0).html();
    window.location.href = basePath + "/faculty/teach/edit?crn=" + crn;
});

//列表
var logTable = $("#courseTable").DataTable({

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
        [5, 10, 15, 20],
        [5, 10, 15, 20]
    ],
    pageLength: 10,
    processing: true,
    serverSide: true,

    ajax: {
        url: basePath + "/teach/list",
        data: function (d) {
            d.mode = "faculty";
        }
    },
    columns: [
        // {"data": "id", "title": "序号", "width" : "30px"},
        {"data": "crn", "title": "编号"},
        {"data": "name", "title": "课程名"},
        {"data": "coulev", "title": "等级"},
        {"data": "cousec", "title": "班级"},
        {"data": "capa", "title": "容量"},
        {"data": "remain", "title": "剩余"},
        {
            "data": "status", "title": "状态", "createdCell": function (nTd, rowData) {
            if (rowData === "1")
                $(nTd).html('<p style="line-height: 1.42857143; padding-top: 0; color:blue; ">未开始</p>');
            else if (rowData === "0")
                $(nTd).html('<p style="line-height: 1.42857143; padding-top: 0; color:green; ">进行中</p>');
            else if (rowData === "-1")
                $(nTd).html('<p style="line-height: 1.42857143; padding-top: 0; color:red; ">已结课</p>');

        }
        },
        {"data": "date", "title": "起止时间"},
        {"data": "time", "title": "上课时间"},
        {"data": "day", "title": "星期"},
        {"data": "faculty", "title": "授课老师"},
        // {"data": "updatetime", "title": "更新时间"},
        {
            "data": null, "title": "操作", "createdCell": function (nTd) {
            $(nTd).html('<button style="width: 100%" class="btn btn-info">查看详情</button>');
        }
        }
    ],
    "columnDefs": [{
        orderable: false,
        targets: [11]
    }, {
        "defaultContent": "",
        "targets": "_all"
    }]
});

//移除课程
$("#courseTable").on("click", ".btn.btn-danger", function () {
    crn = $(this).parents("tr").find("td").eq(0).html();
    Showbo.Msg.confirm("确认删除该课程？", function () {
        if ($(".btnfocus").val() !== "取消") {
            /*删除操作*/
            $.ajax({
                url: basePath + "/teach/remove?crn=" + crn,
                type: "DELETE",
                contentType: "application/json; charset=utf-8",
                success: function (data) {
                    if (data.code === 2001)
                        Showbo.Msg.alert("删除成功!", function () {
                            logTable.draw();
                        });
                    else
                        Showbo.Msg.alert(data.msg, function () {
                        });
                }
            });
        }
    });
});
