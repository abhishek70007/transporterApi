package com.springboot.DocumentAPI.Services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.springboot.DocumentAPI.Constants.CommonConstants;
import com.springboot.DocumentAPI.Dao.DocumentDataDao;
import com.springboot.DocumentAPI.Dao.EntityDao;
import com.springboot.DocumentAPI.Entities.DocumentData;
import com.springboot.DocumentAPI.Entities.EntityData;
import com.springboot.TransporterAPI.Exception.BusinessException;
import com.springboot.DocumentAPI.Model.AddEntityDoc;
import com.springboot.DocumentAPI.Model.DocData;
import com.springboot.DocumentAPI.Model.GetEntityDoc;
import com.springboot.DocumentAPI.Model.UpdateEntityDoc;
import com.springboot.DocumentAPI.Response.DocumentCreateResponse;
import com.springboot.DocumentAPI.Response.DocumentUpdateResponse;

import lombok.extern.slf4j.Slf4j;

import com.springboot.TransporterAPI.Exception.EntityNotFoundException;

@Slf4j
@Service
public class DocServiceImpl implements DocService {

	@Autowired
	private DocumentDataDao docDataDao;

	@Autowired
	private EntityDao entityDao;

	@Autowired
	private AmazonS3 client;

	private String bucketname = "liveasydocsdev";

	@Transactional(rollbackFor=Exception.class)
	@Override
	public DocumentCreateResponse addDocument(AddEntityDoc entityDoc) {
		log.info("addDocument service started");

		DocumentCreateResponse dcr = new DocumentCreateResponse(); 
		Optional<EntityData> D = entityDao.findById(entityDoc.getEntityId());

		//check if account already exist or not
		if(D.isPresent()) {
			throw new BusinessException(CommonConstants.docExists);
		}

		EntityData entityData = new EntityData();
		DocumentData docList = new DocumentData();

		//get current date
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MMM, yyyy");
		LocalDateTime now = LocalDateTime.now();

		//generate random document id
		String docId = "doc:"+UUID.randomUUID();

		//set entity data
		entityData.setEntityId(entityDoc.getEntityId());
		entityData.setDocumentId(docId);
		entityData.setDate(dtf.format(now).toString());
		entityData.setVerfied(false);

		for(DocData user: entityDoc.getDocuments()) {

			String imageName = entityDoc.getEntityId()+user.getDocumentType()+".jpg";

			//convert bytedata to file object
			File fileobj = new File(imageName);
			try (FileOutputStream fos = new FileOutputStream(fileobj)) { 
				fos.write(user.getData());
			}
			catch(IOException e) {	
			}

			//upload data and generate url
			client.putObject(bucketname, imageName, fileobj);
			client.setObjectAcl(bucketname, imageName, CannedAccessControlList.PublicReadWrite);
			URL url = client.getUrl(bucketname, imageName);
			fileobj.delete();

			//set document data
			docList.setId("id"+UUID.randomUUID());
			docList.setDocumentId(docId);
			docList.setDocumentType(user.getDocumentType());
			docList.setDocumentLink(url.toString());
			docDataDao.save(docList);

		}

		entityDao.save(entityData);
		dcr.setStatus(CommonConstants.uploadSuccess);
		log.info("addDocument service response is returned");
		return dcr;
	}


	@Transactional(readOnly = true, rollbackFor = Exception.class)
	@Override
	public GetEntityDoc getDocuments(String entityId) {
		log.info("getDocuments by Id Service started");

		GetEntityDoc getEntity = new GetEntityDoc();
		String documentId = entityDao.findByEntityId(entityId);

		if(documentId==null)
			throw new EntityNotFoundException(DocumentData.class,"id",entityId);

		getEntity.setEntityId(entityId);
		getEntity.setDocuments(docDataDao.findByDocId(documentId));

		log.info("GetEntityDoc service response is returned");
		return getEntity;
	}

	@Transactional(readOnly = true, rollbackFor = Exception.class)
	@Override
	public List<EntityData> getByEntityType(String entityType, Integer pageNo) {
		log.info("getByEntityType service started");
		if(pageNo == null) {
			pageNo = 0;
		}
		Pageable page = PageRequest.of(pageNo, 15,  Sort.Direction.DESC, "timestamp");

		if(entityType != null) {
			if(entityType.equalsIgnoreCase("Truck")) {
				return entityDao.findByTruckType(page);
			}
			else if(entityType.equalsIgnoreCase("Transporter")) {
				return entityDao.findByTransporterType(page);
			}
			else if(entityType.equalsIgnoreCase("Shipper")) {
				return entityDao.findByShipperType(page);
			}
		}

		log.info("getByEntityType service response is returned");
		return entityDao.getAll(page);
	}
	@Transactional(rollbackFor = Exception.class)
	@Override
	public DocumentUpdateResponse updateDocuments(String entityId, UpdateEntityDoc updateEntityDoc) {

		log.info("updateDocuments service is started");

		DocumentUpdateResponse dur = new DocumentUpdateResponse();
		DocumentData updateDoc = new DocumentData();
		String documentId = entityDao.findByEntityId(entityId);

		//check if document with entityId exists or not
		if(documentId == null) {
			throw new EntityNotFoundException(DocumentData.class,"id",entityId);
		}

		List<DocumentData> doclist = docDataDao.findByDocId(documentId);
		boolean found;
		for(DocData user: updateEntityDoc.getDocuments()) {
			found=false;
			for(DocumentData database: doclist) {
				//update existing document
				if(user.getDocumentType().equals(database.getDocumentType())) {

					if(user.getData()!= null) {
						String s[] = database.getDocumentLink().split("/");
						client.deleteObject(bucketname, s[3]);
						String imageName = entityId+user.getDocumentType()+".jpg";
						File fileobj = new File(imageName);

						try (FileOutputStream fos = new FileOutputStream(fileobj)) { 
							fos.write(user.getData());
						}
						catch(IOException e) {	

						}

						client.putObject(bucketname, imageName, fileobj);
						client.setObjectAcl(bucketname, imageName, CannedAccessControlList.PublicReadWrite);
						URL url = client.getUrl(bucketname, imageName);
						database.setDocumentLink(url.toString());
						fileobj.delete();
					}

					database.setVerified(user.isVerified());
					docDataDao.save(database);
					found = true;
				}
			}

			//doubt
			//add new data
			if(!found) {
				//set object name
				String imageName = entityId+user.getDocumentType()+".jpg";

				//convert byte data to file object
				File fileobj = new File(imageName);
				try (FileOutputStream fos = new FileOutputStream(fileobj)) { 
					fos.write(user.getData());
				}
				catch(IOException e) {	
				}

				//upload data and get url
				client.putObject(bucketname, imageName, fileobj);
				client.setObjectAcl(bucketname, imageName, CannedAccessControlList.PublicReadWrite);
				URL url = client.getUrl(bucketname, imageName);
				fileobj.delete();

				//set new document data
				updateDoc.setDocumentId(documentId);
				updateDoc.setId("doc:"+UUID.randomUUID());
				updateDoc.setDocumentType(user.getDocumentType());
				updateDoc.setDocumentLink(url.toString());
				docDataDao.save(updateDoc);
			}
		}

		//check if all documents of given entityId are verfied or not
		EntityData entity = entityDao.findById(entityId).get();
		List<DocumentData> verifiedTest = docDataDao.findByDocId(documentId);
		for(DocumentData d: verifiedTest) {
			if(d.isVerified()==false) {
				entity.setVerfied(false);
				break;
			}
			entity.setVerfied(true);
		}
		entityDao.save(entity);

		dur.setStatus(CommonConstants.updatedSuccess);

		log.info("updateDocuments response is returned");
		return dur;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void deleteDocuments(String entityId) {
		log.info("deleteDocuments Serivce started");
		String documentId = entityDao.findByEntityId(entityId);

		//check if document with entityId exists or not
		if(documentId == null) {
			throw new EntityNotFoundException(DocumentData.class,"id",entityId);
		}

		List<DocumentData> doclist = docDataDao.findByDocId(documentId);

		for(DocumentData database: doclist) {				
			String s[] = database.getDocumentLink().split("/");
			client.deleteObject(bucketname, s[3]);
		}

		docDataDao.deleteByDocumentId(documentId);
		entityDao.deleteById(entityId);

	}
}
