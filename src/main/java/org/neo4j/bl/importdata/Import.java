package org.neo4j.bl.importdata;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

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

        PrintWriter out = new PrintWriter( new FileWriter( "data/book.csv" ) );

        out.write( "identifier,place\n" );

        for ( int i = 0; i < localeTemp.size(); i++ )
        {
            JsonNode row = localeTemp.get( i );

            String place = row.get( "place" ).asText();
            String identifier = row.get( "identifier" ).asText();

            out.write( "\"" + identifier + "\",\"" + place + "\"\n" );
        }
        out.close();

        System.out.println("Finished import.");
    }
}
