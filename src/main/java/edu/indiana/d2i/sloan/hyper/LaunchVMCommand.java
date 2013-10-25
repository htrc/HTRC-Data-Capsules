package edu.indiana.d2i.sloan.hyper;

import org.apache.log4j.Logger;

import edu.indiana.d2i.sloan.bean.VmInfoBean;
import edu.indiana.d2i.sloan.exception.RetriableException;
import edu.indiana.d2i.sloan.vm.VMState;
import edu.indiana.d2i.sloan.vm.VMStateManager;

public class LaunchVMCommand extends HypervisorCommand {
	private static Logger logger = Logger.getLogger(LaunchVMCommand.class);

	public LaunchVMCommand(VmInfoBean vminfo) {
		super(vminfo);
	}

	@Override
	public void execute() throws Exception {

		try {
			HypervisorResponse resp = hypervisor.launchVM(vminfo);

			if (logger.isDebugEnabled()) {
				logger.debug(resp.toString());
			}

		} catch (Exception e) {
			throw new RetriableException(e.getMessage(), e);
		}

		VMStateManager.getInstance().transitTo(vminfo.getVmid(),
				VMState.LAUNCHING, VMState.RUNNING);

	}

	@Override
	public void cleanupOnFailed() throws Exception {
		VMStateManager.getInstance().transitTo(vminfo.getVmid(),
				VMState.LAUNCHING, VMState.ERROR);
	}

	@Override
	public String toString() {
		return "launchvm " + vminfo;
	}
}
