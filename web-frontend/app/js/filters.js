'use strict';

/* Filters */

angular.module('myApp.filters', [])
    .filter('nullable', [function() {
    return function(text) {
      if (text==undefined){
          return 0;
      } else {
          return text;
      }
    };
    }]);

