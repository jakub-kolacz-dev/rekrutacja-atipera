package com.example.rozmowaatipera.model.general;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GitHubBranch {

    private String name;

    @JsonProperty("commit")
    private Commit commit;

}