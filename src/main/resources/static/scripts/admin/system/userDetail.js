var student = false;
var faculty = false;
var admin = false;
var yes = false;
var reset = false;
var password = "";
var userid = location.search.split("&")[0].split("=")[1];//location.search获取url中的?后的字符串

var registerForm = $("#editUserForm").validate({});
//重置
$("#reset-div").click(function () {
    $("#reset").prop("checked", !reset);
    reset = !reset;
});

//启用
$("#active-div").click(function () {
    $("#inactive").prop("checked", false);
    $("#active").prop("checked", true);
});
$("#inactive-div").click(function () {
    $("#active").prop("checked", false);
    $("#inactive").prop("checked", true);
});

//性别
$("#male-div").click(function () {
    $("#female").prop("checked", false);
    $("#male").prop("checked", true);
});
$("#female-div").click(function () {
    $("#male").prop("checked", false);
    $("#female").prop("checked", true);
});

//角色
$("#student-div").click(function () {
    $("#student1").prop("checked", !student);
    student = !student;
});
$("#faculty-div").click(function () {
    $("#faculty1").prop("checked", !faculty);
    faculty = !faculty;
});
$("#admin-div").click(function () {
    $("#admin1").prop("checked", !admin);
    admin = !admin;
});

//确认
$("#yes-div").click(function () {
    $("#yes").prop("checked", !yes);
    yes = !yes;
});

var active = false;

//写入账户属性
function writeSettings(status, type, gender) {

    if (status === "1") {
        $("#active").prop("checked", true);
        active = true;
    }
    else {
        $("#inactive").prop("checked", true);
        active = false;
    }

    if (type.indexOf("a") !== -1) {
        $("#admin1").prop("checked", true);
        admin = true;
    }
    if (type.indexOf("f") !== -1) {
        $("#faculty1").prop("checked", true);
        faculty = true;
    }
    if (type.indexOf("s") !== -1) {
        $("#student1").prop("checked", true);
        student = true;
    }

    if (gender === "male") {
        $("#male").prop("checked", true);

    }
    else {
        $("#female").prop("checked", true);
    }
}

$(function () {

    getUser(userid);
});

//更新用户信息
$("#confirm").click(function () {
    if (registerForm.form()) {
        var gender = "";
        var type = "";
        var status = "";

        $('input[name="gender"]:checked').each(function () {
            gender = $(this).val();
        });

        $('input[name="type"]:checked').each(function () {
            type += $(this).val() + "/";
        });

        $('input[name="status"]:checked').each(function () {
            status = $(this).val();
        });

        if (reset)
            password = hex_md5("pioneer" + userid);

        var formdata = {
            userid: $("#userid").val(),
            username: $("#username").val(),
            firstname: $("#firstname").val(),
            lastname: $("#lastname").val(),
            birthday: $("#birthday").val(),
            email: $("#email").val(),
            wechat: $("#weChat").val(),
            tel: $("#tel").val(),
            dorm: $("#dorm").val(),
            qq: $("#qq").val(),
            comment: $("#comments").val(),
            password: password,
            gender: gender,
            type: type,
            status: status
        };

        $.ajax({
            url: basePath + "/admin/user/update",
            type: "POST",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(formdata),
            success: function (data) {
                if (data.code === 2001)
                    Showbo.Msg.alert("更新成功!", function () {
                        window.location.reload();
                    });
                else
                    Showbo.Msg.alert(data.msg, function () {
                    });
            }
        })
    }
});

//获取用户
function getUser(userid) {
    var user = null;
    $.ajax({
        url: basePath + "/admin/get?userid=" + userid,
        type: "GET",
        success: function (result) {
            if (result.code === 2001) {
                user = result.data;
                $("#userid").val(userid);
                $("#year-semester").val(user.info);
                $("#username").val(user.username);
                $("#firstname").val(user.firstname);
                $("#lastname").val(user.lastname);
                $("#birthday").val(user.birthday);
                $("#email").val(user.email);
                $("#qq").val(user.qq);
                $("#weChat").val(user.wechat);
                $("#tel").val(user.tel);
                $("#dorm").val(user.dorm);
                $("#comments").val(user.comment);

                password = user.password;

                writeSettings(user.status, user.type, user.gender);

            } else {
                Showbo.Msg.alert("用户获取失败", function () {
                });
            }
        }
    });
}