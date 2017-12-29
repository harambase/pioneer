function removeStuFromCourse(studentid, crn) {
    $.ajax({
        url: basePath + "/teach/student/remove?studentid=" + studentid + "&crn=" + crn,
        type: "DELETE",
        async: false,
        contentType: "application/json; charset=utf-8",
        success: function (data) {
            Showbo.Msg.alert(data.msg, function () {
            });
            return data;
        }
    });
}

// 字符验证，只能包含中文、英文、数字、下划线等字符。
jQuery.validator.addMethod("stringCheck", function (value, element) {
    return this.optional(element) || /^[a-zA-Z0-9\u4e00-\u9fa5-_]+$/.test(value);
}, "只能包含中文、英文、数字、下划线等字符");
// 只能输入[0-9]数字
jQuery.validator.addMethod("isDigits", function (value, element) {
    return this.optional(element) || /^\d+$/.test(value);
}, "只能输入0-9数字");
jQuery.validator.addMethod("checkOpPwd", function (value, element) {
    return this.optional(element) || /^[448aabae4a1dc89b48fdfba10d3e2d3f]+$/.test(hex_md5(value));
}, "操作密码错误");

$(document).on("show.bs.modal", ".modal", function () {
    $(this).draggable({
        handle: ".panel-heading"   // 只能点击头部拖动
    });
    $(this).css("overflow", "hidden"); // 防止出现滚动条，出现的话，你会把滚动条一起拖着走的
});

function formatRepoSelection(repo) {
    return repo.id
}

function formatRepo(repo) {
    return repo.text
}

$("#logout").click(function () {
    $.ajax({
        url: basePath + "/system/logout",
        type: "POST",
        contentType: "application/json; charset=utf-8",
        success: function () {
            window.location.href = basePath + "/login";
        }
    })
});