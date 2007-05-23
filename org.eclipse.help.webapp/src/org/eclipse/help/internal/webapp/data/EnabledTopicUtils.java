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

package org.eclipse.help.internal.webapp.data;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.help.IIndexEntry;
import org.eclipse.help.ITopic;
import org.eclipse.help.UAContentFilter;
import org.eclipse.help.internal.base.HelpEvaluationContext;

/**
 * Utilities to test for enabled topics, index entries etc. 
 */

public class EnabledTopicUtils {

	/**
	 * Test whether a topic is enabled
	 * @param topic
	 * @return
	 */
	public static boolean isEnabled(ITopic topic) {
		return topic.isEnabled(HelpEvaluationContext.getContext());
	}
	
	/**
	 * Test whether an entry is enabled
	 * @param entry
	 * @return
	 */
	public static boolean isEnabled(IIndexEntry entry) {
		if (UAContentFilter.isFiltered(entry, HelpEvaluationContext.getContext())) {
			return false;
		}
		ITopic[] topics = entry.getTopics();
		for (int i=0;i<topics.length;++i) {
			if (EnabledTopicUtils.isEnabled(topics[i])) {
				return true;
			}
		}
		IIndexEntry[] subentries = entry.getSubentries();
		for (int i=0;i<subentries.length;++i) {
			if (EnabledTopicUtils.isEnabled(subentries[i])) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Filter out any disabled entries from an array
	 * @param entries an array of entries
	 * @return an array containing only those entries which are enabled
	 */
	public static IIndexEntry[] getEnabled(IIndexEntry[] entries) {
		for (int i=0;i<entries.length;++i) {
			if (!isEnabled(entries[i])) {
				List list = new ArrayList(entries.length);
				for (int j=0;j<entries.length;++j) {
					if (j < i || isEnabled(entries[j])) {
						list.add(entries[j]);
					}
				}
				return (IIndexEntry[])list.toArray(new IIndexEntry[list.size()]);
			}
		}
		return entries;
	}

	/**
	 * Filter out any disable topics form an array
	 * @param topics an array of topics
	 * @return an array containing only those topics which are enabled
	 */
	public static ITopic[] getEnabled(ITopic[] topics) {
		for (int i=0;i<topics.length;++i) {
			if (!isEnabled(topics[i])) {
				List list = new ArrayList(topics.length);
				for (int j=0;j<topics.length;++j) {
					if (j < i || isEnabled(topics[j])) {
						list.add(topics[j]);
					}
				}
				return (ITopic[])list.toArray(new ITopic[list.size()]);
			}
		}
		return topics;
	}

}