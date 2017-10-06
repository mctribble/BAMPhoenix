package com.bam.controller;

import java.io.IOException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bam.service.AssignForceSyncService;
import com.fasterxml.jackson.databind.JsonMappingException;

@RestController
@Api(value = "catalog", tags = "AssignForce", description = "Operations about AssignForce")
public class AssignForceSyncController {
 
  @Autowired
  AssignForceSyncService service;

  @RequestMapping(value = "/refreshBatches", method = RequestMethod.GET)
  @ApiOperation(value = "Sync batch information with AssignForce")
  public void refreshBatches() throws JsonMappingException, IOException {
    service.assignForceSync();
  }

}
