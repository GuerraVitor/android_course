package com.example.quizz;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.app.AlertDialog;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public RadioGroup radioGroup;
    public TextView textoPergunta;

    public RadioButton opcaoA;
    public RadioButton opcaoB;
    public RadioButton opcaoC;

    public Button botaoOk;

    String Perguntas[] = {"Primeira pergunta?",
            "Segunda  pergunta?",
            "Terceira pergunta?",
            "Quarta pergunta?",
            "Quinta pergunta?"};

    String OpcaoA[] = {"Resposta A primeira pergunta.",
            "Resposta A segunda pergunta.",
            "Resposta A terceira pergunta.",
            "Resposta A quarta pergunta.",
            "Resposta A quinta pergunta."};

    String OpcaoB[] = {"Resposta B primeira pergunta.",
            "Resposta B segunda pergunta.",
            "Resposta B terceira pergunta.",
            "Resposta B quarta pergunta.",
            "Resposta B quinta pergunta."};

    String OpcaoC[] = {"Resposta C primeira pergunta.",
            "Resposta C segunda pergunta.",
            "Resposta C terceira pergunta.",
            "Resposta C quarta pergunta.",
            "Resposta C quinta pergunta."};

    int[] listaRespostas = new int[Perguntas.length];
    int listaGabarito[] = {1, 2, 3, 1, 2};

    int respostasCorretas = 0;
    int numeroPergunta = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botaoOk = (Button) findViewById(R.id.botaoOk);
        botaoOk.setEnabled(false);

        textoPergunta = (TextView) findViewById(R.id.campoTexto);

        opcaoA = (RadioButton) findViewById(R.id.opcaoA);
        opcaoB = (RadioButton) findViewById(R.id.opcaoB);
        opcaoC = (RadioButton) findViewById(R.id.opcaoC);

        radioGroup = (RadioGroup) findViewById(R.id.grupoRadio);

        // Prepara a primeira pergunta ao abrir o app
        atualizaPerguntas(botaoOk);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                // CORREÇÃO: Uso do if/else if em vez do switch
                if (checkedId == R.id.opcaoA) {
                    Log.d("s", "Opcao n1!");
                    listaRespostas[numeroPergunta - 1] = 1;
                } else if (checkedId == R.id.opcaoB) {
                    Log.d("s", "Opcao n2!");
                    listaRespostas[numeroPergunta - 1] = 2;
                } else if (checkedId == R.id.opcaoC) {
                    Log.d("s", "Opcao n3!");
                    listaRespostas[numeroPergunta - 1] = 3;
                }

                botaoOk.setEnabled(true);
            }
        });
    }

    public void atualizaPerguntas(View view) {

        if (numeroPergunta == Perguntas.length) {
            opcaoA.setEnabled(false);
            opcaoB.setEnabled(false);
            opcaoC.setEnabled(false);
            radioGroup.clearCheck();
            confereResultado();
        } else {
            textoPergunta.setText(Perguntas[numeroPergunta]);

            opcaoA.setText(OpcaoA[numeroPergunta]);
            opcaoB.setText(OpcaoB[numeroPergunta]);
            opcaoC.setText(OpcaoC[numeroPergunta]);

            numeroPergunta++;
            botaoOk.setEnabled(false);
            radioGroup.clearCheck();
        }
    }

    public void confereResultado() {
        int contadorLista = 0;
        for (int numero : listaRespostas) {
            System.out.println(numero);
            if (numero == listaGabarito[contadorLista]) {
                respostasCorretas++;
                System.out.println("Resposta Correta!!!");
            } else {
                System.out.println("Resposta Errada!!!");
            }
            contadorLista++;
        }
        alertaResultado(botaoOk);
    }

    public void alertaResultado(View view) {
        AlertDialog alertDialog;
        alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Resultado!");
        alertDialog.setMessage("Voce acertou " + respostasCorretas + " questoes!");
        alertDialog.show();
        botaoOk.setEnabled(false);
    }
}