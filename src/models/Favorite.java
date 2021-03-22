package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name = "favorites")

@NamedQueries({
        @NamedQuery(
                name = "getFavoriteRelation",
                query = "SELECT fn FROM Favorite AS fn WHERE fn.user_id = :user_id AND fn.novel_id = :novel_id"
                ),
        @NamedQuery(
                name = "getFavoriteCount",
                query = "SELECT COUNT(fn) FROM Favorite AS fn WHERE fn.novel_id = :novel_id"
                ),
        @NamedQuery(
                name = "getMyFavorite",
                query = "SELECT fn FROM Favorite AS fn WHERE fn.user_id = :user_id ORDER BY fn.id DESC"
                ),
        @NamedQuery(
                name = "getMyFavoriteCount",
                query = "SELECT COUNT(fn) FROM Favorite AS fn WHERE fn.user_id = :user_id"
                )
})
@Entity
public class Favorite {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //お気に入り登録したユーザーのid
    @Column(name = "user_id" , nullable = false)
    private int user_id;

    //お気に入り登録したNovelのid
    @Column(name = "novel_id" , nullable = false)
    private int novel_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getNovel_id() {
        return novel_id;
    }

    public void setNovel_id(int novel_id) {
        this.novel_id = novel_id;
    }

}
