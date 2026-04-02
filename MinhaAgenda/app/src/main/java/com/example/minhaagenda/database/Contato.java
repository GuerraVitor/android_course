package com.example.minhaagenda.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;

@Entity(tableName = "Contatos")
public class Contato implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    private String nome;
    private String email;
    private String site;
    private String telefone;
    private String endereco;
    private String caminhoFoto;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSite() { return site; }
    public void setSite(String site) { this.site = site; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    public String getCaminhoFotoFoto() { return caminhoFoto; }
    public void setCaminhoFotoFoto(String caminhoFoto) { this.caminhoFoto = caminhoFoto; }
}
