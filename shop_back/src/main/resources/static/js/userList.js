layui.use(['form','layer','table','laytpl'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laytpl = layui.laytpl,
        table = layui.table;

    //用户列表
    var tableIns = table.render({
        elem: '#userList',
        url : '/user/list',
        cellMinWidth : 95,
        page : true,
        height : "full-125",
        limits : [10,15,20,25],
        limit : 10,
        id : "userListTable",
        cols : [[
            {type: "checkbox", fixed:"left", width:50},
            {field: 'id', title: 'id', minWidth:100, align:"center"},
            {field: 'username', title: '用户名', minWidth:100, align:"center"},
            {field: 'password', title: '密码', minWidth:100, align:'center'},
            {field: 'name', title: '姓名', align:'center'},
            {field: 'birthday', title: '生日', align:'center'},
            {field: 'phone', title: '手机号', align:'center'},
            {title: '操作', minWidth:175, templet:'#userListBar',fixed:"right",align:"center"}
        ]]
    });
    //添加用户
    function addUser(edit){
        var index = layui.layer.open({
            title : "添加/修改用户",
            type : 2,
            content : "toPage/userAdd",
            area: ['800px', '500px'],
            success : function(layero, index){
                var body = layui.layer.getChildFrame('body', index);
                if(edit){
                    body.find(".id").val(edit.id);
                    body.find(".username").val(edit.username);
                    body.find(".password").val(edit.password);
                    body.find(".name").val(edit.name);
                    body.find(".birthday").val(edit.birthday);
                    body.find(".phone").val(edit.phone);
                    form.render();
                }
                setTimeout(function(){
                    layui.layer.tips('点击此处返回用户列表', '.layui-layer-setwin .layui-layer-close', {
                        tips: 3
                    });
                },500)
            }, end: function () {
                location.reload();
            }
        })
        /*layui.layer.full(index);
        window.sessionStorage.setItem("index",index);
        //改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）
        $(window).on("resize",function(){
            layui.layer.full(window.sessionStorage.getItem("index"));
        })*/
    }
    $(".addUser_btn").click(function(){
        addUser();
    })

    //批量删除
    $(".delAll_btn").click(function(){
        var checkStatus = table.checkStatus('userListTable'),
            data = checkStatus.data,
            usersId = [];
        if(data.length > 0) {
            for (var i in data) {
                usersId.push(data[i].id);
            }
            layer.confirm('确定删除选中的用户？', {icon: 3, title: '提示信息'}, function (index) {
                console.log("usersId="+usersId);
                $.post("/user/batchDel",{"usersId":usersId.toString()},
                    function(msg){
                        tableIns.reload();
                        layer.close(index);
                    }
                );
            })
        }else{
            layer.msg("请选择需要删除的用户");
        }
    })

    //列表操作
    table.on('tool(userList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;
        if(layEvent === 'edit'){ //编辑
            addUser(data);
        }else if(layEvent === 'del'){ //删除
            layer.confirm('确定删除此用户？',{icon:3, title:'提示信息'},function(index){
                obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
                //删除商品
                $.ajax({
                    type: "POST",
                    url: "/user/delUser/"+data.id,
                    success: function(msg){
                        layer.msg(msg);
                        tableIns.reload();
                        layer.close(index);
                    }
                });
            });
        }
    });
})
