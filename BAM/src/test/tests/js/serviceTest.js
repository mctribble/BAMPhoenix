/**
 * @author Jaydeep
 * Testing the ../static/service.js class. 
 */

var sampleServiceObject; 
beforeEach(module('bam'));
//mocking the service to create test cases using this mocked service.
beforeEach(function(){
	  module(function($provide){//$provide registers components with the injector
/*When you request a service, the $injector is responsible for finding the correct service provider,
instantiating it and then calling its $get service factory function to get the instance of the service.	*/	 

	    $provide.service( function(){
	      this.get= jasmine.createSpy('get');//Spy's on method's inside a contoller/service
	    });
	    
	    $provide.service( function(){
		      this.set= jasmine.createSpy('set');
		    });
	    
	    $provide.service( function(){
		      this.unset= jasmine.createSpy('unset');
		    });

	    $provide.service(function(){
		      this.remove= jasmine.createSpy('remove');
		    });
	  });
	});

//beforeEach is to pass the mocked service into a variable to access it in the testcases.
	beforeEach(inject(function(SessionService){
		sampleServiceObject=SessionService;
	}));
	
	
//test cases start here	each (it indicates a new test)
	it('should return the correct value when calling the get method', function(){
		 
		sampleServiceObject.set("user", "jaydeep");
		expect(sampleServiceObject.get("user")).toBe("jaydeep");

		
		});

	it('should return null value when calling the get method on a key which was never set', function(){
		 
		expect(sampleServiceObject.get("nonKey")).toBe(null);
		
		});
	
	it('should return null value when calling the get method on a key which was unset', function(){
		 
		sampleServiceObject.set("user", "jaydeep");
		expect(sampleServiceObject.get("user")).toBe("jaydeep");
		sampleServiceObject.unset("user");
		expect(sampleServiceObject.get("user")).toBe(null);
		
		});
	
	it('should return null value when calling the get method on a key which was removed', function(){
		 
		sampleServiceObject.set("user", "jaydeep");
		expect(sampleServiceObject.get("user")).toBe("jaydeep");
		sampleServiceObject.remove("user");
		expect(sampleServiceObject.get("user")).toBe(null);
		
		});