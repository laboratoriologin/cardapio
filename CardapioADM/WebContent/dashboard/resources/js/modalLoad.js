/**
 *	função de controle do modal load para qualquer página 
 */

var nEventoAtual = 0;
var nEventoTotal = 0;
var nTentativasErro = 0;

function startModalLoad(net){

	nEventoAtual = 0;
	nEventoTotal = net;
	
	$(".error").hide();	
    $("#progressbar" ).progressbar({ value: 0 });
	$("#modalload").show();	
}

function updateCompletedEventProgress(){
	
	if(nEventoTotal != 0){
		nEventoAtual++;		
		if( nEventoAtual >= nEventoTotal)
			closeModalLoad();
		else
			$( "#progressbar" ).progressbar({ value: (nEventoAtual * 100)/ nEventoTotal });
	}
}

function updataMsgErro(){
		
	if(nTentativasErro == 0)
		$(".error").html('Ops, ocorreu um erro.');	
	else if(nTentativasErro == 1)
		$(".error").html('Ops, ocorreu um erro. Tente recarregar a página!');
	else
		$(".error").html('Ops, ocorreu um erro. Entre em contato com administrador do sistema!');
	
	$(".error").append('<span id="reload">Recarregar</span>');
	
	$(".error").show();
	
	$("#reload").click(function(){
		nTentativasErro++;
		loadInit();
	});
}

function closeModalLoad(){
	$("#modalload").hide();	
}