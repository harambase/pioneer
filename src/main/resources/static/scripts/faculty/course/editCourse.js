//添加课程
var createCourseForm = $("#createCourseForm").validate({});

var m = false;
var t = false;
var w = false;
var tr = false;
var f = false;
var sa = false;
var s = false;
var yes = false;


$('#yes-div').click(function () {
    $("#yes").prop("checked", !yes);
    yes = !yes;
});

$("#mon").click(function () {
    $("#m").prop("checked", !m);
    m = !m;
});
$("#tue").click(function () {
    $("#t").prop("checked", !t);
    t = !t;
});
$("#wed").click(function () {
    $("#w").prop("checked", !w);
    w = !w;
});
$("#thr").click(function () {
    $("#tr").prop("checked", !tr);
    tr = !tr;
});
$("#fri").click(function () {
    $("#f").prop("checked", !f);
    f = !f;
});
$("#sat").click(function () {
    $("#sa").prop("checked", !sa);
    sa = !sa;
});
$("#sun").click(function () {
    $("#s").prop("checked", !s);
    s = !s;
});

var uri = location.search.split("&");
var crn = uri[0].split("=")[1];
$(function () {
    getCourse(crn);

    var stuListTable = $("#studentList").DataTable({

        "language": {
            "aria": {
                "sortAscending": ": activate to sort column ascending",
                "sortDescending": ": activate to sort column descending"
            },
            "emptyTable": "没有数据！",
            "info": "显示 _START_ 至 _END_ 条 ，总共_TOTAL_ 条数据",
            "infoEmpty": "没有发现记录！",
            "infoFiltered": "(从_MAX_条记录中搜索)",
            "lengthMenu": "显示: _MENU_",
            "search": "搜索:",
            "zeroRecords": "没有找到匹配的记录！",
            "paginate": {
                "previous": "上一页",
                "next": "下一页",
                "last": "尾页",
                "first": "首页"
            }
        },
        "pagingType": "full_numbers",
        "lengthMenu": [
            [5, 10, 15],
            [5, 10, 15]
        ],
        pageLength: 5,
        processing: true,
        serverSide: true,

        ajax: {
            url: basePath + "/teach/student/list",
            data: function (d) {
                d.crn = crn;
            }
        },
        columns: [
            {"data": "studentid", "title": "学生ID"},
            {"data": "sname", "title": "学生名"},
            {"data": "grade", "title": "学生成绩"},
            {
                "data": "complete", "title": "完成情况", "createdCell": function (nTd, rowData) {
                if (rowData === "1")
                    $(nTd).html('<p style="line-height: 1.42857143; padding-top: 0; color:green; ">完成</p>');
                else if (rowData === "0")
                    $(nTd).html('<p style="line-height: 1.42857143; padding-top: 0; color:blue; ">进行中</p>');
                else if (rowData === "-1")
                    $(nTd).html('<p style="line-height: 1.42857143; padding-top: 0; color:red; ">挂科</p>');
            }
            },
            {
                "data": null, "title": "操作", "createdCell": function (nTd) {
                $(nTd).html('<button style="width: 100%" class="btn btn-success">修改成绩</button>');
            }, "width": "10px"
            }
        ],
        "columnDefs": [{
            orderable: false,
            targets: [4]
        }, {
            "defaultContent": "",
            "targets": "_all"
        }]
    });

});
var credits;

function getCourse(crn) {
    $.ajax({
        url: basePath + "/teach/list/search?search=" + crn,
        type: "GET",
        success: function (result) {
            var course = result.data[0];
            $("#crn").val(course.crn);
            $("#name").val(course.name);
            $("#credits").val(course.credits);
            $("#coulev").val(course.coulev);
            $("#cousec").val(course.cousec);
            $("#startdate").val(course.startdate);
            $("#enddate").val(course.enddate);
            $("#starttime").val(course.starttime);
            $("#endtime").val(course.endtime);
            $("#capa").val(course.capa);
            $("#year-semester").val(course.info);
            $("#classroom").val(course.classroom);
            $("#comment").val(course.comment);

            $("#selectF").css({display: "none"});
            $("#facultyInfo").text(course.faculty + "  ID: " + course.facultyid);


            credits = course.credits;
        }
    });
}

$("#update").click(function () {
    $("#selectF").css({display: "block"});
    $("#faculty").css({display: "none"});
});
$(".cancel").click(function () {
    $("#selectF").css({display: "none"});
    $("#faculty").css({display: "block"});
});
$("#updateC").click(function () {
    $("#selectC").css({display: "block"});
    $("#teach").css({display: "none"});
});
$("#cancel").click(function () {
    $("#selectC").css({display: "none"});
    $("#teach").css({display: "block"});
});

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

$("#transcript").css({"display": "none"});

$("#studentList").on("click", ".btn.btn-success", function () {
    $("#transcript").css({"display": "block"});
    $("#sid").val($(this).parents("tr").find("td").eq(0).html());
    $("#crn2").val(crn);
    $("#credits2").val(credits);
    $("#grade").val($(this).parents("tr").find("td").eq(2).html());

});