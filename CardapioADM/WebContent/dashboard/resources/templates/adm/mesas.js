/**
 * 
 */
$(document).ready(function() {
	$.ajaxSetup({ cache: false });
	
	$("#dialog-historicoconta").dialog({
		resizable: false,
		height: ($("#bodyMesas").height() - 50),
		width: ($("#bodyMesas").width() - 300),
		modal: true,        
		autoOpen: false
			/*,buttons: {
			  "Abrir": function() {
			  },
			  Cancel: function() {    	  	
			    $( this ).dialog( "close" );
			  }
			}*/
	});
	
    $("#dialog-abrirconta").dialog({
    	resizable: false,
        height:215,
        modal: true,
        autoOpen: false,
        buttons: {
          "Abrir": function() {
        	  $.ajax({
        		  url: url + "contas",
        		  data: {'numero' : $("#numeroMesaNovaConta").val(), 'tipoconta' : $("#tipoConta").val()},
        		  type: 'POST',
        		  cache: false 
        	  }).done(function (result){
        		  
        		  console.log(result);
        		  
        		  loadMesas();
        	  }).fail(function (result){
        		  alert('erro');
        	  });
            $( this ).dialog( "close" );
          },
          Cancel: function() {
    	  	$("#numeroMesaNovaConta").val("");
            $( this ).dialog( "close" );
          }
        }
    });
    
	$("#produto").autocomplete({
		source: function( request, response ) {
			$.ajax({
				url: url + "sub_itens/autocomplete/" + $("#produto").val(),
				dataType: "json",
				success: function( data ) {
					response( data );
				}
			});
		},
		minLength: 3,
		select: function( event, ui ) {
			alert( ui.item ?
					"Selected: " + ui.item.label :
						"Nothing selected, input was " + this.value);
		}
	});
	
	loadMesas();
});

function postChangeTable(conta, mesaDe, mesaPara){
	
	$.ajax({
		url: url + "acoes_contas/mudarmesa/" + mesaPara,
		data: { 'conta.id' : conta,  'conta.numero' : mesaDe, 'usuario' : '1'},
		type: 'POST',
		cache: false 
	}).done(function (result){		
		loadMesas();
	}).fail(function (result){
		alert('erro');
	});     
	
}

function postJoinTable(){
		
}

function loadMesas(){
	
	$("#bodyMesas").empty();
	
	$.getJSON(url + "mesas/setor/" + $("#setorId").val(), function(data) {

		if (Array.isArray(data)) {
			for (var i = 0; i < data.length; i++) {
				var obj = data[i];
				createMesa(obj);
			}
		} else {
			createMesa(data);
		}

	}).fail(function() {
		updataMsgErro();
	});
}

function createMesa(obj){
	
	var classMesa = "";
	if (obj.mesa.setor == "") {
		classMesa = "mesaVerde";
	} else {
		classMesa = "mesaVermelho";
	}		
	
	var mesa = $('<div />', {
		class : classMesa,
		contaId: obj.mesa.setor.id //Aqui está o id da conta 
	});

	if (obj.mesa.setor != "")
		mesa.draggable({ opacity: 0.7, helper: "clone", containment: "#bodyMesas", scroll: false });

	var p = $('<p/>', {
		text : obj.mesa.numero
	});

	mesa.append(p);
	
	mesa.droppable({
		drop: function( event, ui ) {
			var attr = $(this).attr('contaId');
			if (typeof attr !== typeof undefined && attr !== false)
				postJoinTable();
			else{								
				if (confirm("Deseja trocar a mesa da conta?"))
					postChangeTable(ui.draggable.attr("contaId"), ui.draggable.text(), $(this).text());
			}
		}
    });
	
	mesa.click(function(){
		var attr = $(this).attr('contaId');
		
		if (typeof attr !== typeof undefined && attr !== false){
			$("#dialog-historicoconta").dialog( "open" );
		}else{
			$("#numeroMesaNovaConta").val($(this).text());
			$("#dialog-abrirconta").dialog( "open" );
		}
	});

	$("#bodyMesas").append(mesa);	
}