package com.example.servingwebcontent.repos;

import com.example.servingwebcontent.domain.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;



public interface MessageRepo extends CrudRepository <Message, Integer> {


    Page<Message> findAll (Pageable pageable);

    Page<Message> findByTag (String tag, Pageable pageable);

    Page<Message> findByName (String name, Pageable pageable);




}
