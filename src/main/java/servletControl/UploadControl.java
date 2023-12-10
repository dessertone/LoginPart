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
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.*;

import DBControl.DB;

import org.json.JSONException;
import org.json.JSONObject;
public class UploadControl extends HttpServlet  {

    private String file ="servletControl/UploadControl";
    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession(true);
        String uid = session.getAttribute("uid").toString();
        String path = "F:/Amusement/Capture/pictures/avatar/";
        String fileName = uid + ".jpg";
        try {
            File file1 = new File(path);
            if(!file1.exists())
            {
                file1.mkdir();
            }
            // 获取上传的文件
            Part filePart = request.getPart("avatar");
            InputStream fileContent = filePart.getInputStream();
            byte[] buffer = new byte[fileContent.available()];
            fileContent.read(buffer);


            DB db = new DB("101.35.232.25:3306","tmxq","123456","yjykfsj8");
            db.Connect();
            JSONObject params = new JSONObject();
            params.put("avatar","/upload/avatar/" + fileName);
            session.setAttribute("avatar","/upload/avatar/" + fileName);
            params.put("username",session.getAttribute("user_name"));
            db.UpdatetoDB(params,request.getSession());
            db.Close();
            // 保存文件到本地
            FileOutputStream outputStream = new FileOutputStream(path + fileName);
            outputStream.write(buffer);
            outputStream.close();
            showDebug("service",path + fileName);
            response.sendRedirect("/main/userManage/extra_profile_account.jsp");
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void showDebug(String method,String message) {
        System.out.println("["+(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date())+"]["+file+"/"+method+"]"+message);
    }

}
