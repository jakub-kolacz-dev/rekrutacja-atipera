package com.example.rozmowaatipera.stubs

class GithubApiReposStubs {

    static def PROPER_RESPONSE = """
                    [
                        {
                            "name": "repo1",
                            "owner": { "login": "testuser" },
                            "fork": false
                        },
                        {
                            "name": "repo2",
                            "owner": { "login": "testuser" },
                            "fork": true
                        }
                    ]
                """
}
