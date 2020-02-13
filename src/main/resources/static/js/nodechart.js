function showGraphs(nodeId, timespan, start, end) {

    if (!timespan) timespan = "hourly"
    if (!start) start = moment().subtract(1, 'days').endOf('day').format()
    if (!end) end = moment().format()

    getGraph(nodeId, timespan, start, end)
        .then((result) => {

            printGraphDataTemperatures(optional(result.data, []))
            printGraphDataLight(optional(result.data, []))
            printMoisturesGraph(optional(result.data, []))
        })
        .catch(() => {
            console.error("Error fetching graph data")
        })

}

function printGraphDataLight(data) {
    var areaChartCanvas = $('#lightChart').get(0).getContext('2d')
    var labels = _.map(data, (v) => {
        return moment(v.date).format("DD/MM hh a")
    })
    var lightPercentages = _.map(data, (v) => {
        return v.sensors.ads[0].percentage
    })
    var areaChartData = {
        labels: labels,
        datasets: [
            {
                label: 'Light Exposure (%)',
                backgroundColor: 'rgba(255,214,54,0.9)',
                borderColor: 'rgba(255,255,255,0.8)',
                pointRadius: false,
                pointColor: '#3b8bba',
                pointStrokeColor: 'rgb(255,3,37)',
                pointHighlightFill: '#fff',
                pointHighlightStroke: 'rgba(60,141,188,1)',
                data: lightPercentages
            }

        ]
    }

    var areaChartOptions = {
        maintainAspectRatio: false,
        responsive: true,
        legend: {
            display: true
        },
        scales: {
            xAxes: [{
                gridLines: {
                    display: false,
                }
            }],
            yAxes: [{
                gridLines: {
                    display: false,
                }
            }]
        },
        animation: {
            duration: 0
        },
        tooltips: {
            enabled: true
        }

    }

    // This will get the first returned node in the jQuery collection.
    if (!window.lightGraph) {
        window.lightGraph = new Chart(areaChartCanvas, {
            type: 'line',
            data: areaChartData,
            options: areaChartOptions
        })
    } else {
        window.lightGraph.data = areaChartData
        window.lightGraph.update()
    }
}

function printGraphDataTemperatures(data) {
    // Get context with jQuery - using jQuery's .get() method.

    var areaChartCanvas = $('#temperatureChart').get(0).getContext('2d')
    var labels = _.map(data, (v) => {
        return moment(v.date).format("DD/MM hh a")
    })
    var temperatures = _.map(data, (v) => {
        return v.sensors.hygrometer.temperature
    })
    var humidities = _.map(data, (v) => {
        return v.sensors.hygrometer.humidity
    })
    var areaChartData = {
        labels: labels,
        datasets: [
            {
                label: 'Temperature (°C)',
                backgroundColor: 'rgba(255,3,37,0.9)',
                borderColor: 'rgba(255,255,255,0.8)',
                pointRadius: false,
                pointColor: '#3b8bba',
                pointStrokeColor: 'rgb(255,3,37)',
                pointHighlightFill: '#fff',
                pointHighlightStroke: 'rgba(60,141,188,1)',
                data: temperatures
            },
            {
                label: 'Humidity (%)',
                backgroundColor: 'rgba(128,199,255,0.9)',
                borderColor: 'rgba(255,255,255,0.8)',
                pointRadius: false,
                pointColor: '#3b8bba',
                pointStrokeColor: 'rgb(255,3,37)',
                pointHighlightFill: '#fff',
                pointHighlightStroke: 'rgba(60,141,188,1)',
                data: humidities
            }
        ]
    }

    var areaChartOptions = {
        maintainAspectRatio: false,
        responsive: true,
        legend: {
            display: true
        },
        scales: {
            xAxes: [{
                gridLines: {
                    display: false,
                }
            }],
            yAxes: [{
                gridLines: {
                    display: false,
                }
            }]
        },
        animation: {
            duration: 0
        },
        tooltips: {
            enabled: true
        }

    }

    // This will get the first returned node in the jQuery collection.
    if (!window.temperatureGraph) {
        window.temperatureGraph = new Chart(areaChartCanvas, {
            type: 'line',
            data: areaChartData,
            options: areaChartOptions
        })
    } else {
        window.temperatureGraph.data = areaChartData
        window.temperatureGraph.update()
    }
}


function printMoisturesGraph(data) {
    var tempData = []
    var moisturesData = []
    var labels = _.map(data, (v) => {
        return moment(v.date).format("DD/MM hh a")
    })

    _.each(data, (d) => {
        tempData.push(d.sensors.ads)
    })
    _.each(tempData, (d) => {
        var currentArray = []
        _.each(d, (v) => {
            if (v.name.startsWith("moisture")) {
                currentArray.push(v)
            }
        })
        moisturesData.push(currentArray)
    })

    var maxLen = 0
    _.each(moisturesData, (row) => {
        if (row.length > maxLen) maxLen = row.length
    })

    if (window.moistureGraphs == null)
        window.moistureGraphs = []

    for (i = 0; i < maxLen; i++) {
        var chartHtml = "             <div class=\"col-md-3\">\n" +
            "                            <div class=\"card card-primary\">\n" +
            "                                <div class=\"card-header\">\n" +
            "                                    <h3 class=\"card-title\">Moisture " + (i + 1) + " History</h3>\n" +
            "\n" +
            "                                    <div class=\"card-tools\">\n" +
            "                                        <button type=\"button\" class=\"btn btn-tool\" data-card-widget=\"collapse\"><i\n" +
            "                                                class=\"fas fa-minus\"></i>\n" +
            "                                        </button>\n" +
            "                                        <button type=\"button\" class=\"btn btn-tool\" data-card-widget=\"remove\"><i\n" +
            "                                                class=\"fas fa-times\"></i></button>\n" +
            "                                    </div>\n" +
            "                                </div>\n" +
            "                                <div class=\"card-body\">\n" +
            "                                    <div class=\"chart\">\n" +
            "                                        <div class=\"chartjs-size-monitor\">\n" +
            "                                            <div class=\"chartjs-size-monitor-expand\">\n" +
            "                                                <div class=\"\"></div>\n" +
            "                                            </div>\n" +
            "                                            <div class=\"chartjs-size-monitor-shrink\">\n" +
            "                                                <div class=\"\"></div>\n" +
            "                                            </div>\n" +
            "                                        </div>\n" +
            "                                        <canvas id=\"moisture" + i + "\"\n" +
            "                                                style=\"min-height: 250px; height: 250px; max-height: 250px; max-width: 100%; display: block;\"\n" +
            "                                                width=\"492\" height=\"250\" class=\"chartjs-render-monitor\"></canvas>\n" +
            "                                    </div>\n" +
            "                                </div>\n" +
            "                                <!-- /.card-body CHART TEMP -->\n" +
            "\n" +
            "\n" +
            "                            </div>\n" +
            "                        </div>"


        var currentMoistureData = []
        _.each(moisturesData, (d) => {
            currentMoistureData.push(d[i].percentage)
        })

        var areaChartData = {
            labels: labels,
            datasets: [
                {
                    label: 'Moisture Wetness (%)',
                    backgroundColor: 'rgba(139,203,255,0.9)',
                    borderColor: 'rgba(255,255,255,0.8)',
                    pointRadius: false,
                    pointColor: '#3b8bba',
                    pointStrokeColor: 'rgb(255,3,37)',
                    pointHighlightFill: '#fff',
                    pointHighlightStroke: 'rgba(60,141,188,1)',
                    data: currentMoistureData
                }
            ]
        }

        var graphbox = $("#graphbox")
        if ($("#moisture" + i).length === 0) {
            $(graphbox).append(chartHtml)
            var areaChartCanvas = $('#moisture' + i).get(0).getContext('2d')

            var areaChartOptions = {
                maintainAspectRatio: false,
                responsive: true,
                legend: {
                    display: true
                },
                scales: {
                    xAxes: [{
                        gridLines: {
                            display: false,
                        }
                    }],
                    yAxes: [{
                        gridLines: {
                            display: false,
                        }
                    }]
                },
                animation: {
                    duration: 0
                },
                tooltips: {
                    enabled: true
                }

            }
        }
        if (window.moistureGraphs[i] == null) {
            window.moistureGraphs.push(new Chart(areaChartCanvas, {
                type: 'line',
                data: areaChartData,
                options: areaChartOptions
            }))
        } else {
            window.moistureGraphs[i].data = areaChartData
            window.moistureGraphs[i].update()
        }
    }
}

