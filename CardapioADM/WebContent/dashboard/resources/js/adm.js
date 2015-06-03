/**
 * 
 */
$(document).ready(function() {
	$.ajaxSetup({cache : false});
	
	startModalLoad(1);	
	
	$("#page").load("../resources/templates/adm/index.xhtml");

	$.getJSON(url + "setores/", function(data) {
		var ulMesas = $('<ul />', {});
		
		if (Array.isArray(data)) {
			var setor;
			for (var i = 0; i < data.length; i++) {				
				setor = data[i];				
				var liMesas = $('<li />', {});
				var a = $('<a />', {
					page : '../resources/templates/adm/mesas.xhtml',
					idSetor: setor.setor.id,
					text : setor.setor.descricao
				});				
				liMesas.append(a);
				ulMesas.append(liMesas);
			}			
			$("#mesas").append(ulMesas);
		} else {
			var liMesas = $('<li />', {});
			var a = $('<a />', {
				page : '../resources/templates/adm/mesas.xhtml',
				idSetor: data.setor.id,
				text : data.setor.descricao
			});
			liMesas.append(a);
			ulMesas.append(liMesas);
			$("#mesas").append(ulMesas);
		}
		
		$("#menu").superfish({});

		$("#menu a").click(function() {
			if($(this).attr("page") != ""){
				stopInterval();
				$("#page").load($(this).attr("page"), {'idSetor': $(this).attr('idSetor')});				
			}
		});
		updateCompletedEventProgress();
	}).fail(function() {
		updataMsgErro();
	});
});

var arrayInterval = [];
function stopInterval(){
	for	(index = 0; index < arrayInterval.length; index++) {
		clearInterval(arrayInterval[index]);
	}
}