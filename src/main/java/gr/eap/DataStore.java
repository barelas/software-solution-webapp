/*******************************************************************************
 * Copyright (c) 2012 George Barelas.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package gr.eap;

import gr.eap.model.Analysis;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class DataStore {
	
	private static DataStore dataStore = null;
	private static final String STOREFILE = "/home/barelas/opt/eap.diplomatiki.storefile";
	private final char[] randomChars = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','X','Y','Z'};

	private Map<String,Analysis> map;
	private Random random = new Random();
	
	public DataStore() throws Exception {
		File storeFile = new File(STOREFILE);
		if (storeFile.exists()) {
			loadMapFromFile();
			System.out.println("Store file loaded ok.");
		} else {
			map = new ConcurrentHashMap<String,Analysis>();
			saveMapToFile();
		}
	}
	
	public Analysis get(String code) {
		return map.get(code);
	}
	
	public synchronized static DataStore get() throws Exception {
		if (dataStore==null) {
			dataStore = new DataStore();
		}
		return dataStore;
	}
	
	public void saveAnalysis(Analysis analysis) throws Exception {
		if (analysis.getCode()==null) {
			analysis.setCode(getNewCode());
		}
		analysis.touch();
		map.put(analysis.getCode(),analysis);
		saveMapToFile();
	}
	
	private synchronized String getNewCode() {
		String code;
		do {
			code = generateDigitCode();
		} while (map.containsKey(code));
		return code;
	}
	
	private String generateDigitCode() {
		StringBuilder sb = new StringBuilder();
		for (int i=0;i<9;i++) {
			sb.append(randomChars[random.nextInt(randomChars.length)]);
		}
		return sb.toString();
	}
	
	private void saveMapToFile() throws Exception {
		OutputStream file = new FileOutputStream(STOREFILE);
		OutputStream buffer = new BufferedOutputStream(file);
		ObjectOutput output = new ObjectOutputStream(buffer);
		output.writeObject(map);
		output.close();
	}
	
	private void loadMapFromFile() throws Exception {
		InputStream file = new FileInputStream(STOREFILE);
		InputStream buffer = new BufferedInputStream(file);
		ObjectInput input = new ObjectInputStream (buffer);
		map = (Map<String,Analysis>) input.readObject();
		input.close();
	}
	
	public synchronized void clear() throws Exception {
		map.clear();
		saveMapToFile();
	}
	
	public List<Analysis> getAll() {
		return new LinkedList<Analysis>(map.values());
	}
}
