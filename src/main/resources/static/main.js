$(document).ready(function () {

        fire_ajax_submit();
        getNumericalInfo();

});

function fire_ajax_submit() {

    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/api/showchart",
        success: function (data1) {
        var dps = [];
         var yValue;
         var label;

        for (var i = 0; i < data1.length; i++){
                    dps.push({
                    label : data1[i].name,
                    y : data1[i].amount,
                        });
                }
        console.log(dps)
        console.log(data1);
        var chart = new CanvasJS.Chart("chartContainer",{
        	title: {
        		text: "Expense By Category"
        	},

        	animationEnabled: true,
        	data: [{
        		type: "pie",
        		startAngle: 40,
        		toolTipContent: "<b>{label}</b>: {y}",
        		showInLegend: "true",
        		legendText: "{label}",
        		indexLabelFontSize: 16,
        		indexLabel: "{label} - {y}",
        		dataPoints: dps
        	}]
        });
        chart.render();
        }
        });
}

function getNumericalInfo(){
$.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/api/showAmount",
        success: function (data) {
        console.log(data)
        $("#expense").append(data[0].Expense);
        $("#catagory").append(data[0].Category);
        $("#income").append(data[0].Income);
        }

        });
}
