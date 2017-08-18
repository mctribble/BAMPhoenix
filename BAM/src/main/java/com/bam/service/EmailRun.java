package com.bam.service;


public class EmailRun implements Runnable{

	public EmailRun(String email) {
	
		MailService.sendMail(email);
	}
	
	@Override
	public void run() {
		//Empty Because of no args run method
	}


}
