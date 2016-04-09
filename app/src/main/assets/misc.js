/**
 * Created by chinmel on 16/4/10.
 */
var OwnCID = 0;
var OwnID = 0;

function friendDIVFind(id) {
    var s = $('#friends').children('#DIVFriend' + id);
    if (s.length) {
        return s;
    }
    else {
        return null;
    }
}

function friendIDFind(friendLineDIV) {
    var s = friendLineDIV.attr("id");
    if (s.indexOf("#DIVFriend")) {
        return s.substring(9);
    }
    else {
        return null;
    }
}

function friendListIsNOTLastOne() {
    var s = $('#friends').children('.friendLine');
    if (s.length > 1) {
        return s.length;
    }
    else {
        return 0;
    }
}

function chatListRefresh(chatMSGDIV) {
    var friend_ = chatMSGDIV.children('.message').not('.sendRecord');
    var self_ = chatMSGDIV.children('.message.sendRecord');
    //时间处理
    var tp = jsDateDiff(friend_.find('span[tsp]').attr('tsp'));
    friend_.find('span[tsp]').html(tp);
    var tp = jsDateDiff(self_.find('span[tsp]').attr('tsp'));
    self_.find('span[tsp]').html(tp);

    //是否需要添加天数,用最后一条label消息计算
    var ll = chatMSGDIV.find('label[tsp]');
    if (0 == ll.length) {
        //需要添加
        ts = new Date();
        var y = ts.getFullYear();
        var m = ts.getMonth() + 1;
        var d = ts.getDate();
        tp = y + "年" + m + "月" + d + "日";
        ts = ts.getTime() / 1000;

        chatMSGDIV.append($('<label tsp="' + ts + '">' + tp + '</label>'));
        return;
    }

    var ts = ll.last().attr('tsp');
    var tp = jsDateDiff(ts);
    if (-1 != tp.indexOf("天前") || -1 != tp.indexOf("年")) {
        //需要添加
        ts = new Date();
        var y = ts.getFullYear();
        var m = ts.getMonth() + 1;
        var d = ts.getDate();
        tp = y + "年" + m + "月" + d + "日";
        ts = ts.getTime() / 1000;

        chatMSGDIV.append($('<label tsp="' + ts + '">' + tp + '</label>'));
    }

    //显示历史
    ll.each(function () {
        ts = new Date($(this).attr('tsp') * 1000);
        var y = ts.getFullYear();
        var m = ts.getMonth() + 1;
        var d = ts.getDate();
        tp = y + "年" + m + "月" + d + "日";
        $(this).html(tp);

    });

}

function friendsSetAction() {
    $('.friendLine').each(function () {
        $('.friend').unbind('click').on("click", function () {
            var childOffset = $(this).offset();
            var parentOffset = $(this).parent().parent().offset();
            var childTop = childOffset.top - parentOffset.top;
            var clone = $(this).find('img').eq(0).clone();
            var top = childTop + 12 + 'px';
            $(clone).css({ 'top': top }).addClass('floatingImg').appendTo('#chatbox');

            var id = friendIDFind($(this).parent('.friendLine'));
            var chatDIV = $('#chat-messages[ids="' + id + '"]');

            setTimeout(function () {
                $('#profile p').addClass('animate');
                $('#profile').addClass('animate');
            }, 100);

            setTimeout(function () {
                chatDIV.addClass('animate');
                $('.cx, .cy').addClass('s1');
                setTimeout(function () {
                    $('.cx, .cy').addClass('s2');
                }, 100);
                setTimeout(function () {
                    $('.cx, .cy').addClass('s3');
                }, 200);
            }, 150);
            $('.floatingImg').animate({
                'width': '68px',
                'left': '108px',
                'top': '20px'
            }, 200);
            var name = $(this).find('p strong').html();
            var email = $(this).find('p span span').html();
            //chinmel @ WuHu，20160220
            // 其实聊天记录的显示永远是同一个界面，只不过数据不一样罢了
            $('#profile p').html(name);
            $('#profile span').html(email);

            var friend_ = chatDIV.children('.message').not('.sendRecord');
            var self_ = chatDIV.children('.message.sendRecord');
            //右侧消息，sendRecord，属于自己发给对面的
            var cloneSelf = $('<img src="img/logo.png"></img>');
            self_.find('img').attr('src', $(cloneSelf).attr('src'));
            friend_.find('img').attr('src', $(clone).attr('src'));

            chatListRefresh(chatDIV);

//            clearInterval(GNewMSG);
            $(this).parent().find('.status').attr("class", "status available");
            $(this).parent().find('.status').show();

            chatDIV.attr("actived", "true");
            chatDIV.css("display", "block");
            chatDIV.show();

            $('#friendslist').fadeOut();
            $('#chatview').fadeIn();


            chatDIV.animate({scrollTop: $('.message:last').offset().top}, 1000);


            $('#close').unbind('click').unbind('click').click(function () {
                chatDIV.removeAttr('actived');
                chatDIV.removeAttr('style');
                $('#chat-messages[ids="' + id + '"], #profile, #profile p').removeClass('animate');
                $('.cx, .cy').removeClass('s1 s2 s3');
                $('.floatingImg').animate({
                    'width': '40px',
                    'top': top,
                    'left': '12px'
                }, 200, function () {
                    $('.floatingImg').remove();
                });
                setTimeout(function () {
                    $('#chatview').fadeOut();
                    $('#friendslist').fadeIn();
                }, 50);
            });


        });

        $('.friendDelete').unbind('click').on("click", function () {
            var id = friendIDFind($(this).parent());
            var chatDIV = $('#chat-messages[ids="' + id + '"]');
            $(this).parent().remove();
            chatDIV.remove();
        });
    });


}

function MainSetAction() {
    var preloadbg = document.createElement('img');
    preloadbg.src = 'img/timeline1.png';
    $('#searchfield').val('请输入好友匿名号');
    $('#searchfield').focus(function () {
        if ($(this).val() == '请输入好友匿名号') {
//            $(this).attr("type", "number");
            $(this).val('');
        }
    });
    $('#searchfield').focusout(function () {
        if ($(this).val() == '') {
//            $(this).attr("type", "text");
            $(this).val('请输入好友匿名号');
        }
    });
    $('#sendmessage input').focus(function () {
        if ($(this).val() == '发送消息...') {
            $(this).val('');
        }
    });
    $('#sendmessage input').focusout(function () {
        if ($(this).val() == '') {
            $(this).val('发送消息...');
        }
    });

    //自动滚动到最后


//    $('#chat-messages').each(function () {
//        $(this).unbind('DOMNodeInserted').on("DOMNodeInserted", function (e) {
//            $(this).animate({scrollTop: $('.message:last').offset().top}, 500);
//        });
//    });
//
//    $('#chat-messages').unbind('DOMNodeRemoved').on("DOMNodeRemoved", function (e) {
//
//        $('#chat-messages').animate({scrollTop: $('.message:last').offset().top}, 500);
//    });

    $('#friends').unbind('DOMNodeInserted').on("DOMNodeInserted", function (e) {
        $('#friends').animate({scrollTop: $('.friendLine:last').offset().top}, 500);
    });
    $('#friends').unbind('DOMNodeRemoved').on("DOMNodeRemoved", function (e) {
        if (friendListIsNOTLastOne()) {
            $('#friends').animate({scrollTop: $('.friendLine:last').offset().top}, 500);
        } else {
//                $('#searchfield').attr("type", "text");
//                $('#searchfield').val('请输入好友匿名号');

        }
    });

    $("#BTNFriendAdd").unbind('click').on('click', function () {
        if (false == isNaN($("#searchfield").val()) && ('' != $("#searchfield").val()) && ("0" != $("#searchfield").val()) && (4294967295 >= parseInt($("#searchfield").val(), 10))) {
            var x = friendDIVFind($("#searchfield").val());
            if (null == x) {
                friendAdd("[" + "昵称获取中..." + "]", $("#searchfield").val(), "anyn.png", "available");

                $("#searchfield").attr("type", "text");
                $("#searchfield").val("请输入好友匿名号");
            } else {

                var y = 0;
                if ($(x).offset().top >= $('#friends').offset().top) {
                    y = $(x).offset().top - $('#friends').offset().top;
                } else {
                    y = 0;
                }
//                            alert($('#friends').scroll().top);
                $('#friends').animate({scrollTop: y}, 500);

                $(x).children('.friend').parent().css("background-color", "#aab8c2");
                setTimeout(function () {
                    $(x).children('.friend').parent().css("background-color", '');
                }, 500);
            }

            friendDIVFind($("#searchfield").val()).click();
        } else {
            alert("好友匿名号,必须为数字,且不要太大");
            $("#searchfield").attr("type", "text");
            $("#searchfield").val("请输入好友匿名号");
        }
    });

    $("#BTNMSGSend").unbind('click').on('click',
        function () {
            var id = $('#chat-messages[actived]').attr('ids');
//                anyMSGSend(OwnID, id, $("#TEXTMSGSend").val(), anyMSGSendCallbackFNC);
            window.webViewJS2Java.clickOnSend(id, $("#TEXTMSGSend").val());

            chatRecordAddByID("sendRecord", id, $("#TEXTMSGSend").val(), parseInt(new Date().getTime() / 1000));
            $("#TEXTMSGSend").val('');
        });

    friendsSetAction();
}

function friendAdd(name, id, pic, status) {
    var ids = id;
    alert("添加了好友：" + OwnCID);
    if (0 == id) {
        ids = "您的匿名号：" + OwnCID;
//        id = OwnCID;
    }

    if (null != friendDIVFind(id)) return false;

    var divString = '<div class="friendLine" id="DIVFriend' + id + '"></div>';
    var friendLineDIV = $(divString);

    var FS = '<div title="点击进入聊天" class="friend"><p><img src="img/' + pic + '"/><span><br><strong>' + name + ' </strong><br><span>' + ids + '</span></br></span></p></div>';
    var friendDIV = $(FS);

    var S = '<div class="status ' + status + '"></div>';
    var SDIV = $(S);
    var D = '<div title="删除好友" class="friendDelete"><span class="icon-bin"></span></div>';

    friendDIV.width($('#friendslist').width() - 40);
    friendLineDIV.append(friendDIV);
    friendLineDIV.append(SDIV);
    friendLineDIV.append($(D));

    friendLineDIV.appendTo($('#friends'));
    var divString = '<div id="chat-messages" ids="' + id + '"></div>';
    $('#DIVMSGHistory').append($(divString));

    friendsSetAction();
}

function chatRecordAddByID(recordType, id, msg, timeStamp) {
    var obj = friendDIVFind(id);
    if (null == obj) return false;

    var clone = obj.find('img').eq(0).clone();
    var divString = '<div class="message ' + recordType + '"><img src="' + $(clone).attr('src') + '"/><div class="bubble">' + msg + '<div class="corner"></div><span tsp="' + timeStamp + '">刚刚</span></div></div>';
    $('#chat-messages[ids="' + id + '"]').append($(divString));

    //当收到消息时好友列表应当有所提示
    if ("RecvRecord" == recordType) {
        var so = $('#friends').children('#DIVFriend' + id).find('.status');
        so.attr("class", "status newMSG");
    }
    $('#chat-messages[ids="' + id + '"]').animate({scrollTop: $('.message:last').offset().top}, 500);
}


function jsDateDiff(publishTime) {
    var d_minutes, d_hours, d_days;
    var timeNow = parseInt(new Date().getTime() / 1000);
    var d;
    d = timeNow - publishTime;
    d_days = parseInt(d / 86400);
    d_hours = parseInt(d / 3600);
    d_minutes = parseInt(d / 60);
    if (d_days > 0 && d_days < 4) {
        return d_days + "天前";
    } else if (d_days <= 0 && d_hours > 0) {
        return d_hours + "小时前";
    } else if (d_hours <= 0 && d_minutes > 0) {
        return d_minutes + "分钟前";
    } else if (d_minutes <= 0) {
        return d + "秒前";
    } else {
        var s = new Date(publishTime * 1000);
        // s.getFullYear()+"年";
        return (s.getFullYear() + "年" + s.getMonth() + 1) + "月" + s.getDate() + "日";
    }
}
//================================================
function anyMSGSendCallbackFNC(jsonObj) {
    try {
        switch (jsonObj.opr.code) {
            case 2000:/*消息已经投递*/
            {
                $("#SPANMSGSystem").html("消息已经投递");
                break;
            }
            case 4000:/*解码数据时出了点问题*/
            {
                $("#SPANMSGSystem").html("解码数据时出了点问题");
                IsLogin = false;
                location.replace(location);
                break;
            }
            case 4001:/*UID或者消息接收方地址，看起来有点问题*/
            {
                $("#SPANMSGSystem").html("UID或者消息接收方地址错误");
                IsLogin = false;
                location.replace(location);
                break;
            }
            case 4010:/*要求登录*/
            {
                $("#SPANMSGSystem").html("要求登录");
                IsLogin = false;
                location.replace(location);
                break;
            }
            case 5030:/*过载,暂时无法处理*/
            {
                $("#SPANMSGSystem").html("暂时无法处理");
                break;
            }
            case 5000:/*服务尚未初始化完毕*/
            {
                $("#SPANMSGSystem").html("服务尚未初始化完毕");
                IsLogin = false;
                location.replace(location);
                break;
            }
            case 5001:/*服务尚未初始化完毕*/
            {
                $("#SPANMSGSystem").html("服务初始化错误");
                IsLogin = false;
                location.replace(location);
                break;
            }
        }
    } catch (e) {

    }
}

function anyMSGLoginCallbackFNC(jsonObjs) {
    eval("var jsonObj = " + jsonObjs);
//    alert("oprCode:" + jsonObj.opr.code);
    try {
        switch (jsonObj.opr.code) {
            case 2001:/*登录OK*/
            {
                OwnCID = jsonObj.opr.desp;
                OwnID = $("#srcids").val();

                MainSetAction();
                friendAdd("[anyMSG私有云]", 0, "server.png", "available");
                chatRecordAddByID("RecvRecord", 0, "与骨干网的链路已建立。anyMSG帐号:" + $("#srcids").val() + "，您的匿名号:" + jsonObj.opr.desp, parseInt(new Date().getTime() / 1000));

                $("#DIVanyMSGLoginMask").hide();
                $("#DIVanyMSGMainMask").show();
                $("#SPANMSGSystem").html("登录OK");
                $("#SPANMSGID").html("anyMSG帐号:" + $("#srcids").val() + "，匿名号:" + OwnCID + "|");
                $("#DIVsysTitle").hide();
                break;
            }
            case 2002:/*接收到消息*/
            {
                alert(OwnCID);
                friendAdd("[昵称获取中...]", jsonObj.msg.srcID, "anyn.png", "available");
                if (jsonObj.msg.desID != OwnCID) {
                    chatRecordAddByID("RecvRecord", 0, "发往" + jsonObj.msg.desID + "的消息:" + jsonObj.msg.content, parseInt(new Date().getTime() / 1000));
                }
                else {
                    chatRecordAddByID("RecvRecord", jsonObj.msg.srcID, jsonObj.msg.content, parseInt(new Date().getTime() / 1000));
                }

                break;
            }
            case 2003:/*发送OK*/
            {
                friendAdd("[昵称获取中...]", jsonObj.msg.srcID, "anyn.png", "available");
                chatRecordAddByID("RecvRecord", jsonObj.opr.desp, "消息已经收到", parseInt(new Date().getTime() / 1000));
                break;
            }
            case 2010:/*登录成功后无法创建队列*/
            {
                friendAdd("[anyMSG私有云]", 0, "server.png", "available");
                chatRecordAddByID("RecvRecord", 0, "登录成功后无法创建队列", parseInt(new Date().getTime() / 1000));
                break;
            }
            case 2060:/*心跳包发送*/
            {
                $("#SPANMSGSystem").html("心跳包发送");
                break;
            }
            case 2062:/*心跳包收到*/
            {
                $("#SPANMSGSystem").html("骨干网链路正常");
                setTimeout(function () {
                    $("#SPANMSGSystem").html('');
                }, 1000);

                break;
            }
            case 2061:/*正在登录*/
            {
                friendAdd("[anyMSG私有云]", 0, "server.png", "available");
                chatRecordAddByID("RecvRecord", 0, "正在登录，请稍后", parseInt(new Date().getTime() / 1000));
                break;
            }
            case 4000:/*解码数据时出了点问题*/
            {
                friendAdd("[anyMSG私有云]", 0, "server.png", "available");
                chatRecordAddByID("RecvRecord", 0, "解码数据时出了点问题", parseInt(new Date().getTime() / 1000));
                break;
            }
            case 4001:/*UID或者消息接收方地址，看起来有点问题*/
            {
                friendAdd("[anyMSG私有云]", 0, "server.png", "available");
                chatRecordAddByID("RecvRecord", 0, "UID或者消息接收方地址错误", parseInt(new Date().getTime() / 1000));
                break;
            }
            case 4010:/*要求登录*/
            {
                friendAdd("[anyMSG私有云]", 0, "server.png", "available");
                chatRecordAddByID("RecvRecord", 0, "请求的服务必须登录", parseInt(new Date().getTime() / 1000));
                IsLogin = false;
                location.replace(location);
                break;
            }
            case 5030:/*过载,暂时无法处理*/
            {
                friendAdd("[anyMSG私有云]", 0, "server.png", "available");
                chatRecordAddByID("RecvRecord", 0, "过载,暂时无法处理", parseInt(new Date().getTime() / 1000));
                break;
            }
            case 5000:/*服务尚未初始化完毕*/
            {
                friendAdd("[anyMSG私有云]", 0, "server.png", "available");
                chatRecordAddByID("RecvRecord", 0, "服务尚未初始化完毕", parseInt(new Date().getTime() / 1000));
                IsLogin = false;
                location.replace(location);
                break;
            }
            case 5001:/*服务尚未初始化完毕*/
            {
                friendAdd("[anyMSG私有云]", 0, "server.png", "available");
                chatRecordAddByID("RecvRecord", 0, "服务初始化错误", parseInt(new Date().getTime() / 1000));
                IsLogin = false;
                location.replace(location);
                break;
            }
        }
    } catch (e) {

    }
}