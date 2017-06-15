package com.github.aconsultinggmbh.networking.message;

import java.io.Serializable;
import java.util.HashMap;

import com.github.aconsultinggmbh.point.MapPosition;

public class MessageHashMap implements Serializable {
	private HashMap<String,MapPosition> hashmapMapPositionMessage;
    private HashMap<String,Integer> hashMapIntegerMessage;
    private HashMap<String, Boolean> hashMapBooleanMessage;
    
	public MessageHashMap() {
		super();
		hashmapMapPositionMessage = new HashMap<String, MapPosition>();
		hashMapIntegerMessage = new HashMap<String, Integer>();
		hashMapBooleanMessage = new HashMap<String, Boolean>();
	}
	
	public HashMap<String, MapPosition> getHashMapMapPositionMessage() {
		return hashmapMapPositionMessage;
	}

	public void setHashMapMapPositionMessage(HashMap<String, MapPosition> hashmapMapPositionMessage) {
		this.hashmapMapPositionMessage = hashmapMapPositionMessage;
	}

	public HashMap<String, Integer> getHashMapIntegerMessage() {
		return hashMapIntegerMessage;
	}

	public void setHashMapIntegerMessage(HashMap<String, Integer> hashMapIntegerMessage) {
		this.hashMapIntegerMessage = hashMapIntegerMessage;
	}

	public HashMap<String, Boolean> getHashMapBooleanMessage() {
		return hashMapBooleanMessage;
	}

	public void setHashMapBooleanMessage(HashMap<String, Boolean> hashMapBooleanMessage) {
		this.hashMapBooleanMessage = hashMapBooleanMessage;
	}
}
