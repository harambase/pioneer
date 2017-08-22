$(function () {
    //变量定义
    var facultyids = "";
    var precrns = "";
    var course, preCourse, status, faculty, newFaculty, newStatus, newPreCourse;
    var createCourseForm = $("#createCourseForm").validate({});
    var crn = "";

    $("#add").click(function(){
        $("#add-div").css({display: "block"});
        $("#course-div").css({display: "none"});
    });
    $("#list").click(function(){
        $("#add-div").css({display: "none"});
        $("#course-div").css({display: "block"});
    });

    //教师列表
    function facultyList(searchFValue, w_select, w_select_li){
        var search = searchFValue.val();
        w_select.css({display: "block"});
        w_select_li.remove();
        $.ajax({
            url : basePath+"/admin/list/faculty?search="+search,
            type : "GET",
            success: function (result) {
                if (result.code === 2001) {
                    for (var i = 0; i < result.data.length; i++) {
                        var userid = result.data[i].userid;
                        newFaculty = result.data[i].lastname + result.data[i].firstname;
                        var temp = "<li data-name="+ newFaculty +" data-id=" + userid + ">" + newFaculty + "</li>";
                        w_select.append(temp);
                        w_select.css({
                            overflow: "auto",
                            position: "absolute",
                            top: "25px",
                            left: "0",
                            width: "100%",
                            margin: "0",
                            "z-index": "5"
                        });
                        w_select.find("li").css({
                            lineHeight: "30px",
                            paddingLeft: "15px",
                            backgroundColor: "#fff",
                            cursor: "pointer"
                        });
                        w_select.find("li").hover(function () {
                            $(this).css({background: "#03ced0", color: "#fff"});
                        }, function () {
                            $(this).css({background: "#fff", color: "#333"});
                        });
                        searchFValue.blur(function () {
                            setTimeout(function () {
                                w_select.hide();
                            }, 200)

                        });
                        w_select.find("li").click(function () {
                            searchFValue.val(this.innerHTML);
                        })
                    }

                } else {
                    Showbo.Msg.alert("获取失败", function () {});
                }
            }
        });
    }

    $("#searchFValue").bind("input propertychange", function () {
        facultyList($("#searchFValue"), $(".w_selected1"),$(".w_selected1 li"));
    });
    $(".w_selected1").on("click", "li", function () {
        $("#searchFValue").val($(this).data("id"));
        $("#searchFValue").data("userid", $(this).data("id"));
        $(".w_selected1").css({display: "none"});
    });
    $("#addf-button").click(function () {
        facultyids = $("#searchFValue").data("userid");
        if(facultyids === "" || facultyids === undefined){
            var faculty = $("#searchFValue").val();
            if(getFaulcty(faculty))
                Showbo.Msg.alert("教师获取成功", function () {});
        }
        else
            Showbo.Msg.alert("认证成功", function () {});

    });

    $("#searchFValue2").bind("input propertychange", function () {
        facultyList($("#searchFValue2"), $(".w_selected3"),$(".w_selected3 li"));
    });
    $(".w_selected3").on("click", "li", function () {
        $("#searchFValue2").val($(this).data("id"));
        $("#searchFValue2").data("userid", $(this).data("id"));
        $("#searchFValue2").data("name", $(this).data("name"));
        $(".w_selected3").css({display: "none"});
    });
    $("#addf-button2").click(function () {
        facultyids = $("#searchFValue2").data("userid");
        var name = $("#searchFValue2").data("name");
        var formdata = {
            crn :  $("#crn2").val(),
            facultyid : facultyids
        };
        $.ajax({
            url:basePath+"/course/assign/faculty",
            type: "POST",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(formdata),
            success: function (data) {
                if (data.code === 2001)
                    Showbo.Msg.alert("更新成功!", function () {
                        logTable.draw();
                        $(".w_manage h4").eq(1).html("Assigned Teacher: " + name);
                    });
                else if(data.code === 2005)
                    Showbo.Msg.alert("系统异常!", function () {});
                else
                    Showbo.Msg.alert("更新失败!", function () {});
            }
        })
    });

    //学生列表
    function studentList(searchFValue, w_select, w_select_li){
        var search = searchFValue.val();
        w_select.css({display: "block"});
        w_select_li.remove();
        $.ajax({
            url : basePath+"/admin/list/student?search="+search,
            type : "GET",
            success: function (result) {
                if (result.code === 2001) {
                    for (var i = 0; i < result.data.length; i++) {
                        var userid = result.data[i].userid;
                        newFaculty = result.data[i].lastname + result.data[i].firstname;
                        var temp = "<li data-name="+ newFaculty +" data-id=" + userid + ">" + newFaculty + "</li>";
                        w_select.append(temp);
                        w_select.css({
                            overflow: "auto",
                            position: "absolute",
                            top: "25px",
                            left: "0",
                            width: "100%",
                            margin: "0",
                            "z-index": "5"
                        });
                        w_select.find("li").css({
                            lineHeight: "30px",
                            paddingLeft: "15px",
                            backgroundColor: "#fff",
                            cursor: "pointer"
                        });
                        w_select.find("li").hover(function () {
                            $(this).css({background: "#03ced0", color: "#fff"});
                        }, function () {
                            $(this).css({background: "#fff", color: "#333"});
                        });
                        searchFValue.blur(function () {
                            setTimeout(function () {
                                w_select.hide();
                            }, 200)

                        });
                        w_select.find("li").click(function () {
                            searchFValue.val(this.innerHTML);
                        })
                    }

                } else {
                    Showbo.Msg.alert("获取失败", function () {});
                }
            }
        });
    }
    $("#searchSValue").bind("input propertychange", function () {
        studentList($("#searchSValue"), $(".w_selected4"),$(".w_selected4 li"));
    });
    $(".w_selected4").on("click", "li", function () {
        $("#searchSValue").val($(this).data("id"));
        $("#searchSValue").data("userid", $(this).data("id"));
        $("#searchSValue").data("name", $(this).data("name"));
        $(".w_selected3").css({display: "none"});
    });
    $("#adds-button").click(function () {
        facultyids = $("#searchSValue").data("userid");
        var name = $("#searchSValue").data("name");
        var formdata = {
            crn :  $("#crn2").val(),
            facultyid : facultyids
        };
        $.ajax({
            url:basePath+"/course/update",
            type: "POST",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(formdata),
            success: function (data) {
                if (data.code === 2001)
                    Showbo.Msg.alert("更新成功!", function () {
                        logTable.draw();
                        $(".w_manage h4").eq(1).html("Assigned Teacher: " + name);
                    });
                else if(data.code === 2005)
                    Showbo.Msg.alert("系统异常!", function () {});
                else
                    Showbo.Msg.alert("更新失败!", function () {});
            }
        })
    });

    //搜索教师
    function getFaulcty(f){
        var isSucc = false;
        $.ajax({
            url : basePath+"/admin/list/faculty?search="+f,
            type : "GET",
            async: false,
            success: function (result) {
                if (result.code === 2001) {
                    if(result.data.length !== 1){
                        Showbo.Msg.alert("教师获取失败", function () {});
                    }
                    else if(result.data.length === 1){
                        facultyids = result.data[0].userid;
                        faculty = result.data[0];
                        isSucc = true;
                    }
                } else {
                    Showbo.Msg.alert("教师获取失败", function () {});
                }
            }
        });
        return isSucc;
    }

    //课程列表
    function courseList(searchCValue, w_select, w_select_li){
        var search = searchCValue.val();
        w_select.css({display: "block"});
        w_select_li.remove();
        $.ajax({
            url : basePath+"/course/list/search?search="+search,
            type : "GET",
            success: function (result) {
                if (result.code === 2001) {
                    for (var i = 0; i < result.data.length; i++) {
                        var crn = result.data[i].crn;
                        newPreCourse = result.data[i].name;
                        var temp = "<li data-name="+ newPreCourse +"data-id=" + crn + ">" + newPreCourse + "</li>";
                        w_select.append(temp);
                        w_select.css({
                            overflow: "auto",
                            position: "absolute",
                            top: "25px",
                            left: "0",
                            width: "100%",
                            margin: "0",
                            "z-index": "5"
                        });
                        w_select.find("li").css({
                            lineHeight: "30px",
                            paddingLeft: "15px",
                            backgroundColor: "#fff",
                            cursor: "pointer"
                        });
                        w_select.find("li").hover(function () {
                            $(this).css({background: "#03ced0", color: "#fff"});
                        }, function () {
                            $(this).css({background: "#fff", color: "#333"});
                        });
                        searchCValue.blur(function () {
                            setTimeout(function () {
                                w_select.hide();
                            }, 200)

                        });
                        w_select.find("li").click(function () {
                            searchCValue.val(this.innerHTML);
                        })
                    }

                } else {
                    Showbo.Msg.alert("获取失败", function () {});
                }
            }
        });
    }

    $("#searchCValue").bind("input propertychange", function () {
       courseList($("#searchCValue"),$(".w_selected2"),$(".w_selected2 li"));
    });
    $(".w_selected2").on("click", "li", function () {
        $("#searchCValue").val($(this).data("id"));
        $("#searchCValue").data("crn", $(this).data("id"));
        $(".w_selected2").css({display: "none"});
    });
    $("#addc-button").click(function () {
        precrns = $("#searchCValue").data("crn");
        if(precrns === "" || precrns === undefined){
            var precrn = $("#searchCValue").val();
            if(getCourse(precrn, true))
                Showbo.Msg.alert("课程获取成功", function () {});
        }
        else
            Showbo.Msg.alert("认证成功", function () {});

    });

    $("#searchCValue2").bind("input propertychange", function () {
        courseList($("#searchCValue2"),$(".w_selected4"),$(".w_selected4 li"));
    });
    $(".w_selected4").on("click", "li", function () {
        $("#searchCValue2").val($(this).data("id"));
        $("#searchCValue2").data("crn", $(this).data("id"));
        $("#searchCValue2").data("name", $(this).data("name"));
        $(".w_selected4").css({display: "none"});
    });
    $("#addc-button2").click(function () {
        precrns = $("#searchCValue2").data("crn");
        var name = $("#searchCValue2").data("name");
        var formdata = {
            crn :  $("#crn2").val(),
            precrn : precrns
        };
        $.ajax({
            url:basePath+"/course/update",
            type: "POST",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(formdata),
            success: function (data) {
                if (data.code === 2001)
                    Showbo.Msg.alert("更新成功!", function () {
                        logTable.draw();
                        $(".w_manage h4").eq(2).html("Assigned Pre-required Course: " + name);
                    });
                else if(data.code === 2005)
                    Showbo.Msg.alert("系统异常!", function () {});
                else
                    Showbo.Msg.alert("更新失败!", function () {});
            }
        })
    });

    //搜索课程
    function getCourse(crn, pre){
        var isSucc = false;
        $.ajax({
            url : basePath+"/course/list/search?search="+crn,
            type : "GET",
            async: false,
            success: function (result) {
                if (result.code === 2001) {
                    if(result.data.length !== 1){
                        Showbo.Msg.alert("课程获取失败", function () {});
                    }
                    else if(result.data.length === 1){
                        isSucc = true;
                        if(pre){
                            precrns = result.data[0].crn;
                            preCourse = result.data[0];
                        }
                        else
                            course = result.data[0];

                    }
                } else {
                    Showbo.Msg.alert("课程获取失败", function () {});
                }
            }
        });
        return isSucc;
    }

    //添加课程
    $("#registerBtn").click(function () {
        if(createCourseForm.form()) {
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

            if (facultyids !== "") {

                $('input[name="day"]:checked').each(function () {
                    day += $(this).val() + "/";
                });

                console.log(facultyids);

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
                    facultyid: facultyids,
                    day: day,
                    info: info,
                    precrn: precrns
                };

                $.ajax({
                    url: basePath + "/course/add",
                    type: "POST",
                    contentType: "application/json; charset=utf-8",
                    data: JSON.stringify(course),
                    success: function (data) {
                        console.log(data);
                        if (data.code === 2001) {
                            Showbo.Msg.alert("添加成功!", function () {
                                logTable.draw();
                                $("#add-div").css({display: "none"});
                                $("#course-div").css({display: "block"});
                                $('[type=text]').val("");
                            });
                        }
                        else if (data.code === 2005) {
                            Showbo.Msg.alert("系统异常!", function () {} );
                        }
                        else
                            Showbo.Msg.alert(data.msg, function () {});
                    }
                })
            }
            else
                Showbo.Msg.alert("教师信息获取失败", function () {});

        }
    });
    //更新课程信息
    $("#confirm").click(function (){
        var formdata = {
            crn :  $("#crn2").val(),
            info  : $("#year-semester2").val(),
            name : $("#name2").val(),
            credits : $("#credits2").val(),
            coulev : $("#coulev2").val(),
            cousec : $("#cousec2").val(),
            startdate : $("#startdate2").val(),
            enddate : $("#enddate2").val(),
            starttime : $("#starttime2").val(),
            endtime : $("#endtime2").val(),
            capa : $("#capa2").val()
        };
        $.ajax({
            url:basePath+"/course/update",
            type: "POST",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(formdata),
            success: function (data) {
                if (data.code === 2001)
                    Showbo.Msg.alert("更新成功!", function () {
                        logTable.draw();
                    });
                else if(data.code === 2005)
                    Showbo.Msg.alert("系统异常!", function () {});
                else
                    Showbo.Msg.alert("更新失败!", function () {});
            }
        })
    });
    //更新课程时间
    $("#change-day").click(function (){
        var newDay="";
        $('input[name="newDay"]:checked').each(function () {
            newDay += $(this).val() + "/";
        });
        var formdata = {
            crn :  $("#crn2").val(),
            day: newDay
        };
        $.ajax({
            url:basePath+"/course/update",
            type: "POST",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(formdata),
            success: function (data) {
                if (data.code === 2001)
                    Showbo.Msg.alert("更新成功!", function () {
                        logTable.draw();
                        course.day = newDay;
                    });
                else if(data.code === 2005)
                    Showbo.Msg.alert("系统异常!", function () {});
                else
                    Showbo.Msg.alert("更新失败!", function () {});
            }
        })
    });
    $("#cancelD").click(function () {
        for(var i = 4; i<=10; i++)
            $(".w_manage input").eq(i).prop("checked", false);
        wirteDay();
    });
    //复选框控制
    $(".enable").click(function () {
        $(".w_manage input").eq(1).prop("checked", false);
        $(".w_manage input").eq(0).prop("checked", true);
        newStatus = $(".enable").val();
    });
    $(".disable").click(function () {
        $(".w_manage input").eq(0).prop("checked", false);
        $(".w_manage input").eq(1).prop("checked", true);
        newStatus = $(".disable").val();
    });
    //更改按钮
    $("#assignF").click(function(){
        $("#assignFDiv").css({display: "block"});
        $("#assignF").css({display: "none"});
    });
    $("#assignC").click(function(){
        $("#assignCDiv").css({display: "block"});
        $("#assignC").css({display: "none"});
    });
    $("#assignS").click(function(){
        $("#assignSDiv").css({display: "block"});
        $("#assignS").css({display: "none"});
    });
    $("#cancelF").click(function(){
        $("#assignFDiv").css({display: "none"});
        $("#assignF").css({display: "block"});
    });
    $("#cancelC").click(function(){
        $("#assignCDiv").css({display: "none"});
        $("#assignC").css({display: "block"});
    });
    $("#cancelS").click(function(){
        $("#assignSDiv").css({display: "none"});
        $("#assignS").css({display: "block"});
    });

    //写入课程属性
    function writeSettings(status, pre){
        if (status === "1") {
            $(".w_manage h4").eq(0).html("Account Status: ACTIVE");
            $(".w_manage input").eq(0).prop("checked", true);
        }
        else {
            $(".w_manage h4").eq(0).html("Account Status: DEACTIVE");
            $(".w_manage input").eq(1).prop("checked", true);
        }
        getFaulcty(course.facultyid);
        $(".w_manage h4").eq(1).html("Assigned Teacher: " + faculty.lastname + faculty.firstname);

        if(pre === null || pre === "")
            $(".w_manage h4").eq(2).html("Assigned Pre-required Course: None");
        else{
            getCourse(pre, true);
            $(".w_manage h4").eq(2).html("Assigned Pre-required Course: " + preCourse.name);
        }
        wirteDay();
    }
    function wirteDay(){
        var day = course.day.split("/");
        for(var i = 0; i < day.length; i++){
            switch (day[i]){
                case "m":
                    $(".w_manage input").eq(4).prop("checked", true);
                    break;
                case "t":
                    $(".w_manage input").eq(5).prop("checked", true);
                    break;
                case "w":
                    $(".w_manage input").eq(6).prop("checked", true);
                    break;
                case "tr":
                    $(".w_manage input").eq(7).prop("checked", true);
                    break;
                case "f":
                    $(".w_manage input").eq(8).prop("checked", true);
                    break;
                case "sa":
                    $(".w_manage input").eq(9).prop("checked", true);
                    break;
                case "s":
                    $(".w_manage input").eq(10).prop("checked", true);
                    break;
                default:
                    break;
            }
        }
    }

    //编辑弹窗
    $("#courseTable").on("click", ".btn.btn-edit", function () {

        crn = $(this).parents("tr").find("td").eq(1).html();
        var baseInfo = $(".w_basicInfo");
        if (getCourse(crn, false)) {
            console.log(course);
            baseInfo.find("#crn2").val(crn);
            baseInfo.find("#year-semester2").val(course.info);
            baseInfo.find("#name2").val(course.name);
            baseInfo.find("#credits2").val(course.credits);
            baseInfo.find("#coulev2").val(course.coulev);
            baseInfo.find("#cousec2").val(course.cousec);
            baseInfo.find("#startdate2").val(course.startdate);
            baseInfo.find("#enddate2").val(course.enddate);
            baseInfo.find("#starttime2").val(course.starttime);
            baseInfo.find("#endtime2").val(course.endtime);
            baseInfo.find("#capa2").val(course.capa);
            newStatus = status = course.status;

            writeSettings(status, course.precrn);

            $("#course").css({display: "block"});
            $(".w_ul li").remove();
            $("#assignFDiv").css({display: "none"});
            $("#assignCDiv").css({display: "none"});
            $("#assignSDiv").css({display: "none"});
            $("#assignF").css({display: "block"});
            $("#assignC").css({display: "block"});
            $("#assignS").css({display: "block"});

            closePop("#cancel");
            closePop("#cancel2");
            closePop("#cancel3");
            closePop(".w_close");
        }
    });
    function closePop(ele) {
        $(ele).click(function () {
            $("#course").css({display: "none"});
            $("[type='checkbox']").removeAttr("checked");//取消全选
        });
    }

    //弹窗条目选择
    $(".base-info").click(function () {
        $(this).siblings("li").removeClass("active");
        $(this).addClass("active");
        $(".w_pop").css({display: "none"});
        $(".w_pop")[0].style.display = "block";
    });
    $(".account").click(function () {
        $(this).siblings("li").removeClass("active");
        $(this).addClass("active");
        $(".w_pop").css({display: "none"});
        $(".w_pop")[1].style.display = "block";
    });
    $(".override").click(function () {
        $(this).siblings("li").removeClass("active");
        $(this).addClass("active");
        $(".w_pop").css({display: "none"});
        $(".w_pop")[2].style.display = "block";
    });
    $(".w_manage span").click(function () {
        $(this).siblings("span").removeClass("active");
        $(this).addClass("active");
    });
    $(".status span").click(function () {
        $(this).siblings("span").removeClass("active");
        $(this).addClass("active");
    });
    $(".w_close").click(function () {
        $("#course").css({display: "none"});
        $("#student").css({display: "none"});
        $("#confirm-wrapper").css({display:"none"});
    });

    //添加学生
    $("#studentTable").on("click", ".btn.btn-info", function () {
        var studentid = $(this).parents("tr").find("td").eq(1).html();
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
            prereq  :pre,
            time : time,
            capacity :capacity,
            studentid :studentid,
            crn: crn
        };
        $.ajax({
            url:basePath+"/course/add/student",
            type: "POST",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(formdata),
            success: function (data) {
                if (data.code === 2001)
                    Showbo.Msg.alert("添加成功!", function () {
                        logTable.draw();
                    });
                else if(data.code === 2005)
                    Showbo.Msg.alert("系统异常!", function () {});
                else
                    Showbo.Msg.alert(data.msg, function () {});
            }
        })
    });

    //课程中的学生
    $("#courseTable").on("click", ".btn.btn-list", function () {

        crn = $(this).parents("tr").find("td").eq(1).html();
        stuListTable.draw();
        $("#student").css({display : "block"})
    });
    $("#cancel4").click(function(){
        $("#student").css({display : "none"})
    });

    //移除学生
    $("#studentList").on("click", ".btn.btn-info", function () {
        var studentid = $(this).parents("tr").find("td").eq(1).html();
        $.ajax({
            url: basePath + "/course/transcript/remove?studentid="+studentid+"&crn="+crn,
            type: "DELETE",
            contentType: "application/json; charset=utf-8",
            success: function (data) {
                if (data.code === 2001)
                    Showbo.Msg.alert("删除成功!", function () {
                        $("#student").css({display : "block"});
                        stuListTable.draw();
                    });
                else if (data.code === 2005)
                    Showbo.Msg.alert("系统异常!", function () {
                    });
                else
                    Showbo.Msg.alert(data.msg, function () {
                    });
            }
        });
    });

    //移除课程
    $("#courseTable").on("click", ".btn.btn-info", function() {
        crn = $(this).parents("tr").find("td").eq(1).html();
        $("#confirm-wrapper").css({display:"block"});
    });
    $("#confirm-delete").click(function(){

        $.ajax({
            url: basePath + "/course/remove?crn="+crn,
            type: "DELETE",
            contentType: "application/json; charset=utf-8",
            success: function (data) {
                if (data.code === 2001)
                    Showbo.Msg.alert("删除成功!", function () {
                        $("#confirm-wrapper").css({display:"none"});
                        logTable.draw();
                    });
                else if (data.code === 2005)
                    Showbo.Msg.alert("系统异常!", function () {
                    });
                else
                    Showbo.Msg.alert(data.msg, function () {
                    });
            }
        });
    });
    $("#cancel-delete").click(function () {
        $("#confirm-wrapper").css({display:"none"});
    });

    //列表
    var logTable = $("#courseTable").DataTable({

        "language": {
            "aria": {
                "sortAscending": ": activate to sort column ascending",
                "sortDescending": ": activate to sort column descending"
            },
            "emptyTable": "No Data Founded！",
            "info": "SHOW FROM _START_ TO _END_ ，TOTAL OF _TOTAL_ RECORDS",
            "infoEmpty": "NO RECORDS FOUND！",
            "infoFiltered": "(SEARCH FROM _MAX_ RECORDS)",
            "lengthMenu": "SHOW: _MENU_",
            "search": "SEARCH:",
            "zeroRecords": "No Record Found！",
            "paginate": {
                "previous": "Previous",
                "next": "Next",
                "last": "Last",
                "first": "First"
            }
        },
        "lengthMenu": [
            [5, 10, 25, 50],
            [5, 10, 25, 50]
        ],
        pageLength: 5,
        processing: true,
        serverSide: true,

        ajax: {
            url: basePath + "/course/list",

            data: function (d) {
                d.startTime = $("#inpstart").val();
                d.endTime = $("#inpend").val();
            }
        },
        columns: [
            {"data": "id", "title": "serial"},
            {"data": "crn", "title": "crn"},
            {"data": "name", "title": "name"},
            {"data": "couLev", "title": "Level"},
            {"data": "couSec", "title": "Section"},
            {"data": "capa", "title": "capacity"},
            {"data": "remain", "title": "Remain"},
            {"data": "status", "title": "status"},
            {"data": "date", "title": "date"},
            {"data": "time", "title": "time"},
            {"data": "day", "title": "day"},
            {"data": "faculty", "title": "faculty"},
            {"data": "updatetime", "title": "updatetime"},
            {
                "data": null, "title": "Tool", "createdCell": function (nTd) {
                $(nTd).html('<button class="btn btn-info">Delete</button>' +
                    '<button class="btn btn-edit">Detail</button>' +
                    '<button class="btn btn-list">Student List</button>');
            }
            }
        ],
        "columnDefs": [{
            orderable: false,
            targets: [13]
        }, {
            "defaultContent": "",
            "targets": "_all"
        }]
    });
    var curStuTable = $("#studentTable").DataTable({

        "language": {
            "aria": {
                "sortAscending": ": activate to sort column ascending",
                "sortDescending": ": activate to sort column descending"
            },
            "emptyTable": "No Data Founded！",
            "info": "SHOW FROM _START_ TO _END_ ，TOTAL OF _TOTAL_ RECORDS",
            "infoEmpty": "NO RECORDS FOUND！",
            "infoFiltered": "(SEARCH FROM _MAX_ RECORDS)",
            "lengthMenu": "SHOW: _MENU_",
            "search": "SEARCH:",
            "zeroRecords": "No Record Found！",
            "paginate": {
                "previous": "Previous",
                "next": "Next",
                "last": "Last",
                "first": "First"
            }
        },
        "lengthMenu": [
            [5],
            [5]
        ],
        pageLength: 5,
        processing: true,
        serverSide: true,

        ajax: {
            url: basePath + "/admin/user/list",

            data: function (d) {
                d.type = "s";
                d.status = "1";
            }
        },
        columns: [
            {"data": "id", "title": "serial"},
            {"data": "userid", "title": "id"},
            {"data": "firstname", "title": "firstname"},
            {"data": "lastname", "title": "lastname"},
            {
                "data": null, "title": "Tool", "createdCell": function (nTd) {
                $(nTd).html('<button class="btn btn-info">Add</button>');
            }, "width": "100px"
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
    var stuListTable = $("#studentList").DataTable({

        "language": {
            "aria": {
                "sortAscending": ": activate to sort column ascending",
                "sortDescending": ": activate to sort column descending"
            },
            "emptyTable": "No Data Founded！",
            "info": "SHOW FROM _START_ TO _END_ ，TOTAL OF _TOTAL_ RECORDS",
            "infoEmpty": "NO RECORDS FOUND！",
            "infoFiltered": "(SEARCH FROM _MAX_ RECORDS)",
            "lengthMenu": "SHOW: _MENU_",
            "search": "SEARCH:",
            "zeroRecords": "No Record Found！",
            "paginate": {
                "previous": "Previous",
                "next": "Next",
                "last": "Last",
                "first": "First"
            }
        },
        "lengthMenu": [
            [5],
            [5]
        ],
        pageLength: 5,
        processing: true,
        serverSide: true,

        ajax: {
            url: basePath + "/course/student/list",
            data: function (d) {
                d.crn = crn;
            }
        },
        columns: [
            {"data": "id", "title": "serial"},
            {"data": "studentid", "title": "id"},
            {"data": "sfirst", "title": "firstname"},
            {"data": "slast", "title": "lastname"},
            {
                "data": null, "title": "Tool", "createdCell": function (nTd) {
                $(nTd).html('<button class="btn btn-info">Remove</button>');
            }, "width": "100px"
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