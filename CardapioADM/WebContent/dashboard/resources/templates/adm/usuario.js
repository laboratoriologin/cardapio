/**
 * 
 */
$(document).ready(function() {
	$.ajaxSetup({cache : false});
	
	$.fn.clearForm = function() {
		return this.each(function() {
			var type = this.type, tag = this.tagName.toLowerCase();
			
		    if (tag == 'form')
		      return $(':input',this).clearForm();
		    if (type == 'text' || type == 'password' || tag == 'textarea')
		      this.value = '';
		    else if (type == 'checkbox' || type == 'radio')
		      this.checked = false;
		    else if (tag == 'select')
		      this.selectedIndex = 0;
		});
	};
	
	$("#dataAniversario").mask("00/00/0000", {clearIfNotMatch: true});
	$("#cpf").mask("000.000.000-00", {reverse: true, clearIfNotMatch: true});
	$("#telefone").mask("(00) 0000-0000", {clearIfNotMatch: true});
	$("#celular").mask("(00) 0000-0000", {clearIfNotMatch: true});
	$("#telefoneContato").mask("(00) 0000-0000", {clearIfNotMatch: true});

	
	$(document).tooltip({ track: true });	
	$("#tabs").tabs();	
	$("#loader").hide();
	$("#loaderDialog").hide();
	
	$.getJSON(url + "grupos_usuarios/getalln2", function(result) {
	    var options = $("#grupousuario");
	    //don't forget error handling!
	    $.each(result, function( key, val ) {	    	
	        options.append($("<option />").val(val.grupousuario.id).text(val.grupousuario.descricao));
	    });
	});
	
	$("#dialog-formusuario").dialog({
		resizable : false,
		height : ($("#page").height() - 100),
		width : ($("#page").width() - 500),
		modal : true,
		autoOpen : false
	});
	
	$("form").validate();
	
	$("#pesquisaUsuario").keyup(function( event ) {
		  if ( event.which == 13 ) {
			  loadDados();
		  }
		  
		  if ( event.which == 8 ) {
			  var strPesquisa = $("#pesquisaUsuario").val();
			  if(strPesquisa.length == 0){
				  loadDados();
			  }
		  }
		  
		  var strPesquisa = $("#pesquisaUsuario").val();
		  if(strPesquisa.length >= 2){
			  loadDados();
		  }

	});
	
	loadDados();
	
	$("#novoUsuario").click(  function(event) {
		$("#dialog-formusuario").dialog("open");
		$('form').clearForm();
		var form = $("form").validate();
		form.resetForm();
		$("#usuarioId").val("");
		$("#senha").prop('disabled', false);
	});
	
	$("#btnSalvar").click(function(){
		$("#loaderDialog").show();
		if ($("form").valid()){
			sendForm();
		}else{
			$("#loaderDialog").hide();
		}
	});
});

function loadDados(){
	
	$("#loader").show();
	
	var strPesquisa = $("#pesquisaUsuario").val();
	
	$.ajax({
		url : url + "usuarios/pesquisa/" + strPesquisa,
		dataType : "json",
		success : function(data) {
			loadTabelaUsuario(data);
		}
	});	
}

function loadTabelaUsuario(data){
	$("#tabelaUsuario tr:not(:first-child)").remove();
	
	if(data.length != 0){	
		$.each( data, function( key, val ) {
			
			var obj = val.usuario;
			
			var msgConfirmacao;
			var title;
			if(obj.flagAtivo){
				msgConfirmacao = "Deseja desativar o usuário?";
				title = "Desativar";
			}else{
				msgConfirmacao = "Deseja ativar o usuário?";
				title = "Ativar";
			}
			
			var iconAtivo = obj.flagAtivo ? "ui-icon-closethick" : "ui-icon-check";
						
			var divIcons = $('<div />', { style : 'display: inline-flex;'});
			var spanIconEdit = $('<span />', { class : 'ui-icon  ui-icon-pencil', title : 'Editar'});
			var spanIconAtivar = $('<span />', { class : 'ui-icon ' + iconAtivo , title : title });

			spanIconAtivar.click(obj,  function(event) {
				$("#loader").show();
				if (confirm(msgConfirmacao)){
					var postData = new Object();
					postData["flagativo"] = obj.flagAtivo;
					
					$.ajax({
						url : url + "usuarios/ativo/" + obj.id,
						data : postData,
						type : 'PUT',
						cache : false
					}).done(function(result) {
						loadDados();
						alert('Operação realizada com sucesso!');
						$("#loader").hide();
					}).fail(function(result) {
						alert('erro');
						$("#loader").hide();
					});					
				}else
					$("#loader").hide();
			});
			
			spanIconEdit.click(obj,  function(event) {

				$("#senha").prop('disabled', true);
				
				$("#usuarioId").val(obj.id);
				$("#nome").val(obj.nome);
				$("#ativo").prop("checked", obj.flagAtivo);
				$("#login").val(obj.login);
				$("#senha").val(obj.senha);
				$("#grupousuario").val(obj.grupoUsuario.id);
				$("#dataAniversario").val(obj.strDataNascimento);
				$("#rg").val(obj.rg);
				$("#cpf").val(obj.cpf);
				$("#endereco").val(obj.endereco);
				$("#telefone").val(obj.telefone);
				$("#celular").val(obj.celular);
				$("#email").val(obj.email);
				$("#contato").val(obj.contato);
				$("#telefoneContato").val(obj.telefoneContato);
				
				$("#dialog-formusuario").dialog("open");
				
				var form = $("form").validate();
				form.resetForm();
			});
			
			divIcons.append(spanIconEdit).append(spanIconAtivar);
			
			$("#tabelaUsuario").append(
					$("<tr />").append(
							$("<td />", { text : obj.id})
					).append(
							$("<td />", { text : obj.nome})
					).append(
							$("<td />", { text : obj.login})
					).append(
							$("<td />", { text : obj.grupoUsuario.descricao})
					).append(
							$("<td />", { text : obj.telefone})
					).append(
							$("<td />", { text : obj.celular})
					).append(
							$("<td />", { text : obj.email})
					).append(
							$("<td />").append(divIcons)
					)					
			);			
		});
	}else{
		$("#tabelaUsuario").append(
				$("<tr />").append(
						$("<td />", { text : "Nenhum registro encotrado!", colspan : "8"})
				)
		);
	}
	
	$("#loader").hide();
}

function sendForm(){
	var objSend = new Object();	
	objSend["nome"] = $("#nome").val();
	objSend["flagativo"] = $("#ativo").prop("checked");
	objSend["login"] = $("#login").val();
	objSend["grupousuario"] = $("#grupousuario").val();
	objSend["datanascimento"] = $("#dataAniversario").val();
	objSend["rg"] = $("#rg").val();
	objSend["cpf"] = $("#cpf").val();
	objSend["endereco"] = $("#endereco").val();
	objSend["telefone"] = $("#telefone").val();
	objSend["celular"] = $("#celular").val();
	objSend["email"] = $("#email").val();
	objSend["contato"] = $("#contato").val();
	objSend["telefonecontato"] = $("#telefoneContato").val();
	
	var type;
	var id;
	if($("#usuarioId").val() != ""){
		type = "PUT";
		id = "/" + $("#usuarioId").val();
		objSend["id"] = $("#usuarioId").val();		
	} else {
		type = "POST";
		id = "";
		objSend["senha"] = $("#senha").val();
	}
	
	
	$.ajax({
		url : url + "usuarios" + id,
		data : objSend,
		type : type,
		cache : false
	}).done(function(result) {
		alert('Processo realizado com sucesso!');
		$("#loaderDialog").hide();
		$("#dialog-formusuario").dialog("close");
		loadDados();
	}).fail(function(result) {
		alert('erro');
		$("#loaderDialog").hide();		
	});
		
}