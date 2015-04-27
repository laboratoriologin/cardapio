/**
 * 
 */
$(document).ready(function() {
	
	startModalLoad(1);
	updateCompletedEventProgress();

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
			if($(this).attr("page") != "")
				$("#page").load($(this).attr("page"), {'idSetor': $(this).attr('idSetor')});
		});
		updateCompletedEventProgress();
	}).fail(function() {
		updataMsgErro();
	});
});