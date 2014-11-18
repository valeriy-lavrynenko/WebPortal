package epam.lavrynev.dbmodel;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by ASAt on 05.06.14.
 */
public class Main {

    public static void main(final String[] args) throws Exception {
        ModelUtil model = new ModelUtil();
        try {
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            Integer id = 3;
//            String json = "{\"title\":\"Sample articleWWW\",\"content\":\"This is sample article...\",\"state\":{\"idState\":1},\"tags\":[{\"tag\":\"sampleNEW\"},{\"tag\":\"sample\"}]}";
//            ArticleStateEntity articleStateEntity = gson.fromJson(json,ArticleStateEntity.class);
//            model.updateArticle(id,articleStateEntity);
            System.out.println(gson.toJson(model.getArticlesBySearch(0,0,null,"sampleNEW",false,false)));

        } finally {
            HibernateManager.destroy();
        }
    }
}
