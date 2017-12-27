var adviseTable = $("#transTable").DataTable({
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
        [10, 20, 50],
        [10, 20, 50]
    ],
    pageLength: 10,
    processing: true,
    serverSide: true,

    ajax: {
        url: basePath + "/admin/advise/list",
        data: function (d) {
            d.studentid = null;
            d.facultyid = null;
        }
    },
    columns: [
        {"data": "sname", "title": "学生姓名"},
        {"data": "updateTime", "title": "更新时间"},
        {"data": "oname", "title": "操作人"},
        {
            "data": null, "title": "操作", "createdCell": function (nTd, rowData) {
            var htmlStr = '<button class="btn btn-info" style="width:50%" onclick="viewReport(\'' + rowData.studentid + '\')">查看学生报告</button>';
            $(nTd).html(htmlStr);

        }, "width": "200px"
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