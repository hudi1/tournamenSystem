package org.tahom.web.model;

import java.io.File;
import java.util.UUID;

import org.apache.wicket.model.AbstractReadOnlyModel;
import org.tahom.processor.callable.AbstractPdfCallable;
import org.tahom.processor.service.callable.CallableService;
import org.tahom.web.WicketApplication;

public class TournamentFileReadOnlyModel<I> extends AbstractReadOnlyModel<File> {

	private static final long serialVersionUID = 1L;

	private CallableService callableService;
	private String uuid;

	public TournamentFileReadOnlyModel(CallableService callableService, I input,
	        Class<? extends AbstractPdfCallable<I>> callableClass) {
		this.uuid = UUID.randomUUID().toString();
		this.callableService = callableService;
		callableService.createFile(uuid, WicketApplication.getFilesPath(), input, callableClass);
	}

	@Override
	public File getObject() {
		return callableService.getFile(uuid);
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		callableService.finalizeFile(uuid);
	}
}