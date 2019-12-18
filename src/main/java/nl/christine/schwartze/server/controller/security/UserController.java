package nl.christine.schwartze.server.controller.security;

import nl.christine.schwartze.server.Application;
import nl.christine.schwartze.server.controller.LetterGetAllController;
import nl.christine.schwartze.server.controller.request.LoginRequest;
import nl.christine.schwartze.server.controller.result.LoginResult;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

@Controller
@CrossOrigin(origins = Application.UI_HOST)
public class UserController {

    Logger logger = Logger.getLogger(UserController.class);

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping(value = "/login/")
    @Transactional("userTransactionManager")
    public ResponseEntity<LoginResult> login(@RequestBody LoginRequest loginRequest) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        LoginResult loginResult = new LoginResult();

        try{

            UsernamePasswordAuthenticationToken authReq
                    = new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPw());
            Authentication auth = authenticationManager.authenticate(authReq);

            SecurityContext sc = SecurityContextHolder.getContext();
            sc.setAuthentication(auth);

            if(auth.isAuthenticated()){
                loginResult.setAuthorities(auth.getAuthorities());
                status = HttpStatus.OK;
            }

         } catch(UsernameNotFoundException ex){
            status = HttpStatus.UNAUTHORIZED;
        }
         return new ResponseEntity<>(loginResult, status);
    }
}
