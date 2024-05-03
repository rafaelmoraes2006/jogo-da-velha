package com.rafaelmoraes2006.jogo_da_velha;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    int jogadorDaVez = R.drawable.circulo;
    boolean jogarContraMaquina = true;
    Integer jogadorVencedor = null;
    boolean empatou = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void jogar(View view) {
        if (empatou || jogadorVencedor != null) {
            this.perguntarSeQuerComecarUmJogoNovo();
            return;
        }
        ImageButton lugar = findViewById(view.getId());
        if (this.ocuparLugar(lugar)) {
            if (jogarContraMaquina) {
                ocuparLugarAleatorio();
            }
            this.checarVitoria();
        }
    }

    public boolean ocuparLugar(ImageButton lugar) {
        if (lugar.getDrawable() != null) {
            Toast.makeText(MainActivity.this, "Este lugar já está ocupado!", Toast.LENGTH_SHORT).show();
            return false;
        }
        lugar.setImageResource(this.jogadorDaVez);
        lugar.setTag(this.jogadorDaVez);
        atualizarJogadorDaVez();
        return true;
    }

    public void ocuparLugarAleatorio() {
        View[] lugares = {
                findViewById(R.id.lugar1),
                findViewById(R.id.lugar2),
                findViewById(R.id.lugar3),
                findViewById(R.id.lugar4),
                findViewById(R.id.lugar5),
                findViewById(R.id.lugar6),
                findViewById(R.id.lugar7),
                findViewById(R.id.lugar8),
                findViewById(R.id.lugar9),
        };
        ArrayList<View> lugaresNulos = new ArrayList<>();
        for (View lugar : lugares) {
            if (lugar.getTag() == null) {
                lugaresNulos.add(lugar);
            }
        }
        if (lugaresNulos.size() == 0) {
            return;
        }
        int indice = new Random().nextInt(lugaresNulos.size());
        this.ocuparLugar((ImageButton) lugaresNulos.get(indice));
    }

    public void atualizarJogadorDaVez() {
        this.jogadorDaVez = this.jogadorDaVez == R.drawable.circulo ? R.drawable.xis : R.drawable.circulo;
    }

    public void checarVitoria() {
        Integer[] jogadores = {R.drawable.circulo, R.drawable.xis};
        View[] lugares = {
                findViewById(R.id.lugar1),
                findViewById(R.id.lugar2),
                findViewById(R.id.lugar3),
                findViewById(R.id.lugar4),
                findViewById(R.id.lugar5),
                findViewById(R.id.lugar6),
                findViewById(R.id.lugar7),
                findViewById(R.id.lugar8),
                findViewById(R.id.lugar9),
        };
        for (Integer jogador : jogadores) {
            // Horizontais
            if (jogador.equals(lugares[0].getTag())
                    && jogador.equals(lugares[1].getTag())
                    && jogador.equals(lugares[2].getTag())
                    || jogador.equals(lugares[3].getTag())
                    && jogador.equals(lugares[4].getTag())
                    && jogador.equals(lugares[5].getTag())
                    || jogador.equals(lugares[6].getTag())
                    && jogador.equals(lugares[7].getTag())
                    && jogador.equals(lugares[8].getTag())
                    // Verticais
                    || jogador.equals(lugares[0].getTag())
                    && jogador.equals(lugares[3].getTag())
                    && jogador.equals(lugares[6].getTag())
                    || jogador.equals(lugares[1].getTag())
                    && jogador.equals(lugares[4].getTag())
                    && jogador.equals(lugares[7].getTag())
                    || jogador.equals(lugares[2].getTag())
                    && jogador.equals(lugares[5].getTag())
                    && jogador.equals(lugares[8].getTag())
                    // Diagonais
                    || jogador.equals(lugares[0].getTag())
                    && jogador.equals(lugares[4].getTag())
                    && jogador.equals(lugares[8].getTag())
                    || jogador.equals(lugares[2].getTag())
                    && jogador.equals(lugares[4].getTag())
                    && jogador.equals(lugares[6].getTag())
            ) {
                this.jogadorVencedor = jogador;
                mostrarToastVitoria();
                return;
            }
        }
        for (View lugar : lugares) {
            if (lugar.getTag() == null) {
                return;
            }
        }
        this.empatou = true;
        this.mostrarToastEmpate();
    }

    public void mostrarToastVitoria() {
        String toastText = this.jogadorVencedor == R.drawable.circulo ? "Círculo ganhou!" : "Xis ganhou!";
        Toast.makeText(MainActivity.this, toastText, Toast.LENGTH_SHORT).show();
    }

    public void mostrarToastEmpate() {
        Toast.makeText(MainActivity.this, "Empatou!", Toast.LENGTH_SHORT).show();
    }

    public void perguntarSeQuerComecarUmJogoNovo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("E aí?");
        builder.setMessage("Recomeçar o jogo?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                View[] lugares = {
                        findViewById(R.id.lugar1),
                        findViewById(R.id.lugar2),
                        findViewById(R.id.lugar3),
                        findViewById(R.id.lugar4),
                        findViewById(R.id.lugar5),
                        findViewById(R.id.lugar6),
                        findViewById(R.id.lugar7),
                        findViewById(R.id.lugar8),
                        findViewById(R.id.lugar9),

                };
                for (View lugar : lugares) {
                    lugar.setTag(null);
                    ImageButton lugarImageButton = (ImageButton) lugar;
                    lugarImageButton.setImageDrawable(null);
                }
                jogadorVencedor = null;
                empatou = false;
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}