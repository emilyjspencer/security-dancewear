package securitydancewear.securitydancewear.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dance-teacher")
@CrossOrigin("*")
public class DanceTeacherController {

    @GetMapping("/")
    public String helloDanceTeacherController() {
        return "Dance teacher access level";
    }
}
