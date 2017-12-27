var yes = false;
var transcript = false;
var course = false;

var createPinForm = $("#createPinForm").validate({});

//种类
$("#teach-div").click(function () {
    $("#teach").prop("checked", !course);
    course = !course;
});
$("#transcript-div").click(function () {
    $("#transcript").prop("checked", !transcript);
    transcript = !transcript;
});

//确认
$("#yes-div").click(function () {
    $("#yes").prop("checked", !yes);
    yes = !yes;
});

$("#generate").click(function () {
    if (createPinForm.form()) {
        $('input[name="type"]:checked').each(function () {
            var role = $(this).val();
            $.ajax({
                url: basePath + "/pin/generate?startTime=" + $("#startTime").val()
                + "&endTime=" + $("#endTime").val()
                + "&role=" + role
                + "&info=" + $("#info").val()
                + "&remark=" + $("#comments").val(),
                type: "GET",
                success: function (data) {
                    if (data.code === 2001)
                        Showbo.Msg.alert(data.msg, function () {
                            window.location.reload();
                        });
                    else
                        Showbo.Msg.alert(data.msg, function () {
                        });
                }
            })
        });
    }
});

//列表
$("#searchInfo").select2({
    ajax: {
        url: basePath + "/pin/list/info",
        type: "GET",
        delay: 250,
        data: function (params) {
            return {
                info: params.term, // search term 请求参数 ， 请求框中输入的参数
                page: params.page
            };
        },
        processResults: function (data, params) {
            //var data = [{ id: 0, text: 'enhancement' }, { id: 1, text: 'bug' }, { id: 2, text: 'duplicate' }, { id: 3, text: 'invalid' }, { id: 4, text: 'wontfix' }];
            var itemList = [];
            var item;
            for (var i = 0; i < data.data.length; i++) {
                item = {
                    id: data.data[i].info,
                    text: data.data[i].info
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

function formatRepoSelection(repo) {
    return repo.id
}

function formatRepo(repo) {
    return repo.text
}