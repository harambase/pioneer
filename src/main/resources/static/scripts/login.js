var loginForm = $("#loginForm").validate({});
$("#btnLogin").click(function () {
    login();
});

$(function () {
    var event = arguments.callee.caller.arguments[0] || window.event;
    if (event.keyCode === 13) {//判断是否按了回车，enter的keycode代码是13，想看其他代码请猛戳这里。
        login();
    }
});

function login() {
    if (loginForm.form()) {
        var userid = $("#userid").val();
        var password = hex_md5($("#password").val());
        var person = {
            userid: userid,
            password: password
        };
        $.ajax({
            url: basePath + "/system/login",
            type: "POST",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(person),
            success: function (data) {
                if (data.code === 2001)
                    window.location.href = basePath + "/index";
                else
                    Showbo.Msg.alert("登录失败", function () {
                    });
            }
        });
    }
}
