package servletControl;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Logout extends HttpServlet {
    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie:cookies)
        {
            if(cookie.getValue().equals("uid"))
            {
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
        request.getSession().invalidate();
        response.sendRedirect("../main/login/login.jsp");
    }

}
