package DBControl;

import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class DB {
    int debugLevel = 0;
    String file = "DBControl/DB";
    private Connection connection;
    private Statement statement;
    private String host ;
    private String user;
    private String password;
    private String DBName;
    // 设置字段读写权限
    public String getHost ()
    {
        return host;
    }
    public String getUser()
    {
        return user;
    }
    public String getPassword()
    {
        return password;
    }
    public String getDBName()
    {
        return DBName;
    }
    public void setHost(String host)
    {
        this.host = host;
    }
    public void setUser(String user)
    {
        this.user = user;
    }
    public void setPassword(String password)
    {
        this.password = password;
    }
    public void setDBName(String DBName)
    {
        this.DBName = DBName;
    }
    private void showDebug (String method, String message) {
        System.out.println("[" + (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()) + "][" + file + "/" + method + "]" + message);
    }
    public DB(String _host,String _user,String _password,String _DBName) {
        host = _host;
        user = _user;
        password = _password;
        DBName = _DBName;
    }
    // 打开数据库
    public void Connect() {
        //加载驱动
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (ClassNotFoundException classnotfoundexception) {
            classnotfoundexception.printStackTrace();
            showDebug("DBConnection","驱动加载失败!");
            return;
        }
        showDebug("DBConnection","驱动加载成功！");
        // 链接数据库
        try {
            String connStr = "jdbc:mysql://" + host + "/"+ DBName +"?user="+user+"&password="+password+"&useUnicode=true&characterEncoding=UTF-8&useSSL=false";
            System.out.println(connStr);
            connection = DriverManager.getConnection(connStr);
            statement = connection.createStatement();
            showDebug("DBConnection","成功链接到" + DBName + "数据库!");
        } catch (SQLException sqlexception) {
            sqlexception.printStackTrace();
        }
    }
    // 关闭数据库
    public void Close() {
        try {
            statement.close();
            connection.close();
            showDebug("Close","数据库已关闭！");
        } catch (SQLException sqlexception) {
            sqlexception.printStackTrace();
        }

    }
    // 执行查询操作返回结果集
    public ResultSet ExecuteQuery(String sql) {
        ResultSet resultSet = null;
        try {
            resultSet = statement.executeQuery(sql);
        } catch (SQLException e) {
            showDebug("ExcuteQuery", e.toString());
            e.printStackTrace();
        }
        return resultSet;
    }
    // 执行修改操作语句
    public void ExecuteUpdate(String sql){
        try {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            showDebug("ExcuteUpdate",e.toString());
            e.printStackTrace();
        }
    }
    public boolean AddtoDB(JSONObject param) throws JSONException, SQLException {
        String user_name = null;
        String password = null;
        String phone = null;
        String nickname = null;
        String email = null;
        String occupation = "nothing";
        int role_id = 3;
        if (param.has("username")) {
            user_name = param.getString("username");
        }
        if(existUser(user_name))
        {
            return true;
        }

        if (param.has("password")) {
            password = param.getString("password");
        }
        if (param.has("phone")) {
            phone = param.getString("phone");
        }
        if (param.has("nickname")) {
            nickname = param.getString("nickname");
        }
        if(param.has("email"))
        {
            email = param.getString("email");
        }
        if(param.has(("occupation")))
        {
            occupation = param.getString("occupation");
        }
        String createTime=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
        String sql = "insert into user_file (user_name,password,nickname,mobile_number,email,create_time,role_id,occupation) values('"+ user_name + "','" +
                password + "','" + nickname + "','" + phone + "','" + email + "','" + createTime + "','" + role_id +"','" + occupation + "')";
        showDebug("AddtoDB",sql);
        this.ExecuteUpdate(sql);
        return false;
    }
    public void UpdatetoDB(JSONObject param, HttpSession session) throws JSONException{
        String user_name = "";
        String password = null;
        String phone = null;
        String nickname = null;
        String email = null;
        String sql = "update user_file set ";
        String avatar = null;
        String occupation = null;
        String set = "";
        showDebug("UpdatetoDB/param",param.toString());
        if (param.has("username")) {
            user_name = param.getString("username");
        }
        if (param.has("avatar")) {
            avatar = param.getString("avatar");
            set = set + "avatar='" +avatar +"',";
            session.setAttribute("avatar",avatar);
        }
        if (param.has("password")) {
            password = param.getString("password");
            set = set + "password='" +password +"',";
            session.setAttribute("password",password);
        }
        if(param.has("newPasswordmd5"))
        {
            password = param.getString("newPasswordmd5");
            set = set + "password='" +password +"',";
            session.setAttribute("password",password);
        }
        if (param.has("phone")) {
            phone = param.getString("phone");
            set = set + "mobile_number='" +phone+"',";
            session.setAttribute("mobile_number",phone);
        }
        if (param.has("nickname")) {
            nickname = param.getString("nickname");
            set = set + "nickname='" +nickname+"',";
            session.setAttribute("nickname",nickname);
        }
        if(param.has("email"))
        {
            email = param.getString("email");
            set= set + "email='" +email+"',";
            session.setAttribute("email",email);
        }
        if(param.has("occupation"))
        {
            occupation = param.getString("occupation");
            set=set + "occupation='" + occupation + "',";
            session.setAttribute("occupation",occupation);
        }
        if(set != "")
        {
            set = set.substring(0, set.length() - 1);
            showDebug("UpdatatoDB/set",set);
            sql = sql + set + " where user_name='" + user_name + "'";
            showDebug("UpdatatoDB", sql);
            this.ExecuteUpdate(sql);
        }
    }


    public boolean existUser(String user_name) throws SQLException {
        String sql = "select * from user_file where user_name='" + user_name + "'";
        ResultSet rs = this.ExecuteQuery(sql);
        if(rs.next())
            return true;
        else
            return false;
    }
}

