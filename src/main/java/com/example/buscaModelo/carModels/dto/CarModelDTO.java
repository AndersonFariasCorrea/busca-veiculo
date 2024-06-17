package com.example.buscaModelo.carModels.dto;

public class CarModelDTO {
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
        return preco_ao;
    }
    public void setPreco_ao(Float preco_ao) {
        this.preco_ao = preco_ao;
    }
    public Float getPreco_alc() {
        return preco_alc;
    }
    public void setPreco_alc(Float preco_alc) {
        this.preco_alc = preco_alc;
    }
}

