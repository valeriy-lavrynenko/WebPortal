'use strict';

/* Services */


// Demonstrate how to register services
// In this case it is a simple value service.
var webServices =  angular.module('myApp.services', ['ngResource']);

webServices.factory('ModelDB', ['$resource', 'Configuration',
    function($resource, Configuration){
        return {
            rest : $resource('http://localhost:8081/web/rest/:item/:query',{},{
                getArticles : {method : 'GET', params : {query : '', limit : Configuration.articleListView.limit, item : 'articles'}},
                getArticlesById : {method : 'GET', params : {item : 'articles'}},
                searchArticle : {method : 'GET', params : {query : 'search', limit : Configuration.articleListView.limit,item : 'articles'}},
                insertArticle : {method : 'POST', params : {item : 'articles'}},
                updateArticle : {method : 'PUT', params : {item : 'articles'}},
                getComments : {method : 'GET', params : {query : '', item : 'comments'}, isArray : true},
                getCommentsNew : {method : 'GET', params : {query : 'all', item : 'comments', limit : Configuration.commentsView.lastCommentsLimit}, isArray : true},
                getCommentsById : {method : 'GET', params : {item : 'comments'}},
                insertComment : {method : 'POST', params : {item : 'comments'}},
                updateComment : {method : 'PUT', params : {item : 'comments'}},
                getTags : {method : 'GET', params : {query : 'tags', item : 'static'}, isArray : true},
                getSearches : {method : 'GET', params : {query : 'searches', item : 'static'}, isArray : true}
            }),

            setPaginator : function(res, from){
                var arr = new Array();
                var limit = Configuration.articleListView.limit;
                for(var i = 0;res.totalArticles>i*limit;i++){
                    arr.push({
                        view : i+1,
                        from : i*limit
                    })
                }
                res.countList = arr;
                res.currentPage = Math.ceil((from+1)/limit);
            },

            tagToString : function(tags){
                var str = "";
                for(var i=0; i<tags.length; i++) {
                    str += tags[i].tag;
                    if(i!=tags.length-1){
                        str+= ", ";
                    }
                };
                return str;
            },

            stringToTag : function(tags){
                var arr = tags.split(", ");
                for(var i=0; i<arr.length; i++){
                    arr[i] = {
                        tag : arr[i]
                    }
                };
                return arr;

            }
        }
    }]);

webServices.factory('Configuration',[
    function(){
        return{
            articleListView : {
                limit : 3
            },
            commentsView : {
                lastCommentsLimit : 3
            }
        }

    }]);

webServices.factory('LocalInstance',['ModelDB',
    function(ModelDB){
        return{
            searchForm : {
                enabled : false,
                value : "",
                byTitle : true,
                byContent : false
            },
            articleList : {
                defaultUri : function(){
                    this.uri = {'from' : 0};
                },
                updateArticles : function(from){
                    var localArticleState = ModelDB.rest.getArticles(this.uri, function(){
                        ModelDB.setPaginator(localArticleState,from);
                    });
                    return localArticleState;
                }
            }
        }

    }]);
