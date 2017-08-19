package com.bam.service;

import com.bam.bean.BamUser;

public class EmailRun implements Runnable{

	public EmailRun(String email) {
	
		MailService.sendMail(email);
	}
	
	@Override
	public void run() {
	}


}
