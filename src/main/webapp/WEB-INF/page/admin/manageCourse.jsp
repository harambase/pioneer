<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../common/commonJs.jsp" %>
<%@include file="../common/commonCSS.jsp" %>
<%@include file="common/adminCSS.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>课程管理</title>

    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">

</head>
<body>
<%@include file="common/manageHeader.jsp" %>
<div class="site-branding-text">
    <h1 class="site-title">Administrator Manage Site</h1>
    <p class="site-description">Course Management</p>
    <div class="choose-view" >
        <div class='w_manage_btn system-control-btn'>
            <button id="add" class="w_button" style="float:left; margin-left: 10px">添加一个课程</button>
        </div>
        <div class='w_manage_btn system-control-btn'>
            <button id="list" class="w_button" style="margin-left: 10px">浏览课程列表</button>
        </div>
    </div>
    <hr/>
</div>

<div class="spilt register-table">
    <div id="add-div" style="position:absolute;height: 700px; display: none" class="account-container register">
        <div class="content clearfix">
            <form id="createCourseForm" method="post" onsubmit="return false">
                <h1>添加一个课程</h1>
                <div class="login-fields">

                    <div class="field">
                        <label for="year-semester">Year-semester:</label>
                        <input id="year-semester" name="year-semester" value="" placeholder="*年份-学期(YYYY-SS)" class="login"
                               minlength="7" maxlength="7" required/>
                    </div> <!-- /field -->

                    <div class="field">
                        <label for="name">Course Name:</label>
                        <input id="name" name="name" value="" placeholder="*课程名" class="login"
                               minlength="1" maxlength="20" required/>
                    </div> <!-- /field -->

                    <div class="field">
                        <label for="credits">Last Name:</label>
                        <input id="credits" name="credits" value="" placeholder="*学分" class="login"
                               minlength="1" maxlength="2" required/>
                    </div> <!-- /field -->

                    <div class="field">
                        <label for="coulev">Course Level:</label>
                        <input id="coulev" name="coulev" value="" placeholder="*课程等级：100-499" class="login" maxlength="3"/>
                    </div> <!-- /field -->

                    <div class="field">
                        <label for="cousec">Section:</label>
                        <input id="cousec" name="cousec" value="" placeholder="*课程班级(01,02,03...)" class="login" maxlength="2"/>
                    </div> <!-- /field -->

                    <div class="field">
                        <label for="startdate">Start Date:</label>
                        <input id="startdate" name="startdate" value="" placeholder="*开始日期：YYYY-MM-DD"
                               class="login" minlength="10" maxlength="10"/>
                    </div> <!-- /field -->

                    <div class="field">
                        <label for="enddate">End Date:</label>
                        <input id="enddate" name="enddate" value="" placeholder="*结束日期：YYYY-MM-DD"
                               class="login" minlength="10" maxlength="10"/>
                    </div> <!-- /field -->
                    <div class="field">
                        <label for="starttime">Start Time:</label>
                        <input id="starttime" name="starttime" value="" placeholder="*开始时间：HH:MM:SS"
                               class="login" minlength="8" maxlength="8"/>
                    </div> <!-- /field -->

                    <div class="field">
                        <label for="endtime">End Time:</label>
                        <input id="endtime" name="endtime" value="" placeholder="*结束时间： HH:MM:SS"
                               class="login" minlength="8" maxlength="8"/>
                    </div> <!-- /field -->

                    <div class="field">
                        <label for="capa">Capacity:</label>
                        <input id="capa" name="capa" value="" placeholder="*课程容量" class="login"/>
                    </div> <!-- /field -->
                    <div class="field">
                    <div class="group-form-input">
                        <label for="searchFValue">分配教师:</label>
                        <input id="searchFValue" placeholder="*分配教师" style="float: left; width: 155px;" required>
                        <i id="searchFButton" class='fa fa-search fa-lg'></i>
                        <span class="w_button w_add" id="addf-button">检查教师信息</span>
                        <ul class="w_selected1">
                        </ul>
                    </div>
                    <div class="group-form-input">
                        <label for="searchCValue">分配预选课程：</label>
                        <input id="searchCValue" placeholder="分配预选课程(非必填)" style="float: left; width: 155px;">
                        <i id="searchCButton" class='fa fa-search fa-lg'></i>
                        <span class="w_button w_add" id="addc-button">检查课程信息</span>
                        <ul class="w_selected2">
                        </ul>
                    </div>

                    </div> <!-- /field -->
                </div> <!-- /login-fields -->
                <div class='w_day'>
                    <h4 class="status" style="margin-top: 0px;">*选择上课的时间: </h4>
                    <input class='m' type="checkbox" name="day" value="m"
                           style="margin: 10px 4px 10px 0; width: 13px;"/>星期一
                    <input class='t' type="checkbox" name="day" value="t"
                           style="margin: 10px 4px 10px 0; width: 13px;"/>星期二
                    <input class='w' type="checkbox" name="day" value="w"
                           style="margin: 10px 4px 10px 0; width: 13px;"/>星期三
                    <input class='tr' type="checkbox" name="day" value="tr"
                           style="margin: 10px 4px 10px 0; width: 13px;"/>星期四
                    <input class='f' type="checkbox" name="day" value="f"
                           style="margin: 10px 4px 10px 0; width: 13px;"/>星期五
                    <input class='sa' type="checkbox" name="day" value="sa"
                           style="margin: 10px 4px 10px 0; width: 13px;"/>星期六
                    <input class='s' type="checkbox" name="day" value="s"
                           style="margin: 10px 4px 10px 0; width: 13px;"/>星期日
                </div>

                <div class="login-actions" style="margin-top: -15px;">

                    <span class="login-checkbox">
                        <input id="Field" name="Field" type="checkbox" class="field login-checkbox" value="First Choice" tabindex="4" />
                        <label class="choice" for="Field">确认上述信息正确无误</label>
                    </span>
                    <button class="button btn btn-primary btn-large" id="registerBtn">创建</button>

                </div> <!-- .actions -->
            </form>
        </div> <!-- /content -->
    </div> <!-- /account-container -->
    <div id="course-div" style="float: right;position:absolute;width: 1563px;height: 700px; display: block"
         class="account-container register">
        <div class="content clearfix">
            <h1>课程列表</h1>
            <div class='course-table'>
                <table width="100%" style="font-size: 14px;" id="courseTable" class="display dataTable">
                </table>
            </div>
        </div> <!-- /content -->
    </div> <!-- /account-container -->
</div>

<div id="course" class="w_wrapper">
    <div class="w_wrap">
        <div class="w_head">
            <span>课程管理台</span>
            <span class="w_close">×</span>
        </div>
        <div class="w_body">
            <ul class="w_tab clearfix">
                <li class="active base-info">基本信息</li>
                <li class='account'> 课程设置</li>
                <li class="override">超级管理</li>
            </ul>

            <div class="w_tabC w_pop">
                <div class="w_content">
                    <div class="w_basicInfo account-container register" style="margin-left: 0px;">
                        <form id="editCourseForm" method="post" onsubmit="return false">
                            <table style="margin: 15px 27px;">
                                <tr>
                                    <td><p style="float: left">课程编号：</td>
                                    <td><input id="crn2" name="crn" value="" class="login" disabled/></td>
                                </tr>
                                <tr>
                                    <td><p style="float: left">年-学期:</td>
                                    <td><input id="year-semester2" name="year-semester2" value="" class="login" disabled/></td>
                                </tr>
                                <tr>
                                    <td><p style="float: left">课程名:</p></td>
                                    <td><input id="name2" name="name2" value=""class="login" minlength="1" maxlength="20" required/></td>
                                </tr>
                                <tr>
                                    <td><p style="float: left">学分:</p></td>
                                    <td><input id="credits2" name="credits2" value="" class="login"
                                               minlength="1" maxlength="1" required/></td>
                                </tr>
                                <tr>
                                    <td><p style="float: left">课程等级:</p></td>
                                    <td><input id="coulev2" name="coulev2" value="" class="login" maxlength="10"/></td>
                                </tr>
                                <tr>
                                    <td><p style="float: left">班级:</p></td>
                                    <td> <input id="cousec2" name="cousec2" value="" class="login"/></td>
                                </tr>
                                <tr>
                                    <td><p style="float: left">开始日期:</p></td>
                                    <td><input id="startdate2" name="startdate2" value="" class="login"/></td>
                                </tr>
                                <tr>
                                    <td><p style="float: left">结束日期:</p></td>
                                    <td><input id="enddate2" name="enddate2" value="" class="login"/></td>
                                </tr>
                                <tr>
                                    <td><p style="float: left">开始时间:</p></td>
                                    <td><input id="starttime2" name="starttime2" value="" class="login"/></td>
                                </tr>
                                <tr>
                                    <td><p style="float: left">结束时间:</p></td>
                                    <td><input id="endtime2" name="endtime2" value="" class="login"/></td>
                                </tr>
                                <tr>
                                    <td><p style="float: left">容量:</p></td>
                                    <td><input id="capa2" name="capa2" value="" class="login"/></td>
                                </tr>
                            </table>
                        </form>
                    </div> <!-- /account-container -->
                </div>
                <div class="w_buttons_w system-control-btn">
                    <button id="confirm" class="w_button">修改</button>
                    <button id="cancel" class="w_button">退出</button>
                </div>
            </div>
            <div class="w_tabD w_pop">
                <div class='w_manage'>
                    <h4 class="status" style="margin-top: 0px;">课程状态(无法修改): </h4>
                    <input class='enable' type="checkbox" name="powerState" value="1" disabled/>尽心
                    <input class='disable' type="checkbox" name="powerState" value="0" disabled/>停课

                    <h4 class="type">Assigned Faculty: </h4>
                    <button class="w_button" style="width: 190px;height: 35px; vertical-align: middle;" id="assignF">Assigned New Faculty</button>
                    <div id="assignFDiv" class="group-form-input">
                        <input id="searchFValue2" placeholder="Assign Faculty" style="margin-right: 20px; height: 30px;float: left; width: 155px;" required>
                        <span class="w_button w_add" id="addf-button2" style="width: 145px; text-align: center;">Change Faculty</span>
                        <span class="w_button w_add" id="cancelF" style="width: 100px; text-align: center; background-color: #3d8ca7;">Cancel</span>
                        <ul class="w_selected3">
                        </ul>
                    </div>
                    <h4 class="gender">Assigned Pre-required Course: None</h4>
                    <button class="w_button" style="width: 190px;height: 35px; vertical-align: middle;" id="assignC">Assigned New Pre-course</button>
                    <div id="assignCDiv" class="group-form-input">
                        <input id="searchCValue2" placeholder="Assign Precourse" style="margin-right: 20px; height: 30px;float: left; width: 155px;">
                        <span class="w_button w_add" id="addc-button2" style="width: 145px; text-align: center;">Change Precourse</span>
                        <span class="w_button w_add" id="cancelC" style="width: 100px; text-align: center; background-color: #3d8ca7;">Cancel</span>
                        <ul class="w_selected4">
                        </ul>
                    </div>
                    <h4 class="status" style="margin-top: 0px;">Current day of the Course: </h4>
                    <input class='m' type="checkbox" name="newDay" value="m"
                           style="margin: 10px 4px 10px 0; width: 12px;"/>Mon
                    <input class='t' type="checkbox" name="newDay" value="t"
                           style="margin: 10px 4px 10px 0; width: 12px;"/>Tue
                    <input class='w' type="checkbox" name="newDay" value="w"
                           style="margin: 10px 4px 10px 0; width: 12px;"/>Wed
                    <input class='tr' type="checkbox" name="newDay" value="tr"
                           style="margin: 10px 4px 10px 0; width: 12px;"/>Thr
                    <input class='f' type="checkbox" name="newDay" value="f"
                           style="margin: 10px 4px 10px 0; width: 12px;"/>Fri
                    <input class='sa' type="checkbox" name="newDay" value="sa"
                           style="margin: 10px 4px 10px 0; width: 12px;"/>Sat
                    <input class='s' type="checkbox" name="newDay" value="s"
                           style="margin: 10px 4px 10px 0; width: 12px;"/>Sun
                    <span class="w_button w_add" id="change-day" style="width: 145px; text-align: center;">Change Day</span>
                    <span class="w_button w_add" id="cancelD" style="width: 100px; text-align: center; background-color: #3d8ca7;">Cancel</span>
                </div>
                <div class='w_manage_btn system-control-btn'>
                    <button id="cancel2" class="w_button">Exit</button>
                </div>
            </div>
            <div class="w_tabE w_pop">
                <h4 class="type">Add a student to course: </h4>
                <button class="w_button" style="width: 190px; vertical-align: middle;" id="assignS">Add Students</button>
                <div id="assignSDiv" class="group-form-input">
                    <h4 class="status" style="margin-top: 0px;">Override Options: </h4>
                    <table style="font-size: 11px">
                        <tr>
                            <td style="margin-bottom: -5px"><input type="checkbox" name="pre" style="margin: 10px 4px 10px 0; width: 12px;"/>Override Without Pre-Required
                                Class
                            </td>
                        </tr>
                        <tr>
                            <td><input type="checkbox" name="time" style="margin: 10px 4px 10px 0; width: 12px;"/>Override with time confilct
                            </td>
                        </tr>
                        <tr>
                            <td><input type="checkbox" name="capa"
                                       style="margin: 10px 4px 10px 0; width: 12px;"/>Override even over course capacity
                            </td>
                        </tr>
                    </table>
                    <hr style="margin-top: 1px;"/>
                    <h4 class="status" style="margin-top: -10px; margin-bottom: 5px">Lists of Active Students: </h4>
                    <div class="content clearfix" style="padding: 0px">
                        <div class='course-table'>
                            <table width="100%" style="font-size: 14px;" id="studentTable" class="display dataTable">
                            </table>
                        </div>
                    </div> <!-- /content -->
                    <span class="w_button w_add" id="cancelS" style="width: 100px; text-align: center; background-color: #3d8ca7;">Cancel</span>
                </div>
                <div class='w_manage_btn system-control-btn'>
                    <button id="cancel3" class="w_button" style="margin: 20px 0 0 122px;width: 190px">Exit</button>
                </div>
            </div>

        </div>
    </div>
</div>

<div id="student" class="w_wrapper">
    <div class="w_wrap">
        <div class="w_head">
            <span>Course Controller</span>
            <span class="w_close">×</span>
        </div>
        <div class="w_body" style="margin-top: 21px;">
                <div class="content clearfix" style="padding: 0px">
                    <h4 class="type">List of Students in Course: </h4>
                    <hr style="margin: 7px 0 7px 0;"/>
                    <table width="100%" style="font-size: 14px;" id="studentList" class="display dataTable">
                    </table>
                </div> <!-- /content -->
            </div>
            <div class='w_manage_btn system-control-btn'>
                <button id="cancel4" class="w_button" style="margin: -14px 0 25px 167px;width: 123px;">Exit</button>
            </div>
         </div>
    </div>


<div id="confirm-wrapper" class="w_wrapper">
    <div class="w_wrap">
        <div class="w_head">
            <span>Course Delete</span>
            <span class="w_close">×</span>
        </div>
        <div class='w_manage_btn system-control-btn'>
            <button id="confirm-delete" class="w_button" style="float:left; margin: 20px 0 25px 50px;width: 123px;">Confirm</button>
        </div>
        <div class='w_manage_btn system-control-btn'>
            <button id="cancel-delete" class="w_button" style="margin: 20px 0 25px 50px;width: 123px;">Cancel</button>
        </div>
    </div>
</div>
<div class='delete-user-pop'>
    <div class='delete-user'>
        <div class='true-delete'>确认删除<span>×</span></div>
        <div class='true-delete-user'>确认删除XXX用户吗？</div>
        <div class='delete-btn'>
            <button class='true w_button'>确认</button>
            <button class='cancel w_button'>取消</button>
        </div>
    </div>
</div>
</body>
<script src="${basePath}/static/plugins/jquery-validate/jquery.validate.min.js"></script>
<script src="${basePath}/static/plugins/jquery-validate/messages_zh.js"></script>
<script src="${basePath}/static/js/adminJs/manageCourse.js"></script>
</html>
