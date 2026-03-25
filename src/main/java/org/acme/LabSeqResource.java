package org.acme;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import java.math.BigInteger;

@Path("/labseq")
public class LabSeqResource {

    @Inject
    LabSeqService labSeqService;

    @GET
    @Path("/{n}")
    @Produces(MediaType.TEXT_PLAIN)
    @Operation(summary = "Calculate LabSeq Value", description = "Returns the value from the labseq sequence at index n.")
    @APIResponse(responseCode = "200", description = "The calculated labseq value.")
    @APIResponse(responseCode = "400", description = "Invalid index provided (e.g., negative).")
    public Response getLabSeqValue(
            @Parameter(description = "The non-negative integer index to calculate.", required = true)
            @PathParam("n") int n) {
        
        if (n < 0) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Index 'n' must be a non-negative integer.")
                    .build();
        }

        BigInteger result = labSeqService.calculate(n);
        return Response.ok(result.toString()).build();
    }
}
