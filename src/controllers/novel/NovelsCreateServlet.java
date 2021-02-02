package controllers.novel;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Novel;
import models.User;
import models.validators.NovelValidator;
import utils.DBUtil;

/**
 * Servlet implementation class NovelsCreateServlet
 */
@WebServlet("/novels/create")
public class NovelsCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public NovelsCreateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _token = (String)request.getParameter("_token");
        if(_token != null && _token.equals(request.getSession().getId())) {
            EntityManager em = DBUtil.createEntityManager();

            Novel n = new Novel();

            n.setUser((User)request.getSession().getAttribute("login_user"));

            Date novel_date = new Date(System.currentTimeMillis());
            String nd_str = request.getParameter("novel_date");
            if(nd_str != null && !nd_str.equals("")) {
                novel_date = Date.valueOf(request.getParameter("novel_date"));
            }
            n.setNovel_date(novel_date);

            n.setTitle(request.getParameter("title"));
            n.setSentence(request.getParameter("sentence"));

            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            n.setCreated_at(currentTime);
            n.setUpdated_at(currentTime);

            List<String> errors = NovelValidator.validate(n);
            if(errors.size() > 0) {
                em.close();

                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("novel", n);
                request.setAttribute("errors", errors);

                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/novels/new.jsp");
                rd.forward(request, response);
            } else {
                em.getTransaction().begin();
                em.persist(n);
                em.getTransaction().commit();
                em.close();
                request.getSession().setAttribute("flush", "登録が完了しました");

                response.sendRedirect(request.getContextPath() + "/novels/index");
            }
        }
    }

}
