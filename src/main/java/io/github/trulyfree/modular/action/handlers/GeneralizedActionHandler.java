package io.github.trulyfree.modular.action.handlers;

import java.util.ArrayList;
import java.util.Collection;

import io.github.trulyfree.modular.action.Action;

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
 * GeneralizedActionHandler class. This class handles all currently held actions
 * synchronously whenever "enact" is called. This action handler is not thread safe.
 * 
 * @author vtcakavsmoace
 *
 */
public class GeneralizedActionHandler implements ActionHandler<Action> {

	/**
	 * A list defining the known actions, all of which will be enacted whenever enact is called.
	 */
	private ArrayList<Action> list;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * io.github.trulyfree.modular.action.ModifiableActionGroup#addAction(io.
	 * github.trulyfree.modular.action.Action)
	 */
	@Override
	public boolean addAction(Action action) {
		if (action == null) {
			return false;
		}
		return list.add(action);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * io.github.trulyfree.modular.action.ModifiableActionGroup#removeAction(io.
	 * github.trulyfree.modular.action.Action)
	 */
	@Override
	public boolean removeAction(Action action) {
		return list.remove(action);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see io.github.trulyfree.modular.action.ActionGroup#enactNextAction()
	 */
	@Override
	public boolean enactNextAction() {
		if (!list.isEmpty()) {
			return list.remove(0).enact();
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see io.github.trulyfree.modular.action.ActionGroup#getActions()
	 */
	@Override
	public Collection<Action> getActions() {
		return new ArrayList<Action>(list);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see io.github.trulyfree.modular.module.Module#setup()
	 */
	@Override
	public boolean setup() {
		list = new ArrayList<Action>();
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see io.github.trulyfree.modular.module.Module#isReady()
	 */
	@Override
	public boolean isReady() {
		return list != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see io.github.trulyfree.modular.module.Module#destroy()
	 */
	@Override
	public boolean destroy() {
		list = null;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see io.github.trulyfree.modular.action.ActionGroup#size()
	 */
	@Override
	public int size() {
		return list.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see io.github.trulyfree.modular.action.ModifiableActionGroup#clear()
	 */
	@Override
	public void clear() {
		list.clear();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see io.github.trulyfree.modular.action.Action#enact()
	 */
	@Override
	public boolean enact() {
		while (!list.isEmpty()) {
			enactNextAction();
		}
		return true;
	}

}
