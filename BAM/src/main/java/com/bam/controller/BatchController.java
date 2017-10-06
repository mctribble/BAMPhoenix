package com.bam.controller;

import com.bam.bean.Batch;
import com.bam.bean.BatchType;
import com.bam.service.BamUserService;
import com.bam.service.BatchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
 
@RestController
@RequestMapping(value = "/rest/api/v1/Batches/")
@Api(value = "catalog", tags = "Batch", description = "Operations about batches")
public class BatchController {

  private static final String EMAIL = "email";

  @Autowired
  BatchService batchService;

  @Autowired
  BamUserService bamUserService;

  @RequestMapping(value = "All", method = RequestMethod.GET, produces = "application/json")
  @ResponseBody
  @ApiOperation(value = "Find all batches")
  public List<Batch> getBatchAll() {
    return batchService.getBatchAll();
  }

  @RequestMapping(value = "Past", method = RequestMethod.GET, produces = "application/json")
  @ResponseBody
  @ApiOperation(value = "Find all batches by trainer email and return only batches that have ended")
  public List<Batch> getPastBatches(HttpServletRequest request) {
    List<Batch> batches = batchService.getBatchByTrainer(bamUserService.findUserByEmail(request.getParameter(EMAIL)));

    List<Batch> pastBatches = new ArrayList<>();
    for (Batch b : batches) {
      if (new Timestamp(System.currentTimeMillis()).after(b.getEndDate())) {
        pastBatches.add(b);
      }
    }
    return pastBatches;
  }

  @RequestMapping(value = "Future", method = RequestMethod.GET, produces = "application/json")
  @ResponseBody
  @ApiOperation(value = "Find all batches by trainer email and return batches not yet started")
  public List<Batch> getFutureBatches(HttpServletRequest request) {
    List<Batch> batches = batchService.getBatchByTrainer(bamUserService.findUserByEmail(request.getParameter(EMAIL)));

    List<Batch> futureBatches = new ArrayList<>();
    for (Batch b : batches) {
      if (new Timestamp(System.currentTimeMillis()).before(b.getStartDate())) {
        futureBatches.add(b);
      }
    }
    return futureBatches;
  }

  @RequestMapping(value = "InProgress", method = RequestMethod.GET, produces = "application/json")
  @ResponseBody
  @ApiOperation(value = "Find all batches by trainer email and return the first batch currently in progress")
  public Batch getBatchInProgress(HttpServletRequest request) {
    List<Batch> batches = batchService.getBatchByTrainer(bamUserService.findUserByEmail(request.getParameter(EMAIL)));

    Batch batchInProgress = null;
    Timestamp t = new Timestamp(System.currentTimeMillis());
    for (Batch b : batches) {
      if (t.after(b.getStartDate()) && t.before(b.getEndDate())) {
        batchInProgress = b;
        break;
      }
    }
    return batchInProgress;
  }

  @RequestMapping(value = "AllInProgress", method = RequestMethod.GET, produces = "application/json")
  @ResponseBody
  @ApiOperation(value = "Find all batches by trainer email and return all batches currently in progress")
  public List<Batch> getAllBatchesInProgress(HttpServletRequest request) {
    List<Batch> batches = batchService.getBatchByTrainer(bamUserService.findUserByEmail(request.getParameter(EMAIL)));

    List<Batch> batchesInProgress = new ArrayList<>();
    Timestamp time = new Timestamp(System.currentTimeMillis());
    for (Batch b : batches) {
      if (time.after(b.getStartDate()) && time.before(b.getEndDate())) {
        batchesInProgress.add(b);
      }
    }
    return batchesInProgress;
  }

  @RequestMapping(value = "Edit", method = RequestMethod.POST, produces = "application/json")
  @ApiOperation(value = "Updates a batch by mapping JSON to a batch object")
  public void updateUser(@RequestBody String jsonObject) {
    Batch currentBatch = null;
    try {
      currentBatch = new ObjectMapper().readValue(jsonObject, Batch.class);
    } catch (IOException e) {
      LogManager.getRootLogger().error(e);
    }

    batchService.addOrUpdateBatch(currentBatch);
  }

  @RequestMapping(value = "ById", method = RequestMethod.GET, produces = "application/json")
  @ResponseBody
  @ApiOperation(value = "Find a batch by id")
  public Batch getBatchById(HttpServletRequest request) {
    return batchService.getBatchById(Integer.parseInt(request.getParameter("batchId")));
  }

  @RequestMapping(value = "UpdateBatch", method = RequestMethod.POST)
  @ApiOperation(value = "Updates a batch")
  public void updateBatch(@RequestBody Batch batch) {
    batchService.addOrUpdateBatch(batch);
  }

  @RequestMapping(value = "BatchTypes", method = RequestMethod.GET, produces = "application/json")
  @ResponseBody
  @ApiOperation(value = "Find all batch types")
  public List<BatchType> getAllBatchTypes() {
    return batchService.getAllBatchTypes();
  }

}
