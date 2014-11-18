package epam.lavrynev.dbmodel;

import epam.lavrynev.dbmodel.db.*;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by ASAt on 09.06.14.
 */
public class ModelUtil implements Serializable{

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    SessionFactory sessionFactory;

    public ModelUtil() {
        sessionFactory = HibernateManager.getSessionFactory();
    }

    public ModelUtil(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Session getSession() throws HibernateException {
        return sessionFactory.openSession();
    }

    public List<ArticleStateEntity> getArticles(Integer limit, Integer fromRow){
        QueryParams qp = new QueryParams(fromRow);
        qp.setLimit(limit);
        return getArticlesList(qp);
    }

    public List<ArticleStateEntity> getArticlesBySearch(Integer limit, Integer fromRow, String query, String tag, Boolean isByTitle, Boolean isByContent){
        QueryParams qp = new QueryParams(fromRow);
        qp.setLimit(limit);
        qp.setQuery(query);
        qp.setTag(tag);
        qp.setByTitle(isByTitle);
        qp.setByContent(isByContent);
        List<ArticleStateEntity> list = getArticlesList(qp);
        return list;
    }

    public ArticleStateEntity getArticlesById(Integer id){
        QueryParams qp = new QueryParams();
        qp.setId(id);
        List<ArticleStateEntity> list = getArticlesList(qp);
        if (list.size()==0) throw new IllegalArgumentException("Article with id \""+id+"\" not founded!");
        return list.get(0);
    }

    private List<ArticleStateEntity> getArticlesList(QueryParams qp){
        final Session session = getSession();
        List<ArticleStateEntity> articles;
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(ArticleStateEntity.class,"articleState")
                    .add(Restrictions.eq("state.idState", 1))
                    .setFirstResult(qp.getFrom())
                    .addOrder(Order.desc("created"));
            if (qp.getId()!=null) criteria.add(Restrictions.eq("idArticle",qp.getId()));
            if (qp.getLimit()>0) criteria.setMaxResults(qp.getLimit());
            if (qp.getQuery()!=null) {
                SimpleExpression se1 = Restrictions.like("title", qp.getQuery(), MatchMode.ANYWHERE);
                SimpleExpression se2 = Restrictions.like("content", qp.getQuery(), MatchMode.ANYWHERE);
                if (qp.getByTitle()&&qp.getByContent()) {
                    criteria.add(Restrictions.or(se1,se2));
                } else {
                    if(qp.getByTitle()) criteria.add(se1);
                    if(qp.getByContent()) criteria.add(se2);
                }
                updateSearch(qp.getQuery(), session);
            }
            if (qp.getTag()!=null){
                criteria.createAlias("articleState.tags","tags1")
                        .add(Restrictions.eq("tags1.tag",qp.getTag()));
            }
            articles = criteria.list();
            session.getTransaction().commit();
        } finally {
            session.close();
        }
        return articles;
    }

    public Integer getArticlesCount(){
        return getArticlesCount(null,null,false,false);
    }
    public Integer getArticlesCount(String query,String tag, Boolean isByTitle, Boolean isByContent){
        final Session session = getSession();
        Integer count;
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(ArticleStateEntity.class,"articleState")
                    .setProjection(Projections.rowCount());
            if (query!=null){
                SimpleExpression se1 = Restrictions.like("title", query, MatchMode.ANYWHERE);
                SimpleExpression se2 = Restrictions.like("content", query, MatchMode.ANYWHERE);
                if (isByTitle&&isByContent) {
                    criteria.add(Restrictions.or(se1,se2));
                } else {
                    if(isByTitle) criteria.add(se1);
                    if(isByContent) criteria.add(se2);
                }
            }
            if (tag!=null){
                criteria.createAlias("articleState.tags","tags1")
                        .add(Restrictions.eq("tags1.tag",tag));
            }
            count = ((Long)criteria.uniqueResult()).intValue();
            session.getTransaction().commit();
        } finally {
            session.close();
        }
        return count;
    }

    public Integer insertArticle(ArticleStateEntity articleStateEntity){
        final Session session = getSession();
        try {
            session.beginTransaction();
            Long today = new Date().getTime();
            articleStateEntity.setCreated(today);
            articleStateEntity.setModified(today);
            session.save(articleStateEntity);
            Integer id = articleStateEntity.getIdArticle();
            updateTags(id, articleStateEntity.getTags(), session);
            session.getTransaction().commit();
            return id;
        } finally {
            session.close();
        }
    }

    public void updateArticle(Integer id, ArticleStateEntity articleStateEntity){
        final Session session = getSession();
        try {
            session.beginTransaction();
            articleStateEntity.setIdArticle(id);
            ArticleStateEntity oldArticleStateEntity = getArticlesById(id);
            articleStateEntity.setModified(new Date().getTime());
            articleStateEntity.setGroup(oldArticleStateEntity.getGroup());
            articleStateEntity.setOwner(oldArticleStateEntity.getOwner());
            updateTags(id,articleStateEntity.getTags(),session);
            session.update(articleStateEntity);
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }

    private void updateTags(Integer idArticle, Set<TagsEntity> tags, Session session){
        List<TagsEntity> tagsEntityList = session.createCriteria(TagsEntity.class)
                .add(Restrictions.eq("article",idArticle))
                .list();
        for(TagsEntity t : tags){
            t.setArticle(idArticle);
            if (!tagsEntityList.contains(t)){
                session.save(t);
            }
        }
    }

    private void updateSearch(String search, Session session){
        SearchStatisticEntity searchStatisticEntity = (SearchStatisticEntity)session.createCriteria(SearchStatisticEntity.class)
                .add(Restrictions.eq("search",search))
                .uniqueResult();
        if (searchStatisticEntity!=null){
            searchStatisticEntity.upCount();
            session.update(searchStatisticEntity);
        }else{
            searchStatisticEntity = new SearchStatisticEntity(search);
            session.save(searchStatisticEntity);
        }
    }

    public List<CommentsEntity> getComments(int limit){
        final Session session = getSession();
        List<CommentsEntity> comments;
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(CommentsEntity.class)
                    .addOrder(Order.desc("created"))
                    .setMaxResults(limit);
            comments = criteria.list();
            session.getTransaction().commit();
        } finally {
            session.close();
        }
        return comments;
    }

    public List<CommentsEntity> getCommentsByArticle(int articleId){
        final Session session = getSession();
        List<CommentsEntity> comments;
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(CommentsEntity.class)
                    .add(Restrictions.eq("article", articleId))
                    .add(Restrictions.isNull("parent"));
            comments = criteria.list();
            session.getTransaction().commit();
        } finally {
            session.close();
        }
        return comments;
    }

    public CommentsEntity getCommentsById(Integer commentId){
        final Session session = getSession();
        List<CommentsEntity> comments;
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(CommentsEntity.class)
                    .add(Restrictions.eq("idComment", commentId));
            comments = criteria.list();
            session.getTransaction().commit();
        } finally {
            session.close();
        }
        return comments.get(0);
    }

    public void insertComment(CommentsEntity commentsEntity){
        final Session session = getSession();
        try {
            session.beginTransaction();
            Long today = new Date().getTime();
            commentsEntity.setModified(today);
            commentsEntity.setCreated(today);
            session.save(commentsEntity);
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }

    public void updateComment(Integer id, CommentsEntity commentsEntity){
        final Session session = getSession();
        try {
            session.beginTransaction();
            Long today = new Date().getTime();
            CommentsEntity oldComment = getCommentsById(id);
            oldComment.setModified(today);
            oldComment.setContent(commentsEntity.getContent());
            session.update(oldComment);
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }

    public List<TagsStateEntity>getTagsState(){
        final Session session = getSession();
        List<TagsStateEntity> tags;
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(TagsStateEntity.class);
            tags = criteria.list();
            session.getTransaction().commit();

        } finally {
            session.close();
        }
        return tags;
    }

    public List<SearchStatisticEntity>getSearchStatistic(){
        final Session session = getSession();
        List<SearchStatisticEntity> search;
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(SearchStatisticEntity.class)
                    .addOrder(Order.desc("count"))
                    .setMaxResults(10);
            search = criteria.list();
            session.getTransaction().commit();

        } finally {
            session.close();
        }
        return search;
    }


}
