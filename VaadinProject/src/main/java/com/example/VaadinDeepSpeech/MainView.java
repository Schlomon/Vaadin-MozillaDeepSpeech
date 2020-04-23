package com.example.VaadinDeepSpeech;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

/**
 * The main view contains two buttons and a speech recognizer
 */
@Route("")
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
@NpmPackage(value = "socket.io", version = "2.3.0")
@NpmPackage(value = "socket.io-client", version = "2.3.0")
@NpmPackage(value = "fs", version = "latest")
public class MainView extends VerticalLayout {

    private SpeechRecognizer speechRecognizer;

    public MainView() {
        speechRecognizer = new SpeechRecognizer();

        // Do your result handling here
        speechRecognizer.addSpeechRecognitionListener(Notification::show);

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(new Button("Start Recording", buttonClickEvent -> speechRecognizer.startRecognition()));
        horizontalLayout.add(new Button("Stop Recording", buttonClickEvent -> speechRecognizer.stopRecognition()));

        this.add(horizontalLayout);

        this.add(speechRecognizer);
    }
}