package dk.palmelundvoldby.authtest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
public class AuthenticateController {
    @RequestMapping(path = "/settoken", method = RequestMethod.GET, produces = "application/text")
    public ResponseEntity setToken(HttpServletResponse response) {
        Cookie cookie = new Cookie("token", "supersecrettoken!");
        response.addCookie(cookie);
        return ResponseEntity.ok("Token is set");
    }

    @RequestMapping(path = "/gettoken", method = RequestMethod.GET)
    public ResponseEntity getToken(@CookieValue(value = "token") String token) {
        return ResponseEntity.ok(token);
    }

    @RequestMapping(path = "/bgtoken", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity getBackgroundToken(@CookieValue(value = "token") String token) {
        String content = "<!DOCTYPE html>\n" +
                "<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js\"></script>" +
                "<html>\n" +
                "<head>\n" +
                "<title>Page Title</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "Check console" +
                "</body>\n" +
                "</html>" +
                "<script>" +
                "console.log('" + token+ "');" +
                "$.ajax({" +
                "url:'http://localhost:8080/gettoken'," +
                "method: 'GET'," +
                "success: function(response) {" +
                "console.log(response);" +
                "}" +
                "});" +
                "</script>";

        return ResponseEntity.ok(content);
    }
}
