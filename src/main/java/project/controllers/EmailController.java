package project.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired

    @GetMapping("/")
    public boolean sendEmail(@RequestParam("to") String to,
                             @RequestParam("from") String from,
                             @RequestParam("subject") String subject,
                             @RequestParam("body") String body){

        return false;

    }
}
