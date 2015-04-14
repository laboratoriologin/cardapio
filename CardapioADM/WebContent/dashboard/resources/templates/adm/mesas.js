/**
 * 
 */

$(document).ready(function() {
	$.ajaxSetup({cache : false});
	
	$("#loader").hide();

	$("#dialog-novopedido").dialog({
		resizable : false,
		height : (($("#bodyMesas").height() - ($("#bodyMesas").height() * 0.20))),
		width : (($("#bodyMesas").width() - ($("#bodyMesas").width() * 0.70))),
		modal : true,
		autoOpen : false	
	});

	$("#dialog-historicoconta").dialog({
		resizable : false,
		height : ($("#bodyMesas").height() - 50),
		width : ($("#bodyMesas").width() - 300),
		modal : true,
		autoOpen : false
	});

	$("#dialog-abrirconta").dialog({
		resizable : false,
		height : 215,
		modal : true,
		autoOpen : false,
		buttons : { 
			"Abrir" : function() {
				$.ajax({ 
					url : url + "contas",
					type : 'POST',
					cache : false,
					data : {
								'numero' : $("#numeroMesaNovaConta").val(),
								'tipoconta' : $("#tipoConta").val()
					   		} 		
				}).done(function(result) {
					console.log(result);
					loadMesas();
				}).fail(function(result) {
					alert('erro');
				});	
				
				$(this).dialog("close");
			},
			Cancel : function() {
				$("#numeroMesaNovaConta").val("");
				$(this).dialog("close");
			}
		}
	});

	$("#novoPedido").click(function() {
		$("#pedidoSubItem tr:not(:first-child)").remove();
		$("#qtd").val("");
		$("#produto").val("");
		$("#obsnovopedido").val("");
		$("#dialog-novopedido").dialog("open");
	});
			
	$("#cancelarPedido").click(function() {
		$("#dialog-novopedido").dialog("close");
	});

	$("#salvarPedido").click(function (){
		var postData= new Object();
		postData["observacao"] = $("#obsnovopedido").val();
		postData["conta"] = $("#contaId").val();
		postData["numero"] = $("#numeroMesa").val();
		postData["usuario"] = "1";
	
		if($("#pedidoSubItem tr:not(:first-child)").size() != 0) {				
			
			$("#pedidoSubItem tr:not(:first-child)").each(function( index ) {	
				postData["subItens[" + index + "].subitem"] = $("td:nth-child(2)", $(this)).attr("idsubitem");
				postData["subItens[" + index + "].quantidade"] = $("td:nth-child(1)", $(this)).text();		
			});
	
			$.ajax({
				url : url + "pedidos",
				data : postData,
				type : 'POST',
				cache : false
			}).done(function(result) {
				alert('Pedido realizado com sucesso!');
				$("#dialog-novopedido").dialog("close");		
				// 	TODO: Atualizar as tabelas do dialog do histórico do pedido
			}).fail(function(result) {
				alert('erro');
			});
		
		} else {
			alert('É necessário incluir algum item!')
		} 
	});
			
	$("#addSubItem").click(function() {
	
			if ($("#produto").attr("idsubitem") == "") {
				alert('Selecione um produto');
			} else if ($("#qtd").val() == "") {
				alert('Informe uma quantidade');
			} else {
				var htmlTr = $('<tr />', {});
		
				var htmlTdQtd = $('<td />', { text : $("#qtd").val() });
				
				var htmlTdProduto = $('<td />', { idsubitem : $("#produto").attr("idsubitem"), text : $("#produto").val() });	
			
				var spanIcon = $('<span />', { class : 'ui-icon ui-icon-trash'});
				
				spanIcon.click(function(){						
					$(this).parent().parent().remove();
				});
			
				var htmlTdIcon = $('<td />', {});
				
				htmlTdIcon.append(spanIcon);
				htmlTr.append(htmlTdQtd);
				htmlTr.append(htmlTdProduto);
				htmlTr.append(htmlTdIcon);
			
				$("#pedidoSubItem").append(htmlTr);
				
				$("#qtd").val("");
				$("#produto").val("");
				$("#produto").attr("idsubitem", "");
			}	
	});

	$("#produto").autocomplete({
		source : function(request, response) {					
			$.ajax({
				url : url + "sub_itens/autocomplete/" + $("#produto").val(),
				dataType : "json",
				success : function(data) {
					response(data);
				}
			});	
		},
		minLength : 1,
		select : function(event, ui) {
			$(this).attr("idsubitem", ui.item.id);
		},
		search : function(event, ui) {
			$(this).attr("idsubitem", "");
		}
	});

	loadMesas();
});

function postChangeTable(conta, mesaDe, mesaPara) {

	$.ajax({
		url : url + "acoes_contas/mudarmesa/" + mesaPara,
		type : 'POST',
		cache : false,
		data : {
					'conta.id' : conta,
					'conta.numero' : mesaDe,
					'usuario' : '1'
		}
	}).done(function(result) {
		loadMesas();
	}).fail(function(result) {
		alert('erro');
	});
}


function postJoinTable() {

}


function loadMesas() {

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


function createMesa(obj) {

	var classMesa = "";
	
	if (obj.mesa.setor == "") {
		classMesa = "mesaVerde";
	} else {
		classMesa = "mesaVermelho";
	}

	var mesa = $('<div />', {
		class : classMesa,
		contaId : obj.mesa.setor.id	// Aqui está o id da conta
	});

	if (obj.mesa.setor != ""){
		mesa.draggable({
			opacity : 0.7,
			helper : "clone",
			containment : "#bodyMesas",
			scroll : false
		});
	}	

	var p = $('<p/>', { text : obj.mesa.numero });

	mesa.append(p);

	mesa.droppable({
		accept : ".mesaVermelho",
		drop : function(event, ui) {
			var attr = $(this).attr('contaId');
			if (typeof attr !== typeof undefined && attr !== false)
				postJoinTable();
			else {
				if (confirm("Deseja trocar a mesa da conta?"))
					postChangeTable(ui.draggable.attr("contaId"), ui.draggable
							.text(), $(this).text());
			}
		}
	});

	mesa.click(function() {
		var attr = $(this).attr('contaId');

		if (typeof attr !== typeof undefined && attr !== false) {
			$("#contaId").val(attr);
			$("#numeroMesa").val($(this).text());
			getHistoricoMesa(attr);
			$("#dialog-historicoconta").dialog("open");			
		} else {
			$("#numeroMesaNovaConta").val($(this).text());
			$("#dialog-abrirconta").dialog("open");
		}
	});

	$("#bodyMesas").append(mesa);
}



function getHistoricoMesa(conta) {
	
	$("#loader").show();
	
	$("#historicoPedido tr:not(:first-child)").remove();
	$("#pedidoAgrupado tr:not(:first-child)").remove();
	
	$.ajax({
		url : url + "pedidos_sub_itens/pedidosubitemgroupqtd/" + conta,
		dataType : "json",
		success : function(data) {			
			$.each( data, function( key, val ) {
				$("#pedidoAgrupado").append(
						$("<tr />").append(
								$("<td />", { text : val.pedidosubitem.quantidade})
						).append(
								$("<td />", { text : val.pedidosubitem.subItem.codigo})
						).append(
								$("<td />", { text : val.pedidosubitem.subItem.item.nome + " - " + val.pedidosubitem.subItem.nome})
						)
				);
			});
			
			$.ajax({
				url : url + "pedidos/pedidohistorico/" + conta,
				dataType : "json",
				success : function(data) {					
					console.log(data);
					$.each( data, function( key, val ) {
						
						var btnExcluir = $("<span />", { class : "ui-icon  ui-icon-trash", pedidoId : val.pedido.id});
						
						btnExcluir.click(function(){
							var pedidoId = $(this).attr("pedidoId");
							alert(pedidoId);
						});
						
						$("#historicoPedido").append(
								$("<tr />").append(
										$("<th />", { text : "P: " + val.pedido.id, colspan : "1", class : "agrupador"})
								).append(
										$("<th />", { text : "Obs: " + val.pedido.observacao, colspan : "2", class : "agrupador"})
								).append(
										$("<th />", { colspan : "1", class : "agrupador"}).append(btnExcluir)
								)
						);
						
						if(Array.isArray(val.pedido.subItens)){
							$.each( val.pedido.subItens, function( chave, valor) {

								var editarPedidoSubItem = $("<span />", { class : "ui-icon ui-icon-pencil", pedidoSubItem : valor.id});
								editarPedidoSubItem.click(function(){
									var pedidoSubItemId = $(this).attr("pedidoSubItem");
									alert(pedidoSubItemId);
								});
								
								var cancelarPedidoSubItem = $("<span />", { class : "ui-icon ui-icon-trash", pedidoSubItem : valor.id});
								cancelarPedidoSubItem.click(function(){
									var pedidoSubItemId = $(this).attr("pedidoSubItem");
									alert(pedidoSubItemId);
								});
								
								$("#historicoPedido").append(
								
									$("<tr />").append(
											$("<td />", {text : valor.quantidade})
									).append(
											$("<td />", {text : valor.subItem.item.nome + " - " + valor.subItem.nome})
									).append(
											$("<td />", {text : valor.status.descricao})
									).append(
											$("<td />").append(
													$("<div />", { style : "display: flex;" }).append(editarPedidoSubItem).append(cancelarPedidoSubItem)
											)
									)
								);
								
							});
						} else {
							
							var editarPedidoSubItem = $("<span />", { class : "ui-icon ui-icon-pencil", pedidoSubItem : val.pedido.subItens.id});
							editarPedidoSubItem.click(function(){
								var pedidoSubItemId = $(this).attr("pedidoSubItem");
								alert(pedidoSubItemId);
							});
							
							var cancelarPedidoSubItem = $("<span />", { class : "ui-icon ui-icon-trash", pedidoSubItem : val.pedido.subItens.id});
							cancelarPedidoSubItem.click(function(){
								var pedidoSubItemId = $(this).attr("pedidoSubItem");
								alert(pedidoSubItemId);
							});
							
							$("#historicoPedido").append(
								$("<tr />").append(
										$("<td />", {text : val.pedido.subItens.quantidade})
								).append(
										$("<td />", {text : val.pedido.subItens.subItem.item.nome + " - " + val.pedido.subItens.subItem.nome})
								).append(
										$("<td />", {text : val.pedido.subItens.status.descricao})
								).append(
										$("<td />").append(
												$("<div />", { style : "display: flex;" }).append(editarPedidoSubItem).append(cancelarPedidoSubItem)
										)
								)
							);
						}
					});
					
					$("#loader").hide();
				}
			});
		}
	});	
}
