package org.jabref.gui.fieldeditors;

import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import org.jabref.gui.DialogService;
import org.jabref.gui.autocompleter.SuggestionProvider;
import org.jabref.gui.fieldeditors.contextmenu.EditorMenus;
import org.jabref.logic.integrity.FieldCheckers;
import org.jabref.model.entry.BibEntry;
import org.jabref.model.entry.field.Field;
import org.jabref.preferences.PreferencesService;

/**
 * Field Editor that provides an EditorTextArea for editing the
 * abstract field of the entry editor panel displayed by
 * double-clicking on a JabRef entry.
 */
public class AbstractAreaEditor extends HBox implements FieldEditorFX {
    private final SimpleEditorViewModel viewModel;
    private final EditorTextArea textInput;

    /**
     * Initializes a new AbstractAreaEditor object which will be
     * supplied to the FieldEditors class
     *
     * @param field defines the properties of the actual editor field.
     * @param suggestionProvider enable user suggestions.
     * @param fieldCheckers adds the appropriate StandardFields
     * @param preferences calls the user's preference settings.
     * @param dialogService provides information dialogs for users.
     */
    public AbstractAreaEditor(final Field field,
                        final SuggestionProvider<?> suggestionProvider,
                        final FieldCheckers fieldCheckers,
                        final PreferencesService preferences,
                        final DialogService dialogService) {

        this.viewModel = new SimpleEditorViewModel(field, suggestionProvider, fieldCheckers);
        textInput = new EditorTextArea();
//        exception handling?
        HBox.setHgrow(textInput, Priority.ALWAYS);

        textInput.textProperty().bindBidirectional(viewModel.textProperty());
        textInput.initContextMenu(EditorMenus.getAbstractMenu(textInput, dialogService));
        this.getChildren().add(textInput);

        new EditorValidator(preferences).configureValidation(viewModel.getFieldValidator().getValidationStatus(), textInput);
    }

    /**
     * Implements abstract method in FieldEditorFX class
     * Binds instances of AbstractAreaEditor to BibEntry
     * object
     *
     * @param entry included in a JabRef library (.bib) file
     */
    @Override
    public void bindToEntry(BibEntry entry) {
        viewModel.bindToEntry(entry);
    }

    /**
     * Implements abstract method in FieldEditorFX class
     *
     * @return the Parent node
     */
    @Override
    public Parent getNode() {
        return this;
    }

    /**
     * Overrides method in Node class
     * Requests that this Node get the input focus.
     */
    @Override
    public void requestFocus() {
        textInput.requestFocus();
    }
}


