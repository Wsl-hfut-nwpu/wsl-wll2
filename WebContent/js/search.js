window.onload=main;
function main(){
    function inti(){
        home.inti();
        search();
        searchResult();
        stateOption();
    }

    var home = (function(){
        var content = document.getElementById('content');   //主容器
        var list = document.getElementById('list');         //图片集合
        var buttons = document.getElementById('buttons').getElementsByTagName('span');  //圆形按钮
        var prev = document.getElementById('prev');
        var next = document.getElementById('next');
        var index = 1;      //图片序号
        var num = 5;
        var animated = false;
        var interval = 2000;
        var timer;

        content.onmouseover = stop;
        content.onmouseout = play;

        function inti(){
            play();
            toBig();
        }

        function animate (offset) {
            if (offset == 0) {
                return;
            }
            animated = true;
            var time = 200;
            var inteval = 10;
            var speed = offset/(time/inteval);
            var left = parseInt(list.style.left) + offset;

            var go = function (){
                if ( (speed > 0 && parseInt(list.style.left) < left) || (speed < 0 && parseInt(list.style.left) > left)) {
                    list.style.left = parseInt(list.style.left) + speed + 'px';
                    setTimeout(go, inteval);
                }
                else {
                    list.style.left = left + 'px';
                    if(left>0){
                        list.style.left = -790 * num + 'px';
                    }
                    if(left<(-790 * num)) {
                        list.style.left = '-790px';
                    }
                    animated = false;
                }
            }
            go();
        }

        function showButton() {
            for (var i = 0; i < buttons.length ; i++) {
                if( buttons[i].className == 'on'){
                    buttons[i].className = '';
                    break;
                }
            }
            buttons[index - 1].className = 'on';
        }

        function play() {
            timer = setTimeout(function () {
                next.onclick();
                play();
            }, interval);
        }
        function stop() {
            clearTimeout(timer);
        }

        next.onclick = function () {
            if (animated) {
                return;
            }
            if (index == 5) {
                index = 1;
            }
            else {
                index += 1;
            }
            animate(-790);
            showButton();
        }
        prev.onclick = function () {
            if (animated) {
                return;
            }
            if (index == 1) {
                index = 5;
            }
            else {
                index -= 1;
            }
            animate(790);
            showButton();
        }

        for (var i = 0; i < buttons.length; i++) {
            buttons[i].onclick = function () {
                if (animated) {
                    return;
                }
                if(this.className == 'on') {
                    return;
                }
                var myIndex = parseInt(this.getAttribute('index'));
                var offset = -790 * (myIndex - index);

                animate(offset);
                index = myIndex;
                showButton();
            }
        }

        function toBig(){
            var accordion=document.getElementsByClassName('accordion')[0];
            var list=accordion.getElementsByTagName('a');
            for(var i=0;i<list.length;i++){
                list[i].onmouseover=function(){
                    for(var j=0;j<list.length;j++){
                        list[j].id="";
                    }
                    this.id="big";
                }
                list[i].onmouseleave=function(){
                    for(var j=1;j<list.length;j++){
                        list[j].id="";
                    }
                    list[0].id="big";
                }
            }
        }
        return {
            inti: inti
        }
    })();

    function searchResult(){
        var content = document.getElementById('se_main');

        inti();

        function inti(){
            show();
        };

        function show(){    //获取网址中的搜索数据
            var url = window.location.href;
            if(url.split('?')[1])
            {
                var data = url.split('?')[1].split('=')[1]; //搜索的数据
            ajax('post', 'denghou', 'option=search&bookname='+data, result);
            }
        }

        function result(str){
            var html = "";
    
            var data = JSON.parse(str);
        
            for(var item in data){
                html += "<div class=\"se_li\" ><img src=\""+ data[item]['photo'] +"\"><div class=\"li_page\"><div class=\"li_id undisplay\">" +data[item]['id']+ "</div><p class=\"font15px\"><a href=\"\"> "+ data[item]['bookname'] +" </a></p><p class=\"font18px red bold\"> ￥" +data[item]['price'] +" </p> <p class=\"font15px\"><span> " +data[item]['author'] +" </span> / <span> "+ data[item]['publishTime'] +" </span> / <span> " +data[item]['publishHome'] +" </span></p><article class=\"li_pro\"> " +data[item]['introduce'] +" </article>  <a href=\"person.html\" class=\"se_btn font15px\">加入购物车</a></div></div>"
            }
            content.innerHTML = html;
            addPro();
        }
    }

    function addPro(){
        var addBtn = document.getElementsByClassName('se_btn');
        for(var i=0; i<addBtn.length; i++){
            addBtn[i].onclick = function(){
 
                var data = this.parentNode.getElementsByClassName('li_id')[0].innerHTML;
                ajax('post', 'denghou', 'option=addcarshop&id='+data, result);
                alert("购物车添加成功");
                return false;
            };
        };

        function result(){
        	console.log(1);
        }
    }
inti();
}