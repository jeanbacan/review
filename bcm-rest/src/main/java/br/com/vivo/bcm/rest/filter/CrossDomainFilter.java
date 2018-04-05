/**
 * 
 */
package br.com.vivo.bcm.rest.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

/**
 * @author A0051460
 *
 */
/**
 * Filter adiciona as headers para que nao ocorra problema de cross doamin.
 * 
 * Esteja ciente que o cross domain server para proteger a aplicacao, portanto seria interessante definir as urls que serao permitidas no cross domain ao inves de liberar todas.
 * 
 * @author g0038078
 *
 */
@WebFilter(urlPatterns = { "/api/v1/*" })
public class CrossDomainFilter implements Filter {

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletResponse httpServletResponse = ((HttpServletResponse) response);
		httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
		httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
		httpServletResponse.setHeader("Access-Control-Max-Age", "3600");
		httpServletResponse.setHeader("Access-Control-Allow-Headers", "x-requested-with, content-type, Authorization, AuthToken, Content-Disposition, content-disposition");
		httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
		httpServletResponse.setHeader("Access-Control-Expose-Headers", "AuthToken, content-disposition");
		httpServletResponse.setHeader("Accept-Charset", "CP1252");
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}
}