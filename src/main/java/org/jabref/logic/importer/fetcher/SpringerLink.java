package org.jabref.logic.importer.fetcher;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;

import org.jabref.logic.importer.FulltextFetcher;
import org.jabref.logic.importer.ImporterPreferences;
import org.jabref.logic.preferences.FetcherApiKey;
import org.jabref.logic.util.BuildInfo;
import org.jabref.model.entry.BibEntry;
import org.jabref.model.entry.field.StandardField;
import org.jabref.model.entry.identifier.DOI;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import kong.unirest.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FulltextFetcher implementation that attempts to find a PDF URL at SpringerLink.
 * <p>
 * Uses Springer API, see <a href="https://dev.springer.com">https://dev.springer.com</a>
 */
public class SpringerLink implements FulltextFetcher {
    private static final Logger LOGGER = LoggerFactory.getLogger(SpringerLink.class);

    private static final String API_URL = "https://api.springer.com/meta/v1/json";
    private static final String API_KEY = new BuildInfo().springerNatureAPIKey;
    private static final String CONTENT_HOST = "link.springer.com";
    private static final String FETCHER_NAME = "Springer";

    private final ImporterPreferences importerPreferences;

    public SpringerLink(ImporterPreferences importerPreferences) {
        this.importerPreferences = importerPreferences;
    }

    private String getApiKey() {
        return importerPreferences.getApiKeys()
                                  .stream()
                                  .filter(key -> key.getName().equalsIgnoreCase(FETCHER_NAME))
                                  .filter(FetcherApiKey::shouldUse)
                                  .findFirst()
                                  .map(FetcherApiKey::getKey)
                                  .orElse(API_KEY);
    }

    @Override
    public Optional<URL> findFullText(BibEntry entry) throws IOException {
        Objects.requireNonNull(entry);

        // Try unique DOI first
        Optional<DOI> doi = entry.getField(StandardField.DOI).flatMap(DOI::parse);

        if (doi.isEmpty()) {
            return Optional.empty();
        }
        // Available in catalog?
        try {
            HttpResponse<JsonNode> jsonResponse = Unirest.get(API_URL)
                                                         .queryString("api_key", getApiKey())
                                                         .queryString("q", String.format("doi:%s", doi.get().getDOI()))
                                                         .asJson();
            if (jsonResponse.getBody() != null) {
                JSONObject json = jsonResponse.getBody().getObject();
                int results = json.getJSONArray("result").getJSONObject(0).getInt("total");

                if (results > 0) {
                    LOGGER.info("Fulltext PDF found @ Springer.");
                    return Optional.of(new URL("http", CONTENT_HOST, String.format("/content/pdf/%s.pdf", doi.get().getDOI())));
                }
            }
        } catch (UnirestException e) {
            LOGGER.warn("SpringerLink API request failed", e);
        }
        return Optional.empty();
    }

    @Override
    public TrustLevel getTrustLevel() {
        return TrustLevel.PUBLISHER;
    }
}
