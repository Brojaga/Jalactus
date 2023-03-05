#!/bin/bash

#ARGS: Uncomment as Needed

#JAR=paper.jar
#MAXRAM=6G
#MINRAM=6G
#TIME=20
#WORLD="world"
#NETHER="world_nether"
#END="world_the_end"

while [ true ]; do
    java -Xmx$MAXRAM -Xms$MINRAM -jar $JAR nogui
    if [ -d "/path-to-server/$WORLD" ]; then
       rm -r "/path-to-server/$WORLD"
       echo "$WORLD is removed"
    fi
    if [ -d "/path-to-server/$NETHER" ]; then
       rm -r "/path-to-server/$NETHER"
       echo "$NETHER is removed"
    fi
    if [ -d "/path-to-server/$END" ]; then
       rm -r "/path-to-server/$END"
       echo "$END is removed"
    fi
    echo "----- Press enter to prevent the server from restarting in $TIME seconds -----";
    read -t $TIME input;
    if [ $? == 0 ]; then
        break;
    else
        echo "------------------- SERVER RESTARTS -------------------";
    fi
done
