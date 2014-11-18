package epam.lavrynev.dbmodel.db;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Created by ASAt on 16.06.14.
 */

@Entity
@Table(name = "tags", schema = "", catalog = "test")
public class TagsEntity {
    @Expose
    private Integer idTag;
    @Expose(serialize = false)
    private Integer article;
    @Expose
    private String tag;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "tag_id", nullable = false, insertable = true, updatable = true, length = 11, precision = 0)
    public Integer getIdTag() {
        return idTag;
    }

    public void setIdTag(Integer idTag) {
        this.idTag = idTag;
    }

    @Basic
    @Column(name = "article", nullable = false, insertable = true, updatable = true, length = 11, precision = 0)
    public Integer getArticle() {
        return article;
    }

    public void setArticle(Integer article) {
        this.article = article;
    }

    @Basic
    @Column(name = "tag_name", nullable = false, insertable = true, updatable = true, length = 12, precision = 0)
    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public boolean equals(Object obj) {
        TagsEntity tagObj = (TagsEntity)obj;
        return this.article == tagObj.getArticle() && this.tag.equals(tagObj.getTag());
    }
}
