	package filter;
	import java.io.IOException;
	import java.security.SecureRandom;
	import javax.servlet.Filter;
	import javax.servlet.FilterChain;
	import javax.servlet.FilterConfig;
	import javax.servlet.ServletException;
	import javax.servlet.ServletRequest;
	import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
	import javax.servlet.http.HttpSession;
	import javax.servlet.http.HttpServletResponse;
	import java.util.Base64;
	import javax.servlet.*;
	@WebFilter("/*")
	public class CrossSecurity implements Filter {
		 private static String nonce;

		    @Override
		    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
		            throws IOException, ServletException {
		        HttpServletRequest httpRequest = (HttpServletRequest) request;
		        HttpServletResponse httpResponse = (HttpServletResponse) response;
		        String duongDanIndex = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
		            + ((HttpServletRequest) request).getContextPath();

		    

		        String cspDirective =
		                "script-src 'self' ; " +
		                "style-src 'self';" +
		                "img-src 'self';" +
		                "font-src 'self';"+
		        "frame-ancestors 'self'; " + 
		        "form-action 'self'; " + // Chỉ cho phép form submit đến chính trang web hiện tại
	            "connect-src 'self'; " + // Chỉ cho phép kết nối từ chính trang web hiện tại
	            "frame-src 'self'; " + // Chỉ cho phép frame được nhúng từ chính trang web hiện tại
	            "media-src 'self'; " + // Chỉ cho phép truy cập đến phương tiện từ chính trang web hiện tại
	            "object-src 'self'; " + // Không cho phép nhúng đối tượng từ bất kỳ nguồn nào
		        "manifest-src 'self' ; ";
//		        httpResponse.setHeader("Content-Security-Policy", cspDirective);
		        
		        
			

		        // Tiếp tục chuỗi filter
		        chain.doFilter(request, response);
		    }

		    // Các phương thức khác...

		    // Hàm sinh giá trị nonce ngẫu nhiên
		    private String generateNonce() {
		        byte[] nonceBytes = new byte[16];
		        new SecureRandom().nextBytes(nonceBytes);
		        return Base64.getEncoder().encodeToString(nonceBytes);
		    }

			@Override
			public void destroy() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void init(FilterConfig arg0) throws ServletException {
				// TODO Auto-generated method stub
				
			}
	}

