package com.school.controllers;

import com.school.controllers.dto.user.UserDto;
import com.school.persistence.entities.User;
import com.school.services.UserService;
import com.school.services.mapper.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserMapper mapper;
    private final UserService userService;

    @Operation(summary = "Authentication", description = "após login com google entra no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "user login successful!"),
            @ApiResponse(responseCode = "401", description = "user unauthorized!") })
    @GetMapping("/me")
    public ResponseEntity<UserDto> me(@AuthenticationPrincipal Authentication auth) {

        if (auth == null || !auth.isAuthenticated())
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        String email = auth.getName();
        User user = userService.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return ResponseEntity.ok().body(mapper.toDto(user));
    }

    /* Spring Security já cuida do logout – expomos só para documentar */
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest req, HttpServletResponse resp) {
        new SecurityContextLogoutHandler().logout(req, resp, SecurityContextHolder.getContext().getAuthentication());
        return ResponseEntity.ok(Map.of("message", "Logged out"));
    }
}
