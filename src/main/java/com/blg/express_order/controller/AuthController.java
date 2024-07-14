package com.blg.express_order.controller;

import com.blg.express_order.payload.request.LoginRequest;
import com.blg.express_order.payload.request.SignUpRequest;
import com.blg.express_order.payload.response.JWTTokenSuccessResponse;
import com.blg.express_order.payload.response.MessageResponse;
import com.blg.express_order.security.JWTTokenProvider;
import com.blg.express_order.security.SecurityConstants;
import com.blg.express_order.service.CustomUserDetailsService;
import com.blg.express_order.service.UserService;
import com.blg.express_order.validations.ResponseErrorValidation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/auth")
@PreAuthorize("permitAll()")
public class AuthController {
    @Autowired
    private JWTTokenProvider jwtTokenProvider;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private ResponseErrorValidation responseErrorValidation;
    @Autowired
    private UserService userService;

    @PostMapping("/signin")
    public ResponseEntity<Object> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult bindingResult){
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) {
            return errors;
        }
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(),
                loginRequest.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = SecurityConstants.TOKEN_PREFIX + jwtTokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JWTTokenSuccessResponse(true, jwt));
    }

    @PostMapping("/signup")
    public ResponseEntity<Object> registerUser(@Valid @RequestBody SignUpRequest signUpRequest, BindingResult bindingResult) {
        // Validate request
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        // Check if username or email already exists
        if (userService.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity<>(new MessageResponse("Error: Username is already taken!"), HttpStatus.BAD_REQUEST);
        }
        if (userService.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity<>(new MessageResponse("Error: Email is already in use!"), HttpStatus.BAD_REQUEST);
        }

        // Create new user
        userService.createUser(signUpRequest);
        return ResponseEntity.ok(new MessageResponse("User registered successfully"));
    }

}
