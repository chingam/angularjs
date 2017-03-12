package com.metafour.mtrak.router.log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SOAPMessageHandler implements SOAPHandler<SOAPMessageContext> {
	private static final Logger logger = LoggerFactory.getLogger(SOAPMessageHandler.class);
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	private String site;
	private String type;
	private String cachePath;
	
	
	public SOAPMessageHandler(String site, String type, String cachePath) {
		this.site = site;
		this.type = type;
		this.cachePath = cachePath;
	}

	@Override
	public boolean handleMessage(SOAPMessageContext ctx) {
		Boolean isOut = (Boolean) ctx.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
		String suffix = isOut.booleanValue() ? "rq" : "rs";
		try {
			Transformer tf = TransformerFactory.newInstance().newTransformer();
			tf.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
			tf.setOutputProperty(OutputKeys.INDENT, "yes");
			tf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			tf.transform(new DOMSource(ctx.getMessage().getSOAPPart()), new StreamResult(new File(getAbsolutePath(suffix))));
		} catch (Exception e) {
			logger.warn("SOAP message transformation failed", e);
		}
		return true;
	}

	@Override
	public boolean handleFault(SOAPMessageContext context) {
		return false;
	}

	@Override
	public void close(MessageContext context) {
	}

	@Override
	public Set<QName> getHeaders() {
		return null;
	}
	
	private String getAbsolutePath(String suffix) {
		Date now = new Date();
		String path = cachePath + File.separator + site + File.separator + sdf.format(now) + File.separator;
		File dir = new File(path);
		if (!dir.exists()) dir.mkdirs();
		path += type + "-" + String.valueOf(System.nanoTime()) + "-" + suffix + ".xml";
		return path;
	}
}