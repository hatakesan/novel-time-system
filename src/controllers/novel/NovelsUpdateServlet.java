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
import models.validators.NovelValidator;
import utils.DBUtil;

/**
 * Servlet implementation class NovelsUpdateServlet
 */
@WebServlet("/novels/update")
public class NovelsUpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public NovelsUpdateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */

    //Novelの更新処理
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _token = (String)request.getParameter("_token");
        if(_token != null && _token.equals(request.getSession().getId())) {
            EntityManager em = DBUtil.createEntityManager();

            Novel n = em.find(Novel.class, (Integer)(request.getSession().getAttribute("novel_id")));

            n.setNovel_date(Date.valueOf(request.getParameter("novel_date")));
            n.setTitle(request.getParameter("title"));
            n.setSentence(request.getParameter("sentence"));
            n.setUpdated_at(new Timestamp(System.currentTimeMillis()));

            //バリデーション
            List<String> errors = NovelValidator.validate(n);
            if(errors.size() > 0) {
                em.close();

                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("novel", n);
                request.setAttribute("errors", errors);

                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/novels/edit.jsp");
                rd.forward(request, response);
            } else {
                em.getTransaction().begin();
                em.getTransaction().commit();
                em.close();
                request.getSession().setAttribute("flush", "更新が完了しました");
                request.getSession().removeAttribute("novel_id");

                response.sendRedirect(request.getContextPath() + "/novels/index");
            }
        }
    }

}
