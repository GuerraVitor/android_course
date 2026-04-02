package com.example.minhaagenda;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.minhaagenda.database.Contato;
import com.example.minhaagenda.databinding.CelulaContatoBinding;

import java.util.List;

public class ContatoAdapter extends RecyclerView.Adapter<ContatoAdapter.ContatoViewHolder> {

    private List<Contato> listaDeContatos;
    private OnItemClickListener listener; // Nosso ouvinte de cliques!

    public interface OnItemClickListener {
        void onItemClick(Contato contato, View viewClicada);
    }

    public ContatoAdapter(List<Contato> listaDeContatos, OnItemClickListener listener) {
        this.listaDeContatos = listaDeContatos;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ContatoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CelulaContatoBinding binding = CelulaContatoBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ContatoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ContatoViewHolder holder, int position) {
        Contato contatoAtual = listaDeContatos.get(position);

        holder.binding.nomeCelula.setText(contatoAtual.getNome());
        holder.binding.telefoneCelula.setText(contatoAtual.getTelefone());
        holder.binding.emailCelula.setText(contatoAtual.getEmail());

        holder.binding.getRoot().setOnClickListener(view -> {
            if (listener != null) {
                listener.onItemClick(contatoAtual, holder.binding.getRoot());
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaDeContatos.size();
    }

    public static class ContatoViewHolder extends RecyclerView.ViewHolder {
        CelulaContatoBinding binding;

        public ContatoViewHolder(CelulaContatoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}