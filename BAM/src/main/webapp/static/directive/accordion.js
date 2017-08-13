/**
 * @Author Ben Babics 
 * 
 * https://codepen.io/benbabics/pen/daiDk
 */
app.directive('accordion', function() {
  return {
    restrict: 'AE',
    replace: true,
    transclude: true,
    template: '<ul class="accordion" ng-transclude></ul>',
    controller: function() {
      var items = [];
      
      this.register = function(item) {
        items.push(item);
        
        if (items.length < 2) {
          item.isVisible = true;
        }
      };
      
      this.handleSelectedItem = function(selectedItem) {
        angular.forEach(items, function(item) {
          if (selectedItem != item) {
            item.isVisible = false;
          }
          else {
            item.isVisible = true;
          }
        });
      }
    }
  }
});

app.directive('expander', function() {
  return {
    restrict: 'E',
    replace: true,
    transclude: true,
    require: '?^accordion',
    scope: {
      title: '@'
    },
    template: '<li class="expander"><div class="title" ng-click="handleToggle()">{{ title }}</div><div class="content" ng-show="isVisible" ng-transclude></div></li>',
    link: function(scope, element, attrs, accordionController) {
      var hasAccordionController = !!accordionController;
      
      scope.isVisible = false;
      
      scope.handleToggle = function() {
        scope.isVisible = !scope.isVisible;
        
        if (hasAccordionController) {
          accordionController.handleSelectedItem(scope);
        }
      }
      
      if (hasAccordionController) {
        accordionController.register(scope);
      }
    }
  };
});

angular.bootstrap('#app', ['app']);