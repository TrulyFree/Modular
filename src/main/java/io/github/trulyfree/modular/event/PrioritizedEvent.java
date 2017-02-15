package io.github.trulyfree.modular.event;

public interface PrioritizedEvent extends Event, Comparable<Event> {

	public boolean setPriority(EventPriority priority);
	public EventPriority getPriority();

	@Override
	public default int compareTo(Event event) {
		if (event instanceof PrioritizedEvent) {
			return getPriority().compareTo(((PrioritizedEvent) event).getPriority());
		} else {
			return 1;
		}
	}

}
