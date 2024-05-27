package org.jabref.gui.edit;

import java.io.IOException;
import java.util.Objects;

import javafx.scene.control.TextArea;

import org.jabref.gui.DialogService;
import org.jabref.gui.actions.SimpleCommand;
import org.jabref.logic.l10n.Localization;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.jabref.logic.formatter.detector.LangDetector.detect;
import static org.jabref.logic.formatter.translator.Translator.translate;

/**
 * Class for handling language detection and translation calls
 * for the Abstract field of a JabRef bibliographic entry.
 */
public class TranslateAbstractAction extends SimpleCommand {

    private final TextArea textArea;

    private final DialogService dialogService;

    static private final Logger LOGGER = LoggerFactory.getLogger(TranslateAbstractAction.class);

    /**
     * Initializes a new TranslateAbstractAction object
     *
     * @param textArea which will be supplied to EditorMenus.getAbstractMenu
     * @param dialogService provides for dialogues informing user of inability to
     *                      connect to translation service or of an already translated
     *                      abstract
     */
    public TranslateAbstractAction(TextArea textArea, DialogService dialogService) {
        this.textArea = textArea;
        this.dialogService = dialogService;
    }

    /**
     * Method which orchestrates (1) language detection, (2) translation of an
     * untranslated English abstract, and (3) dialogue information for interrupted
     * translation service or texts already translated into German
     */
    @Override
    public void execute() {
        if (!Objects.equals(detect(textArea.getText(0, textArea.getLength()/3)), "DE")) {
            try {
                textArea.insertText(0, translate(textArea.getText()) + "\n=====\n");
            } catch (IOException e) {
                LOGGER.error("No connection to translation service.");
                String couldNotConnect = Localization.lang("Could not connect to translation service.");
                String tryLater = Localization.lang("Please try again later and/or check your network connection.");
                dialogService.showErrorDialogAndWait(Localization.lang("Error"), couldNotConnect + "\n" + tryLater);
            }
        } else {
            LOGGER.info("Entry already translated.");
            dialogService.showInformationDialogAndWait(Localization.lang("Note"), Localization.lang("Text is already translated into German."));
        }
    }
}
