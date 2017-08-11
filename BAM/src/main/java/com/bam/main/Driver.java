package com.bam.main;

import java.util.List;

import com.bam.beans.Batch;
import com.bam.service.BatchService;
import com.bam.service.PasswordGenerator;


public class Driver {
	public static void main(String [] args){
		System.out.println(PasswordGenerator.makePassword());
	
	}
	
}
