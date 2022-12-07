package com.example.produtordedados.controller;

import com.example.produtordedados.model.ImagemModel;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
public class ImagemController {

    @Autowired
    KafkaTemplate<String, ImagemModel> kafkaTemplate;

    @PostMapping(value = "salvarImagem")
    public ResponseEntity<String> salvarImagem(@RequestBody ImagemModel imagem){
        if (!imagem.getImagem().toString().isEmpty() || !imagem.getExtensaoImagem().isEmpty()){
            imagem.setAcaoImagem("salvar");
            ProducerRecord<String, ImagemModel> producerRecord = new ProducerRecord<>("Imagem", null, imagem);
            kafkaTemplate.send(producerRecord);
            return new ResponseEntity<>(HttpStatusCode.valueOf(200));
        }else{
            return new ResponseEntity<>(HttpStatusCode.valueOf(400));
        }
    }

    @DeleteMapping(value = "deletarImagem/{id}")
    public ResponseEntity<String> deletarImagem(@PathVariable("id") Integer id){
        ImagemModel imagem = new ImagemModel();
        imagem.setIdImagem(id);
        imagem.setAcaoImagem("deletar");
        ProducerRecord<String, ImagemModel> producerRecord = new ProducerRecord<>("Imagem", id.toString(), imagem);
        kafkaTemplate.send(producerRecord);
        return new ResponseEntity<>(HttpStatusCode.valueOf(200));
    }
}
