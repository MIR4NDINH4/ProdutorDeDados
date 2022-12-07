package com.example.produtordedados.controller;

import com.example.produtordedados.model.PublicacaoModel;
import com.example.produtordedados.model.UsuarioModel;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.concurrent.CompletableFuture;

@RestController
public class PublicacaoController {

    @Autowired
    KafkaTemplate<String, PublicacaoModel> kafkaTemplate;

    @PostMapping(value = "salvarPublicacao")
    public ResponseEntity<String> salvarPublicacao(@RequestBody PublicacaoModel pub){
        if (!pub.getTituloPublicacao().isEmpty() || !pub.getTextoPublicacao().isEmpty()){
            pub.setAcaoPublicacao("salvar");
            pub.setDataHoraPublicacao(pub.getDataHoraPublicacao());
            ProducerRecord<String, PublicacaoModel> producerRecord = new ProducerRecord<>("Postagem", null, pub);
            kafkaTemplate.send(producerRecord);
            return new ResponseEntity<>(HttpStatusCode.valueOf(200));
        }else{
            return new ResponseEntity<>(HttpStatusCode.valueOf(400));
        }
    }

    @PutMapping(value = "alterarPublicacao/{id}")
    public ResponseEntity<String> alterarPublicacao(@RequestBody PublicacaoModel pub, @PathVariable("id") Integer id){
        if(!pub.getTituloPublicacao().isEmpty() || !pub.getTextoPublicacao().isEmpty()){
            pub.setIdPublicacao(id);
            pub.setAcaoPublicacao("alterar");
            pub.setDataHoraPublicacao(pub.getDataHoraPublicacao());
            ProducerRecord<String, PublicacaoModel> producerRecord = new ProducerRecord<>("Postagem", id.toString(), pub);
            kafkaTemplate.send(producerRecord);
            return new ResponseEntity<>(HttpStatusCode.valueOf(200));
        }else{
            return new ResponseEntity<>(HttpStatusCode.valueOf(400));
        }
    }

    @DeleteMapping(value = "deletarPublicacao/{id}")
    public ResponseEntity<String> deletarPublicacao(@PathVariable("id") Integer id){
        PublicacaoModel pub = new PublicacaoModel();
        pub.setIdPublicacao(id);
        pub.setAcaoPublicacao("deletar");
        pub.setDataHoraPublicacao(pub.getDataHoraPublicacao());
        ProducerRecord<String, PublicacaoModel> producerRecord = new ProducerRecord<>("Postagem", id.toString(), pub);
        kafkaTemplate.send(producerRecord);

        return new ResponseEntity<>(HttpStatusCode.valueOf(200));
    }

}
