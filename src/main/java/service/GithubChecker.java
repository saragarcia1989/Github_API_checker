package service;

import com.jayway.restassured.response.Response;
import org.apache.maven.artifact.versioning.DefaultArtifactVersion;

import java.util.Map;

import static com.jayway.restassured.RestAssured.*;

public class GithubChecker {

    private static final String REPO_SEARCH_URL = "https://api.github.com/search/repositories";

    private static final String REPO_LATEST_RELEASE_URL = "https://api.github.com/repos/%s/releases/latest";

    public Map<String, Object> searchRepositories(String searchString) {

        Response response = given()
                .queryParam("q", searchString)
                .queryParam("sort", "stars")
                .queryParam("order", "desc")
                .get(REPO_SEARCH_URL);

        //Extracting top repo
        String topRepoName = response
                .then()
                .extract()
                .path("items[0].name");

        int topRepoStars = response
                .then()
                .extract()
                .path("items[0].stargazers_count");

        String topRepoFullName = response
                .then()
                .extract()
                .path("items[0].full_name");

        return Map.of(
                "name", topRepoName,
                "stars", topRepoStars,
                "full_name", topRepoFullName
        );
    }

    public String getLastReleaseTag(String topRepoFullName) {

        Response response = get(String.format(REPO_LATEST_RELEASE_URL, topRepoFullName));

        String latestTag = response
                .then()
                .extract()
                .path("tag_name");

        return latestTag;

    }

    public void checkLatestReleaseTag(String inputTag, String latestTag) {


        DefaultArtifactVersion latestTagVersion = new DefaultArtifactVersion(latestTag);
        DefaultArtifactVersion inputTagVersion = new DefaultArtifactVersion(inputTag);


        if (latestTagVersion.compareTo(inputTagVersion) == 0) {
            System.out.println("The tag you entered is the latest version");
        } else if (latestTagVersion.compareTo(inputTagVersion) < 0) {
            System.out.println("Version of the tag you entered is not supported");
        } else {
            System.out.println("The tag you entered is NOT the latest version");
        }
    }
}
