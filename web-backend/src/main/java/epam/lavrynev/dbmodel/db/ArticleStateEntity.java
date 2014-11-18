package epam.lavrynev.dbmodel.db;

import com.google.gson.annotations.Expose;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by ASAt on 09.06.14.
 */
@Entity
@Table(name = "article_state", schema = "", catalog = "test")
public class ArticleStateEntity {
    @Expose
    private Integer idArticle;
    @Expose
    private Long created;
    @Expose
    private Long modified;
//    @Expose
//    private ArticlesEntity article;
    @Expose
    private GroupEntity group;
    @Expose
    private UsersEntity owner;
    @Expose
    private StateEntity state;
    @Expose
    private String title;
    @Expose
    private String content;
    @Expose
    private Set<TagsEntity> tags;

    @Expose
    private CommentsStateEntity comments;

    //    private Collection<CommentsEntity> commentsesByArticle;

    public ArticleStateEntity() {
    }

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "article", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    public Integer getIdArticle() {
        return idArticle;
    }

    public void setIdArticle(Integer article) {
        this.idArticle = article;
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



//    @OneToOne(mappedBy = "idArticle")
////    @JoinColumn(name = "article", referencedColumnName = "idarticles", nullable = false)
//    public ArticlesEntity getArticle() {
//        return article;
//    }
//
//    public void setArticle(ArticlesEntity articlesByArticle) {
//        this.idArticle = articlesByArticle.getIdArticle();
//        this.article = articlesByArticle;
//    }

    @ManyToOne
    @JoinColumn(name = "\"group\"", referencedColumnName = "idgroup", nullable = false)
    public GroupEntity getGroup() {
        return group;
    }

    public void setGroup(GroupEntity groupByGroup) {
        this.group = groupByGroup;
    }

    @ManyToOne
    @JoinColumn(name = "\"owner\"", referencedColumnName = "idusers", nullable = false)
    public UsersEntity getOwner() {
        return owner;
    }

    public void setOwner(UsersEntity usersByOwner) {
        this.owner = usersByOwner;
    }

    @ManyToOne
    @JoinColumn(name = "state", referencedColumnName = "idstate", nullable = false)
    public StateEntity getState() {
        return state;
    }

    public void setState(StateEntity stateByState) {
        this.state = stateByState;
    }

    @OneToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @JoinColumn(name = "article", referencedColumnName = "article",updatable = false)
    public Set<TagsEntity> getTags() {
        return tags;
    }

    public void setTags(Set<TagsEntity> tags) {
        this.tags = tags;
    }

    @OneToOne
    @JoinColumn(name = "article", referencedColumnName = "article", nullable = false)
    public CommentsStateEntity getComments() {
        return comments;
    }

    public void setComments(CommentsStateEntity comments) {
        this.comments = comments;
    }

    @Basic
    @Column(name = "title", nullable = true, insertable = true, updatable = true, length = 45, precision = 0)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "content", nullable = true, insertable = true, updatable = true, length = 2147483647, precision = 0)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ArticleStateEntity that = (ArticleStateEntity) o;

        if (idArticle != null ? !idArticle.equals(that.idArticle) : that.idArticle != null) return false;
        if (created != null ? !created.equals(that.created) : that.created != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idArticle != null ? idArticle.hashCode() : 0;
        result = 31 * result + (created != null ? created.hashCode() : 0);
        return result;
    }
}
