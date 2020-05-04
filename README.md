# Vaadin with Mozilla DeepSpeech
This is the node.js Server only, copied directly from:

* [Mozilla DeepSpeech](https://github.com/mozilla/DeepSpeech-examples/tree/r0.7/web_microphone_websocket)

## Installation instructions

### Clone the repository:
```
git clone https://github.com/Schlomon/Vaadin-MozillaDeepSpeech.git
cd Vaadin-MozillaDeepSpeech
git checkout nodeJsServerOnly
```

### Install dependencies for the Node.js server:
If you don't have yarn please visit the [yarn website](https://yarnpkg.com/getting-started/install) for more information.
```
yarn install
```
Don't run the server now, we still need the models:

### Download the pre-trained model from Mozilla (1.8GB):
Still in NodeJsServer folder:
```
wget https://github.com/mozilla/DeepSpeech/releases/download/v0.6.0/deepspeech-0.6.0-models.tar.gz
tar xvfz deepspeech-0.6.0-models.tar.gz
```

### Finally run the Node.js server:
```
node server.js
```
