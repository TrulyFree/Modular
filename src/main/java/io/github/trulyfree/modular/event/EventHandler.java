package io.github.trulyfree.modular.event;

import io.github.trulyfree.modular.module.Module;

public interface EventHandler<T extends Event> extends ModifiableEventGroup<T>, Module {}
