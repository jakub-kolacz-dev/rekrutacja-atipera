package com.example.rozmowaatipera.controller;


import com.example.rozmowaatipera.model.response.GitHubRepositoryResponse;
import com.example.rozmowaatipera.service.GitHubService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/github")
@RequiredArgsConstructor
public class GithubController {

    private final GitHubService gitHubService;


    @GetMapping("/repositories/{username}")
    public ResponseEntity<?> getRepositories(@PathVariable String username) {
        try {
            List<GitHubRepositoryResponse> repositories = gitHubService.getRepositories(username);

            return ResponseEntity.ok(repositories);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND) // this format of error message is what I propose, alternatively I'd create custom object with both fields written down like in the task
                    .body(e.getMessage());
        }
    }
}
