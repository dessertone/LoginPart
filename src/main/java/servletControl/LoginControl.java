package servletControl;

import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.*;


import org.json.JSONException;
import org.json.JSONObject;
import DBControl.DB;
import EmailControl.EmailSend;
public class LoginControl extends HttpServlet {
    private String file = "servletControl/LoginControl";

    private EmailSend emailSender;
    private DB db;
    @Override
    public void init() throws ServletException {
        db = new DB("101.35.232.25:3306","tmxq","123456","yjykfsj8");

    }
    // 获取客户端请求post/get
    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            processAction(request,response);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void processAction(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, JSONException, SQLException {


        HttpSession session = request.getSession();
        request.getSession().setMaxInactiveInterval(60*60*2);
        JSONObject resJson = new JSONObject();
        //设置编码
        request.setCharacterEncoding("UTF-8");
        //设置以下头信息允许跨域
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        // 获取请求参数
        JSONObject param = getParams(request,response);
        // 通过type的不同类型以不同方式处理参数
        String type = "none";
        if(param.has("type"))
            type = param.getString("type");

        switch (type)
        {
            case "login":
                loginEvent(param,request,response,resJson);
                break;
            case "send":
                SendEvent(param,request,response,resJson);
                break;
            case "register":
                registerEvent(param,request,response,resJson);
                break;
            case "verify":
                verifyEvent(param,request,response,resJson);
                break;
            case "resetPassword":
                ResetPasswordEvent(param,request,response,resJson);
                break;
            case "none":
                DefaultEvent(param,request,response,resJson);
                break;
            default:
                break;
        }
        showDebug("processAction",resJson.toString());

//        emailSender = new EmailSend("1550947151@qq.com","我是你叠");
        ResponseBack(request,response,param,resJson);

    }




    // 返回前端数据
    private void ResponseBack(HttpServletRequest request,HttpServletResponse response,JSONObject param,JSONObject resJson) throws JSONException {
        JSONObject res = new JSONObject();
        // 判断是否是ajax方式
        boolean isAjax = true;
        if(request.getHeader("x-requested-with") == null || request.getHeader("x-requested-with").equals("com.tencent.mm"))
        {
            isAjax = false;
        }
        if(param.has("ajax") && param.getString("ajax").equals("true"))
        {
            isAjax = true;
        }
        res.put("data",resJson);
        res.put("ajax",isAjax);
        res.put("ResultCode",200);

        showDebug("ResponseBack",res.toString());
        if(res.has("ajax") && res.getBoolean("ajax")){
            showDebug("ResponseBack","ajax方式返回数据，页面局部刷新");
            response.setContentType("application/json; charset=UTF-8");
            try {
                response.getWriter().print(res);
                response.getWriter().flush();
                response.getWriter().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            showDebug("ResponseBack","跳转方式返回数据，页面跳转到指定页面");
            String errorNo="0";
            String errorMsg="ok";
            String url="";
            try {
                if(resJson.has("redirect_url"))
                {
                    url=resJson.getString("redirect_url");
                    response.sendRedirect(url);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void loginSuccessfully(JSONObject resJson,HttpServletRequest request,HttpServletResponse response, int user_id) throws JSONException, IOException, SQLException {
        showDebug("loginEvent","密码正确,开始登录");
        // 密码正确
        resJson.put("user_id",user_id);
        resJson.put("correct",0);
        resJson.put("redirect_url","/main/userManage/extra_profile_account.jsp");
        HttpSession session = request.getSession();
        session.setAttribute("uid",user_id);

        String sql = "select * from user_file where user_id='" + user_id + "'";
        db.Connect();
        ResultSet rs = db.ExecuteQuery(sql);
        ResultSetMetaData rsmd = rs.getMetaData();
        int fieldCount = rsmd.getColumnCount();
        session.setAttribute("avatar","");
        if(rs.next())
        {
            for(int i = 0; i < fieldCount; ++i)
            {
                if(rs.getString(rsmd.getColumnName(i + 1)) != null)
                    session.setAttribute(rsmd.getColumnName(i + 1),rs.getString(rsmd.getColumnName(i + 1)));
            }
        }
        db.Close();
    }

    private boolean LoginFromCookie(HttpServletRequest request, HttpServletResponse response ,JSONObject resJson) throws SQLException, JSONException, IOException {
        Cookie[] cookies = null;
        cookies = request.getCookies();
        if(cookies != null)
        {
            for (Cookie cookie:cookies)
            {
                showDebug("DefaultEvent",cookie.getName() + " " + cookie.getValue());
                if("uid".equals(cookie.getName()))
                {
                    int cookieUid = Integer.parseInt(cookie.getValue()) ;
                    loginSuccessfully(resJson,request,response,cookieUid);
                    return true;
                }
            }
        }
        return false;
    }
    // post事件开始
    private void DefaultEvent(JSONObject param, HttpServletRequest request, HttpServletResponse response, JSONObject resJson) throws IOException, SQLException, JSONException {
        HttpSession session = request.getSession();
        Object uid =  session.getAttribute("uid");
        boolean login = LoginFromCookie(request,response,resJson);
        if(!login)
        {
            showDebug("DefaultEvent","cookie不存在");
            if(uid != null)
            {
                response.sendRedirect("/main/userManage/extra_profile_account.jsp");
            }
            else
            {
                response.sendRedirect("/main/login/login.jsp");
            }
        }
    }
    private void loginEvent(JSONObject param,HttpServletRequest request, HttpServletResponse response,JSONObject resJson) throws JSONException, IOException {

        String user_name = param.getString("username");
        String password = param.getString("password");
        if(user_name == "")
        {
            resJson.put("correct",-1);
            return;
        }
        // 查询该用户密码
        db.Connect();
        String sql ="select user_id,user_name,PASSWORD from user_file where user_name = \""+user_name+"\"";
        showDebug("loginEvent",sql);
        ResultSet rs = db.ExecuteQuery(sql);

        try
        {
            if(rs.next())
            {
                // 获取数据库对应密码
                String truePassword = rs.getString("password");
                int user_id = rs.getInt("user_id");
                if(truePassword.equals(password))
                {
                    if(param.has("remember"))
                    {
                        Cookie uid =new Cookie("uid",Integer.toString(user_id));
                        // 一天存活时间
                        uid.setMaxAge(60 * 60 * 24);
                        response.addCookie(uid);

                        request.getSession().setAttribute("remember",1);
                    }
                    loginSuccessfully(resJson,request,response,user_id);
                }
                else {
                    showDebug("loginEvent","密码错误");
                    showDebug("loginEvent",truePassword + "   " + password);

                    //密码错误
                    resJson.put("correct",1);
                }
            }
            else{

                showDebug("loginEvent","用户不存在");
                // 用户不存在
                resJson.put("correct",2);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        db.Close();
    }
    private void registerEvent(JSONObject param, HttpServletRequest request, HttpServletResponse response, JSONObject resJson) throws JSONException, SQLException {
        showDebug("registerEvent","开始注册用户");
        db.Connect();
        // 更新数据库
        boolean userAlreadyExist = db.AddtoDB(param);
        db.Close();
        resJson.put("registerCode",0);
        resJson.put("userAlreadyExist",userAlreadyExist);
    }
    private void SendEvent(JSONObject param, HttpServletRequest request, HttpServletResponse response, JSONObject resJson) throws JSONException, SQLException, ServletException, IOException {
        String user_name = param.getString("username");
        String email = param.getString("email");
        HttpSession session = request.getSession();
        db.Connect();
        String sql ="select user_id,user_name,PASSWORD,email from user_file where user_name = \""+user_name+"\"";
        showDebug("loginEvent",sql);
        ResultSet rs = db.ExecuteQuery(sql);
        if(rs.next())
        {
            String trueEmail = rs.getString("email");
            if(trueEmail.equals(email))
            {
                session.setAttribute("username",user_name);
                response.setHeader("pragma","no-cache");
                response.setHeader("cache-control","no-cache");
                response.setHeader("expires","0");

                String code = SendVerifyEmail(email);
                session.removeAttribute("VerifyCode");
                session.setAttribute("VerifyCode",code);
                resJson.put("EmailPass",0);
            }
            else
            {
                showDebug("ResetEvent","邮箱不匹配"+trueEmail + " " + email);
                resJson.put("EmailPass",-1);
            }
        }
        else {
            resJson.put("EmailPass",-1);
        }
    }
    private void verifyEvent(JSONObject param, HttpServletRequest request, HttpServletResponse response, JSONObject resJson) throws JSONException {
        String code = param.getString("verifycode");
        HttpSession session = request.getSession();
        Object trueCode = session.getAttribute("VerifyCode");
        session.removeAttribute("VerifyCode");
        if(code.toLowerCase().equals(trueCode.toString().toLowerCase()))
        {
            resJson.put("VerifyCode",0);
            showDebug("verifyEvent","验证成功!");
        }
        else
        {
            resJson.put("VerifyCode",-1);
            showDebug("verifyEvent","验证失败!");
            showDebug("verifyEvent","trueCode:" +trueCode + " code:" + code);
        }
    }
    private void ResetPasswordEvent(JSONObject param, HttpServletRequest request, HttpServletResponse response, JSONObject resJson) throws JSONException, SQLException {
        db.Connect();
        if(param.has("oldPassword"))
        {
            String sql ="select PASSWORD from user_file where user_name = \""+request.getSession().getAttribute("user_name")+"\"";
            ResultSet rs = db.ExecuteQuery(sql);
            if(rs.next())
            {
                String truePassword = rs.getString("password");

                if(getMD5Str(param.getString("oldPassword")).equals(truePassword))
                {
                    param.put("newPasswordmd5",getMD5Str(param.getString("newPassword")));
                    param.put("username",request.getSession().getAttribute("user_name"));
                    // 更新数据库
                    db.UpdatetoDB(param,request.getSession());
                    db.Close();
                    resJson.put("repasswordCode",0);
                    return;
                }
                else
                {
                    resJson.put("repasswordCode",-1);
                    db.Close();
                    return;
                }
            }
        }
        // 更新数据库
        db.UpdatetoDB(param,request.getSession());
        db.Close();
        resJson.put("repasswordCode",0);
    }
    // post事件结束

    private String SendVerifyEmail(String email) {
        // 随机生成验证码
        String base = "0123456789ABCDEFGHIJKLMNOPQRSDUVWXYZabcdefghijklmnopqrsduvwxyz";
        int size = base.length();
        Random r = new Random();
        StringBuilder code = new StringBuilder();
        for(int i=1;i<=4;i++){
            //产生0到size-1的随机值
            int index = r.nextInt(size);
            //在base字符串中获取下标为index的字符
            char c = base.charAt(index);
            //将c放入到StringBuffer中去
            code.append(c);
        }
        //把验证码保存在session中，方便后续判断

        new EmailSend(email,"您正在进行密码验证操作，验证码为"+code.toString()+",请勿随意外传");
        return code.toString();
    }

    // 获取post,get参数
    private JSONObject getParams(HttpServletRequest request, HttpServletResponse response) throws IOException, JSONException {
        Enumeration requestParameterNames =request.getParameterNames();
        JSONObject params = new JSONObject();
        for(;requestParameterNames.hasMoreElements();)
        {
            String name = requestParameterNames.nextElement().toString();
            String value = request.getParameter(name);
            if(name.equals("password"))
            {
                value = getMD5Str(value);
            }
            params.put(name,value);
        }
        showDebug("getParams",params.toString());
        return params;
    }
    public String getMD5Str(String str) {
        byte[] digest = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("md5");
            digest = md5.digest(str.getBytes("utf-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
//16是表示转换为16进制数
        String md5Str = new BigInteger(1, digest).toString(16);
        return md5Str;
    }
    public void showDebug(String method,String message) {
        System.out.println("["+(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date())+"]["+file+"/"+method+"]"+message);
    }

}