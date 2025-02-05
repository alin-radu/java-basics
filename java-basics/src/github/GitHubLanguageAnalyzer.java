package github;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GitHubLanguageAnalyzer {
    private static final String FILE_PATH = "repos.json"; // JSON file containing repositories
    private static final String GITHUB_TOKEN = "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";

    public static void main(String[] args) {
        try {
            // parse the JSON file containing repositories data
            JSONParser parser = new JSONParser();
            JSONArray repositories = (JSONArray) parser.parse(new FileReader(FILE_PATH));

            // Map to store total language usage across all repositories
            Map<String, Long> languageUsage = new HashMap<>();
            // List to store individual repository language usage
            List<Map<String, Long>> repositoryLanguages = new ArrayList<>();
            long totalRepoSize = 0;

            // Process each repository
            for (Object obj : repositories) {
                JSONObject repo = (JSONObject) obj;
                String repoName = (String) repo.get("full_name");
                String languagesUrl = (String) repo.get("languages_url");

                System.out.println("Fetching languages for: " + repoName);
                JSONObject languages = fetchLanguages(languagesUrl);

                long repoSize = 0;
                Map<String, Long> repoLanguageUsage = new HashMap<>();

                // Aggregate language sizes for the current repository
                for (Object langKey : languages.keySet()) {
                    String language = (String) langKey;
                    Long size = (Long) languages.get(langKey);
                    repoLanguageUsage.put(language, size);
                    repoSize += size;
                    languageUsage.put(language, languageUsage.getOrDefault(language, 0L) + size);
                }

                repositoryLanguages.add(repoLanguageUsage);
                totalRepoSize += repoSize;

                // Add a delay of 2 seconds between requests
                Thread.sleep(500);
            }

            // Calculate the total size for all languages across all repositories
            long totalSize = languageUsage.values().stream().mapToLong(Long::longValue).sum();

            System.out.println("\nPer Repository Impact:");

            // Iterate through each repository's language data
            for (int i = 0; i < repositories.size(); i++) {
                JSONObject repo = (JSONObject) repositories.get(i); // Get the repository JSON object
                String repoName = (String) repo.get("full_name");  // Extract repo name
                Map<String, Long> repoLanguageUsage = repositoryLanguages.get(i);
                long repoSize = repoLanguageUsage.values().stream().mapToLong(Long::longValue).sum();

                double repositoryImpactPercentage = ((double) repoSize / totalRepoSize) * 100;

                for (Map.Entry<String, Long> entry : repoLanguageUsage.entrySet()) {
                    String language = entry.getKey();
                    long size = entry.getValue();

                    // Corrected printf statement
                    System.out.printf("%s | Language %s | Repository impact %.2f%%%n",
                            repoName, language, repositoryImpactPercentage);
                }
            }

        } catch (IOException | ParseException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static JSONObject fetchLanguages(String apiUrl) {
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Set Authorization header with the Personal Access Token
            String authHeaderValue = "Bearer " + GITHUB_TOKEN;
            connection.setRequestProperty("Authorization", authHeaderValue);

            // Read the response
            JSONParser parser = new JSONParser();
            return (JSONObject) parser.parse(new java.io.InputStreamReader(connection.getInputStream()));

        } catch (Exception e) {
            System.err.println("Failed to fetch languages from: " + apiUrl);
            e.printStackTrace();
            System.exit(1);  // Exit the program after printing the exception
            return null;  // This won't be reached because System.exit(1) is called above
        }
    }
}
