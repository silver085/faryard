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

function printGraphDataLight(data){
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
                label: 'Light Exposure',
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
        tooltips:{
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
                label: 'Temperatures',
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
                label: 'Humidity',
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
        tooltips:{
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


function printMoisturesGraph(data){
    var moisturesTitles = []
    return

    var labels = _.map(data, (v) => {
        return moment(v.date).format("DD/MM hh a")
    })

    var moisturesData = _.map(data, (v)=>{
        var output = []
        _.each(v.ads, (ads) =>{
            if(ads.name.startsWith("moisture")){
                output.push({name: ads.name, percentage: ads.percentage, index: ads.evaluation.evaluationIndex, label: ads.evaluation.evaluation})
            }

        })
        debugger
        return output
    })



}