@echo off
 setLocal EnableDelayedExpansion
 set CLASSPATH="
 for /R . %%a in (*.jar) do (
   set CLASSPATH=!CLASSPATH!;%%a
 )
 set CLASSPATH=!CLASSPATH!"
 echo !CLASSPATH!

java gov.nih.nci.cacis.cdw.CDWLoadJob