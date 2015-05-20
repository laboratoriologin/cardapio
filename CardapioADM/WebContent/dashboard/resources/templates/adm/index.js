/**
 * 
 */
$(document).ready(function() {
	$.ajaxSetup({cache : false});
	
	$(document).tooltip({ track: true });
	
	loadTableItervaloFuncionario();
	loadTableFecharConta();
	loadTableCozinha();
	loadTableBar();
});


function loadTableItervaloFuncionario(){
	
	$.ajax({
		url : url + "pausas/getempausa",
		type : 'GET',
		cache : false,						
	}).done(function (result){
		$("#intervaloGarcom tr:not(:first-child)").remove();
		
		if (result.length != 0) {	
			$.each( result, function( key, val ) {
				
				var divIcons = $('<div />', { style : 'display: inline-flex;'});
				var spanIconFinalPausa = $('<span />', { class : 'ui-icon ui-icon-play', title : 'Finalizar a pausa', id : val.pausa.id});
				
				spanIconFinalPausa.click(function(event) {
					$.ajax({
						url : url + "pausas/fecharpausa/pausa/" + $(this).attr("id"),
						data : {},
						type : 'PUT',
						cache : false
					}).done(function(result) {						
						alert('Operação realizada com sucesso!');
						$("#loader").hide();
					}).fail(function(result) {
						alert('erro');
						$("#loader").hide();
					});					
				});
				
				divIcons.append(spanIconFinalPausa);
				
				$("#intervaloGarcom").append(
						$("<tr />").append(									
							$("<td />", {text : val.pausa.usuario.nome})
						).append(									
							$("<td />", {text : val.pausa.strHorarioInicial})
						).append(									
							$("<td />", {text : val.pausa.diffMinuto + " mim"})
						).append(									
							$("<td />").append(divIcons)
						)
				);
			});
		} else {
			$("#intervaloGarcom").append(
					$("<tr />").append(
							$("<td />", { text : "Nenhum registro encontrado...", colspan : "4" })
					)
			);	
		}
	}).fail(function(result) {
		alert('erro');
	});	
}

function loadTableFecharConta(){
	
	$.ajax({
		url : url + "acoes_contas/getsolicitacaofecharconta",
		type : 'GET',
		cache : false,						
	}).done(function (result){
		$("#fecharConta tr:not(:first-child)").remove();
		
		if (result.length != 0) {		
			$.each( result, function( key, val ) {
				$("#fecharConta").append(
						$("<tr />", { contaId : val.acaoconta.conta.id}).append(									
							$("<td />", {text : val.acaoconta.conta.numero})
						).append(									
							$("<td />", {text : val.acaoconta.conta.setor.descricao})
						).append(									
							$("<td />", {text : val.acaoconta.strHorarioSolicitacao})
						).append(									
							$("<td />", {text : val.acaoconta.strDifMinuto + " mim"})
						)
				);
			});
		} else {
			$("#fecharConta").append(
					$("<tr />").append(
							$("<td />", { text : "Nenhum registro encontrado...", colspan : "4" })
					)
			);
		}
	}).fail(function(result) {
		alert('erro');
	});	
}

function loadTableCozinha(){
	$.ajax({
		url : url + "pedidos/pedidosafazer/area/1",
		type : 'GET',
		cache : false,						
	}).done(function (result){
		$("#cozinha tr:not(:first-child)").remove();
		loadPedidoAFazer($("#cozinha"), result);
	}).fail(function(result) {
		alert('erro');
	});	
}

function loadTableBar(){
	$.ajax({
		url : url + "pedidos/pedidosafazer/area/2",
		type : 'GET',
		cache : false,						
	}).done(function (result){
		$("#bar tr:not(:first-child)").remove();
		loadPedidoAFazer($("#bar"), result);
	}).fail(function(result) {
		alert('erro');
	});	
}

function loadPedidoAFazer(table, data){
	
	if (data.length != 0) {
		$.each( data, function( key, val ) {
			table.append(
					$("<tr />").append(
							$("<th />", { text : "P: " + val.pedido.id, colspan : "1", class : "agrupador"})
					).append(
							$("<th />", { text : "Obs: " + val.pedido.observacao, colspan : "1", class : "agrupador"})
					).append(
							$("<th />", { text : "Horário: " + val.pedido.horarioSolicitacao, colspan : "1", class : "agrupador"})
					)
			);
			
			if(Array.isArray(val.pedido.subItens)){
				$.each( val.pedido.subItens, function( chave, valor) {
					createRowPedidoSubItem(table, valor);
				});
			} else {
				createRowPedidoSubItem(table, val.pedido.subItens);
			}
		});
	} else {
		table.append(
				$("<tr />").append(
						$("<td />", { text : "Nenhum registro encontrado...", colspan : "3" })
				)
		);
	}	
}

function createRowPedidoSubItem(table, valor){

	table.append(
	
		$("<tr />").append(
				$("<td />", {text : valor.quantidade})
		).append(
				$("<td />", {text : valor.subItem.item.nome + " - " + valor.subItem.nome})
		).append(
				$("<td />", {text : valor.status.descricao})
		)
	);
}