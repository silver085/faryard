function buildPanel(){
    $(".modal").modal('show')
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
        .append("<tr>\n" +
            "                                        <td>\n" +
            "                                            <img src=\"/static/img/nodeicon.png\" alt=\"Product 1\" class=\"img-circle img-size-32 mr-2\">\n" +
            node.macAddress +
            "                                        </td>\n" +
            "                                        <td>"+node.nodeIp+"</td>\n" +
            "                                        <td>\n" +
            "                                            <span class=\""+textClass+"\">\n" +
            "                                                <i class=\""+arrowClass+"\"></i>\n" +
            textOnline+
            "                                            </span>\n" +
            "                                        </td>\n" +
            "                                        <td>\n" +
            "                                            <a href=\"#\" class=\"text-muted\" onclick=\"displayNodeOverview('"+node.nodeId+"')\">\n" +
            "                                                <i class=\"fas fa-search\"></i>\n" +
            "                                            </a>\n" +
            "                                        </td>\n" +
            "                                    </tr>")
}


function displayNodeOverview(nodeid){
    console.log("Node overview ", nodeid)
}