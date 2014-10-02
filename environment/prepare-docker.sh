#!/bin/bash

/opt/cassandra-start

while sleep 2 ;do echo "SHOW VERSION" | /opt/apache-cassandra-2.1.0/bin/cqlsh `hostname -i` > /dev/null 2>&1 ; STATUS=$? ; if [ $STATUS -eq 0 ] ; then break ;fi ;done

/opt/apache-cassandra-2.1.0/bin/cqlsh `hostname -i` < /tmp/testdata.cql

/bin/bash