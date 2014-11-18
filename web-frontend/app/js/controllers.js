'use strict';

/* Controllers */

    angular.module('myApp.controllers', [])
        .controller('BlogListCtrl', ['$scope', 'ModelDB', 'LocalInstance', function($scope, ModelDB, LocalInstance) {
            $scope.updateArticles = function(from){
                LocalInstance.articleList.uri.from = from;
                $scope.articleState = LocalInstance.articleList.updateArticles(from);
            };

            if(LocalInstance.articleList.preload){
                LocalInstance.articleList.preload = false;
            } else {
                LocalInstance.articleList.defaultUri();
            };
            $scope.updateArticles(0);
        }])
        .controller('SideBarCtrl', ['$scope', 'ModelDB', function($scope, ModelDB) {
        }])
        .controller('TagsCtrl', ['$scope', 'ModelDB', 'LocalInstance','$location', function($scope, ModelDB, LocalInstance,$location) {
            $scope.tags = ModelDB.rest.getTags();

            $scope.searchByTag = function(tag){
                LocalInstance.articleList.uri = {query : 'search', tag : tag};
                if($location.path()!=="/blog"){
                    LocalInstance.articleList.preload = true;
                    $location.path("/blog");
                } else{
                    $scope.updateArticles(0);
                }
            }
        }])
        .controller('SearchCtrl', ['$scope', 'ModelDB', 'LocalInstance','$location', function($scope, ModelDB, LocalInstance, $location) {
            $scope.searchForm = LocalInstance.searchForm;
            $scope.search = function(enabled){
                if(enabled){
                    LocalInstance.articleList.uri = {query : 'search', q : $scope.searchForm.value, byTitle : $scope.searchForm.byTitle, byContent : $scope.searchForm.byContent};
                }else{
                    LocalInstance.articleList.defaultUri();
                }
                if($location.path()!=="/blog"){
                    LocalInstance.articleList.preload = true;
                    $location.path("/blog");
                } else{
                    $scope.updateArticles(0);
                }
                $scope.searchForm.enabled = enabled;
                LocalInstance.searchForm = $scope.searchForm;
            };

            $scope.isEnabled = function(){
                return LocalInstance.searchForm.enabled;
            };

            $scope.getSearchStatistic = function(){
                $scope.searchStatistic = ModelDB.rest.getSearches();
            }

//            if(!LocalInstance.articleList.preload) {$scope.searchForm.enabled = false};
        }])
        .controller('BlogPostCtrl', ['$scope', '$routeParams', 'ModelDB', function($scope, $routeParams, ModelDB) {
            $scope.articleState = {
                article : ModelDB.rest.getArticlesById({query:$routeParams.blogId})
            };
        }])
        .controller('CommentsCtrl', ['$scope', '$routeParams', 'ModelDB', function($scope, $routeParams, ModelDB) {
            $scope.getComments = function(){
                $scope.articleState.comments = ModelDB.rest.getComments({articleId:$routeParams.blogId})
            };
            $scope.getCommentsNew = function(){
                $scope.commentsNew = ModelDB.rest.getCommentsNew();
            };
            $scope.getNewComment = function(){
                return {
                    content : "",
                    article : $routeParams.blogId,
                    owner : {
                        idUser : 2
                    }
                };
            }

            $scope.uploadComment = function(comment){
                ModelDB.rest.insertComment(comment, function(){

                    $scope.getComments();
                    if($scope.articleState.article.comments == undefined){
                        $scope.articleState.article.comments = {
                            count :1
                        }
                    } else {
                        $scope.articleState.article.comments.count++;
                    }

                });

            };
        }])
        .controller('BlogPostEditCtrl', ['$scope', '$routeParams', 'ModelDB', '$location', function($scope, $routeParams, ModelDB, $location) {
            if($routeParams.blogId==="new"){
                $scope.articleState = {
                    article : {
                        "title" : "",
                        "content" : "",
                        "group" : {
                            "idGroup" : 2
                        },
                        "owner" : {
                            "idUser" : 2
                        },
                        "state" : {
                            "idState" : 1
                        },
                        "tags" : []
                    }
                };
            }else{
                $scope.articleState = {
                    article : ModelDB.rest.getArticlesById({query:$routeParams.blogId},function(){
                        $scope.articleState.article.tags = ModelDB.tagToString($scope.articleState.article.tags);
                    })
                };
            }
            $scope.updateArticle = function(){
                if($scope.articleEdit.$invalid){
                    $scope.error.enabled = true;
                    $scope.error.message = "Something going wrong!!!";
                    return null;
                }
                $scope.articleState.article.tags = ModelDB.stringToTag($scope.articleState.article.tags);
                if($routeParams.blogId==="new"){
                    ModelDB.rest.insertArticle($scope.articleState.article, function(){
                        $location.path("/blog");
                    });
                }else{
                    ModelDB.rest.updateArticle({query:$routeParams.blogId},$scope.articleState.article, function(){
                        $location.path("/blog/"+$routeParams.blogId);
                    });
                }
            }

            $scope.error = {
                enabled : false
            }

        }]);
