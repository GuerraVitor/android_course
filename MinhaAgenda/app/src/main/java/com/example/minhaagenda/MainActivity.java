package com.example.minhaagenda;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.minhaagenda.database.AppDatabase;
import com.example.minhaagenda.database.Contato;
import com.example.minhaagenda.database.ContatoDAO;
import com.example.minhaagenda.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ContatoDAO contatoDAO;
    private ContatoAdapter adaptador;
    private String telefoneParaLigar;

    private final ActivityResultLauncher<String> lançadorPermissaoLigacao = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            isGranted -> {
                if (isGranted) {
                    fazerLigacao(telefoneParaLigar);
                } else {
                    Toast.makeText(this, "Permissão negada para ligações.", Toast.LENGTH_SHORT).show();
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        AppDatabase db = AppDatabase.getDatabase(this);
        contatoDAO = db.contatoDAO();

        binding.minhaLista.setLayoutManager(new LinearLayoutManager(this));

        binding.fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, Formulario.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        atualizarLista();
    }

    private void atualizarLista() {
        List<Contato> contatosSalvos = contatoDAO.getLista();

        adaptador = new ContatoAdapter(contatosSalvos, new ContatoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Contato contato, View viewClicada) {
                mostrarMenu(contato, viewClicada);
            }
        });

        binding.minhaLista.setAdapter(adaptador);
    }

    private void mostrarMenu(Contato contatoClicado, View viewClicada) {
        PopupMenu popup = new PopupMenu(this, viewClicada);

        popup.getMenu().add("Ligar");
        popup.getMenu().add("Enviar SMS");
        popup.getMenu().add("Abrir Site");
        popup.getMenu().add("Editar");
        popup.getMenu().add("Apagar");

        popup.setOnMenuItemClickListener(item -> {
            switch (item.getTitle().toString()) {
                case "Ligar":
                    telefoneParaLigar = contatoClicado.getTelefone();
                    lançadorPermissaoLigacao.launch(android.Manifest.permission.CALL_PHONE);
                    return true;

                case "Enviar SMS":
                    enviarSMS(contatoClicado.getTelefone());
                    return true;

                case "Abrir Site":
                    abrirSite(contatoClicado.getSite());
                    return true;

                case "Editar":
                    Intent intentEdicao = new Intent(MainActivity.this, Formulario.class);
                    intentEdicao.putExtra("contato_selecionado", contatoClicado);
                    startActivity(intentEdicao);
                    return true;

                case "Apagar":
                    contatoDAO.apagarContato(contatoClicado);
                    atualizarLista();
                    Toast.makeText(MainActivity.this, "Contato apagado!", Toast.LENGTH_SHORT).show();
                    return true;
            }
            return false;
        });
        popup.show();
    }

    private void fazerLigacao(String telefone) {
        if (telefone == null || telefone.isEmpty()) return;

        Intent intentLigacao = new Intent(Intent.ACTION_CALL);
        intentLigacao.setData(android.net.Uri.parse("tel:" + telefone));
        startActivity(intentLigacao);
    }

    private void enviarSMS(String telefone) {
        if (telefone == null || telefone.isEmpty()) return;

        Intent intentSms = new Intent(Intent.ACTION_VIEW);
        intentSms.setData(android.net.Uri.parse("sms:" + telefone));
        intentSms.putExtra("sms_body", "Olá! Mensagem enviada pelo meu novo app.");
        startActivity(intentSms);
    }

    private void abrirSite(String site) {
        if (site == null || site.isEmpty()) return;

        if (!site.startsWith("http://") && !site.startsWith("https://")) {
            site = "http://" + site;
        }

        Intent intentSite = new Intent(Intent.ACTION_VIEW);
        intentSite.setData(android.net.Uri.parse(site));
        startActivity(intentSite);
    }
}