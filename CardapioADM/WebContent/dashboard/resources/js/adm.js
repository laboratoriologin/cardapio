/**
 * 
 */
$(document).ready(function() {
	$.ajaxSetup({cache : false});
	
	startModalLoad(1);	
	
	$("#page").load("../resources/templates/adm/index.xhtml");
	
	$("#dialog-juntarmesa").dialog({
		resizable : false,
		height : (($("#page").height() - ($("#page").height() * 0.65))),
		width : (($("#page").width() - ($("#page").width() * 0.85))),
		modal : true,
		autoOpen : false,
		 buttons: [
		           {
		               id: "button-unir",
		               text: "Unir",
		               click: function() {
		            	   
		            	   if($("#mesaDestino").val() != ""){
		            		   if($("#mesaOrigem").val() != ""){
		            			   postJoinTable($("#mesaOrigem").val(), $("#mesaDestino").val());
		            		   } else {
			            		   alert("Digite as mesas de origem.");			            		   		            			   
		            		   }
		            		   
		            	   } else {
		            		   alert("Digite uma mesa de destino.");
		            	   }		                   
		               }
		           },
		           {
		               id: "button-cancelar",
		               text: "Cancelar",
		               click: function() {		            	   
		                   $(this).dialog("close");
		               }
		           }
		       ]
	});

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
			var attr = $(this).attr('page');
			if (typeof attr !== typeof undefined && attr !== false) {
				$("#page").load($(this).attr("page"), {'idSetor': $(this).attr('idSetor')});
			} else {
				if($(this).attr('dialog')){
					$("#dialog-juntarmesa").dialog("open");
				}
			}			
		});
		updateCompletedEventProgress();
	}).fail(function() {
		updataMsgErro();
	});
});

function postJoinTable(mesasOrigem, mesaDestino) {
	
	if(confirm("Deseja unir as mesas?")){	
		$.ajax({
			url : url + "acoes_contas/juntarmesa/origem/" + mesasOrigem + "/destino/" + mesaDestino + "/usuario/" + $("#usuarioId").val(),
			type : 'POST',
			cache : false,
			data : {
						'usuario' : $("#usuarioId").val()
			}
		}).done(function(result) {
			alert('União realizada com sucesso!');
		}).fail(function(result) {
			alert('Erro, tente novamente mais tarde.');
		});
	}
}

var arrayInterval = [];
function stopInterval(){
	for	(index = 0; index < arrayInterval.length; index++) {
		clearInterval(arrayInterval[index]);
	}
}