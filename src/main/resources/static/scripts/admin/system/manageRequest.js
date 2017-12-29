// $(function () {
//     let id, userJson, viewStatus = "";
//
//     $("#general").click(function () {
//         viewStatus = "";
//         userReg.draw();
//     });
//     $("#active").click(function () {
//         viewStatus = "0";
//         userReg.draw();
//     });
//     $("#declined").click(function () {
//         viewStatus = "-1";
//         userReg.draw();
//     });
//     $("#approved").click(function () {
//         viewStatus = "1";
//         userReg.draw();
//     });
//
//     //编辑用户跳转
//     $("#userReg").on("click", ".btn.btn-edit", function () {
//         id = $(this).parents("tr").find("td").eq(0).html();
//         let userid = $(this).parents("tr").find("td").eq(1).html();
//
//         let rowIndex = $(this).parents("tr").index();
//         //此处拿到隐藏列的id
//         userJson = $("#userReg").DataTable().row(rowIndex).data().userJson;
//         let createtime = $(this).parents("tr").find("td").eq(3).html();
//
//         localStorage.clear();
//         localStorage.setItem("user", userJson);
//         localStorage.setItem("createtune", createtime);
//
//
//         rowIndex = $(this).parents("tr").index();
//         let status = $("#userReg").DataTable().row(rowIndex).data().status;
//
//         window.location.href = basePath + "/manage/user/request/detail?id=" + id + "&userid=" + userid + "&status=" + status;
//     });
//
// });
