function buildPanel(){
    $(".modal").modal('show')
    $("#backbutton").hide()
    isValidSession().then((result) => {
        if(!result){
            window.location.href = "/admin/login"
        } else{
            getUserProfile().then((result) =>{

                $("#useremail").html(result.email)
                $("#profilepicture").html(
                    "<img src=\""+result.userPicture+"\" />"
                )

                var numNotifications = result.notifications.length

                if(numNotifications > 0){
                    $("#bellcounter").html(numNotifications)
                    $("#notificationscounter").html(numNotifications + " Notifications")
                } else {
                    $("#notificationscounter").html("No new notifications")
                }

                window.nodes = result.myNodes;
                $(result.myNodes).each(function(){
                    console.log(this)
                    var iconClass = "nav-icon fas fa-circle text-danger"
                    if(this.online)
                        iconClass = "nav-icon fas fa-circle text-info"

                    $("#networkmenu").append("<li class=\"nav-item\">\n" +
                        "                                <a href=\"#\" class=\"nav-link\" onclick=\"displayNodeOverview('"+this.nodeId+"')\">\n" +
                        "                                    <i class=\""+iconClass+"\"></i>\n" +
                        "                                    <p>"+this.macAddress+"</p>\n" +
                        "                                </a>\n" +
                        "                            </li>")
                    addNodeOverviewItem(this)
                })


                setInterval(updateNodes, 3000)
                hideModal()
            })
        }
    })
}

function addNodeOverviewItem(node){
    var arrowClass = ""
    var textClass = ""
    var textOnline = ""
    if(node.online){
        arrowClass = "fas fa-arrow-up"
        textClass = "text-success mr-1"
        textOnline = "Online"
    }else{
        arrowClass = "fas fa-arrow-down"
        textClass = "text-danger mr-1"
        textOnline = "Offline"
    }

    $("#nodeoverview tbody")
        .append("<tr id='row_"+node.nodeId+"'>\n" +
            "                                        <td>\n" +
            "                                            <img src=\"/static/img/nodeicon.png\" alt=\"Product 1\" class=\"img-circle img-size-32 mr-2\">\n" +
            node.macAddress +
            "                                        </td>\n" +
            "                                        <td>"+node.nodeIp+"</td>\n" +
            "                                        <td>\n" +
            "                                            <span id='status_"+node.nodeId+"' class=\""+textClass+"\">\n" +
            "                                                <i id='arrow_"+node.nodeId+"'  class=\""+arrowClass+"\"></i>\n" +
            "<span id='text_"+node.nodeId+"'>" + textOnline+ "</span>" +
            "                                            </span>\n" +
            "                                        </td>\n" +
            "                                        <td>\n" +
            "                                            <a href=\"#\" class=\"text-muted\" onclick=\"displayNodeOverview('"+node.nodeId+"')\">\n" +
            "                                                <i class=\"fas fa-search\"></i>\n" +
            "                                            </a>\n" +
            "                                        </td>\n" +
            "                                    </tr>")
}



function displayNodeOverview(selectedNodeId){
    $("#modal").modal("show")
    console.log("Node overview ", selectedNodeId)
    var node = _.find(window.nodes, n =>{
        return n.nodeId === selectedNodeId
    })
    $("#mainoverview").fadeOut("quick", function () {
            $("#backbutton").show()
            $("#mainoverview").hide()
            $("#nodedetails").show()
            $("#nodedetails").fadeIn("quick", function(){
            $("#pagetitle_header").html("Node control page")
            $("#page_head").html("Node details")
                getSensors(selectedNodeId)
                    .then((result) => {
                        var temperature = result.sensorsStatus.hygrometer.temperature + "°"
                        var humidity =  result.sensorsStatus.hygrometer.humidity + "%"
                        $("#temp_box").html(temperature)
                        $("#humidity_box").html(humidity)
                        hideModal()
                    })
                    .catch(()=>{
                        hideModal()
                    })
        })
    })

    $("#node_header").html("Details of " + node.macAddress)
    $("#nodeMacAddr").html(node.macAddress)
    $("#nodeIp").html(node.nodeIp)
    $("#nodeId").html(node.nodeId)
    $("#createdOn").html(moment(node.creationDate).format('MMMM Do YYYY, h:mm:ss a'))
    if(node.online){
        $("#buttonOffline").hide()
        $("#buttonOnline").show()
    } else {
        $("#buttonOffline").show()
        $("#buttonOnline").hide()
    }
    var lastUpdate = $.timeago(node.lastPingDate)
    $("#lastUpdate").html(lastUpdate)

    $("#modal").modal('show')


}
function updateNode(node){

    var arrowClass = ""
    var textClass = ""
    var textOnline = ""

    if(node.online){
        arrowClass = "fas fa-arrow-up"
        textClass = "text-success mr-1"
        textOnline = "Online"
    }else{
        arrowClass = "fas fa-arrow-down"
        textClass = "text-danger mr-1"
        textOnline = "Offline"
    }
    $("#status_" + node.nodeId).removeClass();
    $("#arrow_" + node.nodeId).removeClass();
    $("#status_" + node.nodeId).addClass(textClass)
    $("#arrow_" + node.nodeId).addClass(arrowClass)
    $("#text_" + node.nodeId).html(textOnline)


}

var updateNodes = function(){
    //console.log("Updating nodes...")
    getMyNodes().then(results => {
        $(results).each(function(){
            updateNode(this)
        })
        //console.info("Update complete: ", results)
    })
}

function showHome(){
    $("#nodedetails").fadeOut("quick", function () {
        $("#backbutton").hide()
        $("#nodedetails").hide()
        $("#mainoverview").show()
        $("#mainoverview").fadeIn("quick", function(){
        $("#pagetitle_header").html("Home")
        $("#page_head").html("Gateway Dashboard")

        })
    })
}