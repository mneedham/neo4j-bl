package org.neo4j.bl.importdata;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Import
{
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

        for ( int i = 0; i < 5; i++ )
        {
            JsonNode row = localeTemp.get( i );
            System.out.println( row );
        }

        System.out.println("Finished import.");
    }
}
