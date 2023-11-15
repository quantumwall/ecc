package dev.zykov.expatcalc.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import dev.zykov.expatcalc.dto.JwtRequest;
import dev.zykov.expatcalc.dto.JwtResponse;
import dev.zykov.expatcalc.service.UserService;
import dev.zykov.expatcalc.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import dev.zykov.expatcalc.exception.Error;

@RestController
@RequiredArgsConstructor
public class AuthorizeController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/authorize")
    public ResponseEntity<?> getToken(@RequestBody @Valid JwtRequest request, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest()
                .body(new Error(HttpStatus.BAD_REQUEST.value(), errors.getFieldError().getDefaultMessage()));
        }
        var user = userService.findByEmail(request.email());
        if (user.isPresent()) {
            var token = jwtUtil.generateToken(user.get());
            return ResponseEntity.ok().body(new JwtResponse(token));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(new Error(HttpStatus.UNAUTHORIZED.value(), "User %s not found".formatted(request.email())));
    }

}
