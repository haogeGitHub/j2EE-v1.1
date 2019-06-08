package tmall.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;


public class BackServletFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest request=(HttpServletRequest)req;
		HttpServletResponse response=(HttpServletResponse)res;
		
		//获取URI
		String uri=request.getRequestURI();
		
		String contextPath=request.getContextPath();//就是在server.xml中配置的<context>中的path
		
		uri=StringUtils.remove(uri, contextPath);
		
		//System.out.println(uri);
		if(uri.startsWith("/admin_")){
			String servletPath=StringUtils.substringBetween(uri, "_", "_")+"Servlet";
			String method=StringUtils.substringAfterLast(uri,"_" );
			request.setAttribute("method", method);
			request.getRequestDispatcher("/"+servletPath).forward(request, response);
			
			return;
		}
		
		chain.doFilter(request, response);
		

//        pw.println(contextPath);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
