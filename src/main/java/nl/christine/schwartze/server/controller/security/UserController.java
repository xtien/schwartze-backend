package nl.christine.schwartze.server.controller.security;

import nl.christine.schwartze.server.Application;
import nl.christine.schwartze.server.controller.request.LoginRequest;
import nl.christine.schwartze.server.controller.result.AddLetterResult;
import nl.christine.schwartze.server.controller.result.LoginResult;
import nl.christine.schwartze.server.model.User;
import nl.christine.schwartze.server.security.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@CrossOrigin(origins = Application.UI_HOST)
public class UserController {

    @Autowired
    private UserRepository repository;

    @PostMapping(value = "/login/")
    public ResponseEntity<LoginResult> login(@RequestBody LoginRequest loginRequest) {

        HttpStatus status;
        LoginResult loginResult = new LoginResult();
        User user = repository.findByUsername(loginRequest.getUserName());
        String pwd = loginRequest.getPw();
        BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
        String hashPwd = bc.encode(pwd);
        if (hashPwd != null && hashPwd.equals(pwd)) {
            loginResult.setToken("token");
            status = HttpStatus.OK;
        } else {
            status = HttpStatus.FORBIDDEN;
        }
        return new ResponseEntity<>(loginResult, status);
    }

    @PostMapping(value = "/signup/")
    public ResponseEntity<LoginResult> signup(@RequestBody LoginRequest signupRequest) {

        HttpStatus status;
        LoginResult loginResult = new LoginResult();
        String pwd = signupRequest.getPw();
        BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
        String hashPwd = bc.encode(pwd);
        User newUser = new User();
        newUser.setPasswordHash(hashPwd);
        newUser.setUsername(signupRequest.getUserName());
        newUser.setRole("USER");
        if (repository.findByUsername(signupRequest.getUserName()) == null) {
            repository.save(newUser);
            status = HttpStatus.OK;
        } else {
            status = HttpStatus.FORBIDDEN;
        }
        return new ResponseEntity<>(loginResult, status);
    }

}
