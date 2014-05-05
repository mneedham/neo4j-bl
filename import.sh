#!/bin/bash#!/bin/bash#!/bin/bash

java -cp target/neo4j-bl-1.0-SNAPSHOT.jar org.neo4j.bl.importdata.Import "$@"
