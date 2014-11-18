package epam.lavrynev.dbmodel.db;

import com.google.gson.annotations.Expose;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by ASAt on 17.06.14.
 */
@Entity
@Table(name = "comments_state", schema = "", catalog = "test")
public class CommentsStateEntity {
    private Integer article;
    @Expose
    private Integer count;

    @Id
    @Column(name = "article")
    public Integer getArticle() {
        return article;
    }

    public void setArticle(Integer article) {
        this.article = article;
    }

    @Column(name = "count")
    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
