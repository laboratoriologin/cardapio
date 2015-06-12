/**
 * 
 */
var tempoAtualizacao = 60000;
$(document).ready(function() {
	$.ajaxSetup({cache : false});
	
	 $( "#radio" ).buttonset();
	
	$("#datainicio").datepicker({onSelect: function(date) {
        alert(date);
     }});
	$("#datainicio").datepicker('setDate', new Date());
	$("#datafim").datepicker({onSelect: function(date) {
        alert(date);
    }});
	$("#datafim").datepicker('setDate', new Date());
	
	$('#htmlhorainicial').html("00:00");
	$("#horainicio").slider({
		range: false,
		min: 0,
		max: 1439,
		step: 1,
		slide: function(e, ui) {
			var hours = Math.floor(ui.value / 60);
			var minutes = ui.value - (hours * 60);
	
			if(hours.length == 1) hours = '0' + hours;
			if(minutes.length == 1) minutes = '0' + minutes;
	
			hours = (hours < 10 ? "0" : "") + hours;
			minutes = (minutes < 10 ? "0" : "") + minutes;
			
			$('#htmlhorainicial').html(hours+':'+minutes);
	    }
	});

	$('#htmlhorafim').html("23:59");
	$("#horafim").slider({
		range: false,
		min: 0,
		max: 1439,
		step: 1,
		value : 1439,
		slide: function(e, ui) {
			var hours = Math.floor(ui.value / 60);
			var minutes = ui.value - (hours * 60);
	
			if(hours.length == 1) hours = '0' + hours;
			if(minutes.length == 1) minutes = '0' + minutes;
	
			hours = (hours < 10 ? "0" : "") + hours;
			minutes = (minutes < 10 ? "0" : "") + minutes;
			
			$('#htmlhorafim').html(hours+':'+minutes);
	    }
	});	
});

function getData(){
	
}