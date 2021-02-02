package controllers.novel;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Novel;

/**
 * Servlet implementation class NovelsNewServlet
 */
@WebServlet("/novels/new")
public class NovelsNewServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public NovelsNewServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("_token", request.getSession().getId());

        Novel n = new Novel();
        n.setNovel_date(new Date(System.currentTimeMillis()));
        request.setAttribute("novel", n);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/novels/new.jsp");
        rd.forward(request, response);
    }

}
