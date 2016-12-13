
package base.validationCode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.support.RequestContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 */

public class CommandChainFilter implements Filter {
	private final static Logger log = LoggerFactory
			.getLogger(CommandChainFilter.class);
	private WebApplicationContext webApplicationContext;
	public final static String COMMAND_PARAMETERS = "commands";
	private String[] commands;
	private FilterConfig filterConfig;
	private ServletContext servletContext;
	private boolean initFlag = false;
	private String defaultPage = "/";
	private List<Object> commandsList = Collections
			.synchronizedList(new ArrayList<Object>());

	/**
	 * 
	 */
	public CommandChainFilter() {
	}

	/*
	 * (非 Javadoc)
	 * 
	 * 
	 * @param filterConfig
	 * 
	 * @throws ServletException
	 * 
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		String command = filterConfig.getInitParameter(COMMAND_PARAMETERS);
		if (command != null) {
			commands = command.split(",");
		}
		this.filterConfig = filterConfig;
		// servletContext = filterConfig.getServletContext();
		// setCommandsList();
	}

	/*
	 * (非 Javadoc)
	 * 
	 * 
	 * @param request
	 * 
	 * @param response
	 * 
	 * @param chain
	 * 
	 * @throws IOException
	 * 
	 * @throws ServletException
	 * 
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 * javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		boolean returnFlag = false;
		if (!this.initFlag) {
			servletContext = filterConfig.getServletContext();
			setCommandsList();
		}
		try {
			for (Iterator<Object> it = this.commandsList.iterator(); it
					.hasNext();) {
				Command command = (Command) it.next();
				command.excuteCommand(request);
			}
		} catch (Exception e) {
			log.debug("the error massage:"+e.getMessage());
			if (e instanceof ValidationException) {
				returnFlag = true;
			}
		}
		if (returnFlag) {
			response.getWriter().write(Constants.VALIDATION_ERROR_CODE);
		} else {
			chain.doFilter(request, response);
		}
	}

	/*
	 * (非 Javadoc)
	 * 
	 * 
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
		commands = null;
		commandsList = null;
		filterConfig = null;
		servletContext = null;
	}

	protected WebApplicationContext findWebApplicationContext() {
		if (this.webApplicationContext != null) {
			// the user has injected a context at construction time -> use it
			if (this.webApplicationContext instanceof ConfigurableApplicationContext) {
				if (!((ConfigurableApplicationContext) this.webApplicationContext)
						.isActive()) {
					// the context has not yet been refreshed -> do so before
					// returning it
					((ConfigurableApplicationContext) this.webApplicationContext)
							.refresh();
				}
			}
			return this.webApplicationContext;
		}
		return WebApplicationContextUtils
				.getWebApplicationContext(servletContext);
	}

	protected void setCommandsList() {
		WebApplicationContext wa = findWebApplicationContext();
		if (this.commandsList.size() == 0) {
			for (int i = 0; i < this.commands.length; i++) {
				this.commandsList.add(wa.getBean(commands[i]));
			}
		}
		initFlag = true;
	}
}
