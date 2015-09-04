package com.login.beachstop.android;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.login.beachstop.android.models.AcaoConta;
import com.login.beachstop.android.models.Conta;
import com.login.beachstop.android.models.ServerResponse;
import com.login.beachstop.android.network.AcaoContaRequest;
import com.login.beachstop.android.network.ContaRequest;
import com.login.beachstop.android.network.http.ResponseListener;
import com.login.beachstop.android.utils.Constantes;
import com.login.beachstop.android.utils.Utilitarios;
import com.login.beachstop.android.views.actionbar.ActionBar;

import org.brickred.socialauth.android.DialogListener;
import org.brickred.socialauth.android.SocialAuthAdapter;
import org.brickred.socialauth.android.SocialAuthAdapter.Provider;
import org.brickred.socialauth.android.SocialAuthError;
import org.brickred.socialauth.android.SocialAuthListener;

import me.dm7.barcodescanner.core.ViewFinderView;

import static com.login.beachstop.android.R.layout.activity_checkin;


public class CheckInActivity extends DefaultActivity {

    private final int REFRESH_MILIS = 10000;
    protected ProgressBar progressBar;
    protected TextView textViewMsg;
    protected TextView textViewNumero;
    protected Button buttonQrCode;
    protected Button buttonShared;
    protected ActionBar actionBar;
    protected ImageView imageViewActionBar;
    protected TextView textViewActionBar;
    private SocialAuthAdapter socialAuthAdapter;
    private Conta conta;

    @Override
    protected void onResume() {
        super.onResume();

        if (getDataManager().getContaDAO().get() == null) {
            AcaoConta acaoConta = getDataManager().getAcaoContaDAO().get();

            if (acaoConta != null) {
                if (acaoConta.isAutorizado()) {
                    getNumeroMesa();
                } else {
                    changeStatusView(true, true, false, false);
                    textViewMsg.setText("Sua autorização está em andamento, aguarde!");
                    verificarAutorizacao();
                }
            }
        } else {
            getNumeroMesa();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(activity_checkin);

        loadViews();

        this.actionBar.setDisplayHomeAsUpEnabled(Boolean.FALSE);
        this.imageViewActionBar.setBackgroundResource(R.drawable.tab_check_in);
        this.textViewActionBar.setText("Check-In");

        this.buttonQrCode.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(CheckInActivity.this, CheckInQrCodeActivity.class);
                startActivityForResult(mainIntent, 1);
            }

        });

        socialAuthAdapter = new SocialAuthAdapter(new ResponseListenerSocialAuth());
        socialAuthAdapter.addProvider(Provider.FACEBOOK, R.drawable.facebook);
        socialAuthAdapter.enable(((Button) findViewById(R.id.activity_checkin_share)));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if(resultCode == RESULT_OK){
                String mesa = data.getStringExtra("mesa");
                String key = data.getStringExtra("key");
                this.abrirConta(mesa, key);
            }
        }
    }

    private void loadViews() {
        this.progressBar = (ProgressBar) findViewById(R.id.activity_checkin_progres_bar);
        this.textViewMsg = (TextView) findViewById(R.id.activity_checkin_text_view_lbl_msg);
        this.buttonQrCode = (Button) findViewById(R.id.activity_checkin_text_view_qr_code);
        this.buttonShared = (Button) findViewById(R.id.activity_checkin_share);
        this.textViewNumero = (TextView) findViewById(R.id.activity_checkin_text_view_mesa);
        this.actionBar = (ActionBar) findViewById(R.id.actionbar);
        this.imageViewActionBar = (ImageView) this.actionBar.findViewById(R.id.imagem_action_bar);
        this.textViewActionBar = (TextView) this.actionBar.findViewById(R.id.text_view_action_bar);
    }

    private void abrirConta(String mesaSelecionada, String keyMobile) {
        try {
            if (Utilitarios.isDigit(mesaSelecionada) && Constantes.KEYMOBILE.equals(keyMobile)) {

                if (this.getDataManager().getContaDAO().get() == null) {

                    String horaAtual = Utilitarios.getHourNow();
                    this.conta = new Conta(Long.valueOf(horaAtual));
                    this.conta.setDataAbertura(horaAtual);
                    this.conta.setNumero(Long.parseLong(mesaSelecionada));
                    this.conta.setTipoConta(Constantes.TipoConta.MESA);
                    this.conta.setSistemaId(0l);
                    this.conta.setDataFechamento("");
                    this.conta.setValorTotal("");
                    this.conta.setValorTotalPago("");
                    this.conta.setClienteId(this.getDataManager().getClienteDAO().get().getId());

                    changeStatusView(false, false, false, true);
                }

                new ContaRequest(new ResponseListener() {
                    @Override
                    public void onResult(ServerResponse serverResponse) {

                        if (serverResponse != null) {
                            if (serverResponse.isOK()) {
                                Long contaId = ((Conta) serverResponse.getReturnObject()).getId();
                                changeStatusView(false, false, false, false);
                                try {
                                    conta.setSistemaId(contaId);

                                    if (getDataManager().getContaDAO().get() != null) {
                                        getDataManager().getContaDAO().update(conta, conta.getId());
                                    } else {
                                        getDataManager().getContaDAO().save(conta);
                                    }

                                    textViewNumero.setText(conta.getNumero().toString());
                                    changeStatusView(false, false, false, true);
                                } catch (Exception e) {
                                    textViewMsg.setText(Constantes.MSG_ERRO_GRAVAR_DADOS);
                                    changeStatusView(false, true, true, false);
                                }
                            } else {

                                if ("false".equals(serverResponse.getMsgErro())) {
                                    abrirAcaoContaAutorizacao();
                                } else {
                                    textViewMsg.setText(serverResponse.getMsgErro());
                                    changeStatusView(false, true, true, false);
                                }
                            }
                        } else {
                            textViewMsg.setText(Constantes.MSG_ERRO_NET);
                            changeStatusView(false, true, true, false);
                        }
                    }
                }).post(conta);

                this.textViewMsg.setText("Analisando as condições da mesa!");
                changeStatusView(true, true, false, false);
            } else {
                this.textViewMsg.setText("Erro no QR Code!");
                changeStatusView(false, true, true, false);
            }
        } catch (Exception e) {
            this.textViewMsg.setText("Erro no QR Code!");
            changeStatusView(false, true, true, false);
        }
    }

    private void abrirAcaoContaAutorizacao() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Autorização");
        builder.setMessage("Essa conta necessita de uma permissão para realizar pedidos.");
        builder.setPositiveButton("Solicitar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                new AcaoContaRequest(new ResponseListener() {
                    @Override
                    public void onResult(ServerResponse serverResponse) {
                        if (serverResponse != null) {
                            if (serverResponse.isOK()) {
                                try {
                                    AcaoConta acaoConta = (AcaoConta) serverResponse.getReturnObject();
                                    acaoConta.setIsAutorizado(false);
                                    getDataManager().getAcaoContaDAO().save(acaoConta);

                                    textViewMsg.setText("Sua autorização está em andamento, aguarde!");
                                    changeStatusView(true, true, false, false);

                                    verificarAutorizacao();

                                } catch (Exception e) {
                                    textViewMsg.setText(Constantes.MSG_ERRO_GRAVAR_DADOS);
                                    changeStatusView(false, true, true, false);
                                }
                            } else {

                                if ("false".equals(serverResponse.getMsgErro())) {
                                    abrirAcaoContaAutorizacao();
                                } else {
                                    textViewMsg.setText(serverResponse.getMsgErro());
                                    changeStatusView(false, true, true, false);
                                }
                            }
                        } else {
                            textViewMsg.setText(Constantes.MSG_ERRO_NET);
                            changeStatusView(false, true, true, false);
                        }
                    }
                }).solicitarAutorizacao(conta.getNumero().toString());
            }
        });
        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                changeStatusView(false, false, true, false);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void verificarAutorizacao() {
        new AcaoContaRequest(new ResponseListener() {
            @Override
            public void onResult(ServerResponse serverResponse) {
                if (serverResponse != null) {
                    if (serverResponse.isOK()) {
                        AcaoConta acaoConta = (AcaoConta) serverResponse.getReturnObject();

                        if (acaoConta.isAutorizado()) {
                            getConta(getDataManager().getAcaoContaDAO().get().getNumero());
                        } else {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    verificarAutorizacao();
                                }
                            }, REFRESH_MILIS);
                        }
                    } else {
                        //TODO: VERIFICAR SE A AUTORIZACAO FOI NEGADA E INFORMAR AO CLIENTE
                        if (ServerResponse.SC_UNAUTHORIZED == serverResponse.getStatusCode()) {
                            changeStatusView(false, false, true, false);
                            Toast.makeText(getBaseContext(), "Sua autorização foi negada!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        }).isAuthorized(getDataManager().getAcaoContaDAO().get());
    }

    private void getConta(final Long numero) {
        new ContaRequest(new ResponseListener() {
            @Override
            public void onResult(ServerResponse serverResponse) {
                if (serverResponse != null) {
                    if (serverResponse.isOK()) {
                        try {
                            getDataManager().getAcaoContaDAO().deleteAll();
                            Conta conta = (Conta) serverResponse.getReturnObject();
                            conta.setSistemaId(conta.getId());
                            conta.setTipoConta(1l);
                            getDataManager().getContaDAO().save(conta);
                            textViewNumero.setText(conta.getNumero().toString());
                            changeStatusView(false, false, false, true);
                        } catch (Exception e) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    getConta(numero);
                                }
                            }, REFRESH_MILIS);
                        }
                    } else {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                getConta(numero);
                            }
                        }, REFRESH_MILIS);
                    }
                } else {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getConta(numero);
                        }
                    }, REFRESH_MILIS);
                }
            }
        }).associarCliente(numero, this.getDataManager().getClienteDAO().get().getId());
    }

    private void changeStatusView(Boolean isVisibilityProgressBar, Boolean isVisibilityTextView, Boolean isVisibilityButtonQrCode, Boolean isVisibilityButtonShare) {
        this.progressBar.setVisibility(isVisibilityProgressBar ? ProgressBar.VISIBLE : ProgressBar.GONE);
        this.textViewMsg.setVisibility(isVisibilityTextView ? TextView.VISIBLE : TextView.GONE);
        this.buttonQrCode.setVisibility(isVisibilityButtonQrCode ? Button.VISIBLE : Button.GONE);
        this.buttonShared.setVisibility(isVisibilityButtonShare ? Button.VISIBLE : Button.GONE);
    }

    private void getNumeroMesa() {
        this.textViewNumero.setText("");
        this.changeStatusView(true, false, false, true);

        new ContaRequest(new ResponseListener() {
            @Override
            public void onResult(ServerResponse serverResponse) {
                if (serverResponse != null) {
                    if (serverResponse.isOK()) {
                        try {
                            Conta serverConta = (Conta) serverResponse.getReturnObject();
                            conta.setNumero(serverConta.getNumero());
                            getDataManager().getContaDAO().update(conta, conta.getId());
                            textViewNumero.setText(conta.getNumero().toString());
                            changeStatusView(false, false, false, true);
                        } catch (Exception e) {
                            Toast.makeText(CheckInActivity.this, Constantes.MSG_ERRO_GRAVAR_DADOS, Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                            changeStatusView(false, false, false, true);
                            textViewNumero.setText(conta.getNumero().toString());
                        }
                    } else {
                        Toast.makeText(CheckInActivity.this, serverResponse.getMsgErro(), Toast.LENGTH_LONG).show();
                        changeStatusView(false, false, false, true);
                        textViewNumero.setText(conta.getNumero().toString());
                    }
                } else {
                    Toast.makeText(CheckInActivity.this, Constantes.MSG_ERRO_NET, Toast.LENGTH_LONG).show();
                    changeStatusView(false, false, false, true);
                    textViewNumero.setText(conta.getNumero().toString());
                }
            }
        }).getNumeroConta(this.conta);
    }

    private static class CustomViewFinderView extends ViewFinderView {
        public static final String TRADE_MARK_TEXT = "BeachStop Ipitanga";
        public static final int TRADE_MARK_TEXT_SIZE_SP = 40;
        public final Paint PAINT = new Paint();

        public CustomViewFinderView(Context context) {
            super(context);
            init();
        }

        public CustomViewFinderView(Context context, AttributeSet attrs) {
            super(context, attrs);
            init();
        }

        private void init() {
            PAINT.setColor(Color.WHITE);
            PAINT.setAntiAlias(true);
            float textPixelSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                    TRADE_MARK_TEXT_SIZE_SP, getResources().getDisplayMetrics());
            PAINT.setTextSize(textPixelSize);
        }

        @Override
        public void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            drawTradeMark(canvas);
        }

        private void drawTradeMark(Canvas canvas) {
            Rect framingRect = getFramingRect();
            float tradeMarkTop;
            float tradeMarkLeft;
            if (framingRect != null) {
                tradeMarkTop = framingRect.bottom + PAINT.getTextSize() + 10;
                tradeMarkLeft = framingRect.left;
            } else {
                tradeMarkTop = 10;
                tradeMarkLeft = canvas.getHeight() - PAINT.getTextSize() - 10;
            }

            BitmapDrawable drawable = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_launcher);

            canvas.drawBitmap(drawable.getBitmap(), tradeMarkLeft, tradeMarkTop, PAINT);
        }
    }

    private final class ResponseListenerSocialAuth implements DialogListener {
        public void onComplete(Bundle values) {
            try {
                socialAuthAdapter.updateStatus("No Beach Stop Ipitanga!", new MessageListener(), true);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(CheckInActivity.this, "Erro ao compartilhar na rede social. Tente novamente mais tarde!", Toast.LENGTH_SHORT).show();
            }
        }

        public void onCancel() {
            Log.d("ShareButton", "Cancelado");
        }

        @Override
        public void onBack() {
        }

        @Override
        public void onError(SocialAuthError arg0) {
            Log.d("ShareButton", "Cancelado");
        }
    }

    // To get status of message after authentication
    private final class MessageListener implements SocialAuthListener<Integer> {
        public void onError(SocialAuthError e) {
            System.out.println(e.getMessage());
        }

        @Override
        public void onExecute(String arg0, Integer status) {
            // TODO Auto-generated method stubInteger status = t;
            if (status.intValue() == 200 || status.intValue() == 201 || status.intValue() == 204) {
                Toast.makeText(CheckInActivity.this, "Compartilhado!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(CheckInActivity.this, "Erro ao compartilhar! Tente novamente mais tarde!", Toast.LENGTH_LONG).show();
            }
        }
    }
}
