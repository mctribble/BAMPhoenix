package com.bam.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bam.service.AssignForceSyncService;
import com.fasterxml.jackson.databind.JsonMappingException;

@RestController
public class AssignForceSyncController {
 
  @Autowired
  AssignForceSyncService service;

  @RequestMapping(value = "/refreshBatches", method = RequestMethod.GET)
  public void refreshBatches() throws JsonMappingException, IOException {
    service.assignForceSync();
  }

}
