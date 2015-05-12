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
	startModalLoad(3);
	loadData();
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

var divColuna;
function getPedidosNaoFinalizado(atualizaProgress){
	$.getJSON(url + "pedidos/pedidosafazer/" + $("#usuarioId").val(), function(data) {
		var pedido = null;
		
		consumoWidthDivPedidoAtuais = 0;
		previsaoConsumoWidthDivPedidoAtuais = 0;
		qtdDivColuna = 0;
		consumoHeightDivColuna2 = 0;
		qtdDivPedido = 0;	
		consumoHeightDivColuna = 0;
		previsaoConsumoHeightDivColuna = 0;
		qtdDivRowSubItem = 0;
		createCodSender = 0;
		
		$(".colunaMaior").remove();
		
		divColuna = createDivColunaMaior();
		
		for (var i = 0; i < data.length; i++) {
			pedido = data[i];			
			if(crateDivPedidos(pedido) == 0)
				//Colocar o simbolo que exitem mais pedidos do que a tela possa imprimi
				break;			
		}
		
		isCommit = false;

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
		var divPedido = createDivPedido(pedido.pedido.id, "Mesa " + pedido.pedido.conta.numero);
		
		if(pedido.pedido.observacao != ""){
			createRowSubItem(divPedido, "Obs:", pedido.pedido.observacao, 1, 1, pedido.pedido.id, true);
		}
		
			if(Array.isArray(pedido.pedido.subItens)){
				for (var j = 0; j < pedido.pedido.subItens.length; j++) {
					pedidoSubItem = pedido.pedido.subItens[j];				
					divRowSubItem = createRowSubItem(divPedido, pedidoSubItem.quantidade, pedidoSubItem.subItem.item.nome + " - " +  pedidoSubItem.subItem.nome, pedidoSubItem.status.id, pedidoSubItem.id, pedido.pedido.id, false);				
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
				divRowSubItem = createRowSubItem(divPedido, pedidoSubItem.quantidade, pedidoSubItem.subItem.item.nome + " - " +  pedidoSubItem.subItem.nome, pedidoSubItem.status.id, pedidoSubItem.id, pedido.pedido.id, false);				
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
function createDivPedido(id, numeroMesa){
		qtdDivPedido++;

		var divPedido = templateDivTable.clone();
		divPedido.attr("id", "divPedido_" + qtdDivColuna + "_" + qtdDivPedido);
		createCodSender++;
		divPedido.attr("codesender", createCodSender);
		divPedido.attr("pedidoIdCs", id);
		
		$("#numeroMesa", divPedido).html(numeroMesa);
		
        text = "Pedente Validação";
        src = "../resources/img/icone_confirmar_marrom.png"
		
		var htmlDiv = $('<div />',
	             { id: id,	               
	               align: 'middle',
	               text: createCodSender,
	               style: 'cursor: pointer;',
	               class: 'circuloCinza'
	             });
        
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
		
		$("#cabecalho div:first-child", divPedido).append(divColuna);		
		$("#maisItemPedido", divPedido).hide();
		$("#tempoPedido").append(divPedido);
		
		return divPedido;
}

var consumoHeightDivColuna = 0;
var previsaoConsumoHeightDivColuna = 0;
var qtdDivRowSubItem = 0;
function createRowSubItem(divPedido, qtd, descricao, img, id, pedidoId, isObs){
	var maxHeightDivColuna = divColuna.height() - $("#cabecalho", divPedido).height();
	
	if( (consumoHeightDivColuna + previsaoConsumoHeightDivColuna) >= maxHeightDivColuna){		
		return false;				
	}else if( consumoHeightDivColuna < maxHeightDivColuna){
		var divRowSubItem = templateDivLinhaSubItem.clone();
		
		if(isObs){	
			divRowSubItem.attr("id", "divRowSubItem_" + qtdDivColuna + "_" + qtdDivPedido + "_obs");		
			divRowSubItem.css("color", "red");
			
			$("#qtd", divRowSubItem).html("");
			$("#descricao", divRowSubItem).html(qtd + " " + descricao);
			
		}else{
			qtdDivRowSubItem++;
			divRowSubItem.attr("id", "divRowSubItem_" + qtdDivColuna + "_" + qtdDivPedido + "_" + qtdDivRowSubItem);
			divRowSubItem.attr("pedidosubitemid", "divRowSubItem_" + qtdDivColuna + "_" + qtdDivPedido + "_" + qtdDivRowSubItem);
			createCodSender++;
			divRowSubItem.attr("codesender", createCodSender);
			divRowSubItem.attr("pedidoIdCs", pedidoId);
			divRowSubItem.attr("pedidosubitemid", id);
			
			$("#qtd", divRowSubItem).html(qtd);
			$("#descricao", divRowSubItem).html(descricao);
		}
		
		
		if(!isObs){		
	        text = "Pedente Validação";
	        src = "../resources/img/icone_confirmar_marrom.png"
		
	    		var htmlDiv = $('<div />', { 
	    			id: id,	   	            
	   	            align: 'middle',
	   	            text: createCodSender,
	   	            pedidoid: pedidoId,
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
	           
			$("#icone", divRowSubItem).append(htmlDiv);
		}
				

		$("#divLinhasSubItem", divPedido).append(divRowSubItem);
		consumoHeightDivColuna += divRowSubItem.height();
		previsaoConsumoHeightDivColuna = divRowSubItem.height();
		
		return divRowSubItem;		
	}
}

function getAlertaPedidoEntregue(atualizaProgress){
	var widthDivTotal = $("#alertapedidoentregue").width();
	var widthDivParcial = 0;
	var widthDivPrevisao = 0;
	
	$.getJSON(url + "logs/status/3/usuario/" + $("#usuarioId").val(), function(data) {
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
				$('#descricao', alertaLog).html(log.pedidoSubItem.quantidade + " " + log.pedidoSubItem.subItem.item.nome + "-" + log.pedidoSubItem.subItem.nome);
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
				//if(!isPaused)
					//getPedidosNaoFinalizado(false);			
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
