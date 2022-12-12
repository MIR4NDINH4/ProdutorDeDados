package com.example.produtordedados.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ImagemMessage {
    private Integer idImagem;
    private Integer idPublicacao;
    private String imagem64;
    private String extensaoImagem;
    private String acaoImagem;
}
