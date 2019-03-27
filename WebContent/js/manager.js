window.onload = main;

function main(){

    function inti(){
        myMoney();
        orderData();
    }

   function myMoney () {
       var money = document.getElementById('money');
       ajax('post', 'denghou', 'option=getMoney', result);

       function result (str) {
           var data = JSON.parse(str);
           money.innerHTML = data['money'];
       }
   }

    function orderData(){     //获取需要发货的订单信息
        var wrapper = document.getElementById('man_wrapper');
        ajax('post', 'denghou', 'option=getorderdata', result);

        function result(str){
            var data = JSON.parse(str);
            var html = "";
            for(var item in data){
                html += "<ul class=\"order_pro\" index=\"0\"><li>" +data[item]['id']+ "</li><li><img src=\"" +data[item]['photo']+ "\" alt=\"\"></li><li class=\"order_inf\">" +data[item]['bookname']+ "</li><li><div class=\"order_num\">" +data[item]['much']+ "</div></li><li class=\"order_total\">" +data[item]['total']+ "</li></ul><ul class=\"msg order_msg\"><li><p>收货人</p><p>" +data[item]['person']+ "</p></li><li><p>电话</p><p>" +data[item]['phone']+ "</p></li><li class=\"msg_address\"><p>地址</p><p>" +data[item]['address']+ "</p></li></ul>"
            }
            wrapper.innerHTML = html;
            option();
        }
    }

    //订单操作
    function option(){
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

    inti();
}