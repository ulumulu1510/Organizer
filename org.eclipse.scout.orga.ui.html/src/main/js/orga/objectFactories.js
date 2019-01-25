scout.objectFactories = $.extend(
    scout.objectFactories, {
     'VisFieldAdapter': function() {
       return new orga.VisFieldAdapter();
     },
     'VisField': function() {
         return new orga.VisField();
       }
});
