package com.example.buscaModelo.carModels;

import javax.persistence.*;

@Entity
@Table(name = "car_model")
public class CarModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Long id;
    private String versao;
    private String model;
    private String Config;
    private String conteudo;
    private Float preco_nacional;
    private Float preco_zfm;
    private Float preco_ao;
    private Float preco_alc;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }


    public String getVersao() {
        return versao;
    }

    public void setVersao(String versao) {
        this.versao = versao;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getConfig() {
        return Config;
    }

    public void setConfig(String config) {
        Config = config;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public Float getPreco_nacional() {
        return preco_nacional;
    }

    public void setPreco_nacional(Float preco_nacional) {
        this.preco_nacional = preco_nacional;
    }

    public Float getPreco_zfm() {
        return preco_zfm;
    }

    public void setPreco_zfm(Float preco_zfm) {
        this.preco_zfm = preco_zfm;
    }

    public Float getPreco_ao() {
        return preco_zfm;
    }
    public void setPreco_ao(Float preco_zfm) {
        this.preco_zfm = preco_zfm;
    }
    public Float getPreco_alc() {
        return preco_zfm;
    }

    public void setPreco_alc(Float preco_zfm) {
        this.preco_zfm = preco_zfm;
    }

}
