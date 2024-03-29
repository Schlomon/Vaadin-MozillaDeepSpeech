# Vaadin with Mozilla DeepSpeech
This is an example project of integrating Mozilla DeepSpeech into a Vaadin 14 LTS project:

* [Vaadin](https://vaadin.com/)
* [Mozilla DeepSpeech](https://github.com/mozilla/DeepSpeech)

## Installation instructions

### Clone the repository:
```
git clone https://github.com/Schlomon/Vaadin-MozillaDeepSpeech.git
cd Vaadin-MozillaDeepSpeech
```

### Install dependencies for the Node.js server:
If you don't have yarn please visit the [yarn website](https://yarnpkg.com/getting-started/install) for more information.
```
cd NodeJsServer
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

### Get Vaadin up and running:
Go to the VaadinProject folder.

Run the maven goal jetty:run.

For further information about how to run a Vaadin project please visit the [Vaadin website](https://vaadin.com/start/).



*Note: the actual recognition is done by the node.js server. It would be better to make the recognition itself in Java, which would lower bandwidth usage and latency.*
