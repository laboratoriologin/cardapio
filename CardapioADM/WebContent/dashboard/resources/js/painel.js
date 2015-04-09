/**
 * 
 */
var templateAlertaPedidoEntregue;
var templateAcao;
var templateDivLinhaSubItem;
var templateDivTable;
$(document).ready(function() {
	loadInit();
});

function loadInit(){
	
	startModalLoad(8);
	
	$("#maisitens2").hide();
	$("#maisitens").hide();
	$("#maisitensfooter").hide();
	
	loadData();	
}

function getAcaoFecharConta(atualizaProgress){
	var heightDivTotal = $("#alertaAcaoFecharPedido").height() - $(".diviconetopoalerta").height();
	var heightDivParcial = 0;
	var heightDivPrevisao = 0;
	
	$.getJSON(url + "acoes_contas/acao/4", function(data) {
		var acaoConta;
		var alertaAcao;
		
		$(".divalerta", $("#alertaAcaoFecharPedido")).remove();
		
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
		
		if(atualizaProgress)
			updateCompletedEventProgress();
	}).fail(function() {		
		updataMsgErro();
	});
}

function getAcaoSolicitarGarcom(atualizaProgress){
	var heightDivTotal = $("#alertaSolicitarGarcom").height() - $(".diviconetopoalerta").height();
	var heightDivParcial = 0;
	var heightDivPrevisao = 0;
	
	$(".divalerta", $("#alertaSolicitarGarcom")).remove();
	
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
		
		if(atualizaProgress)
			updateCompletedEventProgress();
	}).fail(function() {		
		updataMsgErro();
	});
}

function getAlertaPedidoEntregue(atualizaProgress){
	var widthDivTotal = $("#alertapedidoentregue").width();
	var widthDivParcial = 0;
	var widthDivPrevisao = 0;
	
	$.getJSON(url + "logs/status/3", function(data) {
		var log;
		var alertaLog;
		
		$(".alertapedidoentrega1").remove();
		
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
		
		if(atualizaProgress)
			updateCompletedEventProgress();
		
	}).fail(function() {		
		updataMsgErro();
	});
}

var divColuna;
function getPedidosNaoFinalizado(atualizaProgress){
	$.getJSON(url + "pedidos/pedidosnaoconcluido/", function(data) {
		var pedido = null;
		
		consumoWidthDivPedidoAtuais = 0;
		previsaoConsumoWidthDivPedidoAtuais = 0;
		qtdDivColuna = 0;
		consumoHeightDivColuna2 = 0;
		qtdDivPedido = 0;	
		consumoHeightDivColuna = 0;
		previsaoConsumoHeightDivColuna = 0;
		qtdDivRowSubItem = 0;
		
		$(".colunaMaior").remove();
		
		divColuna = createDivColunaMaior();
		
		for (var i = 0; i < data.length; i++) {
			pedido = data[i];			
			if(crateDivPedidos(pedido) == 0)
				//Colocar o simbolo que exitem mais pedidos do que a tela possa imprimi
				break;			
		}

		if(atualizaProgress)
			updateCompletedEventProgress();
	}).fail(function() {
		updataMsgErro();
	});
}


/*
 * Retornos:
 * 
 * 0 - break
 * 1 - sucesso
 * */
function crateDivPedidos(pedido){
	
	if(divColuna == false){
		return 0;
	}else{
		var divPedido = createDivPedido("Mesa " + pedido.pedido.conta.numero);
		
			if(Array.isArray(pedido.pedido.subItens)){
				for (var j = 0; j < pedido.pedido.subItens.length; j++) {
					pedidoSubItem = pedido.pedido.subItens[j];				
					divRowSubItem = createRowSubItem(divPedido, pedidoSubItem.quantidade, pedidoSubItem.subItem.item.nome + " - " +  pedidoSubItem.subItem.nome, pedidoSubItem.status.id);				
					if(divRowSubItem == false){
						//Colocar o simbolo que exitem mais pedidos do que a tela possa imprimi
						var qtdRestante = pedido.pedido.subItens.length - j;
						var textoItem;

						if(qtdRestante == 1)
							textoItem = "+1 Item";
						else
							textoItem = "+" + qtdRestante  + " Item";						
						
						$("#maisItemPedidospan", divPedido).html(textoItem);
						$("#maisItemPedido", divPedido).show();
						break;
					}
				}
			}else{
				pedidoSubItem = pedido.pedido.subItens;				
				divRowSubItem = createRowSubItem(divPedido, pedidoSubItem.quantidade, pedidoSubItem.subItem.item.nome + " - " +  pedidoSubItem.subItem.nome, pedidoSubItem.status.id);				
			}
			
			divPedido.height(($("#divTamanhaReal", divPedido).height() + 20));
			
			if( (divPedido.height() + consumoHeightDivColuna2) >= divColuna.height()){
				consumoHeightDivColuna2 = 0;
				consumoHeightDivColuna = 0;
				previsaoConsumoHeightDivColuna = 0;	
				divColuna = createDivColunaMaior();
				
				if(divColuna == false){				
					return 0;
				}else{
					divColuna.append(divPedido);
					$("#tempoPedido").empty();
					
					consumoHeightDivColuna2 += $(divPedido).height();
				}
			}else{
				divColuna.append(divPedido);
				$("#tempoPedido").empty();
				
				consumoHeightDivColuna2 += $(divPedido).height();			
			}
	}
	
	return 1;
}

var consumoWidthDivPedidoAtuais = 0;
var previsaoConsumoWidthDivPedidoAtuais = 0;
var qtdDivColuna = 0;
function createDivColunaMaior() {
	var maxWidthDivPedidosAtuais = $("#pedidosAtuais").width();
	
	if( (consumoWidthDivPedidoAtuais + previsaoConsumoWidthDivPedidoAtuais) >= maxWidthDivPedidosAtuais){
		return false;				
	}else if( consumoWidthDivPedidoAtuais < maxWidthDivPedidosAtuais){
		qtdDivColuna++;
		
		var divColuna = jQuery('<div/>', {
		    id: 'coluna_' + qtdDivColuna,
		    class: 'colunaMaior'		    
		});
		
		divColuna.appendTo('#pedidosAtuais');
		consumoWidthDivPedidoAtuais += $("#coluna_" + qtdDivColuna).width();
		previsaoConsumoWidthDivPedidoAtuais = $("#coluna_" + qtdDivColuna).width();
		
		return divColuna;		
	}
}

var consumoHeightDivColuna2 = 0;
var qtdDivPedido = 0;
function createDivPedido(numeroMesa){
		qtdDivPedido++;

		var divPedido = templateDivTable.clone();
		divPedido.attr("id", "divPedido_" + qtdDivColuna + "_" + qtdDivPedido);
		$("#numeroMesa", divPedido).html(numeroMesa);
		
		$("#maisItemPedido", divPedido).hide();

		$("#tempoPedido").append(divPedido);
		
		return divPedido;		

}

var consumoHeightDivColuna = 0;
var previsaoConsumoHeightDivColuna = 0;
var qtdDivRowSubItem = 0;
function createRowSubItem(divPedido, qtd, descricao, img){
	var maxHeightDivColuna = divColuna.height() - $("#cabecalho", divPedido).height();
	
	if( (consumoHeightDivColuna + previsaoConsumoHeightDivColuna) >= maxHeightDivColuna){		
		return false;				
	}else if( consumoHeightDivColuna < maxHeightDivColuna){
		qtdDivRowSubItem++;
		
		var divRowSubItem = templateDivLinhaSubItem.clone();
		divRowSubItem.attr("id", "divRowSubItem_" + qtdDivColuna + "_" + qtdDivPedido + "_" + qtdDivRowSubItem);
		$("#qtd", divRowSubItem).html(qtd);
		$("#descricao", divRowSubItem).html(descricao);
		
		switch (img) {
		    case 1:
		        text = "Pedente Validação";
		        src = "resources/img/icone_confirmar_marrom.png"
		        break; 
		    case 2:
		        text = "Em produção";
		        src = "resources/img/icone_preparo_marrom.png"
		        break;
		    default: 
		        text = "";
		    	src = "";
		}
		
		var htmlImg = $('<img />',
	             { id: 'icone_' + qtdDivColuna + "_" + qtdDivPedido + "_" + qtdDivRowSubItem,
	               src: src,
	               align: 'middle',
	               alt: text
	             });
		
		$("#icone", divRowSubItem).append(htmlImg);
				

		$("#divLinhasSubItem", divPedido).append(divRowSubItem);
		consumoHeightDivColuna += $("#divRowSubItem_" + qtdDivColuna + "_" + qtdDivPedido + "_" + qtdDivRowSubItem).height();
		previsaoConsumoHeightDivColuna = $("#divRowSubItem_" + qtdDivColuna + "_" + qtdDivPedido + "_" + qtdDivRowSubItem).height();
		
		return divRowSubItem;		
	}
}

function loadData(){
	
	var tempoAtualizacao = 60000;
	
	$.get('resources/templates/pedido/divLinhaSubItem.xhtml', function(data) {
		templateDivLinhaSubItem = $(data);
		updateCompletedEventProgress();
	}).done(function() {
		$.get('resources/templates/pedido/divTabela.xhtml', function(data) {
			templateDivTable = $(data);
			updateCompletedEventProgress();
		}).done(function() {			
			getPedidosNaoFinalizado(true);
			setInterval(function() {
				getPedidosNaoFinalizado(false);			
			}, 60000);
		}).fail(function() {
			updataMsgErro();
		});
	}).fail(function() {
		updataMsgErro();
	});
	
	$.get('resources/templates/alertaacao.xhtml', function(data) {
		templateAcao = $(data);
		updateCompletedEventProgress();
	}).done(function() {
		getAcaoFecharConta(true);
		setInterval(function() {
			getAcaoFecharConta(false);			
		}, tempoAtualizacao);
		
		getAcaoSolicitarGarcom(true);
		setInterval(function() {
			getAcaoSolicitarGarcom(false);			
		}, tempoAtualizacao);
		
	}).fail(function() {
		updataMsgErro();
	});

	$.get('resources/templates/alertapedidoentregue.xhtml', function(data) {
		templateAlertaPedidoEntregue = $(data);
		updateCompletedEventProgress();
	}).done(function() {
		getAlertaPedidoEntregue(true);		
		setInterval(function() {
			getAlertaPedidoEntregue(false);			
		}, tempoAtualizacao);		
	}).fail(function() {
		updataMsgErro();
	});	
}
