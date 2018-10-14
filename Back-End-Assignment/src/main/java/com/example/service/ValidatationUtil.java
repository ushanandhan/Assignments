package com.example.service;

import java.io.File;
import java.io.FileReader;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.JAXB;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import com.example.model.Record;
import com.example.model.Records;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;

@Service
public class ValidatationUtil {

	Workbook workbook;
	
	
	public void readFile(File file) throws Exception {

		if(file.getName().endsWith(".xml")) {
			System.out.println("******** It is Xml file ******************");
			
			Records records = JAXB.unmarshal(file, Records.class);
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(System.out, records);
			Set<Record> uniqueRecords = uniqueRecords(records.getRecords());
			checkEndBalance(uniqueRecords);
			
		}else if(file.getName().endsWith(".csv")) {
			System.out.println("******** It is Excel file ******************");
			FileReader filereader = new FileReader(file); 
	        List<Record> records = new ArrayList<>();
	        CSVReader csvReader = new CSVReader(filereader); 
	        String[] nextRecord; 
	        int i = 0;
	        while ((nextRecord = csvReader.readNext()) != null) { 
	        	/*if(i==0) {
	        		for (String cell : nextRecord) { 
	                    System.out.print(cell + "\t"); 
	                } 
	        	}*/
	        	if(i>0) {
	        		Record record = new Record();
		        	record.setReference(Integer.parseInt(nextRecord[0]));
		        	record.setAccountNumber(nextRecord[1]);
		        	record.setDescription(nextRecord[2]);
		        	record.setStartBalance(new BigDecimal(nextRecord[3]));
		        	record.setMutation(new BigDecimal(nextRecord[4]));
		        	record.setEndBalance(new BigDecimal(nextRecord[5]));
		        	records.add(record);
	        	}
	        	i++;
	        	
	        }
	        Set<Record> uniqueRecords = uniqueRecords(records);
			checkEndBalance(uniqueRecords);
		}
	}

	private Set<Record> uniqueRecords(List<Record> records) {
		Set<Record> uniqueRecords = new HashSet<>();
		List<Record> duplicateRecords = new ArrayList<>();
		for (Record record : records) {
//			Checking duplicate files
			if(!uniqueRecords.add(record)) {
				duplicateRecords.add(record);
			}
			uniqueRecords.add(record);
		}
		
		if(duplicateRecords.isEmpty()) {
			System.out.println(" There is no Duplicate Records");
		}else {
			System.out.println("Duplicate Records are :");
			for (Record record : duplicateRecords) {
				System.out.println("***** Reference id : "+record.getReference()+" *********");
			}
		}
		return uniqueRecords;
	}

	private void checkEndBalance(Set<Record> uniqueRecords) {
		MathContext mc = new MathContext(5);
		for (Record record : uniqueRecords) {
			BigDecimal start = record.getStartBalance().add(record.getMutation(),mc);
			System.out.println(start+":"+record.getEndBalance());
			if(!start.equals(record.getEndBalance())) {
				System.out.println(record.getReference()+" record validation failed");
			}
		}	

	}
	

}
