package controllers.novel;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Favorite;
import models.Novel;
import models.User;
import utils.DBUtil;

/**
 * Servlet implementation class NovelsShowServlet
 */
@WebServlet("/novels/show")
public class NovelsShowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public NovelsShowServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */

    //ここのNovelの詳細画面へ
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        int novel_id = Integer.parseInt(request.getParameter("id"));
        Novel n = em.find(Novel.class, novel_id);

        User login_user = (User) request.getSession().getAttribute("login_user");
        int user_id = login_user.getId();


        //お気に入り登録しているかどうか
        Favorite favoriteRelation = null;

        try {
            favoriteRelation = em.createNamedQuery("getFavoriteRelation", Favorite.class).setParameter("user_id", user_id).setParameter("novel_id", novel_id).getSingleResult();
        } catch(NoResultException e){
            favoriteRelation = null;
        }

        //お気に入り登録数の取得
        long favorite_count = (long)em.createNamedQuery("getFavoriteCount", Long.class)
                .setParameter("novel_id", novel_id)
                .getSingleResult();

        em.close();

        request.setAttribute("novel", n);
        request.setAttribute("_token", request.getSession().getId());
        if(favoriteRelation != null){
            request.setAttribute("favoriteRelation", favoriteRelation);
        }
        request.setAttribute("login_user", login_user);
        request.setAttribute("favorite_count", favorite_count);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/novels/show.jsp");
        rd.forward(request, response);
    }

}
