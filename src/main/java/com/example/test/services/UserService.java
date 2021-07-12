package com.example.test.services;

import com.example.test.entities.User;
import com.example.test.repos.UserRepository;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Service
public class UserService implements UserDetailsService
{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender javaMailSender;

    public void save(User user){
        userRepository.save(user);
    }

    public User loadByUserId(Long id){
        return userRepository.getById(id);
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException{
        return userRepository.findByUsername(username);
    }

    public User getById(Long id){
        return userRepository.getById(id);
    }

    public void deleteById(Long id){
        userRepository.delete(userRepository.getById(id));
    }

    public void register(User user, HttpServletRequest request)
            throws UnsupportedEncodingException, MessagingException {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        String randomCode = RandomString.make(64);
        user.setVerificationCode(randomCode);
        user.setEnabled(false);

        userRepository.save(user);

        String siteURL = getSiteURL(request);
        sendVerificationCode(user, siteURL);
    }

    public void sendVerificationCode(User user, String siteURL)
            throws MessagingException, UnsupportedEncodingException {
        String toAdress = user.getEmail();
        String fromAdress = "fanbooksupserv@gmail.com";
        String senderName = "Fanbook Support Service";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "Your company name.";

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAdress, senderName);
        helper.setTo(toAdress);
        helper.setSubject(content);

        content = content.replace("[[name]]", user.getName());
        String verifyURL = siteURL + "/verify?code=" + user.getVerificationCode();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content,true);

        javaMailSender.send(message);
    }

    private String getSiteURL(HttpServletRequest request){
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(),"");
    }

    public boolean verify(String verificationCode) {
        User user = userRepository.findByVerificationCode(verificationCode);

        if (user == null || user.isEnabled()) {
            return false;
        } else {
            user.setVerificationCode(null);
            user.setEnabled(true);
            userRepository.save(user);

            return true;
        }

    }



}
