package com.login.cardapio.servlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

import com.login.cardapio.model.Empresa;
import com.login.cardapio.model.Usuario;
import com.login.cardapio.util.Constantes;

/**
 * Servlet implementation class QrCode
 */
@WebServlet("/qrcode")
public class QrCode extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public QrCode() {
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
		String qrtext = request.getParameter("mesa");

		Usuario usuario = (Usuario) request.getSession().getAttribute(Constantes.USUARIO_CONECTADO);

		if (usuario == null) {
			return;
		}

		List<Empresa> listEmpresa = new Empresa().findAll("id");
		if (listEmpresa.size() != 0) {

			Empresa empresa = listEmpresa.get(0);

			ByteArrayOutputStream out = QRCode.from("mesa=" + qrtext + "&empresa=" + empresa.getKeyMobile()).to(ImageType.PNG).stream();

			response.setContentType("image/png");
			response.setContentLength(out.size());

			OutputStream outStream = response.getOutputStream();

			outStream.write(out.toByteArray());

			outStream.flush();
			outStream.close();
		}
	}

}
