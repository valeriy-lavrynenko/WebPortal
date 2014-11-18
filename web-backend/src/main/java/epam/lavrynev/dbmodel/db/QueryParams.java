package epam.lavrynev.dbmodel.db;

/**
 * Created by ASAt on 23.06.14.
 */
public class QueryParams {
    public QueryParams(Integer from) {
        this.from = from;
        this.limit = 0;
    }

    public QueryParams() {
        this(0);
    }

    private Integer limit;
    private Integer from;
    private Integer id;
    private String query;
    private String tag;

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getFrom() {
        return from;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Boolean getByTitle() {
        return byTitle;
    }

    public void setByTitle(Boolean byTitle) {
        this.byTitle = byTitle;
    }

    public Boolean getByContent() {
        return byContent;
    }

    public void setByContent(Boolean byContent) {
        this.byContent = byContent;
    }

    private Boolean byTitle;
    private Boolean byContent;

}
