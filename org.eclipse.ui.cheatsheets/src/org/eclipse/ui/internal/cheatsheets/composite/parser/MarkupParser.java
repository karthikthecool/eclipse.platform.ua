/*******************************************************************************
 * Copyright (c) 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

package org.eclipse.ui.internal.cheatsheets.composite.parser;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class MarkupParser {

	public static String parseAndTrimTextMarkup(Node parentNode) {
		return parseMarkup(parentNode).trim();
	}
	
	private static String parseMarkup(Node parentNode) {
	    NodeList children = parentNode.getChildNodes();
		StringBuffer text = new StringBuffer();
		for (int i = 0; i < children.getLength(); i++) {
			Node childNode = children.item(i);
			if (childNode.getNodeType() == Node.TEXT_NODE) {
				text.append(escapeText(childNode.getNodeValue()));
			} else if (childNode.getNodeType() == Node.ELEMENT_NODE) {
				text.append('<');
				text.append(childNode.getNodeName());
				text.append('>');
				text.append(parseMarkup(childNode));
				text.append("</"); //$NON-NLS-1$
				text.append(childNode.getNodeName());
				text.append('>');
			}
		}
		return text.toString();
	}

	private static String escapeText(String nodeValue) {
		String temp = nodeValue.replaceAll(">", "&gt;");  //$NON-NLS-1$//$NON-NLS-2$
		return temp.replaceAll("<", "&lt;"); //$NON-NLS-1$ //$NON-NLS-2$
	}

}
