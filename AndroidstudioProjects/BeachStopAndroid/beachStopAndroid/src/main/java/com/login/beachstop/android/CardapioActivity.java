package com.login.beachstop.android;

import android.os.Bundle;
import android.widget.Toast;

import com.login.beachstop.android.fragments.CardapioFragment;
import com.login.beachstop.android.utils.Constantes;

/**
 * Created by Argus on 28/10/2014.
 */
public class CardapioActivity extends DefaultActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardapio);

        if (findViewById(R.id.activity_cardapio_fragment_layout) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }
            // Create an instance of ExampleFragment
            CardapioFragment firstFragment = new CardapioFragment();

            // In case this activity was started with special instructions from
            // an Intent,
            // pass the Intent's extras to the fragment as arguments
            firstFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction().add(R.id.activity_cardapio_fragment_layout, firstFragment).commit();
        } else {
            Toast.makeText(getBaseContext(), Constantes.MSG_ERRO_GRAVE_SISTEMA, Toast.LENGTH_SHORT).show();
        }
    }
}
