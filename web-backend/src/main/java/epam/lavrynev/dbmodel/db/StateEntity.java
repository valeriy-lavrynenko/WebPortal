package epam.lavrynev.dbmodel.db;

import com.google.gson.annotations.Expose;

import javax.persistence.*;

/**
 * Created by ASAt on 09.06.14.
 */
@Entity
@Table(name = "state", schema = "", catalog = "test")
public class StateEntity {
    @Expose
    private Integer idState;

    @Expose
    private String name;
//    private Collection<ArticleStateEntity> articleStatesByIdstate;

    @Id
    @Column(name = "idstate", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    public Integer getIdState() {
        return idState;
    }

    public void setIdState(Integer idstate) {
        this.idState = idstate;
    }

    @Basic
    @Column(name = "name", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StateEntity that = (StateEntity) o;

        if (idState != null ? !idState.equals(that.idState) : that.idState != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idState != null ? idState.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

//    @OneToMany(mappedBy = "stateByState")
//    public Collection<ArticleStateEntity> getArticleStatesByIdstate() {
//        return articleStatesByIdstate;
//    }
//
//    public void setArticleStatesByIdstate(Collection<ArticleStateEntity> articleStatesByIdstate) {
//        this.articleStatesByIdstate = articleStatesByIdstate;
//    }
}
