/*******************************************************************************
 * Copyright (c) 2000, 2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.help.ui.internal;
import org.eclipse.swt.widgets.Control;
public class HyperlinkAdapter implements IHyperlinkListener {
	/**
	 * HyperlinkAdapter constructor comment.
	 */
	public HyperlinkAdapter() {
		super();
	}
	/**
	 * @param linkLabel
	 *            org.eclipse.swt.widgets.Label
	 */
	@Override
	public void linkActivated(Control linkLabel) {
	}
	/**
	 * @param linkLabel
	 *            org.eclipse.swt.widgets.Label
	 */
	@Override
	public void linkEntered(Control linkLabel) {
	}
	/**
	 * @param linkLabel
	 *            org.eclipse.swt.widgets.Label
	 */
	@Override
	public void linkExited(Control linkLabel) {
	}
}
