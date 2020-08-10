package com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.Message;

import java.util.ArrayList;

import org.apache.http.entity.FileEntity;

//import org.apache.http.entity.mime.FilterEntity;

public class NewValues {

	 private String $id;
	// private String PriductType;
	 public  ArrayList<NewMessage> $values = new ArrayList<NewMessage>();
	 public  ArrayList<FileEntity> $values2 = new ArrayList<FileEntity>();
	 
	  public String get$id() {
		    return $id;
		  }
	/*  public String getPriductType() {
		    return PriductType;
		  }
		*/  public void set$id( String $id ) {
		    this.$id = $id;
		  }
		  
//		  public void setPriductType( String PriductType ) {
//			    this.PriductType = PriductType;
//			  }
//		  
		  
		  
		  
}
