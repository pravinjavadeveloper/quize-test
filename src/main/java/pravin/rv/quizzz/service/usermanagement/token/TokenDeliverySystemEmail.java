
package pravin.rv.quizzz.service.usermanagement.token;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import pravin.rv.quizzz.model.TokenModel;
import pravin.rv.quizzz.model.TokenType;
import pravin.rv.quizzz.model.User;



@Service
public class TokenDeliverySystemEmail implements TokenDeliverySystem {

    private static final String BASE_CONFIG_URI = "quizzz.tokens.%s";

    private MessageSource messageSource;
    private JavaMailSender mailSender;



    @Value("${region}")
    private String region;

    String FROM = "pravinjavad@gmail.com";
    String FROMNAME = "no reply";
    static final int PORT = 587;
    //ses-smtp-user.20210716-015356
    static final String SMTP_USERNAME = "AKIAUO3BJXEYM4UH6C42";
    static final String HOST = "email-smtp.us-west-2.amazonaws.com";
    static final String SMTP_PASSWORD = "D5qBOhqcjPsk7gbQf5uqdmoH2FNdiXg9OUp3BvcI";

    @Value("${app.email.host}")
    private String SMTP_HOST_NAME;  
    
    @Value("${app.email.user}")
    private String SMTP_AUTH_USER;  
    
    @Value("${app.email.password}")
    private String SMTP_AUTH_PWD;   
    
    @Value("${app.email.port}")
    private int SMTP_HOST_PORT; 
    
    

    @Autowired
    public TokenDeliverySystemEmail(MessageSource messageSource, JavaMailSender mailSender) {
        this.messageSource = messageSource;
        //this.mailSender = mailSender;   ses-smtp-user.20210716-022217
    }

    @Override
    public void sendTokenToUser(TokenModel token, User user, TokenType tokenType) {



        
        String base_config = String.format(BASE_CONFIG_URI, tokenType.toString().toLowerCase());
        String url = String.format(messageSource.getMessage(base_config + ".url", null, null), user.getId(),
                token.getToken());

        try {
            sendByMail(user, url, base_config);
        } catch (Exception e) {
            // This runs on a thread so it is too late to notify the user. A
            // re-try mechanism could be put in place.
            e.printStackTrace();
        }
    }

    private void sendByMail(User user, String url, String base_config) throws MessagingException, UnsupportedEncodingException {
        // AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        // com.amazonaws.services.simpleemail.AmazonSimpleEmailService client = AmazonSimpleEmailServiceClientBuilder
        //      .standard().withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion(region).build();

        String subject = messageSource.getMessage(base_config + ".subject", null, null);
        String body = String.format(messageSource.getMessage(base_config + ".body", null, null), user.getUsername(),
                url);

        // SimpleMailMessage mailMessage = new SimpleMailMessage();

        // mailMessage.setTo(user.getEmail());
        // mailMessage.setFrom("pravinjavad@gmail.com");
        // mailMessage.setSubject(subject);
        // mailMessage.setText(body);

        // mailSender.send(mailMessage);


        //final String TO = "example@gmail.com"; // {YOUR_RECIPIENT_EMAIL_ADDRESS}

            Properties props = new Properties();
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.host", SMTP_HOST_NAME);
            props.put("mail.smtp.auth", "false");
            props.put("mail.smtp.starttls.enable", "true");

            Session mailSession = Session.getDefaultInstance(props);
            mailSession.setDebug(true);

            Transport transport = mailSession.getTransport("smtp");

            MimeMessage message = new MimeMessage(mailSession);

            message.setSubject(subject);
            message.setContent(body, "text/html");
        //  message.setSentDate(new Date());
            message.setFrom(new InternetAddress(FROM));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));
                                                                                    
            transport.connect(SMTP_HOST_NAME, SMTP_HOST_PORT, SMTP_AUTH_USER, SMTP_AUTH_PWD);
            transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
            transport.close();
            System.out.println("email sent successfully........");
        
    }
    
}

