package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entidades.Usuario;
import negocio.ControladorNatacion;

/**
 * Servlet implementation class Login
 */
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ControladorNatacion cn = new ControladorNatacion();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String usuario = request.getParameter("login");
		String password = request.getParameter("password");
		
		
		try {
			boolean ingreso = cn.buscarUsuario(usuario, password);
			if(!ingreso)
			{
				//inicializo la sesion
				HttpSession session = request.getSession(true);
				session.setAttribute("existeUsuario", ingreso);
				request.getRequestDispatcher("usuarios.jsp").forward(request, response);
			}
			else
			{
				response.sendRedirect("Ingresar.html");
			}
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
	}

}
