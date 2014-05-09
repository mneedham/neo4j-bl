package org.neo4j.bl.importdata;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

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

        for ( int i = 0; i < 100; i++ )
        {
            JsonNode row = localeTemp.get( i );

            String bookIdentifier = "b" + i;
            String book = escapeQuotes( row.get( "title" ).get( 0 ).asText() );

            String publisherIdentifier = "p" + i;
            String publisher = escapeQuotes(row.get( "publisher" ).asText());

            String imageIdentifier = "i" + i;
            String image = UUID.randomUUID().toString();

            String dateIdentifier = "d" + i;
            String date = escapeQuotes( row.get( "date" ).asText() );

            if(!publisher.equals("")) {
//                System.out.println(row);
                System.out.printf( "MERGE (%s:Year {year: '%s'})%n", dateIdentifier, date );
                System.out.printf( "MERGE (%s:Image {identifier: '%s'})%n", imageIdentifier, image );
                System.out.printf( "MERGE (%s:Book {title: '%s'})%n", bookIdentifier, book );
                System.out.printf( "MERGE (%s)-[:APPEARS_IN]->(%s)%n", imageIdentifier, bookIdentifier );
                System.out.printf( "MERGE (%s)-[:PUBLISHED_IN]->(%s)%n", bookIdentifier, dateIdentifier );
                System.out.printf( "MERGE (%s:Publisher {title: '%s'})%n", publisherIdentifier, publisher );
                System.out.printf( "MERGE (%s)-[:PUBLISHED_BY]->(%s)%n", bookIdentifier, publisherIdentifier );
            }

        }

        System.out.println("Finished import.");
    }

    private static String escapeQuotes( String text )
    {
        return text.replace( "'", "\\'" );
    }
}
