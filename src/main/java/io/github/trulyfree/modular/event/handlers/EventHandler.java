package io.github.trulyfree.modular.event.handlers;

import io.github.trulyfree.modular.event.Event;
import io.github.trulyfree.modular.event.ModifiableEventGroup;
import io.github.trulyfree.modular.module.Module;

public interface EventHandler<T extends Event> extends ModifiableEventGroup<T>, Module, Event {}
