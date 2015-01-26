package com.login.beachstop.garcom.android;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.login.beachstop.garcom.android.models.ServerResponse;
import com.login.beachstop.garcom.android.models.Setor;
import com.login.beachstop.garcom.android.models.UsuarioSetor;
import com.login.beachstop.garcom.android.network.SetorRequest;
import com.login.beachstop.garcom.android.network.UsuarioSetorRequest;
import com.login.beachstop.garcom.android.network.http.ResponseListener;
import com.login.beachstop.garcom.android.utils.Constantes;
import com.login.beachstop.garcom.android.views.actionbar.ActionBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Argus on 18/12/2014.
 */
public class SetorActivity extends DefaultActivity {

    private List<Setor> setores;
    private ScrollView scrollView;
    private LinearLayout linearLayout;
    private ProgressBar progressBar;
    private ProgressDialog progressDialog;
    private List<UsuarioSetor> usuarioSetores;
    private ResponseListener responseListenerGetSetor = new ResponseListener() {
        @Override
        public void onResult(ServerResponse serverResponse) {
            if (serverResponse != null) {
                if (serverResponse.isOK()) {
                    try {
                        getDataManager().getSetorDAO().save((List<Setor>) serverResponse.getReturnObject());
                        new UsuarioSetorRequest(responseListenerGetUsuarioSetor).get(getUsuario());
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(SetorActivity.this, Constantes.MSG_ERRO_GRAVAR_DADOS, Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(SetorActivity.this, serverResponse.getMsgErro(), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(SetorActivity.this, Constantes.MSG_ERRO_NET, Toast.LENGTH_SHORT).show();
            }
        }
    };
    private ResponseListener responseListenerGetUsuarioSetor = new ResponseListener() {
        @Override
        public void onResult(ServerResponse serverResponse) {
            if (serverResponse != null) {
                if (serverResponse.isOK()) {
                    try {
                        usuarioSetores = (List<UsuarioSetor>) serverResponse.getReturnObject();
                        loadSetorRow();
                        changeVibilityProgressBar(false);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(SetorActivity.this, Constantes.MSG_ERRO_GRAVAR_DADOS, Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(SetorActivity.this, serverResponse.getMsgErro(), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(SetorActivity.this, Constantes.MSG_ERRO_NET, Toast.LENGTH_SHORT).show();
            }
        }
    };
    private ResponseListener responseListenerPostUsuarioSetor = new ResponseListener() {
        @Override
        public void onResult(ServerResponse serverResponse) {
            if (serverResponse != null) {
                if (serverResponse.isOK()) {
                    try {
                        progressDialog.setMessage("Operação realizada com sucesso!");
                        progressDialog.dismiss();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(SetorActivity.this, Constantes.MSG_ERRO_GRAVAR_DADOS, Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(SetorActivity.this, serverResponse.getMsgErro(), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(SetorActivity.this, Constantes.MSG_ERRO_NET, Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setor);

        this.scrollView = (ScrollView) findViewById(R.id.activity_setor_scroll_view);
        this.linearLayout = (LinearLayout) findViewById(R.id.activity_setor_linear_layour_row);
        this.progressBar = (ProgressBar) findViewById(R.id.activity_setor_progress_bar);

        changeVibilityProgressBar(true);

        if (this.getDataManager().getSetorDAO().getQtdAll() != 0) {
            this.setores = this.getDataManager().getSetorDAO().getAll();
            new UsuarioSetorRequest(responseListenerGetUsuarioSetor).get(this.getUsuario());
        } else
            new SetorRequest(responseListenerGetSetor).get(new Setor());

        ((ActionBar) findViewById(R.id.actionbar)).setDisplayHomeAsUpEnabled(Boolean.TRUE);
        findViewById(R.id.actionbar).findViewById(R.id.imagem_action_bar_salvar_mesa).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(SetorActivity.this);
                progressDialog.setMessage("Salvando...");
                progressDialog.show();
                progressDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                UsuarioSetor usuarioSetor = new UsuarioSetor();
                usuarioSetor.setUsuarioId(getUsuario().getId());
                usuarioSetor.setSetores(new ArrayList<Setor>());

                for (Setor setor : setores) {
                    if (setor.isAtivo())
                        usuarioSetor.getSetores().add(setor);
                }

                new UsuarioSetorRequest(responseListenerPostUsuarioSetor).save(usuarioSetor);
            }
        });
    }

    private void vibrar() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(500);
    }

    private void changeVibilityProgressBar(boolean isVisibility) {
        this.progressBar.setVisibility(isVisibility ? ProgressBar.VISIBLE : ProgressBar.GONE);
        this.scrollView.setVisibility(!isVisibility ? ProgressBar.VISIBLE : ProgressBar.GONE);
    }

    private void loadSetorRow() {

        RowSetor rowSetor;
        View view;
        UsuarioSetor usuarioSetor;

        for (Setor setor : this.setores) {

            usuarioSetor = new UsuarioSetor();
            usuarioSetor.setSetorId(setor.getId());
            usuarioSetor.setUsuarioId(this.getUsuario().getId());

            setor.setAtivo(usuarioSetores.contains(usuarioSetor));

            view = View.inflate(this, R.layout.activity_setor_row, null);
            rowSetor = new RowSetor((TextView) view.findViewById(R.id.activity_setor_row_text_view_descricao), (ToggleButton) view.findViewById(R.id.activity_setor_row_toggle_button_ativo), setor);

            rowSetor.getTextView().setText(rowSetor.getSetor().getDescricao());
            rowSetor.getToggleButton().setTag(rowSetor);
            rowSetor.getToggleButton().setChecked(rowSetor.getSetor().isAtivo());

            rowSetor.getToggleButton().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    ((RowSetor) buttonView.getTag()).getSetor().setAtivo(isChecked);
                    if (isChecked)
                        vibrar();
                }
            });

            linearLayout.addView(view);
        }
    }

    protected class RowSetor {
        private TextView textView;
        private ToggleButton toggleButton;
        private Setor setor;

        public RowSetor(TextView textView, ToggleButton toggleButton, Setor setor) {
            this.textView = textView;
            this.toggleButton = toggleButton;
            this.setor = setor;
        }

        public TextView getTextView() {
            return textView;
        }

        public ToggleButton getToggleButton() {
            return toggleButton;
        }

        public Setor getSetor() {
            return setor;
        }
    }
}
