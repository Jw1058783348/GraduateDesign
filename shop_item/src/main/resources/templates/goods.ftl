<!DOCTYPE html>
<head>
    <base href="${context}"/>
    <meta charset="UTF-8">
    <title>${goods.gname}立即购买</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <script type="text/javascript" src="/js/jquery.js"></script>
    <script type="text/javascript" src="http://localhost:8084/js/login.js"></script>
</head>
<body>
<!-- start header -->
<header>
    <div class="top center">
        <div class="right fr">
            <div class="gouwuche fr"><a href="http://localhost:8085/cart/list">购物车</a></div>
            <div class="fr">
                <ul id="login_eles">
                </ul>
            </div>
            <div class="clear"></div>
        </div>
        <div class="clear"></div>
    </div>
</header>
<!--end header -->

<!-- start banner_x -->
<div class="banner_x center">
    <a href="http://localhost:8081/" target="_blank"><div class="logo fl"></div></a>
    <a href=""><div class="ad_top fl"></div></a>
    <div class="nav fl">
        <ul class="top_liebiao">
            <script>
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
            </script>
        </ul>
    </div>
    <div class="search fr">
        <form action="http://localhost:8082/search/list" method="get" >
            <div class="text fl">
                <input type="text" class="shuru"  name="keyword" placeholder="小米6">
            </div>
            <div class="submit fl">
                <input type="submit" class="sousuo" value="搜索"/>
            </div>
            <div class="clear"></div>
        </form>
        <div class="clear"></div>
    </div>
</div>
<!-- end banner_x -->


<!-- xiangqing -->
<form action="post" method="">
    <div class="xiangqing">
        <div class="neirong w">
            <div class="xiaomi6 fl">${goods.gname}</div>
            <div class="clear"></div>
        </div>
    </div>

    <div class="jieshao mt20 w">
        <div class="left fl"><img src="http://192.168.154.128/${goods.gimage}" style="width: 150 px;height: 150px"></div>
        <div class="right fr">
            <div class="h3 ml20 mt20">${goods.gname}</div>
            <div class="jianjie mr40 ml20 mt10">${goods.ginfo}</div>
            <div class="jiage ml20 mt10">${versions.gprice}元</div>
            <div class="ft20 ml20 mt20">当前版本</div>
            <div class="xzbb ml20 mt10">
                <div class="banben fl">
                    <a>
                        <span>${versions.gversion}</span>
                        <span>${versions.gprice}</span>
                    </a>
                </div>
                <div class="clear"></div>
            </div>
            <div class="ft20 ml20 mt20">选择颜色</div>
            <div class="xzbb ml20 mt10">
                <div class="banben">
                    <a>
                        <span class="yuandian"></span>
                        <span class="yanse">${goods.gcolor}</span>
                    </a>
                </div>
            </div>
            <div class="xqxq mt20 ml20">
                <div class="top1 mt10">
                    <div class="left1 fl">${goods.gname} ${versions.gversion} ${goods.gcolor}</div>
                    <div class="right1 fr">${versions.gprice}元</div>
                    <div class="clear"></div>
                </div>
                <div class="bot mt20 ft20 ftbc">总计：${versions.gprice}元</div>
            </div>
            <div class="xiadan ml20 mt20">
                <input class="jrgwc"  type="button" name="jrgwc"  onclick="goumai();" value="立即选购" />
                <input class="jrgwc" type="button" name="jrgwc"  onclick="addCart();" value="加入购物车" />
                <script type="text/javascript">
                    function addCart() {
                        var gid =${goods.id};
                        var vid=${versions.id};
                        location .href="http://localhost:8085/cart/add?gid="+gid+"&vid="+vid+"&gnumber=1";
                    }
                    function goumai() {
                        var gid =${goods.id};
                        var vid=${versions.id};
                        location .href="http://localhost:8086/order/toGouMai?gid="+gid+"&vid="+vid+"&gnumber=1";
                    }
                </script>
            </div>
        </div>
        <div class="clear"></div>
    </div>
</form>
<!-- footer -->
<footer class="mt20 center">

    <div class="mt20">小米商城|MIUI|米聊|多看书城|小米路由器|视频电话|小米天猫店|小米淘宝直营店|小米网盟|小米移动|隐私政策|Select Region</div>
    <div>©mi.com 京ICP证110507号 京ICP备10046444号 京公网安备11010802020134号 京网文[2014]0059-0009号</div>
    <div>违法和不良信息举报电话：185-0130-1238，本网站所列数据，除特殊说明，所有数据均出自我司实验室测试</div>

</footer>

</body>
</html>