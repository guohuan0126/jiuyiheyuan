package com.duanrong.newadmin.interceptor;

import java.util.List;

import javax.xml.namespace.QName;

import org.apache.cxf.binding.soap.SoapHeader;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.headers.Header;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.Phase;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * 
 * @Description: 认证拦截
 * @Author: 林志明
 * @CreateDate: Sep 15, 2014
 */
public class HeaderIntercepter extends AbstractSoapInterceptor {
	private String qname;
	private AuthInfo authInfo;

	public HeaderIntercepter() {
		super(Phase.WRITE);
	}

	public void handleMessage(SoapMessage soapMessage) throws Fault {

		System.out.println("come in ClientHeaderIntercepter");

		QName name = new QName("RequestSOAPHeader");
		Document doc = DOMUtils.createDocument();

		Element spId = doc.createElement("tns:spId");
		spId.setTextContent(authInfo.getSpName());

		Element spPass = doc.createElement("tns:spPassword");
		spPass.setTextContent(authInfo.getSpPassword());

		Element root = doc.createElementNS(qname, "tns:RequestSOAPHeader");
		root.appendChild(spId);
		root.appendChild(spPass);

		SoapHeader head = new SoapHeader(name, root);
		List<Header> headers = soapMessage.getHeaders();
		headers.add(head);

	}

	private Object getHeader() {
		QName qName = new QName("", "", "");
		Document document = DOMUtils.createDocument();
		Element element = document.createElementNS(qname, "RequestSOAPHeader");
		Element token = document.createElement("token");
		token.setTextContent("kkkkk");
		// element.appendChild(token);
		SoapHeader header = new SoapHeader(qName, token);
		return (header);
	}

	public String getQname() {
		return qname;
	}

	public void setQname(String qname) {
		this.qname = qname;
	}

	public AuthInfo getAuthInfo() {
		return authInfo;
	}

	public void setAuthInfo(AuthInfo authInfo) {
		this.authInfo = authInfo;
	}

}