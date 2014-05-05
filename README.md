# Build your first Neo4j app using a British Library dataset

The British Library have released over a million images into the public domain. 

The images contain maps, geological diagrams, beautiful illustrations, comical satire, illuminated and decorative letters, colourful illustrations, landscapes, wall-paintings and more. You can read more about the project in [their blog post](http://britishlibrary.typepad.co.uk/digital-scholarship/2013/12/a-million-first-steps.html)

We're going to working with [a data set containing meta data around these images](https://github.com/BL-Labs/imagedirectory). You can download the data set by running the following script:

````
./download.sh
````

This will create a 'data' directory containing one file - 'book_metadata.json'

## Starting web app

````
mvn package
java -jar target/neo4j-bl-1.0-SNAPSHOT.jar server
````

## Pre requisites

* [IntelliJ IDEA Community Edition](http://www.jetbrains.com/idea/download/)
* [Neo4j Community Edition](http://www.neo4j.org/download)
