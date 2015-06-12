/**
 * 
 */
var templateDivLinhaSubItem;
var templateDivTable;
var templateAlertaPedidoEntregue;
var isPaused = false;
var timerBufferKeyCode = null;
var createCodSender = 0;
var isCommit = false;

$(document).ready(function() {
	$.ajaxSetup({cache : false});
	jQuery.fn.exists = function(){return this.length>0;}

	$("#maisitens2").hide();
	
	loadInit();
	
	$(document).keydown(function(event) {		
		isPaused = true;
		
		if(timerBufferKeyCode == null){			
			timerBufferKeyCode = setTimeout(function(){				
				
				isPaused = false;				
				$("#codeSend").text("");				
				clearTimeout(timerBufferKeyCode);
				timerBufferKeyCode = null;
				console.log(timerBufferKeyCode);
				
			}, 2000);
		}
		
		if ( event.which != 13 ) {			
			var valor = String.fromCharCode((96 <= event.which && event.which <= 105) ? event.which-48 : event.which);
			$("#codeSend").append(valor);
		}
	});
	
	$(document).keypress(function( event ) {
		  if ( event.which == 13 ) {
			  isPaused = false;
			  clearTimeout(timerBufferKeyCode);
			  timerBufferKeyCode = null;
			  
			  divPedido = $("div[codesender='" + $("#codeSend").text() + "']");
			  			  
			  if(divPedido.exists() && !isCommit){				  
				  if(divPedido.attr("isPedido") == "true")
					  sendPedidoByCodeSender(divPedido);
				  else
					  sendPedidoSubItemByCodeSender(divPedido);
			  }
			  
			  $("#codeSend").text("");
		  }
	});
});

function loadInit(){
	startModalLoad(5);
	loadData();
}

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
				if(!isPaused)
					getPedidosNaoFinalizado(false);			
			}, 60000);
		}).fail(function() {
			updataMsgErro();
		});
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

function sendPedidoSubItemByCodeSender(div){
	isCommit = true;
	
   	$("div[class=circuloBranco]", div).hide();
   	$("div[class=circuloCinza]", div).hide();
    $("#icone", div).append("<div class='loaderCinza'>Loading...</div>");
       	
   	var extraStuff = {
   			divRow: div,
   	        imgHeader:  $("img", $("#cabecalho", $(div.parent()).parent()))
   	}; 
   	
   	var callBackError = function(extraStuff) {
   	    return function(data, textStatus, jqXHR) {
			$(".loaderCinza", extraStuff.div).remove();
			$("div[class=circuloBranco]", extraStuff.div).show();
			$("div[class=circuloCinza]", extraStuff.div).show();			
   	    };
   	};
	
	$.ajax({
		url: url + "pedidos/pedidopronto/usuario/" + $("#usuarioId").val() + "/pedidosubitem/" + div.attr('pedidosubitemid') + "/pedido/" + div.attr('pedidoidCs'),
		type: 'PUT'
	}).done(function (result){
		getPedidosNaoFinalizado(false);		
	}).fail(callBackError(extraStuff));	
}

function sendPedidoByCodeSender(div){
	isCommit = true;
	
   	$("div[class=circuloBranco]", div).hide();
   	$("div[class=circuloCinza]", div).hide();
	$("#icone", $("#cabecalho", div)).append("<div class='loaderBranco'>Loading...</div>");
	
   	var extraStuff = { pedidoDiv: div };   	
   	var callBackError = function(extraStuff) {
   	    return function(data, textStatus, jqXHR) {
   	    	$("div[class=circuloBranco]", extraStuff.div).show();
   	    	$("div[class=circuloCinza]", extraStuff.div).show();
   	    	$(".loaderBranco", $("#cabecalho", extraStuff.div)).remove();   	    	
   	    };
   	};
   	
	var pedidoSubItemId="";
	$("div[pedidoid='" + div.attr('pedidoidcs') + "']", $("#divLinhasSubItem",div)).each(function( index ) {		
		pedidoSubItemId += $(this).attr('id') + ",";
	});
	
	pedidoSubItemId = pedidoSubItemId.substring(0, (pedidoSubItemId.length - 1));
	
	$.ajax({
		url: url + "pedidos/pedidopronto/usuario/" + $("#usuarioId").val() + "/pedidosubitem/" + pedidoSubItemId + "/pedido/" + div.attr('pedidoidCs'),
		type: 'PUT'
	}).done(function (result){
		getPedidosNaoFinalizado(false);
		getAlertaPedidoEntregue(false);
	}).fail(callBackError(extraStuff));   
	
}


function getAlertaPedidoEntregue(atualizaProgress){
	var widthDivTotal = $("#alertapedidoentregue").width();
	var widthDivParcial = 0;
	var widthDivPrevisao = 0;
	
	$.getJSON(url + "logs/status/3/usuario/" + $("#usuarioId").val(), function(data) {
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
				$('#descricao', alertaLog).html(log.pedidoSubItem.quantidade + " " + log.pedidoSubItem.subItem.item.nome + "-" + log.pedidoSubItem.subItem.nome);
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
	$.getJSON(url + "pedidos/pedidosafazer/" + $("#usuarioId").val(), function(data) {
		$(".colunaMaior").remove();	
		var divTotal = new DivTotal($("#pedidosAtuais"));
		var divColuna = new DivColuna(divTotal);
		
		createCodSender = 0;
		
		isCommit = false;
		
		for (var i = 0; i < data.length; i++) {
			var pedido = data[i].pedido;
			var divItem = new DivItem(divColuna, pedido);
			
			if(pedido.observacao != ""){
				var divSubItem = new DivSubItem(divItem, pedido, 0, true);				
			}
						
			if(Array.isArray(pedido.subItens)){
				for (var j = 0; j < pedido.subItens.length; j++) {
					var pedidoSubItem = pedido.subItens[j];
					var qtdRestante = pedido.subItens.length - j;
					var divSubItem = new DivSubItem(divItem, pedidoSubItem, qtdRestante, false);				
				}
			} else {
				var divSubItem = new DivSubItem(divItem, pedido.subItens, 0, false);
			}
			
			if(divColuna.isFull(divItem)){
				var divColuna = new DivColuna(divTotal);
				
				if(divColuna.html == null)
					//TODO: COlocar o simbolo de mais itens na tela
					break;
			}

			divColuna.append(divItem);
		}
		
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
	};
};

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
	createCodSender++;
	
	this.html.attr("id", "divPedido_" + this.divColuna.divTotal.qtdColuna + "_" + this.divColuna.qtdItem);	
	this.html.attr("codesender", createCodSender);
	this.html.attr("pedidoIdCs", this.pedido.id);

	var htmlDiv = $('<div />',
             { id: this.pedido.id,	               
               align: 'middle',
               text: createCodSender,
               style: 'cursor: pointer;',
               class: 'circuloCinza'
             }
	);
	
    htmlDiv.click(function(){
      	
    	$("div[pedidoid='" + $(this).attr('id') + "']").hide();        	
       	$(this).hide();           	
       	var parent = $(this).parent();           	
       	parent.append("<div class='loaderBranco'>Loading...</div>");

    	var pedidoSubItemId="";
    	$("div[pedidoid='" + $(this).attr('id') + "']" ).each(function( index ) {
    		pedidoSubItemId += $(this).attr('id') + ",";
    	});
    	
    	pedidoSubItemId = pedidoSubItemId.substring(0, (pedidoSubItemId.length - 1));
    	
       	var extraStuff = {
       	        cabeca: $(this),
       	        parent: parent,
       	        subitens: $("div[pedidoid='" + $(this).attr('id') + "']")
   	    }; 
       	
       	var callBackError = function(extraStuff) {
       	    return function(data, textStatus, jqXHR) {
       	    	extraStuff.cabeca.show();
       	    	$(".loaderBranco", extraStuff.parent).remove();
       	    	extraStuff.subitens.show();
       	    };
       	};
   	
			$.ajax({
				url: url + "pedidos/pedidopronto/usuario/" + $("#usuarioId").val() + "/pedidosubitem/" + pedidoSubItemId + "/pedido/" + $(this).attr('id'),
				type: 'PUT'
			}).done(function (result){
				getPedidosNaoFinalizado(false);
			}).fail(callBackError(extraStuff));        	
    });
    
	var divColuna = jQuery('<div/>', {
	    id: 'icone'		    
	});		

	divColuna.append(htmlDiv);
	
	$("#numeroMesa", this.html).html("Mesa " + this.pedido.conta.numero);
	$("#cabecalho div:first-child", this.html).append(divColuna);
	
	$("#maisItemPedido", this.html).hide();
	$("#tempoPedido").append(this.html);	
	
	this.total += $("#cabecalho", this.html).outerHeight();
}

function DivSubItem(pDivItem, pSubItemPedido, qtdRestante, isObs){
	this.divItem = pDivItem;
	this.subItemPedido = pSubItemPedido;
	this.html = templateDivLinhaSubItem.clone();
	
	this.divItem.addQtd();
	
	var qtdColuna = this.divItem.divColuna.divTotal.qtdColuna;
	var qtdItem = this.divItem.divColuna.qtdItem;
	var qtdSubItem = this.divItem.qtdSubItem;
	
	this.html.attr("id", "divRowSubItem_" + qtdColuna + "_" + qtdItem + "_" + qtdSubItem);

	
	if(isObs){	
		this.html.attr("id", "divRowSubItem_" + qtdColuna + "_" + qtdItem + "_" + qtdSubItem + "_obs");
		this.html.css("color", "red");
		
		$("#qtd", this.html).html("");
		$("#descricao", this.html).html("Obs: " + this.subItemPedido.observacao);
		
	}else{
		createCodSender++;
		
		this.html.attr("id", "divRowSubItem_" + qtdColuna + "_" + qtdItem + "_" + qtdSubItem);
		this.html.attr("pedidosubitemid", "divRowSubItem_" + qtdColuna + "_" + qtdItem + "_" + qtdSubItem);		
		this.html.attr("codesender", createCodSender);
		this.html.attr("pedidoIdCs", this.divItem.pedido.id);
		this.html.attr("pedidosubitemid", this.subItemPedido.id);
		
		$("#qtd", this.html).html(this.subItemPedido.quantidade);
		$("#descricao", this.html).html(this.subItemPedido.subItem.item.nome + " - " +  this.subItemPedido.subItem.nome);

		var htmlDiv = $('<div />', { 
			id: this.subItemPedido.id,	   	            
            align: 'middle',
            text: createCodSender,
            pedidoid:  this.divItem.pedido.id,
            style: 'cursor: pointer;',
            class: 'circuloBranco'
        });
           
    	htmlDiv.click(function(){
           	$(this).hide();
            $(this).parent().append("<div class='loaderCinza'>Loading...</div>");           	
           	$("#" + $(this).attr("pedidoid")).hide();
           	
           	var extraStuff = {
           	        img: $(this),
           	        parent:  $(this).parent()
       	    }; 
           	
           	var callBackError = function(extraStuff) {
           	    return function(data, textStatus, jqXHR) {
					$(".loaderCinza", extraStuff.parent).remove();
					extraStuff.img.show();
					$("#" + extraStuff.img.attr("pedidoid")).show();
           	    };
           	};
       	
			$.ajax({
				url: url + "pedidos/pedidopronto/usuario/" + $("#usuarioId").val() + "/pedidosubitem/" + $(this).attr('id') + "/pedido/" + $(this).attr('pedidoid'),
				type: 'PUT'
			}).done(function (result){
				getPedidosNaoFinalizado(false);
			}).fail(callBackError(extraStuff));	
       });
           
    	$("#icone", this.html).append(htmlDiv);
	}
	
	this.divItem.append(this, qtdRestante);
}