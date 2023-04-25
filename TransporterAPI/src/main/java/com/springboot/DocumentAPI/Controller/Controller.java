package com.springboot.DocumentAPI.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.DocumentAPI.Response.DocumentCreateResponse;
import com.springboot.DocumentAPI.Entities.EntityData;
import com.springboot.TransporterAPI.Exception.EntityNotFoundException;
import com.springboot.DocumentAPI.Model.AddEntityDoc;
import com.springboot.DocumentAPI.Model.DocData;
import com.springboot.DocumentAPI.Model.GetEntityDoc;
import com.springboot.DocumentAPI.Model.UpdateEntityDoc;
import com.springboot.DocumentAPI.Services.DocService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin
@RestController
public class Controller {

	@Autowired
	private DocService docService;

//	@GetMapping("/home")
//	public String home() {
//		return "Welcome to documentApi git action with env file check 5...!!!";
//	}

	@PostMapping("/document")
	public DocumentCreateResponse addDocument(@RequestBody AddEntityDoc addEntityDoc) {
		log.info("AddDocument controller started");
		return docService.addDocument(addEntityDoc);
	}

	@GetMapping("/document/{entityId}")
	public GetEntityDoc getDocuments(@PathVariable String entityId)
			throws EntityNotFoundException{
		log.info("getDocuments controller started");
		return docService.getDocuments(entityId);
	}

	@GetMapping("/document")
	public List<EntityData> getByEntityType(@RequestParam(required = false) String entityType,
			@RequestParam(required = false) Integer pageNo)
			throws EntityNotFoundException{
		log.info("getByEntityType controller started");
		return docService.getByEntityType(entityType, pageNo);
	}

	@PutMapping("/document/{entityId}")
	public ResponseEntity<Object> updateDocuments(@PathVariable String entityId, @RequestBody UpdateEntityDoc updateEntityDoc ) 
			throws EntityNotFoundException{
		log.info("updateDocuments controller started");
		return new ResponseEntity<>(docService.updateDocuments(entityId, updateEntityDoc),HttpStatus.OK);
	}

	@DeleteMapping("/document/{entityId}")
	public ResponseEntity<Object> deleteDocument(@PathVariable String entityId){
		log.info("updateDocuments controller started");
		docService.deleteDocuments(entityId);
		return new ResponseEntity<>("Successfully deleted",HttpStatus.OK);
	}
}
