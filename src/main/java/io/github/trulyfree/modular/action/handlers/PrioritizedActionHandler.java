package io.github.trulyfree.modular.action.handlers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import io.github.trulyfree.modular.action.Action;
import io.github.trulyfree.modular.action.PrioritizedAction;
import io.github.trulyfree.modular.general.Priority;

/* Modular library by TrulyFree: A general-use module-building library.
 * Copyright (C) 2016  VTCAKAVSMoACE
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * @author vtcakavsmoace
 *
 */
public class PrioritizedActionHandler
		implements Collection<PrioritizedAction>, ActionHandler<PrioritizedAction> {

	/**
	 * 
	 */
	protected final ArrayList<ArrayList<PrioritizedAction>> lists;

	/**
	 * 
	 */
	public PrioritizedActionHandler() {
		int length = Priority.values().length;
		lists = new ArrayList<>(length);
		for (int index = 0; index < length; index++) {
			lists.add(new ArrayList<PrioritizedAction>());
		}
	}
	
	/* (non-Javadoc)
	 * @see io.github.trulyfree.modular.action.Action#enact()
	 */
	@Override
	public boolean enact() {
		for (Action action : this) {
			action.enact();
		}
		return true;
	}
	
	/**
	 * @param handler
	 */
	protected PrioritizedActionHandler(PrioritizedActionHandler handler) {
		this();
		for (int ord = 0; ord < handler.lists.size(); ord++) {
			for (PrioritizedAction item : handler.lists.get(ord)) {
				lists.get(ord).add(item);
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see io.github.trulyfree.modular.module.Module#setup()
	 */
	public boolean setup() {
		return false;
	}

	/* (non-Javadoc)
	 * @see io.github.trulyfree.modular.module.Module#isReady()
	 */
	@Override
	public boolean isReady() {
		return lists != null;
	}

	/* (non-Javadoc)
	 * @see io.github.trulyfree.modular.module.Module#destroy()
	 */
	@Override
	public boolean destroy() {
		clear();
		return true;
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#add(java.lang.Object)
	 */
	@Override
	public boolean add(PrioritizedAction ero) {
		if (ero == null || ero.getPriority() == null) {
			return false;
		}
		Priority val = ero.getPriority();
		int index = val.ordinal();
		return lists.get(index).add(ero);
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#size()
	 */
	@Override
	public int size() {
		if (lists == null) {
			return 0;
		}
		int size = 0;
		for (ArrayList<PrioritizedAction> list : lists) {
			size += list.size();
			if (size < 0) {
				return Integer.MAX_VALUE;
			}
		}
		return size;
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#contains(java.lang.Object)
	 */
	@Override
	public boolean contains(Object o) {
		if (lists == null) {
			return false;
		}
		for (ArrayList<PrioritizedAction> list : lists) {
			for (PrioritizedAction item : list) {
				if (item.equals(o)) {
					return true;
				}
			}
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#iterator()
	 */
	@Override
	public Iterator<PrioritizedAction> iterator() {
		return new IteratorImpl();
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#toArray()
	 */
	@Override
	public Object[] toArray() {
		if (size() == 0) {
			return new Object[0];
		}
		Object[] objarray = new Object[size()];
		Iterator<PrioritizedAction> iter = iterator();
		for (int i = 0; i < objarray.length; i++) {
			objarray[i] = iter.next();
		}
		return objarray;
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#toArray(java.lang.Object[])
	 */
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

	/**
	 * @param ord
	 * @param index
	 * @return
	 */
	public PrioritizedAction get(int ord, int index) {
		if (lists == null || ord >= lists.size() || ord < 0 || index >= lists.get(ord).size() || index < 0) {
			return null;
		}
		return lists.get(ord).get(index);
	}

	/**
	 * @param index
	 * @return
	 */
	public PrioritizedAction get(int index) {
		if (lists == null) {
			return null;
		}
		int[] locPair = getLocPair(index);
		return get(locPair[0], locPair[1]);
	}

	/**
	 * @param val
	 * @param index
	 * @return
	 */
	public PrioritizedAction get(Priority val, int index) {
		return get(val.ordinal(), index);
	}

	/**
	 * @param index
	 * @return
	 */
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

	/**
	 * @param ord
	 * @param index
	 * @return
	 */
	public PrioritizedAction remove(int ord, int index) {
		if (lists == null || ord >= lists.size() || ord < 0 || index >= lists.get(ord).size() || index < 0) {
			return null;
		}
		return lists.get(ord).remove(index);
	}

	/**
	 * @param index
	 * @return
	 */
	public PrioritizedAction remove(int index) {
		if (lists == null) {
			return null;
		}
		int[] locPair = getLocPair(index);
		return remove(locPair[0], locPair[1]);
	}

	/**
	 * @param val
	 * @param index
	 * @return
	 */
	public PrioritizedAction remove(Priority val, int index) {
		return remove(val.ordinal(), index);
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#remove(java.lang.Object)
	 */
	@Override
	public boolean remove(Object o) {
		if (o == null || !(o instanceof PrioritizedAction) || size() == 0) {
			return false;
		}
		Priority val;
		try {
			val = ((PrioritizedAction) o).getPriority();
		} catch (ClassCastException e) {
			return false;
		}
		return lists.get(val.ordinal()).remove(o);
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#containsAll(java.util.Collection)
	 */
	@Override
	public boolean containsAll(Collection<?> c) {
		for (Object o : c) {
			if (!contains(o)) {
				return false;
			}
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#addAll(java.util.Collection)
	 */
	@Override
	public boolean addAll(Collection<? extends PrioritizedAction> c) {
		boolean changed = false;
		for (PrioritizedAction item : c) {
			if (add(item)) {
				changed = true;
			}
		}
		return changed;
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#removeAll(java.util.Collection)
	 */
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

	/* (non-Javadoc)
	 * @see java.util.Collection#retainAll(java.util.Collection)
	 */
	@Override
	public boolean retainAll(Collection<?> c) {
		boolean changed = false;
		for (ArrayList<PrioritizedAction> list : lists) {
			for (int i = list.size() - 1; i >= 0; i--) {
				if (!c.contains(list.get(i))) {
					list.remove(i);
					changed = true;
				}
			}
		}
		return changed;
	}

	/* (non-Javadoc)
	 * @see java.util.Collection#clear()
	 */
	@Override
	public void clear() {
		for (ArrayList<PrioritizedAction> list : lists) {
			list.clear();
		}
	}

	/**
	 * @author vtcakavsmoace
	 *
	 */
	private class IteratorImpl implements Iterator<PrioritizedAction> {

		/**
		 * 
		 */
		int ord;

		/* (non-Javadoc)
		 * @see java.util.Iterator#next()
		 */
		@Override
		public PrioritizedAction next() {
			PrioritizedAction toDo;
			do {
				toDo = PrioritizedActionHandler.this.remove(ord, 0);
			} while (toDo == null && realign());
			return toDo;
		}

		/* (non-Javadoc)
		 * @see java.util.Iterator#hasNext()
		 */
		@Override
		public boolean hasNext() {
			return realign();
		}

		/* (non-Javadoc)
		 * @see java.util.Iterator#remove()
		 */
		@Override
		public void remove() {
			PrioritizedActionHandler.this.remove(ord, 0);
		}

		/**
		 * @return
		 */
		private boolean realign() {
			if (PrioritizedActionHandler.this.size() == 0) {
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

	/* (non-Javadoc)
	 * @see io.github.trulyfree.modular.action.ModifiableActionGroup#addEvent(io.github.trulyfree.modular.action.Action)
	 */
	@Override
	public boolean addEvent(PrioritizedAction event) {
		return add(event);
	}

	/* (non-Javadoc)
	 * @see io.github.trulyfree.modular.action.ModifiableActionGroup#removeEvent(io.github.trulyfree.modular.action.Action)
	 */
	@Override
	public boolean removeEvent(PrioritizedAction event) {
		return remove(event);
	}

	/* (non-Javadoc)
	 * @see io.github.trulyfree.modular.action.ActionGroup#enactNextEvent()
	 */
	@Override
	public boolean enactNextEvent() {
		for (int ord = Priority.MAX.ordinal(); ord >= 0; ord--) {
			ArrayList<PrioritizedAction> list = lists.get(ord);
			if (!list.isEmpty()) {
				return list.remove(0).enact();
			}
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see io.github.trulyfree.modular.action.ActionGroup#getEvents()
	 */
	@Override
	public Collection<PrioritizedAction> getActions() {
		return new PrioritizedActionHandler(this);
	}

}
