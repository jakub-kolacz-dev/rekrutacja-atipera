package com.example.rozmowaatipera.service;

import com.example.rozmowaatipera.model.general.GitHubBranch;
import com.example.rozmowaatipera.model.response.GitHubRepositoryResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GitHubService {

    private final RestTemplate restTemplate;

    public GitHubService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<GitHubRepositoryResponse> getRepositories(String username) {
        try {
            String url = String.format("https://api.github.com/users/%s/repos", username); //URI should be put in app.prop to match the standards :)
            GitHubRepositoryResponse[] repositories = restTemplate.getForObject(url, GitHubRepositoryResponse[].class);

            if (repositories == null) {
                throw new IllegalArgumentException("User not found");
            }

            return Arrays.stream(repositories)
                    .filter(repo -> !repo.isFork())
                    .peek(repo -> {
                        List<GitHubBranch> branches = getBranches(username, repo.getName());
                        repo.setBranches(branches);
                    })
                    .collect(Collectors.toList());
        } catch (HttpClientErrorException.NotFound e) {
            throw new IllegalArgumentException("User not found");
        }
    }

    private List<GitHubBranch> getBranches(String username, String repoName) {
        String url = String.format("https://api.github.com/repos/%s/%s/branches", username, repoName);
        GitHubBranch[] branches = restTemplate.getForObject(url, GitHubBranch[].class);

        if (branches == null) {
            throw new IllegalArgumentException("Branches not found for repository: " + repoName);
        }

        return Arrays.asList(branches);
    }
}