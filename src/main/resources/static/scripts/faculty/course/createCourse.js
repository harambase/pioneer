
$("#registerBtn").click(function () {
    if (createCourseForm.form()) {
        var name = $("#name").val();
        var credits = $("#credits").val();
        var coulev = $("#coulev").val();
        var cousec = $("#cousec").val();
        var startdate = $("#startdate").val();
        var enddate = $("#enddate").val();
        var starttime = $("#starttime").val();
        var endtime = $("#endtime").val();
        var capa = $("#capa").val();
        var day = "";
        var info = $("#year-semester").val();
        var classroom = $("#classroom").val();
        var comment = $("#comment").val();
        var facultyid = $("#searchFValue").val();
        var precrnArray = $("#searchCValue").val();
        var precrn = "/";

        $('input[name="day"]:checked').each(function () {
            day += $(this).val() + "/";
        });

        for (var i = 0; i < precrnArray.length; i++) {
            precrn += precrnArray[i] + "/";
        }

        var course = {
            credits: credits,
            coulev: coulev,
            name: name,
            cousec: cousec,
            startdate: startdate,
            enddate: enddate,
            starttime: starttime,
            endtime: endtime,
            capa: capa,
            facultyid: facultyid,
            day: day,
            info: info,
            precrn: precrn,
            classroom: classroom,
            comment: comment
        };


        $.ajax({
            url: basePath + "/teach/add",
            type: "POST",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(course),
            success: function (data) {
                if (data.code === 2001) {
                    Showbo.Msg.alert("添加成功!", function () {
                        window.location.href = basePath + "/faculty/teach/view";
                    });
                }
                else
                    Showbo.Msg.alert(data.msg, function () {
                    });
            }
        })
    }
});

