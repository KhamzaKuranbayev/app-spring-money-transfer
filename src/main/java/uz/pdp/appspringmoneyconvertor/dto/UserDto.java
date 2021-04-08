package uz.pdp.appspringmoneyconvertor.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class UserDto {

    @NotNull
    @Email
    private String username;

    @NotNull
    private String password;
}
