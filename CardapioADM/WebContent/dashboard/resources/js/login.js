/**
 * 
 */
var url = "http://localhost:8080/CardapioWS/"; 

$(document).ready(function() {

	$("#esqueciSenha").hide();
	$("#acaoEsqueciSenha").hide();

	$("#login").validate({
		rules : {
			txtLogin : "required",
			txtSenha : {
				required : true
			}
		},
		messages : {
			txtLogin : "Digite seu Login!",
			txtSenha : {
				required : "Digite sua senha"
			}
		}
	});
	
	$("#esqueciSenha").validate({
		rules : {
			txtEmail : "required",
			txtSenha : {
				required : true
			}
		},
		messages : {
			txtEmail : "Digite seu Email!"
		}
	});

	$("#btnEsqueciSenha").click(function() {
		$("#login").hide("blind", callbackShowEsqueciSenha());
		$("#acaoLogin").hide("blind");
	});

	$("#btnVoltarLogin").click(function() {
		$("#esqueciSenha").hide("blind", callbackShowLogin());
		$("#acaoEsqueciSenha").hide("blind");
	});

	$("#btnEntrar").click(function() {
		$("#login").valid()		
	});
	
	$("#btnLembrarSenha").click(function() {
		if ($("#esqueciSenha").valid()){
			$.ajax({
				url: url + "usuarios/enviarEmail/",
				type: 'POST',
				cache : false,
				data : {
							'email' : $("#txtEmail").val()							
				   		} 	
			}).done(function (result){
				$("#esqueciSenha").hide("blind", callbackShowLogin());
				$("#acaoEsqueciSenha").hide("blind");
				alert("Foi enviado para o seu e-mail uma nova senha!");									
			}).fail(function(result) {
				alert('erro');
			});	
		}
	});	

	function callbackShowEsqueciSenha() {
		setTimeout(function() {
			$("#esqueciSenha").show("blind");
			$("#acaoEsqueciSenha").show("blind");
		}, 100);
	}

	function callbackShowLogin() {
		setTimeout(function() {
			$("#login").show("blind");
			$("#acaoLogin").show("blind");
		}, 100);
	}
});