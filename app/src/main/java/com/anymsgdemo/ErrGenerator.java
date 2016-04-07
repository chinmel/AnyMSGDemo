package com.anymsgdemo;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by chinmel on 16/4/7.
 */
public class ErrGenerator {
    public static JSONObject opration(int oprCode) {
        JSONObject root = new JSONObject();
        JSONObject opr = new JSONObject();
        try {
            root.put("opr", opr);
            opr.put("code", oprCode);
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
        }

        return (root);
    }

    public static JSONObject opration(int oprCode, String oprDescription) {
        JSONObject root = new JSONObject();
        JSONObject opr = new JSONObject();
        try {
            root.put("opr", opr);
            opr.put("code", oprCode);
            if (null != oprDescription && oprDescription.length() > 0)
                opr.put("desp", oprDescription);
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {

        }

        return (root);
    }

    public static JSONObject message(int srcID, int desID) {
        JSONObject root = new JSONObject();
        JSONObject opr = new JSONObject();
        JSONObject msg = new JSONObject();
        try {
            root.put("opr", opr);
            opr.put("code", ErrCode.ERRCODE_SUCCESS_RECEIVED);

            root.put("msg", msg);
            msg.put("srcID", srcID);
            msg.put("desID", desID);
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
        }

        return (root);
    }

    public static JSONObject message(int srcID, int desID, String msgString) {
        JSONObject root = new JSONObject();
        JSONObject opr = new JSONObject();
        JSONObject msg = new JSONObject();
        try {
            root.put("opr", opr);
            opr.put("code", ErrCode.ERRCODE_SUCCESS_RECEIVED);

            root.put("msg", msg);
            msg.put("srcID", srcID);
            msg.put("desID", desID);
            if (null != msgString && msgString.length() > 0)
                msg.put("content", msgString);
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
        }

        return (root);
    }

    public static JSONObject message(int srcID, int desID, String msgString, String timeString, String miscString) {
        JSONObject root = new JSONObject();
        JSONObject opr = new JSONObject();
        JSONObject msg = new JSONObject();
        try {
            root.put("opr", opr);
            opr.put("code", ErrCode.ERRCODE_SUCCESS_RECEIVED);

            root.put("msg", msg);
            msg.put("srcID", srcID);
            msg.put("desID", desID);
            if (null != msgString && msgString.length() > 0)
                msg.put("content", msgString);
            if (null != timeString && timeString.length() > 0)
                msg.put("time", timeString);
            if (null != miscString && miscString.length() > 0)
                msg.put("misc", miscString);
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
        }

        return (root);
    }

    public interface ErrCode {
        int ERRCODE_SUCCESS_MAILED = 2000;	/*发往%lu的消息已经投递*/
        int ERRCODE_SUCCESS_LOGINOK = 2001;	/*登录OK*/
        int ERRCODE_SUCCESS_RECEIVED = 2002;	/*接收到消息*/
        int ERRCODE_SUCCESS_SENDOK = 2003;	/*发送OK*/
        int ERRCODE_SUCCESS_LOGINRETRY = 2010;	/*登录成功后无法创建队列*/
        int ERRCODE_SUCCESS_HB = 2060;	/*心跳包发送*/
        int ERRCODE_SUCCESS_HBP = 2062;	/*心跳包收到*/
        int ERRCODE_SUCCESS_LOGINING = 2061;	/*正在登录*/

        //======================================
        int ERRCODE_REQUEST_PARMS = 4000;	/*"[解码数据时出了点问题]"*/
        int ERRCODE_REQUEST_BADID = 4001;	/*"[您的UID或者消息接收方地址，看起来有点问题]"*/
        int ERRCODE_REQUEST_UNAUTH = 4010;	/*要求登录*/
        //======================================
        int ERRCODE_SERVER_OVERLOAD = 5030;	/*过载,暂时无法处理*/
        int ERRCODE_SERVER_UNINIT = 5000;	/*服务尚未初始化完毕*/
        int ERRCODE_SERVER_INITERROR = 5001;	/*服务初始化错误 */
    }

/*
 HTTP状态码列表
 消息（1字头）服务器收到请求，需要请求者继续执行操作
 状态码	状态码英文名称	中文描述
 100	Continue	继续。客户端应继续其请求
 101	Switching Protocols	切换协议。服务器根据客户端的请求切换协议。只能切换到更高级的协议，例如，切换到HTTP的新版本协议
 102	Processing	由WebDAV（RFC 2518）扩展的状态码，代表处理将被继续执行。

 成功（2字头）操作被成功接收并处理
 状态码	状态码英文名称	中文描述
 200	OK	请求成功。一般用于GET与POST请求
 201	Created	已创建。成功请求并创建了新的资源
 202	Accepted	已接受。已经接受请求，但未处理完成
 203	Non-Authoritative Information	非授权信息。请求成功。但返回的meta信息不在原始的服务器，而是一个副本
 204	No Content	无内容。服务器成功处理，但未返回内容。在未更新网页的情况下，可确保浏览器继续显示当前文档
 205	Reset Content	重置内容。服务器处理成功，用户终端（例如：浏览器）应重置文档视图。可通过此返回码清除浏览器的表单域
 206	Partial Content	部分内容。服务器成功处理了部分GET请求
 207	Multi-Status	由WebDAV(RFC 2518)扩展的状态码，代表之后的消息体将是一个XML消息，并且可能依照之前子请求数量的不同，包含一系列独立的响应代码。

 重定向（3字头）需要进一步的操作以完成请求
 状态码	状态码英文名称	中文描述
 300	Multiple Choices	多种选择。请求的资源可包括多个位置，相应可返回一个资源特征与地址的列表用于用户终端（例如：浏览器）选择
 301	Moved Permanently	永久移动。请求的资源已被永久的移动到新URI，返回信息会包括新的URI，浏览器会自动定向到新URI。今后任何新的请求都应使用新的URI代替
 302	Move temporarily	临时移动。与301类似。但资源只是临时被移动。客户端应继续使用原有URI
 303	See Other	查看其它地址。与301类似。使用GET和POST请求查看
 304	Not Modified	未修改。所请求的资源未修改，服务器返回此状态码时，不会返回任何资源。客户端通常会缓存访问过的资源，通过提供一个头信息指出客户端希望只返回在指定日期之后修改的资源
 305	Use Proxy	使用代理。所请求的资源必须通过代理访问
 306	Unused	已经被废弃的HTTP状态码
 307	Temporary Redirect	临时重定向。与302类似。使用GET请求重定向

 请求错误（4字头）客户端错误，请求包含语法错误或无法完成请求
 状态码	状态码英文名称	中文描述
 400	Bad Request	客户端请求的语法错误，服务器无法理解
 401	Unauthorized	请求要求用户的身份认证
 402	Payment Required	保留，将来使用
 403	Forbidden	服务器理解请求客户端的请求，但是拒绝执行此请求
 404	Not Found	服务器无法根据客户端的请求找到资源（网页）。通过此代码，网站设计人员可设置“您所请求的资源无法找到”的个性页面
 405	Method Not Allowed	客户端请求中的方法被禁止
 406	Not Acceptable	服务器无法根据客户端请求的内容特性完成请求
 407	Proxy Authentication Required	请求要求代理的身份认证，与401类似，但请求者应当使用代理进行授权
 408	Request Time-out	服务器等待客户端发送的请求时间过长，超时
 409	Conflict	服务器完成客户端的PUT请求是可能返回此代码，服务器处理请求时发生了冲突
 410	Gone	客户端请求的资源已经不存在。410不同于404，如果资源以前有现在被永久删除了可使用410代码，网站设计人员可通过301代码指定资源的新位置
 411	Length Required	服务器无法处理客户端发送的不带Content-Length的请求信息
 412	Precondition Failed	客户端请求信息的先决条件错误
 413	Request Entity Too Large	由于请求的实体过大，服务器无法处理，因此拒绝请求。为防止客户端的连续请求，服务器可能会关闭连接。如果只是服务器暂时无法处理，则会包含一个Retry-After的响应信息
 414	Request-URI Too Large	请求的URI过长（URI通常为网址），服务器无法处理
 415	Unsupported Media Type	服务器无法处理请求附带的媒体格式
 416	Requested range not satisfiable	客户端请求的范围无效
 417	Expectation Failed	服务器无法满足Expect的请求头信息

 服务器错误（5字头）服务器在处理请求的过程中发生了错误
 状态码	状态码英文名称	中文描述
 500	Internal Server Error	服务器内部错误，无法完成请求
 501	Not Implemented	服务器不支持请求的功能，无法完成请求
 502	Bad Gateway	充当网关或代理的服务器，从远端服务器接收到了一个无效的请求
 503	Service Unavailable	由于超载或系统维护，服务器暂时的无法处理客户端的请求。延时的长度可包含在服务器的Retry-After头信息中
 504	Gateway Time-out	充当网关或代理的服务器，未及时从远端服务器获取请求
 505	HTTP Version not supported	服务器不支持请求的HTTP协议的版本，无法完成处理
*/
//======================================
//            #define ERRCODE_SUCCESS_MAILED		2000	/*发往%lu的消息已经投递*/
//            #define ERRCODE_SUCCESS_LOGINOK		2001	/*登录OK*/
//            #define ERRCODE_SUCCESS_RECEIVED	2002	/*接收到消息*/
//            #define ERRCODE_SUCCESS_SENDOK		2003	/*发送OK*/
//            #define ERRCODE_SUCCESS_LOGINRETRY	2010	/*登录成功后无法创建队列*/
//            #define ERRCODE_SUCCESS_HB			2060	/*心跳包发送*/
//            #define ERRCODE_SUCCESS_HBP			2062	/*心跳包收到*/
//            #define ERRCODE_SUCCESS_LOGINING	2061	/*正在登录*/
//
////======================================
//            #define ERRCODE_REQUEST_PARMS		4000	/*"[🤔解码数据时出了点问题]"*/
//            #define ERRCODE_REQUEST_BADID		4001	/*"[🤔您的UID或者消息接收方地址，看起来有点问题]"*/
//            #define ERRCODE_REQUEST_UNAUTH		4010	/*要求登录*/
////======================================
//            #define ERRCODE_SERVER_OVERLOAD		5030	/*过载,暂时无法处理*/
//            #define ERRCODE_SERVER_UNINIT		5000	/*服务尚未初始化完毕*/
//            #define ERRCODE_SERVER_INITERROR	5001	/*服务初始化错误 */
//======================================
/*
anyMSG 接口格式，符合json标准。请注意对象及其成员，都是无序排列的。
一般来说，opr对象及其成员code是必须的，其它均可选
另外，msg对象中的spliter成员，在通信建立时是必须的,后续通信不可改变。
{
	"opr":{
		"code"		: "错误码参照上表",
		"desp"		: "描述，通常为cid或uid"
	},

	"msg":{
		"srcID"		: 原地址,
		"desID"		: 目的地址,
		"content"	: "内容",
		"time"		:"时间戳",
		"misc"		: "杂项",
		"spliter"	:"6位字符串，UUID"
	}
}
*/
}
