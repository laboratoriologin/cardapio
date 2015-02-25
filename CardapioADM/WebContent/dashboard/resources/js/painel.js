/**
 * 
 */
var templateAlertaPedidoEntregue;
var templateAcao;
$(document).ready(function() {
	loadInit();
});

function loadInit(){
	
	startModalLoad(5);
	
	$("#maisitens2").hide();
	$("#maisitens").hide();
	$("#maisitensfooter").hide();
	
	loadData();
}

function getAcaoFecharConta(){
	var heightDivTotal = $("#alertaAcaoFecharPedido").height() - $(".diviconetopoalerta").height();
	var heightDivParcial = 0;
	var heightDivPrevisao = 0;
	
	$.getJSON(url + "acoes_contas/acao/4", function(data) {
		var acaoConta;
		var alertaAcao;
		for (i = 0; i < data.length; i++) {
			if( (heightDivParcial + heightDivPrevisao) >= heightDivTotal){
				$("#maisitensspan").html("+ " + (data.length - i) + " Itens");
				$("#maisitens").show();
				break;				
			}else if( heightDivParcial < heightDivTotal){			
				acaoConta = data[i];
				alertaAcao = templateAcao.clone();
				
				alertaAcao.attr("id", "acaoconta_4_" + i);
				$('#numero', alertaAcao).html(data[i].acaoconta.conta.numero);
				$("#alertaAcaoFecharPedido").append(alertaAcao);
	
				heightDivParcial += $("#acaoconta_4_" + i).height();
				heightDivPrevisao = $("#acaoconta_4_" + i).height();
			}
		}
		
		updateCompletedEventProgress();
	}).fail(function() {
		console.log("error");
		updataMsgErro();
	});
}

function getAcaoSolicitarGarcom(){
	var heightDivTotal = $("#alertaSolicitarGarcom").height() - $(".diviconetopoalerta").height();
	var heightDivParcial = 0;
	var heightDivPrevisao = 0;
	
	$.getJSON(url + "acoes_contas/acao/3", function(data) {
		var acaoConta;
		var alertaAcao;
		for (i = 0; i < data.length; i++) {
			if( (heightDivParcial + heightDivPrevisao) >= heightDivTotal){
				$("#maisitensfooterspan").html("+ " + (data.length - i) + " Itens");
				$("#maisitensfooter").show();
				break;				
			}else if( heightDivParcial < heightDivTotal){			
				acaoConta = data[i];
				alertaAcao = templateAcao.clone();
				
				alertaAcao.attr("id", "acaoconta_3_" + i);
				$('#numero', alertaAcao).html(data[i].acaoconta.conta.numero);
				$("#alertaSolicitarGarcom").append(alertaAcao);
	
				heightDivParcial += $("#acaoconta_3_" + i).height();
				heightDivPrevisao = $("#acaoconta_3_" + i).height();
			}
		}
		
		updateCompletedEventProgress();
	}).fail(function() {
		console.log("error");
		updataMsgErro();
	});
}

function getAlertaPedidoEntregue(){
	var widthDivTotal = $("#alertapedidoentregue").width();
	var widthDivParcial = 0;
	var widthDivPrevisao = 0;
	
	$.getJSON(url + "logs/status/3", function(data) {
		var log;
		var alertaLog;
		for (i = 0; i < data.length; i++) {
			if( (widthDivParcial + widthDivPrevisao) >= widthDivTotal){
				$("#maisitens2span").html("+ " + (data.length - i));
				$("#maisitens2").show();
				break;				
			}else if( widthDivParcial < widthDivTotal){			
				log = data[i].log;				
				alertaLog = templateAlertaPedidoEntregue.clone();				
				alertaLog.attr("id", "alertalog_3_" + i);
				$('#descricao', alertaLog).html(log.pedidoSubItem.subItem.item.nome + "-" + log.pedidoSubItem.subItem.nome);
				$('#numero', alertaLog).html(log.pedidoSubItem.pedido.conta.numero);
				$("#alertapedidoentregue").append(alertaLog);	
				widthDivParcial += $("#alertalog_3_" + i).width();
				widthDivPrevisao = $("#alertalog_3_" + i).width();
			}
		}
		
		updateCompletedEventProgress();
	}).fail(function() {
		console.log("error");
		updataMsgErro();
	});
}

function loadData(){
	
	$.get('resources/templates/alertaacao.xhtml', function(data) {
		templateAcao = $(data);
		updateCompletedEventProgress();
	}).done(function() {
		getAcaoFecharConta();
		getAcaoSolicitarGarcom();
	}).fail(function() {
		console.log( "error" );
		updataMsgErro();
	});

	$.get('resources/templates/alertapedidoentregue.xhtml', function(data) {
		templateAlertaPedidoEntregue = $(data);
		updateCompletedEventProgress();
	}).done(function() {
		getAlertaPedidoEntregue();
	}).fail(function() {
		console.log( "error" );
		updataMsgErro();
	});	
}
