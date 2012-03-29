package edu.ucdenver.bios.matrixsvc.resource;

import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.representation.Variant;
import org.restlet.resource.Get;

/**
 * Default request resource.  Called from the URI /matrix
 * Simply returns a self-identifying message for the server
 * Copied from edu.cudenver.bios.powersvc.resource.DefaultResource
 * @author VIJAY AKULA
 *
 */
public class DefaultResource {
    /**
     * Returns a full representation for a given variant.
     */
    @Get
    public Representation represent(Variant variant) {
        Representation representation = 
            new StringRepresentation("DefaultResource of Matrix REST Service", MediaType.TEXT_PLAIN);

        return representation;
    }
}
