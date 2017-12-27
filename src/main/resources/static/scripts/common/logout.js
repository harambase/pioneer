$(function () {
    $("#logout").click(function () {
        $.ajax({
            url: basePath + "/admin/logout",
            type: "POST",
            contentType: "application/json; charset=utf-8",
            success: function () {
                window.location.href = basePath + "/login";
            }
        })
    })

});