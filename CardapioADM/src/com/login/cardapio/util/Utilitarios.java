package com.login.cardapio.util;

import java.text.Normalizer;
import java.util.Calendar;

import br.com.topsys.util.TSCryptoUtil;
import br.com.topsys.util.TSUtil;

public final class Utilitarios {
	
	private Utilitarios(){
		
		
	}

    public static String tratarString(String valor) {
        if (!TSUtil.isEmpty(valor)) {
            valor = valor.trim().replaceAll("  ", " ");
        }

        return valor;
    }

    public static Long tratarLong(Long valor) {
        if ((!TSUtil.isEmpty(valor)) && (valor.equals(Long.valueOf(0L)))) {
            valor = null;
        }

        return valor;
    }

    public static String removerAcentos(String palavra) {
        if (TSUtil.isEmpty(palavra)) {
            return null;
        }

        return Normalizer.normalize(new StringBuilder(palavra), Normalizer.Form.NFKD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

    public static String gerarHash(String valor) {
        if (!TSUtil.isEmpty(valor)) {
            valor = TSCryptoUtil.gerarHash(valor, "md5");
        }

        return valor;
    }

    public static String gerarSenha() {
        Calendar rightNow = Calendar.getInstance();

        int diaAtual = rightNow.get(Calendar.DAY_OF_MONTH);
        int mesAtual = rightNow.get(Calendar.MONTH) + 1;
        int anoAtual = rightNow.get(Calendar.YEAR);
        int horaAtual = rightNow.get(Calendar.HOUR);
        int minutoAtual = rightNow.get(Calendar.MINUTE);
        int segAtual = rightNow.get(Calendar.SECOND);
        int miliAtual = rightNow.get(Calendar.MILLISECOND);

        String senha = anoAtual + mesAtual + diaAtual + horaAtual + minutoAtual + segAtual + miliAtual + "";

        return senha;
    }
    
    public static String gerarNomeArquivo(){
    	
    	return String.valueOf(System.currentTimeMillis());
    }

    public static String getSituacao(Boolean flagAtivo) {
        if ((!TSUtil.isEmpty(flagAtivo)) && (flagAtivo.booleanValue())) {
            return "Ativo";
        }

        return "Inativo";
    }
}