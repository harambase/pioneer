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

//教师列表
$(".js-example-basic-single").select2({
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
    placeholder: '请分配所属集群',//默认文字提示
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
//课程列表
$('.js-example-basic-multiple').select2({
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


function formatRepoSelection(repo) {
    return repo.id
}

function formatRepo(repo) {
    return repo.text
}

