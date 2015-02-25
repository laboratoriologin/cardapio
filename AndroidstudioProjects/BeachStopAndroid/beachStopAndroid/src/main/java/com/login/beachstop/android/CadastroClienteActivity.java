package com.login.beachstop.android;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.FacebookDialog;
import com.facebook.widget.LoginButton;
import com.login.beachstop.android.models.Cliente;
import com.login.beachstop.android.models.ServerResponse;
import com.login.beachstop.android.network.ClienteRequest;
import com.login.beachstop.android.network.http.ResponseListener;
import com.login.beachstop.android.utils.Constantes;

import org.json.JSONException;

import java.util.Arrays;
import java.util.Calendar;

import static com.login.beachstop.android.R.layout.activity_cadastro_cliente;

/**
 * Created by Argus on 27/10/2014.
 */
public class CadastroClienteActivity extends DefaultActivity {

    private final int SPLASH_MILIS = 1000;
    protected EditText editTextNome;
    protected EditText editTextEmail;
    protected EditText editTextCelular;
    protected EditText editTextAniversario;
    private ProgressDialog progressDialog;
    private Cliente cliente;
    private LoginButton loginButton;
    private UiLifecycleHelper uiHelper;
    private TextWatcher textWatcher = new TextWatcher() {

        private String current = "";
        private String ddmmyyyy = "DDMMAAAA";

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!s.toString().equals(current)) {
                String clean = s.toString().replaceAll("[^\\d.]", "");
                String cleanC = current.replaceAll("[^\\d.]", "");
                Calendar cal = Calendar.getInstance();
                int cl = clean.length();
                int sel = cl;
                for (int i = 2; i <= cl && i < 6; i += 2) {
                    sel++;
                }
                //Fix for pressing delete next to a forward slash
                if (clean.equals(cleanC)) sel--;

                if (clean.length() < 8){
                    clean = clean + ddmmyyyy.substring(clean.length());
                }else{
                    //This part makes sure that when we finish entering numbers
                    //the date is correct, fixing it otherwise
                    int day  = Integer.parseInt(clean.substring(0,2));
                    int mon  = Integer.parseInt(clean.substring(2,4));
                    int year = Integer.parseInt(clean.substring(4,8));

                    if(mon > 12) mon = 12;
                    cal.set(Calendar.MONTH, mon-1);
                    day = (day > cal.getActualMaximum(Calendar.DATE))? cal.getActualMaximum(Calendar.DATE):day;
                    year = (year<1900)?1900:(year>2100)?2100:year;
                    clean = String.format("%02d%02d%02d",day, mon, year);
                }

                clean = String.format("%s/%s/%s", clean.substring(0, 2),
                        clean.substring(2, 4),
                        clean.substring(4, 8));
                current = clean;
                editTextAniversario.setText(current);
                editTextAniversario.setSelection(sel < current.length() ? sel : current.length());
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // TODO Auto-generated method stub

        }

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub

        }
    };
    private ResponseListener listenerCadastroCliente = new ResponseListener() {
        @Override
        public void onResult(ServerResponse serverResponse) {

            if (serverResponse != null) {

                if (serverResponse.isOK()) {

                    Cliente clienteResponse = (Cliente) serverResponse.getReturnObject();
                    cliente.setId(clienteResponse.getId());
                    cliente.setDataNascimento(clienteResponse.getDataNascimento());
                    progressDialog.setMessage("Você foi cadastrado com sucesso em nosso sistemas. Aproveite!");

                    try {

                        getDataManager().getClienteDAO().save(cliente);

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                progressDialog.dismiss();
                                goToStart();

                            }
                        }, SPLASH_MILIS);


                    } catch (Exception e) {

                        e.printStackTrace();
                        progressDialog.setMessage("Ops! Ocorreu um erro no sistema ao grava os dados no seu celular.");

                    }

                } else {

                    progressDialog.dismiss();
                    Toast.makeText(CadastroClienteActivity.this, serverResponse.getMsgErro(), Toast.LENGTH_LONG).show();

                }


            } else {

                progressDialog.dismiss();
                Toast.makeText(CadastroClienteActivity.this, Constantes.MSG_ERRO_NET, Toast.LENGTH_LONG).show();

            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        uiHelper = new UiLifecycleHelper(this, callback);
        uiHelper.onCreate(savedInstanceState);
        setContentView(activity_cadastro_cliente);

        this.editTextNome = (EditText) findViewById(R.id.activity_cadastro_cliente_edit_text_nome);
        this.editTextEmail = (EditText) findViewById(R.id.activity_cadastro_cliente_edit_text_email);
        this.editTextCelular = (EditText) findViewById(R.id.activity_cadastro_cliente_edit_text_celular);
        this.editTextAniversario = (EditText) findViewById(R.id.activity_cadastro_cliente_edit_text_data_nascimento);
        this.editTextAniversario.addTextChangedListener(this.textWatcher);
        this.loginButton = (LoginButton) findViewById(R.id.fb_login_button);

        loginButton.setReadPermissions(Arrays.asList("public_profile"));

        this.loginButton.setUserInfoChangedCallback(new LoginButton.UserInfoChangedCallback() {
            @Override
            public void onUserInfoFetched(GraphUser user) {

                if (user !=null) {
                    editTextNome.setText((user.getName() + " " + user.getLastName()).trim());

                    try {
                        editTextEmail.setText(user.getInnerJSONObject().getString("email"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    editTextAniversario.setText(user.getBirthday());
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        uiHelper.onResume();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uiHelper.onActivityResult(requestCode, resultCode, data, dialogCallback);
    }

    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }

    public void salvar(View view) {

        boolean isOK = true;

        if ("".equals(this.editTextNome.getText().toString())) {
            Toast.makeText(this, "Digite seu nome!", Toast.LENGTH_LONG).show();
            isOK = false;
        }

        if ("".equals(this.editTextEmail.getText().toString())) {
            Toast.makeText(this, "Digite seu email!", Toast.LENGTH_LONG).show();
            isOK = false;
        }

        if ("".equals(this.editTextCelular.getText().toString())) {
            Toast.makeText(this, "Digite seu celular!", Toast.LENGTH_LONG).show();
            isOK = false;
        }

        if (isOK) {

            this.cliente = new Cliente();
            this.cliente.setNome(this.editTextNome.getText().toString());
            this.cliente.setEmail(this.editTextEmail.getText().toString());
            this.cliente.setCelular(this.editTextCelular.getText().toString());
            this.cliente.setDataNascimento(this.editTextAniversario.getText().toString());
            this.cliente.setToken("");

            new ClienteRequest(this.listenerCadastroCliente).post(cliente);

            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Enviando os dados para os nossos servidores...");
            progressDialog.show();
            progressDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }


    public void cancelar(View view) {

        finish();

    }

    private void goToStart() {

        Intent mainIntent = new Intent(CadastroClienteActivity.this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        this.startActivity(mainIntent);
        this.finish();

    }

    // CallBack Facebook


    private Session.StatusCallback callback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            onSessionStateChange(session, state, exception);
        }
    };

    private void onSessionStateChange(Session session, SessionState state, Exception exception) {
        if ((exception instanceof FacebookOperationCanceledException ||
                        exception instanceof FacebookAuthorizationException)) {
            new AlertDialog.Builder(CadastroClienteActivity.this)
                    .setTitle("Cancelado")
                    .setMessage("Permissão não foi concedida")
                    .setPositiveButton(R.string.ok, null)
                    .show();

        }

        if (session.isClosed()) {

            editTextNome.setText("");

            editTextEmail.setText("");

            editTextAniversario.setText("");

        }

    }


    private FacebookDialog.Callback dialogCallback = new FacebookDialog.Callback() {
        @Override
        public void onError(FacebookDialog.PendingCall pendingCall, Exception error, Bundle data) {
            Log.d("HelloFacebook", String.format("Error: %s", error.toString()));
        }

        @Override
        public void onComplete(FacebookDialog.PendingCall pendingCall, Bundle data) {
            Log.d("HelloFacebook", "Success!");
        }
    };

}
