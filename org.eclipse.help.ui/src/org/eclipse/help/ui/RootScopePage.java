/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.help.ui;

import java.util.Hashtable;

import org.eclipse.help.ui.internal.HelpUIResources;
import org.eclipse.help.ui.internal.views.*;
import org.eclipse.jface.preference.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

/**
 * Clients that contribute search scope root page to the search engine
 * definition must extend this class and implement
 * <code>createScopeContents</code> method. The page will come preset with the
 * engine name, image and description, as well as the master switch that turns
 * the engine on or off. When the engins master switch is set to false, the
 * entire client composite will be disabled.
 * 
 * @since 3.1
 */
public abstract class RootScopePage extends PreferencePage implements
		ISearchScopePage {
	private IEngineDescriptor ed;

	private String scopeSetName;

	private Button masterButton;

	private Label spacer;

	private Text labelText;

	private Text descText;
	
	private Hashtable disabledStates = new Hashtable();

	/**
	 * The default constructor.
	 */
	public RootScopePage() {
	}

	public void init(IEngineDescriptor ed, String scopeSetName) {
		this.ed = ed;
		this.scopeSetName = scopeSetName;
	}

	/**
	 * Creates the initial contents of the page and allocates the area for the
	 * clients. Classes that extend this class should implement
	 * <code>createScopeContents(Composite)</code> instead.
	 * 
	 * @param parent
	 *            the page parent
	 * @return the page client control
	 */
	protected final Control createContents(Composite parent) {
		initializeDefaults(getPreferenceStore());
		Composite container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		if (ed.isUserDefined())
			layout.numColumns = 2;
		container.setLayout(layout);
		masterButton = new Button(container, SWT.CHECK);
		masterButton.setText(HelpUIResources.getString("RootScopePage.masterButton")); //$NON-NLS-1$
		GridData gd = new GridData();
		gd.horizontalSpan = ed.isUserDefined() ? 2 : 1;
		masterButton.setLayoutData(gd);
		Label spacer = new Label(container, SWT.NULL);
		gd = new GridData();
		gd.horizontalSpan = ed.isUserDefined() ? 2 : 1;
		spacer.setLayoutData(gd);
		boolean masterValue = getPreferenceStore().getBoolean(
				ScopeSet.getMasterKey(ed.getId()));
		masterButton.setSelection(masterValue);
		masterButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				masterValueChanged(masterButton.getSelection());
			}
		});
		if (ed.isUserDefined()) {
			Label label = new Label(container, SWT.NULL);
			label.setText(HelpUIResources.getString("RootScopePage.name")); //$NON-NLS-1$
			labelText = new Text(container, SWT.BORDER);
			gd = new GridData(GridData.FILL_HORIZONTAL);
			gd.widthHint = 200;
			labelText.setLayoutData(gd);
			label = new Label(container, SWT.NULL);
			label.setText(HelpUIResources.getString("RootScopePage.desc")); //$NON-NLS-1$
			gd = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
			label.setLayoutData(gd);
			descText = new Text(container, SWT.BORDER | SWT.MULTI | SWT.WRAP);
			gd = new GridData(GridData.FILL_HORIZONTAL);
			gd.widthHint = 200;
			gd.heightHint = 48;
			descText.setLayoutData(gd);
		}
		int ccol = createScopeContents(container);
		// adjust number of columns if needed
		if (ccol > layout.numColumns) {
			layout.numColumns = ccol;
			gd = (GridData) masterButton.getLayoutData();
			gd.horizontalSpan = layout.numColumns;
			gd = (GridData) spacer.getLayoutData();
			gd.horizontalSpan = layout.numColumns;
			if (ed.isUserDefined()) {
				gd = (GridData) labelText.getLayoutData();
				gd.horizontalSpan = layout.numColumns - 1;
				gd = (GridData) descText.getLayoutData();
				gd.horizontalSpan = layout.numColumns - 1;
			}
		}
		updateControls(true);
		return container;
	}

	/**
	 * Called when the value of the master switch has changed. The default
	 * implementation disables the scope contents control when the master switch
	 * is off. Subclass can override this behaviour.
	 * 
	 * @param value
	 *            <code>true</code> if the master switch is on,
	 *            <code>false</code> otherwise.
	 */

	protected void masterValueChanged(boolean value) {
		updateEnableState(value);
	}

	private void updateEnableState(boolean enabled) {
		Composite container = masterButton.getParent();
		Control[] children = container.getChildren();

		boolean first = disabledStates.isEmpty();
		for (int i = 0; i < children.length; i++) {
			Control child = children[i];
			if (child == masterButton)
				continue;
			if (!enabled) {
				disabledStates.put(child, new Boolean(child.isEnabled()));
				child.setEnabled(false);
			}
			else {
				Boolean savedState = (Boolean)disabledStates.get(child);
				if (!first)
					child.setEnabled(savedState!=null?savedState.booleanValue():true);
			}
		}
	}

	/**
	 * Returns the scope set name passed to the page during initialization.
	 * 
	 * @return the name of the current scope set
	 */

	protected String getScopeSetName() {
		return scopeSetName;
	}

	/**
	 * Returns the descriptor of the engine associated with this page.
	 * 
	 * @return the engine descriptor
	 */

	protected IEngineDescriptor getEngineDescriptor() {
		return ed;
	}

	/**
	 * Tests whether the search engine has been selected to participate in the
	 * search.
	 * 
	 * @return <code>true</code> if the search engine is enabled, </code>false</code>
	 *         otherwise.
	 */

	protected boolean isEngineEnabled() {
		return masterButton.getSelection();
	}

	/**
	 * Stores the value of the master switch in the preference store. Subclasses
	 * may override but must call 'super'.
	 * 
	 * @return <code>true</code> if the wizard can be closed,
	 *         <code>false</code> otherwise.
	 */

	public boolean performOk() {
		getPreferenceStore().setValue(ScopeSet.getMasterKey(ed.getId()),
				masterButton.getSelection());
		if (labelText!=null) {
			ed.setLabel(labelText.getText());
			ed.setDescription(descText.getText());
		}
		return true;
	}

	/**
	 * Sets the value of the master switch to the initial value from the
	 * extension. Subclasses may override but must call 'super'.
	 * 
	 */
	protected void performDefaults() {
		getPreferenceStore().setToDefault(ScopeSet.getMasterKey(ed.getId()));
		updateControls(false);
		super.performDefaults();
	}

	private void updateControls(boolean first) {
		boolean value = getPreferenceStore().getBoolean(
				ScopeSet.getMasterKey(ed.getId()));
		boolean cvalue = masterButton.getSelection();
		if (value != cvalue) {
			masterButton.setSelection(value);
			masterValueChanged(value);
		} else if (first)
			masterValueChanged(value);
		if (ed.isUserDefined()) {
			labelText.setText(ed.getLabel());
			descText.setText(ed.getDescription());
		}
	}

	/**
	 * Initializes default values of the store to be used when the user presses
	 * 'Defaults' button. Subclasses may override but must call 'super'.
	 * 
	 * @param store
	 *            the preference store
	 */

	protected void initializeDefaults(IPreferenceStore store) {
		Boolean value = (Boolean) ed.getParameters().get(
				EngineDescriptor.P_MASTER);
		store.setDefault(ScopeSet.getMasterKey(ed.getId()), value
				.booleanValue());
	}

	/**
	 * Abstract method that subclasses must implement in order to provide root
	 * page content.
	 * 
	 * @param parent
	 *            the page parent
	 * @return number of columns required by the client content
	 */
	protected abstract int createScopeContents(Composite parent);
}