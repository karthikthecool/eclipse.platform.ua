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
package org.eclipse.help.internal.dynamic;

import org.eclipse.help.Node;

/*
 * A handler that is notified when the document processor visits a node,
 * allowing it to process the node and return a result.
 */
public abstract class NodeHandler {
	
	public static final short UNHANDLED = 0;
	public static final short HANDLED_CONTINUE = 1;
	public static final short HANDLED_SKIP = 2;
	
	private NodeProcessor processor;
	
	/*
	 * Will be called for every node visited by the processor,
	 * except those requested to be skipped.
	 */
	public abstract short handle(Node node, String id);

	/*
	 * Returns the processor that is calling this handler.
	 */
	public NodeProcessor getProcessor() {
		return processor;
	}

	/*
	 * Sets the processor that is calling this handler. This should only
	 * be called by the processor.
	 */
	public void setProcessor(NodeProcessor processor) {
		this.processor = processor;
	}	
}