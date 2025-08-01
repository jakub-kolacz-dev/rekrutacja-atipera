package com.example.rozmowaatipera.stubs

class GithubApiBranchesStubs {
    static def PROPER_RESPONSE = """
                    [
                        {
                            "name": "master",
                            "commit": {
                                "sha": "abc123"
                            }
                        },
                        {
                            "name": "develop",
                            "commit": {
                                "sha": "def456"
                            }
                        }
                    ]
                """
}
