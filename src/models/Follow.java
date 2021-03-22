package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name = "follows")

@NamedQueries({
        @NamedQuery(
                name = "getFollowRelation",
                query = "SELECT f FROM Follow AS f WHERE f.user_id = :user_id AND f.follow_id = :follow_id"
                ),
        @NamedQuery(
                name = "getFollowCount",
                query = "SELECT COUNT(f) FROM Follow AS f WHERE f.user_id = :user_id"
                ),
        @NamedQuery(
                name = "getFollowerCount",
                query = "SELECT COUNT(f) FROM Follow AS f WHERE f.follow_id = :follow_id"
                ),
        @NamedQuery(
                name = "getAllFollows",
                query = "SELECT f FROM Follow AS f WHERE f.user_id = :user_id ORDER BY f.id DESC"
                ),
        @NamedQuery(
                name = "getAllFollowers",
                query = "SELECT f FROM Follow AS f WHERE f.follow_id = :follow_id ORDER BY f.id DESC"
                )

})


@Entity
public class Follow {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //フォローする側のid
    @Column(name = "user_id", nullable = false)
    private int user_id;

    //フォローされる側のid
    @Column(name ="follow_id", nullable = false)
    private int follow_id;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getFollow_id() {
        return follow_id;
    }

    public void setFollow_id(int follow_id) {
        this.follow_id = follow_id;
    }


}
