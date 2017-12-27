$(function () {
    var regForm = $("#regForm").validate({});
    $("#male1").click(function () {
        $("#male").prop("checked", true);
        $("#female").prop("checked", false);
        $("#gender").val("male");
    });
    $("#female1").click(function () {
        $("#male").prop("checked", false);
        $("#female").prop("checked", true);
        $("#gender").val("female");
    });
    $("#newStudent").click(function () {
        $("#return").prop("checked", false);
        $("#new").prop("checked", true);
        $("#info").prop("disabled", true);
        $("#info").val("2017-02");
    });
    $("#retStudent").click(function () {
        $("#new").prop("checked", false);
        $("#return").prop("checked", true);
        $("#info").prop("disabled", false);
    });


    $("#submit").click(function () {
        if (regForm.form()) {
            var password = $("#inputPassword3").val();
            var formdata = {
                info: $("#info").val(),
                lastname: $("#inputName3").val(),
                firstname: $("#firstName").val(),
                email: $("#inputEmail3").val(),
                password: hex_md5(password),
                qq: $("#qq").val(),
                tel: $("#tel").val(),
                birthday: $("#birthday").val(),
                gender: $("#gender").val()
            };

            $.ajax({
                url: basePath + "/request/user/register",
                type: "POST",
                contentType: "application/json; charset=utf-8",
                data: JSON.stringify(formdata),
                success: function (data) {
                    if (data.code === 2001)
                        Showbo.Msg.alert("成功！请等待管理员审核。", function () {
                            window.location.href = basePath + "/login";
                        });
                    else
                        Showbo.Msg.alert(data.msg, function () {
                        });
                }

            })
        }
    });
});

