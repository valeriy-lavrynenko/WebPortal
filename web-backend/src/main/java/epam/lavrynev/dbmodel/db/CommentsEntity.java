package epam.lavrynev.dbmodel.db;

import com.google.gson.annotations.Expose;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by ASAt on 09.06.14.
 */
@Entity
@Table(name = "comments", schema = "", catalog = "test")
public class CommentsEntity {
    public CommentsEntity() {
    }

    @Expose
    private Integer idComment;

    @Expose
    private String content;
    @Expose(serialize = false)
    private CommentsEntity parent;
    @Expose
    private Long created;
    @Expose
    private Long modified;
    @Expose
    private Integer article;
    @Expose
    private UsersEntity owner;

    @Expose(deserialize = false)
    private Set<CommentsEntity> childComment;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "idcomments", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    public Integer getIdComment() {
        return idComment;
    }

    public void setIdComment(Integer idcomments) {
        this.idComment = idcomments;
    }

    @Basic
    @Column(name = "content", nullable = true, insertable = true, updatable = true, length = 45, precision = 0)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @ManyToOne
    @JoinColumn(name = "parent", nullable = true, insertable = true, updatable = true)
    public CommentsEntity getParent() {
        return parent;
    }

    public void setParent(CommentsEntity parent) {
        this.parent = parent;
    }

    @Basic
    @Column(name = "created", nullable = false, insertable = true, updatable = false, length = 19, precision = 0)
    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    @Basic
    @Column(name = "modified", nullable = false, insertable = true, updatable = true, length = 19, precision = 0)
    public Long getModified() {
        return modified;
    }

    public void setModified(Long modified) {
        this.modified = modified;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CommentsEntity that = (CommentsEntity) o;

        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        if (created != null ? !created.equals(that.created) : that.created != null) return false;
        if (idComment != null ? !idComment.equals(that.idComment) : that.idComment != null) return false;
        if (parent != null ? !parent.equals(that.parent) : that.parent != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idComment != null ? idComment.hashCode() : 0;
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (parent != null ? parent.hashCode() : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        return result;
    }

//    @ManyToOne
//    @JoinColumn(name = "article", referencedColumnName = "article", nullable = false)
//    public ArticleStateEntity getArticleStateByArticle() {
//        return articleStateByArticle;
//    }
//
//    public void setArticleStateByArticle(ArticleStateEntity articleStateByArticle) {
//        this.articleStateByArticle = articleStateByArticle;
//    }

    @Basic
    @Column(name = "article", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    public Integer getArticle() {
        return article;
    }

    public void setArticle(Integer article) {
        this.article = article;
    }


    @ManyToOne
    @JoinColumn(name = "user", referencedColumnName = "idusers", nullable = false)
    public UsersEntity getOwner() {
        return owner;
    }

    public void setOwner(UsersEntity usersByUser) {
        this.owner = usersByUser;
    }

    @OneToMany(mappedBy = "parent", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    public Set<CommentsEntity> getChildComments(){
        return childComment;
    }

    public void setChildComments(Set<CommentsEntity> childComment){
        this.childComment = childComment;
    }
}
