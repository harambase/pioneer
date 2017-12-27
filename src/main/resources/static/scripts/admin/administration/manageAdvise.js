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
        {"data": "fname", "title": "教师姓名"},
        {
            "data": "status", "title": "状态", "createdCell": function (nTd, rowData) {
            if (rowData === "1")
                $(nTd).html('<p style="line-height: 1.42857143; padding-top: 0; color:green; ">正常</p>');
            else if (rowData === "0")
                $(nTd).html('<p style="line-height: 1.42857143; padding-top: 0; color:red; ">已停止</p>');
        }
        },
        {"data": "updateTime", "title": "更新时间"},
        {"data": "oname", "title": "操作人"},
        {
            "data": null, "title": "操作", "createdCell": function (nTd, rowData) {
            var htmlStr = '<button class="btn btn-info" style="width:50%" onclick="deleteAdvise(\'' + rowData.id + '\')">删除辅导关系</button>';
            if (rowData.status === "0")
                htmlStr += '<button class="btn btn-success" style="width:50%" onclick="updateStatus(\'' + rowData.id + '\',\'' + "1" + '\')">启用辅导关系</button>';
            else
                htmlStr += '<button class="btn btn-danger " style="width:50%" onclick="updateStatus(\'' + rowData.id + '\',\'' + "0" + '\')">停止辅导关系</button>';
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

var addAdviseForm = $("#addAdviseForm").validate({});

//移除关系
function deleteAdvise(id) {
    Showbo.Msg.confirm("确认删除该关系？", function () {
        if ($(".btnfocus").val() !== "取消") {
            /*删除操作*/
            $.ajax({
                url: basePath + "/admin/advise/remove?id=" + id,
                type: "DELETE",
                contentType: "application/json; charset=utf-8",
                success: function (data) {
                    if (data.code === 2001)
                        Showbo.Msg.alert("删除成功!", function () {
                            adviseTable.draw();
                        });
                    else
                        Showbo.Msg.alert(data.msg, function () {
                        });
                }
            });
        }
    });
}

//更新状态
function updateStatus(id, status) {
    var formdata = {
        id: id,
        status: status
    };
    $.ajax({
        url: basePath + "/admin/advise/update",
        type: "POST",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(formdata),
        success: function (data) {
            if (data.code === 2001)
                Showbo.Msg.alert("更新成功!", function () {
                    adviseTable.draw();
                });
            else
                Showbo.Msg.alert(data.msg, function () {
                });
        }
    });
}

//添加关系
$("#addNew").click(function () {
    $("#searchFValue").empty();
    $("#searchSValue").empty();
    $("#addAdvise").modal('show');
});
$("#confirm").click(function () {
    if (addAdviseForm.form()) {
        var formdata = {
            studentid: $("#searchSValue").val(),
            facultyid: $("#searchFValue").val(),
            status: "1"
        };
        $.ajax({
            url: basePath + "/admin/advise/assign",
            type: "POST",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(formdata),
            success: function (data) {
                if (data.code === 2001)
                    Showbo.Msg.alert("添加成功!", function () {
                        adviseTable.draw();
                        $("#addAdvise").modal('hide');
                    });
                else
                    Showbo.Msg.alert(data.msg, function () {
                    });
            }
        });
    }
});

//教师列表
$("#searchFValue").select2({
    ajax: {
        url: basePath + "/admin/list/faculty",
        type: "GET",
        delay: 250,
        data: function (params) {
            return {
                search: params.term, // search term 请求参数 ， 请求框中输入的参数
                page: params.page
            };
        },
        processResults: function (data, params) {
            //var data = [{ id: 0, text: 'enhancement' }, { id: 1, text: 'bug' }, { id: 2, text: 'duplicate' }, { id: 3, text: 'invalid' }, { id: 4, text: 'wontfix' }];
            var itemList = [];
            var item;
            for (var i = 0; i < data.data.length; i++) {
                item = {
                    id: data.data[i].userid,
                    text: data.data[i].lastname + ", " + data.data[i].firstname
                };
                itemList.push(item);
            }
            // console.log(itemList);
            return {
                results: itemList//itemList
            };
        },
        cache: true
    },
    language: "zh-CN",
    tags: false,//允许手动添加
    allowClear: false,//允许清空
    escapeMarkup: function (markup) {
        return markup;
    }, // 自定义格式化防止xss注入
    minimumInputLength: 1,//最少输入多少个字符后开始查询
    templateResult: formatRepo,
    templateSelection: formatRepoSelection
});
//学生列表
$("#searchSValue").select2({
    ajax: {
        url: basePath + "/admin/list/student",
        type: "GET",
        delay: 250,
        data: function (params) {
            return {
                search: params.term, // search term 请求参数 ， 请求框中输入的参数
                page: params.page
            };
        },
        processResults: function (data, params) {
            var itemList = [];
            var item;
            for (var i = 0; i < data.data.length; i++) {
                item = {
                    id: data.data[i].userid,
                    text: data.data[i].lastname + ", " + data.data[i].firstname
                };
                itemList.push(item);
            }
            return {
                results: itemList
            };
        },
        cache: true
    },
    language: "zh-CN",
    tags: false,//允许手动添加
    allowClear: false,//允许清空
    escapeMarkup: function (markup) {
        return markup;
    }, // 自定义格式化防止xss注入
    minimumInputLength: 1,//最少输入多少个字符后开始查询
    templateResult: formatRepo,
    templateSelection: formatRepoSelection
});

function formatRepoSelection(repo) {
    return repo.id
}

function formatRepo(repo) {
    return repo.text
}

//解决模态框select不能输入
$.fn.modal.Constructor.prototype.enforceFocus = function () {
};