$(function () {
    //变量定义
    var userid;

    //添加Advise
    $("#studentTable").on("click", ".btn.btn-info", function () {
        var facultyid = userid;
        var studentid = $(this).parents("tr").find("td").eq(1).html();
        assginAdvise(studentid, facultyid)
    });
    $("#facultyTable").on("click", ".btn.btn-info", function () {
        var studentid = userid;
        var facultyid = $(this).parents("tr").find("td").eq(1).html();
        assginAdvise(studentid, facultyid)
    });

    function assginAdvise(studentid, facultyid) {
        var formdata = {
            facultyid: facultyid,
            studentid: studentid
        };
        $.ajax({
            url: basePath + "/admin/advise/assign",
            type: "POST",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(formdata),
            success: function (data) {
                if (data.code === 2001)
                    Showbo.Msg.alert("添加成功!", function () {
                    });
                else
                    Showbo.Msg.alert(data.msg, function () {
                    });
            }
        })
    }


    var studentTable = $("#studentTable").DataTable({
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
                d.type = "s";
                d.status = "1";
            }
        },
        columns: [
            {"data": "id", "title": "序列号", "width": "45px"},
            {"data": "userid", "title": "用户ID"},
            {"data": "firstname", "title": "名"},
            {"data": "lastname", "title": "姓"},
            {
                "data": null, "title": "操作", "createdCell": function (nTd) {
                $(nTd).html('<button class="btn btn-info">选择</button>');
            }, "width": "100px"
            }
        ],
        "columnDefs": [{
            orderable: false,
            targets: [4]
        }, {
            "defaultContent": "",
            "targets": "_all"
        }]
    });
    var facultyTable = $("#facultyTable").DataTable({

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
                d.type = "f";
                d.status = "1";
            }
        },
        columns: [
            {"data": "id", "title": "序列号", "width": "45px"},
            {"data": "userid", "title": "用户ID"},
            {"data": "firstname", "title": "名"},
            {"data": "lastname", "title": "姓"},
            {
                "data": null, "title": "操作", "createdCell": function (nTd) {
                $(nTd).html('<button class="btn btn-info">选择</button>');
            }, "width": "100px"
            }
        ],
        "columnDefs": [{
            orderable: false,
            targets: [4]
        }, {
            "defaultContent": "",
            "targets": "_all"
        }]
    });

    //移除用户
    $("#userTable").on("click", ".btn.btn-info", function () {
        userid = $(this).parents("tr").find("td").eq(1).html();
        Showbo.Msg.confirm("确认删除该用户？", function () {
            if ($(".btnfocus").val() !== "取消") {
                /*删除操作*/
                $.ajax({
                    url: basePath + "/admin/remove/user?userid=" + userid,
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

});


//编辑用户跳转
$("#userTable").on("click", ".btn.btn-edit", function () {
    var userid = $(this).parents("tr").find("td").eq(1).html();
    window.location.href = basePath + "/manage/user/detail?userid=" + userid;
});
