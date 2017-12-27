$(function () {
    var id, userJson, viewStatus = "";

    $("#general").click(function () {
        viewStatus = "";
        userReg.draw();
    });
    $("#active").click(function () {
        viewStatus = "0";
        userReg.draw();
    });
    $("#declined").click(function () {
        viewStatus = "-1";
        userReg.draw();
    });
    $("#approved").click(function () {
        viewStatus = "1";
        userReg.draw();
    });

    //编辑用户跳转
    $("#userReg").on("click", ".btn.btn-edit", function () {
        id = $(this).parents("tr").find("td").eq(0).html();
        var userid = $(this).parents("tr").find("td").eq(1).html();

        var rowIndex = $(this).parents("tr").index();
        //此处拿到隐藏列的id
        userJson = $("#userReg").DataTable().row(rowIndex).data().userJson;
        var createtime = $(this).parents("tr").find("td").eq(3).html();

        localStorage.clear();
        localStorage.setItem("user", userJson);
        localStorage.setItem("createtune", createtime);


        rowIndex = $(this).parents("tr").index();
        var status = $("#userReg").DataTable().row(rowIndex).data().status;

        window.location.href = basePath + "/manage/user/request/detail?id=" + id + "&userid=" + userid + "&status=" + status;
    });

    var userReg = $("#userReg").DataTable({

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
            [5, 10],
            [5, 10]
        ],
        pageLength: 10,
        processing: true,
        serverSide: true,

        ajax: {
            url: basePath + "/request/user/list",

            data: function (d) {
                d.viewStatus = viewStatus;
            }

        },
        columns: [
            {"data": "id", "title": "序列号", "width": "100px"},
            {"data": "userid", "title": "分配ID"},
            {
                "data": "userJson", "title": "姓, 名", "createdCell": function (nTd, rowData) {
                var user = JSON.parse(rowData);
                $(nTd).html(user.lastname + ", " + user.firstname);
            }
            },
            {"data": "createtime", "title": "申请时间"},
            {
                "data": "status", "title": "申请状态", "createdCell": function (nTd, rowData) {
                if (rowData === "0") $(nTd).html('<p style="line-height: 1.42857143; padding-top: 0; color:blue; ">申请中</p>');
                else if (rowData === "1") $(nTd).html('<p style="line-height: 1.42857143; padding-top: 0;color:green; ">已批准</p>');
                else if (rowData === "-1") $(nTd).html('<p style="line-height: 1.42857143; padding-top: 0;color:red; ">已拒绝</p>');
            }
            },
            {
                "data": null, "title": "操作", "createdCell": function (nTd) {
                $(nTd).html('<button class="btn btn-edit btn-warning">查看详情</button>');
            }, "width": "100px"
            }
        ],
        "columnDefs": [{
            orderable: false,
            targets: [5]
        }, {
            "defaultContent": "",
            "targets": "_all"
        }]
    });
});
