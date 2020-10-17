package com.example.servingwebcontent.service;


import com.example.servingwebcontent.domain.Message;
import com.example.servingwebcontent.repos.MessageRepo;
import com.sun.xml.bind.v2.model.core.ID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Service
public class MessageService {
    public final MessageRepo messageRepo;
    public MessageService(MessageRepo messageRepo) {
        this.messageRepo = messageRepo;
    }





    public Page<Message> findAllPageable (Pageable pageable){
        return messageRepo.findAll(pageable);
    }

    public Page<Message> findByTag (String tag, Pageable pageable){
        return messageRepo.findByTag(tag,pageable);
    }

    public Page<Message> findByName (String name, Pageable pageable){
        return messageRepo.findByName(name,pageable);
    }

    public Iterable<Message> findAll () {
        return messageRepo.findAll();
    }

    public void deleteById (Integer id){

        messageRepo.deleteById(id);
    }


    public Message saveMessage (Message message) {
        return messageRepo.save(message);
    }




}
