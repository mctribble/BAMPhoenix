package com.bam.exception;

import org.hamcrest.MatcherAssert;
import org.junit.Test;

import javax.mail.MessagingException;

import static org.junit.Assert.*;


public class CustomExceptionTest
{
    @Test
    public void exceptionsShouldHaveMessages() throws Exception
    {
        final String expectedMessage = "exception message";

        try
        {
            throw new CustomException(new Exception(expectedMessage));
        }
        catch (CustomException e)
        {
            assert(e.getMessage().contains(expectedMessage));
        }

        try
        {
            throw new CustomException(new MessagingException(expectedMessage));
        }
        catch (CustomException e)
        {
            assert(e.getMessage().contains(expectedMessage));
        }

        try
        {
            throw new CustomException(expectedMessage);
        }
        catch (CustomException e)
        {
            assert(e.getMessage().contains(expectedMessage));
        }
    }

    @Test
    public void exceptionsShouldHaveStackTraces() throws Exception
    {
        final String expectedMessage = "exception message";

        try
        {
            throw new CustomException(new Exception(expectedMessage));
        }
        catch (CustomException e)
        {
            assertNotNull(e.getStackTrace());
        }

        try
        {
            throw new CustomException(new MessagingException(expectedMessage));
        }
        catch (CustomException e)
        {
            assertNotNull(e.getStackTrace());
        }

        try
        {
            throw new CustomException(expectedMessage);
        }
        catch (CustomException e)
        {
            assertNotNull(e.getStackTrace());
        }
    }
}