var mediaStream, mediaStreamSource, processor, socket, audioContext, server;

var recognitionCount = 0;

const DOWNSAMPLING_WORKER = './downsampling_worker.js';

var state = {
    connected: false,
    recognitionOutput: []
};

window.setJavaElement = function(el) {
    server = el.$server;
}

window.createSocket = function() {
    var io = require('socket.io-client');

    socket = io.connect('http://localhost:4000', {});
    
    socket.on('connect', () => {
        console.log('socket connected');
        state.connected = true;
    });
    
    socket.on('disconnect', () => {
        console.log('socket disconnected');
        state.connected = false;
        stopRecording();
    });
    
    socket.on('recognize', (results) => {
        console.log('recognized:', results);
        const {recognitionOutput} = state;
        results.id = recognitionCount++;
        recognitionOutput.unshift(results);
        //setState({recognitionOutput});
        state.recognitionOutput = recognitionOutput;
        server.resultReceived(results.text);
    });
}

window.startRecording = function() {
    navigator.mediaDevices.getUserMedia({
        audio: true,
        video: false
    }).then(function(stream) {
        console.log('started recording');
        audioContext = new AudioContext();
        mediaStream = stream;
        mediaStreamSource = audioContext.createMediaStreamSource(stream);
        processor = createAudioProcessor(audioContext, mediaStreamSource);
        mediaStreamSource.connect(processor);
        // mediaStream = stream;
        // recorder = new MediaRecorder(stream);
        // recorder.ondataavailable = function(e) {
        //     var url = URL.createObjectURL(e.data);
        //     var preview = document.createElement('audio');
        //     preview.controls = true;
        //     preview.src = url;
        //     document.body.appendChild(preview);
        // };
        // recorder.start();
    });
}

window.createAudioProcessor = function(audioContext, audioSource) {
    let processor = audioContext.createScriptProcessor(4096, 1, 1);
    
    const sampleRate = audioSource.context.sampleRate;
    
    let downsampler = new Worker(DOWNSAMPLING_WORKER);
    downsampler.postMessage({command: "init", inputSampleRate: sampleRate});
    downsampler.onmessage = (e) => {
        if (state.connected) {
            socket.emit('stream-data', e.data.buffer);
        }
    };
    downsampler.onerror = (e) => {
        console.log('Error with downsampling worker:');
        console.log(e);
    }
    
    processor.onaudioprocess = (event) => {
        var data = event.inputBuffer.getChannelData(0);
        downsampler.postMessage({command: "process", inputFrame: data});
    };
    
    processor.shutdown = () => {
        processor.disconnect();
        //onaudioprocess = null;
    };
    
    processor.connect(audioContext.destination);
    
    return processor;
}

window.stopRecording = function() {
    if (socket.connected) {
        socket.emit('stream-reset');
    }
    if (mediaStream) {
        mediaStream.getTracks()[0].stop();
    }
    if (mediaStreamSource) {
        mediaStreamSource.disconnect();
    }
    if (processor) {
        processor.shutdown();
    }
    if (audioContext) {
        audioContext.close();
    }
}