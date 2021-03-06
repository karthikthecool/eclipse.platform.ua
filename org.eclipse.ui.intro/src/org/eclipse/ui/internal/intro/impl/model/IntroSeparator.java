/*******************************************************************************
 * Copyright (c) 2004, 2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

package org.eclipse.ui.internal.intro.impl.model;

import org.osgi.framework.Bundle;
import org.w3c.dom.Element;

/**
 * A horizontal ruler element.
 */
public class IntroSeparator extends AbstractBaseIntroElement {
    protected static final String TAG_HR = "hr"; //$NON-NLS-1$

    IntroSeparator(Element element, Bundle bundle, String base) {
        super(element, bundle);
    }

    @Override
	public int getType() {
        return AbstractIntroElement.HR;
    }
}
