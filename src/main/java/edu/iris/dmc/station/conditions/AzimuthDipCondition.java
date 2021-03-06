package edu.iris.dmc.station.conditions;

import edu.iris.dmc.fdsn.station.model.Azimuth;
import edu.iris.dmc.fdsn.station.model.Channel;
import edu.iris.dmc.fdsn.station.model.Dip;
import edu.iris.dmc.fdsn.station.model.Network;
import edu.iris.dmc.fdsn.station.model.Station;
import edu.iris.dmc.station.rules.Message;
import edu.iris.dmc.station.rules.Result;

public class AzimuthDipCondition extends AbstractCondition {

	public AzimuthDipCondition(boolean required, String description) {
		super(required, description);

	}

	@Override
	public Message evaluate(Network network) {
		throw new IllegalArgumentException("Not supported!");
	}

	@Override
	public Message evaluate(Station station) {
		throw new IllegalArgumentException("Not supported!");
	}

	@Override
	public Message evaluate(Channel channel) {
		Azimuth azimuth = channel.getAzimuth();
		Dip dip = channel.getDip();
		String code = channel.getCode();
		try {
		if("HLMN".indexOf(code.charAt(1)) >=0 | "hlmn".indexOf(code.charAt(1)) >=0) {
			if (azimuth == null) {
				return Result.error("Azimuth must be included for channels with " + code.charAt(1) + " instrument values");
				
			}
			if (dip == null) {
				return Result.error("Dip must be included for channels with " + code.charAt(1) + " instrument values");
				
			}
			
		}
		}catch(Exception e) {	
			
		}
     		return Result.success();
		}

}
