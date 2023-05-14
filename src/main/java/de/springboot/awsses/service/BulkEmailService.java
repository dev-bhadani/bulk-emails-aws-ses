package de.springboot.awsses.service;

public interface BulkEmailService {

    public void sendBulkEmailAWSSES();

    void createTemplate();

    void deleteTemplate();

}
