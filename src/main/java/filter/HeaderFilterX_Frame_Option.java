package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//Annotation @WebFilter("/*") được sử dụng để chỉ định rằng Filter sẽ áp dụng cho tất cả các request tới ứng dụng web của bạn. 
//Khi bạn đặt "/*" trong @WebFilter, nó đại diện cho mọi URL trong ứng dụng của bạn.
@WebFilter("/*")
public class HeaderFilterX_Frame_Option  implements Filter  {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setHeader("X-Frame-Options", "DENY");


        filterChain.doFilter(request, response);

        
	}
    // Hoặc httpResponse.setHeader("X-Frame-Options", "SAMEORIGIN");
    // Hoặc httpResponse.setHeader("X-Frame-Options", "ALLOW-FROM uri");
    //Phương thức chain.doFilter(request, response) trong phương thức doFilter() của một Filter có tác dụng chuyển tiếp 
    //request và response tới các Filter hoặc Servlet khác trong chuỗi Filter.
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
	public HeaderFilterX_Frame_Option() {
    }
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}
}
