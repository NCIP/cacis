@REM
@REM Copyright 5AM Solutions Inc
@REM Copyright SemanticBits LLC
@REM Copyright AgileX Technologies, Inc
@REM Copyright Ekagra Software Technologies Ltd
@REM
@REM Distributed under the OSI-approved BSD 3-Clause License.
@REM See http://ncip.github.com/cacis/LICENSE.txt for details.
@REM

@echo off
 setLocal EnableDelayedExpansion
 set CLASSPATH="
 for /R . %%a in (*.jar) do (
   set CLASSPATH=!CLASSPATH!;%%a
 )
 set CLASSPATH=!CLASSPATH!"
 echo !CLASSPATH!

java gov.nih.nci.cacis.cdw.CDWLoadJob