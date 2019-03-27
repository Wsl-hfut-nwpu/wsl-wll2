function ajax(method, url, data, success) {
	var xhr = null;
	try {
		xhr = new XMLHttpRequest();
	} catch (e) {
		xhr = new ActiveXObject('Microsoft.XMLHTTP');
	}

	if (method == 'get' && data) {
		url += '?' + data + '&time=' + new Date().getTime();
	}
	else{
		url += '?time=' + new Date().getTime();
	}

	xhr.open(method,url,true);
	if (method == 'get') {
		xhr.send();
	} else {
		xhr.setRequestHeader('content-type', 'application/x-www-form-urlencoded');
		xhr.send(data);
	}

	xhr.onreadystatechange = function() {

		if ( xhr.readyState == 4 ) {
			if ( xhr.status == 200 ) {
				success && success(xhr.responseText);
			} else {
				alert('出错了,Err：' + xhr.status);
			}
		}
	}
}

function search(){
    var ser = document.getElementById('in_search');
    var btn = document.getElementById('seach_btn');
    btn.onclick = function (){
        window.location.href = "search.html?seach=" + ser.value;
        return false;
    }
}

function stateOption(){
    var state = document.getElementById('state');
    ajax('post', 'denghou', 'option=getState', stateResult);
    function stateResult(str){
    	console.log(str);
        data = JSON.parse(str);
        console.log(data);
        if(data['status'] == 'success'){
            state.innerHTML = data['userId'] + "[已登录]";
        }
    }
}

// function searchResult(){
//     var res="";
//     var content = document.getElementById('str');

//     inti();

//     function inti(){
//         show();
//     };

//     function show(){    //获取网址中的搜索数据
//         var url = window.location.href;
//         var data = url.split('?')[1].split('=')[1]; //搜索的数据
//         ajax('post', 'deng', 'bookname='+name, result);
//         content.innerHTML = res;
//     }

//     function result(str){
//         var html = "";
//         data = JSON.parse(str);
//         for(var item in data){
//             data[item] = JSON.parse(data[item]);
//             html += "<div class=\"se_li\"><img src=\""+ data[item]['photo'] +"\"><div class=\"li_page\">
//                         <p class=\"font15px\"><a href=\"\"> "+ data[item]['bookname'] +" </a></p>
//                         <p class=\"font18px red bold\"> " +data[item]['price'] +" </p>
//                         <p class=\"font15px\"><span> " +data[item]['author'] +" </span> / <span> "+ data[item]['publishTime'] +" </span> / <span> " +data[item]['publishHouse'] +" </span></p>
//                         <article class=\"li_pro\"> " +data[item]['introduce'] +" </article>
//                         <a href=\"\" id=\"se_btn\" class=\"font15px\">加入购物车</a>
//                     </div></div>"
//         }
//         res = html;
//     }
// }


