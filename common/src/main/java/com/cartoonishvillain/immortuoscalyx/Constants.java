package com.cartoonishvillain.immortuoscalyx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Constants {
	public static final String MOD_ID = "immortuoscalyx";
	public static final String MOD_NAME = "Immortuos Calyx";
	public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

	public static List<String> decodeCSV(String CSV) {
        return new ArrayList<>(Arrays.asList(CSV.split(",")));
	}

	public static String encodeSCV(List<String> values) {
		String returnValue = "";
		for (String item : values) {
			returnValue += item;
			if (!Objects.equals(values.getLast(), item)) {
				returnValue += ",";
			}
		}
		return returnValue;
	}
}