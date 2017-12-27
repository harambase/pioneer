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
var credits;

$(function () {
    getCourse(crn);
});

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
