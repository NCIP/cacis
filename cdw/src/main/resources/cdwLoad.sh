export JAR_HOME=.

for f in $JAR_HOME/*.jar
do
JAR_CLASSPATH=$JAR_CLASSPATH:$f
done
export JAR_CLASSPATH

#the next line will print the JAR_CLASSPATH to the shell.
echo the classpath $JAR_CLASSPATH

java -classpath $JAR_CLASSPATH gov.nih.nci.cacis.cdw.CDWLoadJob