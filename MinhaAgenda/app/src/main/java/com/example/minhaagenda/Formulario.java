package com.example.minhaagenda; // Mantenha o seu pacote!

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.minhaagenda.database.AppDatabase;
import com.example.minhaagenda.database.Contato;
import com.example.minhaagenda.database.ContatoDAO;
import com.example.minhaagenda.databinding.ActivityFormularioBinding;

public class Formulario extends AppCompatActivity {

    private ActivityFormularioBinding binding;
    private ContatoDAO contatoDAO;
    private Contato contatoSendoEditado = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityFormularioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        AppDatabase db = AppDatabase.getDatabase(this);
        contatoDAO = db.contatoDAO();

        if (getIntent().hasExtra("contato_selecionado")) {
            contatoSendoEditado = (Contato) getIntent().getSerializableExtra("contato_selecionado");

            binding.editNome.setText(contatoSendoEditado.getNome());
            binding.editTelefone.setText(contatoSendoEditado.getTelefone());
            binding.editEmail.setText(contatoSendoEditado.getEmail());
            binding.editEndereco.setText(contatoSendoEditado.getEndereco());
            binding.editSite.setText(contatoSendoEditado.getSite());

            binding.botaoSalvar.setText("ATUALIZAR CONTATO");
        }

        binding.botaoSalvar.setOnClickListener(view -> {
            salvarContato();
        });
    }

    private void salvarContato() {
        String nomeDigitado = binding.editNome.getText().toString();
        String telefoneDigitado = binding.editTelefone.getText().toString();
        String emailDigitado = binding.editEmail.getText().toString();
        String enderecoDigitado = binding.editEndereco.getText().toString();
        String siteDigitado = binding.editSite.getText().toString();

        if (nomeDigitado.isEmpty()) {
            binding.layoutNome.setError("O nome é obrigatório!");
            return;
        }

        if (contatoSendoEditado != null) {

            contatoSendoEditado.setNome(nomeDigitado);
            contatoSendoEditado.setTelefone(telefoneDigitado);
            contatoSendoEditado.setEmail(emailDigitado);
            contatoSendoEditado.setEndereco(enderecoDigitado);
            contatoSendoEditado.setSite(siteDigitado);

            contatoDAO.alteraContato(contatoSendoEditado);
            Toast.makeText(this, "Contato atualizado com sucesso!", Toast.LENGTH_SHORT).show();

        } else {
            Contato novoContato = new Contato();
            novoContato.setNome(nomeDigitado);
            novoContato.setTelefone(telefoneDigitado);
            novoContato.setEmail(emailDigitado);
            novoContato.setEndereco(enderecoDigitado);
            novoContato.setSite(siteDigitado);

            contatoDAO.inserirContato(novoContato);
            Toast.makeText(this, "Contato salvo com sucesso!", Toast.LENGTH_SHORT).show();
        }

        finish();
    }
}