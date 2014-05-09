package org.neo4j.bl.importdata;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.neo4j.graphdb.Label;
import org.neo4j.unsafe.batchinsert.BatchInserter;
import org.neo4j.unsafe.batchinsert.BatchInserters;

import static org.neo4j.graphdb.DynamicLabel.*;
import static org.neo4j.graphdb.DynamicRelationshipType.*;

/**
 * Quick and dirty importer, no authors or contributors yet.
 */
public class Import {

    private static final Map<String, Long> publishers = new HashMap<>();
    private static final Map<String, Long> places = new HashMap<>();
    private static final Map<Integer, Long> years = new HashMap<>();

    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.out.println("Usage: ./import.sh [import-file.json]");
            System.exit(1);
        }

        String importFile = args[0];
        System.out.println("Importing data from: " + importFile);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rows = mapper.readTree(new File(importFile));

        BatchInserter inserter = BatchInserters.inserter("graph.db");

        for (JsonNode row : rows) {
            String flickrUrl = getStringOrNull("flickr_url_to_book_images", row);
            String publisher = getStringOrNull("publisher", row);
            String edition = getStringOrNull("edition", row);
            String place = getStringOrNull("place", row);
            String year = getStringOrNull("date", row);
            String title = getStringOrNull("title", row);
            String identifier = getStringOrNull("identifier", row);

            Map<String, Object> props = new HashMap<>();
            putIfNotNull(props, "title", title);
            putIfNotNull(props, "url", flickrUrl);
            putIfNotNull(props, "edition", edition);
            putIfNotNull(props, "identifier", identifier);

            long bookId = inserter.createNode(props, label("Book"));

            if (place != null) {
                long placeId = findNodeId(inserter, places, place, label("Place"));
                inserter.createRelationship(bookId, placeId, withName("PUBLISHED_AT"), Collections.<String, Object>emptyMap());
            }

            if (publisher != null) {
                long publisherId = findNodeId(inserter, publishers, publisher, label("Publisher"));
                inserter.createRelationship(bookId, publisherId, withName("PUBLISHED_BY"), Collections.<String, Object>emptyMap());
            }

            if (year != null) {
                long yearId = findNodeId(inserter, years, Integer.valueOf(year), label("Year"));
                inserter.createRelationship(bookId, yearId, withName("PUBLISHED_IN"), Collections.<String, Object>emptyMap());
            }
        }

        inserter.shutdown();

        System.out.println("Finished import.");
    }

    public static <T> long findNodeId(BatchInserter inserter, Map<T, Long> map, T key, Label label) {
        if (!map.containsKey(key)) {
            map.put(key, inserter.createNode(Collections.<String, Object>singletonMap("name", key), label));
        }

        return map.get(key);
    }

    public static String getStringOrNull(String fieldName, JsonNode row) {
        if (row == null) {
            return null;
        }

        if (row.get(fieldName) != null) {
            String result = row.get(fieldName).asText();
            if (StringUtils.isNotEmpty(result)) {
                return result;
            }
        }

        return null;
    }

    public static void putIfNotNull(Map<String, Object> map, String key, Object value) {
        if (value != null) {
            map.put(key, value);
        }
    }
}
