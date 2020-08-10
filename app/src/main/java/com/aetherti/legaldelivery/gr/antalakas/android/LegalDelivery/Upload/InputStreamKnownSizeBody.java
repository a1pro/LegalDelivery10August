package com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.Upload;

import java.io.InputStream;

import org.apache.http.entity.mime.content.InputStreamBody;




public class InputStreamKnownSizeBody extends InputStreamBody{
	 private int lenght;
	 
	 public InputStreamKnownSizeBody(
	   final InputStream in, final int lenght, final String mimeType) {
		 
	  super(in, mimeType);
	  this.lenght = lenght;
	 }
	 
	 @Override
	 public long getContentLength() {
	  return this.lenght;
	 }
	}