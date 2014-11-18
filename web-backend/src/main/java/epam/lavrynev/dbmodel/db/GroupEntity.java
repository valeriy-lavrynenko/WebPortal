package epam.lavrynev.dbmodel.db;

import com.google.gson.annotations.Expose;

import javax.persistence.*;

/**
 * Created by ASAt on 09.06.14.
 */
@Entity
@Table(name = "group", schema = "", catalog = "test")
public class GroupEntity {
    @Expose
    private Integer idGroup;

    @Expose
    private String name;
//    private Collection<ArticleStateEntity> articleStatesByIdgroup;
//    private Collection<UsersEntity> usersesByIdgroup;

    @Id
    @Column(name = "idgroup", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    public Integer getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(Integer idgroup) {
        this.idGroup = idgroup;
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

        GroupEntity that = (GroupEntity) o;

        if (idGroup != null ? !idGroup.equals(that.idGroup) : that.idGroup != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idGroup != null ? idGroup.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

//    @OneToMany(mappedBy = "groupByGroup")
//    public Collection<ArticleStateEntity> getArticleStatesByIdgroup() {
//        return articleStatesByIdgroup;
//    }
//
//    public void setArticleStatesByIdgroup(Collection<ArticleStateEntity> articleStatesByIdgroup) {
//        this.articleStatesByIdgroup = articleStatesByIdgroup;
//    }
//
//    @OneToMany(mappedBy = "groupByGroup")
//    public Collection<UsersEntity> getUsersesByIdgroup() {
//        return usersesByIdgroup;
//    }
//
//    public void setUsersesByIdgroup(Collection<UsersEntity> usersesByIdgroup) {
//        this.usersesByIdgroup = usersesByIdgroup;
//    }
}
