package epam.lavrynev.dbmodel.db;

import com.google.gson.annotations.Expose;

import javax.persistence.*;

/**
 * Created by ASAt on 09.06.14.
 */
@Entity
@Table(name = "articles", schema = "", catalog = "test")
public class ArticlesEntity {
    public ArticlesEntity() {
    }

    @Expose(serialize = false)
    private Integer idArticle;

    @Expose
    private String title;

    @Expose
    private String content;
//    private ArticleStateEntity articleStateByIdarticles;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "idarticles", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    public Integer getIdArticle() {
        return idArticle;
    }

    public void setIdArticle(Integer idarticles) {
        this.idArticle = idarticles;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ArticlesEntity that = (ArticlesEntity) o;

        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        if (idArticle != null ? !idArticle.equals(that.idArticle) : that.idArticle != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idArticle != null ? idArticle.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }

//    @OneToOne(mappedBy = "articlesByArticle")
//    public ArticleStateEntity getArticleStateByIdarticles() {
//        return articleStateByIdarticles;
//    }
//
//    public void setArticleStateByIdarticles(ArticleStateEntity articleStateByIdarticles) {
//        this.articleStateByIdarticles = articleStateByIdarticles;
//    }

}
