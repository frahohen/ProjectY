package com.github.aconsultinggmbh.networking.message;


import java.io.Serializable;
import java.util.HashMap;

import com.github.aconsultinggmbh.point.MapPosition;

public class Message implements Serializable {
	
	private String labelMessage;
    private String stringMessage;
    private boolean booleanMessage;
    private int intMessage;
    private float floatMessage;
    private double doubleMessage;
    private MessageHashMap messageHashMap;
    private MapPosition mapPosition;
    
	public Message(String labelMessage) {
		super();
		this.labelMessage = labelMessage;
	}
	
	public Message(String labelMessage, MapPosition mapPosition) {
		super();
		this.labelMessage = labelMessage;
		this.mapPosition = mapPosition;
	}
	
	public Message(String labelMessage, MessageHashMap messageHashMap) {
		super();
		this.labelMessage = labelMessage;
		this.messageHashMap = messageHashMap;
	}

	public Message(String labelMessage, String stringMessage) {
		super();
		this.labelMessage = labelMessage;
		this.stringMessage = stringMessage;
	}
	
	public Message(String labelMessage, boolean booleanMessage) {
		super();
		this.labelMessage = labelMessage;
		this.booleanMessage = booleanMessage;
	}
	
	public Message(String labelMessage, float floatMessage) {
		super();
		this.labelMessage = labelMessage;
		this.floatMessage = floatMessage;
	}
	
	public Message(String labelMessage, int intMessage) {
		super();
		this.labelMessage = labelMessage;
		this.intMessage = intMessage;
	}
	
	public Message(String labelMessage, double doubleMessage) {
		super();
		this.labelMessage = labelMessage;
		this.doubleMessage = doubleMessage;
	}
	
	public String getLabelMessage() {
		return labelMessage;
	}
	public void setLabelMessage(String labelMessage) {
		this.labelMessage = labelMessage;
	}
	public String getStringMessage() {
		return stringMessage;
	}
	public void setStringMessage(String stringMessage) {
		this.stringMessage = stringMessage;
	}
	public boolean isBooleanMessage() {
		return booleanMessage;
	}
	public void setBooleanMessage(boolean booleanMessage) {
		this.booleanMessage = booleanMessage;
	}
	public int getIntMessage() {
		return intMessage;
	}
	public void setIntMessage(int intMessage) {
		this.intMessage = intMessage;
	}
	public float getFloatMessage() {
		return floatMessage;
	}
	public void setFloatMessage(float floatMessage) {
		this.floatMessage = floatMessage;
	}
	public double getDoubleMessage() {
		return doubleMessage;
	}
	public void setDoubleMessage(double doubleMessage) {
		this.doubleMessage = doubleMessage;
	}
	
	public MapPosition getMapPosition() {
		return mapPosition;
	}
	public void setMapPosition(MapPosition mapPosition) {
		this.mapPosition = mapPosition;
	}

	public MessageHashMap getMessageHashMap() {
		return messageHashMap;
	}

	public void setMessageHashMap(MessageHashMap messageHashMap) {
		this.messageHashMap = messageHashMap;
	}	
	
}
