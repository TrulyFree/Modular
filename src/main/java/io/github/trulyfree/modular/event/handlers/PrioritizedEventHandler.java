package io.github.trulyfree.modular.event.handlers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import io.github.trulyfree.modular.event.Event;
import io.github.trulyfree.modular.event.PrioritizedEvent;
import io.github.trulyfree.modular.general.Priority;

public class PrioritizedEventHandler
		implements Collection<PrioritizedEvent>, EventHandler<PrioritizedEvent> {

	protected volatile ArrayList<ArrayList<PrioritizedEvent>> lists;

	public PrioritizedEventHandler() {}
	
	@Override
	public boolean enact() {
		for (Event event : this) {
			event.enact();
		}
		return true;
	}
	
	private PrioritizedEventHandler(PrioritizedEventHandler handler) {
		setup();
		for (int ord = 0; ord < handler.lists.size(); ord++) {
			for (PrioritizedEvent item : handler.lists.get(ord)) {
				lists.get(ord).add(item);
			}
		}
	}
	
	public boolean setup() {
		int length = Priority.values().length;
		lists = new ArrayList<>(length);
		for (int index = 0; index < length; index++) {
			lists.add(new ArrayList<PrioritizedEvent>());
		}
		return true;
	}

	@Override
	public boolean isReady() {
		return lists != null;
	}

	@Override
	public boolean destroy() {
		lists = null;
		return true;
	}

	@Override
	public boolean add(PrioritizedEvent ero) {
		if (ero == null || ero.getPriority() == null) {
			return false;
		}
		Priority val = ero.getPriority();
		int index = val.ordinal();
		return lists.get(index).add(ero);
	}

	@Override
	public int size() {
		if (lists == null) {
			return 0;
		}
		int size = 0;
		for (ArrayList<PrioritizedEvent> list : lists) {
			size += list.size();
			if (size < 0) {
				return Integer.MAX_VALUE;
			}
		}
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public boolean contains(Object o) {
		if (lists == null) {
			return false;
		}
		for (ArrayList<PrioritizedEvent> list : lists) {
			for (PrioritizedEvent item : list) {
				if (item.equals(o)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public Iterator<PrioritizedEvent> iterator() {
		return new IteratorImpl();
	}

	@Override
	public Object[] toArray() {
		if (size() == 0) {
			return new Object[0];
		}
		Object[] objarray = new Object[size()];
		Iterator<PrioritizedEvent> iter = iterator();
		for (int i = 0; i < objarray.length; i++) {
			objarray[i] = iter.next();
		}
		return objarray;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <U> U[] toArray(U[] a) {
		U[] toReturn = Arrays.copyOf(a, a.length);
		try {
			for (int i = 0; i < toReturn.length; i++) {
				toReturn[i] = (U) get(i);
			}
		} catch (ClassCastException e) {
			throw new ArrayStoreException();
		}
		return toReturn;
	}

	public PrioritizedEvent get(int ord, int index) {
		if (lists == null || ord >= lists.size() || ord < 0 || index >= lists.get(ord).size() || index < 0) {
			return null;
		}
		return lists.get(ord).get(index);
	}

	public PrioritizedEvent get(int index) {
		if (lists == null) {
			return null;
		}
		int[] locPair = getLocPair(index);
		return get(locPair[0], locPair[1]);
	}

	public PrioritizedEvent get(Priority val, int index) {
		return get(val.ordinal(), index);
	}

	public int[] getLocPair(int index) {
		int ord;
		for (ord = 0; ord < lists.size(); ord++) {
			int size = lists.get(ord).size();
			if (index < size) {
				break;
			}
			index -= size;
		}
		return new int[] { ord, index };
	}

	public PrioritizedEvent remove(int ord, int index) {
		if (lists == null || ord >= lists.size() || ord < 0 || index >= lists.get(ord).size() || index < 0) {
			return null;
		}
		return lists.get(ord).remove(index);
	}

	public PrioritizedEvent remove(int index) {
		if (lists == null) {
			return null;
		}
		int[] locPair = getLocPair(index);
		return remove(locPair[0], locPair[1]);
	}

	public PrioritizedEvent remove(Priority val, int index) {
		return remove(val.ordinal(), index);
	}

	@Override
	public boolean remove(Object o) {
		if (o == null || !(o instanceof PrioritizedEvent) || size() == 0) {
			return false;
		}
		Priority val;
		try {
			val = ((PrioritizedEvent) o).getPriority();
		} catch (ClassCastException e) {
			return false;
		}
		return lists.get(val.ordinal()).remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		for (Object o : c) {
			if (!contains(o)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean addAll(Collection<? extends PrioritizedEvent> c) {
		boolean changed = false;
		for (PrioritizedEvent item : c) {
			if (add(item)) {
				changed = true;
			}
		}
		return changed;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		boolean changed = false;
		for (Object o : c) {
			if (remove(o)) {
				changed = true;
			}
		}
		return changed;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		boolean changed = false;
		for (ArrayList<PrioritizedEvent> list : lists) {
			for (int i = list.size() - 1; i >= 0; i--) {
				if (!c.contains(list.get(i))) {
					list.remove(i);
					changed = true;
				}
			}
		}
		return changed;
	}

	@Override
	public void clear() {
		for (ArrayList<PrioritizedEvent> list : lists) {
			list.clear();
		}
	}

	private class IteratorImpl implements Iterator<PrioritizedEvent> {

		int ord;

		@Override
		public PrioritizedEvent next() {
			PrioritizedEvent toDo;
			do {
				toDo = PrioritizedEventHandler.this.remove(ord, 0);
			} while (toDo == null && realign());
			return toDo;
		}

		@Override
		public boolean hasNext() {
			return realign();
		}

		@Override
		public void remove() {
			PrioritizedEventHandler.this.remove(ord, 0);
		}

		private boolean realign() {
			if (PrioritizedEventHandler.this.size() == 0) {
				return false;
			}
			for (ord = Priority.MAX.ordinal(); ord >= 0; ord--) {
				if (!lists.get(ord).isEmpty()) {
					return true;
				}
			}
			return false;
		}

	}

	@Override
	public boolean addEvent(PrioritizedEvent event) {
		return add(event);
	}

	@Override
	public PrioritizedEvent removeEvent(PrioritizedEvent event) {
		ArrayList<PrioritizedEvent> list = lists.get(event.getPriority().ordinal());
		int index = list.indexOf(event);
		if (index >= 0) {
			return list.remove(index);
		}
		return null;
	}

	@Override
	public boolean enactNextEvent() {
		for (int ord = Priority.MAX.ordinal(); ord >= 0; ord--) {
			ArrayList<PrioritizedEvent> list = lists.get(ord);
			if (!list.isEmpty()) {
				return list.remove(0).enact();
			}
		}
		return false;
	}

	@Override
	public Collection<PrioritizedEvent> getEvents() {
		return new PrioritizedEventHandler(this);
	}

}
