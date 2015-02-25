package com.login.cardapio.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.topsys.util.TSUtil;

/**
 * Servlet Filter implementation class Piloto
 */
@WebFilter("/pages/*")
public class CardapioFilter implements Filter {

	private final String AJAX = "XMLHttpRequest";

	@Override
	public void destroy() {
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest r = (HttpServletRequest) request;

	
		String uri = r.getRequestURI();

		if (uri != null) {
			uri = uri.substring(uri.lastIndexOf("/"), uri.length());
		}
		if ((!TSUtil.isEmpty(r.getSession().getAttribute(Constantes.USUARIO_CONECTADO))) || uri.equals("/login.xhtml")) {
			
			chain.doFilter(request, response);
			
		} else {

			if (isAjaxRequest(r)) {

				HttpServletResponse res = (HttpServletResponse) response;

				res.getWriter().print(redirectAjaxRequest(r, "/pages/login.xhtml"));

				res.flushBuffer();

			} else {

				((HttpServletResponse) response).sendRedirect(r.getContextPath() + "/pages/login.xhtml");

			}
		}

	}

	private String redirectAjaxRequest(HttpServletRequest request, String page) {
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version='1.0' encoding='UTF-8'?>");
		sb.append("<partial-response><redirect url=\"").append(request.getContextPath()).append(page).append("\"/></partial-response>");
		return sb.toString();
	}

	private boolean isAjaxRequest(HttpServletRequest request) {

		return AJAX.equals(request.getHeader("X-Requested-With"));

	}

}
