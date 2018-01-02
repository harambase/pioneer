$("#confirm").click(function () {
    var formdata = {
        crn: $("#crn2").val(),
        info: $("#year-semester2").val(),
        name: $("#name2").val(),
        credits: $("#credits2").val(),
        coulev: $("#coulev2").val(),
        cousec: $("#cousec2").val(),
        startdate: $("#startdate2").val(),
        enddate: $("#enddate2").val(),
        starttime: $("#starttime2").val(),
        endtime: $("#endtime2").val(),
        capa: $("#capa2").val(),
        classroom: $("#classroom2").val(),
        comment: $("#comment2").val()
    };
    $.ajax({
        url: basePath + "/teach/update",
        type: "POST",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(formdata),
        success: function (data) {
            if (data.code === 2001)
                Showbo.Msg.alert("更新成功!", function () {
                    logTable.draw();
                });
            else
                Showbo.Msg.alert(data.msg, function () {
                });
        }
    })
});

$("#courseTable").on("click", ".btn.btn-info", function () {
    crn = $(this).parents("tr").find("td").eq(0).html();
    window.location.href = basePath + "/manage/teach/edit?crn=" + crn;
});

//列表

//移除课程
$("#courseTable").on("click", ".btn.btn-danger", function () {
    crn = $(this).parents("tr").find("td").eq(0).html();

});
