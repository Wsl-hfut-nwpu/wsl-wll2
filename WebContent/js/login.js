window.onload = main;
function main(){
    var tabParent = document.getElementById('tab');
    var tabs = tabParent.getElementsByTagName('li');
    //登录所用变量
    var logs = document.getElementsByClassName('log');
    var userId = document.getElementById('userId');
    var password = document.getElementById('password');
    var login = document.getElementById('log_btn')
    var logResult = document.getElementById('logResult');

    //注册所用变量
    var reg_userId = document.getElementById('reg_userId');
    var reg_password1 = document.getElementById('reg_password1');
    var reg_password2 = document.getElementById('reg_password2');
    var reg_btn = document.getElementById('reg_btn');


    function inti(){
        change();
        loginOp();
        registerOp();
    }
    function change(){
        for(var i=0; i<tabs.length; i++){
            tabs[i].index = i;
            tabs[i].addEventListener('click', function(){
                for(var j=0; j<logs.length; j++){
                    logs[j].className = "log undisplay";
                }
                logs[this.index].className = "log";
            })
        }       //tab选项卡
    }
    function loginOp(){     //登录
        login.onclick = function(){

           
            ajax("post","deng","option=login&"+"userId="+userId.value+"&password="+password.value,result);
        
            return false;
        }
        function result(str){
            data = JSON.parse(str);
            if(data["status"] === "success"){
                window.location.href = "index.html";
            }
            else if (data["status"] === "fail") {
                logResult.className = "logResult red";
            };
        }
    }
    function registerOp(){      //注册
        var flag = true;
        reg_userId.addEventListener('blur', checkId);       //检测注册账号是否可用
        function checkId(){
            ajax('post', 'deng', 'option=checkid&userId='+reg_userId.value, idResult);
            return flag;
        }
        function idResult(str){
            var idTip = document.getElementById('idTip');
            dat = JSON.parse(str);
            if(dat['status'] === 'success'){
                idTip.innerHTML = '该账号可以使用';
                idTip.className = 'logResult green';
                flag = true;
            }else{
                idTip.innerHTML = '该账号已存在';
                idTip.className = 'logResult red';
                flag = false;
            }
        }
        //点注册后操作
        reg_password2.onblur  = function(){
            var passResult = document.getElementById('passResult');
            if(reg_password1.value != reg_password2.value){
                passResult.className = 'logResult red';
                flag = false;
            }
            else{
                flag = true;
            }
        }
        reg_btn.onclick = function (){
            if(flag){
                ajax('post', 'deng', 'option=register&userId='+reg_userId.value+'&password='+reg_password1.value, regResult);
            }
            return false;
        }
        function regResult (str){
            var regiResult = document.getElementById('regiResult');
            dat = JSON.parse(str);
            if(dat['status'] === 'success'){
                regiResult.innerHTML = '注册成功';
                regiResult.className = 'logResult green';
            }
            else {
                regiResult.innerHTML = '亲，注册失败';
                regiResult.className = 'logResult red';
            }
        }
    }

    inti();
    function test(a){
            console.log(a);
        }
}

