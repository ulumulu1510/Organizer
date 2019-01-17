package org.eclipse.scout.orga.server.document;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.testing.platform.runner.RunWithSubject;
import org.eclipse.scout.rt.testing.server.runner.RunWithServerSession;
import org.eclipse.scout.rt.testing.server.runner.ServerTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.eclipse.scout.orga.database.or.core.tables.records.DocumentRecord;
import org.eclipse.scout.orga.database.table.TableDataInitializer;
import org.eclipse.scout.orga.server.ServerSession;

@RunWithSubject("root")
@RunWith(ServerTestRunner.class)
@RunWithServerSession(ServerSession.class)
public class DocumentServiceTest {

	private DocumentService service = null;

	@Before
	public void initService() {
		service = BEANS.get(DocumentService.class);
		assertNotNull("Document service could not be resolved", service);
	}

	@Test
	public void testReadmeDocument() {
		DocumentRecord docExpected = TableDataInitializer.DOCUMENT_ALICE_1;  
		String docId = docExpected.getId();
		String docName = docExpected.getName();
		assertTrue("Document not found: " + docName, service.exists(docId));
		
		DocumentRecord doc = service.get(docId);
		String fileContent = "hello world";
		
		assertNotNull("Document record should not be null", doc);
		assertEquals("Unexpected document id", docExpected.getId(), doc.getId());
		assertEquals("Unexpected document name", docExpected.getName(), doc.getName());
		assertEquals("Unexpected document size (length)", docExpected.getSize(), doc.getSize());
		assertEquals("Unexpected content length", fileContent.length(), doc.getContent().length);
		assertArrayEquals("Unexpected document content", fileContent.getBytes(), doc.getContent());
		assertEquals("Unexpected document user", docExpected.getUserId(), doc.getUserId());
		assertEquals("Unexpected document active state", docExpected.getActive(), doc.getActive());
	}

	@Test
	public void testLogoDocument() {
		DocumentRecord docExpected = TableDataInitializer.DOCUMENT_ALICE_2;  
		String docId = docExpected.getId();
		String docName = docExpected.getName();
		assertTrue("Document not found: " + docName, service.exists(docId));
		
		DocumentRecord doc = service.get(docId);
		
		assertNotNull("Document record should not be null", doc);
		assertEquals("Unexpected document id", docExpected.getId(), doc.getId());
		assertEquals("Unexpected document name", docExpected.getName(), doc.getName());
		assertEquals("Unexpected document size (length)", docExpected.getSize(), doc.getSize());
		assertEquals("Unexpected content length", docExpected.getContent().length, doc.getContent().length);
		assertArrayEquals("Unexpected document content", docExpected.getContent(), doc.getContent());
		assertEquals("Unexpected document user", docExpected.getUserId(), doc.getUserId());
		assertEquals("Unexpected document active state", docExpected.getActive(), doc.getActive());
	}
}
