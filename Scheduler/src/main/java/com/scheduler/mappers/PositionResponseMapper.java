package com.scheduler.mappers;

import java.util.List;
import java.util.stream.Collectors;

import com.scheduler.apiPayloads.responses.PositionListItem;
import com.scheduler.models.Position;

public class PositionResponseMapper {
	
	public PositionListItem map(Position position)
	{
		PositionListItem positionListItem = new PositionListItem();
		positionListItem.setName(position.getName());
		positionListItem.setId(position.getId());
		return positionListItem;
	}
	
	public List<PositionListItem> map(List<Position> positions)
	{
		return positions.stream().map(this::map).collect(Collectors.toList());
	}
}
