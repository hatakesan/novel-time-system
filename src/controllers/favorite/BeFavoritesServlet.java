package controllers.favorite;

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
 * Servlet implementation class BeFavoritesServlet
 */
@WebServlet("/favorites/be")
public class BeFavoritesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public BeFavoritesServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        //DBに登録するidを取得
        User login_user = (User) request.getSession().getAttribute("login_user");
        int user_id = login_user.getId();
        int novel_id = Integer.parseInt(request.getParameter("id"));

        Novel n = em.find(Novel.class, novel_id);

        Favorite fn = new Favorite();

        fn.setUser_id(user_id);
        fn.setNovel_id(novel_id);

        //Favorite型のfnをDBに登録
        em.getTransaction().begin();
        em.persist(fn);
        em.getTransaction().commit();

        //お気に入り登録しているか検索
        Favorite favoriteRelation = null;

        try {
            favoriteRelation = em.createNamedQuery("getFavoriteRelation", Favorite.class).setParameter("user_id", user_id).setParameter("novel_id", novel_id).getSingleResult();
        } catch(NoResultException e){
            favoriteRelation = null;
        }

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
