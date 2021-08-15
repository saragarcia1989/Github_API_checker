package service;

import java.util.Map;

public class Main {

    public static void main(String[] args) {
        String inputSearch = System.getProperty("search");
        String inputTag = System.getProperty("tag");

        if (inputSearch == null || inputTag == null) {
            throw new IllegalArgumentException("search string or tag is missing");
        }

        GithubChecker githubChecker = new GithubChecker();

        Map getTopRepository = githubChecker.searchRepositories(inputSearch);
        String lastTagRelease = githubChecker.getLastReleaseTag((String) getTopRepository.get("full_name"));


        System.out.println("Name of the repository: " + getTopRepository.get("name"));
        System.out.println("Number of stars: " + getTopRepository.get("stars"));

        githubChecker.checkLatestReleaseTag(inputTag, lastTagRelease);

    }
}
