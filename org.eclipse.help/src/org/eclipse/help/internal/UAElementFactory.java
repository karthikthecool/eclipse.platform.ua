/*******************************************************************************
 * Copyright (c) 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.help.internal;

import java.lang.reflect.Constructor;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.help.IAnchor;
import org.eclipse.help.ICommandLink;
import org.eclipse.help.IContentExtension;
import org.eclipse.help.IContext;
import org.eclipse.help.IInclude;
import org.eclipse.help.IIndex;
import org.eclipse.help.IIndexEntry;
import org.eclipse.help.ILink;
import org.eclipse.help.IToc;
import org.eclipse.help.ITopic;
import org.eclipse.help.IUAElement;
import org.eclipse.help.internal.context.Context;
import org.eclipse.help.internal.extension.ContentExtension;
import org.eclipse.help.internal.index.Index;
import org.eclipse.help.internal.index.IndexEntry;
import org.eclipse.help.internal.toc.Link;
import org.eclipse.help.internal.toc.Toc;
import org.w3c.dom.Element;

/*
 * Constructs typed model elements from DOM elements or interface
 * implementations.
 */
public class UAElementFactory {

	private static final Class[][] interfaceTable = new Class[][] {
		{ ITopic.class, Topic.class },
		{ IIndexEntry.class, IndexEntry.class },
		{ IContext.class, Context.class },
		{ IAnchor.class, Anchor.class },
		{ IInclude.class, Include.class },
		{ ILink.class, Link.class },
		{ IToc.class, Toc.class },
		{ ICommandLink.class, CommandLink.class },
		{ IIndex.class, Index.class },
		{ IContentExtension.class, ContentExtension.class },
	};

	private static final Map classByElementName;

	static {
		classByElementName = Collections.synchronizedMap(new HashMap());
		classByElementName.put(Anchor.NAME, Anchor.class);
		classByElementName.put(Include.NAME, Include.class);
		classByElementName.put(Toc.NAME, Toc.class);
		classByElementName.put(Topic.NAME, Topic.class);
		classByElementName.put(Index.NAME, Index.class);
		classByElementName.put(IndexEntry.NAME, IndexEntry.class);
		classByElementName.put(Context.NAME, Context.class);
		classByElementName.put(CommandLink.NAME, CommandLink.class);
		classByElementName.put(Link.NAME, Link.class);
		classByElementName.put(ContentExtension.NAME_CONTRIBUTION, ContentExtension.class);
		classByElementName.put(ContentExtension.NAME_CONTRIBUTION_LEGACY, ContentExtension.class);
		classByElementName.put(ContentExtension.NAME_REPLACEMENT, ContentExtension.class);
		classByElementName.put(ContentExtension.NAME_REPLACEMENT_LEGACY, ContentExtension.class);
	}
	
	public static UAElement newElement(Element element) {
		String name = element.getNodeName();
		Class clazz = (Class)classByElementName.get(name);
		if (clazz != null) {
			try {
				Constructor constructor = clazz.getConstructor(new Class[] { Element.class });
				return (UAElement)constructor.newInstance(new Object[] { element });
			}
			catch (Exception e) {
				String msg = "Error creating document model element"; //$NON-NLS-1$
				HelpPlugin.logError(msg, e);
			}
		}
		return new UAElement(element);
	}

	public static UAElement newElement(IUAElement src) {
		for (int i=0;i<interfaceTable.length;++i) {
			Class interfaze = interfaceTable[i][0];
			Class clazz = interfaceTable[i][1];
			if (interfaze.isAssignableFrom(src.getClass())) {
				try {
					Constructor constructor = clazz.getConstructor(new Class[] { interfaze });
					return (UAElement)constructor.newInstance(new Object[] { src });
				}
				catch (Exception e) {
					String msg = "Error creating document model element"; //$NON-NLS-1$
					HelpPlugin.logError(msg, e);
				}
			}
		}
		return null;
	}
}