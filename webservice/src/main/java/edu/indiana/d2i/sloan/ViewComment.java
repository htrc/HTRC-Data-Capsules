package edu.indiana.d2i.sloan;


import edu.indiana.d2i.sloan.bean.CommentResponseBean;
import edu.indiana.d2i.sloan.bean.ErrorBean;
import edu.indiana.d2i.sloan.db.DBOperations;
import edu.indiana.d2i.sloan.exception.NoItemIsFoundInDBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

/**
 * Created by ruili on 5/21/17.
 *
 * param: result id
 *
 * return: comment text content
 */

@Path("/viewcomment")
public class ViewComment {
    private static Logger logger = LoggerFactory.getLogger(ViewComment.class);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response viewComment(@QueryParam("resultid") String resultid,
                                    @Context HttpHeaders httpHeaders,
                                    @Context HttpServletRequest httpServletRequest) throws SQLException, NoItemIsFoundInDBException, ParseException, IOException {
        String userName = httpServletRequest.getHeader(Constants.USER_NAME);

        if(resultid == null) {
            return Response.status(204).entity(new ErrorBean(204, "This result does not exist!")).build();
        }

        try {

            String res = DBOperations.getInstance().getComment(resultid);
            return Response.status(200).entity(new CommentResponseBean(res)).build();

        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return Response.status(500)
                    .entity(new ErrorBean(500, e.getMessage())).build();
        }
    }
}
