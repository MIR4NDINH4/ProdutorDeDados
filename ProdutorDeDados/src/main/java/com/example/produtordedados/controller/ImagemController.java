package com.example.produtordedados.controller;

import com.example.produtordedados.model.ImagemMessage;
import com.example.produtordedados.model.ImagemModel;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Base64;

@RestController
public class ImagemController {

    @Autowired
    KafkaTemplate<String, ImagemMessage> kafkaTemplate;

    @PostMapping(value = "salvarImagem",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void salvarImagem(@ModelAttribute ImagemModel imagem) throws IOException {
        if (imagem.getImagem()!=null || imagem.getIdPublicacao()!=null){

            String filename = imagem.getImagem().getOriginalFilename();
            String[] arr = filename.split("\\.");
            String extensao = arr[arr.length-1];
            String base64 = Base64.getEncoder().encodeToString(imagem.getImagem().getBytes());
            String string = "";
            ImagemMessage imagemenvia = new ImagemMessage();
            imagemenvia.setExtensaoImagem(extensao);
            imagemenvia.setAcaoImagem("Salvar");
            imagemenvia.setImagem64(base64);
            ProducerRecord<String, ImagemMessage> producerRecord = new ProducerRecord<>("Imagem", null, imagemenvia);
            kafkaTemplate.send(producerRecord);
        }
    }

    @DeleteMapping(value = "deletarImagem/{id}")
    public ResponseEntity<String> deletarImagem(@PathVariable("id") Integer id){
        ImagemMessage imagem= new ImagemMessage();
        imagem.setIdImagem(id);
        imagem.setAcaoImagem("deletar");
        ProducerRecord<String, ImagemMessage> producerRecord = new ProducerRecord<>("Imagem", id.toString(), imagem);
        kafkaTemplate.send(producerRecord);
        return new ResponseEntity<>(HttpStatusCode.valueOf(200));
    }
}
