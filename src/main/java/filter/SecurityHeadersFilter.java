package filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import javax.servlet.annotation.WebFilter;

@WebFilter("/*")
public class SecurityHeadersFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		httpServletResponse.setContentType("text/html"); // Set Content-Type header
		httpServletResponse.setContentType("text/css"); // Set Content-Type header

		httpServletResponse.setHeader("X-Content-Type-Options", "nosniff");
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {

	}

}