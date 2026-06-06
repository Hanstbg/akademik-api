package org.delcom.app.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.delcom.app.entities.User.Role;

public class RegisterForm {

    @NotBlank(message = "Nama tidak boleh kosong")
    private String name;

    @NotBlank(message = "Email tidak boleh kosong")
    @Email(message = "Format email tidak valid")
    private String email;

    @NotBlank(message = "Password tidak boleh kosong")
    private String password;

    @NotNull(message = "Role tidak boleh kosong")
    private Role role;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
}
