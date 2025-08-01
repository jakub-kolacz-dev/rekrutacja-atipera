package com.example.rozmowaatipera.controller


import com.example.rozmowaatipera.model.response.GitHubRepositoryResponse
import com.example.rozmowaatipera.stubs.GithubApiBranchesStubs
import com.example.rozmowaatipera.stubs.GithubApiReposStubs
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.web.client.RestTemplate
import spock.lang.Specification

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GithubControllerHappyPathIntegrationTest extends Specification {

    @Autowired
    private RestTemplate restTemplate

    @Autowired
    private TestRestTemplate testRestTemplate

    //Mock server to simulate GitHub API responses
    private MockRestServiceServer mockServer

    def setup() {
        mockServer = MockRestServiceServer.bindTo(restTemplate).build()
    }

    /* the other suggestion and approach that I wanted to show is using parametrized test with a lot of test cases
       this would require parametrizing username input as well as mocks, outputs, and results, so I decided to keep it simple
       just to test core logic of this controller*/
    def "should return non-fork repositories with branches for valid username"() {
        given: "a username and mocked GitHub API responses"
        def username = "testuser"


        //Minimal mocking to check if whole logic runs as suppoused
        mockServer.expect(requestTo("https://api.github.com/users/${username}/repos")) //urls can be put in app.properties to match perfect standards
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(GithubApiReposStubs.PROPER_RESPONSE, MediaType.APPLICATION_JSON))

        mockServer.expect(requestTo("https://api.github.com/repos/${username}/repo1/branches"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(GithubApiBranchesStubs.PROPER_RESPONSE, MediaType.APPLICATION_JSON))

        when: "the controller endpoint is called"
        ResponseEntity<List<GitHubRepositoryResponse>> response = testRestTemplate.exchange(
                "/api/github/repositories/${username}",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<GitHubRepositoryResponse>>() {}
        )

        then: "the response status is OK"
        response.statusCode == HttpStatus.OK

        and: "the response contains the expected repositories"
        def repositories = response.body
        repositories != null
        repositories.size() == 1

        and: "the repository has the correct properties"
        with(repositories[0]) {
            it.name == "repo1"
            it.owner.login == "testuser"
            !isFork()

            and: "the branches are correctly populated"
            branches != null
            branches.size() == 2

            and: "each branch has the correct information"
            branches.find { it.name == "master" }.with {
                it.commit.sha == "abc123"
            }
            branches.find { it.name == "develop" }.with {
                it.commit.sha == "def456"
            }
        }

        and: "all mocked endpoints were called"
        mockServer.verify()
    }
}