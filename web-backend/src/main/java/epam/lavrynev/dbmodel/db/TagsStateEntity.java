package epam.lavrynev.dbmodel.db;

import com.google.gson.annotations.Expose;

import javax.persistence.*;

/**
 * Created by ASAt on 16.06.14.
 */
@Entity
@Table(name = "tags_state", schema = "", catalog = "test")
public class TagsStateEntity {
    @Expose
    private String name;
    @Expose
    private Integer count;

    @Id
    @Column(name = "tag")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "count")
    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
