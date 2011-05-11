/*
 * Copyright (c) 2011 Google Inc.
 *
 * All rights reserved. This program and the accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 *
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.google.eclipse.protobuf.ui.preferences.compiler;

import static com.google.eclipse.protobuf.ui.preferences.compiler.Messages.*;
import static com.google.eclipse.protobuf.ui.preferences.compiler.PreferenceNames.*;
import static org.eclipse.core.runtime.IStatus.OK;
import static org.eclipse.xtext.util.Strings.isEmpty;

import java.io.File;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.xtext.ui.editor.preferences.IPreferenceStoreAccess;

import com.google.eclipse.protobuf.ui.preferences.PreferenceAndPropertyPage;
import com.google.eclipse.protobuf.ui.util.FolderNameValidator;
import com.google.inject.Inject;

/**
 * Preference page for protobuf compiler.
 *
 * @author alruiz@google.com (Alex Ruiz)
 */
public class PreferencePage extends PreferenceAndPropertyPage {

  private static final String PREFERENCE_PAGE_ID = PreferencePage.class.getName();

  private Button btnCompileProtoFiles;
  private TabFolder tabFolder;
  private TabItem tbtmMain;
  private TabItem tbtmRefresh;
  private Group grpCompilerLocation;
  private Button btnUseProtocInSystemPath;
  private Button btnUseProtocInCustomPath;
  private Text txtProtocFilePath;
  private Button btnProtocPathBrowse;
  private Group grpTargetLanguage;
  private Button btnJava;
  private Button btnCpp;
  private Button btnPython;
  private Group grpOutput;
  private Text txtOutputFolderName;
  private Label lblOutputFolderName;
  private Button btnRefreshResources;
  private Group grpRefresh;
  private Button btnRefreshProject;
  private Button btnRefreshOutputFolder;
  private Label lblOutputFolderRelative;

  @Inject private FolderNameValidator folderNameValidator;
  
  @Inject public PreferencePage(IPreferenceStoreAccess preferenceStoreAccess) {
    super(preferenceStoreAccess);
  }

  /** {@inheritDoc} */
  @Override protected Control createContents(Composite parent) {
    // generated by WindowBuilder
    Composite contents = contentsComposite(parent);

    btnCompileProtoFiles = new Button(contents, SWT.CHECK);
    btnCompileProtoFiles.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1));
    btnCompileProtoFiles.setText(compileOnSave);

    tabFolder = new TabFolder(contents, SWT.NONE);
    tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

    tbtmMain = new TabItem(tabFolder, SWT.NONE);
    tbtmMain.setText(tabMain);

    Composite cmpMain = new Composite(tabFolder, SWT.NONE);
    tbtmMain.setControl(cmpMain);
    cmpMain.setLayout(new GridLayout(1, false));

    grpCompilerLocation = new Group(cmpMain, SWT.NONE);
    grpCompilerLocation.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
    grpCompilerLocation.setLayout(new GridLayout(4, false));
    grpCompilerLocation.setText(protocLocation);

    btnUseProtocInSystemPath = new Button(grpCompilerLocation, SWT.RADIO);
    btnUseProtocInSystemPath.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 4, 1));
    btnUseProtocInSystemPath.setText(protocInSystemPath);

    btnUseProtocInCustomPath = new Button(grpCompilerLocation, SWT.RADIO);
    btnUseProtocInCustomPath.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 4, 1));
    btnUseProtocInCustomPath.setText(protocInCustomPath);

    txtProtocFilePath = new Text(grpCompilerLocation, SWT.BORDER);
    txtProtocFilePath.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
    txtProtocFilePath.setEditable(false);

    btnProtocPathBrowse = new Button(grpCompilerLocation, SWT.NONE);
    btnProtocPathBrowse.setText(browseCustomPath);
    new Label(grpCompilerLocation, SWT.NONE);
    new Label(grpCompilerLocation, SWT.NONE);

    grpTargetLanguage = new Group(cmpMain, SWT.NONE);
    grpTargetLanguage.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
    grpTargetLanguage.setLayout(new GridLayout(1, false));
    grpTargetLanguage.setText(targetLanguage);

    btnJava = new Button(grpTargetLanguage, SWT.RADIO);
    btnJava.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
    btnJava.setText(generateJava);

    btnCpp = new Button(grpTargetLanguage, SWT.RADIO);
    btnCpp.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
    btnCpp.setText(generateCpp);

    btnPython = new Button(grpTargetLanguage, SWT.RADIO);
    btnPython.setText(generatePython);

    grpOutput = new Group(cmpMain, SWT.NONE);
    grpOutput.setLayout(new GridLayout(2, false));
    grpOutput.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
    grpOutput.setText(generateCode);

    lblOutputFolderName = new Label(grpOutput, SWT.NONE);
    lblOutputFolderName.setText(outputFolderName);

    txtOutputFolderName = new Text(grpOutput, SWT.BORDER);
    txtOutputFolderName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

    lblOutputFolderRelative = new Label(grpOutput, SWT.NONE);
    lblOutputFolderRelative.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
    lblOutputFolderRelative.setText(outputFolderChildOfProjectFolder);

    tbtmRefresh = new TabItem(tabFolder, SWT.NONE);
    tbtmRefresh.setText(tabRefresh);

    Composite cmpRefresh = new Composite(tabFolder, SWT.NONE);
    tbtmRefresh.setControl(cmpRefresh);
    cmpRefresh.setLayout(new GridLayout(1, false));

    btnRefreshResources = new Button(cmpRefresh, SWT.CHECK);
    btnRefreshResources.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
    btnRefreshResources.setText(refreshResources);

    grpRefresh = new Group(cmpRefresh, SWT.NONE);
    grpRefresh.setLayout(new GridLayout(1, false));
    grpRefresh.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

    btnRefreshProject = new Button(grpRefresh, SWT.RADIO);
    btnRefreshProject.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
    btnRefreshProject.setText(refreshProject);

    btnRefreshOutputFolder = new Button(grpRefresh, SWT.RADIO);
    btnRefreshOutputFolder.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
    btnRefreshOutputFolder.setText(refreshOutputProject);
    new Label(contents, SWT.NONE);

    updateFromPreferenceStore();
    addEventListeners();

    return contents;
  }

  private void updateFromPreferenceStore() {
    IPreferenceStore store = doGetPreferenceStore();
    boolean compileProtoFiles = store.getBoolean(COMPILE_PROTO_FILES);
    btnCompileProtoFiles.setSelection(compileProtoFiles);
    btnUseProtocInSystemPath.setSelection(store.getBoolean(USE_PROTOC_IN_SYSTEM_PATH));
    btnUseProtocInCustomPath.setSelection(store.getBoolean(USE_PROTOC_IN_CUSTOM_PATH));
    txtProtocFilePath.setText(store.getString(PROTOC_FILE_PATH));
    btnJava.setSelection(store.getBoolean(GENERATE_JAVA_CODE));
    btnCpp.setSelection(store.getBoolean(GENERATE_CPP_CODE));
    btnPython.setSelection(store.getBoolean(GENERATE_PYTHON_CODE));
    txtOutputFolderName.setText(store.getString(OUTPUT_FOLDER_NAME));
    btnRefreshResources.setSelection(store.getBoolean(REFRESH_RESOURCES));
    btnRefreshProject.setSelection(store.getBoolean(REFRESH_PROJECT));
    btnRefreshOutputFolder.setSelection(store.getBoolean(REFRESH_OUTPUT_FOLDER));
    boolean shouldEnableCompilerOptions = compileProtoFiles;
    if (isPropertyPage()) {
      boolean useProjectSettings = store.getBoolean(ENABLE_PROJECT_SETTINGS);
      activateProjectSettings(useProjectSettings);
      enableProjectSpecificOptions(useProjectSettings);
      shouldEnableCompilerOptions = shouldEnableCompilerOptions && useProjectSettings;
    }
    enableCompilerOptions(shouldEnableCompilerOptions);
  }

  private void addEventListeners() {
    btnCompileProtoFiles.addSelectionListener(new SelectionAdapter() {
      @Override public void widgetSelected(SelectionEvent e) {
        boolean selected = btnCompileProtoFiles.getSelection();
        enableCompilerOptions(selected);
        checkState();
      }
    });
    addSelectionListener(new SelectionAdapter() {
      @Override public void widgetSelected(SelectionEvent e) {
        boolean selected = btnUseProtocInCustomPath.getSelection();
        enableCompilerCustomPathOptions(!selected);
        checkState();
      }
    }, btnUseProtocInCustomPath, btnUseProtocInSystemPath);
    btnProtocPathBrowse.addSelectionListener(new SelectionAdapter() {
      @Override public void widgetSelected(SelectionEvent e) {
        FileDialog dialog = new FileDialog(getShell(), SWT.OPEN | SWT.SHEET);
        String file = dialog.open();
        if (file != null) txtProtocFilePath.setText(file);
      }
    });
    btnRefreshResources.addSelectionListener(new SelectionAdapter() {
      @Override public void widgetSelected(SelectionEvent e) {
        refreshResourcesOptionsEnabled(btnRefreshResources.getSelection());
      }
    });
    addModifyListener(new ModifyListener() {
      public void modifyText(ModifyEvent e) {
        checkState();
      }
    }, txtProtocFilePath, txtOutputFolderName);
  }

  private void checkState() {
    String folderName = txtOutputFolderName.getText().trim();
    if (isEmpty(folderName)) {
      pageIsNowInvalid(errorNoOutputFolderName);
      return;
    }
    IStatus validFolderName = folderNameValidator.validateFolderName(folderName);
    if (validFolderName.getCode() != OK) {
      pageIsNowInvalid(validFolderName.getMessage());
      return;
    }
    if (!customPathOptionSelectedAndEnabled()) {
      pageIsNowValid();
      return;
    }
    String protocPath = txtProtocFilePath.getText();
    if (isEmpty(protocPath)) {
      pageIsNowInvalid(errorNoSelection);
      return;
    }
    File file = new File(protocPath);
    if (!file.isFile() || !"protoc".equals(file.getName())) {
      pageIsNowInvalid(errorInvalidProtoc);
      return;
    }
    pageIsNowValid();
  }

  /** {@inheritDoc} */
  @Override protected void performDefaults() {
    IPreferenceStore store = doGetPreferenceStore();
    boolean compileProtoFiles = store.getDefaultBoolean(COMPILE_PROTO_FILES);
    btnCompileProtoFiles.setSelection(compileProtoFiles);
    btnUseProtocInSystemPath.setSelection(store.getDefaultBoolean(USE_PROTOC_IN_SYSTEM_PATH));
    btnUseProtocInCustomPath.setSelection(store.getDefaultBoolean(USE_PROTOC_IN_CUSTOM_PATH));
    txtProtocFilePath.setText(store.getDefaultString(PROTOC_FILE_PATH));
    btnJava.setSelection(store.getDefaultBoolean(GENERATE_JAVA_CODE));
    btnCpp.setSelection(store.getDefaultBoolean(GENERATE_CPP_CODE));
    btnPython.setSelection(store.getDefaultBoolean(GENERATE_PYTHON_CODE));
    txtOutputFolderName.setText(store.getDefaultString(OUTPUT_FOLDER_NAME));
    btnRefreshResources.setSelection(store.getDefaultBoolean(REFRESH_RESOURCES));
    btnRefreshProject.setSelection(store.getDefaultBoolean(REFRESH_PROJECT));
    btnRefreshOutputFolder.setSelection(store.getDefaultBoolean(REFRESH_OUTPUT_FOLDER));
    boolean enableCompilerOptions = compileProtoFiles;
    if (isPropertyPage()) {
      boolean useProjectSettings = store.getDefaultBoolean(ENABLE_PROJECT_SETTINGS);
      activateProjectSettings(useProjectSettings);
      enableProjectSpecificOptions(useProjectSettings);
      enableCompilerOptions = enableCompilerOptions && useProjectSettings;
    }
    enableCompilerOptions(enableCompilerOptions);
    super.performDefaults();
  }
  
  /** {@inheritDoc} */
  @Override protected void onProjectSettingsActivation(boolean active) {
    enableProjectSpecificOptions(active);
    enableCompilerOptions(isEnabledAndSelected(btnCompileProtoFiles));
  }

  private void enableProjectSpecificOptions(boolean enabled) {
    btnCompileProtoFiles.setEnabled(enabled);
  }

  private void enableCompilerOptions(boolean enabled) {
    tabFolder.setEnabled(enabled);
    enableCompilerPathOptions(enabled);
    enableTargetLanguageOptions(enabled);
    enableOutputOptions(enabled);
    enableRefreshOptions(enabled);
  }

  private void enableCompilerPathOptions(boolean enabled) {
    grpCompilerLocation.setEnabled(enabled);
    btnUseProtocInSystemPath.setEnabled(enabled);
    btnUseProtocInCustomPath.setEnabled(enabled);
    enableCompilerCustomPathOptions(customPathOptionSelectedAndEnabled());
  }

  private void enableCompilerCustomPathOptions(boolean enabled) {
    txtProtocFilePath.setEnabled(enabled);
    btnProtocPathBrowse.setEnabled(enabled);
  }

  private boolean customPathOptionSelectedAndEnabled() {
    return isEnabledAndSelected(btnUseProtocInCustomPath);
  }

  private boolean isEnabledAndSelected(Button b) {
    return b.isEnabled() && b.getSelection();
  }

  private void enableTargetLanguageOptions(boolean enabled) {
    grpTargetLanguage.setEnabled(enabled);
    btnJava.setEnabled(enabled);
    btnCpp.setEnabled(enabled);
    btnPython.setEnabled(enabled);
  }

  private void enableOutputOptions(boolean enabled) {
    grpOutput.setEnabled(enabled);
    lblOutputFolderName.setEnabled(enabled);
    txtOutputFolderName.setEnabled(enabled);
    lblOutputFolderRelative.setEnabled(enabled);
  }

  private void enableRefreshOptions(boolean enabled) {
    btnRefreshResources.setEnabled(enabled);
    refreshResourcesOptionsEnabled(isEnabledAndSelected(btnRefreshResources));
  }

  private void refreshResourcesOptionsEnabled(boolean enabled) {
    grpRefresh.setEnabled(enabled);
    btnRefreshProject.setEnabled(enabled);
    btnRefreshOutputFolder.setEnabled(enabled);
  }

  /** {@inheritDoc} */
  @Override protected void savePreferences() {
    IPreferenceStore store = getPreferenceStore();
    if (isPropertyPage()) store.setValue(ENABLE_PROJECT_SETTINGS, areProjectSettingsActive());
    store.setValue(COMPILE_PROTO_FILES, btnCompileProtoFiles.getSelection());
    store.setValue(USE_PROTOC_IN_SYSTEM_PATH, btnUseProtocInSystemPath.getSelection());
    store.setValue(USE_PROTOC_IN_CUSTOM_PATH, btnUseProtocInCustomPath.getSelection());
    store.setValue(PROTOC_FILE_PATH, txtProtocFilePath.getText());
    store.setValue(GENERATE_JAVA_CODE, btnJava.getSelection());
    store.setValue(GENERATE_CPP_CODE, btnCpp.getSelection());
    store.setValue(GENERATE_PYTHON_CODE, btnPython.getSelection());
    store.setValue(OUTPUT_FOLDER_NAME, txtOutputFolderName.getText().trim());
    store.setValue(REFRESH_RESOURCES, btnRefreshResources.getSelection());
    store.setValue(REFRESH_PROJECT, btnRefreshProject.getSelection());
    store.setValue(REFRESH_OUTPUT_FOLDER, btnRefreshOutputFolder.getSelection());
  }
  
  /** {@inheritDoc} */
  @Override protected String preferencePageId() {
    return PREFERENCE_PAGE_ID;
  }
}
