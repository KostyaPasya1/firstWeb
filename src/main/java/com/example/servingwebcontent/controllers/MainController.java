package com.example.servingwebcontent.controllers;

import com.example.servingwebcontent.domain.Message;
import com.example.servingwebcontent.service.MessageService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;

@Controller
public class MainController {


    public final MessageService messageService;
    public MainController( MessageService messageService) {
        this.messageService = messageService;
    }


    @GetMapping("/")
    public String greeting(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authName = auth.getName();//get logged in username
        model.addAttribute("name",authName);
        return "greeting";
    }


    @GetMapping("/main")
    public String main(Model model,
                       HttpServletRequest request) {

        int page = 0; //default page number is 0
        int size = 12; //default page size is 10

        if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
            page = Integer.parseInt(request.getParameter("page")) - 1;
        }

        if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
            size = Integer.parseInt(request.getParameter("size"));
        }


        model.addAttribute("messages", messageService.findAllPageable(PageRequest.of(page, size,Sort.by("id").descending())));




        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authName = auth.getName();//get logged in username
        model.addAttribute("getAuthentication", authName);


        return "main.html";
    }


    @PostMapping(value = "/main")
    public String add (Message message){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();//get logged in username
        message.setName(name);



        messageService.saveMessage(message);


        return "redirect:/main";
    }




    @GetMapping("/main/filter/{name}")
    public  String filterByName (@PathVariable("name") String name,
                              Model model, HttpServletRequest request) {

        int page = 0; //default page number is 0
        int size = 12; //default page size is 10

        if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
            page = Integer.parseInt(request.getParameter("page")) - 1;
        }
        if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
            size = Integer.parseInt(request.getParameter("size"));
        }
        model.addAttribute("messages", messageService.findByName(name, PageRequest.of(page, size,Sort.by("id").descending())));




        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authName = auth.getName();//get logged in username
        model.addAttribute("getAuthentication", authName);
        return "filter.html";
    }

    @GetMapping("/main/filterByTag/{tag}")
    public  String filterByTag (@PathVariable("tag") String tag,
                                 Model model, HttpServletRequest request) {

        int page = 0; //default page number is 0
        int size = 12; //default page size is 10

        if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
            page = Integer.parseInt(request.getParameter("page")) - 1;
        }
        if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
            size = Integer.parseInt(request.getParameter("size"));
        }
        model.addAttribute("messages", messageService.findByTag(tag, PageRequest.of(page, size,Sort.by("id").descending())));




        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authName = auth.getName();//get logged in username
        model.addAttribute("getAuthentication", authName);

        return "filter.html";
    }



    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @GetMapping("/main/delete/{id}")
    public String messageDeleteForm (@PathVariable("id") Integer id) {
        messageService.deleteById(id);
        return "redirect:/main";
    }



}
