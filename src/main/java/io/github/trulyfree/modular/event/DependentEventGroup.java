package io.github.trulyfree.modular.event;

import java.util.Collection;

public interface DependentEventGroup {
	
	public Event enactNextEvent();
	public Event size();
	public Collection<Event> events();

}
