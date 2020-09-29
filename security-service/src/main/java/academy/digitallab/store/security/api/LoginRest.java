package academy.digitallab.store.security.api;


import academy.digitallab.store.security.auth.util.Constant;
import academy.digitallab.store.security.domain.model.TokenDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@RequestMapping(value = "/security")
@RestController
public class LoginRest {

    @RequestMapping(value="/login" , method = RequestMethod.POST)
    public ResponseEntity<TokenDto> getUser(HttpServletResponse res){
        log.debug("login - inicio");

        TokenDto tokenBean = new TokenDto();
        String token =res.getHeader( Constant.TOKEN_HEADER_STRING );
        if (token !=null){

            tokenBean.setToken(token);
            return new ResponseEntity<TokenDto>(tokenBean, HttpStatus.OK);
        }else{
            return new ResponseEntity<TokenDto>(tokenBean, HttpStatus.UNAUTHORIZED);
        }

    }

    @RequestMapping(value="/welcome" , method = RequestMethod.GET)
    public String welcome(){
        return "welcome";
    }

}