var student = false;
var faculty = false;
var admin = false;
var yes = false;

var registerForm = $("#createUserForm").validate({});

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

$("#registerBtn").click(function () {
    if (registerForm.form()) {
        var firstname = $("#firstname").val();
        var lastname = $("#lastname").val();
        var email = $("#email").val();
        var qq = $("#qq").val();
        var weChat = $("#weChat").val();
        var dorm = $("#dorm").val();
        var gender = "";
        var type = "";
        var birthday = $("#birthday").val();
        var info = $("#year-semester").val();
        var comment = $("#comment").val();

        $('input[name="gender"]:checked').each(function () {
            gender = $(this).val();
        });

        $('input[name="type"]:checked').each(function () {
            type += $(this).val() + "/";
        });

        var person = {
            firstname: firstname,
            lastname: lastname,
            email: email,
            qq: qq,
            wechat: weChat,
            dorm: dorm,
            gender: gender,
            info: info,
            type: type,
            birthday: birthday,
            comment: comment
        };

        $.ajax({
            url: basePath + "/admin/user/add",
            type: "POST",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(person),
            success: function (data) {
                if (data.code === 2001) {
                    Showbo.Msg.alert("添加成功!", function () {
                        window.location.href = basePath + "/manage/user/view";
                    });
                }
                else
                    Showbo.Msg.alert(data.msg, function () {
                    });
            }
        })
    }
});