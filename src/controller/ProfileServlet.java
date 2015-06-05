package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Login;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ProfileServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String url = "link.html";
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		HttpSession session = request.getSession(false);
		if (session != null) {

			DateFormat formatter = new SimpleDateFormat("HH:mm:ss:SSS");
			String hora = formatter.format(session.getCreationTime());
			
			Login l = new Login();
			
			l.setNome((String) session.getAttribute("name"));
			l.setSenha((String) session.getAttribute("password"));
			l.setPerfil((int) session.getAttribute("perfil"));
			
			out.print("Olá, " + l.getNome());
			out.print("<br />");
			out.print("Sua sessão é : "+session.getId());
			out.print("<br />");
			out.print("Iniciada às : "+ hora);
			out.print("<br />");
			
			if (l.getPerfil() == 2){
				out.print("Sua senha é : "+ l.getSenha());
				out.print("<br />");
			}
			
		} else {
			url = "login.html";
			out.print("Faça o Login");
			out.print("<br />");
			out.print("<br />");
		}
		request.getRequestDispatcher(url).include(request, response);
		out.close();
	}
}
