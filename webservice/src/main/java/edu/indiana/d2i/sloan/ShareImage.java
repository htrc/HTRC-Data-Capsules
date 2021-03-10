/*******************************************************************************
 * Copyright 2014 The Trustees of Indiana University
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package edu.indiana.d2i.sloan;

import edu.indiana.d2i.sloan.bean.ErrorBean;
import edu.indiana.d2i.sloan.bean.ImageInfoBean;
import edu.indiana.d2i.sloan.bean.VmInfoBean;
import edu.indiana.d2i.sloan.db.DBOperations;
import edu.indiana.d2i.sloan.exception.InvalidHostNameException;
import edu.indiana.d2i.sloan.exception.NoItemIsFoundInDBException;
import edu.indiana.d2i.sloan.hyper.HypervisorProxy;
import edu.indiana.d2i.sloan.hyper.ShareImageCommand;
import edu.indiana.d2i.sloan.utils.RolePermissionUtils;
import edu.indiana.d2i.sloan.vm.PortsPool;
import edu.indiana.d2i.sloan.vm.VMPorts;
import edu.indiana.d2i.sloan.vm.VMState;
import edu.indiana.d2i.sloan.vm.VMStateManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;

@Path("/shareimage")
public class ShareImage {
	private static final Logger logger = LoggerFactory.getLogger(ShareImage.class);
	private static final String ADMIN = "admin";

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response shareImage(@FormParam("vmid") String vmId, @FormParam("imagename") String imageName,
							   @FormParam("imagedescription") String imageDescription,
			@Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpServletRequest) {
		String userName = httpServletRequest.getHeader(Constants.USER_NAME);

		if (userName == null) {
			logger.error("Username is not present in http header.");
			return Response
					.status(500)
					.entity(new ErrorBean(500,
							"Username is not present in http header.")).build();
		}
		
		if (vmId == null || imageName == null || imageDescription == null) {
			return Response.status(400)
					.entity(new ErrorBean(400, "One or two form parameters are empty!"))
					.build();
		}

		try {
			if (!RolePermissionUtils.isPermittedCommand(userName, vmId, RolePermissionUtils.API_CMD.SHARE_IMAGE)) {
				return Response.status(400).entity(new ErrorBean(400,
						"User " + userName + " cannot perform task "
								+ RolePermissionUtils.API_CMD.SHARE_IMAGE + " on VM " + vmId)).build();
			}

			VmInfoBean vmInfo = DBOperations.getInstance().getVmInfo(userName, vmId);


			if (VMStateManager.isPendingState(vmInfo.getVmstate()) ||!VMStateManager.getInstance().transitTo(vmId, vmInfo.getVmstate(), VMState.IMAGE_SHARE_PENDING, userName)) {
				return Response
						.status(400)
						.entity(new ErrorBean(400, "Cannot share image of VM " + vmId
								+ " when it is " + vmInfo.getVmstate()))
						.build();
			}

			logger.info(userName + " requests to share the image of VM " + vmInfo.getVmid());
			vmInfo.setVmState(VMState.IMAGE_SHARE_PENDING);
			String newImagePath = Configuration.PropertyName.HOST_IMAGE_DIR + vmInfo.getImageName() + "-" +vmId + ".img";
			ImageInfoBean imageInfoBean = new ImageInfoBean(imageName, "PENDING", imageDescription, newImagePath, null, null);
			HypervisorProxy.getInstance().addCommand(new ShareImageCommand(vmInfo, imageInfoBean, userName));
			return Response.status(200).build();

		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			return Response.status(500).
					entity(new ErrorBean(500, "Internal error - " + e.getMessage())).build();
		} catch (NoItemIsFoundInDBException e) {
			logger.error(e.getMessage(), e);
			return Response
					.status(400)
					.entity(new ErrorBean(400, "Cannot find VM " + vmId
							+ " associated with username " + userName)).build();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return Response.status(500)
					.entity(new ErrorBean(500, e.getMessage())).build();
		}
	}
}
