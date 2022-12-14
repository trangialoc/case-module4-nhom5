package com.codegym.vn.controller.admin;

import com.codegym.vn.model.AppUser;
import com.codegym.vn.service.interfaceImpl.IAppUserService;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

import static com.codegym.vn.mailConfig.TeleAPI.web_hook_url;

@RestController
@CrossOrigin("*")
@RequestMapping("/admin/users")
public class AppUserController {
    @Autowired
    public JavaMailSender mailSender;
    @Autowired
    private IAppUserService iAppUserService;

    @GetMapping
    public ResponseEntity<Iterable<AppUser>> showAll() {
        Iterable<AppUser> users = iAppUserService.findAll();
        if (!users.iterator().hasNext()) {
            new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppUser> showOne(@PathVariable("id") Long id) {
        Optional<AppUser> appUser = iAppUserService.findById(id);
        if (!appUser.isPresent()) {
            new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(appUser.get(), HttpStatus.OK);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<AppUser>> showPage(@PageableDefault(value =5) Pageable pageable) {
        Page<AppUser> users = iAppUserService.findPage(pageable);
        if (!users.iterator().hasNext()) {
            new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<AppUser> createAppUser(@RequestPart("json") AppUser appUser) {
        AppUser appUser1 = iAppUserService.save(appUser);

        if(appUser1 != null){
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(appUser1.getEmail());
            message.setSubject("Sign up account confirm email");
            message.setText("Hello, Your Account just have created in CODEGYM EDUCATION \n" + "with username: " + appUser1.getUsername() +"\n"
                    + "and password: " + appUser1.getPassword());
            this.mailSender.send(message);
        }

        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(web_hook_url);

        try {
            String json = "{Xin chuc mung, ban vua dang ki thanh cong tai hoan moi}";
            System.out.println(json);
            StringEntity entity = new StringEntity(json);
            httpPost.setEntity(entity);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            client.execute(httpPost);

            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<AppUser> editStudent(@RequestPart("json") AppUser appUserEdit,
                                               @PathVariable("id") Long id){
        Optional<AppUser> appUser = iAppUserService.findById(id);
        if(!appUser.isPresent()){
            new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        appUserEdit.setId(appUser.get().getId());
        appUserEdit= iAppUserService.save(appUserEdit);
        return new ResponseEntity<>(appUserEdit,HttpStatus.OK);

    }

    @DeleteMapping("{id}")
    public ResponseEntity<AppUser> delete(@PathVariable("id") Long id){
        Optional<AppUser> appUser = iAppUserService.findById(id);
        if(!appUser.isPresent()){
            new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        iAppUserService.delete(id);
        return new ResponseEntity<>(appUser.get(),HttpStatus.OK);
    }

    @GetMapping("/search")
    public  ResponseEntity<Iterable<AppUser>> showAllByName(@RequestParam("search") String search){
        Iterable<AppUser> appUsers = iAppUserService.findByName(search);
        if(!appUsers.iterator().hasNext()){
            new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(appUsers,HttpStatus.OK);
    }
}
