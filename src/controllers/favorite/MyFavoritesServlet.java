package controllers.favorite;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
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
 * Servlet implementation class MyFavoritesServlet
 */
@WebServlet("/favorites/my")
public class MyFavoritesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public MyFavoritesServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        //ログインユーザーのidを取得
        User login_user = (User) request.getSession().getAttribute("login_user");
        int user_id = login_user.getId();

        //ページネーションの設定
        int page;
        try{
            page = Integer.parseInt(request.getParameter("page"));
        }catch(Exception e) {
            page = 1;
        }

        //Favorite型が格納されているリストの作成、同時に15個ずつ表示されるように。
        List<Favorite> favorite_list = em.createNamedQuery("getMyFavorite", Favorite.class)
                .setParameter("user_id", user_id)
                .setFirstResult(15 * (page - 1))
                .setMaxResults(15)
                .getResultList();

        //Novel型のリスト作成
        List<Novel> novels = new ArrayList<Novel>();

        //拡張for文でお気に入り登録しているNovelインスタンスをnovelsに格納
        for(Favorite fl : favorite_list) {
            novels.add(em.find(Novel.class, fl.getNovel_id()));
        }

        //お気に入りの数を取得
        long novels_count = (long)em.createNamedQuery("getMyFavoriteCount", Long.class)
                .setParameter("user_id", user_id)
                .getSingleResult();

        em.close();

        request.setAttribute("novels", novels);
        request.setAttribute("novels_count", novels_count);
        request.setAttribute("page", page);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/novels/favorite.jsp");
        rd.forward(request, response);
    }

}
