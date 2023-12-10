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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.transform.Result;


import org.json.JSONException;
import org.json.JSONObject;
import DBControl.DB;
public class UserControl extends HttpServlet {

    private DB db;
    private Object uid;
    public void init() throws ServletException {
        db = new DB("101.35.232.25:3306","tmxq","123456","yjykfsj8");
    }
    private String file = "servletControl/UserControl";
    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            processAction(request,response);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void processAction(HttpServletRequest request, HttpServletResponse response) throws IOException, JSONException, SQLException {
        JSONObject rtJson = new JSONObject();

        HttpSession session = request.getSession();
        uid = session.getAttribute("uid");
        JSONObject params = getParams(request,response);


        String type = "none";
        if(params.has("type")){type=params.getString("type");}

        switch (type)
        {
            case "getUserInfo":
                getUserInfoEvent(request,response,rtJson);
                break;
            case "setUserInfo":
                setUserInfoEvent(request,response,rtJson,params);
                break;
            case "getUsersList":
                getUsersListEvent(request,response,rtJson,params);
                break;
            case "delete":
                deleteUserEvent(request,response,rtJson,params);
                break;
            case "modify":
                modifyUserEvent(request,response,rtJson,params);
                break;
            case "add":
                addUserEvent(request,response,rtJson,params);
                break;
            case "search":
                searchUserEvent(request,response,rtJson,params);
                break;
            default:
                break;
        }

        ResponseBack(request,response,params,rtJson);
    }




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

        res = resJson;
        res.put("ajax",isAjax);
        res.put("ResultCode",200);

        if(resJson.has("ajax") && resJson.getBoolean("ajax")){
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


    // 事件开始
    private void searchUserEvent(HttpServletRequest request, HttpServletResponse response, JSONObject rtJson, JSONObject params) throws JSONException, SQLException {
        db.Connect();
        String sql = "select user_id,user_name,nickname,mobile_number,email,role_type,create_time,occupation from user_file LEFT JOIN user_role on user_file.role_id=user_role.role_id";
        String user_name ="";
        String nickname="";
        String occupation ="";
        String mobile_number="";
        String email="";
        String set =" where ";
        boolean hasparam = false;
        user_name = params.getString("user_name");

        if(user_name !="")
        {
            set += "user_name='" + user_name +"'";
            hasparam = true;
        }
        nickname = params.getString("nickname");
        if(nickname !="")
        {
            if(hasparam == true)
                set += " and nickname='" + nickname +"'";
            else
            {
                set+=" nickname='" + nickname +"'";
                hasparam = true;
            }
        }
        occupation = params.getString("occupation");
        if(occupation !="")
        {
            if(hasparam == true)
                set += " and occupation='" + occupation+"'";
            else
            {
                set+=" occupation='" + occupation+"'";
                hasparam = true;
            }
        }
        mobile_number =params.getString("mobile_number");
        if(mobile_number !="")
        {
            if(hasparam == true)
                set += " and mobile_number='" + mobile_number+"'";
            else
            {
                set+=" mobile_number='" + mobile_number+"'";
                hasparam = true;
            }
        }
        email = params.getString("email");
        if(email !="")
        {
            if(hasparam == true)
                set += " and email='" + email+"'";
            else
            {
                set+=" email='" + email+"'";
                hasparam = true;
            }
        }
        if(hasparam)
        {
            sql+=set;
        }
        showDebug("searchUserEvent",sql);
        ResultSet rs = db.ExecuteQuery(sql);
        ResultSetMetaData rsmd = rs.getMetaData();
        List<Map<String,Object>> res = new ArrayList<Map<String,Object>>();
        while(rs.next())
        {
            Map<String,Object> rowData = new HashMap<String,Object>();
            for(int i = 0; i < rsmd.getColumnCount(); ++i)
            {
                rowData.put(rsmd.getColumnName(i + 1),rs.getObject(i + 1));
            }
            res.add(rowData);
        }
        rtJson.put("data",res);
        showDebug("searchUserEvent",res.toString());
        db.Close();
    }
    private void addUserEvent(HttpServletRequest request, HttpServletResponse response, JSONObject rtJson, JSONObject params) throws JSONException, SQLException {
        db.Connect();
        boolean userExist = db.AddtoDB(params);
        if(userExist)
        {
            rtJson.put("userExist",1);
        }
        else
        {
            rtJson.put("userExist",0);
        }
        db.Close();

    }
    private void modifyUserEvent(HttpServletRequest request, HttpServletResponse response, JSONObject rtJson, JSONObject params) throws JSONException, SQLException {
        String email = params.getString("email");
        String user_name=params.getString("user_name");
        String nickname=params.getString("nickname");
        String mobile_number=params.getString("mobile_number");
        String occupation = params.getString("occupation");
        String uid=params.getString("uid");

        String sql = "update user_file set user_name='"+user_name+"',email='"+email+"',nickname='"+nickname+"',mobile_number='"+mobile_number+"',occupation='"+occupation+"' ";
        sql +="where user_id='"+uid + "'";
        showDebug("modifyUserEvent",sql);
        db.Connect();
        boolean useralreadyexist= db.existUser(user_name);
        if(!useralreadyexist)
        {
            db.ExecuteUpdate(sql);
            rtJson.put("userExist",0);
        }
        else
        {
            rtJson.put("userExist",1);
        }
        db.Close();

    }
    private void deleteUserEvent(HttpServletRequest request, HttpServletResponse response, JSONObject rtJson, JSONObject params) throws JSONException {
        String user_id = params.getString("uid");
        db.Connect();
        String sql = "delete from user_file where user_id='"+ user_id + "'";
        db.ExecuteUpdate(sql);
        db.Close();
    }
    private void getUserInfoEvent(HttpServletRequest request, HttpServletResponse response,JSONObject rtJson) throws SQLException, JSONException {

    }
    private void setUserInfoEvent(HttpServletRequest request, HttpServletResponse response, JSONObject rtJson, JSONObject params) throws JSONException {
        showDebug("setUserInfoEvent",params.toString());
        db.Connect();
        db.UpdatetoDB(params,request.getSession());
        db.Close();
    }
    private void getUsersListEvent(HttpServletRequest request, HttpServletResponse response, JSONObject rtJson, JSONObject params) throws SQLException, JSONException {
        db.Connect();
        String sql = "select user_id,user_name,nickname,mobile_number,email,role_type,create_time,occupation from user_file LEFT JOIN user_role on user_file.role_id=user_role.role_id";
        ResultSet rs = db.ExecuteQuery(sql);
        ResultSetMetaData rsmd = rs.getMetaData();
        List<Map<String,Object>> res = new ArrayList<Map<String,Object>>();
        while(rs.next())
        {
            Map<String,Object> rowData = new HashMap<String,Object>();
            for(int i = 0; i < rsmd.getColumnCount(); ++i)
            {
                rowData.put(rsmd.getColumnName(i + 1),rs.getObject(i + 1));
            }
            res.add(rowData);
        }
        rtJson.put("data",res);
        showDebug("getUsersListEvent",res.toString());
        db.Close();
    }
    // 事件结束
    public void showDebug(String method,String message) {
        System.out.println("["+(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date())+"]["+file+"/"+method+"]"+message);
    }
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
}
