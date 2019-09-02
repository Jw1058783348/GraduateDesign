$(function(){
    $.ajax({
        url:"http://localhost:8084/sso/checkLogin",
        method:"post",
        dataType:"jsonp"
    });
})
function isLogin(data) {
    console.log(data);
    if(data !=null){
        var person=eval("("+data+")");
        //登录成功
        $("#login_eles").html("<li>"+person.name+",您好!</li>\n" +
            "                    <li>|</li>\n" +
            "                    <li><a href=\"http://localhost:8081/index/toSelfInfo\">个人中心</a></li>"+
            "                    <li>|</li>\n" +
            "                    <li><a href=\"http://localhost:8084/sso/logout\">注销</a></li>");
    }else{
        //登录失败
        $("#login_eles").html("<li> <a href=\"javascript:login();\" target=\"_blank\">登录</a>\n" +
            "                    </li>\n" +
            "                    <li>|</li>\n" +
            "                    <li><a href=\"http://localhost:8084/sso/toRegister\" target=\"_blank\" >注册</a></li>");
    }
}
function login() {
    var returnURL=location.href;
    returnURL=encodeURI(returnURL);
    returnURL.replace("&","%26");
    location.href="http://localhost:8084/sso/toLogin?returnURL="+returnURL;
}