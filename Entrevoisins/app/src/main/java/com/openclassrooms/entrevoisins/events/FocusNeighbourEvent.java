package com.openclassrooms.entrevoisins.events;

import com.openclassrooms.entrevoisins.model.Neighbour;

public class FocusNeighbourEvent {

	public Neighbour neighbour;

	public FocusNeighbourEvent(Neighbour neighbour) {
		this.neighbour = neighbour;
	}
}
