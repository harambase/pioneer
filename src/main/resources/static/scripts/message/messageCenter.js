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




