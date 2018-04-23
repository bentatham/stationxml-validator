package edu.iris.dmc.station.rules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UnitTable {

	public static List<String> units = new ArrayList<String>(Arrays.asList("meter", "m", "m/s", "m/s**2", "centimeter",
			"cm", "cm/s", "cm/s**2", "millimeter", "mm", "mm/s", "mm/s**2", "mm/hour", "micrometer", "um", "um/s",
			"um/s**2", "nanometer", "nm", "nm/s", "nm/s**2", "second", "s", "millisecond", "ms", "microsecond", "us",
			"nanosecond", "ns", "minute", "min", "hour", "radian", "rad", "microradian", "urad", "nanoradian", "nrad",
			"rad/s", "rad/s**2", "degree", "deg", "kelvin", "K", "celsius", "degC", "candela", "cd", "pascal", "Pa",
			"kilopascal", "kPa", "hectopascal", "hPa", "bar", "millibar", "mbar", "ampere", "A", "milliamp", "mA",
			"volt", "V", "millivolt", "mV", "microvolt", "uV", "ohm", "hertz", "Hz", "newton", "N", "joule", "J",
			"tesla", "T", "nanotesla", "nT", "strain", "m/m", "m**3/m**3", "cm/cm", "mm/mm", "um/um", "nm/nm",
			"microstrain", "watt", "W", "milliwatt", "mW", "V/m", "W/m**2", "gap", "reboot", "byte", "bit", "bit/s",
			"percent", "%", "count", "counts", "number", "unitless"));

	public static boolean contains(String name) {
		return units.contains(name);
	}

	public static boolean containsCaseInsensitive(String name) {
		if (name == null) {

		}
		name = name.toLowerCase();
		return units.contains(name);
	}
}