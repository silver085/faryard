function buildPanel() {
    window.selectedScreen = "home"
    window.selectedNodeId = null
    $(".modal").modal('show')
    $("#backbutton").hide()
    isValidSession().then((result) => {
        if (!result) {
            window.location.href = "/admin/login"
        } else {
            getUserProfile().then((result) => {

                $("#useremail").html(result.email)
                $("#profilepicture").html(
                    "<img src=\"" + result.userPicture + "\" />"
                )

                var numNotifications = result.notifications.length

                if (numNotifications > 0) {
                    $("#bellcounter").html(numNotifications)
                    $("#notificationscounter").html(numNotifications + " Notifications")
                } else {
                    $("#notificationscounter").html("No new notifications")
                }

                window.nodes = result.myNodes;
                $(result.myNodes).each(function () {
                    console.log(this)
                    var iconClass = "nav-icon fas fa-circle text-danger"
                    if (this.online)
                        iconClass = "nav-icon fas fa-circle text-info"

                    $("#networkmenu").append("<li class=\"nav-item\">\n" +
                        "                                <a href=\"#\" id='menu_" + this.nodeId + "' class=\"nav-link\" onclick=\"displayNodeOverview('" + this.nodeId + "')\">\n" +
                        "                                    <i class=\"" + iconClass + "\"></i>\n" +
                        "                                    <p>" + this.macAddress + "</p>\n" +
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

function addNodeOverviewItem(node) {
    var arrowClass = ""
    var textClass = ""
    var textOnline = ""
    if (node.online) {
        arrowClass = "fas fa-arrow-up"
        textClass = "text-success mr-1"
        textOnline = "Online"
    } else {
        arrowClass = "fas fa-arrow-down"
        textClass = "text-danger mr-1"
        textOnline = "Offline"
    }

    $("#nodeoverview tbody")
        .append("<tr id='row_" + node.nodeId + "'>\n" +
            "                                        <td>\n" +
            "                                            <img src=\"/static/img/nodeicon.png\" alt=\"Product 1\" class=\"img-circle img-size-32 mr-2\">\n" +
            node.macAddress +
            "                                        </td>\n" +
            "                                        <td>" + node.nodeIp + "</td>\n" +
            "                                        <td>\n" +
            "                                            <span id='status_" + node.nodeId + "' class=\"" + textClass + "\">\n" +
            "                                                <i id='arrow_" + node.nodeId + "'  class=\"" + arrowClass + "\"></i>\n" +
            "<span id='text_" + node.nodeId + "'>" + textOnline + "</span>" +
            "                                            </span>\n" +
            "                                        </td>\n" +
            "                                        <td>\n" +
            "                                            <a href=\"#\" class=\"text-muted\" onclick=\"displayNodeOverview('" + node.nodeId + "')\">\n" +
            "                                                <i class=\"fas fa-search\"></i>\n" +
            "                                            </a>\n" +
            "                                        </td>\n" +
            "                                    </tr>")
}


function displayNodeOverview(selectedNodeId) {
    window.selectedScreen = "nodeOverview"
    window.selectedNodeId = selectedNodeId
    $("#modal").modal("show")
    $("#networkmenu li").each(function () {
        $(this).children().removeClass("active")

    })

    $("#menu_" + selectedNodeId).toggleClass("active")

    var node = _.find(window.nodes, n => {
        return n.nodeId === selectedNodeId
    })
    $("#mainoverview").fadeOut("quick", function () {
        $("#backbutton").show()
        $("#mainoverview").hide()
        $("#nodedetails").show()
        $("#nodedetails").fadeIn("quick", function () {
            $("#pagetitle_header").html("Node control page")
            $("#page_head").html("Node details")
            getSensors(selectedNodeId)
                .then((result) => {
                    window.selectedSensors = result
                    updateSensorsBox(result.sensorsStatus)
                    showGraphs(selectedNodeId, null , null, null)
                    hideModal()
                })
                .catch(() => {
                    hideModal()
                })
        })
    })

    $("#node_header").html("Details of " + node.macAddress)
    $("#nodeMacAddr").html(node.macAddress)
    $("#nodeIp").html(node.nodeIp)
    $("#nodeId").html(node.nodeId)
    $("#createdOn").html(moment(node.creationDate).format('MMMM Do YYYY, h:mm:ss a'))
    $("#nodeWanIp").html(node.nodeWanIp)
    if (node.online) {
        $("#buttonOffline").hide()
        $("#buttonOnline").show()
    } else {
        $("#buttonOffline").show()
        $("#buttonOnline").hide()
    }
    var lastUpdate = $.timeago(node.lastPingDate)
    $("#lastUpdate").html(lastUpdate)



}

function updateNode(node) {

    if (window.selectedScreen === "home") {
        var arrowClass = ""
        var textClass = ""
        var textOnline = ""

        if (node.online) {
            arrowClass = "fas fa-arrow-up"
            textClass = "text-success mr-1"
            textOnline = "Online"
        } else {
            arrowClass = "fas fa-arrow-down"
            textClass = "text-danger mr-1"
            textOnline = "Offline"
        }
        $("#status_" + node.nodeId).removeClass();
        $("#arrow_" + node.nodeId).removeClass();
        $("#status_" + node.nodeId).addClass(textClass)
        $("#arrow_" + node.nodeId).addClass(arrowClass)
        $("#text_" + node.nodeId).html(textOnline)
    } else if(window.selectedScreen === "nodeOverview" && window.selectedNodeId != null){
        if(window.selectedNodeId === node.nodeId){
            displayNodeOverview(window.selectedNodeId)
        }

    }

}

var updateNodes = function () {
    //console.log("Updating nodes...")
    getMyNodes().then(results => {
        $(results).each(function () {
            updateNode(this)
        })
        //console.info("Update complete: ", results)
    })
}

function showHome() {
    window.selectedScreen = "home"
    window.selectedNodeId = null
    window.selectedSensors = null
    $("#nodedetails").fadeOut("quick", function () {
        $("#backbutton").hide()
        $("#nodedetails").hide()
        $("#mainoverview").show()
        $("#mainoverview").fadeIn("quick", function () {
            $("#pagetitle_header").html("Home")
            $("#page_head").html("Gateway Dashboard")

        })
    })
}

function updateSensorsBox(sensors){
    var hygrometer = sensors.hygrometer
    var adsList = optional(sensors.ads, [])
    var relays = optional(sensors.relays, [])
    var sensorbox = $("#sensorsbox")
    //hygrometer
    if(hygrometer){
        var temperature = optional(hygrometer.temperature, 0)

        var temperatureIcon = "<i class=\"fas fa-temperature-low\"></i>"
        if(temperature > 20)
            temperatureIcon = "<i class=\"fas fa-temperature-high\"></i>"
        if($("#tempbox").length === 0){
            $(sensorbox).append("<div id='tempbox' class=\"col-md-3 col-sm-5 col-6\">\n" +
                "            <div id=\"tempboxcontent\" class=\"info-box\">\n" +
                "              <span id='temperatureicon' class=\"info-box-icon bg-info\">"+temperatureIcon+"</span>\n" +
                "              <div class=\"info-box-content\">\n" +
                "                <span class=\"info-box-text\">Temperature</span>\n" +
                "                <h2 id='temperaturevalue' class=\"info-box-number\">"+temperature+"°</h2>\n" +
                "              </div>\n" +
                "            </div>\n" +
                "          </div>")
        } else {

            $("#temperatureicon").html(temperatureIcon)
            $("#temperaturevalue").html(temperature + "°")
        }

        //HUMIDITY
        var humidityIcon = "<i class=\"fas fa-percent\"></i>"
        var humidityValue = optional(sensors.hygrometer.humidity, 0)
        if($("#humiditybox").length === 0){
            $(sensorbox).append("<div id='humiditybox' class=\"col-md-3 col-sm-5 col-6\">\n" +
                "            <div id=\"humidityboxcontent\" class=\"info-box\">\n" +
                "              <span id='humidityboxicon' class=\"info-box-icon bg-info\">"+humidityIcon+"</span>\n" +
                "              <div class=\"info-box-content\">\n" +
                "                <span class=\"info-box-text\">Humidity</span>\n" +
                "                <h2 id='humidityvalue' class=\"info-box-number\">"+humidityValue+"°</h2>\n" +
                "              </div>\n" +
                "            </div>\n" +
                "          </div>")
        } else {
            $("#humidityvalue").html(humidityValue + "%")
        }


    }

    //ads

    _.each(adsList, (ads) => {

        var adsName = optional(ads.name, "")
        var evaluation = optional(ads.evaluation, {})
        var representation = optional(getRepresentation(evaluation, adsName), {icon: null, text: "NA", image: null})
        ads.percentage = parseFloat(ads.percentage).toFixed(0) + " %";
        if($("#" + adsName + "box").length === 0){
            $(sensorbox).append("<div id='"+adsName+"box' class=\"col-md-3 col-sm-5 col-6\">\n" +
                "            <div id='"+adsName+"infobox' class=\"info-box\">\n" +
                "              <span id=''"+adsName+"reprIcon' class=\"info-box-icon bg-info\">"+representation.icon+"</span>\n" +
                "              <div class=\"info-box-content\">\n" +
                "                <span id='"+adsName+"reprText' class=\"info-box-text\">"+representation.text+"<small> ("+ads.percentage+") </small></span>\n" +
                "                <h2 id='"+adsName+"reprImage' class=\"info-box-number\">"+representation.image+"</h2>\n" +
                "              </div>\n" +
                "            </div>\n" +
                "          </div>")
        } else {
            $("#" + adsName + "reprIcon").html(representation.icon)
            $("#" + adsName + "reprText").html(representation.text+"<small> ("+ads.percentage+") </small></span>\n")
            $("#" + adsName + "reprImage").html(representation.image)

        }
    })

    var c = 0;
    _.each(relays, (relay) => {

        var isOn = optional(relay.status, false)
        var lastUpdate = optional(relay.lastUpdate, new Date())
        var adsName = "relay" + c
        var representation = getRelayRepresentation(relay, c)
        var status = isOn ? "On" : "Off"
        if($("#" + adsName + "box").length === 0){
            $(sensorbox).append("<div id='"+adsName+"box' class=\"col-md-3 col-sm-5 col-6\"\">\n" +
                "            <div id='"+adsName+"infobox' class=\"info-box\">\n" +
                "              <span id=''"+adsName+"reprIcon' class=\"info-box-icon bg-info\">"+representation.icon+"</span>\n" +
                "              <div class=\"info-box-content\">\n" +
                "                <span id='"+adsName+"reprText' class=\"info-box-text\">"+representation.text+"<small> ("+status+") </small></span>\n" +
                "                <h2 id='"+adsName+"reprImage' class=\"info-box-number\">"+representation.image+"</h2>\n" +
                "              </div>\n" +
                "            </div>\n" +
                "          </div>")

            $("#"+adsName+"box").attr('onclick', 'deployCommand('+c+', \'switchrelay\')')

        } else {
            $("#" + adsName + "reprIcon").html(representation.icon)
            $("#" + adsName + "reprText").html(representation.text+"<small> ("+status+") </small></span>\n")
            $("#" + adsName + "reprImage").html(representation.image)

        }
        c++;
    })


}

function getRelayRepresentation(relay, index){
    var output = {
        icon: null,
        text: "NA",
        image: null
    }
    if(optional(relay.status, false)){
        output.image = "<i class=\"fas fa-toggle-on\"></i>"
    } else {
        output.image = "<i class=\"fas fa-toggle-off\"></i>"
    }
    output.icon = "<i class=\"fab fa-superpowers\"></i>"
    output.text = "Irrigator (" + (index+1) + ")"
    return output
}

function getRepresentation(evaluation, adsName){
  var output = {
      icon: null,
      text: "NA",
      image: null
  }

  if(adsName.startsWith("light")){
      output.icon = "<i class=\"fas fa-lightbulb\"></i>"
      if(evaluation.evaluationInteger >= 0 && evaluation.evaluationInteger <= 2){
          output.image = "<i class=\"fas fa-cloud\"></i>"
      } else if(evaluation.evaluationInteger > 2 && evaluation.evaluationInteger <=5){
          output.image = "<i class=\"fas fa-sun\"></i>"
      }
  }
  if(adsName.startsWith("moisture")){
      output.icon = "<i class=\"fas fa-seedling\"></i>"
      if(evaluation.evaluationInteger === 0){
          output.image = "<i class=\"fas fa-shower\"></i>"
      } else if(evaluation.evaluationInteger === 1){
          output.image = "<i class=\"fas fa-tint\"></i>"
      } else if(evaluation.evaluationInteger === 2 || evaluation.evaluationInteger === 3){
          output.image = "<i class=\"fas fa-water\"></i>"
      }
  }

  output.text = evaluation.evaluation
  return output
}

function displayModal(title, text, onConfirm){
    var modalHtml = "<div id=\"question\" class=\"modal fade show\" id=\"modal-sm\" style=\"display: block;\" aria-modal=\"true\">\n" +
        "        <div class=\"modal-dialog modal-sm\">\n" +
        "          <div class=\"modal-content\">\n" +
        "            <div class=\"modal-header\">\n" +
        "              <h4 class=\"modal-title\">"+title+"</h4>\n" +
        "              <button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-label=\"Close\">\n" +
        "                <span aria-hidden=\"true\">×</span>\n" +
        "              </button>\n" +
        "            </div>\n" +
        "            <div class=\"modal-body\">\n" +
        "              <p>"+text+"</p>\n" +
        "            </div>\n" +
        "            <div class=\"modal-footer justify-content-between\">\n" +
        "              <button id='closeModal' type=\"button\" class=\"btn btn-default\" data-dismiss=\"modal\">Cancel</button>\n" +
        "              <button id=\"confirmModal\" type=\"button\" class=\"btn btn-primary\">Confirm</button>\n" +
        "            </div>\n" +
        "          </div>\n" +
        "          <!-- /.modal-content -->\n" +
        "        </div>\n" +
        "        <!-- /.modal-dialog -->\n" +
        "      </div>"
    $("body").append(modalHtml)
    $("#closeModal").click(()=>{
        $("#question").remove()
    })
    $("#confirmModal").click(()=>{
        $("#question").remove()
        onConfirm()
    })
}

function deployCommand(id, command){
    if(command === "switchrelay"){

        var relayStatus = window.selectedSensors.sensorsStatus.relays[id].status
        var switchCommand = relayStatus ? "OFF":"ON"
        displayModal("Confirm action", "This will switch " + switchCommand + " the relay "+ (id+1) + "\nand will have priority on normal actions to node\nContinue?",function(){
            console.log("Action confirmed")
            postSwitchRelay(window.selectedNodeId, id, !relayStatus)
                .then((data)=>{
                    if(data.message === "OK"){
                        Toast.fire({
                            type: 'success',
                            title: "Your action has been deployed :)"
                        });
                    } else {
                        Toast.fire({
                            type: 'error',
                            title: "Error deploying action :("
                        });
                    }

                })
                .catch(()=>{
                    Toast.fire({
                        type: 'error',
                        title: "Error deploying action :("
                    });
                })
        })
    } else if(command === "directcommand"){

    }
}

function getSelectedNode(){
    var selectedNodeId = window.selectedNodeId
    return _.filter(window.nodes, (n) => n.nodeId===selectedNodeId)
}