//发送
$("#send").click(function () {

    var receiverList = $("#searchUser").val();
    var body = $("#write-body").val();
    var subject = $("#write-subject").val();

    for (var i = 0; i < receiverList.length; i++) {
        var message = {
            receiverid: receiverList[i],
            body: body,
            subject: subject,
            title: subject,
            tag: "重要",
            status: "unread"
        };
        $.ajax({
            url: basePath + "/message/create",
            type: "POST",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(message),
            success: function (data) {
                if (data.code === 2001) {
                    messageTable.draw();
                    resetWrite();
                }
                else
                    Showbo.Msg.alert("发送失败", function () {
                    });
            }
        });
    }
});
//保存
$("#save").click(function () {
    var receiverList = $("#searchUser").val();
    var body = $("#write-body").val();
    var subject = $("#write-subject").val();

    for (var i = 0; i < receiverList.length; i++) {
        var message = {
            receiverid: receiverList[i],
            body: body,
            subject: subject,
            title: subject,
            tag: "重要",
            status: "unread"
        };
        $.ajax({
            url: basePath + "/message/create",
            type: "POST",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(message),
            success: function (data) {
                if (data.code === 2001) {
                    messageTable.draw();
                    resetWrite();
                }
                else
                    Showbo.Msg.alert("发送失败", function () {
                    });
            }
        });
    }
});
$("#close").click(function () {
    resetWrite();
});
$("#quit").click(function () {
    resetWrite();
});

function resetWrite() {
    $("#writeMail").css({display: "none"});
    $("#write-subject").val("");
    $("#write-body").val("");
}

$("#searchUser").select2({
    ajax: {
        url: basePath + "/admin/list/active/user",
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

            return {
                results: itemList//itemList
            };
        },
        cache: true
    },
    placeholder: "收件人",
    language: "zh-CN",
    tags: false,//允许手动添加
    allowClear: true,//允许清空
    escapeMarkup: function (markup) {
        return markup;
    }, // 自定义格式化防止xss注入
    minimumInputLength: 1,//最少输入多少个字符后开始查询
    templateResult: formatRepo,
    templateSelection: formatRepoSelection
});