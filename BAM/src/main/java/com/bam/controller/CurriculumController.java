package com.bam.controller;

import com.bam.bean.*;
import com.bam.dto.CurriculumSubtopicDTO;
import com.bam.dto.DaysDTO;
import com.bam.exception.CustomException;
import com.bam.service.*;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/rest/api/v1/Curriculum/")
@Api(value="catalog", tags="Curriculum", description = "Operations about curriculums")
public class CurriculumController {

	@Autowired
	CurriculumService curriculumService;
	
	@Autowired
	CurriculumSubtopicService curriculumSubtopicService;
	
	@Autowired
	SubtopicService subtopicService;
	
	@Autowired 
	BatchService batchService;

	@Autowired
	BCMService bcmService;

	public CurriculumService get(){
		return curriculumService;
	}
	
	/***
	 * @author Nam Mai
	 * Method is needed for injecting mocked services for unit test
	 */
	@Autowired
	public CurriculumController(CurriculumService cs, CurriculumSubtopicService css, SubtopicService ss, BatchService bs, BCMService bcm){
		curriculumService = cs;
		curriculumSubtopicService =css;
		subtopicService = ss;
		batchService = bs;
		bcmService = bcm;
	}

	/**
	 * Matthew Hill
	 * Slight modification of the original method to use the newly created
	 * Batch_curr_master table that represents an individual batch's master version
	 */
	@RequestMapping(value = "All", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	@ApiOperation(value = "Find all curriculums")
	public List<Curriculum> getAllCurriculum(HttpServletRequest request){

		List<Curriculum> l = curriculumService.getAllCurriculum();

		String b = request.getParameter("bid");
		Integer bid = null;
		try {bid = Integer.parseInt(b);}
		catch(Exception e)
		{
			e.printStackTrace();
			bid = null;
		}
		if (bid == null)
		{return l;}

		for (Curriculum c : l)
		{
			Integer ver = bcmService.getMaster(bid, c.getCurriculumName());

			if (ver != null) {
				c.setIsMaster(ver == c.getCurriculumVersion() ? 1 : 0);
			}
			else
				c.setIsMaster(0);
		}

		return l;

	}

	/**
	 * Matthew Hill
	 * Slight modification of the original method to use the newly created
	 * Batch_curr_master table that represents an individual batch's master version
	 */
	@RequestMapping(value = "GetCurriculum", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	@ApiOperation(value = "Find a curriculum by id")
	public Curriculum getCurriculumById(HttpServletRequest request){
		int curriculumId = Integer.parseInt(request.getParameter("curriculumId"));
		int bid = Integer.parseInt(request.getParameter("bid"));
		Curriculum c = curriculumService.getCuricullumById(curriculumId);
		c.setIsMaster(bcmService.getMaster(bid, c.getCurriculumName()) == c.getCurriculumVersion()? 1 : 0 );
		return c;
	}
	
	@RequestMapping(value = "Schedule", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	@ApiOperation(value = "Find all subtopics for a curriculum")
	public List<CurriculumSubtopic> getAllCurriculumSchedule(HttpServletRequest request){
		Curriculum c = new Curriculum();
		c.setCurriculumId(Integer.parseInt(request.getParameter("curriculumId")));
		return curriculumSubtopicService.getCurriculumSubtopicForCurriculum(c);
	}

	@RequestMapping(value = "Schedules", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	@ApiOperation(value = "Find all subtopics")
	public List<CurriculumSubtopic> getAllCurriculumSchedules(){
		return curriculumSubtopicService.getCurriculums();
	}
	
	@RequestMapping(value = "TopicPool", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	@ApiOperation(value = "Find all subtopics")
	public List<SubtopicName> getTopicPool(){
		return subtopicService.getAllSubtopics();
	}
	
	@RequestMapping(value = "SubtopicPool", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	@ApiOperation(value = "Find all subtopic being covered by batches")
	public List<Subtopic> getSubtopicPool(){
		return subtopicService.getSubtopics();
	}

	@RequestMapping(value = "AddCurriculum", method = RequestMethod.GET)
	@ApiOperation(value = "Add a new curriculum to the system")
	public void addSchedule(HttpServletRequest req) throws JsonMappingException, IOException{
		ObjectMapper mapper = new ObjectMapper();
		CurriculumSubtopicDTO c = mapper.readValue(req.getParameter("json"), CurriculumSubtopicDTO.class);
		
		//save curriculum object first

		Curriculum curriculum = new Curriculum();
		curriculum.setCurriculumCreator(c.getMeta().getCurriculum().getCurriculumCreator());
		curriculum.setCurriculumdateCreated(c.getMeta().getCurriculum().getCurriculumdateCreated());
		curriculum.setCurriculumName(c.getMeta().getCurriculum().getCurriculumName());
		curriculum.setCurriculumNumberOfWeeks(c.getMeta().getCurriculum().getCurriculumNumberOfWeeks());
		curriculum.setCurriculumVersion(c.getMeta().getCurriculum().getCurriculumVersion());
		curriculum.setIsMaster(c.getMeta().getCurriculum().getIsMaster());

		curriculumService.save(curriculum);
		

		int numWeeks = c.getWeeks().length;
		for(int i = 0; i < numWeeks; i++){
			DaysDTO[] days = c.getWeeks()[i].getDays();
			for(int j = 0; j < days.length; j++){
				SubtopicName[] subtopic = days[j].getSubtopics();
				for(int k = 0; k < subtopic.length; k++){
					CurriculumSubtopic cs = new CurriculumSubtopic();
					cs.setCurriculumSubtopicCurriculumID(curriculum);
					cs.setCurriculumSubtopicNameId(subtopic[k]);
					cs.setCurriculumSubtopicWeek(i + 1);
					cs.setCurriculumSubtopicDay(j + 1);
					curriculumSubtopicService.saveCurriculumSubtopic(cs);
				}
			}
		}
	}

	@RequestMapping(value = "MakeMaster", method = RequestMethod.GET)
	@ApiOperation("Updates a curriculum to be the master curriculum")
	public void markCurriculumAsMaster(HttpServletRequest request){

		String b = request.getParameter("bid");

		if (b != null && b != "") {
			Integer bid = Integer.parseInt(request.getParameter("bid"));

			String cname = request.getParameter("cname");
			Integer version = Integer.parseInt(request.getParameter("version"));
			bcmService.setMaster(bid, cname, version);
			//find the curriculum with same name and isMaster = 1; set to 0; save
		}

	}
	
	//syncs a curriculum with batch from Assignforce
	@RequestMapping(value = "SyncBatch/{id}", method = RequestMethod.GET)
	@ApiOperation(value = "Syncs a curriculum with batch from AssignForce")
	public void syncBatch(@PathVariable int id) throws CustomException{
		Batch currBatch = batchService.getBatchById(id);
		String batchType = currBatch.getType().getName();
		
		//get curriculums with same batchTypes
		List<Curriculum> curriculumList = curriculumService.findAllCurriculumByName(batchType);
		
		//find the master curriculum; otherwise find one with most up to date version
		Curriculum c = null;
		for(int i = 0;  i < curriculumList.size(); i++){
			//master version found
			if(curriculumList.get(i).getIsMaster() == 1)
				c = curriculumList.get(i);
		}
		
		//if master not found, get latest version
		if(c == null){
			int min = curriculumList.get(0).getCurriculumVersion();
			Curriculum tempCurric = curriculumList.get(0);
			for(int i = 1; i < curriculumList.size(); i++){
				if(curriculumList.get(i).getCurriculumVersion() > min){
					min = curriculumList.get(i).getCurriculumVersion();
					tempCurric = curriculumList.get(i);
				}
			}
			c = tempCurric;
		}
		
		//get all curriculumSubtopics associated with curriculum
		List<CurriculumSubtopic> subtopicList = curriculumSubtopicService.getCurriculumSubtopicForCurriculum(c);
		
		//logic goes here to add to calendar
		if(subtopicService.getNumberOfSubtopics(id) ==  0){
			batchService.addCurriculumSubtopicsToBatch(subtopicList, currBatch);
		}else{
			throw new CustomException("Batch already synced");
		}
	}

}
