package com.login.cardapio.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.login.cardapio.model.Usuario;
import com.login.cardapio.util.Constantes;

import br.com.topsys.util.TSUtil;
import br.com.topsys.web.util.TSFacesUtil;

/**
 * Servlet implementation class QrCode
 */
@WebServlet("/hassession")
public class HasSession extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public HasSession() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		exec(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		exec(request, response);
	}

	private void exec(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		
		Boolean sesseion = new Boolean(false);
		HttpServletRequest r = (HttpServletRequest) request;
		String uri = r.getRequestURI();

		if (uri != null) {
			uri = uri.substring(uri.lastIndexOf("/"), uri.length());
		}
		
		if ((!TSUtil.isEmpty(r.getSession().getAttribute(Constantes.USUARIO_CONECTADO)))) {			
			sesseion=true;
		}
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		try {
			response.getWriter().write(new JSONObject().put("session", sesseion).toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.flushBuffer();
		
	}
}
