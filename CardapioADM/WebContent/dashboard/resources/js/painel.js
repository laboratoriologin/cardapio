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

function getPedidosNaoFinalizado(){
	$.getJSON(url + "pedidos/pedidosnaoconcluido/", function(data) {
		var pedido;
		var divColuna = createDivColunaMaior();
		
		for (var i = 0; i < data.length; i++) {
			pedido = data[i];
			console.log(i);
			
			if(crateDivPedidos(pedido, false, divColuna) == "0"){
				console.log(divColuna);
				break;
			}
				
		}
		
		updateCompletedEventProgress();
	}).fail(function() {
		console.log("error");
		updataMsgErro();
	});
}


/*
 * Retornos:
 * 
 * 0 - break
 * 1 - sucesso
 * */
function crateDivPedidos(pedido, novaDivColunaMaior, divColuna){
	
	var divPedido;
	var divRowSubItem;
	var pedidoSubItem;
	
	if(novaDivColunaMaior){
		divColuna = createDivColunaMaior();
	}
	
	if(divColuna == false){
		console.log(divColuna);
		//Colocar o simbolo que exitem mais pedidos do que a tela possa imprimi
		return "0";
	}else{
		divPedido = createDivPedido(divColuna, pedido.pedido.conta.numero);
		
		if(divPedido == false){
			//console.log(divPedido);
			//crateDivPedidos(pedido, true, divColuna);
		}else{
			if(Array.isArray(pedido.pedido.subItens)){
				for (var j = 0; j < pedido.pedido.subItens.length; j++) {
					pedidoSubItem = pedido.pedido.subItens[j];				
					divRowSubItem = createRowSubItem(divColuna, divPedido, pedidoSubItem.quantidade, pedidoSubItem.subItem.item.nome + " - " +  pedidoSubItem.subItem.nome, "1");				
					if(divRowSubItem == false){
						//Colocar o simbolo que exitem mais pedidos do que a tela possa imprimi
						break;
					}
				}
			}else{
				pedidoSubItem = pedido.pedido.subItens;				
				divRowSubItem = createRowSubItem(divColuna, divPedido, pedidoSubItem.quantidade, pedidoSubItem.subItem.item.nome + " - " +  pedidoSubItem.subItem.nome, "1");				
				if(divRowSubItem == false){
					//Colocar o simbolo que exitem mais pedidos do que a tela possa imprimi
				}
			}
			
			$(divPedido).height(($("#divTamanhaReal", divPedido).height() + 20));
			return "1";
		}
	}
}

var widthDivColunaParcial = 0;
var widthDivColunaPrevisao = 0;
var qtdDivColuna = 0;
function createDivColunaMaior() {
	var widthDivColunaTotal = $("#pedidosAtuais").width();
	
	if( (widthDivColunaParcial + widthDivColunaPrevisao) >= widthDivColunaTotal){
		//alert("não cabe mais uma coluna");
		console.log("não cabe mais uma coluna");
		return false;				
	}else if( widthDivColunaParcial < widthDivColunaTotal){
		qtdDivColuna++;
		
		var divColuna = jQuery('<div/>', {
		    id: 'coluna_' + qtdDivColuna,
		    class: 'colunaMaior'		    
		});
		
		divColuna.appendTo('#pedidosAtuais');
		widthDivColunaParcial += $("#coluna_" + qtdDivColuna).width();
		widthDivColunaPrevisao = $("#coluna_" + qtdDivColuna).width();
		
		return divColuna;		
	}
}


var heightDivPedidoParcial = 0;
var heightDivPedidoPrevisao = 0;
var qtdDivPedido = 0;
function createDivPedido(divColuna, numeroMesa){
	var heightDivPedidoTotal= $("#pedidosAtuais").height();
	
	if( (heightDivPedidoParcial + heightDivPedidoPrevisao) >= heightDivPedidoTotal){
		//alert("não cabe mais pedido na coluna");
		console.log("não cabe mais pedido na coluna");
		return false;				
	}else if( heightDivPedidoParcial < heightDivPedidoTotal){
		qtdDivPedido++;
		
		var divPedido = templateDivTable.clone();
		divPedido.attr("id", "divPedido_" + qtdDivColuna + "_" + qtdDivPedido);
		$("#numeroMesa", divPedido).html(numeroMesa);
				

		divColuna.append(divPedido);
		heightDivPedidoParcial += $("#divPedido_" + qtdDivColuna + "_" + qtdDivPedido).height();
		heightDivPedidoPrevisao = $("#divPedido_" + qtdDivColuna + "_" + qtdDivPedido).height();
		
		return divPedido;		
	}
}

var heightDivRowSubItemParcial = 0;
var heightDivRowSubItemPrevisao = 0;
var qtdDivRowSubItem = 0;
function createRowSubItem(divColuna, divPedido, qtd, descricao, img){
	var heightDivRowSubItemTotal= divColuna.height() - $("#cabecalho").height();;
	
	if( (heightDivRowSubItemParcial + heightDivRowSubItemPrevisao) >= heightDivRowSubItemTotal){
		//alert("não cabe linha na tabela do pedido");
		console.log("não cabe linha na tabela do pedido");
		return false;				
	}else if( heightDivRowSubItemParcial < heightDivRowSubItemTotal){
		qtdDivRowSubItem++;
		
		var divRowSubItem = templateDivLinhaSubItem.clone();
		divRowSubItem.attr("id", "divRowSubItem_" + qtdDivColuna + "_" + qtdDivPedido + "_" + qtdDivRowSubItem);
		$("#qtd", divRowSubItem).html(qtd);
		$("#descricao", divRowSubItem).html(descricao);
		
		switch (img) {
		    case "1":
		        text = "Pedente Validação";
		        src = "resources/img/icone_confirmar_marrom.png"
		        break; 
		    case "2":
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
		heightDivRowSubItemParcial += $("#divRowSubItem_" + qtdDivColuna + "_" + qtdDivPedido + "_" + qtdDivRowSubItem).height();
		heightDivRowSubItemPrevisao = $("#divRowSubItem_" + qtdDivColuna + "_" + qtdDivPedido + "_" + qtdDivRowSubItem).height();
		
		return divRowSubItem;		
	}
}


function loadData(){
	
	$.get('resources/templates/pedido/divLinhaSubItem.xhtml', function(data) {
		templateDivLinhaSubItem = $(data);
		updateCompletedEventProgress();
	}).done(function() {
		$.get('resources/templates/pedido/divTabela.xhtml', function(data) {
			templateDivTable = $(data);
			updateCompletedEventProgress();
		}).done(function() {
			getPedidosNaoFinalizado();
		}).fail(function() {
			console.log("error");
			updataMsgErro();
		});
	}).fail(function() {
		console.log("error");
		updataMsgErro();
	});
	
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
