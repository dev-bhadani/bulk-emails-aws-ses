package de.springboot.awsses.controller;
import de.springboot.awsses.service.BulkEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BulkEmailController {

    @Autowired
    BulkEmailService bulkEmailService;

    @GetMapping("/sendBulkEmail")
    public String sendBulkEmail() {

        bulkEmailService.createTemplate();
        try {
            bulkEmailService.sendBulkEmailAWSSES();
        } finally {
            bulkEmailService.deleteTemplate();
        }
        return "email sent successfully";



    }

}
