/**
 * Copyright 5AM Solutions Inc
 * Copyright SemanticBits LLC
 * Copyright AgileX Technologies, Inc
 * Copyright Ekagra Software Technologies Ltd
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Goal executes a script using the Mirth Connect CLI.
 *
 * @author bhumphrey
 * @goal cli
 * @phase deploy
 * @since May 5, 2011
 */
public class MirthConnectCLIMojo extends AbstractMojo {

    /**
     * Location of MirthConnect.
     *
     * @parameter expression="${cli.mirthconnect}"
     * @required
     */
    private File mirthConnect;

    /**
     * Mirth Script file.
     *
     * @parameter expression="${cli.script}"
     * @required
     */
    private File script;

    /**
     * Will pause before running the script
     * Needed sometimes for MC to startup
     * completely
     *
     * @parameter expression="${pause}"
     * default-value=0
     */
    private int pause;

    /**
     * {@inheritDoc}
     */
    public void execute() throws MojoExecutionException, MojoFailureException {

        if (!script.exists()) {
            throw new MojoExecutionException("Unable to find script " + script);
        }

        String line;
        BufferedReader input = null;

        try {

            pause();

            Process process = Runtime.getRuntime().exec(
                    new String[]{mirthConnect.getCanonicalPath(), "-s" + script.getCanonicalPath()});
            input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while ((line = input.readLine()) != null) {
                getLog().info(line);
            }
            input.close();
            if (process.waitFor() != 0) {
                throw new MojoExecutionException("Failed");
            }

            pause();

        } catch (SecurityException e) {
            throw new MojoExecutionException("Does the user have permission to run the command line?" + mirthConnect, e);
        } catch (IOException e) {
            throw new MojoExecutionException("Unable to find MirthConnect CLI, is it install at this location? "
                    + mirthConnect, e);
        } catch (InterruptedException e) {
            throw new MojoExecutionException("Command line execution was interrupted ");
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    // eat this exception
                }
            }
        }

    }

    private void pause() throws InterruptedException {
        if (pause > 0) {
            getLog().info("Will pause for " + pause + " milliseconds");
            Thread.sleep(pause);
        }

    }
}
