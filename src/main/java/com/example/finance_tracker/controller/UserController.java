package com.example.finance_tracker.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.finance_tracker.constants.ResponseConstants;
import com.example.finance_tracker.handlers.ResponseHandler;
import com.example.finance_tracker.model.UserPublicDto;
import com.example.finance_tracker.model.pagination.PaginationDto;
import com.example.finance_tracker.model.Role;
import com.example.finance_tracker.model.User;
import com.example.finance_tracker.model.pagination.PaginationResponse;
import com.example.finance_tracker.service.UserService;
import com.example.finance_tracker.tools.PaginationResponseUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class UserController {
    private final UserService userService;

    @GetMapping("/super-admin/users")
    public ResponseEntity<Object> getUsers(@RequestBody PaginationDto paginationDto) {
        int pageNo = paginationDto.getPageNo();
        int pageSize = paginationDto.getPageSize();
        Page<UserPublicDto> users = userService.getUsers(pageNo, pageSize);
        PaginationResponse<UserPublicDto> userPaginationResponse = PaginationResponseUtil.formatPaginationResponse(users);

        return ResponseHandler.generateResponse(
                ResponseConstants.SUCCESS_GET,
                HttpStatus.OK,
                userPaginationResponse,
                null,
                "getUsersAsync");
    }

    @PostMapping("/signup")
    public ResponseEntity<Object> addUser(@RequestBody User user) {
        User savedUser = userService.saveUser(user);
        try {
            userService.assignRoleToUser(savedUser.getUsername(), "ROLE_USER");
        } catch (Exception e) {
            return ResponseHandler.generateResponse("Failed to assign role", HttpStatus.INTERNAL_SERVER_ERROR, null, e.getMessage(), "addUserAsync");
        }
        return ResponseHandler.generateResponse(ResponseConstants.SUCCESS_POST, HttpStatus.CREATED, savedUser, null, "addUserAsync");
    }


    @PutMapping("/users/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        return ResponseHandler.generateResponse(ResponseConstants.SUCCESS_PUT, HttpStatus.OK, userService.updateUser(id, userDetails), null, "updateUserAsync");
    }

    @PatchMapping("/users/{id}")
    public ResponseEntity<Object> patchUser(@PathVariable Long id, @RequestBody User userDetails) {
        return ResponseHandler.generateResponse(ResponseConstants.SUCCESS_PATCH, HttpStatus.OK, userService.patchUser(id, userDetails), null, "patchUserAsync");
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseHandler.generateResponse(ResponseConstants.SUCCESS_DELETE, HttpStatus.NO_CONTENT, null, null, "deleteUserAsync");
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Object> getUser(@PathVariable Long id) {
        return ResponseHandler.generateResponse(ResponseConstants.SUCCESS_GET, HttpStatus.OK, userService.getUserById(id), null, "getUserAsync");
    }


    @GetMapping("/roles/{id}")
    public ResponseEntity<Object> getRole(@PathVariable Long id) {
        return ResponseHandler.generateResponse(ResponseConstants.SUCCESS_GET, HttpStatus.OK, userService.getRoleById(id), null, "getRoleAsync");
    }

    @GetMapping("/roles")
    public ResponseEntity<Object> getAllRoles() {
        return ResponseHandler.generateResponse(ResponseConstants.SUCCESS_GET, HttpStatus.OK, userService.getRoles(), null, "getAllRolesAsync");
    }

    @DeleteMapping("/roles")
    public ResponseEntity<Object> deleteRole(@PathVariable Long id) {
        userService.deleteRole(id);
        return ResponseHandler.generateResponse(
                ResponseConstants.SUCCESS_DELETE,
                HttpStatus.NO_CONTENT,
                null,
                null,
                "deleteRoleAsync"
        );
    }


    @PutMapping("/roles/{id}")
    public ResponseEntity<Object> updateRole(@PathVariable Long id, @RequestBody Role role) {
        return ResponseHandler.generateResponse(ResponseConstants.SUCCESS_GET, HttpStatus.OK, userService.updateRole(role), null, "updateRoleAsync");
    }

    @PostMapping("/roles")
    public ResponseEntity<Object> addRole(@RequestBody Role role) {
        return ResponseHandler.generateResponse(ResponseConstants.SUCCESS_POST, HttpStatus.CREATED, userService.saveRole(role), null, "addRoleAsync");
    }



    @PostMapping("users/role")
    public ResponseEntity<?> assignRole(@RequestBody AssignRoleRequest request) throws Exception {
        return ResponseHandler.generateResponse(ResponseConstants.SUCCESS_POST, HttpStatus.CREATED, userService.assignRoleToUser(request.getUsername(), request.getRoleName()), null, "assignRoleAsync");
    }

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            try{
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                User user = userService.getUser(username);
                String access_token = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis()+10*60*1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                        .sign(algorithm);
                        Map<String, String> tokens = new HashMap<>();
                        tokens.put("access_token", access_token);
                        tokens.put("refresh_token", refresh_token);
                        response.setContentType(APPLICATION_JSON_VALUE);
                        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            }catch (Exception exception){
                log.error("Error logging in: {}", exception.getMessage());
                Map<String, Object> errorDetails = new HashMap<>();
                errorDetails.put("errorMessage", exception.getMessage());
                errorDetails.put("errorCause", exception.getCause() != null ? exception.getCause().toString() : null);
                errorDetails.put("errorCode", "D03");

                Map<String, Object> responseMap = new HashMap<>();
                responseMap.put("message", "Error occurred");
                responseMap.put("success", false);
                responseMap.put("status", FORBIDDEN.value());
                responseMap.put("errors", errorDetails);
                responseMap.put("payload", null);

                response.setStatus(FORBIDDEN.value());
                response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), responseMap);
            }
        } else {
            throw new RuntimeException("Refresh token is missing");
        }
    }

}
    @Data
    class AssignRoleRequest {
     private String username;
     private String roleName;
    }

