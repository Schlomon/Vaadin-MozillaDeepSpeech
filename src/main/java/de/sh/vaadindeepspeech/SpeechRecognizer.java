package de.sh.vaadindeepspeech;

import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.html.Div;

import java.util.ArrayList;

@JsModule("./js/recorder.js")
public class SpeechRecognizer extends Div {

    public ArrayList<OnSpeechRecognizedListener> speechRecognitionListeners = new ArrayList();

    public SpeechRecognizer() {
        UI.getCurrent().getPage().executeJs("setJavaElement($0)", getElement());
        UI.getCurrent().getPage().executeJs("createSocket()");
    }

    public void startRecognition() {
        UI.getCurrent().getPage().executeJs("startRecording()");
    }

    public void stopRecognition() {
        UI.getCurrent().getPage().executeJs("stopRecording()");
    }

    @ClientCallable
    public void resultReceived(String result) {
        speechRecognitionListeners.forEach(speechRecognitionListener -> speechRecognitionListener.onSpeechRecognized(result));
    }

    public void addSpeechRecognitionListener(OnSpeechRecognizedListener speechRecognitionListener) {
        speechRecognitionListeners.add(speechRecognitionListener);
    }
}
