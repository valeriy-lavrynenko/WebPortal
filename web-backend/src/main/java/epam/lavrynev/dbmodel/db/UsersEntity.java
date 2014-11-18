package epam.lavrynev.dbmodel.db;

import com.google.gson.annotations.Expose;

import javax.persistence.*;

/**
 * Created by ASAt on 09.06.14.
 */
@Entity
@Table(name = "users", schema = "", catalog = "test")
public class UsersEntity {
    @Expose()
    private Integer idUser;
    @Expose
    private String name;
    @Expose(serialize = false)
    private String pass;
//    private Collection<ArticleStateEntity> articleStatesByIdusers;
//    private Collection<CommentsEntity> commentsesByIdusers;


    @Id
    @Column(name = "idusers", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idusers) {
        this.idUser = idusers;
    }

    @Basic
    @Column(name = "name", nullable = false, insertable = true, updatable = true, length = 45, precision = 0)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "pass", nullable = false, insertable = true, updatable = true, length = 45, precision = 0)
    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UsersEntity that = (UsersEntity) o;

        if (idUser != null ? !idUser.equals(that.idUser) : that.idUser != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (pass != null ? !pass.equals(that.pass) : that.pass != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idUser != null ? idUser.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (pass != null ? pass.hashCode() : 0);
        return result;
    }

//    @OneToMany(mappedBy = "usersByOwner")
//    public Collection<ArticleStateEntity> getArticleStatesByIdusers() {
//        return articleStatesByIdusers;
//    }
//
//    public void setArticleStatesByIdusers(Collection<ArticleStateEntity> articleStatesByIdusers) {
//        this.articleStatesByIdusers = articleStatesByIdusers;
//    }
//
//    @OneToMany(mappedBy = "usersByUser")
//    public Collection<CommentsEntity> getCommentsesByIdusers() {
//        return commentsesByIdusers;
//    }
//
//    public void setCommentsesByIdusers(Collection<CommentsEntity> commentsesByIdusers) {
//        this.commentsesByIdusers = commentsesByIdusers;
//    }
//

}
