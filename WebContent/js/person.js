window.onload = main;
function main(){
	var tabs = document.getElementsByClassName('per_tab');
    var lists = document.getElementsByClassName('per_li');
    
    function inti(){
        tab();
//        myMoney();
        addressMsg();
        order();
        search();
        stateOption();
    }

    function tab(){     //选项卡
        var tabs = document.getElementsByClassName('per_tab');
        var lists = document.getElementsByClassName('per_li');

        for(var i=0; i<tabs.length; i++){
            tabs[i].index = i;
            tabs[i].onclick = function () {
                for(var j=0; j<lists.length; j++){
                    lists[j].style.display = 'none';
                }
                lists[this.index].style.display = 'block';
                if(this.inde === 1){
                	order();
                }
                if(this.inde === 2){
                    shopCar.addMsg('msg');
                }
            }
        }
    }

//    function myMoney () {
//        var money = document.getElementById('money');
//        ajax('post', 'denghou', 'option=getMoney', result);
//
//        function result (str) {
//            var data = JSON.parse(str);
//            money.innerHTML = data['money'];
//        }
//    }

    var shopCar = (function (){     //购物车操作
            var prosParent = document.getElementById('shopcar_proWrapper');
            var car_num = document.getElementsByClassName('car_num');
            var car_total = document.getElementsByClassName('car_total');
            var car_price = document.getElementsByClassName('car_price');
            var btn = document.getElementById('shopcar_btn');
            var carMsgWrapper = document.getElementById('shopcar_msg_wrapper');

            getCarData();
            search();


            function choseAll(){    //购物车全选
            	var proCheckboxs = document.getElementsByClassName('pro_checkbox')
                var choseAll = document.getElementById('car_choseAll');
                choseAll.onchange = function(){
                    if(choseAll.checked === true){
                        for(var i=0; i<proCheckboxs.length; i++){
                            proCheckboxs[i].checked = true;
                        }
                    }else{
                        for(var j=0; j<proCheckboxs.length; j++){
                            proCheckboxs[j].checked = false;
                        }
                    }
                }
            }

            function getCarData(){      //购物车数据获取
                var proWrapper = document.getElementById('shopcar_proWrapper');
                ajax('post', 'denghou', 'option=getcardata', result);
                function result(str){
                    var html = "";
                    data = JSON.parse(str);
                    if(!data.hasOwnProperty('status')){
                        for(var item in data){
                        html += "<ul class=\"shopcar_pro\"><span class=\"li_index undisplay\">" +data[item]['id']+ "</span><li><input type=\"checkbox\" class=\"pro_checkbox\"><img src=\" " +data[item]['photo']+ " \" alt=\"\"></li><li class=\"car_inf\">" +data[item]['bookname']+ "</li><li>¥<span class=\"car_price\">" +data[item]['price']+ "</span></li><li><div class=\"car_num_wrapper\"><div class=\"car_decrease\">-</div><input type=\"text\" value=\" "+data[item]['much']+" \" class=\"car_num\"><div class=\"car_increase\">+</div></div></li><li class=\"car_total\">¥" +data[item]['total']+ "</li><li><a class=\"car_delete\">删除</a></li></ul>"
                        proWrapper.innerHTML = html;
                        choseAll();
                        }
                    }else{
                        proWrapper.innerHTML = "购物车为空";
                    }
                    prosOption();

                }
            }

            function prosOption(){      //购物车操作
                var shopcarPros = document.getElementsByClassName('shopcar_pro');

                for(var i=0; i<shopcarPros.length; i++){
                    shopcarPros[i].index = i;

                    shopcarPros[i].onmouseover = function(){
                        var This = this;
                        var j = this.index;
                        this.getElementsByClassName('car_decrease')[0].onclick = function (){   //数量-1
                            if(This.getElementsByClassName('car_num')[0].value > 0)
                            This.getElementsByClassName('car_num')[0].value--;
                            var totalPrice = (parseFloat(This.getElementsByClassName('car_price')[0].innerHTML, 10) * This.getElementsByClassName('car_num')[0].value).toFixed(2);
                            This.getElementsByClassName('car_total')[0].innerHTML = '￥' + totalPrice;//总价
                        }
                         this.getElementsByClassName('car_increase')[0].onclick = function (){  //数量+1
                            This.getElementsByClassName('car_num')[0].value++;
                            var totalPrice = (parseFloat(This.getElementsByClassName('car_price')[0].innerHTML, 10) * This.getElementsByClassName('car_num')[0].value).toFixed(2);
                            This.getElementsByClassName('car_total')[0].innerHTML = '￥' + totalPrice;//总价
                        }
                        this.getElementsByClassName('car_delete')[0].onclick = function (){ //删除
                            var data = This.getElementsByClassName('li_index')[0].innerHTML;
                            ajax('post', 'denghou', 'option=cardelete&id='+data, result);
                            prosParent.removeChild(This);
                            return false;
                        }
                    }
                }

                btn.onclick = function (){    //提交
                    addMsg("shopcar");
                    return false;
                }
            }

            function addMsg(type){

                var carMsgWrapper = document.getElementById('shopcar_msg');         //地址弹窗
                var carAddMsgTab = document.getElementById('shopcar_addmsg_tab');  //添加地址窗表头
                var carAddMsg = document.getElementById('shopcar_addmsg_wrapper'); //添加地址窗内容

                var carMsgTab = document.getElementById('shopcar_msg_tab');
                var carMsg = document.getElementById('shopcar_msg_wrapper');

                var msgWrapper = document.getElementById('msg_wrapper');    //收货信息窗

                ajax('post', 'denghou', 'option=getmsg', result);

                function result (str){
                    console.log("地址收到");
                    var data = JSON.parse(str);

                    if(type === "shopcar"){                          //购物车结算时地址显示

                        var addMsgbtn = document.getElementById('shopcar_addmsg_btn');

                        if(data['status'] === 'success'){
                            carAddMsgTab.style.display = 'none';
                            carAddMsg.style.display = 'none';
                            carMsgTab.style.display = 'block';
                            carMsg.style.display = 'block';
                            carMsgWrapper.style.display = 'block';

                            carMsg.innerHTML = msgShow(data, "shopcar");
                            msgOption();    //地址选择操作
                            var msgBtn = document.getElementById('shopcar_msg_btn');
                            msgBtn.onclick = function(){
                            	console.log('点击成功');
                                hasData();
                                return false;
                            }
                        }
                        else if(data['status'] === 'fail'){
                            carMsgTab.style.display = 'none';
                            carMsg.style.display = 'none';
                            carAddMsgTab.style.display = 'display';
                            carAddMsg.style.display = 'display';
                            carMsgWrapper.style.display = 'block';

                            addMsgbtn.onclick = function(){
                            	console.log('点击成功');
                                noData();
                                return false;
                            }
                        }
                    }

                    if(type === "msg"){                        //收货地址信息窗口显示
                        var person = document.getElementById('msg_person');
                        var phone = document.getElementById('msg_phone');
                        var adress = document.getElementById('msg_address');
                        var btn = document.getElementById('msg_btn');
                        var str = "";

                        if (data['status'] === 'success') {
                            msgWrapper.innerHTML = msgShow(data, ",msg");
                        }
                        else{
                            msgWrapper.innerHTML = "没有收货信息"
                            btn.onclick = function (){
                                str = "[" + person.value + "," + phone.value + "," + address.value + "]";
                                ajax('post', 'denghou', 'option=msghandin&data='+str, msgresult);
                                function msgresult(str){
                                    var msg = JSON.parse(str);
                                    if(msg['status'] === "success"){
                                        alert("提交成功");
                                    }else{
                                        alert("提交失败");
                                    }
                                }
                            }
                        }
                    }
                }
                //收货信息展示
                function msgShow(data, type){
                        var html = '';

                        for(var item in data){
                            if(item != "status")
                            {
                                html += "<ul class=\"msg shopcar_msg_list\" index=\"" +data[item]['id']+ "\"><li><p>收货人</p><p>" +data[item]['person']+ "</p></li><li><p>电话</p><p>" +data[item]['phone']+ "</p></li><li class=\"msg_address\"><p>地址</p><p>" +data[item]['address']+ "</p></li><li><p>操作</p><p><a href=\"#\">删除</a></p></li></ul>"
                            }
                        }
                        if(type == "shopcar")
                        {
                        	html += "<a href=\"#\" class=\"msg_btn\" id=\"shopcar_msg_btn\">确 定</a>";
                        }
                        return html;
                    }
            }

             //购物车的地址操作
            function msgOption(){
                var carMsg = carMsgWrapper.getElementsByClassName('shopcar_msg_list');   //购物车地址获取

                for(var i=0; i<carMsg.length; i++){
                    carMsg[i].onclick = function(){
                        for(var j=0; j<carMsg.length; j++){
                            carMsg[j].className = "msg shopcar_msg_list";
                        }
                        this.className = "msg shopcar_msg_list active";
                    }
                }
            }

            //购物车提交数据返回
            function hasData(){
            //传送代码
            	console.log('进入hasdata');
                var proCheckboxs = document.getElementsByClassName('pro_checkbox');
                var carMsg = carMsgWrapper.getElementsByClassName('shopcar_msg_list');   //购物车地址获取
                var str = "{";
                var addressId = "";   //地址的ID

                for(var k=0; k<carMsg.length; k++){
                    if(carMsg[k].className === "msg shopcar_msg_list active")
                    {
                        addressId = carMsg[k].getAttribute('index');
                    }
                }

                str += "["+ addressId + "]";

                for(var j=0; j<proCheckboxs.length; j++){
                    if(proCheckboxs[j].checked === true){
                        str += "[" +proCheckboxs[j].parentNode.parentNode.getElementsByClassName('li_index')[0].innerHTML+ "," +proCheckboxs[j].parentNode.parentNode.getElementsByClassName('car_num')[0].value+ "]";
                    }
                }

                str+= "}";
                postAllData(str);
            }

            function noData(){
            	console.log('进入nodata')
                var proCheckboxs = document.getElementsByClassName('pro_checkbox');
                var person = document.getElementById('shopcar_person'); //收货人
                var phone = document.getElementById('shopcar_phone');
                var address = document.getElementById('shopcar_address');
                var str = "{[999]";

                for(var j=0; j<proCheckboxs.length; j++){
                    if(proCheckboxs[j].checked === true){
                        str += "[" +proCheckboxs[j].parentNode.parentNode.getElementsByClassName('li_index')[0].innerHTML+ "," +proCheckboxs[j].parentNode.parentNode.getElementsByClassName('car_num')[0].value+ "]";
                    }
                }

                str+= "["+person.value+","+phone.value+","+address.value+"]}";
                postAllData(str);
            }

            function postAllData(str){
            	var shopcar_msg = document.getElementById('shopcar_msg');
                var proWrapper = document.getElementById('shopcar_proWrapper');

            	console.log('进入postAllData和ajax');

                ajax('post', 'denghou', 'option=alldatareturn&data='+str, result);     //提交所有数据并询问是否成功

                function result(str){
//                    myMoney ();
                    prompt("请输入密码");
                    alert('订单提交成功');
                    shopcar_msg.style.display = "none";
                    proWrapper.innerHTML = "购物车为空";

                }
            }

            return {
                addMsg : addMsg
            }
        })();

//收货信息
    function addressMsg(){
        var btn = document.getElementById('msg_btn');
        btn.onclick = function(){
            var person = document.getElementById('msg_person'); //收货人
            var phone = document.getElementById('msg_phone');
            var address = document.getElementById('msg_address');
            var str = "{[999]";
            str+= "["+person.value+","+phone.value+","+address.value+"]}";

            ajax('post', 'denghou', 'option=addAddress&data='+str, result);
            function result(str){
                alert("地址提交成功");
                shopCar.addMsg('msg');
                lists[2].click();
            }
        }
        shopCar.addMsg('msg');
    }

//我的订单
    function order(){
        var wrapper = document.getElementById('order_wrapper');

        getData();

        function option(){  //订单操作
            var order_pro = document.getElementsByClassName('order_pro_wrapper');

            for(var i=0; i<order_pro.length; i++){  //删除
                order_pro[i].onmouseover = function (){
                    var This = this;
                    this.getElementsByClassName('order_delete')[0].onclick = function(){
                        wrapper.removeChild(This);
                        return false;
                    }
                }
            }
        }

        function getData(){     //数据获取
            var tabs = document.getElementsByClassName('per_tab');
            var orderTab = tabs[1];

            ajax('post', 'denghou', 'option=getorderdata', result);

            function result(str){
            	console.log(str);
                var data = JSON.parse(str);
                var html = "";
                for(var item in data){
                    html += "<div class=\"order_pro_wrapper\"><ul class=\"order_pro\" index=\"0\"><li>" +data[item]['id']+ "</li><li><img src=\"" +data[item]['photo']+ "\" alt=\"\"></li><li class=\"order_inf\">" +data[item]['bookname']+ "</li><li><div class=\"order_num\">" +data[item]['much']+ "</div></li><li class=\"order_total\">" +data[item]['total']+ "</li><li><a href=\"\" >退换货</a>&nbsp;&nbsp;<a href=\"\" class=\"order_delete\">删除</a></li></ul><ul class=\"msg order_msg\"><li><p>收货人</p><p>" +data[item]['person']+ "</p></li><li><p>电话</p><p>" +data[item]['phone']+ "</p></li><li class=\"msg_address\"><p>地址</p><p>" +data[item]['address']+ "</p></li></ul></div>"
                }
                wrapper.innerHTML = html;
                option();
            }
        }
    }

    inti();
}