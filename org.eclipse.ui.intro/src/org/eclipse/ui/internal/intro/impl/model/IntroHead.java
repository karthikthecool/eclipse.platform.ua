/*******************************************************************************
 * Copyright (c) 2004, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

package org.eclipse.ui.internal.intro.impl.model;

import org.eclipse.core.runtime.IConfigurationElement;
import org.osgi.framework.Bundle;
import org.w3c.dom.Element;

/**
 * An intro Head element. Head elements are only interpreted for HTML case. They
 * are always inlined. Ignored in UI Forms case.
 */
public class IntroHead extends AbstractIntroElement {

    protected static final String TAG_HEAD = "head"; //$NON-NLS-1$

    private static final String ATT_SRC = "src"; //$NON-NLS-1$
    // default encoding is UTF-8
    private static final String ATT_ENCODING = "encoding"; //$NON-NLS-1$

    private String src;
    private String encoding;

    IntroHead(IConfigurationElement element) {
        super(element);
        src = element.getAttribute(ATT_SRC);
        encoding = element.getAttribute(ATT_ENCODING);
        if (encoding == null)
            encoding = "UTF-8"; //$NON-NLS-1$

        // Resolve.
        src = BundleUtil.getResourceLocation(src, element);
    }


    IntroHead(Element element, Bundle bundle, String base) {
        super(element, bundle);
        src = getAttribute(element, ATT_SRC);
        encoding = getAttribute(element, ATT_ENCODING);
        if (encoding == null)
            encoding = "UTF-8"; //$NON-NLS-1$

        // Resolve.
        src = BundleUtil.getResolvedResourceLocation(base, src, bundle);
    }


    /**
     * @return Returns the src.
     */
    public String getSrc() {
        return src;
    }

    /**
     * @return Returns the encoding of the inlined file. Default is UTF-8.
     */
    public String getInlineEncoding() {
        return encoding;
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.internal.intro.impl.model.IntroElement#getType()
     */
    public int getType() {
        return AbstractIntroElement.HEAD;
    }

}
