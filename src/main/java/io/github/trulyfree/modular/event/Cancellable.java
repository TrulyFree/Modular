package io.github.trulyfree.modular.event;

public interface Cancellable {

	public boolean setCancelled(boolean cancelled);
	public boolean isCancelled();
	
}
