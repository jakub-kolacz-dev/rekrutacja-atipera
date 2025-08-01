package com.example.rozmowaatipera.model.response;

import com.example.rozmowaatipera.model.general.GitHubBranch;
import com.example.rozmowaatipera.model.general.Owner;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class GitHubRepositoryResponse {

    private String name;

    @JsonProperty("owner")
    private Owner owner;

    @JsonProperty("fork")
    private boolean isFork;

    @Setter
    private List<GitHubBranch> branches;

    public boolean isFork() {
        return isFork;
    }
}