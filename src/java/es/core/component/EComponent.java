package es.core.component;

import es.core.entity.Entity;
import es.core.entity.EntityManager;

/**
 * Interface for a component. This can be anything but it should implement <b>no
 * function, no code, no helper methods</b>, just keep data.<br />
 * Components should also have no setters and be immutable. To change the data
 * of a component, just create a new component and assign it to the entity.
 * <p />
 * 
 * A component can be mapped to an {@link Entity} using the
 * {@link EntityManager}.
 * 
 * @author Stephan Dreyer
 * 
 */
public interface EComponent {

}
