package com.revature.main;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.revature.beans.Batch;
import com.revature.beans.Topic;
import com.revature.service.Service;

public class Driver {
	@Autowired
	static Service s;
	public static void main(String[] args) {
		
	/*	List<Batch> blist = s.getBatchByTrainer("Jonathan@mail.com");
		Batch b = s.getBatchById(1);
		List<Topic> t = s.getTopicsByCurriculum(b.getCurriculum());
		
		System.out.println("========================List of Batches===================\n\n\n");
		for (Batch r : blist){
			System.out.println(r);
		}
		System.out.println("\n\n\n========================Single Batche===================\n\n\n");
		System.out.println(b);
		
		System.out.println("\n\n\n========================List of Topics===================\n\n\n");
		for (Topic r : t){
			System.out.println(r);
		}
*/
	}

}
