$.ajax({
    url: "http://localhost:8080/goods/queryTypes",
    success: function (data) {
        var typesList=eval(data);
        for (var i = 0; i < typesList.length; i++) {
            console.log(typesList[i].tname);
            var items="<li><a href=\"http://localhost:8082/search/list?keyword="+typesList[i].tname+"\" target=\"_blank\">"+typesList[i].tname+"</a></li>";
            $(".top_liebiao").append(items);
        }
    }
});
