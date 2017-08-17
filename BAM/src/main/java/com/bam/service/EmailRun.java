package com.bam.service;

public class EmailRun implements Runnable{
	private String email;

	public EmailRun() {
	
		
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public void run() {
		MailService.sendMail(email);
		
	}


}
