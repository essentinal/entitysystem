package es.core.entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import es.core.component.EComponent;

public final class MapEntityManager extends EntityManager {
  private final ConcurrentHashMap<Entity, ConcurrentHashMap<Class<? extends EComponent>, EComponent>> components = new ConcurrentHashMap<Entity, ConcurrentHashMap<Class<? extends EComponent>, EComponent>>();

  @SuppressWarnings("unchecked")
  @Override
  public <T extends EComponent> T getComponent(final Entity entity,
      final Class<T> compClasses) {
    final ConcurrentHashMap<Class<? extends EComponent>, EComponent> entityMap = components
        .get(entity);
    if (entityMap == null) {
      return null;
    } else {
      return (T) entityMap.get(compClasses);
    }
  }

  @Override
  public List<EComponent> getComponents(final Entity entity,
      final Class<? extends EComponent>... compClasses) {
    final List<EComponent> list = new ArrayList<EComponent>();
    final ConcurrentHashMap<Class<? extends EComponent>, EComponent> entityMap = components
        .get(entity);
    if (entityMap != null) {
      for (final Class<? extends EComponent> controlType : compClasses) {
        final EComponent c = entityMap.get(controlType);
        list.add(c);
      }
    }

    return list;
  }

  @Override
  public void addComponents(final Entity entity, final EComponent... eComponent) {
    ConcurrentHashMap<Class<? extends EComponent>, EComponent> entityMap = components
        .get(entity);
    if (entityMap == null) {
      entityMap = new ConcurrentHashMap<Class<? extends EComponent>, EComponent>();
      components.put(entity, entityMap);
    }

    for (final EComponent comp : eComponent) {
      entityMap.put(comp.getClass(), comp);
    }
  }

  @Override
  public void removeComponent(final Entity entity,
      final Class<? extends EComponent> compClass) {
    final ConcurrentHashMap<Class<? extends EComponent>, EComponent> entityMap = components
        .get(entity);
    if (entityMap == null) {
      return;
    }
    entityMap.remove(compClass);
  }

  @Override
  public List<Entity> getEntities(
      final Class<? extends EComponent>... compClasses) {
    final LinkedList<Entity> list = new LinkedList<Entity>();
    // TODO bottleneck: ineffective iteration
    for (final Iterator<Entry<Entity, ConcurrentHashMap<Class<? extends EComponent>, EComponent>>> it = components
        .entrySet().iterator(); it.hasNext();) {
      final Entry<Entity, ConcurrentHashMap<Class<? extends EComponent>, EComponent>> entry = it
          .next();

      for (final Class<? extends EComponent> clss : compClasses) {
        if (entry.getValue().containsKey(clss)) {
          list.add(entry.getKey());
          break;
        }
      }
    }
    return list;
  }

  @Override
  public List<Entity> getEntities(final EComponent component) {
    final LinkedList<Entity> list = new LinkedList<Entity>();
    // TODO bottleneck: ineffective iteration
    for (final Iterator<Entry<Entity, ConcurrentHashMap<Class<? extends EComponent>, EComponent>>> it = components
        .entrySet().iterator(); it.hasNext();) {
      final Entry<Entity, ConcurrentHashMap<Class<? extends EComponent>, EComponent>> entry = it
          .next();
      if (entry.getValue().containsKey(component.getClass())) {
        final EComponent curComponent = entry.getValue().get(
            component.getClass());
        if (curComponent.equals(component)) {
          list.add(entry.getKey());
        }
      }
    }
    return list;
  }
}
