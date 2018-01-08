var box = "inbox";

$("#write").click(function () {
    $("#writeMail").css({display: "block"});
});

$("#detail").css({display: "none"});
$("#writeMail").css({display: "none"});
$("#refresh").click(function () {
    messageTable.draw();
});
$("#back").click(function () {
    $("#detail").css({display: "none"});
    $("#table").css({display: "block"});
    messageTable.draw();
});

$("#inbox").click(function () {
    box = "inbox";
    $(this).addClass("active").siblings().removeClass("active");
    messageTable.draw();
});

$("#sent").click(function () {
    box = "sent";
    $(this).addClass("active").siblings().removeClass("active");
    messageTable.draw();
});

$("#draft").click(function () {
    box = "draft";
    $(this).addClass("active").siblings().removeClass("active");
    messageTable.draw();
});

$("#important").click(function () {
    box = "important";
    $(this).addClass("active").siblings().removeClass("active");
    messageTable.draw();
});

$("#trash").click(function () {
    box = "trash";
    $(this).addClass("active").siblings().removeClass("active");
    messageTable.draw();
});





function viewDraft(id) {

}

function viewDetail(id) {
    markAsRead(id);
    $.ajax({
        url: basePath + "/message/view?id=" + id,
        type: "GET",
        success: function (data) {
            if (data.code === 2001) {
                var message = data.data;
                $("#detail").css({display: "block"});
                $("#table").css({display: "none"});
                var senderInfo =
                    '<img class="human-picture" src="' + message.pic + '">' +
                    '   <div class="name"><h2 class="name-h ng-binding">发件人：' + message.sender + '</h2>' +
                    '       <div>' +
                    '           <span class="mail-tag tag label family">' + message.tag + '</span>' +
                    '       </div>' +
                    '   </div>';
                $("#senderInfo").html(senderInfo);

                var contactInfo =
                    '<div class="contact-info phone-email">' +
                    '    <div>' +
                    '       <i class="fa fa-phone-square fa-2x"></i> ' +
                    '       <span class="phone"> ' + message.tel + '</span>' +
                    '    </div>' +
                    '    <div>' +
                    '       <i class="fa fa-envelope-square fa-2x"></i> ' +
                    '       <span class="email"> ' + message.email + '</span>' +
                    '    </div>' +
                    '</div>';
                $("#contactInfo").html(contactInfo);

                var roleInfo =
                    '<div class="contact-info position-address">' +
                    '   <div>' +
                    '       <i class="fa fa-user-circle fa-2x"></i>' +
                    '       <span class="position">Technical Chef</span>' +
                    '   </div>' +
                    '   <div>' +
                    '       <i class="fa fa-address-card fa-2x"></i>' +
                    '       <span class="address">12 Nezavisimosti st. Vilnius, Lithuania</span>' +
                    '   </div>' +
                    '</div>';
                $("#roleInfo").html(roleInfo);

                var subject =
                    '<span class="subject ng-binding">' + message.subject + '</span>' +
                    '<span class="date ng-binding">• ' + message.date + ' </span>';
                $("#subject").html(subject);

                var body = '<p>' + message.body + '</p>';
                $("#body").html(body);
            }
            else
                Showbo.Msg.alert("消息获取失败", function () {
                });
        }
    });
}

function markAsUnread(id) {
    var status = "unread";
    sendStatusUpdateAjax(id, status);
}

function markAsRead(id) {
    var status = "read";
    sendStatusUpdateAjax(id, status);
}

function sendStatusUpdateAjax(id, status) {
    $.ajax({
        url: basePath + "/message/update/status?id=" + id + "&status=" + status,
        type: "PUT",
        success: function (data) {
            if (data.code === 2001) {
                init();
                messageTable.draw();
            }
            else
                Showbo.Msg.alert("消息更新失败!", function () {
                });
        }
    });
}

$(function () {
    init();
});

function init() {
    initUnread();
    initDraft();
    initTrash();
    initImportant();
}

function initImportant() {
    $.ajax({
        url: basePath + "/message/count?status=unread&box=important",
        type: "GET",
        async: false,
        success: function (data) {
            if (data.code === 2001) {
                $("#importantCount").text(data.data);
            }
            else
                Showbo.Msg.alert("消息获取失败", function () {
                });
        }
    });
}

function initUnread() {
    $.ajax({
        url: basePath + "/message/count?status=unread&box=inbox",
        type: "GET",
        async: false,
        success: function (data) {
            if (data.code === 2001) {
                $("#inboxCount").text(data.data);
            }
            else
                Showbo.Msg.alert("消息获取失败", function () {
                });
        }
    });
}

function initDraft() {
    $.ajax({
        url: basePath + "/message/count?status=saved&box=draft",
        type: "GET",
        async: false,
        success: function (data) {
            if (data.code === 2001) {
                $("#draftCount").text(data.data);
            }
            else
                Showbo.Msg.alert("消息获取失败", function () {
                });
        }
    });
}

function initTrash() {
    $.ajax({
        url: basePath + "/message/count?status=trashed&box=trash",
        type: "GET",
        async: false,
        success: function (data) {
            if (data.code === 2001) {
                $("#trashCount").text(data.data);
            }
            else
                Showbo.Msg.alert("消息获取失败", function () {
                });
        }
    });
}


