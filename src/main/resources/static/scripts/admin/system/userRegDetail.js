var student = false;
var faculty = false;
var admin = false;
var yes = false;
var reset = false;
var password = "";
var user;

var uri = location.search.split("&");
var id = uri[0].split("=")[1];
var userid = uri[1].split("=")[1];
var status = uri[2].split("=")[1];
var createtime = localStorage.getItem("createtime");
var userJson = localStorage.getItem("user");

if (status !== "0") {
    $(' input').each(function () {
        $(this).prop("disabled", true);
    });
    $("#action").css({display: "none"});
    $("#type").css({display: "none"});
    $("#comments").prop("disabled", true);
}

//拒绝
$("#decline").click(function () {

    var comment = $("#comments").val();
    if (comment === "")
        Showbo.Msg.alert("拒绝申请，必须填写备注！", function () {
        });
    else
        updateTempUser("-1", comment, userid);
});


function updateTempUser(regStatus, comment, userid) {
    user.comment = comment;
    var newUserJson = JSON.stringify(user);

    var tempUser = {
        id: id,
        userJson: newUserJson,
        userid: userid,
        status: regStatus,
        createtime: createtime
    };
    $.ajax({
        url: basePath + "/request/update/user",
        type: "POST",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(tempUser),
        success: function (data) {
            if (data.code === 2001) {
                Showbo.Msg.alert(data.msg, function () {
                    window.location.href = basePath + "/manage/user/request";
                });
            } else if (data.code !== 2001)
                Showbo.Msg.alert(data.msg, function () {
                });
        }
    });
}

var registerForm = $("#registerUserForm").validate({});

//性别
$("#male-div").click(function () {
    if (status === "0") {
        $("#female").prop("checked", false);
        $("#male").prop("checked", true);
    }
});
$("#female-div").click(function () {
    if (status === "0") {
        $("#male").prop("checked", false);
        $("#female").prop("checked", true);
    }
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


$(function () {
    user = JSON.parse(userJson);

    $("#userid").val(userid);
    $("#year-semester").val(user.info);
    $("#username").val(user.lastname + user.firstname);
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

    if (user.gender === "male")
        $("#male").prop("checked", true);
    else
        $("#female").prop("checked", true);

});

//批准
$("#approve").click(function () {
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

        var comment = $("#comments").val();

        user.username = $("#username").val();
        user.firstname = $("#firstname").val();
        user.lastname = $("#lastname").val();
        user.birthday = $("#birthday").val();
        user.email = $("#email").val();
        user.wechat = $("#weChat").val();
        user.tel = $("#tel").val();
        user.dorm = $("#dorm").val();
        user.qq = $("#qq").val();
        user.comment = comment;
        user.gender = gender;
        user.type = type;
        user.status = "1";


        $.ajax({
            url: basePath + "/admin/user/add",
            type: "POST",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(user),
            success: function (data) {
                if (data.code === 2001) {
                    updateTempUser("1", comment, userid);
                }
                else
                    Showbo.Msg.alert(data.msg, function () {
                    });
            }
        });
    }
});
