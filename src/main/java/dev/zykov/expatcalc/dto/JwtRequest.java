package dev.zykov.expatcalc.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record JwtRequest(@Email @NotBlank String email) {

}
