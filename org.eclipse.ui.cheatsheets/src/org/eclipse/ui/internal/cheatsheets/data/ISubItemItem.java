/*******************************************************************************
 * Copyright (c) 2002, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.ui.internal.cheatsheets.data;

import java.util.ArrayList;

public interface ISubItemItem {

	/**
	 * @param subItem the SubItem to add.
	 */
	public void addSubItem(AbstractSubItem subItem);

	/**
	 * @return Returns the subItems.
	 */
	public ArrayList<AbstractSubItem> getSubItems();

}
