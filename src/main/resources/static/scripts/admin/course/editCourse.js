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
    getPreCourse(crn);
    var curStuTable = $("#studentTable").DataTable({

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
            [5],
            [5]
        ],
        pageLength: 5,
        processing: true,
        serverSide: true,

        ajax: {
            url: basePath + "/student/list",

            data: function (d) {
                d.status = "1";
            }
        },
        columns: [
            {"data": "studentid", "title": "学生ID"},
            {"data": "lastname", "title": "姓"},
            {"data": "firstname", "title": "名"},
            {"data": "max_credits", "title": "学分上限"},
            {"data": "complete", "title": "已完成"},
            {"data": "progress", "title": "进行中"},
            {"data": "incomplete", "title": "未完成"},
            {
                "data": null, "title": "操作", "createdCell": function (nTd) {
                $(nTd).html('<button style="width: 100%" class="btn btn-info">添加</button>');
            }, "width": "100px"
            }
        ],
        "columnDefs": [{
            orderable: false,
            targets: [7]
        }, {
            "defaultContent": "",
            "targets": "_all"
        }]
    });
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
                $(nTd).html('<button style="width: 50%" class="btn btn-success">修改成绩</button><button style="width: 50%" class="btn btn-danger">移除</button>');
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

    //移除学生
    $("#studentList").on("click", ".btn.btn-danger", function () {
        var studentid = $(this).parents("tr").find("td").eq(0).html();
        Showbo.Msg.confirm("确认删除该学生？", function () {
            if ($(".btnfocus").val() !== "取消") {
                removeStuFromCourse(studentid, crn);
                $("#student").css({display: "block"});
                stuListTable.draw();
            }
        });
    });

    //添加学生
    $("#studentTable").on("click", ".btn.btn-info", function () {
        var studentid = $(this).parents("tr").find("td").eq(0).html();
        var pre = false;
        var time = false;
        var capacity = false;
        $('input[name="pre"]:checked').each(function () {
            pre = true;
        });
        $('input[name="time"]:checked').each(function () {
            time = true;
        });
        $('input[name="capa"]:checked').each(function () {
            capacity = true;
        });

        var formdata = {
            prereq: pre,
            time: time,
            capacity: capacity,
            studentid: studentid,
            crn: crn
        };
        $.ajax({
            url: basePath + "/teach/add/student",
            type: "POST",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(formdata),
            success: function (data) {
                if (data.code === 2001)
                    Showbo.Msg.alert("添加成功!", function () {
                        curStuTable.draw();
                    });
                else
                    Showbo.Msg.alert(data.msg, function () {
                    });
            }
        })
    });

});
var credits;

function getPreCourse(crn) {
    $.ajax({
        url: basePath + "/teach/list/precourse?crn=" + crn,
        type: "GET",
        success: function (result) {
            var preCourseList = result.data;
            $("#selectC").css({display: "none"});
            console.log(preCourseList.length);
            if (preCourseList.length > 0) {
                var preCourseListStr = "";
                for (var i = 0; i < preCourseList.length; i++)
                    preCourseListStr += preCourseList[i].name + ",";
                $("#preCourseInfo").text(preCourseListStr);
                //教师列表
            } else
                $("#preCourseInfo").text("当前无预选课程");
        }
    });
}

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
$("#searchFValue").select2({
    ajax: {
        url: basePath + "/admin/list/faculty",
        type: "GET",
        delay: 250,
        data: function (params) {
            return {
                search: params.term, // search term 请求参数 ， 请求框中输入的参数
                page: params.page
            };
        },
        processResults: function (data, params) {
            //var data = [{ id: 0, text: 'enhancement' }, { id: 1, text: 'bug' }, { id: 2, text: 'duplicate' }, { id: 3, text: 'invalid' }, { id: 4, text: 'wontfix' }];
            var itemList = [];
            var item;
            for (var i = 0; i < data.data.length; i++) {
                item = {
                    id: data.data[i].userid,
                    text: data.data[i].lastname + ", " + data.data[i].firstname
                };
                itemList.push(item);
            }
            // console.log(itemList);
            return {
                results: itemList//itemList
            };
        },
        cache: true
    },
    language: "zh-CN",
    tags: false,//允许手动添加
    allowClear: true,//允许清空
    escapeMarkup: function (markup) {
        return markup;
    }, // 自定义格式化防止xss注入
    minimumInputLength: 1,//最少输入多少个字符后开始查询
    templateResult: formatRepo,
    templateSelection: formatRepoSelection
});
$("#searchCValue").select2({
    ajax: {
        url: basePath + "/teach/list/search",
        type: "GET",
        delay: 250,
        data: function (params) {
            return {
                search: params.term, // search term 请求参数 ， 请求框中输入的参数
                page: params.page
            };
        },
        processResults: function (data, params) {
            var itemList = [];
            var item;
            for (var i = 0; i < data.data.length; i++) {
                item = {
                    id: data.data[i].crn,
                    text: data.data[i].name
                };
                itemList.push(item);
            }
            return {
                results: itemList
            };
        },
        cache: true
    },
    language: "zh-CN",
    tags: false,//允许手动添加
    allowClear: true,//允许清空
    escapeMarkup: function (markup) {
        return markup;
    }, // 自定义格式化防止xss注入
    minimumInputLength: 1,//最少输入多少个字符后开始查询
    templateResult: formatRepo,
    templateSelection: formatRepoSelection
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
                        window.location.href = basePath + "/manage/teach/view";
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