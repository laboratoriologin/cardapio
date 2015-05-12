/**
 * 
 */
$(document).ready(function() {
	
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
		$.each( result, function( key, val ) {
			$("#intervaloGarcom").append(
					$("<tr />").append(									
						$("<td />", {text : val.pausa.usuario.nome})
					).append(									
						$("<td />", {text : val.pausa.strHorarioInicial})
					).append(									
						$("<td />", {text : val.pausa.diffMinuto + " mim"})
					)
			);
		});
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
	});
}