package com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.Upload;

import java.util.ArrayList;

public class UploadValues {

	 private String $id;
	 private String  DeviceID;
	 
	 public  ArrayList<UploadStructure> $values = new ArrayList<UploadStructure>();
	 
	  public String get$id() {
		    return $id;
		  }
	  public String getDeviceID() {
		    return DeviceID;
		  }

		 // Setter Methods 

		  public void set$id( String $id ) {
		    this.$id = $id;
		  }
		  public void setDeviceID( String DeviceID ) {
			    this.DeviceID = DeviceID;
			  }
		  
}
