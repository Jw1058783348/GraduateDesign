layui.use(['jquery','table','laytpl','layer'], function(){
        var form = layui.form,
            table = layui.table,
            layer = layui.layer,
            laytpl = layui.laytpl,
            $ = layui.jquery;

        //商品列表
        var tableIns =table.render({
            elem: '#goodstable'
            ,url:'/goods/list'
            ,page: true
            // ,cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
            ,size : 'lg'
            ,cols: [[
                {type: "checkbox",  width: 50,style:"height:120px",}
                ,{field:'id', width:100, title: 'id', sort: true, style:"height:120px"}
                ,{field:'gname', width:100,title: '商品名称',style:"height:120px"}
                ,{field:'gimage',width:120, title: '图片',width:120,templet:"#imgtmp",align:"center",style:"height:120px"}
                ,{field:'ginfo', width:290, title:'商品信息',align:"center",style:"height:120px"}
                ,{field:'gcolor', width:100, title: '商品颜色',style:"height:120px"}
                ,{field:'gversion', width:290, title: '商品版本信息',style:"height:120px" ,templet:function (row) {
                        if(row.gversion!=0){
                            var vss;
                            $.ajax({
                                url:"/goods/queryVersions",
                                data:{"gid":row.id},
                                async: false,//设置成同步才能实现数据外传
                                success:function (data) {
                                    var vss2="";
                                    var versions=eval(data);
                                    for (var i = 0; i <versions.length ; i++) {
                                        vss2+=versions[i].gversion+" "+versions[i].gprice.toString()+"元"+"<br/>";
                                    }
                                    vss=vss2;
                                }
                            });
                            return vss;
                        }else{
                            return "没有版本信息";
                        }
                    }}
                ,{field:'tid',width:100, title: '分类',sort:true,style:"height:120px",templet: function(row) {
                        if (row.tid == 1) {
                            return "小米手机"
                        } else if (row.tid == 2) {
                            return "华为手机"
                        }else if (row.tid ==3){
                            return "IPhone"
                        }else if (row.tid ==4){
                            return "一加手机"
                        }else if (row.tid ==5){
                            return "努比亚手机"
                        }else if (row.tid ==6){
                            return "三星手机"
                        }else if (row.tid ==7){
                            return "OPPO手机"
                        }else if (row.tid ==8){
                            return "VIVO手机"
                        }else if (row.tid ==9){
                            return "魅族手机"
                        }else if (row.tid ==10){
                            return "联想手机"
                        }else {
                            return "未分类"
                        }
                    }
                }
                ,{title: '操作', width:120, templet:'#goodsOperate',style:"height:120px"}
            ]]
        });

        //列表操作
        table.on('tool(test)', function(obj){
            var layEvent = obj.event,
                data = obj.data;
            //console.log(data);
            if(layEvent === 'edit'){ //编辑
                addGoods(data);
            } else if(layEvent === 'del'){ //删除
                layer.confirm('确定删除此商品？',{icon:3, title:'提示信息'},function(index){
                    obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
                    //删除商品
                    $.ajax({
                        type: "POST",
                        url: "/goods/delGoods/"+data.id,
                        success: function(msg){
                            layer.msg(msg);
                            tableIns.reload();
                            layer.close(index);
                        }
                    });
                });
            }
        });

        //触发点击添加商品
        $(".addGoods_btn").click(function(){
            addGoods();
        });

        //添加商品
        function addGoods(edit) {
            var imgPath=$("#imgPath").val();//获取session中的imgPath，因为下面自动生成的表格没有th:src使用不了thymeleaf获取session的值
            //console.log("imgPath"+imgPath);
            var index = layui.layer.open({
                title: "添加/修改商品",
                type: 2,
                content: "toPage/goodsadd",
                area: ['800px', '500px'],
                success: function (layero, index) {
                    var body = layui.layer.getChildFrame('body', index);
                    if (edit) {
                        body.find(".id").val(edit.id);
                        body.find(".gname").val(edit.gname);
                        body.find(".ginfo").val(edit.ginfo);
                        body.find(".gimage").val(edit.gimage);
                        body.find(".thumbBox").attr("src",imgPath+edit.gimage);
                        body.find(".gcolor").val(edit.gcolor);
                        body.find(".tid_2").val(edit.tid);
                        body.find(".gversion_2").val(edit.gversion);
                        //form.render('select');
                        form.render();
                    }
                    setTimeout(function () {
                        layui.layer.tips('点击此处返回商品列表', '.layui-layer-setwin .layui-layer-close', {
                            tips: 3
                        });
                    }, 500)

                },
                end: function () {
                    location.reload();
                }
            })

        }

        //批量删除
        $(".delAll_btn").click(function(){
            var checkStatus = table.checkStatus('goodstable'),
                data = checkStatus.data,
                goodsId = [];
            //console.log(data);
            if(data.length > 0) {
                for (var i in data) {
                    goodsId.push(data[i].id);
                }
                layer.confirm('确定删除选中的商品？', {icon: 3, title: '提示信息'}, function (index) {
                    console.log("goodsId="+goodsId);
                    $.post("/goods/batchDel",{"goodsId":goodsId.toString()},
                        function(msg){
                            tableIns.reload();
                            layer.close(index);
                        }
                    );
                })
            }else{
                layer.msg("请选择需要删除的商品");
            }
        })

    });