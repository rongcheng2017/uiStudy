var xiangxuejs = {};
xiangxuejs.os = {};
xiangxuejs.os.isIOS = /iOS|iPhone|iPad|iPod/i.test(navigator.userAgent);
xiangxuejs.os.isAndroid = !xiangxuejs.os.isIOS;
xiangxuejs.callbacks={}

xiangxuejs.callback =function(param){

}

xiangxuejs.takeNativeAction = function(commandname, parameters){
    console.log("f_web_view takenativeaction")
    var request = {};
    request.name = commandname;
    request.param = parameters;
    if(window.xiangxuejs.os.isAndroid){
        console.log("android take native action" + JSON.stringify(request));
        window.f_web_view.takeNativeAction(JSON.stringify(request))
    } else {
        window.webkit.messageHandlers.xiangxuewebview.postMessage(JSON.stringify(request))
    }
}

xiangxuejs.takeNativeActionWithCallback = function(commandname, parameters,callback){
 var callback_name ="native_to_js_callback"+(new Date()).getTime()+"_"+Math.floor(Math.random()*10000);
 xiangxuejs.callbacks[callback_name]={callback:callback}

 var request = {};
    request.name = commandname;
    request.param = parameters;
    request.param.callback_name =callback_name
    if(window.xiangxuejs.os.isAndroid){
        console.log("android take native action" + JSON.stringify(request));
        window.f_web_view.takeNativeAction(JSON.stringify(request))
    } else {
        window.webkit.messageHandlers.xiangxuewebview.postMessage(JSON.stringify(request))
    }



}
window.f_web_view = f_web_view;
