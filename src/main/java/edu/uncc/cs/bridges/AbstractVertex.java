package edu.uncc.cs.bridges;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Base class for visual graph vertices; either subclass it or use Vertex.
 * Implement `outgoing` with any List-compatible structure to get different
 * performance characteristics.
 * 
 * The default implementation Vertex uses a HashMap. You are free to use any you prefer.
 */
abstract public class AbstractVertex implements Comparable<AbstractVertex> {
	/**
	 * This is the string by which this AbstractVertex should be found.
	 * This is not a label; it includes provider information as well.
	 */
	final String identifier;
	
	/**
	 * Links, with properties other than just target Node.
	 */
	//public Map<String, Edge>outgoing;
	List<AbstractEdge> outgoing;
	/**
	 * Visualization properties for this Node.
	 */
	Map<String, String> properties = new HashMap<>();
	
	/**
	 * Create an AbstractVertex
	 * @param identifier  The unique, final id for this vertex. 
	 */
	public AbstractVertex(String identifier) {
		if(identifier!=null){
			this.identifier = identifier;
			this.setColor("black");
		}
		else 
			throw new IllegalArgumentException("param cannot be null.");
	}
	
	/// Accessors and mutators for visualization properties follow
	
	/**
	 * Get the String associated with this Vertex.
	 * This is not a label; it includes provider information as well.
	 * @return
	 */
	public String getIdentifier() {
		return identifier;
	}

	/**
	 * Get the color, according to CSS formats.
	 * By default, the color will be chosen at random.
	 * Setting the color to {@code null} or {@code ""} resets to defaults.
	 * 
	 * @param color Color as a String
	 * @see Validation#validateColor(String)
	 */
	public String getColor() {
		return properties.get("color");
	}
	
	/**
	 * Set the color, according to CSS formats.
	 * By default, the color will be chosen at random.
	 * Setting the color to {@code null} or {@code ""} resets to defaults.
	 * 
	 * @param color Color as a String
	 * @see Validation#validateColor(String)
	 */
	public AbstractVertex setColor(String color) {		
		color = color.toLowerCase();
		if (color == null || color.isEmpty()) {
			properties.put("color", "black");
		} else {
			Validation.validateColor(color);
			properties.put("color", color);
		}
		return this;
	}
	
	/**
	 * Take a node by it's identifier string and get it's shape
	 * @return "square" or "circle"
	 */
	public String getShape() {
		String prop = properties.get("shape");
		if (prop == null) {
			return "circle";
		} else {
			return prop;
		}
	}
	
	/**
	 * Take a node by it's identifier string and get it's shape
	 * @param shape "Circle" or "Square"
	 */
	public AbstractVertex setShape(String shape) {
		shape = shape.toLowerCase();
		if(shape.equals("square")){
			shape = "rect";
		}
		Validation.validateShape(shape);
		properties.put("shape", shape);
		
		return this;
	}
	
	/**
	 * Take a node by it's identifier string and get it's node color
	 * @param node  Node identifier
	 * @returns  Diameter of the node
	 */
	public double getSize() {
		String prop = properties.get("size");
		if (prop == null)
			return 1.0;
		else
			return Double.parseDouble(prop);
	}
	
	/**
	 * Take a node by it's identifier string and get it's node color
	 * @param pixels  Diameter of the node, in range [0.0, 50.0]
	 */
	public AbstractVertex setSize(double pixels) {
		Validation.validateSize(pixels);
		properties.put("size", Double.toString(pixels));
		
		return this;
	}
	
	/**
	 * Get the node's current opacity
	 * 0.0 is invisible
	 * 1.0 is opaque
	 * @returns  Alpha, in range [0.0, 1.0]
	 */
	public double getOpacity() {
		String prop = properties.get("opacity");
		if (prop == null)
			return 1.0;
		else
			return Double.parseDouble(prop);
	}
	
	/**
	 * Set the node opacity
	 * 0.0 is invisible
	 * 1.0 is opaque
	 * @param opacity  Alpha, in range [0.0, 1.0]
	 */
	public AbstractVertex setOpacity(double opacity) {
		Validation.validateOpacity(opacity);
		properties.put("opacity", Double.toString(opacity));
		
		return this;
	}
	
	/// Hash code and equals: implements map, but it only uses this.identifier

	/**
	 * Get the hash code for this AbstractVertex.
	 * The code is only based on the identifier.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((identifier == null) ? 0 : identifier.hashCode());
		return result;
	}

	/**
	 * Find whether two AbstractVertex's are the same.
	 * Based only on the identifier.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractVertex other = (AbstractVertex) obj;
		if (identifier == null) {
			if (other.identifier != null)
				return false;
		} else if (!identifier.equals(other.identifier))
			return false;
		return true;
	}
	
	/**
	 * Internal code for getting the properties of an AbstractVertex.
	 * 
	 * It produces (without the spaces or newlines):
	 * <tt>
	 * {
	 *  "name": "Some identifier",
	 *  "other CSS properties like color": any_JSON_value
	 * }
	 * @returns the encoded JSON string
	 */
	String getRepresentation() {
		String json = "{";
		for (Entry<String, String> entry : properties.entrySet()) {
			json += String.format("\"%s\": \"%s\", ", entry.getKey(), entry.getValue());
		}
		json += String.format("\"name\": \"%s\"", identifier);
		return json + "}";
	}

	/**
	 * Abstract Vertex comparator: sorts by identifier.
	 */
	@Override
	public int compareTo(AbstractVertex o) {
		if (o != null) {
			return identifier.compareTo(o.identifier);
		}
		return 0;
	}
	
	
}
