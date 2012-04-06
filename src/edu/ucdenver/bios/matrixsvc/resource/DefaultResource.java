package edu.ucdenver.bios.matrixsvc.resource;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import edu.ucdenver.bios.matrixsvc.application.MatrixConstants;

/**
 * Default request resource.  Called from the URI /matrix
 * Simply returns a self-identifying message for the server
 * Copied from edu.cudenver.bios.powersvc.resource.DefaultResource
 * @author VIJAY AKULA
 *
 */
public class DefaultResource extends ServerResource
{
    /**
     * Returns a full representation for a given variant.
     */
    @Get
    public String represent()
    {
        return "Matrix REST Service, version "+MatrixConstants.VERSION;
    }
}
