package edu.iris.dmc.station.conditions;

import java.util.List;

import edu.iris.dmc.fdsn.station.model.Channel;
import edu.iris.dmc.fdsn.station.model.Network;
import edu.iris.dmc.fdsn.station.model.Response;
import edu.iris.dmc.fdsn.station.model.ResponseStage;
import edu.iris.dmc.fdsn.station.model.Station;
import edu.iris.dmc.fdsn.station.model.Units;
import edu.iris.dmc.station.restrictions.Restriction;
import edu.iris.dmc.station.rules.Message;
import edu.iris.dmc.station.rules.NestedMessage;
import edu.iris.dmc.station.rules.Result;
import edu.iris.dmc.station.rules.Success;
import edu.iris.dmc.station.rules.UnitTable;

public class UnitCondition extends ChannelRestrictedCondition {

	public UnitCondition(boolean required, String description, Restriction[] restrictions) {
		super(required, description, restrictions);
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
		if (channel == null) {
			return new Success("");
		}
		return evaluate(channel, channel.getResponse());
	}

	@Override
	public Message evaluate(Channel channel, Response response) {
		if (isRestricted(channel)) {
			return Result.success();
		}
		if (channel == null || channel.getResponse() == null || channel.getResponse().getStage() == null) {
			// should be checked somewhere else
			return new Success("");
		}

		List<ResponseStage> stages = channel.getResponse().getStage();

		if (stages.size() < 2) {
			return new Success("");
		}

		NestedMessage message = new NestedMessage();
		for (ResponseStage stage : stages) {
			if (!hasFilter(stage)) {
				continue;
			}
			Units[] units = getUnits(stage);
			if (units == null) {
				message.add(Result.error("stage [ null units for stage " + stage.getNumber().intValue() + "]"));
			} else {
				Units inputUnits = units[0];
				Units outputUnits = units[1];

				if (inputUnits == null || inputUnits.getName() == null) {
					message.add(Result.error("Inputunit cannot be null [stage " + stage.getNumber().intValue() + "]"));
				} else {
					boolean result = UnitTable.contains(inputUnits.getName());
					if (!result) {
						result = UnitTable.containsCaseInsensitive(inputUnits.getName());
						if (!result) {
							message.add(Result.error("[stage " + stage.getNumber().intValue() + "] invalid inputUnit "
									+ inputUnits.getName()));
						}
					}
				}

				if (outputUnits == null || outputUnits.getName() == null) {
					message.add(Result.error("Outputunit cannot be null [stage " + stage.getNumber().intValue() + "]"));
				} else {
					boolean result = UnitTable.contains(outputUnits.getName());
					if (!result) {
						result = UnitTable.containsCaseInsensitive(outputUnits.getName().toLowerCase());
						if (result) {
							message.add(Result.warning("[stage " + stage.getNumber().intValue()
									+ "] invalid outputUnits " + outputUnits.getName()));
						} else {
							message.add(Result.error("[stage " + stage.getNumber().intValue() + "] invalid outputUnits "
									+ outputUnits.getName()));
						}
					}
				}
			}
		}
		return message;
	}

	private boolean hasFilter(ResponseStage stage) {
		if (stage.getPolesZeros() != null) {
			return true;
		}
		if (stage.getResponseList() != null) {
			return true;
		}
		if (stage.getFIR() != null) {
			return true;
		}
		if (stage.getPolynomial() != null) {
			return true;
		}
		if (stage.getCoefficients() != null) {
			return true;
		}
		return false;
	}

	public Units[] getUnits(ResponseStage stage) {

		if (stage.getPolesZeros() != null) {
			return new Units[] { stage.getPolesZeros().getInputUnits(), stage.getPolesZeros().getOutputUnits() };
		}
		if (stage.getResponseList() != null) {
			return new Units[] { stage.getResponseList().getInputUnits(), stage.getResponseList().getOutputUnits() };
		}
		if (stage.getFIR() != null) {
			return new Units[] { stage.getFIR().getInputUnits(), stage.getFIR().getOutputUnits() };
		}
		if (stage.getPolynomial() != null) {
			return new Units[] { stage.getPolynomial().getInputUnits(), stage.getPolynomial().getOutputUnits() };
		}
		if (stage.getCoefficients() != null) {
			return new Units[] { stage.getCoefficients().getInputUnits(), stage.getCoefficients().getOutputUnits() };
		}
		return null;
	}

	public Message evaluate(Units units) {
		boolean result = UnitTable.contains(units.getName());
		if (!result) {
			result = UnitTable.containsCaseInsensitive(units.getName());
			if (!result) {
				return new edu.iris.dmc.station.rules.Error("Invalid inputUnit " + units.getName());
			}
		}
		return Result.success();
	}
}
