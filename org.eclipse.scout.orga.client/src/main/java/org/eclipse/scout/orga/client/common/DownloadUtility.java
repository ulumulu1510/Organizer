package org.eclipse.scout.orga.client.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.eclipse.scout.rt.client.session.ClientSessionProvider;
import org.eclipse.scout.rt.client.ui.desktop.OpenUriAction;
import org.eclipse.scout.rt.client.ui.dnd.ResourceListTransferObject;
import org.eclipse.scout.rt.client.ui.dnd.TransferObject;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.exception.ProcessingException;
import org.eclipse.scout.rt.platform.resource.BinaryResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.eclipse.scout.orga.client.ClientSession;
import org.eclipse.scout.orga.shared.document.IDocumentService;

public class DownloadUtility {

	private static final Logger LOG = LoggerFactory.getLogger(DownloadUtility.class);

	private DownloadUtility() {
	}

	public static void download(File file) {
		try {
			byte[] content = Files.readAllBytes(file.toPath());
			download(file.getName(), content);
		} catch (IOException e) {
			LOG.error("Failed to read file content from " + file.getAbsolutePath(), e);
		}
	}

	public static void download(String name, byte[] content) {
		download(new BinaryResource(name, content));
	}

	public static void download(BinaryResource resource) {
		ClientSessionProvider.currentSession()
				.getDesktop()
				.openUri(resource, OpenUriAction.DOWNLOAD);
	}

	public static void upload(TransferObject t, String bookingId) {
		try {
			if (t instanceof ResourceListTransferObject) {
				for (BinaryResource resource : ((ResourceListTransferObject) t).getResources()) {
					addFile(resource, bookingId);
				}
			}
		} catch (RuntimeException e) {
			throw new ProcessingException(e.getMessage(), e);
		}
	}

	private static void addFile(BinaryResource file, String bookingId) {
		String name = file.getFilename();
		String username = ClientSession.get().getUserId();
		byte[] content = file.getContent();

		BEANS.get(IDocumentService.class).store(name, content, username, bookingId);
	}

}
