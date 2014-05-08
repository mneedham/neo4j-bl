package org.neo4j.bl.importdata;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.neo4j.unsafe.batchinsert.BatchInserter;
import org.neo4j.unsafe.batchinsert.BatchInserters;

public class Import
{
    private static final Map<String, Long> publishers = new HashMap<>();
    private static final Map<String, Long> authors = new HashMap<>();
    private static final Map<String, Long> places = new HashMap<>();

    public static void main( String[] args ) throws IOException
    {
        if(args.length < 1) {
            System.out.println("Usage: ./import.sh [import-file.json]");
            System.exit( 1 );
        }

        String importFile = args[0];
        System.out.println("Importing data from: " + importFile);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode localeTemp = mapper.readTree(new File(importFile));

        BatchInserter batchInserter = BatchInserters.inserter("graph.db");

        for (JsonNode row : localeTemp) {
            System.out.println( row );
        }

        System.out.println("Finished import.");
    }
}
