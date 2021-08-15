package githubCheckerTests;

import static org.testng.AssertJUnit.assertTrue;

import org.testng.Assert;
import org.testng.annotations.Test;
import service.GithubChecker;

import java.util.Map;

public class SearchTest {

    GithubChecker githubChecker = new GithubChecker();

    @Test

    public void searchRepositoryTest() {

        String searchString = "selenium";
        Map<String, Object> response = githubChecker.searchRepositories(searchString);

        String nameRepo = (String) response.get("name");
        String fullNameRepo = (String) response.get("full_name");
        int starsRepo = (int) response.get("stars");

        assertTrue("Name of the repository does not found", nameRepo.contains(searchString));
        assertTrue("Full_name of the repository not found", fullNameRepo.contains(searchString));
        assertTrue("Repository does not have any stars", starsRepo > 0);

    }

    @Test
    public void getLatestReleaseTag() {

        String latestTag = githubChecker.getLastReleaseTag("SeleniumHQ/selenium");
        Assert.assertNotNull(latestTag);

    }

}

