'use strict';


angular.module('myApp', [
  'ngRoute',
  'myApp.filters',
  'myApp.services',
  'myApp.directives',
  'myApp.controllers'
]).
config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/blog', {templateUrl: 'partials/blog.html', controller: 'BlogListCtrl'});
  $routeProvider.when('/blog/:blogId', {templateUrl: 'partials/blog_post.html', controller: 'BlogPostCtrl'});
  $routeProvider.when('/blog/edit/:blogId', {templateUrl: 'partials/blog_post_edit.html', controller: 'BlogPostEditCtrl'});
  $routeProvider.when('/blog/edit/new', {templateUrl: 'partials/blog_post_edit.html', controller: 'BlogPostEditCtrl'});
  $routeProvider.otherwise({redirectTo: '/blog'});
}]);
