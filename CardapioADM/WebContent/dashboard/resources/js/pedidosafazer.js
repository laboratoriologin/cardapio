/**
 * 
 */
var templateDivLinhaSubItem;
var templateDivTable;
$(document).ready(function() {
	loadInit();
});

function loadInit(){
	startModalLoad(3);
	loadData();
}

var divColuna;
function getPedidosNaoFinalizado(atualizaProgress){
	$.getJSON(url + "pedidos/pedidosafazer/2", function(data) {
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
		var divPedido = createDivPedido(pedido.pedido.id, "Mesa " + pedido.pedido.conta.numero);
		
			if(Array.isArray(pedido.pedido.subItens)){
				for (var j = 0; j < pedido.pedido.subItens.length; j++) {
					pedidoSubItem = pedido.pedido.subItens[j];				
					divRowSubItem = createRowSubItem(divPedido, pedidoSubItem.quantidade, pedidoSubItem.subItem.item.nome + " - " +  pedidoSubItem.subItem.nome, pedidoSubItem.status.id, pedidoSubItem.id, pedido.pedido.id);				
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
				divRowSubItem = createRowSubItem(divPedido, pedidoSubItem.quantidade, pedidoSubItem.subItem.item.nome + " - " +  pedidoSubItem.subItem.nome, pedidoSubItem.status.id, pedidoSubItem.id, pedido.pedido.id);				
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
		$("#numeroMesa", divPedido).html(numeroMesa);
		
        text = "Pedente Validação";
        src = "resources/img/icone_confirmar_marrom.png"
		
		var htmlImg = $('<img />',
	             { id: id,
	               src: src,
	               align: 'middle',
	               alt: text,
	               style: 'cursor: pointer;'
	             });
        
        htmlImg.click(function(){
        	//alert("Teste click  id: " + $(this).attr('id'));
        	
        	$("img[pedidoid='" + $(this).attr('id') + "']").hide();        	
           	$(this).hide();           	
           	var parent = $(this).parent();           	
           	parent.append("<div class='loaderBranco'>Loading...</div>");

        	var pedidoSubItemId;
        	$("img[pedidoid='" + $(this).attr('id') + "']" ).each(function( index ) {
        		pedidoSubItemId = $(this).attr('id') + ",";
        	});
        	
        	pedidoSubItemId = pedidoSubItemId.substring(0, (pedidoSubItemId.length - 1));
        	
           	var extraStuff = {
           	        cabeca: $(this),
           	        parent: parent,
           	        subitens: $("img[pedidoid='" + $(this).attr('id') + "']")
           	    }; 
           	
           	var callBackError = function(extraStuff) {
           	    return function(data, textStatus, jqXHR) {
           	    	extraStuff.cabeca.show();
           	    	$(".loaderBranco", extraStuff.parent).remove();
           	    	extraStuff.subitens.show();
           	    };
           	};
       	
				$.ajax({
					url: url + "pedidos/pedidopronto/usuario/1/pedidosubitem/" + pedidoSubItemId + "/pedido/" + $(this).attr('id'),
					type: 'PUT'
				}).done(function (result){
					getPedidosNaoFinalizado(false);
				}).fail(callBackError(extraStuff));        	
        });
		
		var divColuna = jQuery('<div/>', {
		    id: 'icone'		    
		});
		
		divColuna.append(htmlImg);
		
		$("#cabecalho div:first-child", divPedido).append(divColuna);
		
		$("#maisItemPedido", divPedido).hide();

		$("#tempoPedido").append(divPedido);
		
		return divPedido;		

}

var consumoHeightDivColuna = 0;
var previsaoConsumoHeightDivColuna = 0;
var qtdDivRowSubItem = 0;
function createRowSubItem(divPedido, qtd, descricao, img, id, pedidoId){
	var maxHeightDivColuna = divColuna.height() - $("#cabecalho", divPedido).height();
	
	if( (consumoHeightDivColuna + previsaoConsumoHeightDivColuna) >= maxHeightDivColuna){		
		return false;				
	}else if( consumoHeightDivColuna < maxHeightDivColuna){
		qtdDivRowSubItem++;
		
		var divRowSubItem = templateDivLinhaSubItem.clone();
		divRowSubItem.attr("id", "divRowSubItem_" + qtdDivColuna + "_" + qtdDivPedido + "_" + qtdDivRowSubItem);
		$("#qtd", divRowSubItem).html(qtd);
		$("#descricao", divRowSubItem).html(descricao);
		
        text = "Pedente Validação";
        src = "resources/img/icone_confirmar_marrom.png"
	
    		var htmlImg = $('<img />', { 
    			id: id,
   	            src: src,
   	            align: 'middle',
   	            alt: text,
   	            pedidoid: pedidoId,
   	            style: 'cursor: pointer;'
            });
           
           htmlImg.click(function(){
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
						url: url + "pedidos/pedidopronto/usuario/1/pedidosubitem/" + $(this).attr('id') + "/pedido/" + $(this).attr('pedidoid'),
						type: 'PUT'
					}).done(function (result){
						getPedidosNaoFinalizado(false);
					}).fail(callBackError(extraStuff));	
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
}
