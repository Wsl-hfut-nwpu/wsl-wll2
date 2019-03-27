window.onload=main;
function main(){
    function inti(){
        home.inti();
        search();
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

inti();
}