/**
 * 
 */
$(document).ready(function() {
	$.ajaxSetup({cache : false});
	
	$("#loader").hide();	
	$("#msgLoading").hide();
	
	$.getScript( "../resources/templates/adm/mesas_pagamento.js");
	
	$("#dialog-novopedido").dialog({
		resizable : false,
		height : (($("#bodyMesas").height() - ($("#bodyMesas").height() * 0.20))),
		width : (($("#bodyMesas").width() - ($("#bodyMesas").width() * 0.70))),
		modal : true,
		autoOpen : false	
	});
	
	$("#dialog-mudarmesa").dialog({
		resizable : false,
		height : (($("#bodyMesas").height() - ($("#bodyMesas").height() * 0.80))),
		width : (($("#bodyMesas").width() - ($("#bodyMesas").width() * 0.90))),
		modal : true,
		autoOpen : false,
		buttons : { 
			"Mudar" : function() {				
				mudarMesaComsetor();
			},			
			Cancel : function() {
				$("#numeroMesaNovaConta").val("");
				$(this).dialog("close");
			}
		}
	});
	
	$("#dialog-reabrirconta").dialog({
		resizable : false,
		height : (($("#bodyMesas").height() - ($("#bodyMesas").height() * 0.20))),
		width : (($("#bodyMesas").width() - ($("#bodyMesas").width() * 0.70))),
		modal : true,
		autoOpen : false	
	});

	$("#dialog-historicoconta").dialog({
		resizable : false,
		height : ($("#bodyMesas").height() - 50),
		width : ($("#bodyMesas").width() - 500),
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
					loadMesas();
				}).fail(function(result) {
					alert('Erro, tente novamente mais tarde!');
				});	
				
				$(this).dialog("close");
			},
			"Reabrir" : function() {
				reabrirConta($("#numeroMesaNovaConta").val());				
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
	
	$("#mudarMesa").click(function() {
		$("#dialog-mudarmesa").dialog("open");
	});
			
	$("#cancelarPedido").click(function() {
		$("#dialog-novopedido").dialog("close");
	});
	
	$("#reabrir").click(function(){
		$("#loader").show();
		$(this).prop("disabled", true);
		
		if (confirm("Deseja reabrir a conta?")){
			$.ajax({
				url : url + "acoes_contas/reabrirconta/" + $("#acaoContaId").val(),
				data : { 'id' : $("#acaoContaId").val()},
				type : 'PUT',
				cache : false
			}).done(function(result) {
				alert('Conta reaberta!');
				$("#loader").hide();
				$("#reabrir").prop("disabled", false);
				$("#reabrir").hide();
				$("#novoPedido").show();
				$("#mudarMesa").show();
			}).fail(function(result) {
				alert('Erro, tente novamente mais tarde.');
				$("#loader").hide();
				$("#reabrir").prop("disabled", false);
			});	
		} else {
			$("#loader").hide();
			$(this).prop("disabled", false);
		}		
	});

	$("#salvarPedido").click(function (){
		
		$(this).prop("disabled", true);
		
		var postData = new Object();
		postData["observacao"] = $("#obsnovopedido").val();
		postData["conta"] = $("#contaId").val();
		postData["numero"] = $("#numeroMesa").val();
		postData["usuario"] = $("#usuarioId").val();
	
		if($("#pedidoSubItem tr:not(:first-child)").size() != 0) {				
			
			$("#pedidoSubItem tr:not(:first-child)").each(function( index ) {	
				postData["subItens[" + index + "].subitem"] = $("td:nth-child(2)", $(this)).attr("idsubitem");
				postData["subItens[" + index + "].quantidade"] = $("td:nth-child(1)", $(this)).text();				
				postData["subItens[" + index + "].finished"] = $("input", $("td:nth-child(3)", $(this))).prop("checked");
			});
	
			$.ajax({
				url : url + "pedidos",
				data : postData,
				type : 'POST',
				cache : false
			}).done(function(result) {
				alert('Pedido realizado com sucesso!');
				$("#salvarPedido").prop("disabled", false);
				$("#dialog-novopedido").dialog("close");		
				getHistoricoMesa($("#contaId").val());				
			}).fail(function(result) {
				alert('Erro, tente novamente mais tarde.');
				$("#salvarPedido").prop("disabled", false);
			});		
		} else {
			alert('É necessário incluir algum item!');
			$("#salvarPedido").prop("disabled", false);
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
				
				var htmlTdCheckImprimir = $('<td />', { idsubitem : $("#produto").attr("idsubitem") });
				var inputCheckImprimir = $('<input />', { type : "checkbox", idsubitem : $("#produto").attr("idsubitem"), name : "imprimir_" + $("#produto").attr("idsubitem"), id : "imprimir_" + $("#produto").attr("idsubitem") });
				
				htmlTdCheckImprimir.append(inputCheckImprimir);
			
				var spanIcon = $('<span />', { class : 'ui-icon ui-icon-trash'});
				
				spanIcon.click(function(){						
					$(this).parent().parent().remove();
				});
			
				var htmlTdIcon = $('<td />', {});
				
				htmlTdIcon.append(spanIcon);
				htmlTr.append(htmlTdQtd);
				htmlTr.append(htmlTdProduto);
				htmlTr.append(htmlTdCheckImprimir);
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
					'usuario' : $("#usuarioId").val()
		}
	}).done(function(result) {
		loadMesas();
		$("#dialog-mudarmesa").dialog("close");
		$("#dialog-historicoconta").dialog("close");
	}).fail(function(result) {
		alert('Erro, tente novamente mais tarde.');
	});
}

var end = 1;
var nowEndLoading = 0;
var error = false;
function endLoading(isError){
	if(!error || !isError){
		nowEndLoading++;
		
		if(nowEndLoading >= end){
			$("#loading").hide();
		}
	} else {
		$("#msgLoading").show();
		error = true;
	}
}

function postJoinTable(mesaOrigem, mesaDestino) {
	
	$.ajax({
		url : url + "acoes_contas/juntarmesa/origem/" + mesaOrigem + "/destino/" + mesaDestino + "/usuario/" + $("#usuarioId").val(),
		type : 'POST',
		cache : false,
		data : {
					'usuario' : $("#usuarioId").val()
		}
	}).done(function(result) {
		loadMesas();
		$("#dialog-mudarmesa").dialog("close");
		$("#dialog-historicoconta").dialog("close");
	}).fail(function(result) {
		alert('Erro, tente novamente mais tarde.');
	});
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
		
		endLoading(false);
	}).fail(function() {		
		endLoading(true);
	});
}

function createMesa(obj) {

	var classMesa = "";
	var qtdPessoa = 0;
	var qtdValor = 0;
	var amarelo = "";
	var acaoContaId;
	
	if (!obj.mesa.conta.hasOwnProperty("id")) {
		classMesa = "mesaVerde";		
	} else {
		classMesa = "mesaVermelho";
		qtdPessoa = obj.mesa.conta.qtdPessoa;
		if (typeof obj.mesa.conta.valor !== typeof undefined && obj.mesa.conta.valor !== false)
			qtdValor = obj.mesa.conta.valor;
		else {
			qtdValor = 0;
		}
		
		
		if (obj.mesa.conta.acaoConta.hasOwnProperty("id")) {
			amarelo = "background-color: #dce100;";
			acaoContaId = obj.mesa.conta.acaoConta.id;
		}
	}

	var mesa = $('<div />', {
		class : classMesa,
		style: amarelo,
		contaId : obj.mesa.conta.id,
		acaoContaId : acaoContaId,
		nMesa : obj.mesa.numero
	});

	if (obj.mesa.conta.hasOwnProperty("id")){
		mesa.draggable({
			opacity : 0.7,
			helper : "clone",
			containment : "#bodyMesas",
			scroll : false
		});
	}	

	var div = $('<div/>', { text : obj.mesa.numero, class : "numero" });

	mesa.append(div);
	
	var htmlImgPessoa = $('<img />',
			{ 
				id: 'icone_pessoa_' + obj.mesa.numero,
				src: '../resources/img/usuario-branco.png',
				align: 'middle',
				alt: 'Quantidade de pessoas',
				style: 'height: 13px; vertical-align: baseline; margin-left: 5px;'	
			});
	
	var htmlImgValor= $('<img />',
			{ 
				id: 'icone_pessoa_' + obj.mesa.numero,
				src: '../resources/img/cifrao-branco.png',
				align: 'middle',
				alt: 'Valor total',
				style: 'height: 15px; vertical-align: top; margin-left: 5px;'
					
			});
	
	var htmlQtdPessoa = $('<p/>', { text : qtdPessoa });
	htmlQtdPessoa.append(htmlImgPessoa);
	
	var htmlQtdValor = $('<p/>', { text : qtdValor });
	htmlQtdValor.append(htmlImgValor);
	
	var div3 = $('<div/>', {style : 'margin-right: 25%; text-align: right;'});
	var div4 = $('<div/>', {style : 'margin-right: 25%; text-align: right;'});
	
	var div2 = $('<div/>', {class : 'mesaDetalhe'});
	
	div3.append(htmlQtdPessoa);	
	div4.append(htmlQtdValor);

	div2.append(div3);
	div2.append(div4);
	
	mesa.append(div2);
	
	mesa.droppable({
		accept : ".mesaVermelho",
		drop : function(event, ui) {
			var attr = $(this).attr('contaId');
			if (typeof attr !== typeof undefined && attr !== false)
				if (confirm("Deseja unir as mesas?"))
					postJoinTable(ui.draggable.attr("nmesa"), $(this).attr("nmesa"));
			else {
				if (confirm("Deseja trocar a mesa da conta?"))
					postChangeTable(ui.draggable.attr("contaId"), ui.draggable.attr("nmesa"), $(this).attr("nmesa"));
			}
		}
	});

	mesa.click(function() {
		var attr = $(this).attr('contaId');

		if (typeof attr !== typeof undefined && attr !== false) {
			$("#contaId").val(attr);			
			$("#numeroMesa").val( $(this).attr('nMesa'));
		
			var acaoContaId = $(this).attr('acaoContaId');
			$("#acaoContaId").val(acaoContaId);
			
			if (typeof acaoContaId !== typeof undefined && acaoContaId !== false) {
				$("#novoPedido").hide();
				$("#mudarMesa").hide();
				$("#reabrir").show();
			} else {
				$("#novoPedido").show();
				$("#mudarMesa").show();
				$("#reabrir").hide();				
			}
			
			getHistoricoMesa(attr);
			getPagamentos();
			$("#dialog-historicoconta").dialog("open");			
		} else {
			$("#numeroMesaNovaConta").val($(this).attr('nMesa'));
			$("#dialog-abrirconta").dialog("open");
		}
	});

	$("#bodyMesas").append(mesa);
}

function getHistoricoMesa(conta) {
	
	$("#loader").show();
	
	$("#historicoPedido tr:not(:first-child)").remove();
	$("#pedidoAgrupado tr:not(:first-child)").remove();
		
	var tr;	
	$.ajax({
		url : url + "pedidos_sub_itens/pedidosubitemgroupqtd/" + conta,
		dataType : "json",
		success : function(data) {			
			$.each( data, function( key, val ) {
				
				tr = $("<tr />", {subItemId : val.pedidosubitem.subItem.id});
				
				tr.click(function(){
					loadTabelaAnalitica(this);
				});
				
				tr.append(
							$("<td />", { text : val.pedidosubitem.quantidade})
						).append(
							$("<td />", { text : val.pedidosubitem.subItem.codigo})
						).append(
							$("<td />", { text : val.pedidosubitem.subItem.item.nome + " - " + val.pedidosubitem.subItem.nome})
						);
				
				$("#pedidoAgrupado").append(tr);
			});
			
			loadHistoricoConta(conta);
		},
		fail : function(){
			$("#pedidoAgrupado").append(
					$("<tr />").append(
							$("<td />", { text : "Erro ao carregar os dados...", colspan : "4", style : "color : red;" })
					)
			);
		}
	});	
}

function createRowPSIHistorico(valor, pedidoID){
	
	var cancelarPedidoSubItem;
	if(valor.status.id != 4){
		cancelarPedidoSubItem = $("<span />", { class : "ui-icon ui-icon-trash", pedidoSubItem : valor.id, pedidoId : pedidoID});
		cancelarPedidoSubItem.click(function(){			
			$(this).hide();			
			if(confirm("Deseja cancelar o item?")){
				var pedidoId = $(this).attr("pedidoId");
				var pedidoSubItemId = $(this).attr("pedidoSubItem");
				$("#loader").show();
				
				$.ajax({
					url : url + "pedidos/cancelar/" + pedidoId,
					type : 'PUT',
					cache : false,
					data : { "usuario" : $("#usuarioId").val(), "id" : pedidoId, "subItens[0].id" : pedidoSubItemId }
				}).done(function(result) {
					getHistoricoMesa($("#contaId").val());
				}).fail(function(result) {
					alert('erro');
				});
			} else {
				$(this).show();
			}								
		});
	} else {
		cancelarPedidoSubItem = $("<span />"); 
	}

	$("#historicoPedido").append(
	
		$("<tr />").append(
				$("<td />", {text : valor.quantidade})
		).append(
				$("<td />", {text : valor.subItem.item.nome + " - " + valor.subItem.nome})
		).append(
				$("<td />", {text : valor.status.descricao})
		).append(
				$("<td />").append(						
						$("<div />", { style : "display: flex;" }).append(cancelarPedidoSubItem)
				)
		)
	);
}

function reabrirConta(mesa){
	
	$("#contasFechadas tr:not(:first-child)").remove();
	
	$.ajax({
		url : url + "contas/contafechada/" + mesa,
		dataType : "json",
		success : function(data) {
			
			if (data.length != 0) {	
				$.each( data, function( key, val ) {
					
					var btnReabrirConta;
					btnReabrirConta = $("<span />", { class : "ui-icon  ui-icon-arrowreturn-1-e", contaId : val.conta.id});						
					btnReabrirConta.click(function(){
						$(this).hide();
	
						if(confirm("Deseja reabrir a conta?")){
							var contaId = $(this).attr("ContaId");						
							$.ajax({
								url : url + "acoes_contas/reabrirconta/conta/" + contaId,
								type : 'PUT',
								cache : false,
								data : { "id" : contaId}
							}).done(function(result) {
								alert('Conta reaberta!');
								loadMesas();
							}).fail(function(result) {
								alert('Erro no sistema, tente novamente mais tarde!');
							});
						} else {
							$(this).show();
						}								
					});
	
					
					$("#contasFechadas").append(
							
							$("<tr />").append(
									$("<td />", {text : val.conta.id})
							).append(
									$("<td />", {text : val.conta.strDataFechamento})
							).append(
									$("<td />", {text : val.conta.valor})
							).append(
									$("<td />").append(						
											$("<div />", { style : "display: flex;" }).append(btnReabrirConta)
									)
							)
					);
	
				});
			} else {
				$("#contasFechadas").append(
						$("<tr />").append(
								$("<td />", { text : "Nenhum registro encontrado...", colspan : "4" })
						)
				);	
			}
			
			$("#dialog-reabrirconta").dialog("open");
		}
	});	
}

function loadHistoricoConta(conta){

	$.ajax({
		url : url + "pedidos/pedidohistorico/" + conta,
		dataType : "json",
		success : function(data) {					
			$.each( data, function( key, val ) {
				
				var btnExcluir;
				if(!val.pedido.cancelado){
					btnExcluir = $("<span />", { class : "ui-icon  ui-icon-trash", pedidoId : val.pedido.id});						
					btnExcluir.click(function(){								
						$(this).hide();
						
						if(confirm("Deseja cancelar o pedido?")){
							var pedidoId = $(this).attr("pedidoId");								
							$("#loader").show();
							
							$.ajax({
								url : url + "pedidos/cancelar/" + pedidoId,
								type : 'PUT',
								cache : false,
								data : { "usuario" : $("#usuarioId").val(), "id" : pedidoId}
							}).done(function(result) {
								getHistoricoMesa($("#contaId").val());
							}).fail(function(result) {
								alert('erro');
							});
						} else {
							$(this).show();
						}								
					});
				} else {
					btnExcluir = $("<span />");
				}
				
				$("#historicoPedido").append(
						$("<tr />").append(
								$("<th />", { text : "Mesa: " + val.pedido.acaoConta.numero, colspan : "1", class : "agrupador"})
						).append(
								$("<th />", { text : "Obs: " + val.pedido.observacao, colspan : "1", class : "agrupador"})
						).append(
								$("<th />", { text : "Horário: " + val.pedido.horarioSolicitacao, colspan : "1", class : "agrupador"})
						).append(
								$("<th />", { colspan : "1", class : "agrupador"}).append(btnExcluir)
						)
				);
				
				if(Array.isArray(val.pedido.subItens)){
					$.each( val.pedido.subItens, function( chave, valor) {
						createRowPSIHistorico(valor, val.pedido.id);
					});
				} else {
					createRowPSIHistorico(val.pedido.subItens, val.pedido.id);
				}
			});
			
			$("#loader").hide();
		},
		fail : function(){
			$("#historicoPedido").append(
					$("<tr />").append(
							$("<td />", { text : "Erro ao carregar os dados...", colspan : "4", style : "color : red;" })
					)
			);
		}
	});
}

function loadTabelaAnalitica(button){
	var extraStuff = {				   			
   			thisTr : $(button)
		}; 
   	
   	var callBackAjax = function(extraStuff) {
   	    return function(result) {
			$("#pedidoAgrupado tr[tranalitico='true']").remove();

			var tdAnalitico = $("<td />", { colspan : "3" });
			
			var tableAnalitico = $("<table />", { class : "reference" });
			tableAnalitico.append(
					$("<tr />").append(									
						$("<th />", {text : "Qtd"})
					).append(									
						$("<th />", {text : "Atendimento"})
					).append(									
						$("<th />", {text : "Garçom"})
					).append(									
						$("<th />", {text : "Status"})
					)
			);
			$.each( result, function( key, val ) {
				tableAnalitico.append(
						$("<tr />").append(									
							$("<td />", {text : val.pedidosubitem.quantidade})
						).append(									
							$("<td />", {text : val.pedidosubitem.log.strHorario})
						).append(									
							$("<td />", {text : val.pedidosubitem.log.usuario.nome})
						).append(									
							$("<td />", {text : val.pedidosubitem.status.descricao})
						)
				);
			});
			
			tdAnalitico.append(tableAnalitico);
			
			var trAnalitico = $("<tr />", {tranalitico : true, class : 'subTable'});
										
			trAnalitico.append(tdAnalitico);
			trAnalitico.hide();

			extraStuff.thisTr.after(trAnalitico);
			trAnalitico.show( "Blind" );
   	    };
   	};
   	
   	var callBackError = function(extraStuff) {
   	    return function(result) {
			$("#pedidoAgrupado tr[tranalitico='true']").remove();

			var tdAnalitico = $("<td />", { colspan : "3" });
			
			var tableAnalitico = $("<table />", { class : "reference" });
			tableAnalitico.append(
					$("<tr />").append(									
						$("<th />", {text : "Qtd"})
					).append(									
						$("<th />", {text : "Atendimento"})
					).append(									
						$("<th />", {text : "Garçom"})
					).append(									
						$("<th />", {text : "Status"})
					)
			);
			
			tableAnalitico.append(
					$("<tr />").append(
							$("<td />", { text : "Erro ao carregar os dados...", colspan : "4", style : "color : red;" })
					)
			);

			tdAnalitico.append(tableAnalitico);
			
			var trAnalitico = $("<tr />", {tranalitico : true, class : 'subTable'});
										
			trAnalitico.append(tdAnalitico);
			trAnalitico.hide();

			extraStuff.thisTr.after(trAnalitico);
			trAnalitico.show( "Blind" );
   	    };
   	};

	$.ajax({
		url : url + "pedidos_sub_itens/getbycontasubitem/conta/" + $("#contaId").val() + "/subitem/" + $(button).attr("subitemid"),
		type : 'GET',
		cache : false,						
	}).done(callBackAjax(extraStuff)).fail(callBackError(extraStuff));
}

function mudarMesaComsetor(){
	var de = $("#numeroMesa").val();
	var para = $("#novaMesa").val();
	var conta = $("#contaId").val();
	
	if(para != "") {
		postChangeTable(conta, de, para);		
	} else {
		alert('Digite uma mesa de destino.');
	}	 
}
