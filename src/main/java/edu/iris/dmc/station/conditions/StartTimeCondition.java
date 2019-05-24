package edu.iris.dmc.station.conditions;

import edu.iris.dmc.TimeUtil;
import edu.iris.dmc.fdsn.station.model.BaseNodeType;
import edu.iris.dmc.fdsn.station.model.Channel;
import edu.iris.dmc.fdsn.station.model.Network;
import edu.iris.dmc.fdsn.station.model.Station;
import edu.iris.dmc.station.rules.Message;
import edu.iris.dmc.station.rules.Result;

public class StartTimeCondition extends AbstractCondition {

	public StartTimeCondition(boolean required, String description) {
		super(required, description);
	}

	@Override
	public Message evaluate(Network network) {
		return check(network);
	}

	@Override
	public Message evaluate(Station station) {
		return check(station);
	}

	@Override
	public Message evaluate(Channel channel) {
		return check(channel);
	}

	public Message check(BaseNodeType node) {
		if (node == null) {
			throw new IllegalArgumentException("Node cannot be null");
		}
		if (node.getStartDate() == null) {
			return Result.error("startDate is null");
		}

		if (node.getEndDate() != null) {
			if(!TimeUtil.isBefore(node.getStartDate(), node.getEndDate())) {
				return Result
						.error("startDate " + node.getStartDate() + " must occur before endDate " + node.getEndDate());
			}
		}
		return Result.success();
	}
}
