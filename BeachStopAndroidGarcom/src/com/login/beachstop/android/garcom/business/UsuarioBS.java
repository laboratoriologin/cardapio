/**
 * 
 */
package com.login.beachstop.android.garcom.business;

import com.login.beachstop.android.garcom.model.Usuario;
import com.login.beachstop.android.garcom.util.CryptoUtil;
import com.login.beachstop.android.garcom.util.JSONLembrarSenha;
import com.login.beachstop.android.garcom.util.JSONUsuario;


/**
 * @author Ricardo
 *
 */
public class UsuarioBS  implements Observable  {
	
	private BusinessResult businessResult;
	
	
	public UsuarioBS(BusinessResult businessResult) {
		this.businessResult = businessResult;
	}

	public void logar(Usuario usuario) throws Exception {
		usuario.setSenha(CryptoUtil.gerarHash(usuario.getSenha(), "MD5"));
		new JSONUsuario(this).execute(usuario);
	}
	
	
	
	@Override
	public void observe(Object result) throws Exception {
		businessResult.getBusinessResult(result);
		
	}

	public void lembrarSenha(Usuario usuarioPesquisado) {
		businessResult.getBusinessResult(usuarioPesquisado);
		
	}

	public void enviarEmail(Usuario usuarioPesquisado) {
		new JSONLembrarSenha(this).execute(usuarioPesquisado);
		
	}

	
}
