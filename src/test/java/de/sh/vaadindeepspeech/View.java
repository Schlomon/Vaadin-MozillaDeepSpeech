package de.sh.vaadindeepspeech;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.Route;

@Route("")
public class View extends Div {

    SpeechRecognizer speechRecognizer = new SpeechRecognizer();

    public View() {

        speechRecognizer.addSpeechRecognitionListener(new OnSpeechRecognizedListener() {
            @Override
            public void onSpeechRecognized(String result) {
                Notification.show(result);
            }
        });

        add(speechRecognizer);

        add(new Button("Start Recording", buttonClickEvent -> speechRecognizer.startRecognition()));
        add(new Button("Stop Recording", buttonClickEvent -> speechRecognizer.stopRecognition()));

    }
}
