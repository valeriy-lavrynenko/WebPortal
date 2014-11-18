package epam.lavrynev.dbmodel.db;

import com.google.gson.annotations.Expose;

import javax.persistence.*;

/**
 * Created by ASAt on 16.06.14.
 */
@Entity
@Table(name = "search_statistic", schema = "", catalog = "test")
public class SearchStatisticEntity {
    private Integer id;
    @Expose
    private String search;
    @Expose
    private Integer count;

    public SearchStatisticEntity(String search) {
        this.search = search;
        this.count = 1;
    }

    public SearchStatisticEntity() {
    }

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "search")
    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    @Column(name = "count")
    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
    public void upCount() {
        this.count++;
    }
}
