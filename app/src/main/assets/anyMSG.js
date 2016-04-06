/**
 * Created by chinmel on 16/2/17.
 * 请注意务必在本脚本之前包含jquery 1.9 以上
 *     <script type="text/javascript" src="jquery-1.12.0.js"></script>
 *     <script type="text/javascript" src="jquery-ajaxreadystate.js"></script>
 */

//#define ERRCODE_SUCCESS_MAILED		2000	/*消息已经投递*/
//#define ERRCODE_SUCCESS_LOGINOK		2001	/*登录OK*/
//#define ERRCODE_SUCCESS_RECEIVED	    2002	/*接收到消息*/
//#define ERRCODE_SUCCESS_SENDOK		2003	/*发送OK*/
//#define ERRCODE_SUCCESS_LOGINRETRY	2010	/*登录成功后无法创建队列*/
//#define ERRCODE_SUCCESS_HB			2060	/*心跳包发送*/
//#define ERRCODE_SUCCESS_HBP			2062	/*心跳包收到*/
//#define ERRCODE_SUCCESS_LOGINING	2061	/*正在登录*/
//
////======================================
//#define ERRCODE_REQUEST_PARMS		4000	/*解码数据时出了点问题*/
//#define ERRCODE_REQUEST_BADID		4001	/*UID或者消息接收方地址，看起来有点问题*/
//#define ERRCODE_REQUEST_UNAUTH		4010	/*要求登录*/
////======================================
//#define ERRCODE_SERVER_OVERLOAD		5030	/*过载,暂时无法处理*/
//#define ERRCODE_SERVER_UNINIT		5000	/*服务尚未初始化完毕*/
//#define ERRCODE_SERVER_INITERROR	5001	/*服务初始化错误*/

var IsLogin = false;
var OwnCID = 0;
var OwnID = 0;
var BufferLast = 0;
var BufferLastCount = 0;
var GErrSpliter = Math.uuid(6);
function anyMSGSend(srcID, desID, msg,callbackFNC) {
    $.ajaxreadystate({
        type: "POST",
        dataType: "json",
        url: "/cgi-bin/s.cgi",
        data: {
            "msg": {
                "srcID": srcID,
                "desID": desID,
                "content": msg,
                "spliter": GErrSpliter
            }
        },
        readystate: function (jqXHR, readyState) {
            if (3 == readyState) {
                var x = jqXHR.responseText;
                var y = x.substring(x.lastIndexOf('{"opr"'));
                callbackFNC($.parseJSON(y));

            }
        }
    });
}


function anyMSGLogin(val, callbackFNC) {
    $.ajaxreadystate({
        type: "POST",
        dataType: "json",
        url: "/cgi-bin/r.cgi",
        data: {
            "msg": {
                "srcID": $("#srcids").val(),
                "spliter": GErrSpliter
            }
        },
        readystate: function (jqXHR, readyState) {
            if (3 == readyState) {
                var xx = jqXHR.responseText.substring(BufferLastCount);
                var x = xx.split(GErrSpliter);
                console.log("xx=" + xx + ",x.len=", x.length);
                $.each(x,function(n,v){
                    if(0 == v.length){
                     BufferLastCount += 6;
                     return true;
                    }
                    else if(-1 == v.indexOf('{"opr"')){
                        BufferLastCount += v.length;
                        return true;
                    }
                    else{
                        console.log("  x[" + n + "]=" + v);
                        callbackFNC($.parseJSON(v));

                        BufferLastCount += v.length;
                        BufferLastCount += 6;

                    }

                });
                BuffLast = xx;
                console.log("    BufferLastCount=",BufferLastCount);

            }
        }
    });
}
