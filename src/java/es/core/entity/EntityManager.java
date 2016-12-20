package es.core.entity;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicLong;

import es.core.component.EComponent;

public abstract class EntityManager {

  /**
   * For atomic generation of, IDs use an atomic long.
   */
  private final AtomicLong indexGenerator = new AtomicLong();

  public static EntityManager getInstance() {
    return Holder.INSTANCE;
  }

  /**
   * Creates a new {@link Entity} with a new id.
   * 
   * @return The new entity.
   */
  public Entity createEntity() {
    return new Entity(indexGenerator.getAndIncrement());
  }

  /**
   * Retrieves a single component assigned to the given {@link Entity}.
   * 
   * @param entity
   *          The entity.
   * @param compClass
   *          The class of the component that should be retrieved.
   * @return The component, may be <code>null</code>.
   */
  public abstract <T extends EComponent> T getComponent(final Entity entity,
      final Class<T> compClass);

  /**
   * Retrieves a list of components assigned to the given {@link Entity}.
   * 
   * @param entity
   *          The entity.
   * @param compClasses
   *          The classes of the components that should be retrieved.
   * @return The list components, may be empty.
   */
  public abstract List<EComponent> getComponents(final Entity entity,
      final Class<? extends EComponent>... compClasses);

  /**
   * Assigns a component to the given entity.
   * 
   * @param entity
   *          The entity.
   * @param comp
   *          The component to assign.
   */
  public abstract void addComponents(final Entity entity,
      final EComponent... components);

  /**
   * Removes a component from the given {@link Entity}.
   * 
   * @param entity
   *          The entity.
   * @param compClass
   *          The class of the component to remove.
   */
  public abstract void removeComponent(final Entity entity,
      final Class<? extends EComponent> compClass);

  /**
   * Gets a list of {@link Entity} objects that have components of the given
   * classes assigned.
   * 
   * @param compClasses
   *          The classes of the components.
   * @return The list of entities, may be empty.
   */
  public abstract List<Entity> getEntities(
      final Class<? extends EComponent>... compClasses);

  /**
   * Gets a list of {@link Entity} objects that have the given component
   * assigned.
   * 
   * @param component
   *          The component to look for.
   * @return The list of entities, may be empty.
   */
  public abstract List<Entity> getEntities(final EComponent component);

  /**
   * Holder pattern for thread safe lazy initialization.
   * 
   * @author Stephan Dreyer
   */
  private static final class Holder {

    private static EntityManager INSTANCE;

    static {
      final Properties p = new Properties();
      try {
        p.load(EntityManager.class.getClassLoader().getResourceAsStream(
            "es/es.properties"));
        final Class<?> clss = Class
            .forName(p.getProperty("EntityManagerClass"));
        INSTANCE = (EntityManager) clss.newInstance();
      } catch (final Exception e) {
        // if something goes wrong, instanciate the default manager
        INSTANCE = new MapEntityManager();
      }
    }

  }
}