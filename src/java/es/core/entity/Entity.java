package es.core.entity;

import java.util.List;

import es.core.component.EComponent;

/**
 * An entity describes an ID of a game part.
 * 
 * @author Stephan Dreyer
 * 
 */
public final class Entity implements Cloneable, Comparable<Entity> {
  /**
   * The entitymanager is stored to allow quick access to components. It is
   * declared as transient to prevent it form being stored during serialization.
   */
  private static final transient EntityManager man = EntityManager
      .getInstance();

  /**
   * The core part of the entity, the Id.
   */
  private final Long id;

  /**
   * Creates a new entity with the given Id.
   * 
   * @param id
   *          The id
   */
  Entity(final long id) {
    this.id = id;
  }

  /**
   * Calls the {@link EntityManager} and retrieves a single component assigned
   * to this entity.
   * 
   * @param compClass
   *          The class of the component that should be retrieved.
   * @return The component, may be <code>null</code>.
   */
  public <T extends EComponent> T getComponent(final Class<T> compClass) {
    return man.getComponent(this, compClass);
  }

  /**
   * Calls the {@link EntityManager} and checks if the given component class is
   * assigned for this entity.
   * 
   * @param compClass
   *          The class of the component that should be checked.
   * @return <code>true</code> if the given component class is assigned to the
   *         entity.
   */
  public <T extends EComponent> boolean hasComponent(final Class<T> compClass) {
    return man.getComponent(this, compClass) != null;
  }

  /**
   * Calls the {@link EntityManager} and retrieves a list of components assigned
   * to this entity.
   * 
   * @param compClasses
   *          The classes of the components that should be retrieved.
   * @return The list components, may be empty.
   */
  public List<EComponent> getComponents(
      final Class<? extends EComponent>... compClasses) {
    return man.getComponents(this, compClasses);
  }

  /**
   * Calls the {@link EntityManager} and assigns one or more components to this
   * entity.
   * 
   * @param comps
   *          The components to assign.
   */
  public void addComponents(final EComponent... comps) {
    man.addComponents(this, comps);
  }

  /**
   * Calls the {@link EntityManager} and assigns a component to this entity.
   * 
   * @param comp
   *          The component to assign.
   */
  public void addComponent(final EComponent comp) {
    man.addComponents(this, comp);
  }

  /**
   * Calls the {@link EntityManager} and removes the component with the given
   * class from this entity.
   * 
   * @param componentClass
   *          The class of the component that should be removed.
   */
  public void removeComponent(final Class<? extends EComponent> componentClass) {
    man.removeComponent(this, componentClass);
  }

  /**
   * Gets the long id.
   * 
   * @return The id.
   */
  // public long getId() {
  // return id;
  // }

  /**
   * Entities are equal if the id is equal.
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(final Object o) {
    if (o instanceof Entity) {
      final Entity that = (Entity) o;
      return that.id == this.id;
    }
    return super.equals(o);
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }

  @Override
  public int compareTo(final Entity o) {
    return id.compareTo(o.id);
  }

  @Override
  public String toString() {
    return String.format("Entity(%d)", id);
  }

  @Override
  protected Entity clone() {
    try {
      return (Entity) super.clone();
    } catch (final CloneNotSupportedException e) {
      e.printStackTrace();
      return null;
    }
  }

  public static void main(final String[] args) {
    final Entity e1 = new Entity(10000000000L);
    final Entity e2 = e1.clone();

    System.out.println(e1);
    System.out.println(e1.hashCode());
    System.out.println();
    System.out.println(e2);
    System.out.println(e2.hashCode());
    System.out.println();
    System.out.println(e1.compareTo(e2));
    System.out.println(e1.equals(e2));

  }
}
