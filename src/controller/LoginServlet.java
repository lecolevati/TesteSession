package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Login;

import persistence.LoginDao;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LoginServlet() {
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
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String url = "link.html";

		Login l = new Login();
		l.setNome(request.getParameter("name"));
		l.setSenha(request.getParameter("password"));
		
		LoginDao lDao;
		try {
			lDao = new LoginDao();
			if (lDao.validaLogin(l)) {
				out.print("Bem vindo à sua sessão, " + l.getNome());
				out.print("<br />");
				out.print("<br />");
				
				l = lDao.retornaLogin(l);
				
				HttpSession session = request.getSession();
				session.setAttribute("name", l.getNome());
				session.setAttribute("password", l.getSenha());
				session.setAttribute("perfil", l.getPerfil());
			} else {
				url = "login.html";
				out.print("Login ou Senha Inválidos!");
				out.print("<br />");
			}
		} catch (SQLException e) {
			url = "login.html";
			out.print("Erro no processamento da requisição !");
			out.print("<br />");
		}
		request.getRequestDispatcher(url).include(request, response);
		out.close();
	}

}
