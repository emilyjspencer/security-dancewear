package securitydancewear.securitydancewear.payload;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class RegistrationDTO {
    private String username;
    private String password;

    private String userType;



    public RegistrationDTO(String username, String password, String userType) {
        super();
        this.username = username;
        this.password = password;
        this.userType  = userType;
    }

    public String getUsername(){
        return this.username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getPassword(){
        return this.password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String toString(){
        return "Registration info: username: " + this.username + " password: " + this.password;
    }

}
