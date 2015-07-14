/**
 * 
 */
var templateAlertaPedidoEntregue;
var templateAcao;
var templateDivLinhaSubItem;
var templateDivTable;
$(document).ready(function() {
	$.ajaxSetup({cache : false});
	startModalLoad(8);
	
	$("#maisitens2").hide();
	
	loadData();
});

function loadData(){
	
	var tempoAtualizacao = 60000;
	
	$.get('../resources/templates/pedido/divLinhaSubItem.xhtml', function(data) {
		templateDivLinhaSubItem = $(data);
		updateCompletedEventProgress();
	}).done(function() {
		$.get('../resources/templates/pedido/divTabela.xhtml', function(data) {
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
	
	$.get('../resources/templates/alertaacao.xhtml', function(data) {
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

	$.get('../resources/templates/alertapedidoentregue.xhtml', function(data) {
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

function getAcaoFecharConta(atualizaProgress){
	var heightDivTotal = $("#alertaAcaoFecharPedido").height() - $(".diviconetopoalerta").height();
	var heightDivParcial = 0;
	var heightDivPrevisao = 0;
	
	$("#maisitens").hide();
	$(".divalerta", $("#alertaAcaoFecharPedido")).remove();
	
	$.getJSON(url + "acoes_contas/acao/4", function(data) {
		var acaoConta;
		var alertaAcao;
		
		for (var i = 0; i < data.length; i++) {
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
	
	$("#maisitensfooter").hide();	
	$(".divalerta", $("#alertaSolicitarGarcom")).remove();
	
	$.getJSON(url + "acoes_contas/acao/3", function(data) {
		var acaoConta;
		var alertaAcao;
		for (var i = 0; i < data.length; i++) {
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
		
		for (var i = 0; i < data.length; i++) {			
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
				
				widthDivParcial += $("#alertalog_3_" + i).outerWidth(true);
				widthDivPrevisao = $("#alertalog_3_" + i).outerWidth(true);
			}
		}
		
		if(atualizaProgress)
			updateCompletedEventProgress();
		
	}).fail(function() {		
		updataMsgErro();
	});
}

function getPedidosNaoFinalizado(atualizaProgress){	
	$.getJSON(url + "pedidos/pedidosnaoconcluido/", function(data) {
		
		$(".colunaMaior").remove();	
		var divTotal = new DivTotal($("#pedidosAtuais"));
		var divColuna = new DivColuna(divTotal);
		var divItem = null;
		
		for (var i = 0; i < data.length; i++) {

			var pedido = data[i].pedido;
			
			var divItem = new DivItem(divColuna, pedido);
			if(Array.isArray(pedido.subItens)){
				for (var j = 0; j < pedido.subItens.length; j++) {
					var pedidoSubItem = pedido.subItens[j];
					var qtdRestante = pedido.subItens.length - j;
					var divSubItem = new DivSubItem(divItem, pedidoSubItem, qtdRestante);				
				}
			} else {
				var divSubItem = new DivSubItem(divItem, pedido.subItens, 0);
			}
			
			if(divColuna.isFull(divItem)){
				var divColuna = new DivColuna(divTotal);
				
				if(divColuna.html == null)
					//TODO: COlocar o simbolo de mais itens na tela
					break;
			}

			divColuna.append(divItem);
		}
		
		if(divItem != null)		
			divItem.clearTemp();
		
		if(atualizaProgress)
			updateCompletedEventProgress();
	}).fail(function() {
		updataMsgErro();
	});
}

function DivTotal(pHtml){
	
	this.html = pHtml;
	this.max = pHtml.width();
	
	this.total = 0; 
	this.previsao = 0;
	this.qtdColuna = 0;	
	
	this.addQtd = function(){
		this.qtdColuna++;
	};
	
	this.isFull = function(){
		if( (this.total + this.previsao) >= this.max)
			return true;				
		else if( this.total < this.max)
			return false;
		else
			return true;
	};
	
	this.append = function(divColuna){
		this.html.append(divColuna.html);
		this.total += $("#coluna_" + this.qtdColuna).width();
		this.previsao = $("#coluna_" + this.qtdColuna).width();
	};
}

function DivColuna(pDivTotal){
	
	this.divTotal = pDivTotal;
	
	this.max = 0;
	this.total = 0;
	this.qtdItem = 0;
	
	this.html = null;
	
	this.addQtd = function(){
		this.qtdItem++;
	};

	this.isFull = function(divItem) {
		return ( this.total + divItem.html.outerHeight() ) >= this.max;		
	};
	
	this.append = function(divItem){
		
		divItem.html.height(($("#divTamanhaReal", divItem.html).height() + divItem.margin));		
		this.html.append(divItem.html);
		divItem.clearTemp();
		this.total += divItem.html.outerHeight();		
	};

	if(!this.divTotal.isFull()) {
		this.divTotal.addQtd();
		this.html = jQuery('<div/>', { id: 'coluna_' + this.divTotal.qtdColuna, class: 'colunaMaior' });		
		this.divTotal.append(this);		
		this.max =  $("#coluna_" + this.divTotal.qtdColuna).height();		
	}
}

function DivItem(pDivColuna, pPedido){

	this.divColuna = pDivColuna;
	this.html = templateDivTable.clone();
	this.pedido = pPedido;
	this.margin = 20;
	
	this.total = 0;	
	this.qtdSubItem = 0;

	this.addQtd = function(){
		this.qtdSubItem++;
	};
	
	this.isFull = function() {
		return (this.total + this.margin) >= this.divColuna.max;		
	};
	
	this.clearTemp = function(){
		$("#tempoPedido").empty();
	};
	
	this.append = function(divSubItem, qtdRestante){
		
		if(this.isFull()) {
			var textoItem;
			if(qtdRestante == 1)
				textoItem = "+1 Item";
			else
				textoItem = "+" + qtdRestante  + " Item";
			
			$("#maisItemPedidospan", this.html).html(textoItem);
			$("#maisItemPedido", this.html).show();			
		} else {		
			$("#divLinhasSubItem" ,this.html).append(divSubItem.html);
			this.total += divSubItem.html.outerHeight();
		}
	};
	
	this.divColuna.addQtd();
	
	this.html.attr("id", "divPedido_" + this.divColuna.divTotal.qtdColuna + "_" + this.divColuna.qtdItem);
	
	$("#numeroMesa", this.html).html("Mesa " + this.pedido.conta.numero);	
	$("#maisItemPedido", this.html).hide();
	$("#tempoPedido").append(this.html);	
	
	this.total += $("#cabecalho", this.html).outerHeight();
}

function DivSubItem(pDivItem, pSubItemPedido, qtdRestante){
	this.divItem = pDivItem;
	this.subItemPedido = pSubItemPedido;
	this.html = templateDivLinhaSubItem.clone();
	
	this.divItem.addQtd();
	
	var qtdColuna = this.divItem.divColuna.divTotal.qtdColuna;
	var qtdItem = this.divItem.divColuna.qtdItem;
	var qtdSubItem = this.divItem.qtdSubItem;

	
	this.html.attr("id", "divRowSubItem_" + qtdColuna + "_" + qtdItem + "_" + qtdSubItem);
	$("#qtd", this.html).html(this.subItemPedido.quantidade);
	$("#descricao", this.html).html(this.subItemPedido.subItem.item.nome + " - " +  this.subItemPedido.subItem.nome);
	
	var text = "";
	var src = "";
	switch (this.subItemPedido.status.id) {
	    case 1:
	        text = "Pedente Validação";
	        src = "../resources/img/icone_confirmar_marrom.png";
	        break; 
	    case 2:
	        text = "Em produção";
	        src = "../resources/img/icone_preparo_marrom.png";
	        break;
	    default: 
	        text = "";
	    	src = "";
	};
	
	var htmlImg = $('<img />',
            { id: 'icone_' + qtdColuna + "_" + qtdItem + "_" + qtdSubItem,
              src: src,
              align: 'middle',
              alt: text
            }
	);
	
	$("#icone", this.html).append(htmlImg);
	this.divItem.append(this, qtdRestante);
}