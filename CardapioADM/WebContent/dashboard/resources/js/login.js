/**
 * 
 */
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
		if ($("#login").valid())
			alert('Sim');
		else
			alert('Não');
	});
	
	$("#btnLembrarSenha").click(function() {
		if ($("#esqueciSenha").valid())
			alert('Sim');
		else
			alert('Não');
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