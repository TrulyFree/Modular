package io.github.trulyfree.modular.event;

public interface DataCarrier<T> {

	public T getData();
	
	public boolean setData(T data);
	
}
