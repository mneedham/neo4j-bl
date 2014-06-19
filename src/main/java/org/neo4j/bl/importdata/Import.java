package org.neo4j.bl.importdata;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Import
{
    public static void main( String[] args ) throws IOException
    {
        if ( args.length < 1 )
        {
            System.err.println( "Usage: ./import.sh [import-file.json]" );
            System.exit( 1 );
        }

        String importFile = args[0];
        System.err.println( "Importing data from: " + importFile );

        ObjectMapper mapper = new ObjectMapper();
        ArrayNode rows = (ArrayNode) mapper.readTree( new File( importFile ) );

        for ( int i = 0; i < rows.size(); i++ )
        {
            JsonNode row = rows.get( i );
            String bookId = row.get( "identifier" ).textValue();
            ObjectNode author = (ObjectNode) row.get( "authors" );
            Iterator<String> fieldNames = author.fieldNames();
            while ( fieldNames.hasNext() )
            {
                String fieldName = fieldNames.next();
                ArrayNode authorNames = (ArrayNode) author.get( fieldName );
                for ( JsonNode authorName : authorNames )
                {
                    System.out.printf( "%s,%s%n", bookId, authorName );
                }
            }
        }

        System.err.println( "Finished import." );
    }
}
