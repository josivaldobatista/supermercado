package com.jfb.supermercado.api.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class EmailDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "Preenchimento obrigatório!")
    @Email(message = "Email invalído!")
    private String email;

    public EmailDTO() {
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}