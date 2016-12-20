package es.core.system;

import es.core.entity.Entity;
import es.core.entity.EntityManager;

/**
 * Interface for a system. This can be anything that implements function. A
 * system can be anything, but Ideally should store no entity-specific data.
 * <p />
 * 
 * A component can be mapped to an {@link Entity} using the
 * {@link EntityManager}.
 * 
 * @author Stephan Dreyer
 * 
 */
public interface ESystem {
  public void process(float tpf);
}
