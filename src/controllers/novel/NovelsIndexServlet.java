package controllers.novel;

import java.io.IOException;
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
import utils.DBUtil;

/**
 * Servlet implementation class SentenceIndexServlet
 */
@WebServlet("/novels/index")
public class NovelsIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public NovelsIndexServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    //ログインユーザーの投稿の表示
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        User login_user = (User)request.getSession().getAttribute("login_user");


        //ページネーション
        int page;
        try{
            page = Integer.parseInt(request.getParameter("page"));
        }catch(Exception e) {
            page = 1;
        }

        //ログインユーザーの投稿をリストへ
        List<Novel> novels = em.createNamedQuery("getMyAllNovels", Novel.class)
                .setParameter("user", login_user)
                .setFirstResult(15 * (page - 1))
                .setMaxResults(15)
                .getResultList();

        long novels_count = (long)em.createNamedQuery("getMyNovelsCount", Long.class)
                .setParameter("user", login_user)
                .getSingleResult();

        em.close();

        request.setAttribute("novels", novels);
        request.setAttribute("novels_count", novels_count);
        request.setAttribute("page", page);




        if(request.getSession().getAttribute("flush") != null) {
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/topPage/index.jsp");
        rd.forward(request, response);
    }

}
