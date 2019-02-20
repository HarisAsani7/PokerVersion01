package jabberwocky.letterBased.appClasses;

import java.util.ArrayList;
import java.util.HashMap;

import jabberwocky.letterBased.ServiceLocator;
import jabberwocky.letterBased.ServiceLocator.Mode;
import jabberwocky.letterBased.abstractClasses.Model;
import jabberwocky.letterBased.appClasses.dataClasses.*;

/**
 * Copyright 2015, FHNW, Prof. Dr. Brad Richards. All rights reserved. This code
 * is licensed under the terms of the BSD 3-clause license (see the file
 * license.txt).
 * 
 * @author Brad Richards
 */
public class App_Model extends Model {
	private int numUnits;
	ServiceLocator serviceLocator;
	private HashMap<String, ArrayList<HashEntry>> trainedData = new HashMap<>();

	public App_Model() {
		serviceLocator = ServiceLocator.getServiceLocator();
		serviceLocator.getLogger().info("Application model initialized");
	}

	public int getNumEntries() {
		return trainedData.size();
	}

	public int getNumLinks() {
		int sum = 0;
		for (ArrayList<HashEntry> list : trainedData.values()) {
			for (HashEntry he : list)
				sum += he.getQuantity();
		}
		return sum;
	}

	/**
	 * This method clears the training data
	 */
	public void clearTrainingData() {
		trainedData = new HashMap<>();
	}

	/**
	 * This method accepts a string and (if not yet set) the amount of data to use
	 * for prediction. It then processes the string and adds it to the training
	 * database.
	 */
	public void train(int numUnits, String data) {
		if (this.numUnits <= 0) this.numUnits = numUnits;

		StringBuffer sb = new StringBuffer(data);
		preprocessData(sb); // Remove excess whitespace
		Sequence sequence = new Sequence(BOF_Unit.BOF);

		while (sb.length() > 0) {
			TrainingUnit tu = parseTrainingUnit(sb);
			trainOneUnit(sequence, tu);
			sequence.addUnit(tu, numUnits);
		}
		trainOneUnit(sequence, EOF_Unit.EOF);
	}

	/**
	 * Parse one unit from the beginning of the StringBuffer, removing the parsed
	 * data.
	 */
	private TrainingUnit parseTrainingUnit(StringBuffer sb) {
		TrainingUnit tu = null;
		if (serviceLocator.getMode() == Mode.CharacterMode) {
			tu = new CharUnit(sb.charAt(0));
			sb.deleteCharAt(0);
		} else if (serviceLocator.getMode() == Mode.WordMode) {
			String word;
			// parse out one word, including any punctuation or end-of-line character as
			// part of the word
			int posSpace = sb.indexOf(" ");
			int posEOL = sb.indexOf("\n");

			if (posSpace >= 0 && (posEOL < 0 || posSpace < posEOL)) {
				// Parse to next space, discarding the space
				word = sb.substring(0, posSpace);
				sb.delete(0, posSpace + 1);
			} else if (posEOL >= 0 && (posSpace < 0 || posEOL < posSpace)) {
				// Parse to next EOL, including the EOL character
				word = sb.substring(0, posEOL + 1);
				sb.delete(0, posEOL + 1);
			} else { // last word of the file
				word = sb.toString();
				sb.delete(0, sb.length());
			}
			tu = new WordUnit(word);
		}
		return tu;
	}

	/**
	 * Add one unit to the training data
	 */
	private void trainOneUnit(Sequence sequence, TrainingUnit c) {
		ArrayList<HashEntry> hashEntries = getHashEntries(sequence);
		boolean found = false;
		for (HashEntry entry : hashEntries) {
			if (entry.getFollowingUnit().equals(c)) {
				entry.incrementQuantity();
				found = true;
				break;
			}
		}
		if (!found) {
			hashEntries.add(new HashEntry(1, c));
		}
	}

	/**
	 * Generate text using the training data.
	 */
	public String generateText() {
		StringBuffer sb = new StringBuffer();
		Sequence sequence = new Sequence(BOF_Unit.BOF);
		TrainingUnit t = BOF_Unit.BOF;
		while (!t.equals(EOF_Unit.EOF)) {
			t = genOneUnit(sequence);
			if (!t.equals(EOF_Unit.EOF)) sb.append(t.toString());
			sequence.addUnit(t, numUnits);
		}
		postprocessData(sb);
		return sb.toString();
	}

	/**
	 * This method generates a single unit, based on the given sequence
	 */
	private TrainingUnit genOneUnit(Sequence sequence) {
		ArrayList<HashEntry> hashEntries = trainedData.get(sequence.toString());
		int totalOptions = sum(hashEntries);
		int pick = (int) (Math.random() * totalOptions);
		TrainingUnit t = pickUnit(hashEntries, pick);
		return t;
	}

	/**
	 * Count the total options in the list of HashEntries
	 */
	private int sum(ArrayList<HashEntry> hashEntries) {
		int sum = 0;
		for (HashEntry he : hashEntries)
			sum += he.getQuantity();
		return sum;
	}

	/**
	 * Pick a unit based on the number given
	 */
	private TrainingUnit pickUnit(ArrayList<HashEntry> hashEntries, int pick) {
		int sum = 0;
		TrainingUnit t = null;
		for (int i = 0; i < hashEntries.size(); i++) {
			HashEntry he = hashEntries.get(i);
			sum += he.getQuantity();
			if (sum > pick) {
				t = he.getFollowingUnit();
				break;
			}
		}
		return t;
	}

	/**
	 * This method returns the list of HashEntries for the character-sequence given.
	 */
	private ArrayList<HashEntry> getHashEntries(Sequence sequence) {
		ArrayList<HashEntry> hashEntries = trainedData.get(sequence.toString());
		if (hashEntries == null) {
			hashEntries = new ArrayList<>();
			trainedData.put(sequence.toString(), hashEntries);
		}
		return hashEntries;
	}

	/**
	 * Clean input data in the given StringBuffer, by removing doubled whitespace
	 * characters, and removing any whitespace at the start or end of the file.
	 */
	private void preprocessData(StringBuffer sb) {
		// Remove whitespace at start and end
		while (sb.length() > 0 && sb.charAt(0) <= 0x20)
			sb.deleteCharAt(0);
		while (sb.length() > 0 && sb.charAt(sb.length() - 1) <= 0x20)
			sb.deleteCharAt(sb.length() - 1);

		// Remove doubled whitespace characters
		for (int pos = 0; pos < sb.length() - 1; pos++) {
			while (sb.length() > 1 && sb.charAt(pos) <= 0x20 && sb.charAt(pos) == sb.charAt(pos + 1))
				sb.deleteCharAt(pos + 1);
		}
	}
	
	/**
	 * Format the output data for better display, by doubling line-breaks.
	 */
	private void postprocessData(StringBuffer sb) {
		for (int pos = 0; pos < sb.length(); pos++) {
			if (sb.charAt(pos) == '\n') {
				sb.insert(pos, '\n');
				pos++;
			}
		}
	}
}
