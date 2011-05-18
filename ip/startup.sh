export JAVA_OPTS="-Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false"

"$JAVA_HOME/bin/java" $JAVA_OPTS -jar target/ipf-1.0-SNAPSHOT.jar gov.nih.nci.cacis.SampleServer
