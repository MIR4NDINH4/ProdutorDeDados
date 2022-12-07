package com.example.produtordedados.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.awt.image.BufferedImage;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ImagemModel {
    private Integer idImagem;
    private String extensaoImagem;
    private byte[] imagem;
    private String acaoImagem;
}
