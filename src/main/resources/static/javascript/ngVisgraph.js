/** directive for building vis.js network graph */
angular.module('ngVisgraph', [])
  .directive('visGraph', [function() {
  return {
    restrict: 'AE',
    scope: {
      data: '=data',
      options: '=options',
      event: '@event',
      callback: '&'
    },
    link: function(scope, element, attrs) {
 	  var container = element[0], buildGraph = function(scope) {
 	  /** instantiate the graph */
        var graph = null;

        /** build graph, pass in properties */
        graph = new vis.Network(container, scope.data, scope.options);
          return graph.on(scope.event, function(properties) {
            if (properties.nodes.length !== 0) {
              scope.callback({params: properties});
            }
        });
      };
      /** async watch for data change events */
      scope.$watch('data', function(newval, oldval) {
        buildGraph(scope);
      }, true);
    }
  };
}]);