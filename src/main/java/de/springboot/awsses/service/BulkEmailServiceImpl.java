package de.springboot.awsses.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.BulkEmailDestination;
import com.amazonaws.services.simpleemail.model.BulkEmailDestinationStatus;
import com.amazonaws.services.simpleemail.model.CreateTemplateRequest;
import com.amazonaws.services.simpleemail.model.CreateTemplateResult;
import com.amazonaws.services.simpleemail.model.DeleteTemplateRequest;
import com.amazonaws.services.simpleemail.model.DeleteTemplateResult;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.SendBulkTemplatedEmailRequest;
import com.amazonaws.services.simpleemail.model.SendBulkTemplatedEmailResult;
import com.amazonaws.services.simpleemail.model.Template;

@Service
public class BulkEmailServiceImpl implements BulkEmailService {

    private String template_name = "Happy-Diwali-Template";

    @Autowired
    AmazonSimpleEmailService amazonSimpleEmailService;

    public List<String> getRecipients(){

        List<String> list = new ArrayList<String>();
        list.add("shaileshgbhadani1998@gmail.com");
      //  list.add("gpbhadani1954@gmail.com");
      //  list.add("sbhadani002@rku.ac.in");
        return list;
    }
    @Override
    public  void createTemplate() {
        Template template = new Template();
        template.setTemplateName(template_name);
        template.setSubjectPart("Send Bulk Email with Template");
        template.setTextPart("Hello Dear ! Happy Diwali!");
        CreateTemplateRequest createTemplateRequest = new CreateTemplateRequest();
        createTemplateRequest.setTemplate(template);
        CreateTemplateResult result = amazonSimpleEmailService.createTemplate(createTemplateRequest);
        System.out.println("template created successfully=================" +  result);
    }

    @Override
    public  void deleteTemplate() {
        DeleteTemplateRequest deleteTemplateRequest = new DeleteTemplateRequest();
        deleteTemplateRequest.setTemplateName(template_name);
        DeleteTemplateResult result = amazonSimpleEmailService.deleteTemplate(deleteTemplateRequest);
        System.out.println("response after template delete" + result);
        System.out.println(template_name+ " template deleted successfully===========");
    }

    @Override
    public void sendBulkEmailAWSSES() {

        List<BulkEmailDestination> listBulkEmailDestination = new ArrayList<BulkEmailDestination>();
        SendBulkTemplatedEmailRequest sendBulkTemplatedEmailRequest = null;

        try {

            for(String email : getRecipients()) {

                BulkEmailDestination bulkEmailDestination = new BulkEmailDestination();
                bulkEmailDestination.setDestination(new Destination(Arrays.asList(email)));
                listBulkEmailDestination.add(bulkEmailDestination);
            }

            sendBulkTemplatedEmailRequest = new SendBulkTemplatedEmailRequest();
            sendBulkTemplatedEmailRequest.setSource("shaileshkumarbhadani@gmail.com");
            sendBulkTemplatedEmailRequest.setTemplate(template_name);
            sendBulkTemplatedEmailRequest.setDefaultTemplateData("{\"FULL_NAME\":\"Shaileshkumar Bhadani\", \"EMAIL\":\"shaileshkumarbhadani@gmail.com\"}");
            sendBulkTemplatedEmailRequest.setDestinations(listBulkEmailDestination);
            SendBulkTemplatedEmailResult res = amazonSimpleEmailService.sendBulkTemplatedEmail(sendBulkTemplatedEmailRequest);

            System.out.println("response from aws ======================================" +res);
            System.out.println("======================================");

            for(BulkEmailDestinationStatus status : res.getStatus()) {
                System.out.println("status==========" + status.getStatus());
                System.out.println("message Id==========" + status.getMessageId());
            }

        }catch (Exception ex) {
            System.out.println("The email was not sent. Error message: " + ex.getMessage());
            ex.printStackTrace();
        }


    }




}
