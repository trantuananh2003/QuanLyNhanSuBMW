package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import Models.LoginBean;

@WebFilter("/*")
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Khởi tạo filter (nếu cần)
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
  
    	 HttpServletRequest request = (HttpServletRequest) servletRequest;
         HttpServletResponse response = (HttpServletResponse) servletResponse;

         
         
         

         String requestPath = request.getServletPath();
         if (!(requestPath.equals("/login")||requestPath.equals("/pages/login.jsp"))) {
        	 HttpSession session = request.getSession();
             LoginBean accLogin = (LoginBean) session.getAttribute("accLogin");
             if (accLogin == null) {
            	  
                 response.sendRedirect(request.getContextPath() + "/pages/login.jsp");
                 return;
             }

         }
         filterChain.doFilter(request, response);
         
       
    }

    @Override
    public void destroy() {
        // Giải phóng tài nguyên (nếu cần)
    }
}
