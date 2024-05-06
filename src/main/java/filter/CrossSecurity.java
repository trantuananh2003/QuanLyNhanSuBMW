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
		  
		        if (nonce == null) {
		            nonce = generateNonce();
		        }
		        httpRequest.setAttribute("nonce", nonce);
		        String cspDirective =
		        		"script-src 'self' 'nonce-" + nonce + "'; " +
		        		"style-src 'self' 'nonce-" + nonce + "' https://pro.fontawesome.com https://fonts.googleapis.com; " +
		        	    "img-src 'self'; " +
		        	    "font-src 'self' https://pro.fontawesome.com https://fonts.gstatic.com ;  " +
		        	    "frame-ancestors 'self'; " +
		        	    "form-action 'self'; " +
		        	    "connect-src 'self'  'nonce-" + nonce + "';" +
		        	    "frame-src 'self'; " +
		        	    "media-src 'self'; " +
		        	    "object-src 'self'; " +
		        	    "manifest-src 'self';";
		        	httpResponse.setHeader("Content-Security-Policy", cspDirective);

		        
		        chain.doFilter(request, response);
		    }


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

