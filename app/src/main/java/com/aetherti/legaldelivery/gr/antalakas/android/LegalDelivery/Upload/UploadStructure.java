package com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.Upload;

import java.util.ArrayList;

import com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.Message.LegalDeliveryMessage;

public class UploadStructure {
	
	public ArrayList<LegalDeliveryMessage> LegalDelivery = new ArrayList<LegalDeliveryMessage>();
	
	public ArrayList<LegalDeliveryMessage> getLegalDelivery() {
		return LegalDelivery;
	}
	void setLegalDelivery(ArrayList<LegalDeliveryMessage> legalDelivery) {
		LegalDelivery = legalDelivery;
	}
}