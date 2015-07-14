package br.com.login.cardapio.beachstop.ws.util;

public final class Constantes {

	private Constantes() {

	}

	public static final String REMETENTE = "laboratoriologin@gmail.com";
	public static final String SMTP_GMAIL = "imap.gmail.com";
	public static final String PORTAL_GMAIL = "465";
	public static final String SENHA_GMAIL = "l0g1n.s3n41";
	public static final String ASSUNTO_EMAIL = "E-mail enviado pelo Cardappio";
	public static final String MENSAGEM_SENHA = "Sua senha atual do app cardápio  é :";
	public static final String CRIPTOGRAFIA_MD5 = "md5";
	
	public interface StatusPedido{
		public static final Long PENDENTE_APROVACAO = 1L;
		public static final Long PENDENTE_ENTREGA = 2L;
		public static final Long ENTREGUE = 3L;
		public static final Long CANCELADO = 4L;
		public static final Long TRANSFERIDO = 5L;
	}
	
    public interface Acoes {
    	Long ChamarGarcom = 3l;
    	Long PedirConta = 4l;
    	Long NovoPedido = 5l;
    	Long MudarMesa = 6l;
    	Long ReabrirConta = 7l;
    	Long AutorizacaoAssociacao = 8l;
    	Long JuntarMesa = 9l;
    }
}
