package io.itpl.microservice.base;

public interface ObjectValidator {

	public static final int MAXIMUM_LENGTH = 50;
	public static final int MINIMUM_LENGTH = 3;
	public static final String ERR_MSG_ATTRIBUTE_MISSING = "Missing Property in request :<%s>";
	public static final String ERR_MSG_INVALID_LENGTH = "Length of %s Must be in Range of %d - %d Characters";
	public void validate();
}
