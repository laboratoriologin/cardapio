/**
 * 
 */
var isEdit=false;
function getPagamentos(){
	
	$("#loader").show();
	
	$.ajax({ 
		url : url + "pagamentos/pagamentobyconta/" + $("#contaId").val(),
		type : 'GET',
		cache : false,	
	}).done(function(result) {
		$("#loader").hide();
		loaderTablePagamento(result);
	}).fail(function(result) {
		alert('Erro, tente novamente mais tarde!');
		$("#loader").hide();
	});
	
	
	$("#editPagamento").dblclick(function() {
		if(!isEdit){
			isEdit = true;
			$("#loader").show();
			
			$.ajax({ 
				url : url + "tipos_pagamentos/",
				type : 'GET',
				cache : false,	
			}).done(function(result) {
				$("#loader").hide();
				createRowEdit(result); 
			}).fail(function(result) {
				alert('Erro, tente novamente mais tarde!');
				$("#loader").hide();
			});
		}
	});
}


function loaderTablePagamento(result) {
	clearTable();
	
	if(result.length != 0) {
		var valorTotal = 0;		 		
		$.each(result, function( key, val ) {
			valorTotal += parseFloat(val.pagamento.valor);
			
			var spanDelete = $("<span />", { id : "excluirPag", class : "ui-icon ui-icon-trash", pagamentoId : val.pagamento.id });
			spanDelete.click(function(){
				$("#loader").show();
				if(confirm("Deseja excluir o registro?")){				
					var pagamentoId = $(this).attr("pagamentoId");				
					$.ajax({
						url : url + "pagamentos/" + pagamentoId,
						type : 'DELETE',
						cache : false						
					}).done(function(result) {						
						$("#loader").hide();
						getPagamentos();
					}).fail(function(result) {
						alert('Erro ao excluir, tente novamente mais tarde.');
					});
				}
			});
			
			var trTboddy = $("<tr />").append(
							   $("<td />", {text : val.pagamento.strHorairo})
				       ).append(
							   $("<td />", {text : val.pagamento.tipoPagamento.descricao})
				       ).append(
							   $("<td />", {text : val.pagamento.valor})
				       ).append(
							   $("<td />").append($("<div />").append(spanDelete))
				       ).append(
				    		   $("<td />")
				       );
			
			appendBody(trTboddy);
		});
		
		var thTfoot = $("<tr />").append(
						  $("<th />", { colspan : "2", style : "font-weight: bold; text-align: right;", text : "Total"})
				  ).append(
						  $("<th />", { colspan : "2", style : "font-weight: bold;", text : valorTotal})
				  ).append(
						  $("<th />")
				  );
		
		appendFoot(thTfoot);
	} else {
		
		var trTboddy;
		var thTfoot;
		
		trTboddy = $("<tr />").append(
				   		$("<td />", {text : "Nenhum pagmento", colspan : "4"})
				   ).append(
					    $("<td />")
				   );
		
		thTfoot = $("<tr />").append(
			      		$("<th />", { colspan : "2", style : "font-weight: bold; text-align: right;", text : "Total"})
			      ).append(
		    		    $("<th />", { colspan : "2", style : "font-weight: bold;", text : "0,00"})
			      ).append(
			    		$("<th />")
			      );
		
		appendBody(trTboddy);
		appendFoot(thTfoot);
	}
}

function createRowEdit(result){
	
	var dt = new Date();
	var time = (dt.getHours() < 10 ? "0" : "") + dt.getHours() + ":" + (dt.getMinutes() < 10 ? "0" : "") + dt.getMinutes() + ":" + (dt.getSeconds() < 10 ? "0" : "") + dt.getSeconds();
	
	var select = $("<select />", { id : "selectTipoPagamento"});
	$.each(result, function( key, val ) {
		select.append( $("<option />", { text : val.tipopagamento.descricao, value : val.tipopagamento.id}));
	});
	
	var input = $("<input />", { id : "inputValor",  type : "text", style : "width: 90px;"});
	input.mask('000.000.000,00', {reverse: true});
	
	var spanDelete = $("<span />", { class : "ui-icon ui-icon-trash", style : "float: left; margin-right: 0px;" });
	spanDelete.click(function(){
		$("#editPag").remove();
		isEdit = false;
	});
	
	var spanSave = $("<span />", { class : "ui-icon ui-icon-check", style : "cursor: pointer;" });
	spanSave.click(function(){
		savePag();			
	});
	
	var trTboddy = $("<tr />", { id : "editPag"}).append(
									   $("<td />").append($("<span />", {text : time}))
						    ).append(
									   $("<td />").append($("<div />").append(select))
						    ).append(
									   $("<td />").append($("<div />").append(input))
						    ).append(
									   $("<td />").append($("<div />").append(spanDelete).append(spanSave))
						    ).append(
						 		   $("<td />")
						    );
	
	$("#valorPagoTbody tbody").prepend(trTboddy);
	$("#inputValor").focus();
}

function savePag(){
	
	if($("#inputValor").val() != "") {
		$("#loader").show();
		
		var postSender = new Object();
		postSender["valor"] = $("#inputValor").val().replace(".", "").replace(",", ".");
		postSender["tipopagamento.id"] = $("#selectTipoPagamento").val();
		postSender["conta.id"] = $("#contaId").val();
		
		console.log(postSender);
		
		$.ajax({
			url : url + "pagamentos/",
			data : postSender,
			type : 'POST',
			cache : false
		}).done(function(result) {			
			$("#loader").hide();
			getPagamentos();
		}).fail(function(result) {
			alert('Erro ao inserir, tente novamente mais tarde.');
			$("#loader").hide();
		});
	}
}

function appendBody(obj){
		
	$("#valorPagoTbody tbody").append(obj.clone(true));
	$("#valorPagoThead tbody").append(obj.clone(true));	
	$("#valorPagoTfood tbody").append(obj.clone(true));
}

function appendFoot(obj){
	$("#valorPagoThead tfoot").append(obj.clone(true));
	$("#valorPagoTbody tfoot").append(obj.clone(true));
	$("#valorPagoTfood tfoot").append(obj.clone(true));
}

function clearTable(){
	$("#valorPagoThead tfoot").empty();
	$("#valorPagoTbody tfoot").empty();
	$("#valorPagoTfood tfoot").empty();
	
	$("#valorPagoThead tbody").empty();
	$("#valorPagoTbody tbody").empty();
	$("#valorPagoTfood tbody").empty();
}