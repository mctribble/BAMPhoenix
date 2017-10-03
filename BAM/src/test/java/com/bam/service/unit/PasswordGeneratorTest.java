package com.bam.service.unit;

import static org.hamcrest.MatcherAssert.assertThat;
import com.bam.service.PasswordGenerator;
import org.junit.Test;

import java.util.regex.Pattern;

public class PasswordGeneratorTest
{
    //happy path
    @Test
    public void makePassword() throws Exception
    {
        String generatedPassword = PasswordGenerator.makePassword();

        assertThat("Password should be 8 characters",generatedPassword.length() == 8);

        //define a regular expression that matches any non-alphanumeric characters
        //(specifically, anything that falls outside the ranges a-z, A-Z, and 0-9)
        Pattern regex = Pattern.compile("[^a-zA-Z0-9]");

        //enforce that the password does not contain any special characters
        //this is only because the intention of makePassword() was clearly to produce alphanumeric output
        //I don't know of anything that would actually break if given special characters
        assertThat("Password should be alphanumeric", !regex.matcher(generatedPassword).find());
    }
}