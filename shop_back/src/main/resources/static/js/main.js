layui.use(['form','element','layer','jquery'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        element = layui.element;
        $ = layui.jquery;



    //用户数量
    $.get("../json/userList.json",function(data){
        $(".userAll span").text(data.count);
    })

    //外部图标
    $.get(iconUrl,function(data){
        $(".outIcons span").text(data.split(".icon-").length-1);
    })

})
