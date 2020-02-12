function hideModal() {
    $("#modal-overlay").removeClass("in");
    $(".modal-backdrop").remove();
    $('body').removeClass('modal-open');
    $('body').css('padding-right', '');
    $("#modal-overlay").hide();
}

function getSecure(url, data, callback, errorCallback){
    if(!hasCredentials()){
        throw "No credentials";
    }
    var xhr = new XMLHttpRequest();

    if(data)
        url += "?" + $.param(data)

    xhr.open("GET", url, true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    var credentials = getCredentials();
    if(credentials.token){
        xhr.setRequestHeader("Authorization" , credentials.token)
    } else {
        throw "No vaild token"
    }
    // send the collected data as JSON


    xhr.onloadend = function () {
        // done
        try{
            if(xhr.status !== 200 && xhr.status !== 500){
                errorCallback()
                invalidateSession()
            } else {
                var parsed = JSON.parse(xhr.response)
                callback(parsed)
            }
        }catch(err){
            console.info("Err parsing: ", err)
            callback(xhr.response)
        }


    };
    xhr.onerror = function(data){
        errorCallback(xhr.response)
    }

    xhr.send(data);
}

function postSecure(url, data, callback, errorCallback){
    if(!hasCredentials()){
        throw "No credentials";
    }
    var xhr = new XMLHttpRequest();
    xhr.open("POST", url, true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    var credentials = getCredentials();
    if(credentials.token){
        xhr.setRequestHeader("Authorization" , credentials.token)
    } else {
        throw "No vaild token"
    }
    // send the collected data as JSON


    xhr.onloadend = function () {
        // done
        try{
            if(xhr.status !== 200 && xhr.status !== 500){
                errorCallback()
                isValidSession()
            } else {
                var parsed = JSON.parse(xhr.response)
                callback(parsed)
            }

        }catch(err){
            console.info("Err parsing: ", err)
            callback(xhr.response)
        }
    };
    xhr.onerror = function(data){
        errorCallback(xhr.response)
    }

    xhr.send(JSON.stringify(data));
}

function postRequest(url, data, callback, errorCallback) {
    var xhr = new XMLHttpRequest();
    xhr.open("POST", url, true);
    xhr.setRequestHeader('Content-Type', 'application/json');

    // send the collected data as JSON


    xhr.onloadend = function () {
        // done
        callback(JSON.parse(xhr.response))
    };
    xhr.onerror = function(data){
        errorCallback(xhr.response)
    }

    xhr.send(data);
}
function invalidateSession(){
    localStorage.removeItem("auth")
}

function saveCredentials(email, token,remember){
    var authentication = {
        email: email,
        token: "Bearer " + token,
        remember: remember
    }

    localStorage.setItem("auth" , JSON.stringify(authentication))
}

function getCredentials(){
    return JSON.parse(localStorage.getItem("auth"))
}

function hasCredentials(){
   return localStorage.getItem("auth") != null
}

if(!hasCredentials() && window.location.href.indexOf("/login") === 0){
    window.location.href = "/admin/login";
}

function isValidSession(){
    return new Promise(((resolve, reject) => {
        if(!hasCredentials()){
            resolve(false)
        }
        getSecure("/api/v1/isvalidauth",null,function (data) {
            resolve(data==="OK")

        })
    }))


}

function getUserProfile(){
    return new Promise(((resolve, reject) => {
        getSecure("/api/v1/userprofile",  null, function(data){
            resolve(data)
        }, function(){
            reject("unable to get profile")
        })
    }))
}

function getMyNodes(){
    return new Promise(((resolve, reject) => {
        getSecure("/api/v1/mynodes",null, function(data){
            resolve(data)
        }, function(){
            console.log("Error updating nodes!")
        })
    }))
}

function getSensors(nodeid){
    return new Promise(((resolve, reject) => {
        getSecure("/api/v1/nodesensors",{nodeId:nodeid}, function(data){
            resolve(data)
        }, function(){
            reject()
            console.log("Error updating nodes!")
        })
    }))
}

function getGraph(nodeid, timespan, startDate, endDate){
    data = {nodeId: nodeid, timespan: timespan, startDate: startDate, endDate: endDate}
    return new Promise(((resolve, reject) => {
        getSecure("/api/v1/nodegraphs",data, function(data){
            resolve(data)
        }, function (){
            reject()
        })

    }))
}

function postSwitchRelay(nodeid, relayindex, status){
    data = {nodeId: nodeid, relayIndex: relayindex, status: status}
    return new Promise(((resolve, reject) => {
        postSecure("/api/v1/switchrelay", data, function(data){
            resolve(data)
        }, function(){
            reject()
        })
    }))
}

function optional(val, optional) {
    if(val)
        return val
    return optional
}